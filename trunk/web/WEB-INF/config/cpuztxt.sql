-- MySQL dump 10.11
--
-- Host: localhost    Database: CPUZ
-- ------------------------------------------------------
-- Server version	5.0.51b-community-nt

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `config`
--

DROP TABLE IF EXISTS `config`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `config` (
  `cfg_id` int(11) NOT NULL default '0',
  `cfg_visitas` int(11) default '0',
  PRIMARY KEY  (`cfg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `config`
--

LOCK TABLES `config` WRITE;
/*!40000 ALTER TABLE `config` DISABLE KEYS */;
INSERT INTO `config` VALUES (1,701);
/*!40000 ALTER TABLE `config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doclogger`
--

DROP TABLE IF EXISTS `doclogger`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `doclogger` (
  `dol_id` int(11) NOT NULL auto_increment,
  `dol_id_dom` int(11) NOT NULL default '0',
  `dol_reference_repository` varchar(128) default '',
  `dol_logged_action` varchar(16) default '',
  `dol_date_action` datetime default NULL,
  `dol_user` varchar(8) default '',
  `dol_url` varchar(256) default '',
  `dol_document_use` varchar(128) default '',
  PRIMARY KEY  (`dol_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `doclogger`
--

LOCK TABLES `doclogger` WRITE;
/*!40000 ALTER TABLE `doclogger` DISABLE KEYS */;
/*!40000 ALTER TABLE `doclogger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `docmanager`
--

DROP TABLE IF EXISTS `docmanager`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `docmanager` (
  `dom_id` int(11) NOT NULL auto_increment,
  `dom_reference_repository` varchar(128) default '',
  `dom_reference_creator` varchar(128) default '',
  `dom_date` datetime default NULL,
  `dom_user` varchar(8) default '',
  `dom_size` varchar(32) default '',
  PRIMARY KEY  (`dom_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `docmanager`
--

LOCK TABLES `docmanager` WRITE;
/*!40000 ALTER TABLE `docmanager` DISABLE KEYS */;
/*!40000 ALTER TABLE `docmanager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `documents`
--

DROP TABLE IF EXISTS `documents`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `documents` (
  `doc_id` int(11) NOT NULL auto_increment,
  `doc_date` datetime default NULL,
  `doc_user` varchar(8) default '',
  `doc_user_reference` varchar(64) default '',
  `doc_filename` varchar(64) default '',
  `doc_repository_reference` varchar(64) default '',
  `doc_scope` int(11) default '0',
  PRIMARY KEY  (`doc_id`),
  KEY `DATE` (`doc_date`),
  KEY `USER` (`doc_user`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `documents`
--

LOCK TABLES `documents` WRITE;
/*!40000 ALTER TABLE `documents` DISABLE KEYS */;
INSERT INTO `documents` VALUES (1,'2010-12-28 10:15:34','ecosys','Nota en PDF','Indice Taller Javascript.rtf','001293570966140_Indice Taller Javascript.rtf',0),(2,'2011-01-06 11:00:06','ecosys','El Zorongo sostenible','EL ZORONGO SOSTENIBLE.pdf','001294351268578_EL ZORONGO SOSTENIBLE.pdf',0);
/*!40000 ALTER TABLE `documents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `images`
--

DROP TABLE IF EXISTS `images`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `images` (
  `img_id` int(11) NOT NULL auto_increment,
  `img_date` datetime default NULL,
  `img_user` varchar(8) default '',
  `img_user_reference` varchar(64) default '',
  `img_filename` varchar(64) default '',
  `img_repository_reference` varchar(64) default '',
  `img_scope` int(11) default '0',
  PRIMARY KEY  (`img_id`),
  KEY `DATE` (`img_date`),
  KEY `USER` (`img_user`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `images`
--

LOCK TABLES `images` WRITE;
/*!40000 ALTER TABLE `images` DISABLE KEYS */;
INSERT INTO `images` VALUES (1,'2010-08-21 05:30:05','admin','Aceras rotas','th_p1020116_mini.png','001282579072546_th_p1020116_mini.png',0),(2,'2010-08-22 11:58:11','admin','Imagen de documento','ScanImage009.jpg','001282578857843_ScanImage009.jpg',0),(3,'2010-08-23 12:09:02','admin','Cuarta imagen','ff03.jpg','001282558142156_ff03.jpg',0),(4,'2010-09-08 04:51:08','admin','Desde Atalaya','desde_atalaya1mini.jpg','001290369962859_desde_atalaya1mini.jpg',0),(5,'2010-09-08 05:06:36','admin','Otro erizo','erizo2.jpg','001283958396359_erizo2.jpg',0),(6,'2010-09-08 05:06:54','admin','Erizo 3','erizo3.jpg','001283958414859_erizo3.jpg',0),(7,'2010-11-21 10:21:06','ecosys','Seales de trafico','senales_trafico_200.jpg','001290374490390_senales_trafico_200.jpg',1);
/*!40000 ALTER TABLE `images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `infoblocks`
--

DROP TABLE IF EXISTS `infoblocks`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `bugs` (
  `bug_id` int(11) NOT NULL auto_increment,
  `bug_date` datetime default NULL,
  `bug_status` int(11) default '0',             //0=created; 1=incourse; 2=ended
  `bug_user` varchar(8) default '',
  `bug_priority` int(11) default '0',           //0=minimum; 10=maximum
  `bug_type` varchar(16) default '',
  `bug_application` varchar(16) default '',
  `bug_header` varchar(128) default '',
  `bug_body` text,
  PRIMARY KEY  (`bug_id`),
  KEY `DATE` (`bug_date`),
  KEY `PRORITY` (`bug_priority`),
  KEY `TYPE` (`bug_type`),
  KEY `USER` (`bug_user`)
) ENGINE=InnoDB ;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `infoblocks`
--

LOCK TABLES `infoblocks` WRITE;
/*!40000 ALTER TABLE `infoblocks` DISABLE KEYS */;
INSERT INTO `infoblocks` VALUES (2,'2010-08-07 09:18:26',2,'admin','Subtítulo','GENERICO','Vecinal',1),(3,'2010-08-20 06:43:17',2,'admin','Destácado','Gago: Algunos van a tener que salir, pero yo me quedo','Me voy a quedar está año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos van a tener que salir, pero yo me quedo. Lo que salió publicado en todos lados me sorprendió; yo había hablado con el técnico y con Valdano, comentó.',0),(4,'2010-08-20 07:00:28',2,'admin','Titular','Sería dificil decirle no al Manchestár','Rafael van der Vaart se mostró dispuestá a ser traspasado al Manchestár United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés, posible descarte blanco en una plantilla con cuatro mediapuntas, señaló que sería difícil decirle que no al United con la situación que tengo en el Madrid. Alex Ferguson es un gran entrenador y yo nunca me comprometería con un equipo que no tuviera opciones de ganar títulos. En el Manchestár alcanzar el éxito es posible y ese es un factor muy importante para mí en está momento de carrera\r\n\r\nVan der Vaart dijo que no jugará en un equipo español que no sea el Madrid: Si tengo que marcharme sólo hay club que no significaría un paso atrás: el Manchestár United. Si Mourinho me deja claro que no jugaré mucho, veo mi futuro en el fútbol inglés, concluyó.',0),(5,'2010-09-08 04:48:37',2,'admin','Titular','Criar erizos en el Zorongo','Si estás interesado en la cría de erizos, solamente tienes que poner cada noche un poco de alimento en gránulos para gatos en alguna parte de tu jardín. \r\nEl erizo sale de sus escondites una vez que ha anochecido, como una hora u hora y media después de hacerse de noche, y tarde o temprano irá a comer del alimento que le has puestá.\r\nSi deseas observarlos, tienes que estár atento a la hora que hemos indicado y acercarte hasta el lugar que le has puestá el alimento con una linterna.\r\nCuando detectan que un extraño se acerca, el erizo se aplana contra el suelo no mostrando su cabeza ni sus extremidades, ofreciendo tan sólo sus púas, que lo recubren en su totalidad.',1),(6,'2010-09-08 04:56:47',2,'admin','Titular','El erizo de tierra es un animal protegido','Se recuerda a todos los vecinos que los erizos de tierra son animales protegidos y su maltrato o caza está penado por ley.\r\nLos erizos realizan una labor muy importante en nuestáos jardines y en el campo. Son animales que se alimentan de larvas y gusanos que capturan escarbando la tierra por lo que controlan las plagas de insectos perjudiciales para los cultivos.\r\n',0),(7,'2010-09-08 05:05:57',2,'admin','Titular','ONG Erizos sin fronteras','Si deseas participar en la defensa de los erizos de tierra para evitar su exterminio y favorecer su cría, te ofrecemos la posibilidad de ser socio de está ONG dedicada a la conservación de dicha especie animal.\r\nErizos sin fronteras nació hace ya más de tres días y desde entonces hemos logrado que la población de erizos pasara de unas 4 parejas a más de 8 millones de ejemplares que se encuentran censados y reciben una renta mensual en especies (alimentos, jaulas dignas con spa para erizos, servicios sanitarios, etc.) de aproximadamente 500 euros mensuales por erizo.\r\nAdvertimos que solamente puedes ser colaborador y que no vale apuntarse a ser erizo, que hay gente muy lista.',1),(8,'2010-11-17 12:00:00',2,'ecosys','Titular','ENVIO DE FOTOGRAFIAS','Los que deseeis mandar fotografías para su inserción en algún artículo o en alguna galería de fotos, os pedimos, por favor, que lo hagais con una resolución de 640 pixels en su lado mayor. Esto es para que no \"pesen\" demasiado a la hora de ponerlas en la página web y sean lentas de visualizar; además, el servidor no es un pozo sin fondo y tiene una capacidad limitada. El que desee tener las originales con mayor resolución, que se ponga en contacto con el autor origen de las mismas y que se las pida de forma particular.',1),(9,'2011-01-14 09:37:02',2,'ecosys','Titular','Recogida de perros','A todo perro que se le vea suelto por el Zorongo, se le intentará coger con el viejo truco de echarle un trozo de carne para hacerse amigo suyo y en cuento lo tienes cerca lo trincas.\r\nY a los dueños les vamos a meter una multa pa cagarse.',1),(10,'2011-01-14 09:45:11',2,'ecosys','Titular','Próximos cortes de agua','El pesao de Ferruz está empeñado en cortar el agua cada dos por tres. Por eso, la próxima semana hay anunciados cortes de agua según el siguiente programa:\r\n\r\nLunes: De 10 a 11 de la mañana. Parcelas afectadas: 112 a 128, 149 a 175 y 201 a 216.\r\n\r\nMartes: De 10 a 11 de la mañana. Parcelas afectadas: Un montón.\r\n\r\nY así sucesivamente.',1),(11,'2011-01-14 09:56:55',2,'ecosys','Titular','Busco bicicleta vieja','Recogería gratis una bicicleta vieja, no importa estádo. Quiero hacer con ella una rueca para hilar algodón. 6789012345 ext. jeje',0),(12,'2011-01-14 09:59:09',0,'ecosys','Titular','Vendo hormigonera eléctrica de 160 litros.','Por 50 euros. Está como nueva. Solamente hay que darle una manita de pintura y queda guapa, guapa. 976000001',0);
/*!40000 ALTER TABLE `infoblocks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `newscomposition`
--

DROP TABLE IF EXISTS `newscomposition`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `newscomposition` (
  `nco_composition_id` int(11) NOT NULL auto_increment,
  `nco_npi_id` int(11) NOT NULL,
  `nco_component_type` varchar(16) default '',
  `nco_component_id` int(11) NOT NULL default '0',
  `nco_order` varchar(8) default '',
  `nco_header_alternate` varchar(128) default '',
  `nco_header_style` varchar(16) default '',
  `nco_body_abstract` varchar(256) default '',
  `nco_image_high` int(11) default '0',
  `nco_image_width` int(11) default '0',
  `nco_linked_element` varchar(256) default '',
  PRIMARY KEY  (`nco_composition_id`),
  KEY `NCO_ORDER` (`nco_npi_id`,`nco_order`)
) ENGINE=InnoDB AUTO_INCREMENT=223 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `newscomposition`
--

LOCK TABLES `newscomposition` WRITE;
/*!40000 ALTER TABLE `newscomposition` DISABLE KEYS */;
INSERT INTO `newscomposition` VALUES (4,2,'InfoBlock',2,'1','FIESTAS DEL ZORONGO','Subtítulo','Consulta el programa de fiestás en el enlace.',0,0,''),(7,2,'InfoBlock',2,'2','GENERICO','Subtítulo','Se avisa de cortes de agua.',0,0,''),(10,2,'Image',3,'3','Cuarta imagen','left','001282558142156_ff03.jpg',0,0,''),(13,4,'Image',5,'1','Otro erizo','left','001283958396359_erizo2.jpg',0,0,''),(14,4,'InfoBlock',6,'2','El erizo de tierra es un animal protegido','Titular','Se recuerda a todos los vecinos que los erizos de tierra son animales protegidos y su maltrato o caza está penado por ley.\r\nLos erizos realizan una labor muy importante en nuestáos jardines y en el campo. Son animales que se alimentan de larvas y gusanos ',0,0,''),(17,33,'Image',2,'1','Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(18,33,'InfoBlock',4,'2','Sería dificil decirle no al Manchestár','Titular','Rafael van der Vaart se mostró dispuestá a ser traspasado al Manchestár United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(19,33,'Image',1,'3','Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(20,33,'InfoBlock',3,'4','Gago: Algunos van a tener que salir, pero yo me quedo','Destácado','Me voy a quedar está año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(21,34,'Image',2,'1','Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(22,34,'InfoBlock',4,'2','Sería dificil decirle no al Manchestár','Titular','Rafael van der Vaart se mostró dispuestá a ser traspasado al Manchestár United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(23,34,'Image',1,'3','Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(24,34,'InfoBlock',3,'4','Gago: Algunos van a tener que salir, pero yo me quedo','Destácado','Me voy a quedar está año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(25,35,'Image',2,'1','Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(26,35,'InfoBlock',4,'2','Sería dificil decirle no al Manchestár','Titular','Rafael van der Vaart se mostró dispuestá a ser traspasado al Manchestár United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(27,35,'Image',1,'3','Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(28,35,'InfoBlock',3,'4','Gago: Algunos van a tener que salir, pero yo me quedo','Destácado','Me voy a quedar está año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(29,36,'Image',2,'1','Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(30,36,'InfoBlock',4,'2','Sería dificil decirle no al Manchestár','Titular','Rafael van der Vaart se mostró dispuestá a ser traspasado al Manchestár United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(31,36,'Image',1,'3','Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(32,36,'InfoBlock',3,'4','Gago: Algunos van a tener que salir, pero yo me quedo','Destácado','Me voy a quedar está año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(33,37,'Image',2,'1','Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(34,37,'InfoBlock',4,'2','Sería dificil decirle no al Manchestár','Titular','Rafael van der Vaart se mostró dispuestá a ser traspasado al Manchestár United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(35,37,'Image',1,'3','Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(36,37,'InfoBlock',3,'4','Gago: Algunos van a tener que salir, pero yo me quedo','Destácado','Me voy a quedar está año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(37,38,'Image',2,'1','Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(38,38,'InfoBlock',4,'2','Sería dificil decirle no al Manchestár','Titular','Rafael van der Vaart se mostró dispuestá a ser traspasado al Manchestár United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(39,38,'Image',1,'3','Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(40,38,'InfoBlock',3,'4','Gago: Algunos van a tener que salir, pero yo me quedo','Destácado','Me voy a quedar está año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(41,39,'Image',2,'1','Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(42,39,'InfoBlock',4,'2','Sería dificil decirle no al Manchestár','Titular','Rafael van der Vaart se mostró dispuestá a ser traspasado al Manchestár United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(43,39,'Image',1,'3','Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(44,39,'InfoBlock',3,'4','Gago: Algunos van a tener que salir, pero yo me quedo','Destácado','Me voy a quedar está año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(45,40,'Image',2,'1','Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(46,40,'InfoBlock',4,'2','Sería dificil decirle no al Manchestár','Titular','Rafael van der Vaart se mostró dispuestá a ser traspasado al Manchestár United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(47,40,'Image',1,'3','Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(48,40,'InfoBlock',3,'4','Gago: Algunos van a tener que salir, pero yo me quedo','Destácado','Me voy a quedar está año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(49,41,'Image',2,'1','Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(50,41,'InfoBlock',4,'2','Sería dificil decirle no al Manchestár','Titular','Rafael van der Vaart se mostró dispuestá a ser traspasado al Manchestár United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(51,41,'Image',1,'3','Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(52,41,'InfoBlock',3,'4','Gago: Algunos van a tener que salir, pero yo me quedo','Destácado','Me voy a quedar está año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(53,42,'Image',2,'1','Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(54,42,'InfoBlock',4,'2','Sería dificil decirle no al Manchestár','Titular','Rafael van der Vaart se mostró dispuestá a ser traspasado al Manchestár United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(55,42,'Image',1,'3','Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(56,42,'InfoBlock',3,'4','Gago: Algunos van a tener que salir, pero yo me quedo','Destácado','Me voy a quedar está año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(57,43,'Image',2,'1','Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(58,43,'InfoBlock',4,'2','Sería dificil decirle no al Manchestár','Titular','Rafael van der Vaart se mostró dispuestá a ser traspasado al Manchestár United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(59,43,'Image',1,'3','Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(60,43,'InfoBlock',3,'4','Gago: Algunos van a tener que salir, pero yo me quedo','Destácado','Me voy a quedar está año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(61,44,'Image',2,'1','Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(62,44,'InfoBlock',4,'2','Sería dificil decirle no al Manchestár','Titular','Rafael van der Vaart se mostró dispuestá a ser traspasado al Manchestár United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(63,44,'Image',1,'3','Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(64,44,'InfoBlock',3,'4','Gago: Algunos van a tener que salir, pero yo me quedo','Destácado','Me voy a quedar está año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(65,45,'Image',2,'1','Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(66,45,'InfoBlock',4,'2','Sería dificil decirle no al Manchestár','Titular','Rafael van der Vaart se mostró dispuestá a ser traspasado al Manchestár United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(67,45,'Image',1,'3','Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(68,45,'InfoBlock',3,'4','Gago: Algunos van a tener que salir, pero yo me quedo','Destácado','Me voy a quedar está año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(69,46,'Image',2,'1','Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(70,46,'InfoBlock',4,'2','Sería dificil decirle no al Manchestár','Titular','Rafael van der Vaart se mostró dispuestá a ser traspasado al Manchestár United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(71,46,'Image',1,'3','Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(72,46,'InfoBlock',3,'4','Gago: Algunos van a tener que salir, pero yo me quedo','Destácado','Me voy a quedar está año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(73,47,'Image',2,'1','Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(74,47,'InfoBlock',4,'2','Sería dificil decirle no al Manchestár','Titular','Rafael van der Vaart se mostró dispuestá a ser traspasado al Manchestár United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(75,47,'Image',1,'3','Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(76,47,'InfoBlock',3,'4','Gago: Algunos van a tener que salir, pero yo me quedo','Destácado','Me voy a quedar está año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(77,48,'Image',2,'1','Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(78,48,'InfoBlock',4,'2','Sería dificil decirle no al Manchestár','Titular','Rafael van der Vaart se mostró dispuestá a ser traspasado al Manchestár United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(79,48,'Image',1,'3','Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(80,48,'InfoBlock',3,'4','Gago: Algunos van a tener que salir, pero yo me quedo','Destácado','Me voy a quedar está año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(81,49,'Image',2,'1','Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(82,49,'InfoBlock',4,'2','Sería dificil decirle no al Manchestár','Titular','Rafael van der Vaart se mostró dispuestá a ser traspasado al Manchestár United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(83,49,'Image',1,'3','Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(84,49,'InfoBlock',3,'4','Gago: Algunos van a tener que salir, pero yo me quedo','Destácado','Me voy a quedar está año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(85,50,'Image',2,'1','Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(86,50,'InfoBlock',4,'2','Sería dificil decirle no al Manchestár','Titular','Rafael van der Vaart se mostró dispuestá a ser traspasado al Manchestár United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(87,50,'Image',1,'3','Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(88,50,'InfoBlock',3,'4','Gago: Algunos van a tener que salir, pero yo me quedo','Destácado','Me voy a quedar está año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(89,51,'Image',2,'1','Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(90,51,'InfoBlock',4,'2','Sería dificil decirle no al Manchestár','Titular','Rafael van der Vaart se mostró dispuestá a ser traspasado al Manchestár United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(91,51,'Image',1,'3','Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(92,51,'InfoBlock',3,'4','Gago: Algunos van a tener que salir, pero yo me quedo','Destácado','Me voy a quedar está año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(93,52,'Image',2,'1','Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(94,52,'InfoBlock',4,'2','Sería dificil decirle no al Manchestár','Titular','Rafael van der Vaart se mostró dispuestá a ser traspasado al Manchestár United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(95,52,'Image',1,'3','Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(96,52,'InfoBlock',3,'4','Gago: Algunos van a tener que salir, pero yo me quedo','Destácado','Me voy a quedar está año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(97,53,'Image',2,'1','Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(98,53,'InfoBlock',4,'2','Sería dificil decirle no al Manchestár','Titular','Rafael van der Vaart se mostró dispuestá a ser traspasado al Manchestár United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(99,53,'Image',1,'3','Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(100,53,'InfoBlock',3,'4','Gago: Algunos van a tener que salir, pero yo me quedo','Destácado','Me voy a quedar está año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(101,54,'Image',2,'1','Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(102,54,'InfoBlock',4,'2','Sería dificil decirle no al Manchestár','Titular','Rafael van der Vaart se mostró dispuestá a ser traspasado al Manchestár United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(103,54,'Image',1,'3','Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(104,54,'InfoBlock',3,'4','Gago: Algunos van a tener que salir, pero yo me quedo','Destácado','Me voy a quedar está año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(105,55,'Image',2,'1','Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(106,55,'InfoBlock',4,'2','Sería dificil decirle no al Manchestár','Titular','Rafael van der Vaart se mostró dispuestá a ser traspasado al Manchestár United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(107,55,'Image',1,'3','Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(108,55,'InfoBlock',3,'4','Gago: Algunos van a tener que salir, pero yo me quedo','Destácado','Me voy a quedar está año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(109,56,'Image',2,'1','Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(110,56,'InfoBlock',4,'2','Sería dificil decirle no al Manchestár','Titular','Rafael van der Vaart se mostró dispuestá a ser traspasado al Manchestár United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(111,56,'Image',1,'3','Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(112,56,'InfoBlock',3,'4','Gago: Algunos van a tener que salir, pero yo me quedo','Destácado','Me voy a quedar está año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(113,57,'Image',2,'1','Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(114,57,'InfoBlock',4,'2','Sería dificil decirle no al Manchestár','Titular','Rafael van der Vaart se mostró dispuestá a ser traspasado al Manchestár United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(115,57,'Image',1,'3','Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(116,57,'InfoBlock',3,'4','Gago: Algunos van a tener que salir, pero yo me quedo','Destácado','Me voy a quedar está año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(117,58,'Image',2,'1','Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(118,58,'InfoBlock',4,'2','Sería dificil decirle no al Manchestár','Titular','Rafael van der Vaart se mostró dispuestá a ser traspasado al Manchestár United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(119,58,'Image',1,'3','Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(120,58,'InfoBlock',3,'4','Gago: Algunos van a tener que salir, pero yo me quedo','Destácado','Me voy a quedar está año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(125,60,'Image',2,'1','Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(126,60,'InfoBlock',4,'2','Sería dificil decirle no al Manchestár','Titular','Rafael van der Vaart se mostró dispuestá a ser traspasado al Manchestár United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(127,60,'Image',1,'3','Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(128,60,'InfoBlock',3,'4','Gago: Algunos van a tener que salir, pero yo me quedo','Destácado','Me voy a quedar está año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(129,61,'Image',2,'1','Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(130,61,'InfoBlock',4,'2','Sería dificil decirle no al Manchestár','Titular','Rafael van der Vaart se mostró dispuestá a ser traspasado al Manchestár United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(131,61,'Image',1,'3','Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(132,61,'InfoBlock',3,'4','Gago: Algunos van a tener que salir, pero yo me quedo','Destácado','Me voy a quedar está año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(133,62,'Image',2,'1','Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(134,62,'InfoBlock',4,'2','Sería dificil decirle no al Manchestár','Titular','Rafael van der Vaart se mostró dispuestá a ser traspasado al Manchestár United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(135,62,'Image',1,'3','Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(136,62,'InfoBlock',3,'4','Gago: Algunos van a tener que salir, pero yo me quedo','Destácado','Me voy a quedar está año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(181,5,'Image',5,'1','Otro erizo','left','001283958396359_erizo2.jpg',0,0,''),(182,5,'InfoBlock',7,'2','ONG Erizos sin fronteras','Titular','Si deseas participar en la defensa de los erizos de tierra para evitar su exterminio y favorecer su cría, te ofrecemos la posibilidad de ser socio de está ONG dedicada a la conservación de dicha especie animal.\r\nErizos sin fronteras nació hace ya más de t',0,0,''),(183,5,'Document',2,'3','El Zorongo sostenible','Destácado','001294351268578_EL ZORONGO SOSTENIBLE.pdf',0,0,''),(196,63,'Image',2,'1','Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(197,63,'InfoBlock',4,'2','Sería dificil decirle no al Manchestár','Titular','Rafael van der Vaart se mostró dispuestá a ser traspasado al Manchestár United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(198,63,'Image',1,'3','Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(199,63,'InfoBlock',3,'4','Gago: Algunos van a tener que salir, pero yo me quedo','Destácado','Me voy a quedar está año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(200,1,'Image',2,'1','Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(201,1,'InfoBlock',4,'2','Sería dificil decirle no al Manchestár','Titular','Rafael van der Vaart se mostró dispuestá a ser traspasado al Manchestár United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(202,1,'Image',1,'3','Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(203,1,'InfoBlock',3,'4','Gago: Algunos van a tener que salir, pero yo me quedo','Destácado','Me voy a quedar está año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(204,1,'InfoBlock',8,'5','ENVIO DE FOTOGRAFIAS','Titular','Los que deseeis mandar fotografías para su inserción en algún artículo o en alguna galería de fotos, os pedimos, por favor, que lo hagais con una resolución de 640 pixels en su lado mayor. Esto es para que no \"pesen\" demasiado a la hora de ponerlas en la ',0,0,''),(205,1,'InfoBlock',6,'6','El erizo de tierra es un animal protegido','Titular','Se recuerda a todos los vecinos que los erizos de tierra son animales protegidos y su maltrato o caza está penado por ley.\r\nLos erizos realizan una labor muy importante en nuestáos jardines y en el campo. Son animales que se alimentan de larvas y gusanos ',0,0,''),(206,1,'InfoBlock',5,'7','Criar erizos en el Zorongo','Titular','Si estás interesado en la cría de erizos, solamente tienes que poner cada noche un poco de alimento en gránulos para gatos en alguna parte de tu jardín. \r\nEl erizo sale de sus escondites una vez que ha anochecido, como una hora u hora y media después de h',0,0,''),(211,70,'InfoBlock',9,'1','Recogida de perros','Titular','A todo perro que se le vea suelto por el Zorongo, se le intentará coger con el viejo truco de echarle un trozo de carne para hacerse amigo suyo y en cuento lo tienes cerca lo trincas.\r\nY a los dueños les vamos a meter una multa pa cagarse',0,0,''),(212,71,'InfoBlock',10,'1','Próximos cortes de agua','Titular','El pesao de Ferruz está empeñado en cortar el agua cada dos por tres. Por eso, la próxima semana hay anunciados cortes de agua según el siguiente programa:\r\n\r\nLunes: De 10 a 11 de la mañana. Parcelas afectadas: 112 a 128, 149 a 175 y 201 a 216.\r\n\r\nMartes:',0,0,''),(213,72,'InfoBlock',12,'1','Vendo hormigonera eléctrica de 160 litros.','Titular','Por 50 euros. Está como nueva. Solamente hay que darle una manita de pintura y queda guapa, guapa. 97600000',0,0,''),(214,72,'InfoBlock',11,'2','Busco bicicleta vieja','Titular','Recogería gratis una bicicleta vieja, no importa estádo. Quiero hacer con ella una rueca para hilar algodón. 6789012345 ext. jej',0,0,''),(219,3,'Image',4,'1','Erizo de tierra','left','001283957468093_erizo.jpg',0,0,''),(220,3,'InfoBlock',5,'2','Criar erizos en el Zorongo','Titular','Si estás interesado en la cría de erizos, solamente tienes que poner cada noche un poco de alimento en gránulos para gatos en alguna parte de tu jardín. \r\nEl erizo sale de sus escondites una vez que ha anochecido, como una hora u hora y media después de h',NULL,NULL,''),(221,3,'Image',4,'3','Desde Atalaya','right','001290369962859_desde_atalaya1mini.jpg',0,0,''),(222,3,'InfoBlock',6,'4','El erizo de tierra es un animal protegido','Destácado','Se recuerda a todos los vecinos que los erizos de tierra son animales protegidos y su maltrato o caza está penado por ley.\r\nLos erizos realizan una labor muy importante en nuestáos jardines y en el campo. Son animales que se alimentan de larvas y gusanos ',NULL,NULL,'');
/*!40000 ALTER TABLE `newscomposition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `newspieces`
--

DROP TABLE IF EXISTS `newspieces`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `newspieces` (
  `npi_id` int(11) NOT NULL auto_increment,
  `npi_date` datetime default NULL,
  `npi_status` int(11) default '0',
  `npi_user` varchar(8) default '',
  `npi_section` varchar(16) default '',
  `npi_description` varchar(128) default '',
  `npi_show_parameters` varchar(128) default '',
  `npi_scope` int(11) default '0',
  `npi_access` int(11) default '0',
  PRIMARY KEY  (`npi_id`),
  KEY `DATE` (`npi_date`),
  KEY `USER` (`npi_user`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `newspieces`
--

LOCK TABLES `newspieces` WRITE;
/*!40000 ALTER TABLE `newspieces` DISABLE KEYS */;
INSERT INTO `newspieces` VALUES (1,'2010-08-09 12:41:21',2,'admin','DEPORTES','Noticia de Deportes corregida mucho más','colspan=3;',0,1),(2,'2010-08-20 03:51:49',2,'admin','COMPROP','Aviso de cortes de agua','',0,0),(3,'2010-09-08 05:09:53',2,'admin','COLABORACIONES','Pon erizo en tu vida','colspan=2;',0,0),(5,'2010-09-08 05:15:15',2,'admin','ATALAYA','Información de ONG','',0,0),(62,'2010-08-09 12:41:21',2,'admin','DEPORTES','Noticia de Deportes Modificada nueva','',1,1),(63,'2010-08-09 12:41:21',2,'admin','DEPORTES','Noticia de Deportes Modificada','colspan=3;',1,1),(68,'2010-11-25 07:56:56',0,'ecosys','COLABORACIONES','Velocidad inadecuada','',1,0),(69,'2010-12-09 07:49:28',0,'ecosys','COLABORACIONES','Colaboración de fecha 9/12/2010','',0,0),(70,'2011-01-14 10:02:07',2,'ecosys','ANUN_COM_PROP','Recogida de perros','',1,1),(71,'2011-01-14 10:02:54',2,'ecosys','ANUN_COM_PROP','Anuncio comunidad','',1,1),(72,'2011-01-14 10:19:50',2,'ecosys','ANUN_MTO','Anuncios particulares','',0,1);
/*!40000 ALTER TABLE `newspieces` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `páginas`
--

DROP TABLE IF EXISTS `paginas`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `paginas` (
  `pag_id` int(11) NOT NULL auto_increment,
  `pag_fecha` datetime default NULL,
  `pag_pagina` varchar(60) default '',
  `pag_usersdeny` text,
  `pag_usersallow` text,
  PRIMARY KEY  (`pag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `páginas`
--

LOCK TABLES `paginas` WRITE;
/*!40000 ALTER TABLE `paginas` DISABLE KEYS */;
/*!40000 ALTER TABLE `paginas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paises`
--

DROP TABLE IF EXISTS `paises`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `paises` (
  `pai_id` int(11) NOT NULL auto_increment,
  `pai_nombre` varchar(20) default NULL,
  `pai_abrev` varchar(3) default NULL,
  PRIMARY KEY  (`pai_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `paises`
--

LOCK TABLES `paises` WRITE;
/*!40000 ALTER TABLE `paises` DISABLE KEYS */;
/*!40000 ALTER TABLE `paises` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `roles` (
  `rol_id` int(11) NOT NULL auto_increment,
  `rol_role` varchar(40) default '',
  `rol_description` varchar(80) default '',
  PRIMARY KEY  (`rol_id`),
  UNIQUE KEY `ROLE` (`rol_role`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'regularRole','normal'),(2,'adminRole','administrador'),(3,'newsManager','Gestár de Noticias'),(4,'playerRole','Participante en Eventos');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sections`
--

DROP TABLE IF EXISTS `sections`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `sections` (
  `sec_id` varchar(16) NOT NULL,
  `sec_name` varchar(48) default 'Not defined',
  `sec_authorized_roles` text,
  `sec_group` varchar(8) default NULL,
  PRIMARY KEY  (`sec_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `sections`
--

LOCK TABLES `sections` WRITE;
/*!40000 ALTER TABLE `sections` DISABLE KEYS */;
INSERT INTO `sections` VALUES ('ANUN_ADMIN','Anuncios Administración','adminRole,newsManager','211'),('ANUN_COM_PROP','Anuncios Comunidad Propietarios','adminRole,newsManager','201'),('ANUN_MTO','Anuncios Mantenimiento','adminRole,newsManager','212'),('ANUN_PENA','Anuncios Peña y Realización','adminRole,newsManager','202'),('ATALAYA','Asociación Vecinos Atalaya','regularRole,newsManager',NULL),('COLABORACIONES','Colaboraciones de vecinos','',NULL),('COMPROP','Comunidad Propietarios','adminRole,newsManager,regularRole',NULL),('DEPORTES','DEPORTES','regularRole,newsManager',NULL),('ENTIDAD','Entidad de Conservación','adminRole,newsManager',NULL),('REALIZACION','REALIZACION','adminRole,newsManager',NULL);
/*!40000 ALTER TABLE `sections` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userroles`
--

DROP TABLE IF EXISTS `userroles`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `userroles` (
  `usr_id` int(11) NOT NULL auto_increment,
  `usr_estádo` int(11) default '0',
  `usu_user` varchar(8) default '',
  `usr_role` varchar(40) default '',
  `usr_description` varchar(80) default '',
  PRIMARY KEY  (`usr_id`),
  UNIQUE KEY `USERROLE` (`usu_user`,`usr_role`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `userroles`
--

LOCK TABLES `userroles` WRITE;
/*!40000 ALTER TABLE `userroles` DISABLE KEYS */;
INSERT INTO `userroles` VALUES (19,0,'ecosys','adminRole',''),(20,0,'ecosys','regularRole',''),(21,0,'admin','adminRole',''),(23,0,'p89p89','regularRole',''),(24,0,'p90p90','playerRole',''),(35,0,'p88p88','playerRole',''),(36,0,'p88p88','regularRole','');
/*!40000 ALTER TABLE `userroles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `users` (
  `usu_id` int(11) NOT NULL auto_increment,
  `usu_fecha` datetime default NULL,
  `usu_estádo` int(11) default '0',
  `usu_category` int(11) default '0',
  `usu_user` varchar(8) default '',
  `usu_nombre` varchar(40) default '',
  `usu_password` varchar(8) default '',
  `usu_email` varchar(60) default '',
  `usu_libre1` varchar(16) default '',
  `usu_libre2` varchar(60) default '',
  `usu_libre3` varchar(250) default '',
  PRIMARY KEY  (`usu_id`),
  UNIQUE KEY `USERCODE` (`usu_user`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2008-12-21 00:00:00',2,2,'ecosys','rafaferruz','ecosys','r2004@ecosysw.com','','','0'),(2,'2008-12-20 00:00:00',0,2,'admin','rafa ferruz','07021951','anonimo260@hotmail.com','','',''),(3,'2008-12-27 00:00:00',0,1,'ofiZoron','Oficina Zorongo','Zo701430','zorongo@zorongo.es','','','0'),(4,'2010-11-14 00:00:00',0,0,'p88p88','Parcela 88','zorros','p88@p88.com','null','null','null'),(5,'2010-11-14 00:00:00',0,0,'p89p89','parcela 89','p89p89','p89@p88.com','null','null','null'),(6,'2010-11-14 00:00:00',0,0,'p90p90','parcela 90','p90p90','p90@p88.com','null','null','null');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-01-23 20:50:15
