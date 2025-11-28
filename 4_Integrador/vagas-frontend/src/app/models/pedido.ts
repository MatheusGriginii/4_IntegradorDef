import { Cliente } from './cliente';
import { Produto } from './produto';

export enum StatusPedido {
  PENDENTE = 'PENDENTE',
  CONFIRMADO = 'CONFIRMADO',
  PREPARANDO = 'PREPARANDO',
  PRONTO = 'PRONTO',
  ENTREGUE = 'ENTREGUE',
  CANCELADO = 'CANCELADO'
}

export enum TipoPedido {
  BALCAO = 'BALCAO',
  DELIVERY = 'DELIVERY',
  RETIRADA = 'RETIRADA'
}

export interface Pedido {
  id?: number;
  cliente?: Cliente;
  itens?: ItemPedido[];
  status?: StatusPedido;
  tipoPedido?: TipoPedido;
  valorTotal?: number;
  valorDesconto?: number;
  valorFinal?: number;
  observacoes?: string;
  dataPedido?: Date | string;
  dataEntregaPrevista?: Date | string;
  dataEntregaRealizada?: Date | string;
  formaPagamento?: string;
  enderecoEntrega?: string;
  telefoneContato?: string;
}

export interface ItemPedido {
  id?: number;
  pedido?: Pedido;
  produto?: Produto;
  quantidade: number;
  precoUnitario: number;
  subtotal?: number;
  observacoes?: string;
}

