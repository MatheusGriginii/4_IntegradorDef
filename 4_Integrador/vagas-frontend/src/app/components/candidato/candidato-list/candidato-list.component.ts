import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CandidatoService } from '../../../services/candidato.service';
import { Candidato } from '../../../models/candidato';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-candidato-list',
  imports: [CommonModule, FormsModule],
  templateUrl: './candidato-list.component.html',
  styleUrl: './candidato-list.component.scss'
})
export class CandidatoListComponent implements OnInit {
  candidatos: Candidato[] = [];
  candidatosFiltrados: Candidato[] = [];
  filtroNome: string = '';
  filtroTituloVaga: string = '';
  loading: boolean = false;

  constructor(
    private candidatoService: CandidatoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.carregarCandidatos();
  }

  carregarCandidatos(): void {
    this.loading = true;
    this.candidatoService.listar().subscribe({
      next: (candidatos) => {
        this.candidatos = candidatos;
        this.candidatosFiltrados = candidatos;
        this.loading = false;
      },
      error: (erro) => {
        Swal.fire('Erro!', erro.error || 'Erro ao carregar candidatos', 'error');
        this.loading = false;
      }
    });
  }

  buscarPorNome(): void {
    if (this.filtroNome.trim()) {
      this.loading = true;
      this.candidatoService.buscarPorNome(this.filtroNome).subscribe({
        next: (candidatos) => {
          this.candidatosFiltrados = candidatos;
          this.loading = false;
        },
        error: (erro) => {
          Swal.fire('Erro!', erro.error || 'Erro ao buscar candidatos', 'error');
          this.loading = false;
        }
      });
    } else {
      this.candidatosFiltrados = this.candidatos;
    }
  }

  buscarPorTituloVaga(): void {
    if (this.filtroTituloVaga.trim()) {
      this.loading = true;
      this.candidatoService.buscarPorTituloVaga(this.filtroTituloVaga).subscribe({
        next: (candidatos) => {
          this.candidatosFiltrados = candidatos;
          this.loading = false;
        },
        error: (erro) => {
          Swal.fire('Erro!', erro.error || 'Erro ao buscar candidatos', 'error');
          this.loading = false;
        }
      });
    } else {
      this.candidatosFiltrados = this.candidatos;
    }
  }

  limparFiltros(): void {
    this.filtroNome = '';
    this.filtroTituloVaga = '';
    this.candidatosFiltrados = this.candidatos;
  }

  editarCandidato(id: number): void {
    this.router.navigate(['/app/candidatos/editar', id]);
  }

  novoCandidato(): void {
    this.router.navigate(['/app/candidatos/novo']);
  }

  excluirCandidato(candidato: Candidato): void {
    Swal.fire({
      title: 'Tem certeza?',
      text: `Deseja excluir o candidato ${candidato.nome}?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Sim, excluir!',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed && candidato.id) {
        this.candidatoService.deletar(candidato.id).subscribe({
          next: () => {
            Swal.fire('Excluído!', 'Candidato excluído com sucesso.', 'success');
            this.carregarCandidatos();
          },
          error: (erro) => {
            Swal.fire('Erro!', erro.error || 'Erro ao excluir candidato', 'error');
          }
        });
      }
    });
  }
}
