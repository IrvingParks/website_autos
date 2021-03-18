
-- crear la base de datos autos y en ella ejecutar este script

DROP TABLE IF EXISTS alquileres;
DROP TABLE IF EXISTS autos;
DROP TABLE IF EXISTS marcas;

CREATE TABLE `marcas` (
  `idmarca` int(11) NOT NULL AUTO_INCREMENT,
  `tipo` varchar(50) NOT NULL,
  `descripcion` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`idmarca`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

CREATE TABLE `autos` (
  `idauto` int(11) NOT NULL AUTO_INCREMENT,
  `idmarca` int(11) NOT NULL,
  `modelo` varchar(200) NOT NULL,
  `foto` mediumblob NOT NULL,
  `estado` varchar(50) NOT NULL DEFAULT 'sinalquiler' COMMENT 'sinalquiler, conalquiler',
  PRIMARY KEY (`idauto`),
  KEY `FK_autos_1` (`idmarca`),
  CONSTRAINT `FK_autos_1` FOREIGN KEY (`idmarca`) REFERENCES `marcas` (`idmarca`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `alquileres` (
  `idalquiler` int(11) NOT NULL AUTO_INCREMENT,
  `idauto` int(11) NOT NULL,
  `fechaalquiler` date NOT NULL,
  `fechafinalquiler` date NOT NULL,
  `horainicio` varchar(20) NOT NULL,
  `horafin` varchar(20) NOT NULL,
  `descripcion` varchar(200) DEFAULT NULL,
  `estado` varchar(20) NOT NULL DEFAULT 'novence' COMMENT 'novence, yavencio',
  PRIMARY KEY (`idalquiler`),
  KEY `FK_alquileres_1` (`idauto`),
  CONSTRAINT `FK_alquileres_1` FOREIGN KEY (`idauto`) REFERENCES `autos` (`idauto`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `marcas` (idmarca, tipo, descripcion) VALUES (1, 'Chevrolet', NULL);
INSERT INTO `marcas` (idmarca, tipo, descripcion) VALUES (2, 'Ferrari', NULL);
INSERT INTO `marcas` (idmarca, tipo, descripcion) VALUES (3, 'Hyundai', NULL);
INSERT INTO `marcas` (idmarca, tipo, descripcion) VALUES (4, 'Toyota', NULL);
