import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService, UsuarioLogado } from '../../services/auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-menu-superior',
  imports: [RouterModule, CommonModule],
  templateUrl: './menu-superior.component.html',
  styleUrl: './menu-superior.component.scss'
})
export class MenuSuperiorComponent implements OnInit {
  usuarioLogado: UsuarioLogado | null = null;

  constructor(
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit() {
    // Subscrever para mudanças no usuário logado
    this.authService.usuarioLogado$.subscribe(usuario => {
      this.usuarioLogado = usuario;
    });
  }

  logout() {
    Swal.fire({
      title: 'Confirmação',
      text: 'Deseja realmente sair do sistema?',
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: 'Sim, sair',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.authService.logout();
        Swal.fire({
          title: 'Sucesso!',
          text: 'Logout realizado com sucesso!',
          icon: 'success',
          timer: 1500,
          showConfirmButton: false
        });
      }
    });
  }

  // Métodos auxiliares para exibir/ocultar menus baseado no perfil
  isAdmin(): boolean {
    return this.authService.isAdmin();
  }

  isGerente(): boolean {
    return this.authService.isGerente();
  }

  isFuncionario(): boolean {
    return this.authService.isFuncionario();
  }

  temAlgumPerfil(perfis: string[]): boolean {
    return this.authService.temAlgumPerfil(perfis);
  }
}
