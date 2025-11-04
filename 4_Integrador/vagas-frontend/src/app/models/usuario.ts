import { Endereco } from './endereco';
import { Produto } from './vaga';

export interface Usuario {
  id?: number;
  nome: string;
  email?: string;
  endereco: Endereco;
  produtos: Produto[];
}
