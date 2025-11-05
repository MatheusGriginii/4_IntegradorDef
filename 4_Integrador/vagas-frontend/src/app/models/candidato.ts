export interface Cliente {
  id?: number;
  nome: string;
  sobrenome?: string;
  email?: string;
  telefone?: string;
  cpf?: string;
  dataNascimento?: Date;
  genero?: 'MASCULINO' | 'FEMININO' | 'OUTRO' | 'NAO_INFORMADO';
  endereco?: any;
  ativo?: boolean;
  preferencias?: string;
  observacoes?: string;
  dataCriacao?: Date;
  dataAtualizacao?: Date;
  pedidos?: any[];
}

// Export para compatibilidade com código antigo
export type Candidato = Cliente;
