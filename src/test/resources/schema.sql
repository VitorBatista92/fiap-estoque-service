CREATE SEQUENCE estoque_id_seq START WITH 5 INCREMENT BY 1;

CREATE TABLE estoque (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         nome VARCHAR(255) NOT NULL,
                         sku VARCHAR(255) UNIQUE NOT NULL,
                         quantidade INT,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         deleted_tmsp TIMESTAMP
);