export interface Categoria {
  id?: number;
  nome: string;
  descricao?: string;
  cor?: string;
  icone?: string;
  ativa?: boolean;
  dataCriacao?: Date;
  dataAtualizacao?: Date;
}

// Export para compatibilidade com código antigo
export type Empresa = Categoria;
