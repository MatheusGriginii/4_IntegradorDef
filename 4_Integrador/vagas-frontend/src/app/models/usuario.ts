export interface Usuario {
  id?: number;
  nome: string;
  email: string;
  perfil: 'ADMIN' | 'GERENTE' | 'FUNCIONARIO';
  ativo: boolean;
  dataCriacao?: Date;
  dataAtualizacao?: Date;
}
