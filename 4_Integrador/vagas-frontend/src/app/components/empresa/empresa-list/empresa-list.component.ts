import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { EmpresaService } from '../../../services/empresa.service';
import { Empresa } from '../../../models/empresa';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-empresa-list',
  imports: [CommonModule, FormsModule],
  templateUrl: './empresa-list.component.html',
  styleUrl: './empresa-list.component.scss'
})
export class EmpresaListComponent implements OnInit {
  empresas: Empresa[] = [];
  empresasFiltradas: Empresa[] = [];
  filtroNome: string = '';
  loading: boolean = false;

  constructor(
    private empresaService: EmpresaService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.carregarEmpresas();
  }

  carregarEmpresas(): void {
    this.loading = true;
    this.empresaService.listar().subscribe({
      next: (empresas) => {
        this.empresas = empresas;
        this.empresasFiltradas = empresas;
        this.loading = false;
      },
      error: (erro) => {
        Swal.fire('Erro!', erro.error || 'Erro ao carregar empresas', 'error');
        this.loading = false;
      }
    });
  }

  buscarPorNome(): void {
    if (this.filtroNome.trim()) {
      this.loading = true;
      this.empresaService.buscarPorNome(this.filtroNome).subscribe({
        next: (empresas) => {
          this.empresasFiltradas = empresas;
          this.loading = false;
        },
        error: (erro) => {
          Swal.fire('Erro!', erro.error || 'Erro ao buscar empresas', 'error');
          this.loading = false;
        }
      });
    } else {
      this.empresasFiltradas = this.empresas;
    }
  }

  editarEmpresa(id: number): void {
    this.router.navigate(['/app/empresas/editar', id]);
  }

  novaEmpresa(): void {
    this.router.navigate(['/app/empresas/novo']);
  }

  excluirEmpresa(empresa: Empresa): void {
    Swal.fire({
      title: 'Tem certeza?',
      text: `Deseja excluir a empresa ${empresa.nome}?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Sim, excluir!',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed && empresa.id) {
        this.empresaService.deletar(empresa.id).subscribe({
          next: () => {
            Swal.fire('Excluído!', 'Empresa excluída com sucesso.', 'success');
            this.carregarEmpresas();
          },
          error: (erro) => {
            Swal.fire('Erro!', erro.error || 'Erro ao excluir empresa', 'error');
          }
        });
      }
    });
  }
}
