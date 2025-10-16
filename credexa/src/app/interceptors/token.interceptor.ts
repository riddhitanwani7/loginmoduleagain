import { HttpInterceptorFn, HttpRequest, HttpHandlerFn, HttpEvent } from '@angular/common/http';
import { inject } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

export const tokenInterceptor: HttpInterceptorFn = (req: HttpRequest<unknown>, next: HttpHandlerFn): Observable<HttpEvent<unknown>> => {
  const auth = inject(AuthService);
  const accessToken = auth.token;
  const user = auth.user;
  const headers: { [key: string]: string } = { 'Accept-Language': (user?.preferredLanguage || 'EN').toString() };

  // Use refresh token for refresh endpoint
  const isRefresh = req.url.includes('/api/auth/refresh');
  const refreshToken = localStorage.getItem('refresh_token');
  const bearer = isRefresh ? refreshToken : accessToken;

  const authReq = bearer
    ? req.clone({ setHeaders: { ...headers, Authorization: `Bearer ${bearer}` } })
    : req.clone({ setHeaders: headers });
  return next(authReq);
};
