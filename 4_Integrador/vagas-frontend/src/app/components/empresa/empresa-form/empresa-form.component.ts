import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { CategoriaService } from '../../../services/empresa.service';
import { Categoria } from '../../../models/empresa';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-empresa-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './empresa-form.component.html',
  styleUrl: './empresa-form.component.scss'
})
export class EmpresaFormComponent implements OnInit {
  categoria: Categoria = {
    nome: '',
    descricao: '',
    cor: '#6c757d',
    icone: 'bi-tag',
    ativa: true
  };
  
  modoEdicao: boolean = false;
  loading: boolean = false;

  constructor(
    private categoriaService: CategoriaService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.modoEdicao = true;
      this.carregarCategoria(Number(id));
    }
  }

  carregarCategoria(id: number): void {
    this.loading = true;
    this.categoriaService.buscarPorId(id).subscribe({
      next: (categoria: Categoria) => {
        this.categoria = categoria;
        this.loading = false;
      },
      error: (erro: any) => {
        console.error('Erro ao carregar categoria:', erro);
        let mensagem = 'Erro ao carregar categoria';
        
        if (erro.error) {
          if (typeof erro.error === 'string') {
            mensagem = erro.error;
          } else if (erro.error.mensagem) {
            mensagem = erro.error.mensagem;
          }
        }
        
        Swal.fire('Erro!', mensagem, 'error');
        this.loading = false;
        this.voltar();
      }
    });
  }

  salvar(): void {
    // Validações
    if (!this.categoria.nome || this.categoria.nome.trim() === '') {
      Swal.fire('Atenção!', 'Nome da categoria é obrigatório', 'warning');
      return;
    }
    
    if (this.categoria.nome.trim().length < 2) {
      Swal.fire('Atenção!', 'Nome deve ter pelo menos 2 caracteres', 'warning');
      return;
    }
    
    if (this.categoria.nome.length > 50) {
      Swal.fire('Atenção!', 'Nome deve ter no máximo 50 caracteres', 'warning');
      return;
    }

    this.loading = true;
    
    const operacao = this.modoEdicao
      ? this.categoriaService.atualizar(this.categoria.id!, this.categoria)
      : this.categoriaService.criar(this.categoria);

    operacao.subscribe({
      next: (categoria: Categoria) => {
        Swal.fire(
          'Sucesso!',
          `Categoria ${this.modoEdicao ? 'atualizada' : 'cadastrada'} com sucesso`,
          'success'
        );
        this.loading = false;
        this.voltar();
      },
      error: (erro: any) => {
        console.error('Erro ao salvar categoria:', erro);
        let mensagem = 'Erro ao salvar categoria';
        
        if (erro.error) {
          // Erro com validação de campos
          if (erro.error.fields) {
            const campos = Object.keys(erro.error.fields);
            const erros = campos.map(campo => `${campo}: ${erro.error.fields[campo]}`).join('\n');
            mensagem = erros;
          }
          // Erro com mensagem simples
          else if (erro.error.mensagem) {
            mensagem = erro.error.mensagem;
          }
          // Erro como string
          else if (typeof erro.error === 'string') {
            mensagem = erro.error;
          }
        }
        
        Swal.fire({
          icon: 'error',
          title: 'Erro ao salvar!',
          html: mensagem.replace(/\n/g, '<br>'),
          confirmButtonText: 'OK'
        });
        this.loading = false;
      }
    });
  }

  voltar(): void {
    this.router.navigate(['/app/empresas']);
  }
}

