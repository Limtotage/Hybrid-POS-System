import { Routes } from '@angular/router';
import { authGuard } from './services/Auth/auth-guard';
import { Login } from './components/login/login';

export const routes: Routes = [
  { path: 'login', component: Login },

  {
    path: 'owner',
    loadChildren: () =>
      import('./modules/owner/owner-module').then(m => m.OwnerModule),
    canActivate: [authGuard],
    data: { role: 'OWNER' }
  },

  {
    path: 'cashier',
    loadChildren: () =>
      import('./modules/cashier/cashier-module').then(m => m.CashierModule),
    canActivate: [authGuard],
    data: { role: 'CASHIER' }
  },

  { path: '**', redirectTo: 'login' }
];
