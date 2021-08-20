DROP TABLE IF EXISTS `operacao`;
DROP TABLE IF EXISTS `associado`;

CREATE TABLE `associado` (
  `id` bigint NOT NULL,
  `conta` varchar(255) DEFAULT NULL,
  `documento` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
CREATE TABLE `operacao` (
  `id` bigint NOT NULL,
  `produto` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `taxa_juros` decimal(19,2) DEFAULT NULL,
  `valor` decimal(19,2) DEFAULT NULL,
  `vencimento` date DEFAULT NULL,
  `id_associado` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_operacao_associado` FOREIGN KEY (`id_associado`) REFERENCES `associado` (`id`)
);

INSERT INTO associado(id, conta, documento) VALUES(1, '123456', '12345678912345');
INSERT INTO OPERACAO(id, produto, status, taxa_juros, valor, vencimento, id_associado)
VALUES(1, 'Financiamento', 'ABERTO', 100.1, 100.1, '2022-08-21', 1);

