export interface Produto {
  id?: number;
  nome: string;
  descricao?: string;
  preco?: number;
  categoria?: { id: number } | any;
  estoque?: number;
  estoqueMinimo?: number;
  imagem?: string;
  ativo?: boolean;
  tempoPreparoMinutos?: number;
  ingredientes?: string;
}
