-- Script SQL para criar usuários de teste com senhas criptografadas (BCrypt)
-- Execute este script no banco de dados após iniciar a aplicação

-- IMPORTANTE: As senhas abaixo são:
-- admin123 para ADMIN
-- gerente123 para GERENTE  
-- func123 para FUNCIONARIO

-- Usuário ADMIN
INSERT INTO usuarios (nome, email, senha, perfil, ativo, data_criacao, data_atualizacao) 
VALUES (
  'Administrador do Sistema',
  'admin@adorela.com',
  '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', -- senha: admin123
  'ADMIN',
  true,
  NOW(),
  NOW()
);

-- Usuário GERENTE
INSERT INTO usuarios (nome, email, senha, perfil, ativo, data_criacao, data_atualizacao) 
VALUES (
  'Gerente da Padaria',
  'gerente@adorela.com',
  '$2a$10$YOu2WkOPjKhMBz/aKNTNj.5jxnbK9JqLF8FKlNLHJVHZqIj4TL3Yy', -- senha: gerente123
  'GERENTE',
  true,
  NOW(),
  NOW()
);

-- Usuário FUNCIONARIO
INSERT INTO usuarios (nome, email, senha, perfil, ativo, data_criacao, data_atualizacao) 
VALUES (
  'Funcionário da Loja',
  'funcionario@adorela.com',
  '$2a$10$qgVdGqHBqPMg5JH5vu8FO.FPwNqCMj3F/kNfJz4Z.s7VgHJHKLT6S', -- senha: func123
  'FUNCIONARIO',
  true,
  NOW(),
  NOW()
);

-- Verificar inserção
SELECT id, nome, email, perfil, ativo FROM usuarios;
