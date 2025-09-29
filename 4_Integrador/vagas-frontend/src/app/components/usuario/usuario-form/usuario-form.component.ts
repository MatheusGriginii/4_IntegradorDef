import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';

import { Usuario } from '../../../models/usuario';
import { Endereco } from '../../../models/endereco';
import { UsuarioService } from '../../../services/usuario.service';

@Component({
  selector: 'app-usuario-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './usuario-form.component.html',
  styleUrl: './usuario-form.component.scss'
})
export class UsuarioFormComponent implements OnInit {
  
  usuario: Usuario = {
    nome: '',
    email: '',
    endereco: {
      cep: '',
      rua: '',
      numero: '',
      cidade: '',
      estado: '',
      uf: '',
      complemento: ''
    },
    vagas: []
  };

  isEdicao = false;
  salvando = false;
  usuarioId?: number;

  constructor(
    private usuarioService: UsuarioService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.usuarioId = Number(this.route.snapshot.paramMap.get('id'));
    
    if (this.usuarioId) {
      this.isEdicao = true;
      this.carregarUsuario();
    }
  }

  carregarUsuario(): void {
    if (!this.usuarioId) return;

    this.usuarioService.buscarPorId(this.usuarioId).subscribe({
      next: (usuario) => {
        this.usuario = { ...usuario };
        // Garantir que o endereço existe
        if (!this.usuario.endereco) {
          this.usuario.endereco = {
            cep: '',
            rua: '',
            numero: '',
            cidade: '',
            estado: '',
            uf: '',
            complemento: ''
          };
        }
      },
      error: (error: any) => {
        console.error('Erro ao carregar usuário:', error);
        Swal.fire({
          title: 'Erro!',
          text: 'Não foi possível carregar os dados do usuário.',
          icon: 'error',
          confirmButtonText: 'OK'
        }).then(() => {
          this.router.navigate(['/app/usuarios']);
        });
      }
    });
  }

  salvar(): void {
    if (this.salvando) return;

    this.salvando = true;

    const operacao = this.isEdicao 
      ? this.usuarioService.atualizar(this.usuarioId!, this.usuario)
      : this.usuarioService.criar(this.usuario);

    operacao.subscribe({
      next: (usuario) => {
        Swal.fire({
          title: 'Sucesso!',
          text: `Usuário ${this.isEdicao ? 'atualizado' : 'criado'} com sucesso!`,
          icon: 'success',
          confirmButtonText: 'OK'
        }).then(() => {
          this.router.navigate(['/app/usuarios']);
        });
      },
      error: (error: any) => {
        console.error('Erro ao salvar usuário:', error);
        this.salvando = false;
        Swal.fire({
          title: 'Erro!',
          text: `Não foi possível ${this.isEdicao ? 'atualizar' : 'criar'} o usuário.`,
          icon: 'error',
          confirmButtonText: 'OK'
        });
      }
    });
  }

  confirmarExclusao(): void {
    if (!this.usuarioId) return;

    Swal.fire({
      title: 'Confirmar Exclusão',
      text: `Deseja realmente excluir o usuário "${this.usuario.nome}"?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Sim, excluir!',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.excluirUsuario();
      }
    });
  }

  private excluirUsuario(): void {
    if (!this.usuarioId) return;

    this.usuarioService.deletar(this.usuarioId).subscribe({
      next: () => {
        Swal.fire({
          title: 'Excluído!',
          text: 'Usuário excluído com sucesso.',
          icon: 'success',
          confirmButtonText: 'OK'
        }).then(() => {
          this.router.navigate(['/app/usuarios']);
        });
      },
      error: (error: any) => {
        console.error('Erro ao excluir usuário:', error);
        Swal.fire({
          title: 'Erro!',
          text: 'Não foi possível excluir o usuário.',
          icon: 'error',
          confirmButtonText: 'OK'
        });
      }
    });
  }
}
