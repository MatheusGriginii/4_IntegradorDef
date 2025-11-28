import { Endereco } from './endereco';

export interface Cliente {
  id?: number;
  nome: string;
  sobrenome?: string;
  email?: string;
  cpf?: string;
  telefone?: string;
  dataNascimento?: string;
  genero?: 'MASCULINO' | 'FEMININO' | 'OUTRO' | 'NAO_INFORMADO';
  endereco?: Endereco;
  enderecoId?: number;
  preferencias?: string;
  observacoes?: string;
  ativo?: boolean;
}
