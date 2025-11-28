export interface Produto {
  id?: number;
  nome: string;
  descricao?: string;
  preco?: number;
  categoria?: any;
  estoque?: number;
  imagemUrl?: string;
  disponivel?: boolean;
}
