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
    cidade: ''
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
      const cidadeMatch = !this.filtros.cidade || 
        usuario.endereco?.cidade?.toLowerCase().includes(this.filtros.cidade.toLowerCase());
      
      return nomeMatch && cidadeMatch;
    });
  }

  limparFiltros() {
    this.filtros.nome = '';
    this.filtros.cidade = '';
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

  gerenciarVagas(usuario: Usuario) {
    // TODO: Implementar modal para gerenciar vagas do usuário
    Swal.fire({
      title: 'Em desenvolvimento',
      text: 'Funcionalidade de gerenciar vagas será implementada em breve',
      icon: 'info'
    });
  }
}
