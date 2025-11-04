export interface Categoria {
  id?: number;
  nome: string;
  descricao?: string;
  produtos?: any[];
}

// Export para compatibilidade com código antigo
export type Empresa = Categoria;
