import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  loginData = {
    email: '',
    senha: ''
  };
  
  loading = false;
  returnUrl: string = '/app/dashboard';

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService
  ) {
    // Se já está autenticado, redireciona para dashboard
    if (this.authService.isAutenticado()) {
      this.router.navigate(['/app/dashboard']);
    }
    
    // Obtém a URL de retorno dos query params
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/app/dashboard';
  }

  onLogin() {
    // Validação básica
    if (!this.loginData.email || !this.loginData.senha) {
      Swal.fire({
        title: 'Atenção!',
        text: 'Por favor, preencha email e senha!',
        icon: 'warning',
        confirmButtonText: 'OK'
      });
      return;
    }

    this.loading = true;

    // Chama o serviço de autenticação
    this.authService.login(this.loginData.email, this.loginData.senha).subscribe({
      next: (response) => {
        this.loading = false;
        
        Swal.fire({
          title: 'Sucesso!',
          text: `Bem-vindo(a), ${response.nome}!`,
          icon: 'success',
          timer: 1500,
          showConfirmButton: false
        }).then(() => {
          // Redireciona para a URL solicitada ou dashboard
          this.router.navigateByUrl(this.returnUrl);
        });
      },
      error: (error) => {
        this.loading = false;
        
        let errorMessage = 'Email ou senha inválidos!';
        
        if (error.error && error.error.erro) {
          errorMessage = error.error.erro;
        } else if (error.status === 0) {
          errorMessage = 'Não foi possível conectar ao servidor. Verifique sua conexão.';
        }
        
        Swal.fire({
          title: 'Erro!',
          text: errorMessage,
          icon: 'error',
          confirmButtonText: 'OK'
        });
      }
    });
  }
}
