CREATE TABLE produto (
	id bigserial NOT NULL,
	descricao VARCHAR(100) NOT NULL,
	categoria_id BIGINT NOT NULL,
    FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);	