import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { PedidoService } from '../../../services/pedido.service';
import { Pedido, StatusPedido } from '../../../models/pedido';

@Component({
  selector: 'app-pedido-list',
  imports: [CommonModule, RouterModule],
  templateUrl: './pedido-list.component.html',
  styleUrl: './pedido-list.component.scss'
})
export class PedidoListComponent implements OnInit {
  pedidos: Pedido[] = [];
  pedidosFiltrados: Pedido[] = [];
  filtroStatus: string = 'TODOS';
  carregando: boolean = false;
  
  StatusPedido = StatusPedido;

  constructor(private pedidoService: PedidoService) {}

  ngOnInit(): void {
    this.carregarPedidos();
  }

  carregarPedidos(): void {
    this.carregando = true;
    this.pedidoService.listar().subscribe({
      next: (data) => {
        this.pedidos = data;
        this.aplicarFiltro();
        this.carregando = false;
      },
      error: (error) => {
        console.error('Erro ao carregar pedidos:', error);
        this.carregando = false;
      }
    });
  }

  aplicarFiltro(): void {
    if (this.filtroStatus === 'TODOS') {
      this.pedidosFiltrados = this.pedidos;
    } else {
      this.pedidosFiltrados = this.pedidos.filter(p => p.status === this.filtroStatus);
    }
  }

  mudarFiltro(status: string): void {
    this.filtroStatus = status;
    this.aplicarFiltro();
  }

  confirmarPedido(id: number | undefined): void {
    if (!id) return;
    
    if (confirm('Deseja confirmar este pedido?')) {
      this.pedidoService.confirmar(id).subscribe({
        next: () => {
          this.carregarPedidos();
        },
        error: (error) => {
          console.error('Erro ao confirmar pedido:', error);
          alert('Erro ao confirmar pedido');
        }
      });
    }
  }

  cancelarPedido(id: number | undefined): void {
    if (!id) return;
    
    if (confirm('Deseja cancelar este pedido? Esta ação não pode ser desfeita.')) {
      this.pedidoService.cancelar(id).subscribe({
        next: () => {
          this.carregarPedidos();
        },
        error: (error) => {
          console.error('Erro ao cancelar pedido:', error);
          alert('Erro ao cancelar pedido');
        }
      });
    }
  }

  atualizarStatus(id: number | undefined, novoStatus: StatusPedido): void {
    if (!id) return;
    
    this.pedidoService.atualizarStatus(id, novoStatus).subscribe({
      next: () => {
        this.carregarPedidos();
      },
      error: (error) => {
        console.error('Erro ao atualizar status:', error);
        alert('Erro ao atualizar status do pedido');
      }
    });
  }

  marcarComoEntregue(id: number | undefined): void {
    if (!id) return;
    
    if (confirm('Confirmar entrega do pedido?')) {
      this.pedidoService.marcarComoEntregue(id).subscribe({
        next: () => {
          this.carregarPedidos();
        },
        error: (error) => {
          console.error('Erro ao marcar como entregue:', error);
          alert('Erro ao marcar pedido como entregue');
        }
      });
    }
  }

  getStatusClass(status: string | undefined): string {
    const statusClasses: {[key: string]: string} = {
      'PENDENTE': 'badge bg-warning text-dark',
      'CONFIRMADO': 'badge bg-info',
      'PREPARANDO': 'badge bg-primary',
      'PRONTO': 'badge bg-success',
      'ENTREGUE': 'badge bg-secondary',
      'CANCELADO': 'badge bg-danger'
    };
    return statusClasses[status || ''] || 'badge bg-secondary';
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

  formatarValor(valor: number | undefined): string {
    if (!valor) return 'R$ 0,00';
    return `R$ ${valor.toFixed(2).replace('.', ',')}`;
  }
}

