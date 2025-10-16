import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { environment } from '../../environments/environment';

export interface AuthResponse {
  token: string;
  refreshToken: string;
  type: string;
  username: string;
  email: string;
  role: string;
  expiresIn: number;
  message: string;
  preferredLanguage: string;
  preferredCurrency: string;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private baseUrl = `${environment.apiUrl}/api/auth`;
  private tokenKey = 'auth_token';
  private refreshKey = 'refresh_token';
  private userKey = 'auth_user';

  constructor(private http: HttpClient) {}

  register(payload: any): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.baseUrl}/register`, payload).pipe(
      tap((res) => this.persist(res))
    );
  }

  login(payload: { username: string; password: string; language?: string }): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.baseUrl}/login`, payload).pipe(
      tap((res) => this.persist(res))
    );
  }

  refresh(): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.baseUrl}/refresh`, {}, { observe: 'body' }).pipe(
      tap((res) => this.persist(res))
    );
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.refreshKey);
    localStorage.removeItem(this.userKey);
  }

  get token(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  get user(): any | null {
    const raw = localStorage.getItem(this.userKey);
    return raw ? JSON.parse(raw) : null;
  }

  private persist(res: AuthResponse) {
    localStorage.setItem(this.tokenKey, res.token);
    localStorage.setItem(this.refreshKey, res.refreshToken);
    localStorage.setItem(this.userKey, JSON.stringify({
      username: res.username,
      email: res.email,
      role: res.role,
      preferredLanguage: res.preferredLanguage,
      preferredCurrency: res.preferredCurrency,
    }));
  }
}