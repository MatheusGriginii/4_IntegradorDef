export interface Endereco {
  id?: number;
  cep?: string;
  rua: string;
  numero?: string;
  cidade: string;
  estado?: string;
  uf?: string;
  complemento?: string;
  usuarios?: any[];
}
