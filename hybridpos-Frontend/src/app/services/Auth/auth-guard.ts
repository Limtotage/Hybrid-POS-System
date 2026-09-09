import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

import { Auth } from './auth';

export const authGuard: CanActivateFn = (route) => {

  const authService = inject(Auth);
  const router = inject(Router);

  if (!authService.isLoggedIn()) {

    return router.createUrlTree(['/login']);

  }

  const expectedRole = route.data['role'];

  if (expectedRole) {

    const userRole = authService.getRoleFromToken();

    if (userRole !== expectedRole) {

      if (userRole === 'ADMIN') {

        return router.createUrlTree(['/admin']);

      }

      if (userRole === 'CASHIER') {

        return router.createUrlTree(['/cashier']);

      }

      authService.logout();

      return router.createUrlTree(['/login']);
    }
  }

  return true;
};
