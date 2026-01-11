import { Routes } from '@angular/router';
import { AppLayoutComponent } from './layout/app-layout/app-layout';

export const routes: Routes = [
    {
        path: '',
        component: AppLayoutComponent,
        children: [
            {
                path: '',
                loadComponent: () =>
                    import('./pages/landing/landing').then((m) => m.LandingComponent),
            },
            {
                path: 'find',
                loadComponent: () =>
                    import('./pages/find-ride/find-ride').then((m) => m.FindRideComponent),
            },
            {
                path: 'about',
                loadComponent: () =>
                    import('./pages/about-us/about-us').then((m) => m.AboutUsComponent),
            },
            {
                path: 'contact',
                loadComponent: () =>
                    import('./pages/contact-us/contact-us').then((m) => m.ContactUsComponent),
            },
            {
                path: 'reviews',
                loadComponent: () =>
                    import('./pages/reviews/reviews').then((m) => m.ReviewsComponent),
            },
            {
                path: 'imprint',
                loadComponent: () =>
                    import('./pages/imprint/imprint').then((m) => m.ImprintComponent),
            },
            {
                path: 'privacy',
                loadComponent: () =>
                    import('./pages/privacy-policy/privacy-policy').then((m) => m.PrivacyPolicyComponent),
            },
            {
                path: 'support',
                loadComponent: () =>
                    import('./pages/support/support').then((m) => m.SupportComponent),
            },
        ],
    },
    { path: '**', redirectTo: '' },
];