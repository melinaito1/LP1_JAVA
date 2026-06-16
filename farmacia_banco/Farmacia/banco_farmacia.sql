-- ================================================
--  Script de criação do banco de dados - Farmácia
-- ================================================

CREATE DATABASE IF NOT EXISTS farmacia
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE farmacia;

-- Tabela funcionario
CREATE TABLE IF NOT EXISTS funcionario (
    id    INT          NOT NULL AUTO_INCREMENT,
    nome  VARCHAR(100) NOT NULL,
    cargo VARCHAR(80)  NOT NULL,
    cpf   VARCHAR(14)  NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

-- Tabela remedio
CREATE TABLE IF NOT EXISTS remedio (
    id    INT            NOT NULL AUTO_INCREMENT,
    nome  VARCHAR(100)   NOT NULL,
    tipo  VARCHAR(80)    NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (id)
);

-- Tabela shampoo
CREATE TABLE IF NOT EXISTS shampoo (
    id      INT         NOT NULL AUTO_INCREMENT,
    marca   VARCHAR(100) NOT NULL,
    tipo    VARCHAR(80)  NOT NULL,
    tamanho VARCHAR(30)  NOT NULL,
    PRIMARY KEY (id)
);

-- ================================================
--  Dados de exemplo (opcional)
-- ================================================

INSERT INTO funcionario (nome, cargo, cpf) VALUES
    ('Ana Silva',   'Farmacêutica',   '111.222.333-44'),
    ('Carlos Lima', 'Atendente',      '555.666.777-88'),
    ('Mariana Souza','Gerente',       '999.000.111-22');

INSERT INTO remedio (nome, tipo, preco) VALUES
    ('Dipirona 500mg',    'Analgésico',    8.90),
    ('Amoxicilina 500mg', 'Antibiótico',  22.50),
    ('Omeprazol 20mg',    'Antiácido',    15.00);

INSERT INTO shampoo (marca, tipo, tamanho) VALUES
    ('Head & Shoulders', 'Anticaspa',  200),
    ('Pantene',          'Hidratação', 400),
    ('Seda',             'Liso',       325);
