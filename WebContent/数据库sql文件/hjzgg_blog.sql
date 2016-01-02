/*
Navicat MySQL Data Transfer

Source Server         : hjzgg
Source Server Version : 50621
Source Host           : localhost:3306
Source Database       : fff

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2016-01-02 12:06:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `articlecomment`
-- ----------------------------
DROP TABLE IF EXISTS `articlecomment`;
CREATE TABLE `articlecomment` (
  `commentId` int(11) NOT NULL AUTO_INCREMENT,
  `articleId` int(11) DEFAULT NULL,
  `commentContent` varchar(2000) DEFAULT NULL,
  `commentTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `commentPeopleContact` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`commentId`),
  KEY `FK_articleAndcomment` (`articleId`),
  CONSTRAINT `FK_articleAndcomment` FOREIGN KEY (`articleId`) REFERENCES `blogarticle` (`articleId`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of articlecomment
-- ----------------------------

-- ----------------------------
-- Table structure for `blogarticle`
-- ----------------------------
DROP TABLE IF EXISTS `blogarticle`;
CREATE TABLE `blogarticle` (
  `articleId` int(11) NOT NULL AUTO_INCREMENT,
  `articleContent` text,
  `articleTitle` varchar(50) DEFAULT NULL,
  `articleBuildTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `articleSummary` varchar(200) DEFAULT NULL,
  `articleReadingCount` int(11) DEFAULT NULL,
  PRIMARY KEY (`articleId`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of blogarticle
-- ----------------------------

-- ----------------------------
-- Table structure for `blogarticle_articlecomment`
-- ----------------------------
DROP TABLE IF EXISTS `blogarticle_articlecomment`;
CREATE TABLE `blogarticle_articlecomment` (
  `blogArticle_articleId` int(11) NOT NULL,
  `comments_commentId` int(11) NOT NULL,
  PRIMARY KEY (`blogArticle_articleId`,`comments_commentId`),
  UNIQUE KEY `UK_ne863iojcasbp8j0j643m770u` (`comments_commentId`),
  CONSTRAINT `FK_dsrih5ss71dyb9vdnld34klaa` FOREIGN KEY (`blogArticle_articleId`) REFERENCES `blogarticle` (`articleId`),
  CONSTRAINT `FK_ne863iojcasbp8j0j643m770u` FOREIGN KEY (`comments_commentId`) REFERENCES `articlecomment` (`commentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of blogarticle_articlecomment
-- ----------------------------

-- ----------------------------
-- Table structure for `bloggroup`
-- ----------------------------
DROP TABLE IF EXISTS `bloggroup`;
CREATE TABLE `bloggroup` (
  `groupId` int(11) NOT NULL AUTO_INCREMENT,
  `groupName` varchar(50) DEFAULT NULL,
  `groupBuildTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `groupDescrib` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`groupId`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bloggroup
-- ----------------------------
INSERT INTO `bloggroup` VALUES ('1', '未分组', '2016-01-02 12:05:41', '未分组');

-- ----------------------------
-- Table structure for `grouparticle`
-- ----------------------------
DROP TABLE IF EXISTS `grouparticle`;
CREATE TABLE `grouparticle` (
  `groupId` int(11) DEFAULT NULL,
  `articleId` int(11) DEFAULT NULL,
  KEY `FK_blogArticleAndgroupArticle` (`articleId`),
  KEY `FK_blogGroupAndgroupArticle` (`groupId`),
  CONSTRAINT `FK_blogArticleAndgroupArticle` FOREIGN KEY (`articleId`) REFERENCES `blogarticle` (`articleId`),
  CONSTRAINT `FK_blogGroupAndgroupArticle` FOREIGN KEY (`groupId`) REFERENCES `bloggroup` (`groupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of grouparticle
-- ----------------------------

-- ----------------------------
-- Table structure for `groupmessage`
-- ----------------------------
DROP TABLE IF EXISTS `groupmessage`;
CREATE TABLE `groupmessage` (
  `articleId` int(11) NOT NULL,
  `groupId` int(11) NOT NULL,
  PRIMARY KEY (`groupId`,`articleId`),
  KEY `FK_oss5xkwuu6ql86jswykfv4w6g` (`articleId`),
  CONSTRAINT `FK_ocx8akjoxfwgs86g9tdpkghg7` FOREIGN KEY (`groupId`) REFERENCES `bloggroup` (`groupId`),
  CONSTRAINT `FK_oss5xkwuu6ql86jswykfv4w6g` FOREIGN KEY (`articleId`) REFERENCES `blogarticle` (`articleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of groupmessage
-- ----------------------------

-- ----------------------------
-- Table structure for `leaveword`
-- ----------------------------
DROP TABLE IF EXISTS `leaveword`;
CREATE TABLE `leaveword` (
  `wordId` int(11) NOT NULL AUTO_INCREMENT,
  `wordTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `wordContent` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`wordId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of leaveword
-- ----------------------------

-- ----------------------------
-- Table structure for `mypicture`
-- ----------------------------
DROP TABLE IF EXISTS `mypicture`;
CREATE TABLE `mypicture` (
  `pictureId` int(11) NOT NULL AUTO_INCREMENT,
  `groupId` int(11) DEFAULT NULL,
  `pictureName` varchar(50) DEFAULT NULL,
  `picturePath` varchar(100) DEFAULT NULL,
  `pictureBuildTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`pictureId`),
  KEY `FK_pictureAndpicGroup` (`groupId`),
  CONSTRAINT `FK_pictureAndpicGroup` FOREIGN KEY (`groupId`) REFERENCES `picturegroup` (`groupId`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mypicture
-- ----------------------------

-- ----------------------------
-- Table structure for `picturecomment`
-- ----------------------------
DROP TABLE IF EXISTS `picturecomment`;
CREATE TABLE `picturecomment` (
  `commentId` int(11) NOT NULL AUTO_INCREMENT,
  `groupId` int(11) DEFAULT NULL,
  `commentContent` varchar(2000) DEFAULT NULL,
  `commentTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `commentPeopleContact` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`commentId`),
  KEY `FK_pictureGroupAndcomment` (`groupId`),
  CONSTRAINT `FK_pictureGroupAndcomment` FOREIGN KEY (`groupId`) REFERENCES `picturegroup` (`groupId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of picturecomment
-- ----------------------------

-- ----------------------------
-- Table structure for `picturegroup`
-- ----------------------------
DROP TABLE IF EXISTS `picturegroup`;
CREATE TABLE `picturegroup` (
  `groupId` int(11) NOT NULL AUTO_INCREMENT,
  `groupDescrib` varchar(200) DEFAULT NULL,
  `groupBuildTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `groupName` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`groupId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of picturegroup
-- ----------------------------
