export interface Cliente {
  id?: number;
  nome: string;
  email: string;
  telefone: string;
  endereco?: any;
  dataCadastro?: Date;
  pedidos?: any[];
}

// Export para compatibilidade com código antigo
export type Candidato = Cliente;
