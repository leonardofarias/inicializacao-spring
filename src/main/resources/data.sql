INSERT INTO TB_UNIDADE VALUES (1, 'UNIDADE CRISTOVÃO', 'Rua Cristovão Colombo, 341');

INSERT INTO TB_CLIENTE VALUES (null, 'Fulano da Silva', '000.111.222-33', FALSE, 1);
INSERT INTO TB_CLIENTE VALUES (null, 'Ciclano Santos', '111.222.333-44', FALSE, 1);

INSERT INTO TB_ANIMAL(ID_CLIENTE, DATA_NASCIMENTO, ESPECIE, NOME, ID_UNIDADE)
VALUES (1, '2019-01-02', 'MAMIFERO', 'TOTO', 1);

INSERT INTO TB_ANIMAL(ID_CLIENTE, DATA_NASCIMENTO, ESPECIE, NOME, ID_UNIDADE)
VALUES (2, '2019-01-02', 'REPTIL', 'REX', 1);

INSERT INTO TB_PRODUTO(ID, VALOR, DESCRICAO, ID_ANIMAL)
VALUES (1, 40.00, 'RAÇÃO', 1);