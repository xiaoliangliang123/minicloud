/*
Navicat MariaDB Data Transfer

Source Server         : mini_cloud
Source Server Version : 100603
Source Host           : localhost:3306
Source Database       : mini_cloud_log

Target Server Type    : MariaDB
Target Server Version : 100603
File Encoding         : 65001

Date: 2023-03-27 15:08:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for iolog
-- ----------------------------
DROP TABLE IF EXISTS `iolog`;
CREATE TABLE `iolog` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '住建',
  `timestamp` bigint(20) DEFAULT NULL COMMENT '时间戳',
  `method` varchar(10) DEFAULT '' COMMENT '请求方式',
  `url` varchar(100) DEFAULT NULL COMMENT '请求url',
  `content_type` varchar(30) DEFAULT '' COMMENT '数据类型',
  `args` varchar(500) DEFAULT '' COMMENT '请求参数',
  `response` varchar(1000) DEFAULT '' COMMENT '响应',
  `data_time` varchar(30) DEFAULT '' COMMENT '日志时间',
  `keyword` varchar(50) DEFAULT '' COMMENT '关键字',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of iolog
-- ----------------------------
INSERT INTO `iolog` VALUES ('18', '391401904316400', 'GET', '/role', null, '[{\"size\":10,\"page\":1},{\"upmsPermDTOS\":[]}]', '{\"headers\":{},\"statusCodeValue\":200,\"body\":{\"data\":[{\"roleId\":33,\"roleDesc\":\"最大权限\",\"roleCode\":\"SUPER_ADMIN\",\"roleName\":\"超级管理员\",\"tenantId\":1,\"upmsPermDTOS\":[]},{\"roleId\":34,\"roleDesc\":\"普通用户1\",\"roleCode\":\"USER1\",\"roleName\":\"普通用户1\",\"tenantId\":1,\"upmsPermDTOS\":[]},{\"roleId\":35,\"roleDesc\":\"普通用户2\",\"roleCode\":\"USER2\",\"roleName\":\"普通用户2\",\"tenantId\":1,\"upmsPermDTOS\":[]}],\"total\":3,\"size\":10,\"page\":1},\"statusCode\":\"OK\"}', '2022-09-13 21:33:44.185', '', '');
INSERT INTO `iolog` VALUES ('19', '434005739254100', 'GET', '/role', null, '[{\"size\":10,\"page\":1},{\"upmsPermDTOS\":[]}]', '{\"headers\":{},\"statusCodeValue\":200,\"body\":{\"data\":[{\"roleId\":33,\"roleDesc\":\"最大权限\",\"roleCode\":\"SUPER_ADMIN\",\"roleName\":\"超级管理员\",\"tenantId\":1,\"upmsPermDTOS\":[]},{\"roleId\":34,\"roleDesc\":\"普通用户1\",\"roleCode\":\"USER1\",\"roleName\":\"普通用户1\",\"tenantId\":1,\"upmsPermDTOS\":[]},{\"roleId\":35,\"roleDesc\":\"普通用户2\",\"roleCode\":\"USER2\",\"roleName\":\"普通用户2\",\"tenantId\":1,\"upmsPermDTOS\":[]}],\"total\":3,\"size\":10,\"page\":1},\"statusCode\":\"OK\"}', '2022-09-14 09:23:41.691', '', '');
