-- MySQL dump 10.13  Distrib 5.5.14, for Win32 (x86)
--
-- Host: localhost    Database: cpuz
-- ------------------------------------------------------
-- Server version	5.5.14

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
-- Table structure for table `bugs`
--

DROP TABLE IF EXISTS `bugs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bugs` (
  `bug_id` int(11) NOT NULL AUTO_INCREMENT,
  `bug_date` datetime DEFAULT NULL,
  `bug_status` int(11) DEFAULT '0',
  `bug_user` varchar(8) DEFAULT '',
  `bug_priority` int(11) DEFAULT '0',
  `bug_type` varchar(16) DEFAULT '',
  `bug_application` varchar(16) DEFAULT '',
  `bug_header` varchar(128) DEFAULT '',
  `bug_body` text,
  PRIMARY KEY (`bug_id`),
  KEY `DATE` (`bug_date`),
  KEY `PRORITY` (`bug_priority`),
  KEY `TYPE` (`bug_type`),
  KEY `USER` (`bug_user`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bugs`
--

LOCK TABLES `bugs` WRITE;
/*!40000 ALTER TABLE `bugs` DISABLE KEYS */;
INSERT INTO `bugs` VALUES (1,'2011-02-23 11:00:35',1,'webcpuz',7,'enhancement','cpuz','Publicar algo','pppppppppppppppppppppppppp');
/*!40000 ALTER TABLE `bugs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `config`
--

DROP TABLE IF EXISTS `config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `config` (
  `cfg_id` int(11) NOT NULL DEFAULT '0',
  `cfg_accesses` int(10) DEFAULT NULL,
  PRIMARY KEY (`cfg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `config`
--

LOCK TABLES `config` WRITE;
/*!40000 ALTER TABLE `config` DISABLE KEYS */;
INSERT INTO `config` VALUES (1,987);
/*!40000 ALTER TABLE `config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contacts`
--

DROP TABLE IF EXISTS `contacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contacts` (
  `con_id` int(11) NOT NULL AUTO_INCREMENT,
  `con_date` datetime DEFAULT NULL,
  `con_status` int(11) DEFAULT '0',
  `con_user` varchar(8) DEFAULT '',
  `con_target` varchar(16) DEFAULT '',
  `con_header` varchar(128) DEFAULT '',
  `con_body` text,
  `con_email` varchar(64) DEFAULT '',
  PRIMARY KEY (`con_id`),
  KEY `DATE` (`con_date`),
  KEY `TARGET` (`con_target`),
  KEY `USER` (`con_user`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contacts`
--

LOCK TABLES `contacts` WRITE;
/*!40000 ALTER TABLE `contacts` DISABLE KEYS */;
INSERT INTO `contacts` VALUES (1,'2011-02-25 07:07:05',NULL,'','Admin. Web','Mi primer mensaje','Este es mi primer mensaje.\r\n\r\nEstamos tirando el agua. ',NULL),(2,'2011-02-26 09:07:17',NULL,'','Admin. Web','otro','otro','');
/*!40000 ALTER TABLE `contacts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doclogger`
--

DROP TABLE IF EXISTS `doclogger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `doclogger` (
  `dol_id` int(11) NOT NULL AUTO_INCREMENT,
  `dol_id_dom` int(11) NOT NULL DEFAULT '0',
  `dol_reference_repository` varchar(128) DEFAULT '',
  `dol_logged_action` varchar(16) DEFAULT '',
  `dol_date_action` datetime DEFAULT NULL,
  `dol_user` varchar(8) DEFAULT '',
  `dol_url` varchar(256) DEFAULT '',
  `dol_document_use` varchar(128) DEFAULT '',
  PRIMARY KEY (`dol_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `docmanager` (
  `dom_id` int(11) NOT NULL AUTO_INCREMENT,
  `dom_reference_repository` varchar(128) DEFAULT '',
  `dom_reference_creator` varchar(128) DEFAULT '',
  `dom_date` datetime DEFAULT NULL,
  `dom_user` varchar(8) DEFAULT '',
  `dom_size` varchar(32) DEFAULT '',
  PRIMARY KEY (`dom_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `documents` (
  `doc_id` int(11) NOT NULL AUTO_INCREMENT,
  `doc_date` datetime DEFAULT NULL,
  `doc_user` varchar(8) DEFAULT '',
  `doc_user_reference` varchar(64) DEFAULT '',
  `doc_filename` varchar(64) DEFAULT '',
  `doc_repository_reference` varchar(64) DEFAULT '',
  `doc_scope` int(11) DEFAULT '0',
  PRIMARY KEY (`doc_id`),
  KEY `DATE` (`doc_date`),
  KEY `USER` (`doc_user`),
  CONSTRAINT `documents_ibfk_1` FOREIGN KEY (`doc_user`) REFERENCES `users` (`usu_user`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documents`
--

LOCK TABLES `documents` WRITE;
/*!40000 ALTER TABLE `documents` DISABLE KEYS */;
INSERT INTO `documents` VALUES (1,'2010-12-28 10:15:34','ecosys','Nota en PDF','Indice Taller Javascript.rtf','001293570966140_Indice Taller Javascript.rtf',0),(2,'2011-01-06 11:00:06','ecosys','El Zorongo sostenible','EL ZORONGO SOSTENIBLE.pdf','001294351268578_EL ZORONGO SOSTENIBLE.pdf',0),(3,'2011-01-26 08:13:35','ecosys','Informe del Agua','Informe Agua Zorongo 2010.doc','001296069301171_Informe Agua Zorongo 2010.doc',1);
/*!40000 ALTER TABLE `documents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `images`
--

DROP TABLE IF EXISTS `images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `images` (
  `img_id` int(11) NOT NULL AUTO_INCREMENT,
  `img_date` datetime DEFAULT NULL,
  `img_user` varchar(8) DEFAULT '',
  `img_user_reference` varchar(64) DEFAULT '',
  `img_filename` varchar(64) DEFAULT '',
  `img_repository_reference` varchar(64) DEFAULT '',
  `img_scope` int(11) DEFAULT '0',
  PRIMARY KEY (`img_id`),
  KEY `DATE` (`img_date`),
  KEY `USER` (`img_user`),
  CONSTRAINT `images_ibfk_1` FOREIGN KEY (`img_user`) REFERENCES `users` (`usu_user`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `infoblocks` (
  `inb_id` int(11) NOT NULL AUTO_INCREMENT,
  `inb_date` datetime DEFAULT NULL,
  `inb_status` int(11) DEFAULT '0',
  `inb_user` varchar(8) DEFAULT '',
  `inb_type` varchar(16) DEFAULT '',
  `inb_header` varchar(128) DEFAULT '',
  `inb_body` text,
  `inb_scope` int(11) DEFAULT '0',
  PRIMARY KEY (`inb_id`),
  KEY `DATE` (`inb_date`),
  KEY `USER` (`inb_user`),
  CONSTRAINT `infoblocks_ibfk_1` FOREIGN KEY (`inb_user`) REFERENCES `users` (`usu_user`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `infoblocks`
--

LOCK TABLES `infoblocks` WRITE;
/*!40000 ALTER TABLE `infoblocks` DISABLE KEYS */;
INSERT INTO `infoblocks` VALUES (2,'2010-08-07 09:18:26',2,'admin','Subtítulo','GENERICO','Vecinal',1),(3,'2010-08-20 06:43:17',2,'admin','Destacado','Gago: Algunos van a tener que salir, pero yo me quedo','Me voy a quedar este año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos van a tener que salir, pero yo me quedo. Lo que salió publicado en todos lados me sorprendió; yo había hablado con el técnico y con Valdano, comentó.',0),(4,'2010-08-20 07:00:28',2,'admin','Titular','Sería dificil decirle no al Manchester','Rafael van der Vaart se mostró dispuesto a ser traspasado al Manchester United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés, posible descarte blanco en una plantilla con cuatro mediapuntas, señaló que sería difícil decirle que no al United con la situación que tengo en el Madrid. Alex Ferguson es un gran entrenador y yo nunca me comprometería con un equipo que no tuviera opciones de ganar títulos. En el Manchester alcanzar el éxito es posible y ese es un factor muy importante para mí en este momento de carrera\r\n\r\nVan der Vaart dijo que no jugará en un equipo español que no sea el Madrid: Si tengo que marcharme sólo hay club que no significaría un paso atrás: el Manchester United. Si Mourinho me deja claro que no jugaré mucho, veo mi futuro en el fútbol inglés, concluyó.',0),(5,'2010-09-08 04:48:37',2,'admin','Titular','Criar erizos en el Zorongo','Si estás interesado en la cría de erizos, solamente tienes que poner cada noche un poco de alimento en gránulos para gatos en alguna parte de tu jardín. \r\nEl erizo sale de sus escondites una vez que ha anochecido, como una hora u hora y media después de hacerse de noche, y tarde o temprano irá a comer del alimento que le has puesto.\r\nSi deseas observarlos, tienes que estar atento a la hora que hemos indicado y acercarte hasta el lugar que le has puesto el alimento con una linterna.\r\nCuando detectan que un extraño se acerca, el erizo se aplana contra el suelo no mostrando su cabeza ni sus extremidades, ofreciendo tan sólo sus púas, que lo recubren en su totalidad.',1),(6,'2010-09-08 04:56:47',2,'admin','Titular','El erizo de tierra es un animal protegido','Se recuerda a todos los vecinos que los erizos de tierra son animales protegidos y su maltrato o caza está penado por ley.\r\nLos erizos realizan una labor muy importante en nuestros jardines y en el campo. Son animales que se alimentan de larvas y gusanos que capturan escarbando la tierra por lo que controlan las plagas de insectos perjudiciales para los cultivos.\r\n',0),(7,'2010-09-08 05:05:57',2,'admin','Titular','ONG Erizos sin fronteras','Si deseas participar en la defensa de los erizos de tierra para evitar su exterminio y favorecer su cría, te ofrecemos la posibilidad de ser socio de esta ONG dedicada a la conservación de dicha especie animal.\r\nErizos sin fronteras nació hace ya más de tres días y desde entonces hemos logrado que la población de erizos pasara de unas 4 parejas a más de 8 millones de ejemplares que se encuentran censados y reciben una renta mensual en especies (alimentos, jaulas dignas con spa para erizos, servicios sanitarios, etc.) de aproximadamente 500 euros mensuales por erizo.\r\nAdvertimos que solamente puedes ser colaborador y que no vale apuntarse a ser erizo, que hay gente muy lista.',1),(8,'2010-11-17 12:00:00',2,'ecosys','Titular','ENVIO DE FOTOGRAFIAS','Los que deseeis mandar fotografías para su inserción en algún artículo o en alguna galería de fotos, os pedimos, por favor, que lo hagais con una resolución de 640 pixels en su lado mayor. Esto es para que no \"pesen\" demasiado a la hora de ponerlas en la página web y sean lentas de visualizar; además, el servidor no es un pozo sin fondo y tiene una capacidad limitada. El que desee tener las originales con mayor resolución, que se ponga en contacto con el autor origen de las mismas y que se las pida de forma particular.',1),(9,'2011-01-14 09:37:02',2,'ecosys','Titular','Recogida de perros','A todo perro que se le vea suelto por el Zorongo, se le intentará coger con el viejo truco de echarle un trozo de carne para hacerse amigo suyo y en cuento lo tienes cerca lo trincas.\r\nY a los dueños les vamos a meter una multa pa cagarse.',1),(10,'2011-01-14 09:45:11',2,'ecosys','Titular','Próximos cortes de agua','El pesao de Ferruz está empeñado en cortar el agua cada dos por tres. Por eso, la próxima semana hay anunciados cortes de agua según el siguiente programa:\r\n\r\nLunes: De 10 a 11 de la mañana. Parcelas afectadas: 112 a 128, 149 a 175 y 201 a 216.\r\n\r\nMartes: De 10 a 11 de la mañana. Parcelas afectadas: Un montón.\r\n\r\nY así sucesivamente.',1),(11,'2011-01-14 09:56:55',2,'ecosys','Titular','Busco bicicleta vieja','Recogería gratis una bicicleta vieja, no importa estado. Quiero hacer con ella una rueca para hilar algodón. 6789012345 ext. jeje',0),(12,'2011-01-14 09:59:09',0,'ecosys','Titular','Vendo hormigonera eléctrica de 160 litros.','Por 50 euros. Está como nueva. Solamente hay que darle una manita de pintura y queda guapa, guapa. 976000001',0),(13,'2011-02-08 07:54:48',2,'webcpuz','Titular','Titular de bloque','Este es un pequeño tutorial on-line para aprender a crear una noticia en nuestra web.\r\n\r\nLo primero que hay que crear es un Bloque de Información. Pulsar en el menú GESTION_WEB y opción Bloques de Información. Aparecerá una lista de Bloques actualmente creados. Pulsar Nuevo.\r\n\r\nEn la ventana de Creación, marcar Estado como Autorizado, Tipo Titular como Titular, en el campo Titular se debe escribir el titular de la noticia y en el contenido se debe escribir lo que se quiere decir. Por último, el Ambito debe ponerse a Universal si se quiere que el bloque lo pueda leer todo el mundo o Vecinos si es solamente leible por los vecinos registrados del Zorongo. Para finalizar, pulsar Guardar.\r\n\r\nEl Bloque de Información deberá aparecer en la lista de Bloques.\r\n\r\nCon esta explicación no se consigue publicar una noticia en la web. El proceso completo lo veremos en partes sucesivas, pero hay que coger un poco de práctica creando Bloques.\r\n\r\nFin de la primera parte.',2),(14,'2011-02-15 10:44:23',2,'aperez','Titular','Titular del usuario aperez','Este es el contenido',1),(15,'2011-02-15 10:45:37',0,'aperez','Titular','Titular del usuario aperez','Contenido de aperez',1),(16,'2011-02-15 10:56:03',0,'aperez','Titular','aaaaaaaaaaaaa','aaaaaaaaaaaaaaaaaaaaaaa',1);
/*!40000 ALTER TABLE `infoblocks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `newscomposition`
--

DROP TABLE IF EXISTS `newscomposition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `newscomposition` (
  `nco_composition_id` int(11) NOT NULL AUTO_INCREMENT,
  `nco_npi_id` int(11) NOT NULL,
  `nco_component_type` varchar(16) DEFAULT '',
  `nco_component_id` int(11) NOT NULL DEFAULT '0',
  `nco_order` int(11) DEFAULT '0',
  `nco_header_alternate` varchar(128) DEFAULT '',
  `nco_header_style` varchar(16) DEFAULT '',
  `nco_body_abstract` varchar(256) DEFAULT '',
  `nco_image_high` int(11) DEFAULT '0',
  `nco_image_width` int(11) DEFAULT '0',
  `nco_linked_element` varchar(256) DEFAULT '',
  PRIMARY KEY (`nco_composition_id`),
  KEY `NCO_ORDER` (`nco_npi_id`,`nco_order`)
) ENGINE=InnoDB AUTO_INCREMENT=232 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `newscomposition`
--

LOCK TABLES `newscomposition` WRITE;
/*!40000 ALTER TABLE `newscomposition` DISABLE KEYS */;
INSERT INTO `newscomposition` VALUES (4,2,'InfoBlock',2,1,'FIESTAS DEL ZORONGO','Subtítulo','Consulta el programa de fiestas en el enlace.',0,0,''),(7,2,'InfoBlock',2,2,'GENERICO','Subtítulo','Se avisa de cortes de agua.',0,0,''),(10,2,'Image',3,3,'Cuarta imagen','left','001282558142156_ff03.jpg',0,0,''),(13,4,'Image',5,1,'Otro erizo','left','001283958396359_erizo2.jpg',0,0,''),(14,4,'InfoBlock',6,2,'El erizo de tierra es un animal protegido','Titular','Se recuerda a todos los vecinos que los erizos de tierra son animales protegidos y su maltrato o caza está penado por ley.\r\nLos erizos realizan una labor muy importante en nuestros jardines y en el campo. Son animales que se alimentan de larvas y gusanos ',0,0,''),(17,33,'Image',2,1,'Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(18,33,'InfoBlock',4,2,'Sería dificil decirle no al Manchester','Titular','Rafael van der Vaart se mostró dispuesto a ser traspasado al Manchester United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(19,33,'Image',1,3,'Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(20,33,'InfoBlock',3,4,'Gago: Algunos van a tener que salir, pero yo me quedo','Destacado','Me voy a quedar este año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(21,34,'Image',2,1,'Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(22,34,'InfoBlock',4,2,'Sería dificil decirle no al Manchester','Titular','Rafael van der Vaart se mostró dispuesto a ser traspasado al Manchester United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(23,34,'Image',1,3,'Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(24,34,'InfoBlock',3,4,'Gago: Algunos van a tener que salir, pero yo me quedo','Destacado','Me voy a quedar este año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(25,35,'Image',2,1,'Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(26,35,'InfoBlock',4,2,'Sería dificil decirle no al Manchester','Titular','Rafael van der Vaart se mostró dispuesto a ser traspasado al Manchester United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(27,35,'Image',1,3,'Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(28,35,'InfoBlock',3,4,'Gago: Algunos van a tener que salir, pero yo me quedo','Destacado','Me voy a quedar este año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(29,36,'Image',2,1,'Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(30,36,'InfoBlock',4,2,'Sería dificil decirle no al Manchester','Titular','Rafael van der Vaart se mostró dispuesto a ser traspasado al Manchester United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(31,36,'Image',1,3,'Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(32,36,'InfoBlock',3,4,'Gago: Algunos van a tener que salir, pero yo me quedo','Destacado','Me voy a quedar este año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(33,37,'Image',2,1,'Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(34,37,'InfoBlock',4,2,'Sería dificil decirle no al Manchester','Titular','Rafael van der Vaart se mostró dispuesto a ser traspasado al Manchester United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(35,37,'Image',1,3,'Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(36,37,'InfoBlock',3,4,'Gago: Algunos van a tener que salir, pero yo me quedo','Destacado','Me voy a quedar este año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(37,38,'Image',2,1,'Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(38,38,'InfoBlock',4,2,'Sería dificil decirle no al Manchester','Titular','Rafael van der Vaart se mostró dispuesto a ser traspasado al Manchester United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(39,38,'Image',1,3,'Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(40,38,'InfoBlock',3,4,'Gago: Algunos van a tener que salir, pero yo me quedo','Destacado','Me voy a quedar este año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(41,39,'Image',2,1,'Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(42,39,'InfoBlock',4,2,'Sería dificil decirle no al Manchester','Titular','Rafael van der Vaart se mostró dispuesto a ser traspasado al Manchester United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(43,39,'Image',1,3,'Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(44,39,'InfoBlock',3,4,'Gago: Algunos van a tener que salir, pero yo me quedo','Destacado','Me voy a quedar este año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(45,40,'Image',2,1,'Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(46,40,'InfoBlock',4,2,'Sería dificil decirle no al Manchester','Titular','Rafael van der Vaart se mostró dispuesto a ser traspasado al Manchester United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(47,40,'Image',1,3,'Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(48,40,'InfoBlock',3,4,'Gago: Algunos van a tener que salir, pero yo me quedo','Destacado','Me voy a quedar este año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(49,41,'Image',2,1,'Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(50,41,'InfoBlock',4,2,'Sería dificil decirle no al Manchester','Titular','Rafael van der Vaart se mostró dispuesto a ser traspasado al Manchester United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(51,41,'Image',1,3,'Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(52,41,'InfoBlock',3,4,'Gago: Algunos van a tener que salir, pero yo me quedo','Destacado','Me voy a quedar este año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(53,42,'Image',2,1,'Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(54,42,'InfoBlock',4,2,'Sería dificil decirle no al Manchester','Titular','Rafael van der Vaart se mostró dispuesto a ser traspasado al Manchester United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(55,42,'Image',1,3,'Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(56,42,'InfoBlock',3,4,'Gago: Algunos van a tener que salir, pero yo me quedo','Destacado','Me voy a quedar este año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(57,43,'Image',2,1,'Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(58,43,'InfoBlock',4,2,'Sería dificil decirle no al Manchester','Titular','Rafael van der Vaart se mostró dispuesto a ser traspasado al Manchester United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(59,43,'Image',1,3,'Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(60,43,'InfoBlock',3,4,'Gago: Algunos van a tener que salir, pero yo me quedo','Destacado','Me voy a quedar este año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(61,44,'Image',2,1,'Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(62,44,'InfoBlock',4,2,'Sería dificil decirle no al Manchester','Titular','Rafael van der Vaart se mostró dispuesto a ser traspasado al Manchester United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(63,44,'Image',1,3,'Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(64,44,'InfoBlock',3,4,'Gago: Algunos van a tener que salir, pero yo me quedo','Destacado','Me voy a quedar este año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(65,45,'Image',2,1,'Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(66,45,'InfoBlock',4,2,'Sería dificil decirle no al Manchester','Titular','Rafael van der Vaart se mostró dispuesto a ser traspasado al Manchester United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(67,45,'Image',1,3,'Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(68,45,'InfoBlock',3,4,'Gago: Algunos van a tener que salir, pero yo me quedo','Destacado','Me voy a quedar este año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(69,46,'Image',2,1,'Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(70,46,'InfoBlock',4,2,'Sería dificil decirle no al Manchester','Titular','Rafael van der Vaart se mostró dispuesto a ser traspasado al Manchester United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(71,46,'Image',1,3,'Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(72,46,'InfoBlock',3,4,'Gago: Algunos van a tener que salir, pero yo me quedo','Destacado','Me voy a quedar este año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(73,47,'Image',2,1,'Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(74,47,'InfoBlock',4,2,'Sería dificil decirle no al Manchester','Titular','Rafael van der Vaart se mostró dispuesto a ser traspasado al Manchester United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(75,47,'Image',1,3,'Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(76,47,'InfoBlock',3,4,'Gago: Algunos van a tener que salir, pero yo me quedo','Destacado','Me voy a quedar este año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(77,48,'Image',2,1,'Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(78,48,'InfoBlock',4,2,'Sería dificil decirle no al Manchester','Titular','Rafael van der Vaart se mostró dispuesto a ser traspasado al Manchester United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(79,48,'Image',1,3,'Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(80,48,'InfoBlock',3,4,'Gago: Algunos van a tener que salir, pero yo me quedo','Destacado','Me voy a quedar este año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(81,49,'Image',2,1,'Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(82,49,'InfoBlock',4,2,'Sería dificil decirle no al Manchester','Titular','Rafael van der Vaart se mostró dispuesto a ser traspasado al Manchester United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(83,49,'Image',1,3,'Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(84,49,'InfoBlock',3,4,'Gago: Algunos van a tener que salir, pero yo me quedo','Destacado','Me voy a quedar este año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(85,50,'Image',2,1,'Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(86,50,'InfoBlock',4,2,'Sería dificil decirle no al Manchester','Titular','Rafael van der Vaart se mostró dispuesto a ser traspasado al Manchester United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(87,50,'Image',1,3,'Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(88,50,'InfoBlock',3,4,'Gago: Algunos van a tener que salir, pero yo me quedo','Destacado','Me voy a quedar este año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(89,51,'Image',2,1,'Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(90,51,'InfoBlock',4,2,'Sería dificil decirle no al Manchester','Titular','Rafael van der Vaart se mostró dispuesto a ser traspasado al Manchester United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(91,51,'Image',1,3,'Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(92,51,'InfoBlock',3,4,'Gago: Algunos van a tener que salir, pero yo me quedo','Destacado','Me voy a quedar este año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(93,52,'Image',2,1,'Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(94,52,'InfoBlock',4,2,'Sería dificil decirle no al Manchester','Titular','Rafael van der Vaart se mostró dispuesto a ser traspasado al Manchester United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(95,52,'Image',1,3,'Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(96,52,'InfoBlock',3,4,'Gago: Algunos van a tener que salir, pero yo me quedo','Destacado','Me voy a quedar este año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(97,53,'Image',2,1,'Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(98,53,'InfoBlock',4,2,'Sería dificil decirle no al Manchester','Titular','Rafael van der Vaart se mostró dispuesto a ser traspasado al Manchester United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(99,53,'Image',1,3,'Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(100,53,'InfoBlock',3,4,'Gago: Algunos van a tener que salir, pero yo me quedo','Destacado','Me voy a quedar este año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(101,54,'Image',2,1,'Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(102,54,'InfoBlock',4,2,'Sería dificil decirle no al Manchester','Titular','Rafael van der Vaart se mostró dispuesto a ser traspasado al Manchester United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(103,54,'Image',1,3,'Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(104,54,'InfoBlock',3,4,'Gago: Algunos van a tener que salir, pero yo me quedo','Destacado','Me voy a quedar este año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(105,55,'Image',2,1,'Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(106,55,'InfoBlock',4,2,'Sería dificil decirle no al Manchester','Titular','Rafael van der Vaart se mostró dispuesto a ser traspasado al Manchester United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(107,55,'Image',1,3,'Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(108,55,'InfoBlock',3,4,'Gago: Algunos van a tener que salir, pero yo me quedo','Destacado','Me voy a quedar este año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(109,56,'Image',2,1,'Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(110,56,'InfoBlock',4,2,'Sería dificil decirle no al Manchester','Titular','Rafael van der Vaart se mostró dispuesto a ser traspasado al Manchester United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(111,56,'Image',1,3,'Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(112,56,'InfoBlock',3,4,'Gago: Algunos van a tener que salir, pero yo me quedo','Destacado','Me voy a quedar este año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(113,57,'Image',2,1,'Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(114,57,'InfoBlock',4,2,'Sería dificil decirle no al Manchester','Titular','Rafael van der Vaart se mostró dispuesto a ser traspasado al Manchester United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(115,57,'Image',1,3,'Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(116,57,'InfoBlock',3,4,'Gago: Algunos van a tener que salir, pero yo me quedo','Destacado','Me voy a quedar este año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(117,58,'Image',2,1,'Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(118,58,'InfoBlock',4,2,'Sería dificil decirle no al Manchester','Titular','Rafael van der Vaart se mostró dispuesto a ser traspasado al Manchester United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(119,58,'Image',1,3,'Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(120,58,'InfoBlock',3,4,'Gago: Algunos van a tener que salir, pero yo me quedo','Destacado','Me voy a quedar este año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(125,60,'Image',2,1,'Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(126,60,'InfoBlock',4,2,'Sería dificil decirle no al Manchester','Titular','Rafael van der Vaart se mostró dispuesto a ser traspasado al Manchester United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(127,60,'Image',1,3,'Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(128,60,'InfoBlock',3,4,'Gago: Algunos van a tener que salir, pero yo me quedo','Destacado','Me voy a quedar este año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(129,61,'Image',2,1,'Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(130,61,'InfoBlock',4,2,'Sería dificil decirle no al Manchester','Titular','Rafael van der Vaart se mostró dispuesto a ser traspasado al Manchester United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(131,61,'Image',1,3,'Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(132,61,'InfoBlock',3,4,'Gago: Algunos van a tener que salir, pero yo me quedo','Destacado','Me voy a quedar este año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(133,62,'Image',2,1,'Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(134,62,'InfoBlock',4,2,'Sería dificil decirle no al Manchester','Titular','Rafael van der Vaart se mostró dispuesto a ser traspasado al Manchester United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(135,62,'Image',1,3,'Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(136,62,'InfoBlock',3,4,'Gago: Algunos van a tener que salir, pero yo me quedo','Destacado','Me voy a quedar este año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(196,63,'Image',2,1,'Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(197,63,'InfoBlock',4,2,'Sería dificil decirle no al Manchester','Titular','Rafael van der Vaart se mostró dispuesto a ser traspasado al Manchester United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(198,63,'Image',1,3,'Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(199,63,'InfoBlock',3,4,'Gago: Algunos van a tener que salir, pero yo me quedo','Destacado','Me voy a quedar este año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(200,1,'Image',2,1,'Imagen de documento','right','001282578857843_ScanImage009.jpg',0,0,''),(201,1,'InfoBlock',4,2,'Sería dificil decirle no al Manchester','Titular','Rafael van der Vaart se mostró dispuesto a ser traspasado al Manchester United en unas declaraciones que hizo al Daily Express: Es un gran club y si el presunto interés que tienen en mí fuese cierto, me sentaría con ellos y les escucharía.\r\n\r\nEl holandés,',0,0,''),(202,1,'Image',1,3,'Aceras rotas','left','001282579072546_th_p1020116_mini.png',0,0,''),(203,1,'InfoBlock',3,4,'Gago: Algunos van a tener que salir, pero yo me quedo','Destacado','Me voy a quedar este año en el Madrid, ya hablé del asunto con Jorge Valdano (director deportivo del club) y Mourinho y en ningún momento me dijeron que me querían traspasar, dijo el futbolista al diario deportivo.\r\n\r\nEstoy muy cómodo y tranquilo. Algunos',0,0,''),(204,1,'InfoBlock',8,5,'ENVIO DE FOTOGRAFIAS','Titular','Los que deseeis mandar fotografías para su inserción en algún artículo o en alguna galería de fotos, os pedimos, por favor, que lo hagais con una resolución de 640 pixels en su lado mayor. Esto es para que no \"pesen\" demasiado a la hora de ponerlas en la ',0,0,''),(205,1,'InfoBlock',6,6,'El erizo de tierra es un animal protegido','Titular','Se recuerda a todos los vecinos que los erizos de tierra son animales protegidos y su maltrato o caza está penado por ley.\r\nLos erizos realizan una labor muy importante en nuestros jardines y en el campo. Son animales que se alimentan de larvas y gusanos ',0,0,''),(206,1,'InfoBlock',5,7,'Criar erizos en el Zorongo','Titular','Si estás interesado en la cría de erizos, solamente tienes que poner cada noche un poco de alimento en gránulos para gatos en alguna parte de tu jardín. \r\nEl erizo sale de sus escondites una vez que ha anochecido, como una hora u hora y media después de h',0,0,''),(211,70,'InfoBlock',9,1,'Recogida de perros','Titular','A todo perro que se le vea suelto por el Zorongo, se le intentará coger con el viejo truco de echarle un trozo de carne para hacerse amigo suyo y en cuento lo tienes cerca lo trincas.\r\nY a los dueños les vamos a meter una multa pa cagarse',0,0,''),(212,71,'InfoBlock',10,1,'Próximos cortes de agua','Titular','El pesao de Ferruz está empeñado en cortar el agua cada dos por tres. Por eso, la próxima semana hay anunciados cortes de agua según el siguiente programa:\r\n\r\nLunes: De 10 a 11 de la mañana. Parcelas afectadas: 112 a 128, 149 a 175 y 201 a 216.\r\n\r\nMartes:',0,0,''),(213,72,'InfoBlock',12,1,'Vendo hormigonera eléctrica de 160 litros.','Titular','Por 50 euros. Está como nueva. Solamente hay que darle una manita de pintura y queda guapa, guapa. 97600000',0,0,''),(214,72,'InfoBlock',11,2,'Busco bicicleta vieja','Titular','Recogería gratis una bicicleta vieja, no importa estado. Quiero hacer con ella una rueca para hilar algodón. 6789012345 ext. jej',0,0,''),(223,5,'Image',5,1,'Otro erizo','left','001283958396359_erizo2.jpg',0,0,''),(224,5,'InfoBlock',7,2,'ONG Erizos sin fronteras','Titular','Si deseas participar en la defensa de los erizos de tierra para evitar su exterminio y favorecer su cría, te ofrecemos la posibilidad de ser socio de esta ONG dedicada a la conservación de dicha especie animal.\r\nErizos sin fronteras nació hace ya más de t',0,0,''),(225,5,'Document',2,3,'El Zorongo sostenible','Destacado','001294351268578_EL ZORONGO SOSTENIBLE.pdf',0,0,''),(226,5,'Document',3,4,'Informe del Agua','Destacado','001296069301171_Informe Agua Zorongo 2010.doc',0,0,''),(227,3,'Image',4,1,'Erizo de tierra','left','001283957468093_erizo.jpg',0,0,''),(228,3,'InfoBlock',5,2,'Criar erizos en el Zorongo','Titular','Si estás interesado en la cría de erizos, solamente tienes que poner cada noche un poco de alimento en gránulos para gatos en alguna parte de tu jardín. \r\nEl erizo sale de sus escondites una vez que ha anochecido, como una hora u hora y media después de h',0,0,''),(229,3,'Image',4,3,'Desde Atalaya','right','001290369962859_desde_atalaya1mini.jpg',0,0,''),(230,3,'InfoBlock',6,4,'El erizo de tierra es un animal protegido','Destacado','Se recuerda a todos los vecinos que los erizos de tierra son animales protegidos y su maltrato o caza está penado por ley.\r\nLos erizos realizan una labor muy importante en nuestros jardines y en el campo. Son animales que se alimentan de larvas y gusanos ',0,0,''),(231,73,'InfoBlock',13,1,'Titular de bloque','Titular','Este es un pequeño tutorial on-line para aprender a crear una noticia en nuestra web.\r\n\r\nLo primero que hay que crear es un Bloque de Información. Pulsar en el menú GESTION_WEB y opción Bloques de Información. Aparecerá una lista de Bloques actualmente cr',0,0,'');
/*!40000 ALTER TABLE `newscomposition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `newspieces`
--

DROP TABLE IF EXISTS `newspieces`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `newspieces` (
  `npi_id` int(11) NOT NULL AUTO_INCREMENT,
  `npi_date` datetime DEFAULT NULL,
  `npi_status` int(11) DEFAULT '0',
  `npi_user` varchar(8) DEFAULT '',
  `npi_section` varchar(16) DEFAULT '',
  `npi_description` varchar(128) DEFAULT '',
  `npi_show_parameters` varchar(128) DEFAULT '',
  `npi_scope` int(11) DEFAULT '0',
  `npi_access` int(11) DEFAULT '0',
  PRIMARY KEY (`npi_id`),
  KEY `DATE` (`npi_date`),
  KEY `USER` (`npi_user`),
  KEY `section` (`npi_section`),
  CONSTRAINT `newspieces_ibfk_1` FOREIGN KEY (`npi_user`) REFERENCES `users` (`usu_user`),
  CONSTRAINT `newspieces_ibfk_2` FOREIGN KEY (`npi_user`) REFERENCES `users` (`usu_user`),
  CONSTRAINT `newspieces_ibfk_3` FOREIGN KEY (`npi_section`) REFERENCES `sections` (`sec_id`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `newspieces`
--

LOCK TABLES `newspieces` WRITE;
/*!40000 ALTER TABLE `newspieces` DISABLE KEYS */;
INSERT INTO `newspieces` VALUES (1,'2010-08-09 12:41:21',2,'admin','DEPORTES','Noticia de Deportes corregida mucho más','colspan=3;',0,1),(2,'2010-08-20 03:51:49',2,'admin','COMPROP','Aviso de cortes de agua','',0,0),(3,'2010-09-08 05:09:53',2,'admin','COLABORACIONES','Pon erizo en tu vida','colspan=2;',1,0),(5,'2010-09-08 05:15:15',2,'admin','ATALAYA','Información de ONG','',1,0),(62,'2010-08-09 12:41:21',2,'admin','DEPORTES','Noticia de Deportes Modificada nueva','',1,1),(63,'2010-08-09 12:41:21',2,'admin','DEPORTES','Noticia de Deportes Modificada','colspan=3;',1,1),(68,'2010-11-25 07:56:56',0,'aperez','COLABORACIONES','Velocidad inadecuada','',1,0),(69,'2010-12-09 07:49:28',0,'ecosys','COLABORACIONES','Colaboración de fecha 9/12/2010','',0,0),(70,'2011-01-14 10:02:07',2,'ecosys','ANUN_COM_PROP','Recogida de perros','',1,1),(71,'2011-01-14 10:02:54',2,'ecosys','ANUN_COM_PROP','Anuncio comunidad','',1,1),(72,'2011-01-14 10:19:50',2,'ecosys','ANUN_MTO','Anuncios particulares','',0,1),(73,'2011-02-08 07:55:51',2,'webcpuz','COLABORACIONES','Prueba de restringidos','',2,0);
/*!40000 ALTER TABLE `newspieces` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paginas`
--

DROP TABLE IF EXISTS `paginas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paginas` (
  `pag_id` int(11) NOT NULL AUTO_INCREMENT,
  `pag_fecha` datetime DEFAULT NULL,
  `pag_pagina` varchar(60) DEFAULT '',
  `pag_usersdeny` text,
  `pag_usersallow` text,
  PRIMARY KEY (`pag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paginas`
--

LOCK TABLES `paginas` WRITE;
/*!40000 ALTER TABLE `paginas` DISABLE KEYS */;
/*!40000 ALTER TABLE `paginas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paises`
--

DROP TABLE IF EXISTS `paises`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paises` (
  `pai_id` int(11) NOT NULL AUTO_INCREMENT,
  `pai_nombre` varchar(20) DEFAULT NULL,
  `pai_abrev` varchar(3) DEFAULT NULL,
  PRIMARY KEY (`pai_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `rol_id` int(11) NOT NULL AUTO_INCREMENT,
  `rol_role` varchar(40) DEFAULT '',
  `rol_description` varchar(80) DEFAULT '',
  PRIMARY KEY (`rol_id`),
  UNIQUE KEY `ROLE` (`rol_role`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'regularRole','normal'),(2,'adminRole','administrador'),(3,'newsManager','Gestor de Noticias'),(4,'playerRole','Participante en Eventos'),(5,'all','Permiso para todas secciones'),(7,'COMPROP','comunidad propietarios'),(8,'ENTIDAD','entidad conservación'),(9,'readerContact','Lector de Mensajes de Contacto');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sections`
--

DROP TABLE IF EXISTS `sections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sections` (
  `sec_id` varchar(16) NOT NULL,
  `sec_name` varchar(48) DEFAULT 'Not defined',
  `sec_authorized_roles` text,
  `sec_group` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`sec_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sections`
--

LOCK TABLES `sections` WRITE;
/*!40000 ALTER TABLE `sections` DISABLE KEYS */;
INSERT INTO `sections` VALUES ('ANUN_ADMIN','Anuncios Administración','adminRole,newsManager','211'),('ANUN_COM_PROP','Anuncios Comunidad Propietarios','adminRole,newsManager','201'),('ANUN_MTO','Anuncios Mantenimiento','adminRole,newsManager','212'),('ANUN_PENA','Anuncios Peña y Realización','adminRole,newsManager','202'),('ATALAYA','Asociación Vecinos Atalaya','regularRole,newsManager',NULL),('COLABORACIONES','Colaboraciones de vecinos','adminRole,newsManager',''),('COMPROP','Comunidad Propietarios','adminRole,newsManager,regularRole',NULL),('DEPORTES','DEPORTES','regularRole,newsManager',NULL),('ENTIDAD','Entidad de Conservación','adminRole,newsManager',NULL),('REALIZACION','REALIZACION','adminRole,newsManager',NULL);
/*!40000 ALTER TABLE `sections` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userroles`
--

DROP TABLE IF EXISTS `userroles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userroles` (
  `usr_id` int(11) NOT NULL AUTO_INCREMENT,
  `usr_status` int(11) DEFAULT '0',
  `usu_user` varchar(8) DEFAULT '',
  `usr_role` varchar(40) DEFAULT '',
  `usr_description` varchar(80) DEFAULT '',
  PRIMARY KEY (`usr_id`),
  UNIQUE KEY `USERROLE` (`usu_user`,`usr_role`),
  KEY `role` (`usr_role`),
  CONSTRAINT `userroles_ibfk_1` FOREIGN KEY (`usr_role`) REFERENCES `roles` (`rol_role`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userroles`
--

LOCK TABLES `userroles` WRITE;
/*!40000 ALTER TABLE `userroles` DISABLE KEYS */;
INSERT INTO `userroles` VALUES (19,0,'ecosys','adminRole',''),(20,0,'ecosys','regularRole',''),(21,0,'admin','adminRole',''),(23,0,'p89p89','regularRole',''),(24,0,'p90p90','playerRole',''),(35,0,'p88p88','playerRole',''),(36,0,'p88p88','regularRole',''),(38,0,'supermgr','newsManager',''),(40,0,'rgarcia','newsManager',''),(41,0,'jllafuen','newsManager',''),(43,0,'angubide','newsManager',''),(44,0,'rpalomar','newsManager',''),(52,0,'plopez','newsManager',''),(53,0,'plopez','regularRole',''),(55,0,'webcpuz','adminRole',''),(56,0,'webcpuz','regularRole',''),(60,0,'aperez','COMPROP',''),(61,0,'aperez','ENTIDAD',''),(62,0,'aperez','newsManager',''),(63,0,'vecino','regularRole',''),(64,0,'vecino','readerContact','');
/*!40000 ALTER TABLE `userroles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `usu_id` int(11) NOT NULL AUTO_INCREMENT,
  `usu_date` datetime DEFAULT NULL,
  `usu_status` int(11) DEFAULT '0',
  `usu_category` int(11) DEFAULT '0',
  `usu_user` varchar(8) DEFAULT '',
  `usu_name` varchar(40) DEFAULT '',
  `usu_password` varchar(8) DEFAULT '',
  `usu_email` varchar(60) DEFAULT '',
  PRIMARY KEY (`usu_id`),
  UNIQUE KEY `USERCODE` (`usu_user`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'2008-12-21 00:00:00',2,2,'ecosys','rafaferruz','ecosys','r2004@ecosysw.com'),(2,'2008-12-20 00:00:00',0,2,'admin','rafa ferruz','07021951','anonimo260@hotmail.com'),(3,'2008-12-27 00:00:00',0,1,'ofiZoron','Oficina Zorongo','Zo701430','zorongo@zorongo.es'),(7,'2011-01-28 00:00:00',2,2,'webcpuz','Administrador Web','webcpuz','rafaferruz@hotmail.com'),(8,'2011-01-30 00:00:00',2,1,'supermgr','Super Manager','supermgr','rafaferruz@hotmail.com'),(9,'2011-01-30 00:00:00',2,1,'aperez','Ana Pérez','5a46Mb','anaperez@gmail.com'),(10,'2011-01-30 00:00:00',2,1,'rgarcia','Rafael García','aicragr','rgarciagalvez@gmail.com'),(11,'2011-01-31 00:00:00',2,1,'jllafuen','José Luis Lafuente','neufallj','jllafuente@hotmail.com'),(12,'2011-01-31 00:00:00',2,1,'plopez','Pilar López','plopez','pilarlopez66@hotmail.com'),(13,'2011-02-01 00:00:00',2,1,'angubide','Angel Ubide','edibugna','aubide@hotmail.com'),(14,'2011-02-01 00:00:00',0,0,'rpalomar','Rosa Palomares','ramolapr','rpalomar@hotmail.com'),(15,'2011-02-08 00:00:00',2,0,'vecino','vecino','vecino','vecino@elzorongo.es');
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

-- Dump completed on 2012-04-23 18:46:18
