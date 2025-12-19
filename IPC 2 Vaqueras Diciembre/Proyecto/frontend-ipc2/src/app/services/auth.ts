import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';

export interface LoginRequest {
  email: string;
  password: string;
}

export interface MeResponse {
  userId: number;
  rol: 'ADMIN' | 'EMPRESA' | 'GAMER';
  email: string;
  gamerId?: number | null;
  nickname?: string | null;
}

@Injectable({ providedIn: 'root' })

export class AuthService {
  private baseUrl = 'http://localhost:8080/IPC2_EV_Diciembre/api';


  me: MeResponse | null = null;

  constructor(private http: HttpClient) {}

  login(data: LoginRequest): Observable<any> {
    return this.http.post(`${this.baseUrl}/auth/login`, data, { withCredentials: true })
      .pipe(
        tap(() => {
          
          this.fetchMe().subscribe({ error: () => {} });
        })
      );
  }

  fetchMe(): Observable<MeResponse> {
    return this.http.get<MeResponse>(`${this.baseUrl}/auth/me`, { withCredentials: true })
      .pipe(tap((me) => this.me = me));
  }

  logout(): Observable<any> {
    return this.http.post(`${this.baseUrl}/auth/logout`, {}, { withCredentials: true })
      .pipe(tap(() => this.me = null));
  }

  isLoggedIn(): boolean {
    return this.me != null;
  }
}
