CREATE TABLE `veiculo` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ano` int NOT NULL,
  `created` datetime(6) NOT NULL,
  `descricao` varchar(500) NOT NULL,
  `marca` enum('AUDI','BMW','CHEVROLET','CITROEN','FIAT','FORD','HONDA','HYUNDAI','JEEP','KIA','LAND_ROVER','MERCEDES_BENZ','MITSUBISHI','NISSAN','PEUGEOT','PORSCHE','RENAULT','TOYOTA','VOLKSWAGEN','VOLVO') NOT NULL,
  `updated` datetime(6) DEFAULT NULL,
  `veiculo` varchar(255) NOT NULL,
  `vendido` boolean NOT NULL,
  `cor` varchar(255) DEFAULT NULL,
  `excluido` boolean DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;
