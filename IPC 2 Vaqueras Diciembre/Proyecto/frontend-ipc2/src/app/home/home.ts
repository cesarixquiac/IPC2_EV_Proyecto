import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService, MeResponse } from '../services/auth';
import { NgIf } from '@angular/common';
import { WalletService } from '../services/wallet';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [NgIf, FormsModule],
  templateUrl: './home.html',
})
export class Home implements OnInit {
  loading = true;
  error = '';
  me: MeResponse | null = null;

  monto = 0;
saldo = 0;

constructor(private auth: AuthService, private wallet: WalletService, private router: Router) {}

ngOnInit(): void {
  this.auth.fetchMe().subscribe({
    next: (data) => {
      this.me = data;
      this.loading = false;

      if (this.me?.rol === 'GAMER') {
        this.wallet.saldo().subscribe({ next: r => this.saldo = r.saldo });
      }
    },
    error: () => this.router.navigate(['/login']),
  });
}

recargar(): void {
  if (this.monto <= 0) return;
  this.wallet.recargar(this.monto).subscribe({
    next: (r) => { this.saldo = r.saldo; this.monto = 0; },
  });
}

  logout(): void {
    this.auth.logout().subscribe({
      next: () => this.router.navigate(['/login']),
      error: () => this.router.navigate(['/login']),
    });
  }
}
