import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { VagaService } from '../../../services/vaga.service';
import { Vaga } from '../../../models/vaga';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-vaga-list',
  imports: [CommonModule, FormsModule],
  templateUrl: './vaga-list.component.html',
  styleUrl: './vaga-list.component.scss'
})
export class VagaListComponent implements OnInit {
  vagas: Vaga[] = [];
  vagasFiltradas: Vaga[] = [];
  filtroTitulo: string = '';
  filtroEmpresa: string = '';
  loading: boolean = false;

  constructor(
    private vagaService: VagaService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.carregarVagas();
  }

  carregarVagas(): void {
    this.loading = true;
    this.vagaService.listar().subscribe({
      next: (vagas) => {
        this.vagas = vagas;
        this.vagasFiltradas = vagas;
        this.loading = false;
      },
      error: (erro) => {
        Swal.fire('Erro!', erro.error || 'Erro ao carregar vagas', 'error');
        this.loading = false;
      }
    });
  }

  buscarPorTitulo(): void {
    if (this.filtroTitulo.trim()) {
      this.loading = true;
      this.vagaService.buscarPorTitulo(this.filtroTitulo).subscribe({
        next: (vagas) => {
          this.vagasFiltradas = vagas;
          this.loading = false;
        },
        error: (erro) => {
          Swal.fire('Erro!', erro.error || 'Erro ao buscar vagas', 'error');
          this.loading = false;
        }
      });
    } else {
      this.vagasFiltradas = this.vagas;
    }
  }

  buscarPorEmpresa(): void {
    if (this.filtroEmpresa.trim()) {
      this.loading = true;
      this.vagaService.buscarPorEmpresa(this.filtroEmpresa).subscribe({
        next: (vagas) => {
          this.vagasFiltradas = vagas;
          this.loading = false;
        },
        error: (erro) => {
          Swal.fire('Erro!', erro.error || 'Erro ao buscar vagas', 'error');
          this.loading = false;
        }
      });
    } else {
      this.vagasFiltradas = this.vagas;
    }
  }

  limparFiltros(): void {
    this.filtroTitulo = '';
    this.filtroEmpresa = '';
    this.vagasFiltradas = this.vagas;
  }

  editarVaga(id: number): void {
    this.router.navigate(['/app/vagas/editar', id]);
  }

  novaVaga(): void {
    this.router.navigate(['/app/vagas/novo']);
  }

  excluirVaga(vaga: Vaga): void {
    Swal.fire({
      title: 'Tem certeza?',
      text: `Deseja excluir a vaga ${vaga.titulo}?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Sim, excluir!',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed && vaga.id) {
        this.vagaService.deletar(vaga.id).subscribe({
          next: () => {
            Swal.fire('Excluído!', 'Vaga excluída com sucesso.', 'success');
            this.carregarVagas();
          },
          error: (erro) => {
            Swal.fire('Erro!', erro.error || 'Erro ao excluir vaga', 'error');
          }
        });
      }
    });
  }
}
