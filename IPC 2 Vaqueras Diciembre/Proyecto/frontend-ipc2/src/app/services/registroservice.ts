import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RegistroService {

  private http = inject(HttpClient);

  private apiUrl = 'http://localhost:8080/IPC2_EV_Diciembre/api/gamer/registro';

  registrar(data: any) {
    return this.http.post(this.apiUrl, data);
  }
}
