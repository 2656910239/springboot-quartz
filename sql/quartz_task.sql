/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : springboot

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-05-30 23:49:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for quartz_task
-- ----------------------------
DROP TABLE IF EXISTS `quartz_task`;
CREATE TABLE `quartz_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(128) DEFAULT NULL,
  `task_group` varchar(128) DEFAULT NULL,
  `task_class` varchar(128) DEFAULT NULL,
  `cron` varchar(64) DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  `state` tinyint(4) DEFAULT NULL,
  `createdate` date DEFAULT NULL,
  `memory_state` tinyint(4) DEFAULT '1' COMMENT '任务在内存中的状态，1 正常， 0 暂停',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of quartz_task
-- ----------------------------
INSERT INTO `quartz_task` VALUES ('1', '测试1', 'task1', 'com.quartz.quartz.task.ScheduledJob1', '0/5 * * * * ?', '1111', '1', '2017-11-11', '1');
INSERT INTO `quartz_task` VALUES ('2', '测试2', 'task1', 'com.quartz.quartz.task.ScheduledJob2', '0/15 * * * * ?', '222', '1', '2018-05-28', '1');
