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
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `idcli` int NOT NULL AUTO_INCREMENT,
  `nomecli` varchar(50) NOT NULL,
  `phonecli` varchar(15) NOT NULL,
  `emailcli` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idcli`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES (1,'jenilson ribeiro','098798765','janilsonrb@gmail.com'),(2,'flavio henrique','92738293540','inflavio@gmail.com'),(3,'maria julia','19098234836','majucalu@gmail.com'),(5,'marli da silva','7498341526','marlisilva@gmail.com'),(6,'felipe araujo','19999263541','faraujo@gmail.com'),(7,'debora santos','99987636514','debsantos@gmail.com'),(9,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(10,'flavio henrique','92738293540','inflavio@gmail.com'),(11,'maria julia','19098234836','majucalu@gmail.com'),(12,'lorenzo gabriel','51983769204','lgw.deev@gmail.com'),(13,'marli da silva','7498341526','marlisilva@gmail.com'),(14,'felipe araujo','19999263541','faraujo@gmail.com'),(15,'debora santos','99987636514','debsantos@gmail.com'),(16,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(17,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(18,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(19,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(20,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(21,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(22,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(23,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(24,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(25,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(26,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(27,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(28,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(29,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(30,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(31,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(32,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(33,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(34,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(35,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(36,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(37,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(38,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(39,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(40,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(41,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(42,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(43,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(44,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(45,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(46,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(47,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(48,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(49,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(50,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(51,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(52,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(53,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(54,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(55,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(56,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(57,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(58,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(59,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(60,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(61,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(62,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(63,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(64,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(65,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(66,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(67,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(68,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(69,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(70,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(71,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(72,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(73,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(74,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(75,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(76,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(77,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(78,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(79,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(80,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(81,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(82,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(83,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(84,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(85,'jenilson ribeiro','19987345274','janilsonrb@gmail.com'),(86,'jenilson ribeiro','19987345274','janilsonrb@gmail.com');
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
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
