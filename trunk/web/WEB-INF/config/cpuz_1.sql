--
-- Table structure for table `config`
--

ALTER TABLE `config` 
  CHANGE COLUMN `cfg_id` `cfg_id` int(11) NOT NULL default '0',
  CHANGE COLUMN `cfg_visitas` `cfg_visitas` int(11) default '0'
;

--
-- Table structure for table `doclogger`
--

ALTER TABLE `doclogger` 
  CHANGE COLUMN `dol_id` `dol_id` int(11) NOT NULL auto_increment,
  CHANGE COLUMN `dol_id_dom` `dol_id_dom` int(11) NOT NULL default '0',
  CHANGE COLUMN `dol_reference_repository` `dol_reference_repository` varchar(128) default '',
  CHANGE COLUMN `dol_logged_action` `dol_logged_action` varchar(16) default '',
  CHANGE COLUMN `dol_date_action` `dol_date_action` datetime default NULL,
  CHANGE COLUMN `dol_user` `dol_user` varchar(8) default '',
  CHANGE COLUMN `dol_url` `dol_url` varchar(256) default '',
  CHANGE COLUMN `dol_document_use` `dol_document_use` varchar(128) default ''
;

--
-- Table structure for table `docmanager`
--

ALTER TABLE `docmanager` 
  CHANGE COLUMN `dom_id` `dom_id` int(11) NOT NULL auto_increment,
  CHANGE COLUMN `dom_reference_repository` `dom_reference_repository` varchar(128) default '',
  CHANGE COLUMN `dom_reference_creator` `dom_reference_creator` varchar(128) default '',
  CHANGE COLUMN `dom_date` `dom_date` datetime default NULL,
  CHANGE COLUMN `dom_user` `dom_user` varchar(8) default '',
  CHANGE COLUMN `dom_size` `dom_size` varchar(32) default ''
;

--
-- Table structure for table `images`
--

ALTER TABLE `documents` 
  CHANGE COLUMN `doc_id` `doc_id` int(11) NOT NULL auto_increment,
  CHANGE COLUMN `doc_date` `doc_date` datetime default NULL,
  CHANGE COLUMN `doc_user` `doc_user` varchar(8) default '',
  CHANGE COLUMN `doc_user_reference` `doc_user_reference` varchar(64) default '',
  CHANGE COLUMN `doc_filename` `doc_filename` varchar(64) default '',
  CHANGE COLUMN `doc_repository_reference` `doc_repository_reference` varchar(64) default '',
  CHANGE COLUMN `doc_scope` `doc_scope` int(11) default '0'
;

--
-- Table structure for table `images`
--

ALTER TABLE `images` 
  CHANGE COLUMN `img_id` `img_id` int(11) NOT NULL auto_increment,
  CHANGE COLUMN `img_date` `img_date` datetime default NULL,
  CHANGE COLUMN `img_user` `img_user` varchar(8) default '',
  CHANGE COLUMN `img_user_reference` `img_user_reference` varchar(64) default '',
  CHANGE COLUMN `img_filename` `img_filename` varchar(64) default '',
  CHANGE COLUMN `img_repository_reference` `img_repository_reference` varchar(64) default '',
  CHANGE COLUMN `img_scope` `img_scope` int(11) default '0'
;

--
-- Table structure for table `infoblocks`
--

ALTER TABLE `infoblocks` 
  CHANGE COLUMN `inb_id` `inb_id` int(11) NOT NULL auto_increment,
  CHANGE COLUMN `inb_date` `inb_date` datetime default NULL,
  CHANGE COLUMN `inb_status` `inb_status` int(11) default '0',
  CHANGE COLUMN `inb_user` `inb_user` varchar(8) default '',
  CHANGE COLUMN `inb_type` `inb_type` varchar(16) default '',
  CHANGE COLUMN `inb_header` `inb_header` varchar(128) default '',
  CHANGE COLUMN `inb_body` `inb_body` text,
  CHANGE COLUMN `inb_scope` `inb_scope` int(11) default '0'
;

--
-- Table structure for table `newscomposition`
--

ALTER TABLE `newscomposition` 
  CHANGE COLUMN `nco_composition_id` `nco_composition_id` int(11) NOT NULL auto_increment,
  CHANGE COLUMN `nco_npi_id` `nco_npi_id` int(11) NOT NULL,
  CHANGE COLUMN `nco_component_type` `nco_component_type` varchar(16) default '',
  CHANGE COLUMN `nco_component_id` `nco_component_id` int(11) NOT NULL default '0',
  CHANGE COLUMN `nco_order` `nco_order` int(11) default '0',
  CHANGE COLUMN `nco_header_alternate` `nco_header_alternate` varchar(128) default '',
  CHANGE COLUMN `nco_header_style` `nco_header_style` varchar(16) default '',
  CHANGE COLUMN `nco_body_abstract` `nco_body_abstract` varchar(256) default '',
  CHANGE COLUMN `nco_image_high` `nco_image_high` int(11) default '0',
  CHANGE COLUMN `nco_image_width` `nco_image_width` int(11) default '0',
  CHANGE COLUMN `nco_linked_element` `nco_linked_element` varchar(256) default ''
;

--
-- Table structure for table `newspieces`
--

ALTER TABLE `newspieces` 
  CHANGE COLUMN `npi_id` `npi_id` int(11) NOT NULL auto_increment,
  CHANGE COLUMN `npi_date` `npi_date` datetime default NULL,
  CHANGE COLUMN `npi_status` `npi_status` int(11) default '0',
  CHANGE COLUMN `npi_user` `npi_user` varchar(8) default '',
  CHANGE COLUMN `npi_section` `npi_section` varchar(16) default '',
  CHANGE COLUMN `npi_description` `npi_description` varchar(128) default '',
  CHANGE COLUMN `npi_show_parameters` `npi_show_parameters` varchar(128) default '',
  CHANGE COLUMN `npi_scope` `npi_scope` int(11) default '0',
  CHANGE COLUMN `npi_access` `npi_access` int(11) default '0'
;

--
-- Table structure for table `páginas`
--

ALTER TABLE `paginas` 
  CHANGE COLUMN `pag_id` `pag_id` int(11) NOT NULL auto_increment,
  CHANGE COLUMN `pag_fecha` `pag_fecha` datetime default NULL,
  CHANGE COLUMN `pag_pagina` `pag_pagina` varchar(60) default '',
  CHANGE COLUMN `pag_usersdeny` `pag_usersdeny` text,
  CHANGE COLUMN `pag_usersallow` `pag_usersallow` text
;

--
-- Table structure for table `paises`
--

ALTER TABLE `paises` 
  CHANGE COLUMN `pai_id` `pai_id` int(11) NOT NULL auto_increment,
  CHANGE COLUMN `pai_nombre` `pai_nombre` varchar(20) default NULL,
  CHANGE COLUMN `pai_abrev` `pai_abrev` varchar(3) default NULL
;

--
-- Table structure for table `roles`
--

ALTER TABLE `roles` 
  CHANGE COLUMN `rol_id` `rol_id` int(11) NOT NULL auto_increment,
  CHANGE COLUMN `rol_role` `rol_role` varchar(40) default '',
  CHANGE COLUMN `rol_description` `rol_description` varchar(80) default ''
;

--
-- Table structure for table `sections`
--

ALTER TABLE `sections` 
  CHANGE COLUMN `sec_id` `sec_id` varchar(16) NOT NULL,
  CHANGE COLUMN `sec_name` `sec_name` varchar(48) default 'Not defined',
  CHANGE COLUMN `sec_authorized_roles` `sec_authorized_roles` text,
  CHANGE COLUMN `sec_group` `sec_group` varchar(8)
;

--
-- Table structure for table `userroles`
--

ALTER TABLE `userroles` 
  CHANGE COLUMN `usr_id` `usr_id` int(11) NOT NULL auto_increment,
  CHANGE COLUMN `usr_estádo` `usr_estádo` int(11) default '0',
  CHANGE COLUMN `usu_user` `usu_user` varchar(8) default '',
  CHANGE COLUMN `usr_role` `usr_role` varchar(40) default '',
  CHANGE COLUMN `usr_description` `usr_description` varchar(80) default ''
;

--
-- Table structure for table `users`
--

ALTER TABLE `users` 
  CHANGE COLUMN `usu_id` `usu_id` int(11) NOT NULL auto_increment,
  CHANGE COLUMN `usu_fecha` `usu_fecha` datetime default NULL,
  CHANGE COLUMN `usu_estádo` `usu_estádo` int(11) default '0',
  CHANGE COLUMN `usu_category` `usu_category` int(11) default '0',
  CHANGE COLUMN `usu_user` `usu_user` varchar(8) default '',
  CHANGE COLUMN `usu_nombre` `usu_nombre` varchar(40) default '',
  CHANGE COLUMN `usu_password` `usu_password` varchar(8) default '',
  CHANGE COLUMN `usu_email` `usu_email` varchar(60) default '',
  CHANGE COLUMN `usu_libre1` `usu_libre1` varchar(16) default '',
  CHANGE COLUMN `usu_libre2` `usu_libre2` varchar(60) default '',
  CHANGE COLUMN `usu_libre3` `usu_libre3` varchar(250) default ''
;
