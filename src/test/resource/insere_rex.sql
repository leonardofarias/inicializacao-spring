INSERT INTO TB_UNIDADE VALUES (1, 'UNIDADE CRISTOVAO', 'Rua Cristovao Colombo, 341');

INSERT INTO TB_CLIENTE VALUES (133, 'Ciclano Santos', '111.222.333-44', FALSE, 1);

INSERT INTO TB_ANIMAL(ID_CLIENTE, DATA_NASCIMENTO, ESPECIE, NOME, ID_UNIDADE)
VALUES (133, '2019-08-02', 'MAMIFERO', 'Rex', 1);
