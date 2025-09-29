import { Endereco } from './endereco';
import { Vaga } from './vaga';

export interface Usuario {
  id?: number;
  nome: string;
  email?: string;
  endereco: Endereco;
  vagas: Vaga[];
}
