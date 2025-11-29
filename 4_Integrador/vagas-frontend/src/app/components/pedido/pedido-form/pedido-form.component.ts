import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { PedidoService } from '../../../services/pedido.service';
import { ClienteService } from '../../../services/cliente.service';
import { ProdutoService } from '../../../services/produto.service';
import { Pedido, ItemPedido, StatusPedido, TipoPedido } from '../../../models/pedido';
import { Cliente } from '../../../models/cliente';
import { Produto } from '../../../models/produto';

@Component({
  selector: 'app-pedido-form',
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './pedido-form.component.html',
  styleUrl: './pedido-form.component.scss'
})
export class PedidoFormComponent implements OnInit {
  pedido: Pedido = {
    tipoPedido: TipoPedido.BALCAO,
    status: StatusPedido.PENDENTE,
    itens: []
  };
  
  clientes: Cliente[] = [];
  produtos: Produto[] = [];
  modoVisualizacao: boolean = false;
  isEdicao: boolean = false;
  
  StatusPedido = StatusPedido;
  TipoPedido = TipoPedido;
  
  // Para adicionar novo item
  novoItem: {produtoId?: number, quantidade: number, observacoes?: string} = {
    quantidade: 1
  };

  constructor(
    private pedidoService: PedidoService,
    private clienteService: ClienteService,
    private produtoService: ProdutoService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.carregarClientes();
    this.carregarProdutos();
    
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdicao = true;
      this.modoVisualizacao = true;
      this.carregarPedido(+id);
    }
  }

  carregarClientes(): void {
    this.clienteService.listar().subscribe({
      next: (data) => this.clientes = data,
      error: (error) => console.error('Erro ao carregar clientes:', error)
    });
  }

  carregarProdutos(): void {
    this.produtoService.listarAtivos().subscribe({
      next: (data) => this.produtos = data,
      error: (error) => console.error('Erro ao carregar produtos:', error)
    });
  }

  carregarPedido(id: number): void {
    this.pedidoService.buscarPorId(id).subscribe({
      next: (data) => {
        this.pedido = data;
      },
      error: (error) => {
        console.error('Erro ao carregar pedido:', error);
        alert('Erro ao carregar pedido');
        this.router.navigate(['/app/pedidos']);
      }
    });
  }

  salvar(): void {
    if (!this.pedido.itens || this.pedido.itens.length === 0) {
      alert('Adicione pelo menos um produto ao pedido');
      return;
    }

    console.log('📦 Dados do pedido a enviar:', JSON.stringify(this.pedido, null, 2));

    if (this.isEdicao && this.pedido.id) {
      this.pedidoService.atualizar(this.pedido.id, this.pedido).subscribe({
        next: () => {
          alert('Pedido atualizado com sucesso!');
          this.router.navigate(['/app/pedidos']);
        },
        error: (error) => {
          console.error('❌ Erro completo:', error);
          console.error('❌ Erro detalhes:', error.error);
          alert('Erro ao atualizar pedido: ' + (error.error?.message || error.message));
        }
      });
    } else {
      this.pedidoService.criar(this.pedido).subscribe({
        next: () => {
          alert('Pedido criado com sucesso!');
          this.router.navigate(['/app/pedidos']);
        },
        error: (error) => {
          console.error('❌ Erro completo:', error);
          console.error('❌ Erro detalhes:', error.error);
          console.error('❌ Status:', error.status);
          console.error('❌ Message:', error.message);
          alert('Erro ao criar pedido: ' + (error.error?.message || error.message));
        }
      });
    }
  }

  adicionarItem(): void {
    if (!this.novoItem.produtoId || this.novoItem.quantidade < 1) {
      alert('Selecione um produto e informe a quantidade');
      return;
    }

    const produto = this.produtos.find(p => p.id === this.novoItem.produtoId);
    if (!produto) return;

    const item: ItemPedido = {
      produto: produto,
      quantidade: this.novoItem.quantidade,
      precoUnitario: produto.preco || 0,
      observacoes: this.novoItem.observacoes
    };
    
    item.subtotal = item.precoUnitario * item.quantidade;

    if (!this.pedido.itens) {
      this.pedido.itens = [];
    }
    
    this.pedido.itens.push(item);
    this.calcularTotal();
    
    // Limpar formulário
    this.novoItem = { quantidade: 1 };
  }

  removerItem(index: number): void {
    if (confirm('Deseja remover este item?')) {
      this.pedido.itens?.splice(index, 1);
      this.calcularTotal();
    }
  }

  calcularTotal(): void {
    this.pedido.valorTotal = this.pedido.itens?.reduce(
      (total, item) => total + (item.subtotal || 0), 0
    ) || 0;
    
    this.pedido.valorFinal = this.pedido.valorTotal - (this.pedido.valorDesconto || 0);
  }

  voltar(): void {
    this.router.navigate(['/app/pedidos']);
  }

  formatarValor(valor: number | undefined): string {
    if (!valor) return 'R$ 0,00';
    return `R$ ${valor.toFixed(2).replace('.', ',')}`;
  }

  getStatusDescricao(status: string | undefined): string {
    const statusDescricoes: {[key: string]: string} = {
      'PENDENTE': 'Pendente',
      'CONFIRMADO': 'Confirmado',
      'PREPARANDO': 'Preparando',
      'PRONTO': 'Pronto',
      'ENTREGUE': 'Entregue',
      'CANCELADO': 'Cancelado'
    };
    return statusDescricoes[status || ''] || status || '';
  }

  getTipoPedidoDescricao(tipo: string | undefined): string {
    const tipoDescricoes: {[key: string]: string} = {
      'BALCAO': 'Balcão',
      'DELIVERY': 'Delivery',
      'RETIRADA': 'Retirada'
    };
    return tipoDescricoes[tipo || ''] || tipo || '';
  }

  formatarData(data: Date | string | undefined): string {
    if (!data) return '';
    const dataObj = typeof data === 'string' ? new Date(data) : data;
    return dataObj.toLocaleString('pt-BR');
  }

  compararClientes(c1: Cliente, c2: Cliente): boolean {
    return c1 && c2 ? c1.id === c2.id : c1 === c2;
  }
}

