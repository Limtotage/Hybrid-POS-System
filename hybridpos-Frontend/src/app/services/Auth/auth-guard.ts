import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { Auth } from './auth';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(Auth)
  const router = inject(Router)
  if(!authService.isLoggedIn()){
    router.navigate(['/login']);
    return false;
  }
  const expectedRole = route.data['role'];
  if(expectedRole){
    const userRole=authService.getRoleFromToken();
    if(userRole != expectedRole){
      router.navigate(['/login']);
      return false;
    }
  }
  return true;
};
