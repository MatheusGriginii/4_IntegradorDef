import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { EnderecoService } from '../../../services/endereco.service';
import { Endereco } from '../../../models/endereco';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-endereco-form',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './endereco-form.component.html',
  styleUrl: './endereco-form.component.scss'
})
export class EnderecoFormComponent implements OnInit {
  endereco: Endereco = {
    cep: '',
    logradouro: '',
    numero: '',
    complemento: '',
    bairro: '',
    cidade: '',
    estado: '',
    pontoReferencia: '',
    observacoes: '',
    areaEntrega: false,
    taxaEntrega: 0,
    ativo: true
  };
  
  modoEdicao: boolean = false;
  loading: boolean = false;
  buscandoCep: boolean = false;

  constructor(
    private enderecoService: EnderecoService,
    private router: Router,
    private route: ActivatedRoute,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.modoEdicao = true;
      this.carregarEndereco(Number(id));
    }
  }

  carregarEndereco(id: number): void {
    this.loading = true;
    this.enderecoService.buscarPorId(id).subscribe({
      next: (endereco: Endereco) => {
        this.endereco = endereco;
        this.loading = false;
      },
      error: (erro: any) => {
        console.error('Erro ao carregar endereço:', erro);
        const mensagem = typeof erro.error === 'string' ? erro.error : 'Erro ao carregar endereço';
        Swal.fire('Erro!', mensagem, 'error');
        this.loading = false;
        this.voltar();
      }
    });
  }

  buscarCep(): void {
    const cep = this.endereco.cep.replace(/\D/g, '');
    
    if (cep.length !== 8) {
      return;
    }

    this.buscandoCep = true;
    
    this.http.get<any>(`https://viacep.com.br/ws/${cep}/json/`).subscribe({
      next: (dados: any) => {
        if (dados.erro) {
          Swal.fire('Atenção!', 'CEP não encontrado', 'warning');
        } else {
          this.endereco.logradouro = dados.logradouro || '';
          this.endereco.bairro = dados.bairro || '';
          this.endereco.cidade = dados.localidade || '';
          this.endereco.estado = dados.uf || '';
          this.endereco.complemento = dados.complemento || '';
        }
        this.buscandoCep = false;
      },
      error: (erro: any) => {
        Swal.fire('Erro!', 'Erro ao buscar CEP', 'error');
        this.buscandoCep = false;
      }
    });
  }

  salvar(): void {
    if (!this.validarEndereco()) {
      return;
    }

    this.loading = true;
    
    const operacao = this.modoEdicao
      ? this.enderecoService.atualizar(this.endereco.id!, this.endereco)
      : this.enderecoService.criar(this.endereco);

    operacao.subscribe({
      next: (endereco: Endereco) => {
        Swal.fire(
          'Sucesso!',
          `Endereço ${this.modoEdicao ? 'atualizado' : 'cadastrado'} com sucesso`,
          'success'
        );
        this.loading = false;
        this.voltar();
      },
      error: (erro: any) => {
        console.error('Erro ao salvar endereço:', erro);
        const mensagem = typeof erro.error === 'string' ? erro.error : 'Erro ao salvar endereço';
        Swal.fire('Erro!', mensagem, 'error');
        this.loading = false;
      }
    });
  }

  validarEndereco(): boolean {
    if (!this.endereco.cep || this.endereco.cep.trim() === '') {
      Swal.fire('Atenção!', 'CEP é obrigatório', 'warning');
      return false;
    }
    
    if (!this.endereco.logradouro || this.endereco.logradouro.trim() === '') {
      Swal.fire('Atenção!', 'Logradouro é obrigatório', 'warning');
      return false;
    }
    
    if (!this.endereco.numero || this.endereco.numero.trim() === '') {
      Swal.fire('Atenção!', 'Número é obrigatório', 'warning');
      return false;
    }
    
    if (!this.endereco.bairro || this.endereco.bairro.trim() === '') {
      Swal.fire('Atenção!', 'Bairro é obrigatório', 'warning');
      return false;
    }
    
    if (!this.endereco.cidade || this.endereco.cidade.trim() === '') {
      Swal.fire('Atenção!', 'Cidade é obrigatória', 'warning');
      return false;
    }
    
    if (!this.endereco.estado || this.endereco.estado.trim() === '') {
      Swal.fire('Atenção!', 'Estado é obrigatório', 'warning');
      return false;
    }
    
    return true;
  }

  voltar(): void {
    this.router.navigate(['/app/enderecos']);
  }
}

