import { Routes } from '@angular/router';

import { Registro } from './pages/registro/registro';
import { authGuard } from './auth-guard';

export const routes: Routes = [
    {path: 'registro', component: Registro},
    {path: '', redirectTo   : 'registro', pathMatch: 'full'},   
    { path: 'login', loadComponent: () => import('./login/login').then(m => m.LoginComponent) },
    { path: 'home', canActivate: [authGuard], loadComponent: () => import('./home/home').then(m => m.Home) },


];

