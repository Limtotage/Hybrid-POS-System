import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';

import { Auth } from './services/Auth/auth';

export const jwtInterceptor: HttpInterceptorFn = (req, next) => {

  const authService = inject(Auth);

  const token = authService.getToken();

  const isAuthRequest = req.url.includes('/auth/');

  if (
    !isAuthRequest &&
    token &&
    token !== 'undefined'
  ) {

    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });

  }

  return next(req);
};
