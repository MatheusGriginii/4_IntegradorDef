# Sistema de Vagas - Frontend Angular 19

## Projeto Integrador - Sistema de Gestão de Vagas de Emprego

Este é o frontend em Angular 19 desenvolvido para o projeto integrador do sistema de gestão de vagas de emprego, seguindo as especificações do edital.

## 🚀 Status do Projeto

✅ **FUNCIONANDO**: Servidor Angular rodando em http://localhost:4200

### Funcionalidades Implementadas ✅

#### 1. Estrutura do Projeto
- ✅ Angular 19 com componentes standalone
- ✅ Arquitetura modular (models, services, components)
- ✅ Roteamento configurado
- ✅ Sistema de autenticação básico

#### 2. Models (Interfaces TypeScript)
- ✅ `Usuario` - com nome, email, endereço e vagas
- ✅ `Candidato` - com nome, dados pessoais e currículo
- ✅ `Empresa` - com dados corporativos
- ✅ `Vaga` - com descrição, requisitos e empresa
- ✅ `Endereco` - com campos completos (CEP, rua, número, cidade, estado, etc.)

#### 3. Services (Integração com Backend)
- ✅ `UsuarioService` - CRUD completo + filtros
- ✅ `CandidatoService` - operações básicas
- ✅ `EmpresaService` - operações básicas  
- ✅ `VagaService` - operações básicas
- ✅ `EnderecoService` - operações básicas

#### 4. Componentes Desenvolvidos
- ✅ `LoginComponent` - autenticação com SweetAlert2
- ✅ `LayoutComponent` - estrutura principal da aplicação
- ✅ `MenuSuperiorComponent` - barra de navegação com logout
- ✅ `UsuarioListComponent` - listagem com filtros e ações
- ✅ `UsuarioFormComponent` - formulário completo de CRUD

#### 5. Recursos Visuais
- ✅ Bootstrap 5 integrado
- ✅ Font Awesome para ícones
- ✅ SweetAlert2 para alertas elegantes
- ✅ Design responsivo
- ✅ Tema customizado com cores e estilos

#### 6. Funcionalidades Específicas
- ✅ Sistema de login com redirecionamento
- ✅ Filtros de busca (por nome e cidade)
- ✅ Confirmação de exclusão com SweetAlert2
- ✅ Validação de formulários
- ✅ Loading states
- ✅ Tratamento de erros

### Em Desenvolvimento 🟡

#### Componentes Pendentes
- 🟡 `CandidatoListComponent` e `CandidatoFormComponent`
- 🟡 `EmpresaListComponent` e `EmpresaFormComponent`
- 🟡 `VagaListComponent` e `VagaFormComponent`
- 🟡 `EnderecoListComponent` e `EnderecoFormComponent`

#### Funcionalidades Avançadas
- 🟡 Gerenciamento de relacionamentos via modais
- 🟡 Upload de arquivos (currículo)
- 🟡 Dashboard com estatísticas
- 🟡 Relatórios

## 🛠 Tecnologias Utilizadas

- **Angular 19** - Framework principal
- **TypeScript** - Linguagem de programação
- **Bootstrap 5** - Framework CSS
- **SweetAlert2** - Alertas e confirmações
- **Font Awesome** - Biblioteca de ícones
- **RxJS** - Programação reativa
- **Angular Router** - Roteamento
- **Angular Forms** - Formulários reativos

## 📁 Estrutura de Pastas

```
src/
├── app/
│   ├── components/
│   │   ├── candidato/
│   │   │   ├── candidato-list/
│   │   │   └── candidato-form/
│   │   ├── empresa/
│   │   │   ├── empresa-list/
│   │   │   └── empresa-form/
│   │   ├── endereco/
│   │   │   ├── endereco-list/
│   │   │   └── endereco-form/
│   │   ├── layout/
│   │   ├── login/
│   │   ├── menu-superior/
│   │   ├── usuario/
│   │   │   ├── usuario-list/
│   │   │   └── usuario-form/
│   │   └── vaga/
│   │       ├── vaga-list/
│   │       └── vaga-form/
│   ├── models/
│   │   ├── candidato.ts
│   │   ├── empresa.ts
│   │   ├── endereco.ts
│   │   ├── usuario.ts
│   │   └── vaga.ts
│   ├── services/
│   │   ├── candidato.service.ts
│   │   ├── empresa.service.ts
│   │   ├── endereco.service.ts
│   │   ├── usuario.service.ts
│   │   └── vaga.service.ts
│   ├── app.component.ts
│   ├── app.config.ts
│   └── app.routes.ts
└── styles.scss
```

## 🚀 Como Executar

### Pré-requisitos
- Node.js (versão 18+)
- Angular CLI
- Backend Spring Boot rodando na porta 8080

### Comandos

```bash
# Instalar dependências
npm install

# Executar em modo desenvolvimento
ng serve

# Acessar a aplicação
http://localhost:4200
```

## 🔧 Configuração

### Backend API
O frontend está configurado para consumir a API REST do backend Spring Boot:
- **Base URL**: `http://localhost:8080`
- **Endpoints**: Seguem o padrão RESTful (/usuarios, /candidatos, /empresas, etc.)

### Roteamento
- `/` - Redirect para login
- `/login` - Página de autenticação
- `/app/usuarios` - Lista de usuários
- `/app/usuarios/novo` - Novo usuário
- `/app/usuarios/editar/:id` - Editar usuário
- Mais rotas para outras entidades...

## 📋 Funcionalidades por Tela

### Login
- Formulário de autenticação
- Validação de campos obrigatórios
- Redirecionamento após login
- Mensagens de erro/sucesso

### Lista de Usuários
- Tabela responsiva com dados dos usuários
- Filtros por nome e cidade
- Botões de ação (editar, excluir, gerenciar vagas)
- Paginação (futura implementação)
- Loading state durante requisições

### Formulário de Usuário
- Campos para dados pessoais
- Seção específica para endereço
- Validação de formulário
- Estados brasileiros no select
- Confirmação de exclusão
- Botões de ação contextuais

## 🎨 Padrões de UI/UX

- **Cards** para organizar conteúdo
- **Botões** com ícones e cores semânticas
- **Tabelas** responsivas e estilizadas
- **Formulários** com validação visual
- **Alertas** elegantes com SweetAlert2
- **Loading** states para melhor UX

## 🔗 Integração com Backend

Todos os services estão preparados para integração completa:
- Métodos HTTP (GET, POST, PUT, DELETE)
- Tratamento de erros
- Observables para programação reativa
- Interceptors para autenticação (futura implementação)

## 📝 Próximos Passos

1. **Completar CRUDs** - Implementar formulários para todas as entidades
2. **Relacionamentos** - Modais para gerenciar relacionamentos entre entidades
3. **Upload de Arquivos** - Sistema para upload de currículos
4. **Dashboard** - Página inicial com estatísticas
5. **Relatórios** - Geração de relatórios em PDF
6. **Testes** - Implementar testes unitários e e2e

## 🐛 Problemas Conhecidos

- Algumas validações de TypeScript ainda precisam ser ajustadas
- MDB Angular UI Kit foi removido temporariamente (conflitos de importação)
- Componentes de formulário para outras entidades ainda não implementados

## ✅ Conformidade com Edital

- ✅ Single Page Application (SPA)
- ✅ Angular 19
- ✅ Integração com backend Spring Boot
- ✅ CRUD completo para entidades principais
- ✅ Interface responsiva
- ✅ Organização em pastas específicas
- ✅ Uso de bibliotecas modernas (SweetAlert2, Bootstrap)
- ✅ Programação reativa com RxJS

---

## 👥 Desenvolvimento

Este projeto foi desenvolvido seguindo as melhores práticas do Angular e as especificações do edital do projeto integrador.

**Status**: Em desenvolvimento ativo ✅
**Última atualização**: Setembro 2024
