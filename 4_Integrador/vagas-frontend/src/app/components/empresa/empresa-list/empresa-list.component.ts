import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { CategoriaService } from '../../../services/empresa.service';
import { Categoria } from '../../../models/empresa';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-categoria-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './empresa-list.component.html',
  styleUrl: './empresa-list.component.scss'
})
export class CategoriaListComponent implements OnInit {
  categorias: Categoria[] = [];
  categoriasFiltradas: Categoria[] = [];
  filtroNome: string = '';
  loading: boolean = false;

  constructor(
    private categoriaService: CategoriaService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.carregarCategorias();
  }

  carregarCategorias(): void {
    this.loading = true;
    this.categoriaService.listar().subscribe({
      next: (categorias: Categoria[]) => {
        this.categorias = categorias;
        this.categoriasFiltradas = categorias;
        this.loading = false;
      },
      error: (erro: any) => {
        console.error('Erro ao carregar categorias:', erro);
        let mensagem = 'Erro ao carregar categorias';
        
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

  buscarPorNome(): void {
    if (this.filtroNome.trim()) {
      this.loading = true;
      this.categoriaService.buscarPorNomeContendo(this.filtroNome).subscribe({
        next: (categorias: Categoria[]) => {
          this.categoriasFiltradas = categorias;
          this.loading = false;
        },
        error: (erro: any) => {
          console.error('Erro ao buscar categorias:', erro);
          let mensagem = 'Erro ao buscar categorias';
          
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
    } else {
      this.categoriasFiltradas = this.categorias;
    }
  }

  editarCategoria(id: number): void {
    this.router.navigate(['/app/categorias/editar', id]);
  }

  novaCategoria(): void {
    this.router.navigate(['/app/categorias/novo']);
  }

  excluirCategoria(categoria: Categoria): void {
    Swal.fire({
      title: 'Tem certeza?',
      text: `Deseja excluir a categoria ${categoria.nome}?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Sim, excluir!',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed && categoria.id) {
        this.categoriaService.deletar(categoria.id).subscribe({
          next: () => {
            Swal.fire('Excluído!', 'Categoria excluída com sucesso.', 'success');
            this.carregarCategorias();
          },
          error: (erro: any) => {
            console.error('Erro ao excluir categoria:', erro);
            let mensagem = 'Erro ao excluir categoria';
            
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
          }
        });
      }
    });
  }
}
