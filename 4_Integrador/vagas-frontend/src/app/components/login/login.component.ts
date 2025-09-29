import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  loginData = {
    usuario: '',
    senha: ''
  };

  constructor(private router: Router) {}

  onLogin() {
    // Simulação de login - substitua pela lógica real de autenticação
    if (this.loginData.usuario === 'admin' && this.loginData.senha === 'admin') {
      Swal.fire({
        title: 'Sucesso!',
        text: 'Login realizado com sucesso!',
        icon: 'success',
        timer: 1500,
        showConfirmButton: false
      }).then(() => {
        this.router.navigate(['/app']);
      });
    } else {
      Swal.fire({
        title: 'Erro!',
        text: 'Usuário ou senha inválidos!',
        icon: 'error',
        confirmButtonText: 'OK'
      });
    }
  }
}
