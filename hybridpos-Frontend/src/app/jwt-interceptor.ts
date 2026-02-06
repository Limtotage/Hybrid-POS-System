import { HttpInterceptorFn } from '@angular/common/http';
import { Auth } from './services/Auth/auth';
import { inject } from '@angular/core';

export const jwtInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(Auth);
  const token = authService.getToken();

  const isAuthRequest = req.url.includes('/auth/');

  if (!isAuthRequest && token && token !== 'undefined') {
    req = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
  }

  return next(req);
};

