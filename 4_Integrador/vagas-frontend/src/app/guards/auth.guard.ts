import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  
  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    // Verifica se o usuário está autenticado
    if (this.authService.isAutenticado()) {
      // Verifica se a rota exige perfis específicos
      const perfisRequeridos = route.data['perfis'] as string[];
      
      if (perfisRequeridos && perfisRequeridos.length > 0) {
        // Verifica se o usuário tem um dos perfis requeridos
        if (this.authService.temAlgumPerfil(perfisRequeridos)) {
          return true;
        } else {
          // Usuário não tem permissão - redireciona para dashboard
          this.router.navigate(['/dashboard']);
          return false;
        }
      }
      
      // Não há requisitos de perfil, apenas autenticação
      return true;
    }

    // Usuário não está autenticado - redireciona para login
    // Salva a URL que o usuário tentou acessar para redirecionar após login
    this.router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
    return false;
  }
}
