export interface Usuario {
  id?: number;
  nome: string;
  email: string;
  senha?: string;
  perfil: 'ADMIN' | 'GERENTE' | 'FUNCIONARIO';
  ativo: boolean;
  dataCriacao?: Date;
  dataAtualizacao?: Date;
}
