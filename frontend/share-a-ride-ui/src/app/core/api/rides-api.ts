import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export type PaymentMethod = 'CASH' | 'PAYPAL' | 'CARD';

export interface RideOfferSummaryDto {
  id: string;
  departureCity: string;
  destinationCity: string;
  departureTime: string;

  seatsAvailable: number;
  luggageCount: number;
  pricePerPerson: number;

  driverName?: string;
  driverChatinessLevel?: number;
  driverOverallKmCovered?: number;
  carMake?: string;
  carModel?: string;
}

export interface RideOfferDetailDto {
  offer: any;
  driver?: any;
  car?: any;
  insurance?: any;
}

export interface CreateRideRequestDto {
  rideOfferId: string;
  personId: string;
  pickupLocation: string;
  dropoffLocation: string;
  luggageCount: number;
  pet: boolean;
  kid: boolean;
  paymentMethod: PaymentMethod;
}

export interface RideRequest {
  id: string;
  rideOfferId: string;
  personId: string;

  pickupLocation: string;
  dropoffLocation: string;

  luggageCount: number;
  pet: boolean;
  kid: boolean;

  paymentMethod: PaymentMethod;

  status: 'PENDING' | 'ACCEPTED' | 'REJECTED';
  rideId?: string;
  passengerId?: string;
}

export interface RideHistoryDto {
  rideId: string;
  departureCity?: string;
  destinationCity?: string;
  departureTime?: string;
  role: 'DRIVER' | 'PASSENGER';
}

@Injectable({ providedIn: 'root' })
export class RidesApi {
  private readonly baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  searchRideOffers(departureCity: string, destinationCity: string, date?: string) {
    let params = new HttpParams()
      .set('departureCity', departureCity)
      .set('destinationCity', destinationCity);

    if (date) params = params.set('date', date);

    return this.http.get<RideOfferSummaryDto[]>(`${this.baseUrl}/rideoffers/search`, { params });
  }

  getRideOfferDetail(id: string) {
    return this.http.get<RideOfferDetailDto>(`${this.baseUrl}/rideoffers/${id}`);
  }

  createRideRequest(dto: CreateRideRequestDto) {
    return this.http.post<RideRequest>(`${this.baseUrl}/riderequests`, dto);
  }

  getRideRequest(id: string) {
    return this.http.get<RideRequest>(`${this.baseUrl}/riderequests/${id}`);
  }

  getRide(id: string) {
    return this.http.get<any>(`${this.baseUrl}/rides/${id}`);
  }

  getRidePassengers(rideId: string) {
    return this.http.get<any[]>(`${this.baseUrl}/rides/${rideId}/passengers`);
  }

  getPersonRides(personId: string) {
  return this.http.get<RideHistoryDto[]>(`${this.baseUrl}/persons/${personId}/rides`);
}
}