import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { EnderecoService } from '../../../services/endereco.service';
import { Endereco } from '../../../models/endereco';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-endereco-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './endereco-list.component.html',
  styleUrl: './endereco-list.component.scss'
})
export class EnderecoListComponent implements OnInit {
  enderecos: Endereco[] = [];
  enderecosFiltrados: Endereco[] = [];
  filtroTexto: string = '';
  loading: boolean = false;

  constructor(
    private enderecoService: EnderecoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.carregarEnderecos();
  }

  carregarEnderecos(): void {
    this.loading = true;
    this.enderecoService.listar().subscribe({
      next: (enderecos: Endereco[]) => {
        this.enderecos = enderecos;
        this.enderecosFiltrados = enderecos;
        this.loading = false;
      },
      error: (erro: any) => {
        console.error('Erro ao carregar endereços:', erro);
        const mensagem = typeof erro.error === 'string' ? erro.error : 'Erro ao carregar endereços';
        Swal.fire('Erro!', mensagem, 'error');
        this.loading = false;
      }
    });
  }

  filtrarEnderecos(): void {
    const termo = this.filtroTexto.toLowerCase().trim();
    if (termo) {
      this.enderecosFiltrados = this.enderecos.filter(e => 
        e.logradouro.toLowerCase().includes(termo) ||
        e.bairro.toLowerCase().includes(termo) ||
        e.cidade.toLowerCase().includes(termo) ||
        (e.complemento && e.complemento.toLowerCase().includes(termo))
      );
    } else {
      this.enderecosFiltrados = this.enderecos;
    }
  }

  editarEndereco(id: number): void {
    this.router.navigate(['/app/enderecos/editar', id]);
  }

  novoEndereco(): void {
    this.router.navigate(['/app/enderecos/novo']);
  }

  excluirEndereco(endereco: Endereco): void {
    Swal.fire({
      title: 'Tem certeza?',
      text: `Deseja excluir o endereço ${endereco.logradouro}, ${endereco.numero}?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Sim, excluir!',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed && endereco.id) {
        this.enderecoService.deletar(endereco.id).subscribe({
          next: () => {
            Swal.fire('Excluído!', 'Endereço excluído com sucesso.', 'success');
            this.carregarEnderecos();
          },
          error: (erro: any) => {
            console.error('Erro ao excluir endereço:', erro);
            const mensagem = typeof erro.error === 'string' ? erro.error : 'Erro ao excluir endereço';
            Swal.fire('Erro!', mensagem, 'error');
          }
        });
      }
    });
  }
}
