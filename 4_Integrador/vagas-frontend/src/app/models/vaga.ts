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

// Export para compatibilidade com código antigo
export type Vaga = Produto;
