# API RESTful de Vagas/Candidatos

## Requisitos
- Java 17
- Maven

## Como executar

1. **Clonar o repositório**
```bash
git clone <url-do-repo>
cd 4_Periodo-main
```

2. **Executar a aplicação**
```bash
mvn -f .\main\pom.xml spring-boot:run
```

## Endpoints disponíveis

### Usuarios
- `GET /usuarios` - Listar todos
- `POST /usuarios` - Criar novo
- `GET /usuarios/{id}` - Buscar por ID
- `PUT /usuarios/{id}` - Atualizar
- `DELETE /usuarios/{id}` - Deletar

### Empresas
- `GET /empresas` - Listar todos
- `POST /empresas` - Criar novo
- `GET /empresas/{id}` - Buscar por ID
- `PUT /empresas/{id}` - Atualizar
- `DELETE /empresas/{id}` - Deletar

### Vagas
- `GET /vagas` - Listar todos
- `POST /vagas` - Criar novo
- `GET /vagas/{id}` - Buscar por ID
- `PUT /vagas/{id}` - Atualizar
- `DELETE /vagas/{id}` - Deletar

### Candidatos
- `GET /candidatos` - Listar todos
- `POST /candidatos` - Criar novo
- `GET /candidatos/{id}` - Buscar por ID
- `PUT /candidatos/{id}` - Atualizar
- `DELETE /candidatos/{id}` - Deletar


