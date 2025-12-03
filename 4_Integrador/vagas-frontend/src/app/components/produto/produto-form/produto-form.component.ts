import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { ProdutoService } from '../../../services/produto.service';
import { CategoriaService } from '../../../services/categoria.service';
import { Produto } from '../../../models/produto';
import { Categoria } from '../../../models/empresa';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-produto-form',
  imports: [CommonModule, FormsModule],
  templateUrl: './produto-form.component.html',
  styleUrl: './produto-form.component.scss'
})
export class ProdutoFormComponent implements OnInit {
  produto: Produto = { nome: '', ativo: true };
  categorias: Categoria[] = [];
  isEdicao: boolean = false;
  loading: boolean = false;

  constructor(
    private produtoService: ProdutoService,
    private categoriaService: CategoriaService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdicao = true;
      this.carregarProduto(+id);
    }
    this.carregarCategorias();
  }

  carregarProduto(id: number): void {
    this.loading = true;
    this.produtoService.buscarPorId(id).subscribe({
      next: (produto: Produto) => {
        this.produto = produto;
        this.loading = false;
      },
      error: (erro: any) => {
        console.error('Erro ao carregar produto:', erro);
        let mensagem = 'Erro ao carregar produto';
        
        if (erro.error) {
          if (erro.error.error && typeof erro.error.error === 'string') {
            mensagem = erro.error.error;
          } else if (erro.error.mensagem) {
            mensagem = erro.error.mensagem;
          } else if (typeof erro.error === 'string') {
            mensagem = erro.error;
          }
        }
        
        Swal.fire('Erro!', mensagem, 'error');
        this.loading = false;
      }
    });
  }

  carregarCategorias(): void {
    this.categoriaService.listar().subscribe({
      next: (categorias: Categoria[]) => {
        this.categorias = categorias.filter((c: Categoria) => c.ativa);
      },
      error: (erro: any) => {
        console.error('Erro ao carregar categorias:', erro);
      }
    });
  }

  salvar(): void {
    if (!this.produto.nome?.trim()) {
      Swal.fire('Atenção!', 'Nome é obrigatório', 'warning');
      return;
    }

    if (!this.produto.categoria?.id) {
      Swal.fire('Atenção!', 'Categoria é obrigatória', 'warning');
      return;
    }

    if (this.produto.preco && this.produto.preco <= 0) {
      Swal.fire('Atenção!', 'Preço deve ser maior que zero', 'warning');
      return;
    }

    if (this.produto.estoque && this.produto.estoque < 0) {
      Swal.fire('Atenção!', 'Estoque não pode ser negativo', 'warning');
      return;
    }

    this.loading = true;
    const operacao = this.isEdicao 
      ? this.produtoService.atualizar(this.produto.id!, this.produto)
      : this.produtoService.criar(this.produto);

    operacao.subscribe({
      next: () => {
        Swal.fire(
          'Sucesso!',
          `Produto ${this.isEdicao ? 'atualizado' : 'criado'} com sucesso!`,
          'success'
        );
        this.router.navigate(['/app/produtos']);
      },
      error: (erro) => {
        console.error('Erro ao salvar produto:', erro);
        let mensagem = 'Erro ao salvar produto';
        
        if (erro.error) {
          if (erro.error.fields) {
            mensagem = 'Erros de validação:\n';
            for (const campo in erro.error.fields) {
              mensagem += `- ${campo}: ${erro.error.fields[campo]}\n`;
            }
          } else if (erro.error.error && typeof erro.error.error === 'string') {
            mensagem = erro.error.error;
          } else if (erro.error.mensagem) {
            mensagem = erro.error.mensagem;
          } else if (typeof erro.error === 'string') {
            mensagem = erro.error;
          }
        }
        
        Swal.fire('Erro!', mensagem, 'error');
        this.loading = false;
      }
    });
  }

  cancelar(): void {
    this.router.navigate(['/app/produtos']);
  }

  onCategoriaChange(categoriaId: string): void {
    if (categoriaId) {
      const categoria = this.categorias.find(c => c.id === +categoriaId);
      this.produto.categoria = categoria;
    } else {
      this.produto.categoria = undefined;
    }
  }

  voltar(): void {
    this.router.navigate(['/app/produtos']);
  }
}
