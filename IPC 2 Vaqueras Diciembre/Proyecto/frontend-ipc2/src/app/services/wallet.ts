import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class WalletService {
  private baseUrl = 'http://localhost:8080/IPC2_EV_Diciembre/api';

  constructor(private http: HttpClient) {}

  saldo(): Observable<{ saldo: number }> {
    return this.http.get<{ saldo: number }>(`${this.baseUrl}/gamer/cartera/saldo`, { withCredentials: true });
  }

  recargar(monto: number): Observable<{ mensaje: string; saldo: number }> {
    return this.http.post<{ mensaje: string; saldo: number }>(
      `${this.baseUrl}/gamer/cartera/recargar`,
      { monto },
      { withCredentials: true }
    );
  }
}
