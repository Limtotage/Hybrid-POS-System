import { Routes } from '@angular/router';
import { authGuard } from './services/Auth/auth-guard';
import { Register } from './services/Auth/register/register';
import { Login } from './services/Auth/login/login';

export const routes: Routes = [
  { path: 'login', component: Login },
  { path: 'register', component: Register },

  {
    path: 'owner',
    loadChildren: () => import('./mycomponents/owner-page/owner-module').then((m) => m.OwnerModule),
    canActivate: [authGuard],
    data: { role: 'OWNER' },
  },

  {
    path: 'cashier',
    loadComponent: () =>
      import('./mycomponents/cashier-page/cashier-page').then((m) => m.CashierPage),
    canActivate: [authGuard],
    data: { role: 'CASHIER' },
  },

  { path: '**', redirectTo: 'login' },
];
