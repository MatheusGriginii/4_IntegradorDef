import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { UsuarioService } from '../../../services/usuario.service';
import { Usuario } from '../../../models/usuario';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-usuario-list',
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './usuario-list.component.html',
  styleUrl: './usuario-list.component.scss'
})
export class UsuarioListComponent implements OnInit {
  usuarios: Usuario[] = [];
  usuariosFiltrados: Usuario[] = [];
  loading = false;
  
  filtros = {
    nome: '',
    perfil: ''
  };

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit() {
    this.carregarUsuarios();
  }

  carregarUsuarios() {
    this.loading = true;
    this.usuarioService.listar().subscribe({
      next: (usuarios) => {
        this.usuarios = usuarios;
        this.usuariosFiltrados = usuarios;
        this.loading = false;
      },
      error: (error: any) => {
        console.error('Erro ao carregar usuários:', error);
        this.loading = false;
        
        let mensagem = 'Erro ao carregar usuários';
        
        if (typeof error === 'string') {
          mensagem = error;
        } else if (error?.message) {
          mensagem = error.message;
        } else if (error?.error?.message) {
          mensagem = error.error.message;
        }
        
        Swal.fire({
          title: 'Erro de Conexão!',
          text: mensagem + '\n\nVerifique se o backend está rodando na porta 8080.',
          icon: 'error',
          confirmButtonText: 'OK'
        });
      }
    });
  }

  filtrarUsuarios() {
    this.usuariosFiltrados = this.usuarios.filter(usuario => {
      const nomeMatch = !this.filtros.nome || 
        usuario.nome.toLowerCase().includes(this.filtros.nome.toLowerCase());
      const perfilMatch = !this.filtros.perfil || 
        usuario.perfil === this.filtros.perfil;
      
      return nomeMatch && perfilMatch;
    });
  }

  limparFiltros() {
    this.filtros.nome = '';
    this.filtros.perfil = '';
    this.usuariosFiltrados = this.usuarios;
  }

  confirmarExclusao(usuario: Usuario) {
    Swal.fire({
      title: 'Confirmação',
      text: `Deseja realmente excluir o usuário "${usuario.nome}"?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sim, excluir',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#dc3545'
    }).then((result) => {
      if (result.isConfirmed && usuario.id) {
        this.excluirUsuario(usuario.id);
      }
    });
  }

  excluirUsuario(id: number) {
    this.usuarioService.deletar(id).subscribe({
      next: () => {
        Swal.fire({
          title: 'Sucesso!',
          text: 'Usuário excluído com sucesso!',
          icon: 'success',
          timer: 1500,
          showConfirmButton: false
        });
        this.carregarUsuarios();
      },
      error: (error: any) => {
        console.error('Erro ao excluir usuário:', error);
        Swal.fire({
          title: 'Erro!',
          text: error.error || 'Erro ao excluir usuário',
          icon: 'error'
        });
      }
    });
  }

  alterarStatus(usuario: Usuario) {
    const novoStatus = !usuario.ativo;
    const acao = novoStatus ? 'ativar' : 'desativar';
    
    Swal.fire({
      title: 'Confirmação',
      text: `Deseja realmente ${acao} o usuário "${usuario.nome}"?`,
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: `Sim, ${acao}`,
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed && usuario.id) {
        this.usuarioService.alterarStatus(usuario.id, novoStatus).subscribe({
          next: () => {
            Swal.fire({
              title: 'Sucesso!',
              text: `Usuário ${acao}do com sucesso!`,
              icon: 'success',
              timer: 1500,
              showConfirmButton: false
            });
            this.carregarUsuarios();
          },
          error: (error: any) => {
            console.error('Erro ao alterar status:', error);
            Swal.fire({
              title: 'Erro!',
              text: `Erro ao ${acao} usuário`,
              icon: 'error'
            });
          }
        });
      }
    });
  }

  alterarSenha(usuario: Usuario) {
    Swal.fire({
      title: 'Alterar Senha',
      text: `Digite a nova senha para ${usuario.nome}:`,
      input: 'password',
      inputPlaceholder: 'Nova senha (mínimo 6 caracteres)',
      showCancelButton: true,
      confirmButtonText: 'Alterar',
      cancelButtonText: 'Cancelar',
      inputValidator: (value) => {
        if (!value || value.length < 6) {
          return 'A senha deve ter no mínimo 6 caracteres!';
        }
        return null;
      }
    }).then((result) => {
      if (result.isConfirmed && usuario.id) {
        this.usuarioService.alterarSenha(usuario.id, result.value).subscribe({
          next: () => {
            Swal.fire({
              title: 'Sucesso!',
              text: 'Senha alterada com sucesso!',
              icon: 'success',
              timer: 1500,
              showConfirmButton: false
            });
          },
          error: (error: any) => {
            console.error('Erro ao alterar senha:', error);
            Swal.fire({
              title: 'Erro!',
              text: 'Erro ao alterar senha',
              icon: 'error'
            });
          }
        });
      }
    });
  }
}
