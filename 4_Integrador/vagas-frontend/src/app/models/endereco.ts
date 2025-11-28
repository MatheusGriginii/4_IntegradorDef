export interface Endereco {
  id?: number;
  cep: string;
  logradouro: string;
  numero: string;
  complemento?: string;
  bairro: string;
  cidade: string;
  estado: string;
  pontoReferencia?: string;
  observacoes?: string;
  areaEntrega?: boolean;
  taxaEntrega?: number;
  ativo?: boolean;
  dataCriacao?: Date;
  dataAtualizacao?: Date;
}
