import { Routes } from '@angular/router';

import { authGuard } from './services/Auth/auth-guard';
import { Login } from './services/Auth/login/login';

export const routes: Routes = [

  {
    path: 'login',
    component: Login
  },

  {
    path: 'admin',
    loadChildren: () =>
      import('./mycomponents/admin-page/admin-module')
        .then(m => m.AdminModule),

    canActivate: [authGuard],

    data: {
      role: 'ADMIN'
    }
  },

  {
    path: 'cashier',
    loadComponent: () =>
      import('./mycomponents/cashier-page/cashier-page')
        .then(m => m.CashierPage),

    canActivate: [authGuard],

    data: {
      role: 'CASHIER'
    }
  },

  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },

  {
    path: '**',
    redirectTo: 'login'
  }

];
