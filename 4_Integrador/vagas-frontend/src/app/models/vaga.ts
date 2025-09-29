export interface Vaga {
  id?: number;
  titulo: string;
  descricao?: string;
  requisitos?: string;
  salario?: number;
  empresa?: any;
  candidatos?: any[];
  usuarios?: any[];
}
