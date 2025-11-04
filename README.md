# 🍞 Adorela - Panificadora & Confeitaria

Sistema completo de gerenciamento para padaria e confeitaria, desenvolvido com Spring Boot e Angular 19.

## 🎨 Sobre o Projeto

**Adorela Panificadora & Confeitaria** é um sistema web completo que oferece:

- **Área Pública**: Landing page moderna com informações da padaria, produtos em destaque e canais de atendimento
- **Área Administrativa**: Dashboard completo para gestão de produtos, clientes, pedidos e estoque

### Características Principais

- 🏠 **Home Page Elegante**: Design vermelho e branco com fonte Bodoni para a marca
- 👨‍💼 **Sistema Administrativo**: Gestão completa de produtos, clientes e pedidos
- 📱 **Responsivo**: Interface adaptada para desktop, tablet e mobile
- 🎯 **Intuitivo**: Navegação simples e direta para clientes e administradores

## 🛠️ Tecnologias Utilizadas

### Backend
- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **MySQL**
- **Maven**

### Frontend
- **Angular 19** (Standalone Components)
- **TypeScript**
- **SCSS**
- **Bootstrap 5**
- **Font Awesome**
- **Google Fonts** (Bodoni Moda, Poppins)

## 🚀 Como Executar

### Pré-requisitos
- Java 17 ou superior
- Node.js 18+ e npm
- MySQL
- Maven

### Backend (Spring Boot)

1. **Clone o repositório**
```bash
git clone https://github.com/MatheusGriginii/4_IntegradorDef.git
cd 4_IntegradorDef
```

2. **Configure o banco de dados**
   - Edite `main/src/main/resources/application.properties`
   - Configure as credenciais do MySQL

3. **Execute a aplicação**
```bash
cd main
mvn spring-boot:run
```

O backend estará rodando em `http://localhost:8080`

### Frontend (Angular)

1. **Navegue até a pasta do frontend**
```bash
cd vagas-frontend
```

2. **Instale as dependências**
```bash
npm install
```

3. **Execute o servidor de desenvolvimento**
```bash
npm start
```

O frontend estará disponível em `http://localhost:4200`

## 📁 Estrutura do Projeto

```
4_Integrador/
├── main/                          # Backend Spring Boot
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/app/projeto/
│   │   │   │   ├── config/       # Configurações
│   │   │   │   ├── controller/   # Controllers REST
│   │   │   │   ├── entity/       # Entidades JPA
│   │   │   │   ├── repository/   # Repositórios
│   │   │   │   └── service/      # Serviços
│   │   │   └── resources/        # Propriedades
│   └── pom.xml
│
└── vagas-frontend/                # Frontend Angular
    ├── src/
    │   ├── app/
    │   │   ├── components/
    │   │   │   ├── home/         # Landing Page
    │   │   │   ├── login/        # Autenticação
    │   │   │   ├── candidato/    # Gestão de Clientes
    │   │   │   ├── empresa/      # Gestão da Empresa
    │   │   │   ├── usuario/      # Gestão de Usuários
    │   │   │   └── vaga/         # Gestão de Produtos
    │   │   ├── models/           # Modelos TypeScript
    │   │   └── services/         # Serviços HTTP
    │   └── styles.scss
    └── package.json
```

## 🎨 Design System

### Cores
- **Vermelho Principal**: `#C8102E` (vermelho intenso)
- **Vermelho Escuro**: `#8B0000` (dark red)
- **Vermelho Vibrante**: `#E31837`
- **Branco**: `#FFFFFF`

### Tipografia
- **Marca (ADORELA)**: Bodoni Moda 900 - Maiúsculas
- **Subtítulo**: Arial/Helvetica - Sans Serif
- **Corpo**: Poppins

## 📱 Áreas do Sistema

### Área Pública (/)
- Landing page com informações da padaria
- Produtos em destaque
- Depoimentos de clientes
- Informações de contato (telefone e WhatsApp)
- Acesso discreto à área administrativa

### Área Administrativa (/app)
Após login, acesso a:
- Dashboard
- Gestão de Produtos
- Gestão de Clientes
- Gestão de Pedidos
- Gestão de Usuários
- Configurações

## 🔐 Acesso Administrativo

Para acessar a área administrativa:
1. Na home page, clique em "Acesso administrativo?" no rodapé
2. Faça login com suas credenciais
3. Acesse o dashboard completo

## 📄 Documentação Adicional

- [Estrutura do Sistema](ESTRUTURA_SISTEMA.md)
- [Documentação da Home Page](HOME_PAGE_ADORELA.md)
- [Exemplos de Código](EXEMPLOS_CODIGO.md)
- [Solução de CORS](CORS_SOLUTION.md)

## 📝 Licença

Este projeto é de uso educacional.

## 👨‍💻 Autor

**João**
- GitHub: [@jo-4o](https://github.com/jo-4o)

## 👥 Participantes

- **Matheus Grigini** - [@MatheusGriginii](https://github.com/MatheusGriginii)
- **Victor Silva** - [@victorsilv19](https://github.com/victorsilv19)

