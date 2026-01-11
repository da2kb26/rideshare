import { Component, OnDestroy, OnInit, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { Subscription, debounceTime, distinctUntilChanged, interval, startWith, switchMap, takeWhile, timeout, forkJoin } from 'rxjs';

import { RidesApi, RideOfferSummaryDto, CreateRideRequestDto, RideRequest, RideOfferDetailDto } from '../../core/api/rides-api';

type UiState = 'idle' | 'loading' | 'done' | 'error';

@Component({
  selector: 'app-find-ride',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './find-ride.html',
  styleUrl: './find-ride.scss',
})
export class FindRideComponent implements OnInit, OnDestroy {
  state: UiState = 'idle';
  errorMessage = '';
  detailsExpandedOfferId: string | null = null;
  joinExpandedOfferId: string | null = null;
  detailsById: Record<string, any> = {};
  detailsLoadingById: Record<string, boolean> = {};
  detailsErrorById: Record<string, string> = {};
  allResults: RideOfferSummaryDto[] = [];
  results: RideOfferSummaryDto[] = [];
  joinFormsByOfferId: Record<string, ReturnType<FormBuilder['group']>> = {};
  joinStateByOfferId: Record<string, { stage: 'idle' | 'requesting' | 'waiting' | 'accepted' | 'error'; message?: string; requestId?: string; rideId?: string }> = {};

  // For the prototype: choose a passenger that exists in your seed data // this is me, Tim152
  private readonly passengerPersonId = 'person_passenger_1';
  private readonly fb = inject(FormBuilder);
  private sub = new Subscription();

  // Search criteria -> triggers backend call
  searchForm = this.fb.group({
    departureCity: [''],
    destinationCity: [''],
    date: [''], // optional (yyyy-mm-dd)
  });

  // Filters -> client-side only
  filterForm = this.fb.group({
    time: [''],        // optional (HH:mm)
    minSeats: [1],
    minLuggage: [0],
  });

  constructor(private api: RidesApi, private route: ActivatedRoute, private cdr: ChangeDetectorRef) { }

  ngOnInit(): void {
    // Prefill from query params
    this.sub.add(
      this.route.queryParamMap.subscribe((p) => {
        this.searchForm.patchValue(
          {
            departureCity: p.get('departureCity') ?? '',
            destinationCity: p.get('destinationCity') ?? '',
            date: p.get('date') ?? '',
          },
          { emitEvent: false }
        );

        this.filterForm.patchValue(
          {
            time: p.get('time') ?? '',
            minSeats: Number(p.get('minSeats') ?? 1),
            minLuggage: Number(p.get('minLuggage') ?? 0),
          },
          { emitEvent: false }
        );

        // run once on init if dep+dst present
        this.maybeSearch();
      })
    );

    // Search-as-you-type (debounced) for departure/destination/date
    this.sub.add(
      this.searchForm.valueChanges
        .pipe(
          debounceTime(250),
          distinctUntilChanged((a, b) => JSON.stringify(a) === JSON.stringify(b))
        )
        .subscribe(() => this.maybeSearch())
    );

    // Filters apply immediately client-side
    this.sub.add(this.filterForm.valueChanges.subscribe(() => this.applyClientFilters()));
  }

  ngOnDestroy(): void {
    this.sub.unsubscribe();
  }

  private maybeSearch(): void {
    const v = this.searchForm.value;
    const dep = (v.departureCity ?? '').trim();
    const dst = (v.destinationCity ?? '').trim();
    const date = (v.date ?? '').trim() ? (v.date ?? '').trim() : undefined;

    if (!dep || !dst) {
      this.state = 'idle';
      this.allResults = [];
      this.results = [];
      return;
    }

    this.state = 'loading';
    this.errorMessage = '';
    this.allResults = [];
    this.results = [];

    this.api.searchRideOffers(dep, dst, date).pipe(timeout(7000)).subscribe({
      next: (rows) => {
        this.allResults = rows ?? [];
        this.state = 'done';
        this.applyClientFilters();

        this.detailsExpandedOfferId = null;
        this.joinExpandedOfferId = null;
        this.cdr.markForCheck(); // <-- add
      },
      error: (err) => {
        this.state = 'error';
        this.errorMessage =
          err?.error?.message ??
          err?.message ??
          'Search failed. Ensure backend is running on http://localhost:8080.';

        this.cdr.markForCheck(); // <-- add
      },
    });
  }

  private applyClientFilters(): void {
    if (this.state !== 'done') return;

    const f = this.filterForm.value;
    const minSeats = Number(f.minSeats ?? 1);
    const minLuggage = Number(f.minLuggage ?? 0);
    const time = (f.time ?? '').trim();

    let filtered = [...this.allResults];

    filtered = filtered.filter((r) => (r.seatsAvailable ?? 0) >= minSeats);
    filtered = filtered.filter((r) => (r.luggageCount ?? 0) >= minLuggage);

    if (time) {
      const [hh, mm] = time.split(':').map((x) => Number(x));
      const selectedMinutes = hh * 60 + mm;

      filtered = filtered.filter((r) => {
        if (!r.departureTime) return true;
        const d = new Date(r.departureTime);
        const rideMinutes = d.getHours() * 60 + d.getMinutes();
        return rideMinutes >= selectedMinutes;
      });
    }

    filtered.sort((a, b) => {
      const ta = a.departureTime ? new Date(a.departureTime).getTime() : 0;
      const tb = b.departureTime ? new Date(b.departureTime).getTime() : 0;
      return ta - tb;
    });

    this.results = filtered;
    this.cdr.markForCheck();
  }

  formatInstant(iso?: string): string {
    if (!iso) return '';
    const d = new Date(iso);
    return d.toLocaleString(undefined, {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit',
    });
  }

  toggleDetails(offerId: string): void {
    if (this.detailsExpandedOfferId === offerId) {
      this.detailsExpandedOfferId = null; // hide details only
      this.cdr.markForCheck();
      return;
    }

    this.detailsExpandedOfferId = offerId;
    this.loadDetails(offerId);
    this.cdr.markForCheck();
  }

  private loadDetails(offerId: string): void {
    if (this.detailsById[offerId] || this.detailsLoadingById[offerId]) return;

    this.detailsLoadingById[offerId] = true;
    this.detailsErrorById[offerId] = '';

    this.api.getRideOfferDetail(offerId).subscribe({
      next: (dto) => {
        this.detailsById[offerId] = dto;
        this.detailsLoadingById[offerId] = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        this.detailsLoadingById[offerId] = false;
        this.detailsErrorById[offerId] =
          err?.error?.message ?? err?.message ?? 'Failed to load details.';
        this.cdr.markForCheck();
      },
    });
  }

  getJoinForm(offer: RideOfferSummaryDto) {
    const id = offer.id;

    if (!this.joinFormsByOfferId[id]) {
      this.joinFormsByOfferId[id] = this.fb.group({
        pickupLocation: [offer.departureCity],
        dropoffLocation: [offer.destinationCity],
        luggageCount: [0],
        pet: [false],
        kid: [false],
        paymentMethod: ['CASH'],
      });

      this.joinStateByOfferId[id] = { stage: 'idle' };
    }

    return this.joinFormsByOfferId[id];
  }

  openJoin(offer: RideOfferSummaryDto): void {
    this.joinExpandedOfferId = offer.id;
    this.detailsExpandedOfferId = offer.id; // requirement: Join opens details too
    this.loadDetails(offer.id);
    this.getJoinForm(offer);
    this.cdr.markForCheck();
  }

  cancelJoin(offerId: string): void {
    if (this.joinExpandedOfferId === offerId) this.joinExpandedOfferId = null;
    if (this.detailsExpandedOfferId === offerId) this.detailsExpandedOfferId = null;
    this.cdr.markForCheck();
  }

  submitJoin(offer: RideOfferSummaryDto): void {
    const form = this.getJoinForm(offer);
    const id = offer.id;

    const v = form.value as any;

    const dto: CreateRideRequestDto = {
      rideOfferId: id,
      personId: this.passengerPersonId,
      pickupLocation: (v.pickupLocation ?? '').trim(),
      dropoffLocation: (v.dropoffLocation ?? '').trim(),
      luggageCount: Number(v.luggageCount ?? 0),
      pet: Boolean(v.pet),
      kid: Boolean(v.kid),
      paymentMethod: (v.paymentMethod ?? 'CASH'),
    };

    if (!dto.pickupLocation || !dto.dropoffLocation) {
      this.joinStateByOfferId[id] = { stage: 'error', message: 'Pickup and dropoff are required.' };
      this.cdr.markForCheck();
      return;
    }

    this.joinStateByOfferId[id] = { stage: 'requesting', message: 'Creating request…' };
    this.cdr.markForCheck();

    this.api.createRideRequest(dto).subscribe({
      next: (rr) => {
        this.joinStateByOfferId[id] = {
          stage: rr.status === 'ACCEPTED' ? 'accepted' : 'waiting',
          message: rr.status === 'ACCEPTED' ? 'Accepted.' : 'Waiting for driver acceptance…',
          requestId: rr.id,
          rideId: rr.rideId,
        };
        this.cdr.markForCheck();

        if (rr.status === 'ACCEPTED') {
          this.onAccepted(offer, rr);
        } else {
          this.pollRideRequest(offer, rr.id);
        }
      },
      error: (err) => {
        this.joinStateByOfferId[id] = {
          stage: 'error',
          message: err?.error?.message ?? err?.message ?? 'Failed to create ride request.',
        };
        this.cdr.markForCheck();
      },
    });
  }

  private pollRideRequest(offer: RideOfferSummaryDto, requestId: string): void {
    const offerId = offer.id;

    interval(800)
      .pipe(
        startWith(0),
        switchMap(() => this.api.getRideRequest(requestId)),
        takeWhile((rr) => rr.status === 'PENDING', true),
        timeout(20000)
      )
      .subscribe({
        next: (rr) => {
          if (rr.status === 'PENDING') {
            this.joinStateByOfferId[offerId] = {
              stage: 'waiting',
              message: 'Waiting for driver acceptance…',
              requestId,
              rideId: rr.rideId,
            };
            this.cdr.markForCheck();
            return;
          }

          if (rr.status === 'REJECTED') {
            this.joinStateByOfferId[offerId] = { stage: 'error', message: 'Request was rejected.' };
            this.cdr.markForCheck();
            return;
          }

          // ACCEPTED
          this.joinStateByOfferId[offerId] = {
            stage: 'accepted',
            message: 'Accepted. You joined the ride.',
            requestId,
            rideId: rr.rideId,
          };
          this.cdr.markForCheck();
          this.onAccepted(offer, rr);
        },
        error: () => {
          this.joinStateByOfferId[offerId] = {
            stage: 'error',
            message: 'Polling timed out. Please try again.',
          };
          this.cdr.markForCheck();
        },
      });
  }

  private onAccepted(offer: RideOfferSummaryDto, rr: RideRequest): void {
    // Optional: load ride + passengers for confirmation
    if (rr.rideId) {
      forkJoin({
        ride: this.api.getRide(rr.rideId),
        passengers: this.api.getRidePassengers(rr.rideId),
      }).subscribe({
        next: () => {
          // If you want, store these and display them in the UI later.
          this.cdr.markForCheck();
        },
        error: () => {
          // Not critical for prototype; ignore
        },
      });
    }

    // Optional: update local UI counts immediately (so user sees seats/luggage drop)
    const form = this.getJoinForm(offer);
    const v = form.value as any;

    const seatCost = 1 + (v.pet ? 1 : 0) + (v.kid ? 1 : 0);
    const luggageUsed = Number(v.luggageCount ?? 0);

    offer.seatsAvailable = Math.max(0, (offer.seatsAvailable ?? 0) - seatCost);
    offer.luggageCount = Math.max(0, (offer.luggageCount ?? 0) - luggageUsed);

    this.cdr.markForCheck();
  }

}