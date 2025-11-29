-- Dados iniciais para a Panificadora ADORELA
-- SENHAS DE TESTE (BCrypt):
-- admin@adorela.com.br -> admin123
-- gerente@adorela.com.br -> gerente123
-- vendas@adorela.com.br -> func123

-- Inserindo categorias
INSERT INTO categorias (nome, descricao, icone, cor, categoria_ativa, data_criacao, data_atualizacao) VALUES
('Pães', 'Pães frescos e crocantes feitos diariamente', 'fa-bread-slice', '#8B4513', true, NOW(), NOW()),
('Bolos', 'Bolos artesanais para todas as ocasiões', 'fa-birthday-cake', '#D2691E', true, NOW(), NOW()),
('Tortas', 'Tortas doces e salgadas', 'fa-chart-pie', '#CD853F', true, NOW(), NOW()),
('Doces', 'Doces e sobremesas tradicionais', 'fa-candy-cane', '#DDA0DD', true, NOW(), NOW()),
('Salgados', 'Salgados frescos para lanche', 'fa-hamburger', '#F4A460', true, NOW(), NOW()),
('Bebidas', 'Bebidas quentes e frias', 'fa-coffee', '#8B4513', true, NOW(), NOW());

-- Inserindo produtos - Pães (categoria_id = 1)
INSERT INTO produtos (nome, descricao, preco, categoria_id, estoque, estoque_minimo, produto_ativo, tempo_preparo_minutos, ingredientes, data_criacao, data_atualizacao) VALUES
('Pão Francês', 'Pão crocante e quentinho, saído direto do forno', 0.75, 1, 200, 50, true, 20, 'Farinha de trigo, água, sal, fermento, açúcar', NOW(), NOW()),
('Pão de Forma', 'Pão macio ideal para sanduíches', 8.50, 1, 30, 10, true, 45, 'Farinha de trigo, leite, ovos, açúcar, sal, fermento, manteiga', NOW(), NOW()),
('Pão Integral', 'Pão nutritivo com fibras', 9.00, 1, 25, 8, true, 50, 'Farinha integral, farinha de trigo, água, mel, sal, fermento', NOW(), NOW()),
('Pão de Açúcar', 'Pão doce tradicional', 1.25, 1, 80, 20, true, 30, 'Farinha de trigo, açúcar, ovos, leite, fermento', NOW(), NOW()),
('Pão de Alho', 'Pão temperado com alho e ervas', 12.00, 1, 15, 5, true, 25, 'Pão francês, alho, manteiga, salsinha, orégano', NOW(), NOW());

-- Inserindo produtos - Bolos (categoria_id = 2)
INSERT INTO produtos (nome, descricao, preco, categoria_id, estoque, estoque_minimo, produto_ativo, tempo_preparo_minutos, ingredientes, data_criacao, data_atualizacao) VALUES
('Bolo de Chocolate', 'Bolo fofinho com cobertura de chocolate', 45.00, 2, 8, 2, true, 90, 'Farinha, açúcar, ovos, chocolate, leite, manteiga', NOW(), NOW()),
('Bolo de Cenoura', 'Bolo de cenoura com cobertura de chocolate', 35.00, 2, 6, 2, true, 80, 'Cenoura, farinha, açúcar, ovos, óleo, chocolate', NOW(), NOW()),
('Bolo de Fubá', 'Bolo tradicional de fubá', 28.00, 2, 10, 3, true, 70, 'Fubá, farinha, açúcar, ovos, leite, manteiga', NOW(), NOW()),
('Bolo Red Velvet', 'Bolo vermelho com cream cheese', 55.00, 2, 4, 1, true, 120, 'Farinha, açúcar, ovos, cacau, corante, cream cheese', NOW(), NOW()),
('Bolo de Limão', 'Bolo cítrico refrescante', 32.00, 2, 7, 2, true, 75, 'Farinha, açúcar, ovos, limão, manteiga, leite', NOW(), NOW());

-- Inserindo produtos - Tortas (categoria_id = 3)
INSERT INTO produtos (nome, descricao, preco, categoria_id, estoque, estoque_minimo, produto_ativo, tempo_preparo_minutos, ingredientes, data_criacao, data_atualizacao) VALUES
('Torta de Morango', 'Torta cremosa com morangos frescos', 48.00, 3, 5, 1, true, 100, 'Massa, creme, morangos, açúcar, gelatina', NOW(), NOW()),
('Torta de Frango', 'Torta salgada com frango desfiado', 42.00, 3, 6, 2, true, 95, 'Massa, frango, legumes, temperos, molho', NOW(), NOW()),
('Torta de Maçã', 'Torta doce com maçãs e canela', 38.00, 3, 4, 1, true, 85, 'Massa, maçã, açúcar, canela, manteiga', NOW(), NOW()),
('Torta de Palmito', 'Torta salgada com palmito', 45.00, 3, 3, 1, true, 90, 'Massa, palmito, queijo, temperos, molho branco', NOW(), NOW());

-- Inserindo produtos - Doces (categoria_id = 4)
INSERT INTO produtos (nome, descricao, preco, categoria_id, estoque, estoque_minimo, produto_ativo, tempo_preparo_minutos, ingredientes, data_criacao, data_atualizacao) VALUES
('Brigadeiro', 'Doce tradicional brasileiro', 3.50, 4, 50, 15, true, 15, 'Leite condensado, chocolate, manteiga, granulado', NOW(), NOW()),
('Beijinho', 'Doce de coco com coco ralado', 3.50, 4, 45, 15, true, 15, 'Leite condensado, coco, manteiga, coco ralado', NOW(), NOW()),
('Pudim', 'Pudim cremoso com calda de caramelo', 25.00, 4, 8, 2, true, 180, 'Leite condensado, leite, ovos, açúcar', NOW(), NOW()),
('Quindim', 'Doce de coco e gema', 4.00, 4, 30, 10, true, 45, 'Gema, açúcar, coco ralado', NOW(), NOW()),
('Trufa de Chocolate', 'Trufa artesanal', 5.00, 4, 40, 12, true, 30, 'Chocolate, creme de leite, manteiga, cacau', NOW(), NOW());

-- Inserindo produtos - Salgados (categoria_id = 5)
INSERT INTO produtos (nome, descricao, preco, categoria_id, estoque, estoque_minimo, produto_ativo, tempo_preparo_minutos, ingredientes, data_criacao, data_atualizacao) VALUES
('Coxinha', 'Salgado tradicional de frango', 6.50, 5, 40, 15, true, 30, 'Massa, frango desfiado, temperos', NOW(), NOW()),
('Pão de Açúcar Salgado', 'Pão recheado com presunto e queijo', 8.00, 5, 25, 8, true, 25, 'Massa de pão, presunto, queijo, temperos', NOW(), NOW()),
('Empada', 'Empada de frango ou palmito', 7.50, 5, 30, 10, true, 35, 'Massa, recheio variado, temperos', NOW(), NOW()),
('Croissant', 'Croissant folhado e amanteigado', 8.50, 5, 20, 6, true, 40, 'Farinha, manteiga, leite, fermento', NOW(), NOW()),
('Esfirra', 'Esfirra de carne ou queijo', 6.00, 5, 35, 12, true, 25, 'Massa, carne moída ou queijo, temperos', NOW(), NOW());

-- Inserindo produtos - Bebidas (categoria_id = 6)
INSERT INTO produtos (nome, descricao, preco, categoria_id, estoque, estoque_minimo, produto_ativo, tempo_preparo_minutos, ingredientes, data_criacao, data_atualizacao) VALUES
('Café Expresso', 'Café forte e encorpado', 4.50, 6, 100, 30, true, 3, 'Grãos de café especiais', NOW(), NOW()),
('Cappuccino', 'Café com leite vaporizado e espuma', 7.50, 6, 60, 20, true, 5, 'Café expresso, leite, espuma de leite', NOW(), NOW()),
('Chocolate Quente', 'Chocolate cremoso e quentinho', 8.00, 6, 40, 15, true, 8, 'Chocolate, leite, açúcar, canela', NOW(), NOW()),
('Suco de Laranja', 'Suco natural da fruta', 6.00, 6, 30, 10, true, 5, 'Laranjas frescas', NOW(), NOW()),
('Água Mineral', 'Água mineral gelada', 3.00, 6, 80, 25, true, 0, 'Água mineral', NOW(), NOW());

-- Inserindo usuários com senhas criptografadas
-- Senha: admin123 (BCrypt)
INSERT INTO usuarios (nome, email, senha, perfil, ativo, data_criacao, data_atualizacao) VALUES
('Administrador ADORELA', 'admin@adorela.com.br', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN', true, NOW(), NOW());

-- Senha: gerente123 (BCrypt)
INSERT INTO usuarios (nome, email, senha, perfil, ativo, data_criacao, data_atualizacao) VALUES
('Gerente ADORELA', 'gerente@adorela.com.br', '$2a$10$YOu2WkOPjKhMBz/aKNTNj.5jxnbK9JqLF8FKlNLHJVHZqIj4TL3Yy', 'GERENTE', true, NOW(), NOW());

-- Senha: func123 (BCrypt)
INSERT INTO usuarios (nome, email, senha, perfil, ativo, data_criacao, data_atualizacao) VALUES
('Funcionário Vendas', 'vendas@adorela.com.br', '$2a$10$qgVdGqHBqPMg5JH5vu8FO.FPwNqCMj3F/kNfJz4Z.s7VgHJHKLT6S', 'FUNCIONARIO', true, NOW(), NOW());

-- Inserindo alguns clientes de exemplo
INSERT INTO clientes (nome, sobrenome, email, telefone, cpf, cliente_ativo, data_criacao, data_atualizacao) VALUES
('Maria', 'Silva', 'maria.silva@email.com', '11987654321', '12345678901', true, NOW(), NOW()),
('João', 'Santos', 'joao.santos@email.com', '11976543210', '10987654321', true, NOW(), NOW()),
('Ana', 'Costa', 'ana.costa@email.com', '11965432109', '98765432101', true, NOW(), NOW());

-- Inserindo endereços para os clientes
INSERT INTO enderecos (cep, logradouro, numero, bairro, cidade, estado, area_entrega, taxa_entrega, endereco_ativo, data_criacao, data_atualizacao) VALUES
('01234567', 'Rua das Flores', '123', 'Centro', 'São Paulo', 'SP', true, 5.00, true, NOW(), NOW()),
('02345678', 'Avenida Brasil', '456', 'Vila Nova', 'São Paulo', 'SP', true, 7.50, true, NOW(), NOW()),
('03456789', 'Rua da Paz', '789', 'Jardim América', 'São Paulo', 'SP', true, 6.00, true, NOW(), NOW());

-- Atualizando clientes com endereços
UPDATE clientes SET endereco_id = 1 WHERE id = 1;
UPDATE clientes SET endereco_id = 2 WHERE id = 2;
UPDATE clientes SET endereco_id = 3 WHERE id = 3;
