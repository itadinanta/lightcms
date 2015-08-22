-- MySQL dump 9.11
--
-- Host: localhost    Database: itadinanta
-- ------------------------------------------------------
-- Server version	4.0.24_Debian-10ubuntu2-log

--
-- Table structure for table `articles`
--

CREATE TABLE `articles` (
  `id` int(11) NOT NULL auto_increment,
  `language` char(3) default NULL,
  `kind` smallint(6) default NULL,
  `author` int(11) default NULL,
  `title` varchar(200) default NULL,
  `body` text,
  `created` date default NULL,
  `lastmodified` date default NULL,
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;

--
-- Dumping data for table `articles`
--


--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(32) NOT NULL default '',
  `password` varchar(32) default NULL,
  `clearance` int(11) default NULL,
  PRIMARY KEY  (`id`)
) TYPE=MyISAM;

--
-- Dumping data for table `user`
--

INSERT INTO `user` VALUES (1,'nigu',NULL,10);

