import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { VagaService } from '../../../services/vaga.service';
import { EmpresaService } from '../../../services/empresa.service';
import { CandidatoService } from '../../../services/candidato.service';
import { Vaga } from '../../../models/vaga';
import { Empresa } from '../../../models/empresa';
import { Candidato } from '../../../models/candidato';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-vaga-form',
  imports: [CommonModule, FormsModule],
  templateUrl: './vaga-form.component.html',
  styleUrl: './vaga-form.component.scss'
})
export class VagaFormComponent implements OnInit {
  vaga: Vaga = { titulo: '' };
  empresas: Empresa[] = [];
  candidatos: Candidato[] = [];
  candidatosSelecionados: number[] = [];
  isEdicao: boolean = false;
  loading: boolean = false;
  showCandidatosModal: boolean = false;

  constructor(
    private vagaService: VagaService,
    private empresaService: EmpresaService,
    private candidatoService: CandidatoService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdicao = true;
      this.carregarVaga(+id);
    }
    this.carregarEmpresas();
    this.carregarCandidatos();
  }

  carregarVaga(id: number): void {
    this.loading = true;
    this.vagaService.buscarPorId(id).subscribe({
      next: (vaga) => {
        this.vaga = vaga;
        this.candidatosSelecionados = vaga.candidatos?.map(c => c.id) || [];
        this.loading = false;
      },
      error: (erro) => {
        Swal.fire('Erro!', erro.error || 'Erro ao carregar vaga', 'error');
        this.loading = false;
      }
    });
  }

  carregarEmpresas(): void {
    this.empresaService.listar().subscribe({
      next: (empresas) => {
        this.empresas = empresas;
      },
      error: (erro) => {
        console.error('Erro ao carregar empresas:', erro);
      }
    });
  }

  carregarCandidatos(): void {
    this.candidatoService.listar().subscribe({
      next: (candidatos) => {
        this.candidatos = candidatos;
      },
      error: (erro) => {
        console.error('Erro ao carregar candidatos:', erro);
      }
    });
  }

  salvar(): void {
    if (!this.vaga.titulo?.trim()) {
      Swal.fire('Atenção!', 'Título é obrigatório', 'warning');
      return;
    }

    if (!this.vaga.empresa?.id) {
      Swal.fire('Atenção!', 'Empresa é obrigatória', 'warning');
      return;
    }

    this.loading = true;
    const operacao = this.isEdicao 
      ? this.vagaService.atualizar(this.vaga.id!, this.vaga)
      : this.vagaService.criar(this.vaga);

    operacao.subscribe({
      next: () => {
        Swal.fire(
          'Sucesso!',
          `Vaga ${this.isEdicao ? 'atualizada' : 'criada'} com sucesso!`,
          'success'
        );
        this.router.navigate(['/app/vagas']);
      },
      error: (erro) => {
        Swal.fire('Erro!', erro.error || 'Erro ao salvar vaga', 'error');
        this.loading = false;
      }
    });
  }

  cancelar(): void {
    this.router.navigate(['/app/vagas']);
  }

  abrirModalCandidatos(): void {
    this.showCandidatosModal = true;
  }

  fecharModalCandidatos(): void {
    this.showCandidatosModal = false;
  }

  toggleCandidato(candidatoId: number): void {
    const index = this.candidatosSelecionados.indexOf(candidatoId);
    if (index > -1) {
      this.candidatosSelecionados.splice(index, 1);
    } else {
      this.candidatosSelecionados.push(candidatoId);
    }
  }

  isCandidatoSelecionado(candidatoId: number): boolean {
    return this.candidatosSelecionados.includes(candidatoId);
  }

  onEmpresaChange(empresaId: string): void {
    if (empresaId) {
      const empresa = this.empresas.find(e => e.id === +empresaId);
      this.vaga.empresa = empresa;
    } else {
      this.vaga.empresa = undefined;
    }
  }
}
