-- ========================================
-- SCHEMA COMPLETO DO BANCO DE DADOS ADORELA
-- Sistema de Panificadora
-- Database: MySQL 8.0+
-- ========================================

-- Remover tabelas existentes (cuidado em produção!)
DROP TABLE IF EXISTS itens_pedido;
DROP TABLE IF EXISTS pedidos;
DROP TABLE IF EXISTS clientes;
DROP TABLE IF EXISTS enderecos;
DROP TABLE IF EXISTS produtos;
DROP TABLE IF EXISTS categorias;
DROP TABLE IF EXISTS usuarios;

-- ========================================
-- TABELA: usuarios
-- Descrição: Usuários do sistema (Admin, Gerente, Funcionário)
-- ========================================
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    perfil VARCHAR(20) NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE,
    data_criacao DATETIME NOT NULL,
    data_atualizacao DATETIME,
    
    INDEX idx_usuario_email (email),
    INDEX idx_usuario_perfil (perfil)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ========================================
-- TABELA: categorias
-- Descrição: Categorias de produtos (Pães, Bolos, etc)
-- ========================================
CREATE TABLE categorias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE,
    descricao VARCHAR(255),
    icone VARCHAR(255),
    cor VARCHAR(7) DEFAULT '#C8102E',
    categoria_ativa BOOLEAN NOT NULL DEFAULT TRUE,
    data_criacao DATETIME NOT NULL,
    data_atualizacao DATETIME,
    
    INDEX idx_categoria_ativa (categoria_ativa)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ========================================
-- TABELA: produtos
-- Descrição: Produtos da panificadora
-- ========================================
CREATE TABLE produtos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(500),
    preco DECIMAL(10,2) NOT NULL,
    categoria_id BIGINT NOT NULL,
    estoque INTEGER NOT NULL DEFAULT 0,
    estoque_minimo INTEGER DEFAULT 0,
    produto_ativo BOOLEAN NOT NULL DEFAULT TRUE,
    imagem VARCHAR(255),
    tempo_preparo_minutos INTEGER,
    ingredientes VARCHAR(1000),
    data_criacao DATETIME NOT NULL,
    data_atualizacao DATETIME,
    
    CONSTRAINT fk_produto_categoria 
        FOREIGN KEY (categoria_id) 
        REFERENCES categorias(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    
    INDEX idx_produto_categoria (categoria_id),
    INDEX idx_produto_ativo (produto_ativo),
    INDEX idx_produto_nome (nome)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ========================================
-- TABELA: enderecos
-- Descrição: Endereços de entrega
-- ========================================
CREATE TABLE enderecos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cep VARCHAR(8) NOT NULL,
    logradouro VARCHAR(200) NOT NULL,
    numero VARCHAR(10) NOT NULL,
    complemento VARCHAR(100),
    bairro VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    estado VARCHAR(2) NOT NULL,
    ponto_referencia VARCHAR(200),
    endereco_ativo BOOLEAN NOT NULL DEFAULT TRUE,
    area_entrega BOOLEAN NOT NULL DEFAULT TRUE,
    taxa_entrega DECIMAL(10,2),
    observacoes VARCHAR(255),
    data_criacao DATETIME NOT NULL,
    data_atualizacao DATETIME,
    
    INDEX idx_endereco_cep (cep),
    INDEX idx_endereco_cidade (cidade),
    INDEX idx_endereco_area_entrega (area_entrega)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ========================================
-- TABELA: clientes
-- Descrição: Clientes da panificadora
-- ========================================
CREATE TABLE clientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    sobrenome VARCHAR(100),
    email VARCHAR(150) UNIQUE,
    telefone VARCHAR(11),
    cpf VARCHAR(11) UNIQUE,
    data_nascimento DATE,
    genero VARCHAR(10),
    endereco_id BIGINT,
    cliente_ativo BOOLEAN NOT NULL DEFAULT TRUE,
    preferencias VARCHAR(500),
    observacoes VARCHAR(1000),
    data_criacao DATETIME NOT NULL,
    data_atualizacao DATETIME,
    
    CONSTRAINT fk_cliente_endereco 
        FOREIGN KEY (endereco_id) 
        REFERENCES enderecos(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE,
    
    INDEX idx_cliente_email (email),
    INDEX idx_cliente_cpf (cpf),
    INDEX idx_cliente_telefone (telefone),
    INDEX idx_cliente_ativo (cliente_ativo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ========================================
-- TABELA: pedidos
-- Descrição: Pedidos realizados pelos clientes
-- ========================================
CREATE TABLE pedidos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
    tipo_pedido VARCHAR(20) NOT NULL DEFAULT 'BALCAO',
    valor_total DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    valor_desconto DECIMAL(10,2) DEFAULT 0.00,
    valor_final DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    observacoes VARCHAR(500),
    data_pedido DATETIME NOT NULL,
    data_entrega_prevista DATETIME,
    data_entrega_realizada DATETIME,
    forma_pagamento VARCHAR(20),
    endereco_entrega VARCHAR(500),
    telefone_contato VARCHAR(15),
    
    CONSTRAINT fk_pedido_cliente 
        FOREIGN KEY (cliente_id) 
        REFERENCES clientes(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    
    INDEX idx_pedido_cliente (cliente_id),
    INDEX idx_pedido_status (status),
    INDEX idx_pedido_tipo (tipo_pedido),
    INDEX idx_pedido_data (data_pedido),
    INDEX idx_pedido_status_data (status, data_pedido)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ========================================
-- TABELA: itens_pedido
-- Descrição: Itens de cada pedido
-- ========================================
CREATE TABLE itens_pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pedido_id BIGINT NOT NULL,
    produto_id BIGINT NOT NULL,
    quantidade INTEGER NOT NULL,
    preco_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    observacoes VARCHAR(255),
    
    CONSTRAINT fk_item_pedido 
        FOREIGN KEY (pedido_id) 
        REFERENCES pedidos(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    
    CONSTRAINT fk_item_produto 
        FOREIGN KEY (produto_id) 
        REFERENCES produtos(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    
    INDEX idx_item_pedido (pedido_id),
    INDEX idx_item_produto (produto_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ========================================
-- CONSTRAINTS E VALIDAÇÕES ADICIONAIS
-- ========================================

-- Garantir que valores monetários sejam positivos
ALTER TABLE produtos ADD CONSTRAINT chk_produto_preco CHECK (preco > 0);
ALTER TABLE produtos ADD CONSTRAINT chk_produto_estoque CHECK (estoque >= 0);
ALTER TABLE pedidos ADD CONSTRAINT chk_pedido_valor_total CHECK (valor_total >= 0);
ALTER TABLE pedidos ADD CONSTRAINT chk_pedido_valor_final CHECK (valor_final >= 0);
ALTER TABLE itens_pedido ADD CONSTRAINT chk_item_quantidade CHECK (quantidade > 0);
ALTER TABLE itens_pedido ADD CONSTRAINT chk_item_preco CHECK (preco_unitario > 0);

-- ========================================
-- COMENTÁRIOS DAS TABELAS
-- ========================================

ALTER TABLE usuarios COMMENT = 'Tabela de usuários do sistema (Admin, Gerente, Funcionário)';
ALTER TABLE categorias COMMENT = 'Categorias de produtos da panificadora';
ALTER TABLE produtos COMMENT = 'Produtos disponíveis para venda';
ALTER TABLE enderecos COMMENT = 'Endereços de entrega dos clientes';
ALTER TABLE clientes COMMENT = 'Cadastro de clientes';
ALTER TABLE pedidos COMMENT = 'Pedidos realizados';
ALTER TABLE itens_pedido COMMENT = 'Itens de cada pedido';

-- ========================================
-- FIM DO SCHEMA
-- ========================================
