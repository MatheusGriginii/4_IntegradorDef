export interface Pedido {
  id?: number;
  cliente?: any;
  dataPedido?: Date;
  status?: string;
  valorTotal?: number;
  itens?: ItemPedido[];
}

export interface ItemPedido {
  id?: number;
  produto?: any;
  quantidade: number;
  precoUnitario: number;
  subtotal?: number;
}
