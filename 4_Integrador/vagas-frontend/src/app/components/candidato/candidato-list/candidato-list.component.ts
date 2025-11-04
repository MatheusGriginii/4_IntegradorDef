import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { ClienteService } from '../../../services/candidato.service';
import { Cliente } from '../../../models/candidato';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-cliente-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './candidato-list.component.html',
  styleUrl: './candidato-list.component.scss'
})
export class ClienteListComponent implements OnInit {
  clientes: Cliente[] = [];
  clientesFiltrados: Cliente[] = [];
  filtroNome: string = '';
  filtroEmail: string = '';
  loading: boolean = false;

  constructor(
    private clienteService: ClienteService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.carregarClientes();
  }

  carregarClientes(): void {
    this.loading = true;
    this.clienteService.listar().subscribe({
      next: (clientes) => {
        this.clientes = clientes;
        this.clientesFiltrados = clientes;
        this.loading = false;
      },
      error: (erro) => {
        Swal.fire('Erro!', erro.error || 'Erro ao carregar clientes', 'error');
        this.loading = false;
      }
    });
  }

  buscarPorNome(): void {
    if (this.filtroNome.trim()) {
      this.loading = true;
      this.clienteService.buscarPorNome(this.filtroNome).subscribe({
        next: (clientes) => {
          this.clientesFiltrados = clientes;
          this.loading = false;
        },
        error: (erro) => {
          Swal.fire('Erro!', erro.error || 'Erro ao buscar clientes', 'error');
          this.loading = false;
        }
      });
    } else {
      this.clientesFiltrados = this.clientes;
    }
  }

  buscarPorEmail(): void {
    if (this.filtroEmail.trim()) {
      this.loading = true;
      this.clienteService.buscarPorEmail(this.filtroEmail).subscribe({
        next: (cliente) => {
          this.clientesFiltrados = cliente ? [cliente] : [];
          this.loading = false;
        },
        error: (erro) => {
          Swal.fire('Erro!', erro.error || 'Erro ao buscar cliente', 'error');
          this.loading = false;
        }
      });
    } else {
      this.clientesFiltrados = this.clientes;
    }
  }

  limparFiltros(): void {
    this.filtroNome = '';
    this.filtroEmail = '';
    this.clientesFiltrados = this.clientes;
  }

  editarCliente(id: number): void {
    this.router.navigate(['/app/clientes/editar', id]);
  }

  novoCliente(): void {
    this.router.navigate(['/app/clientes/novo']);
  }

  excluirCliente(cliente: Cliente): void {
    Swal.fire({
      title: 'Tem certeza?',
      text: `Deseja excluir o cliente ${cliente.nome}?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Sim, excluir!',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed && cliente.id) {
        this.clienteService.deletar(cliente.id).subscribe({
          next: () => {
            Swal.fire('Excluído!', 'Cliente excluído com sucesso.', 'success');
            this.carregarClientes();
          },
          error: (erro) => {
            Swal.fire('Erro!', erro.error || 'Erro ao excluir cliente', 'error');
          }
        });
      }
    });
  }
}
