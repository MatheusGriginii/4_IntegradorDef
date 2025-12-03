import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { ProdutoService } from '../../../services/produto.service';
import { CategoriaService } from '../../../services/categoria.service';
import { Produto } from '../../../models/produto';
import { Categoria } from '../../../models/empresa';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-produto-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './produto-list.component.html',
  styleUrl: './produto-list.component.scss'
})
export class ProdutoListComponent implements OnInit {
  produtos: Produto[] = [];
  produtosFiltrados: Produto[] = [];
  categorias: Categoria[] = [];
  filtroNome: string = '';
  filtroCategoria: string = '';
  mostrarDisponiveis: boolean = false;
  loading: boolean = false;

  constructor(
    private produtoService: ProdutoService,
    private categoriaService: CategoriaService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.carregarProdutos();
    this.carregarCategorias();
  }

  carregarProdutos(): void {
    this.loading = true;
    this.produtoService.listar().subscribe({
      next: (produtos: Produto[]) => {
        this.produtos = produtos;
        this.produtosFiltrados = produtos;
        this.loading = false;
      },
      error: (erro: any) => {
        console.error('Erro ao carregar produtos:', erro);
        let mensagem = 'Erro ao carregar produtos';
        
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
        this.categorias = categorias;
      },
      error: (erro: any) => {
        console.error('Erro ao carregar categorias', erro);
      }
    });
  }

  buscarPorNome(): void {
    if (this.filtroNome.trim()) {
      this.loading = true;
      this.produtoService.buscarPorNome(this.filtroNome).subscribe({
        next: (produtos: Produto[]) => {
          this.produtosFiltrados = produtos;
          this.loading = false;
        },
        error: (erro: any) => {
          Swal.fire('Erro!', erro.error || 'Erro ao buscar produtos', 'error');
          this.loading = false;
        }
      });
    } else {
      this.produtosFiltrados = this.produtos;
    }
  }

  buscarPorCategoria(): void {
    if (this.filtroCategoria.trim()) {
      this.loading = true;
      // Converte o nome da categoria para ID (assumindo que filtroCategoria é um número em string)
      const categoriaId = parseInt(this.filtroCategoria);
      if (!isNaN(categoriaId)) {
        this.produtoService.listarPorCategoria(categoriaId).subscribe({
          next: (produtos: Produto[]) => {
            this.produtosFiltrados = produtos;
            this.loading = false;
          },
          error: (erro: any) => {
            Swal.fire('Erro!', erro.error || 'Erro ao buscar produtos', 'error');
            this.loading = false;
          }
        });
      } else {
        this.loading = false;
        this.produtosFiltrados = this.produtos;
      }
    } else {
      this.produtosFiltrados = this.produtos;
    }
  }

  filtrarDisponiveis(): void {
    if (this.mostrarDisponiveis) {
      this.loading = true;
      this.produtoService.listarAtivos().subscribe({
        next: (produtos: Produto[]) => {
          this.produtosFiltrados = produtos;
          this.loading = false;
        },
        error: (erro: any) => {
          Swal.fire('Erro!', erro.error || 'Erro ao buscar produtos', 'error');
          this.loading = false;
        }
      });
    } else {
      this.produtosFiltrados = this.produtos;
    }
  }

  // Métodos auxiliares para o template
  contarDisponiveis(): number {
    return this.produtosFiltrados.filter(p => p.ativo === true).length;
  }

  contarEstoqueBaixo(): number {
    return this.produtosFiltrados.filter(p => (p.estoque || 0) < 10).length;
  }

  limparFiltros(): void {
    this.filtroNome = '';
    this.filtroCategoria = '';
    this.mostrarDisponiveis = false;
    this.produtosFiltrados = this.produtos;
  }

  editarProduto(id: number): void {
    this.router.navigate(['/app/produtos/editar', id]);
  }

  novoProduto(): void {
    this.router.navigate(['/app/produtos/novo']);
  }

  excluirProduto(produto: Produto): void {
    Swal.fire({
      title: 'Tem certeza?',
      text: `Deseja excluir o produto ${produto.nome}?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Sim, excluir!',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed && produto.id) {
        this.produtoService.deletar(produto.id).subscribe({
          next: () => {
            Swal.fire('Excluído!', 'Produto excluído com sucesso.', 'success');
            this.carregarProdutos();
          },
          error: (erro: any) => {
            Swal.fire('Erro!', erro.error || 'Erro ao excluir produto', 'error');
          }
        });
      }
    });
  }
}
