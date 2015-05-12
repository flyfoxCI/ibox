-- MySQL dump 10.13  Distrib 5.6.24, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: ibox
-- ------------------------------------------------------
-- Server version	5.6.24-0ubuntu2

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
-- Table structure for table `file`
--

DROP TABLE IF EXISTS `file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file` (
  `id` varchar(45) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `size` bigint(20) DEFAULT NULL,
  `upload_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `md5` varchar(45) DEFAULT NULL,
  `pid` varchar(45) DEFAULT NULL,
  `category` smallint(6) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file`
--

LOCK TABLES `file` WRITE;
/*!40000 ALTER TABLE `file` DISABLE KEYS */;
INSERT INTO `file` VALUES ('094f5396-a4e2-4365-bc24-73009a9f694a',NULL,'','dir',NULL,'2015-05-02 14:56:07','2015-05-02 14:56:07',NULL,NULL,NULL,NULL),('0a453724-bec0-4a8b-8872-11b853521eff',NULL,'','dir',NULL,'2015-05-01 23:43:55','2015-05-01 23:43:55',NULL,NULL,NULL,NULL),('0d3af1a1-d485-4cf7-b29b-c29fb5d022eb',NULL,'','dir',NULL,'2015-05-03 21:30:15','2015-05-03 21:30:15',NULL,NULL,NULL,NULL),('0dd4fe86-8e48-4055-b80a-9c40bb214ccc',NULL,'','dir',NULL,'2015-05-03 16:06:04','2015-05-03 16:06:04',NULL,NULL,NULL,NULL),('10997a94-924b-4888-9820-8cdf99fd74ce','国际大学生程序设计竞赛例题解 1 数论、计算几何、搜索算法专集..pdf','/国际大学生建模/国际大学生程序设计竞赛例题解 1 数论、计算几何、搜索算法专集..pdf','pdf',41047047,'2015-05-04 23:36:59','2015-05-04 23:36:59',NULL,'739ad900-7d69-4a8e-963f-33d49771053d',4,'jerry@gmail.com'),('1a8fdabd-3414-4dbf-80c8-f5af43acec23','图片','/图片','dir',NULL,'2015-05-04 17:12:26',NULL,NULL,'d6845dde-535e-49b9-aa2b-1349c601982a',NULL,NULL),('1f41dc4b-c781-40da-aa4e-a0c06774bdf4','国际大学生程序设计竞赛例题解 2 广东省大学生程序设计竞赛试题 2003-2005年.pdf','/国际大学生建模/国际大学生程序设计竞赛例题解 2 广东省大学生程序设计竞赛试题 2003-2005年.pdf','pdf',44238427,'2015-05-04 23:37:00','2015-05-04 23:37:00',NULL,'739ad900-7d69-4a8e-963f-33d49771053d',4,'jerry@gmail.com'),('212dd69e-0b92-4acb-a7d8-e14cf65b8694','国际大学生程序设计竞赛例题解 8 广东省信息学奥林匹克竞赛试题（2007-2009年）.pdf','/国际大学生建模/国际大学生程序设计竞赛例题解 8 广东省信息学奥林匹克竞赛试题（2007-2009年）.pdf','pdf',47154588,'2015-05-04 23:37:11','2015-05-04 23:37:11',NULL,'739ad900-7d69-4a8e-963f-33d49771053d',4,'jerry@gmail.com'),('3199f4dc-d3d9-4299-86bc-cf08a35c2053',NULL,'','dir',NULL,'2015-05-02 00:07:29','2015-05-02 00:07:29',NULL,NULL,NULL,NULL),('408cd647-eea1-437b-a228-98e4bc7143f5',NULL,'','dir',NULL,'2015-05-01 23:55:40','2015-05-01 23:55:40',NULL,NULL,NULL,NULL),('49290221-a49f-46d0-bc5a-2131251d3c8c',NULL,'','dir',NULL,'2015-05-02 00:01:07','2015-05-02 00:01:07',NULL,NULL,NULL,NULL),('53103a96-86ce-4b08-b09b-34e8967cdd5a','视频','/视频','dir',NULL,'2015-05-04 17:12:12',NULL,NULL,'d6845dde-535e-49b9-aa2b-1349c601982a',NULL,NULL),('59edfabe-49d8-426d-94e4-9ff6b58ec669',NULL,'','dir',NULL,'2015-05-01 23:58:14','2015-05-01 23:58:14',NULL,NULL,NULL,NULL),('606a2312-13ea-4888-b4ba-977179171e9f','测试1','/测试1','dir',NULL,'2015-05-04 01:24:11',NULL,NULL,'d02c94ac-5376-4ff9-a57b-28948da3c631',NULL,NULL),('60c50b32-71de-417b-8c00-3e41a0fe851b',NULL,'','dir',NULL,'2015-05-02 00:12:32','2015-05-02 00:12:32',NULL,NULL,NULL,NULL),('61ca3f1e-6178-486d-88da-158b2fe47306',NULL,'','dir',NULL,'2015-05-01 23:40:11','2015-05-01 23:40:11',NULL,NULL,NULL,NULL),('632020ae-78f4-4c68-a928-22642746fc1a',NULL,'','dir',NULL,'2015-05-02 16:59:37','2015-05-02 16:59:37',NULL,NULL,NULL,NULL),('647bb1ee-50f8-4d8b-a490-83d31acdbfdf',NULL,'','dir',NULL,'2015-05-02 00:04:28','2015-05-02 00:04:28',NULL,NULL,NULL,NULL),('64fae16e-0a74-4ced-9517-fdbd703ae95d',NULL,'','dir',NULL,'2015-05-03 10:18:11','2015-05-03 10:18:11',NULL,NULL,NULL,NULL),('6a704dca-a26f-4617-9a58-82111b76ad29','国际大学生程序设计竞赛例题解 6 广东省大学生程序设计竞赛试题解 2008-2009年.pdf','/国际大学生建模/国际大学生程序设计竞赛例题解 6 广东省大学生程序设计竞赛试题解 2008-2009年.pdf','pdf',39125882,'2015-05-04 23:37:04','2015-05-04 23:37:04',NULL,'739ad900-7d69-4a8e-963f-33d49771053d',4,'jerry@gmail.com'),('739ad900-7d69-4a8e-963f-33d49771053d','国际大学生建模','/国际大学生建模','dir',NULL,'2015-05-04 23:36:01',NULL,NULL,'d02c94ac-5376-4ff9-a57b-28948da3c631',NULL,NULL),('75adadd7-c4dd-4248-85f0-92ccfe7045e1',NULL,'','dir',NULL,'2015-05-01 23:47:49','2015-05-01 23:47:49',NULL,NULL,NULL,NULL),('7bafa69f-e69f-4e7a-b0f8-7139d0fd2e63','文件','/文件','dir',NULL,'2015-05-04 17:12:36',NULL,NULL,'d6845dde-535e-49b9-aa2b-1349c601982a',NULL,NULL),('7f438d3e-9b69-4935-88c6-6e8a764528ff',NULL,'','dir',NULL,'2015-05-01 23:57:56','2015-05-01 23:57:56',NULL,NULL,NULL,NULL),('859f1efc-233b-4a34-a1ee-77cc0065a77b','测试tu','/图片/测试tu','dir',NULL,'2015-05-04 17:49:15',NULL,NULL,'1a8fdabd-3414-4dbf-80c8-f5af43acec23',NULL,NULL),('8a20e1d1-4b6c-4f91-9448-1b54bfced054',NULL,'','dir',NULL,'2015-05-01 23:03:59','2015-05-01 23:03:59',NULL,NULL,NULL,NULL),('8bf7b9b0-c4f9-4190-bb27-a3f2a52da0a3',NULL,'','dir',NULL,'2015-05-01 23:08:30','2015-05-01 23:08:30',NULL,NULL,NULL,NULL),('8c8265d2-fd95-49e2-a398-255f417052de','国际大学生程序设计竞赛例题解 5 广东省大学生程序设计竞赛试题解 2006-2007年.pdf','/国际大学生建模/国际大学生程序设计竞赛例题解 5 广东省大学生程序设计竞赛试题解 2006-2007年.pdf','pdf',39811697,'2015-05-04 23:37:00','2015-05-04 23:37:00',NULL,'739ad900-7d69-4a8e-963f-33d49771053d',4,'jerry@gmail.com'),('8e52de49-45e8-4e8c-980f-e993c96153b7','国际大学生程序设计竞赛例题解 4 广东省信息学奥林匹克竞赛试题 2003-2006年.pdf','/国际大学生建模/国际大学生程序设计竞赛例题解 4 广东省信息学奥林匹克竞赛试题 2003-2006年.pdf','pdf',39828598,'2015-05-04 23:37:00','2015-05-04 23:37:00',NULL,'739ad900-7d69-4a8e-963f-33d49771053d',4,'jerry@gmail.com'),('93d4dc93-3a9c-4a4e-bfee-74ad3ad729bc','Chris McDonough - Using Supervisor For Fun And Profit - PyCon 2015-p0iQfT85IvM.mp4','/视频/Chris McDonough - Using Supervisor For Fun And Profit - PyCon 2015-p0iQfT85IvM.mp4','mp4',246811186,'2015-05-04 17:40:36','2015-05-04 17:40:36',NULL,'53103a96-86ce-4b08-b09b-34e8967cdd5a',1,'xiaohui@gmail.com'),('98ca174d-1084-473a-8ad8-17e2a43c14cf','国际大学生程序设计竞赛例题解 3 图论·动态规划算法·综合题专集.pdf','/国际大学生建模/国际大学生程序设计竞赛例题解 3 图论·动态规划算法·综合题专集.pdf','pdf',46127300,'2015-05-04 23:37:00','2015-05-04 23:37:00',NULL,'739ad900-7d69-4a8e-963f-33d49771053d',4,'jerry@gmail.com'),('9af44d91-1d14-4dc5-a1d0-b6efc294b0c7',NULL,'','dir',NULL,'2015-05-01 22:56:11','2015-05-01 22:56:11',NULL,NULL,NULL,NULL),('a24a59e6-9fb3-4b47-b41a-b8244d08c866','bg.jpg','/图片/bg.jpg','jpg',53719,'2015-05-04 17:12:50','2015-05-04 17:12:50',NULL,'1a8fdabd-3414-4dbf-80c8-f5af43acec23',3,'xiaohui@gmail.com'),('aaba98f8-50db-43e6-8233-026744ad258e','Ashwini Oruganti, Christopher Armstrong - Introduction to HTTPS - A Comedy of Errors - PyCon 2015-HqnUKTjxI1E.mp4','/视频/Ashwini Oruganti, Christopher Armstrong - Introduction to HTTPS - A Comedy of Errors - PyCon 2015-HqnUKTjxI1E.mp4','mp4',163106377,'2015-05-04 17:14:06','2015-05-04 17:14:06',NULL,'53103a96-86ce-4b08-b09b-34e8967cdd5a',1,'xiaohui@gmail.com'),('afaa1975-7d79-477e-ad6b-8523d8923e23','Java Servlet Programming88.pdf','/文件/Java Servlet Programming88.pdf','pdf',9195239,'2015-05-02 17:14:58','2015-05-04 17:14:58',NULL,'7bafa69f-e69f-4e7a-b0f8-7139d0fd2e63',4,'xiaohui@gmail.com'),('b11ac541-78fa-4407-be4c-09157b4349bb',NULL,'','dir',NULL,'2015-05-02 00:10:20','2015-05-02 00:10:20',NULL,NULL,NULL,NULL),('b1e89f6f-f7e0-4c59-979b-8e55358f2f73',NULL,'','dir',NULL,'2015-04-30 18:56:18','2015-04-30 18:56:18',NULL,NULL,NULL,NULL),('b6a6587e-7cee-4168-bc62-150a088c75d7','a6.jpg','/图片/a6.jpg','jpg',18426,'2015-05-04 17:12:48','2015-05-04 17:12:48',NULL,'1a8fdabd-3414-4dbf-80c8-f5af43acec23',3,'xiaohui@gmail.com'),('bc100ed5-9bad-45b2-aff9-25683b50932d',NULL,'','dir',NULL,'2015-05-01 23:31:19','2015-05-01 23:31:19',NULL,NULL,NULL,NULL),('c520104b-56cc-4d26-a12b-b6c78ffc00ca',NULL,'','dir',NULL,'2015-05-01 23:23:36','2015-05-01 23:23:36',NULL,NULL,NULL,NULL),('c540b651-9052-425e-8bc9-702f191bdbeb',NULL,'','dir',NULL,'2015-05-01 23:00:29','2015-05-01 23:00:29',NULL,NULL,NULL,NULL),('ca9f7e4f-fd82-42d6-a424-ee9ef80eb3b0',NULL,'','dir',NULL,'2015-05-02 00:05:07','2015-05-02 00:05:07',NULL,NULL,NULL,NULL),('cf0ad92f-9d8c-4b08-aa45-daddbc693242','Javascript教程--从入门到精通【完整版2】.pdf','/文件/Javascript教程--从入门到精通【完整版2】.pdf','pdf',495640,'2015-05-04 17:14:56','2015-05-04 17:14:56',NULL,'7bafa69f-e69f-4e7a-b0f8-7139d0fd2e63',4,'xiaohui@gmail.com'),('d02c94ac-5376-4ff9-a57b-28948da3c631',NULL,'','dir',NULL,'2015-05-03 21:39:16','2015-05-03 21:39:16',NULL,NULL,NULL,NULL),('d126e308-6c0f-4752-89e3-611523404faf',NULL,'','dir',NULL,'2015-05-01 23:18:53','2015-05-01 23:18:53',NULL,NULL,NULL,NULL),('d6845dde-535e-49b9-aa2b-1349c601982a',NULL,'','dir',NULL,'2015-05-04 17:11:50','2015-05-04 17:11:50',NULL,NULL,NULL,NULL),('d6f67a44-f9dc-4dc8-bca8-610c488b7655',NULL,'','dir',NULL,'2015-05-02 19:28:10','2015-05-02 19:28:10',NULL,NULL,NULL,NULL),('e800b8c0-2353-46db-ae05-08325a7533bc',NULL,'','dir',NULL,'2015-05-02 16:55:51','2015-05-02 16:55:51',NULL,NULL,NULL,NULL),('e9da5002-0644-422c-a5db-6607459f770d','head.png','/图片/head.png','png',7601,'2015-05-04 17:12:50','2015-05-04 17:12:50',NULL,'1a8fdabd-3414-4dbf-80c8-f5af43acec23',3,'xiaohui@gmail.com'),('e9ed40fe-ec76-417e-b079-dbabf8b72d7b','a2.jpg','/图片/a2.jpg','jpg',18426,'2015-05-04 17:12:48','2015-05-04 17:12:48',NULL,'1a8fdabd-3414-4dbf-80c8-f5af43acec23',3,'xiaohui@gmail.com'),('f08af676-7a54-48c2-9c1e-734fbff3bdc0','a7.jpg','/图片/a7.jpg','jpg',18426,'2015-05-04 17:12:48','2015-05-04 17:12:48',NULL,'1a8fdabd-3414-4dbf-80c8-f5af43acec23',3,'xiaohui@gmail.com'),('f3a8760a-022d-4209-969c-5000bd0df170','国际大学生程序设计竞赛例题解 7 中山大学ICPC集训队内部选拔赛试题 2005-2006年.pdf','/国际大学生建模/国际大学生程序设计竞赛例题解 7 中山大学ICPC集训队内部选拔赛试题 2005-2006年.pdf','pdf',41606641,'2015-05-04 23:37:11','2015-05-04 23:37:11',NULL,'739ad900-7d69-4a8e-963f-33d49771053d',4,'jerry@gmail.com'),('f8ee2aff-abc0-4120-86f8-4816bd7a0a51','spring 源代码分析.pdf','/测试1/spring 源代码分析.pdf','pdf',32514894,'2015-05-04 13:13:13','2015-05-04 13:13:13',NULL,'606a2312-13ea-4888-b4ba-977179171e9f',4,'jerry@gmail.com'),('fa252ef6-be6f-4740-9967-1cefc4790b5a','SVN搭建和使用手册.pdf','/测试1/SVN搭建和使用手册.pdf','pdf',1330657,'2015-05-04 13:13:06','2015-05-04 13:13:06',NULL,'606a2312-13ea-4888-b4ba-977179171e9f',4,'jerry@gmail.com'),('fadf83b2-833c-432d-b19a-c25398948cb2','[www.java1234.com]轻量级Java EE企业应用实战_Struts 2_Spring3_Hibernate(第三版).pdf','/测试1/[www.java1234.com]轻量级Java EE企业应用实战_Struts 2_Spring3_Hibernate(第三版).pdf','pdf',226829159,'2015-05-04 13:14:33','2015-05-04 13:14:33',NULL,'606a2312-13ea-4888-b4ba-977179171e9f',4,'jerry@gmail.com'),('fbb3ff5c-ac4b-4243-a9fd-b51c43bd07bc','a9.jpg','/图片/a9.jpg','jpg',18426,'2015-05-04 17:12:49','2015-05-04 17:12:49',NULL,'1a8fdabd-3414-4dbf-80c8-f5af43acec23',3,'xiaohui@gmail.com');
/*!40000 ALTER TABLE `file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `machine`
--

DROP TABLE IF EXISTS `machine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `machine` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `ip` varchar(15) DEFAULT NULL,
  `hostname` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `passwd` varchar(32) DEFAULT NULL,
  `state` smallint(6) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `operation` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ip` (`ip`),
  UNIQUE KEY `hostname` (`hostname`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `machine`
--

LOCK TABLES `machine` WRITE;
/*!40000 ALTER TABLE `machine` DISABLE KEYS */;
INSERT INTO `machine` VALUES (1,'172.31.5.142','master','cherry','e9cbf3e3adae9361c3896741ccbbb008',0,'namenode',NULL);
/*!40000 ALTER TABLE `machine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `u_login_log`
--

DROP TABLE IF EXISTS `u_login_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `u_login_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(80) DEFAULT NULL COMMENT 'username',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  `login_ip` varchar(15) DEFAULT NULL COMMENT '登录ip',
  `logout_time` datetime DEFAULT NULL COMMENT '退出时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=308 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `u_login_log`
--

LOCK TABLES `u_login_log` WRITE;
/*!40000 ALTER TABLE `u_login_log` DISABLE KEYS */;
INSERT INTO `u_login_log` VALUES (248,'jerry','2015-05-03 09:30:00','127.0.0.1',NULL),(249,'admin','2015-05-03 09:35:00','127.0.0.1',NULL),(250,'jerry','2015-05-03 09:39:00','127.0.0.1',NULL),(251,'admin','2015-05-03 09:50:00','127.0.0.1',NULL),(252,'admin','2015-05-03 09:59:00','127.0.0.1',NULL),(253,'admin','2015-05-03 10:00:00','127.0.0.1',NULL),(254,'admin','2015-05-03 10:10:00','127.0.0.1',NULL),(255,'admin','2015-05-03 11:30:00','127.0.0.1',NULL),(256,'admin','2015-05-04 12:52:00','127.0.0.1',NULL),(257,'cherryzxh007','2015-05-04 01:18:00','127.0.0.1',NULL),(258,'jerry','2015-05-04 01:19:00','127.0.0.1',NULL),(259,'admin','2015-05-04 01:24:00','127.0.0.1',NULL),(260,'jerry','2015-05-04 01:38:00','127.0.0.1',NULL),(261,'admin','2015-05-04 01:38:00','127.0.0.1',NULL),(262,'admin','2015-05-04 01:46:00','127.0.0.1',NULL),(263,'jerry','2015-05-04 01:51:00','127.0.0.1',NULL),(264,'admin','2015-05-04 01:54:00','127.0.0.1',NULL),(265,'admin','2015-05-04 01:56:00','127.0.0.1',NULL),(266,'admin','2015-05-04 09:28:00','127.0.0.1',NULL),(267,'jerry','2015-05-04 09:37:00','127.0.0.1',NULL),(268,'admin','2015-05-04 09:38:00','127.0.0.1',NULL),(269,'jerry','2015-05-04 10:09:00','127.0.0.1',NULL),(270,'admin','2015-05-04 10:11:00','127.0.0.1',NULL),(271,'jerry','2015-05-04 10:13:00','127.0.0.1',NULL),(272,'admin','2015-05-04 10:15:00','127.0.0.1',NULL),(273,'admin','2015-05-04 10:30:00','127.0.0.1',NULL),(274,'admin','2015-05-04 10:37:00','127.0.0.1',NULL),(275,'jerry','2015-05-04 10:55:00','127.0.0.1',NULL),(276,'admin','2015-05-04 10:56:00','127.0.0.1',NULL),(277,'admin','2015-05-04 11:38:00','127.0.0.1',NULL),(278,'admin','2015-05-04 12:12:00','127.0.0.1',NULL),(279,'admin','2015-05-04 01:02:00','127.0.0.1',NULL),(280,'jerry','2015-05-04 01:11:00','127.0.0.1',NULL),(281,'admin','2015-05-04 01:15:00','127.0.0.1',NULL),(282,'admin','2015-05-04 03:28:00','127.0.0.1',NULL),(283,'admin','2015-05-04 04:30:00','127.0.0.1',NULL),(284,'admin','2015-05-04 04:33:00','127.0.0.1',NULL),(285,'admin','2015-05-04 04:43:00','127.0.0.1',NULL),(286,'admin','2015-05-04 04:49:00','127.0.0.1',NULL),(287,'jerry','2015-05-04 04:52:00','127.0.0.1',NULL),(288,'admin','2015-05-04 04:54:00','127.0.0.1',NULL),(289,'admin','2015-05-04 05:03:00','127.0.0.1',NULL),(290,'admin','2015-05-04 05:09:00','127.0.0.1',NULL),(291,'xiaohui','2015-05-04 05:12:00','127.0.0.1',NULL),(292,'admin','2015-05-04 05:15:00','127.0.0.1',NULL),(293,'xiaohui','2015-05-04 05:21:00','127.0.0.1',NULL),(294,'admin','2015-05-04 05:21:00','127.0.0.1',NULL),(295,'xiaohui','2015-05-04 05:39:00','127.0.0.1',NULL),(296,'xiaohui','2015-05-04 06:41:00','127.0.0.1',NULL),(297,'xiaohui','2015-05-04 06:49:00','127.0.0.1',NULL),(298,'xiaohui','2015-05-04 06:52:00','127.0.0.1',NULL),(299,'jerry','2015-05-04 08:42:00','127.0.0.1',NULL),(300,'admin','2015-05-04 09:44:00','127.0.0.1',NULL),(301,'admin','2015-05-04 10:41:00','127.0.0.1',NULL),(302,'jerry','2015-05-04 11:34:00','127.0.0.1',NULL),(303,'admin','2015-05-04 11:39:00','127.0.0.1',NULL),(304,'admin','2015-05-04 11:44:00','127.0.0.1',NULL),(305,'jerry','2015-05-04 11:45:00','127.0.0.1',NULL),(306,'jerry','2015-05-04 11:47:00','127.0.0.1',NULL),(307,'jerry','2015-05-05 12:34:00','127.0.0.1',NULL);
/*!40000 ALTER TABLE `u_login_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `u_resc`
--

DROP TABLE IF EXISTS `u_resc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `u_resc` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type` char(1) DEFAULT '0' COMMENT '权限类型 默认：0 后台权限：1',
  `url` varchar(150) DEFAULT NULL COMMENT 'URL',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `enable` char(1) DEFAULT '0' COMMENT '是否停用 默认：0 停用：1',
  `name` varchar(80) DEFAULT NULL,
  `icon` varchar(80) DEFAULT NULL,
  `level` smallint(6) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `u_resc`
--

LOCK TABLES `u_resc` WRITE;
/*!40000 ALTER TABLE `u_resc` DISABLE KEYS */;
INSERT INTO `u_resc` VALUES (1,'1','/admin/','2015-04-30 09:34:23','1','主页','fa fa-th-large',1,0),(2,'1','/admin/machine/','2015-04-30 09:34:23','1','主机管理','fa fa-desktop',1,0),(4,'1','/admin/role','2015-04-30 09:34:23','1','角色管理',NULL,2,8),(5,'1','/admin/user','2015-04-30 09:34:23','1','用户管理','fa fa-users',1,0),(6,'1','/admin/file','2015-04-30 09:43:07','1','文件管理','fa fa-folder',1,0),(7,'1','/admin/info','2015-04-30 09:44:54','1','个人资料','fa fa-user',1,0),(8,'1','/admin/set','2015-05-02 17:24:15','1','系统设置','fa fa-cog',1,0),(9,'1','/admin/loginlog','2015-05-02 19:12:09','1','日志管理','',2,8);
/*!40000 ALTER TABLE `u_resc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `u_role`
--

DROP TABLE IF EXISTS `u_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `u_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `type` char(1) DEFAULT '0' COMMENT ' 角色类型 默认：0 后台角色：1',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `sort` int(2) DEFAULT NULL COMMENT '排序(不超过100)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `enable` char(1) DEFAULT '0' COMMENT '是否停用 默认：0 停用：1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `u_role`
--

LOCK TABLES `u_role` WRITE;
/*!40000 ALTER TABLE `u_role` DISABLE KEYS */;
INSERT INTO `u_role` VALUES (1,'admin','1',NULL,4,'2015-05-03 03:19:52','1'),(2,'普通管理员','1','',2,'2015-05-03 21:34:22','1'),(3,'user','0','',2,'2015-05-03 21:34:48','1');
/*!40000 ALTER TABLE `u_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `u_role_resc`
--

DROP TABLE IF EXISTS `u_role_resc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `u_role_resc` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` int(11) DEFAULT NULL COMMENT 'role id',
  `resc_id` int(11) DEFAULT NULL COMMENT 'resc_id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=156 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `u_role_resc`
--

LOCK TABLES `u_role_resc` WRITE;
/*!40000 ALTER TABLE `u_role_resc` DISABLE KEYS */;
INSERT INTO `u_role_resc` VALUES (151,2,1),(152,2,2),(153,2,5),(154,2,6),(155,2,7);
/*!40000 ALTER TABLE `u_role_resc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `u_role_user`
--

DROP TABLE IF EXISTS `u_role_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `u_role_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` int(11) DEFAULT NULL COMMENT 'role id',
  `user_id` int(11) DEFAULT NULL COMMENT 'user id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `u_role_user`
--

LOCK TABLES `u_role_user` WRITE;
/*!40000 ALTER TABLE `u_role_user` DISABLE KEYS */;
INSERT INTO `u_role_user` VALUES (1,1,22),(26,2,48),(27,2,49),(28,3,51),(29,3,52);
/*!40000 ALTER TABLE `u_role_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(80) DEFAULT NULL COMMENT '登录账号',
  `head_photo_path` varchar(150) DEFAULT NULL COMMENT '头像路径',
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `last_time` datetime DEFAULT NULL,
  `space` bigint(20) NOT NULL,
  `use_space` bigint(20) NOT NULL,
  `last_ip` varchar(45) DEFAULT NULL,
  `is_admin` int(11) NOT NULL,
  `is_active` int(11) NOT NULL,
  `file_id` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (22,'admin',NULL,'cherry@gmail.com','84eaf0d8662b14e6cf83567443db8735','2015-05-04 23:44:30',2014,641137,'127.0.0.1',1,1,'ab5ffc8e-b3aa-49c3-95c0-8626111aaa13'),(48,'cherry',NULL,'cherryzxh007@gmail.com','84eaf0d8662b14e6cf83567443db8735','2015-05-03 16:33:22',2015,641137,'127.0.0.1',0,1,'64fae16e-0a74-4ced-9517-fdbd703ae95d'),(49,'cherryzxh007',NULL,'420479946@qq.com','84eaf0d8662b14e6cf83567443db8735','2015-05-04 01:18:28',2015,641137,'127.0.0.1',0,1,'0dd4fe86-8e48-4055-b80a-9c40bb214ccc'),(51,'jerry',NULL,'jerry@gmail.com','','2015-05-05 00:34:25',2014,734280,'127.0.0.1',0,1,'d02c94ac-5376-4ff9-a57b-28948da3c631'),(52,'xiaohui',NULL,'xiaohui@gmail.com','84eaf0d8662b14e6cf83567443db8735','2015-05-04 18:52:59',2014,801444,'127.0.0.1',0,1,'d6845dde-535e-49b9-aa2b-1349c601982a');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-05-05 10:35:32



----------------------------------------------------------------------------------------

-- MySQL dump 10.13  Distrib 5.6.24, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: ibox
-- ------------------------------------------------------
-- Server version 5.6.24-0ubuntu2

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
-- Dumping routines for database 'ibox'
--
/*!50003 DROP FUNCTION IF EXISTS `getChildList` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getChildList`(rootId varchar(255)) RETURNS varchar(8000) CHARSET utf8
BEGIN 
       DECLARE str varchar(255) ; 
       DECLARE cid varchar(255) ; 
      
       SET str = ''; 
       SET cid =rootId; 
      
       WHILE cid is not null DO 
         SET str= concat(str,',',cid); 
         SELECT group_concat(id) INTO cid FROM file where FIND_IN_SET(pid,cid)>0; 
       END WHILE; 
       RETURN str; 
     END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-05-05 10:32:54

