-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: dbsyscontroll
-- ------------------------------------------------------
-- Server version	8.0.35

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `osinfo`
--

DROP TABLE IF EXISTS `osinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `osinfo` (
  `os` int NOT NULL AUTO_INCREMENT,
  `data_os` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `idcli` int NOT NULL,
  `produto` varchar(150) NOT NULL,
  `descricao` varchar(150) DEFAULT NULL,
  `funcionario` varchar(100) NOT NULL,
  `status` varchar(30) DEFAULT NULL,
  `quantidade` int NOT NULL,
  `valor` decimal(10,2) DEFAULT NULL,
  `valorttl` decimal(10,2) DEFAULT NULL,
  `pagamento` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`os`),
  KEY `idcli` (`idcli`),
  CONSTRAINT `osinfo_ibfk_1` FOREIGN KEY (`idcli`) REFERENCES `clientes` (`idcli`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `osinfo`
--

LOCK TABLES `osinfo` WRITE;
/*!40000 ALTER TABLE `osinfo` DISABLE KEYS */;
INSERT INTO `osinfo` VALUES (1,'2023-11-10 04:21:27',1,'banner','90x90','marcelo','Produzindo',1,NULL,140.00,'Débito'),(2,'2023-11-10 05:29:47',17,'saco de cimento','salcicha e linguça','cuzin','Terminado',2134,NULL,2342.00,'Crédito'),(3,'2023-11-10 05:37:38',3,'45ty67yujnhg','esdfcvb','ewdfg','Efetuado',123456,NULL,123.00,'Crédito'),(4,'2023-11-10 05:44:40',10,'dfghnfe','wfrgffhnf','esghd','Produzindo',345,NULL,234.00,'Dinheiro');
/*!40000 ALTER TABLE `osinfo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-10  3:57:18
