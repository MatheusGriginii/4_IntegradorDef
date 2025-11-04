import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { ProdutoService } from '../../../services/vaga.service';
import { CategoriaService } from '../../../services/empresa.service';
import { Produto } from '../../../models/vaga';
import { Categoria } from '../../../models/empresa';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-produto-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './vaga-list.component.html',
  styleUrl: './vaga-list.component.scss'
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
      next: (produtos) => {
        this.produtos = produtos;
        this.produtosFiltrados = produtos;
        this.loading = false;
      },
      error: (erro) => {
        Swal.fire('Erro!', erro.error || 'Erro ao carregar produtos', 'error');
        this.loading = false;
      }
    });
  }

  carregarCategorias(): void {
    this.categoriaService.listar().subscribe({
      next: (categorias) => {
        this.categorias = categorias;
      },
      error: (erro) => {
        console.error('Erro ao carregar categorias', erro);
      }
    });
  }

  buscarPorNome(): void {
    if (this.filtroNome.trim()) {
      this.loading = true;
      this.produtoService.buscarPorNome(this.filtroNome).subscribe({
        next: (produtos) => {
          this.produtosFiltrados = produtos;
          this.loading = false;
        },
        error: (erro) => {
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
      this.produtoService.buscarPorCategoria(this.filtroCategoria).subscribe({
        next: (produtos) => {
          this.produtosFiltrados = produtos;
          this.loading = false;
        },
        error: (erro) => {
          Swal.fire('Erro!', erro.error || 'Erro ao buscar produtos', 'error');
          this.loading = false;
        }
      });
    } else {
      this.produtosFiltrados = this.produtos;
    }
  }

  filtrarDisponiveis(): void {
    if (this.mostrarDisponiveis) {
      this.loading = true;
      this.produtoService.buscarDisponiveis().subscribe({
        next: (produtos) => {
          this.produtosFiltrados = produtos;
          this.loading = false;
        },
        error: (erro) => {
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
    return this.produtosFiltrados.filter(p => p.disponivel === true).length;
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
          error: (erro) => {
            Swal.fire('Erro!', erro.error || 'Erro ao excluir produto', 'error');
          }
        });
      }
    });
  }
}
