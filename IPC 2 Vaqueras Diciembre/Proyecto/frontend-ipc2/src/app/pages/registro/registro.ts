  import { Component, inject } from '@angular/core';
  import { FormsModule } from '@angular/forms';
  import { RegistroService } from '../../services/registroservice';

  @Component({
    selector: 'app-registro',
    standalone: true,
    imports: [FormsModule],
    templateUrl: './registro.html',
    styleUrl: './registro.css',
  })
  export class Registro {

    constructor(private registroService: RegistroService) {}

    usuario = {
      email: '',
      password: '',
      nickname: '',
      fechaNacimiento: ''
    };

    registrar() {
      this.registroService.registrar(this.usuario)
        .subscribe({
          next: () => alert('Registro exitoso'),
          error: err => console.error(err)
        });
    }
  }

