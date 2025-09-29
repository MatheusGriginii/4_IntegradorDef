import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { CandidatoService } from '../../../services/candidato.service';
import { VagaService } from '../../../services/vaga.service';
import { Candidato } from '../../../models/candidato';
import { Vaga } from '../../../models/vaga';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-candidato-form',
  imports: [CommonModule, FormsModule],
  templateUrl: './candidato-form.component.html',
  styleUrl: './candidato-form.component.scss'
})
export class CandidatoFormComponent implements OnInit {
  candidato: Candidato = { nome: '' };
  vagas: Vaga[] = [];
  vagasSelecionadas: number[] = [];
  isEdicao: boolean = false;
  loading: boolean = false;
  showVagasModal: boolean = false;

  constructor(
    private candidatoService: CandidatoService,
    private vagaService: VagaService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdicao = true;
      this.carregarCandidato(+id);
    }
    this.carregarVagas();
  }

  carregarCandidato(id: number): void {
    this.loading = true;
    this.candidatoService.buscarPorId(id).subscribe({
      next: (candidato) => {
        this.candidato = candidato;
        this.vagasSelecionadas = candidato.vagas?.map(v => v.id) || [];
        this.loading = false;
      },
      error: (erro) => {
        Swal.fire('Erro!', erro.error || 'Erro ao carregar candidato', 'error');
        this.loading = false;
      }
    });
  }

  carregarVagas(): void {
    this.vagaService.listar().subscribe({
      next: (vagas) => {
        this.vagas = vagas;
      },
      error: (erro) => {
        console.error('Erro ao carregar vagas:', erro);
      }
    });
  }

  salvar(): void {
    if (!this.candidato.nome?.trim()) {
      Swal.fire('Atenção!', 'Nome é obrigatório', 'warning');
      return;
    }

    this.loading = true;
    const operacao = this.isEdicao 
      ? this.candidatoService.atualizar(this.candidato.id!, this.candidato)
      : this.candidatoService.criar(this.candidato);

    operacao.subscribe({
      next: () => {
        Swal.fire(
          'Sucesso!',
          `Candidato ${this.isEdicao ? 'atualizado' : 'criado'} com sucesso!`,
          'success'
        );
        this.router.navigate(['/app/candidatos']);
      },
      error: (erro) => {
        Swal.fire('Erro!', erro.error || 'Erro ao salvar candidato', 'error');
        this.loading = false;
      }
    });
  }

  cancelar(): void {
    this.router.navigate(['/app/candidatos']);
  }

  abrirModalVagas(): void {
    this.showVagasModal = true;
  }

  fecharModalVagas(): void {
    this.showVagasModal = false;
  }

  toggleVaga(vagaId: number): void {
    const index = this.vagasSelecionadas.indexOf(vagaId);
    if (index > -1) {
      this.vagasSelecionadas.splice(index, 1);
    } else {
      this.vagasSelecionadas.push(vagaId);
    }
  }

  isVagaSelecionada(vagaId: number): boolean {
    return this.vagasSelecionadas.includes(vagaId);
  }
}
