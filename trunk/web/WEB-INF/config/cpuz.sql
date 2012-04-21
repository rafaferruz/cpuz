--
-- Table structure for table `config`
--

CREATE TABLE `config` (
  `cfg_id` int(11) NOT NULL default '0',
  `cfg_visitas` int(11) default '0',
  PRIMARY KEY  (`cfg_id`)
);

--
-- Table structure for table `doclogger`
--

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
);

--
-- Table structure for table `docmanager`
--

CREATE TABLE `docmanager` (
  `dom_id` int(11) NOT NULL auto_increment,
  `dom_reference_repository` varchar(128) default '',
  `dom_reference_creator` varchar(128) default '',
  `dom_date` datetime default NULL,
  `dom_user` varchar(8) default '',
  `dom_size` varchar(32) default '',
  PRIMARY KEY  (`dom_id`)
);

--
-- Table structure for table `images`
--

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
);

--
-- Table structure for table `images`
--

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
);

--
-- Table structure for table `infoblocks`
--

CREATE TABLE `infoblocks` (
  `inb_id` int(11) NOT NULL auto_increment,
  `inb_date` datetime default NULL,
  `inb_status` int(11) default '0',
  `inb_user` varchar(8) default '',
  `inb_type` varchar(16) default '',
  `inb_header` varchar(128) default '',
  `inb_body` text,
  `inb_scope` int(11) default '0',
  PRIMARY KEY  (`inb_id`),
  KEY `DATE` (`inb_date`),
  KEY `USER` (`inb_user`)
);

--
-- Table structure for table `newscomposition`
--

CREATE TABLE `newscomposition` (
  `nco_composition_id` int(11) NOT NULL auto_increment,
  `nco_npi_id` int(11) NOT NULL,
  `nco_component_type` varchar(16) default '',
  `nco_component_id` int(11) NOT NULL default '0',
  `nco_order` int(11) default '0',
  `nco_header_alternate` varchar(128) default '',
  `nco_header_style` varchar(16) default '',
  `nco_body_abstract` varchar(256) default '',
  `nco_image_high` int(11) default '0',
  `nco_image_width` int(11) default '0',
  `nco_linked_element` varchar(256) default '',
  PRIMARY KEY  (`nco_composition_id`),
  KEY `nco_order` (`nco_npi_id`,`nco_order`)
);

--
-- Table structure for table `newspieces`
--

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
);

--
-- Table structure for table `páginas`
--

CREATE TABLE `paginas` (
  `pag_id` int(11) NOT NULL auto_increment,
  `pag_fecha` datetime default NULL,
  `pag_pagina` varchar(60) default '',
  `pag_usersdeny` text,
  `pag_usersallow` text,
  PRIMARY KEY  (`pag_id`)
);

--
-- Table structure for table `paises`
--

CREATE TABLE `paises` (
  `pai_id` int(11) NOT NULL auto_increment,
  `pai_nombre` varchar(20) default NULL,
  `pai_abrev` varchar(3) default NULL,
  PRIMARY KEY  (`pai_id`)
);

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `rol_id` int(11) NOT NULL auto_increment,
  `rol_role` varchar(40) default '',
  `rol_description` varchar(80) default '',
  PRIMARY KEY  (`rol_id`),
  UNIQUE KEY `ROLE` (`rol_role`)
);

--
-- Table structure for table `sections`
--

CREATE TABLE `sections` (
  `sec_id` varchar(16) NOT NULL auto_increment,
  `sec_name` varchar(48) default 'Not defined',
  `sec_authorized_roles` text,
  `sec_group` varchar(8),
  PRIMARY KEY  (`sec_id`)
);

--
-- Table structure for table `userroles`
--

CREATE TABLE `userroles` (
  `usr_id` int(11) NOT NULL auto_increment,
  `usr_status` int(11) default '0',
  `usu_user` varchar(8) default '',
  `usr_role` varchar(40) default '',
  `usr_description` varchar(80) default '',
  PRIMARY KEY  (`usr_id`),
  UNIQUE KEY `USERROLE` (`usu_user`,`usr_role`)
);

--
-- Table structure for table `users`
--

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
);

--
-- Table structure for table `bugs`
--
CREATE TABLE `bugs` (
  `bug_id` int(11) NOT NULL auto_increment,
  `bug_date` datetime default NULL,
  `bug_status` int(11) default '0',             -- 0=created; 1=incourse; 2=ended
  `bug_user` varchar(8) default '',
  `bug_priority` int(11) default '0',           -- 0=minimum; 10=maximum
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
