import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { CandidatoService } from '../../../services/candidato.service';
import { Candidato } from '../../../models/candidato';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-candidato-form',
  imports: [CommonModule, FormsModule],
  templateUrl: './candidato-form.component.html',
  styleUrl: './candidato-form.component.scss'
})
export class CandidatoFormComponent implements OnInit {
  candidato: Candidato = { 
    nome: '', 
    sobrenome: '',
    email: '', 
    telefone: '',
    observacoes: ''
  };
  isEdicao: boolean = false;
  loading: boolean = false;

  constructor(
    private candidatoService: CandidatoService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdicao = true;
      this.carregarCandidato(+id);
    }
  }

  carregarCandidato(id: number): void {
    this.loading = true;
    this.candidatoService.buscarPorId(id).subscribe({
      next: (candidato: any) => {
        this.candidato = candidato;
        this.loading = false;
      },
      error: (erro: any) => {
        Swal.fire('Erro!', erro.error || 'Erro ao carregar cliente', 'error');
        this.loading = false;
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
          `Cliente ${this.isEdicao ? 'atualizado' : 'cadastrado'} com sucesso!`,
          'success'
        );
        this.router.navigate(['/app/clientes']);
      },
      error: (erro) => {
        Swal.fire('Erro!', erro.error || 'Erro ao salvar cliente', 'error');
        this.loading = false;
      }
    });
  }

  cancelar(): void {
    this.router.navigate(['/app/clientes']);
  }
}
