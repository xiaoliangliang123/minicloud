/*
Navicat MariaDB Data Transfer

Source Server         : mini_cloud
Source Server Version : 100603
Source Host           : localhost:3306
Source Database       : mini_cloud_upms

Target Server Type    : MariaDB
Target Server Version : 100603
File Encoding         : 65001

Date: 2023-03-27 15:09:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of undo_log
-- ----------------------------

-- ----------------------------
-- Table structure for upms_menu
-- ----------------------------
DROP TABLE IF EXISTS `upms_menu`;
CREATE TABLE `upms_menu` (
  `menu_Id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单id',
  `menu_name` varchar(20) DEFAULT NULL COMMENT '菜单名称',
  `menu_type` tinyint(4) DEFAULT NULL COMMENT '菜单类型',
  `menu_route` varchar(100) DEFAULT '' COMMENT '菜单路由',
  `menu_parent_Id` int(11) DEFAULT NULL COMMENT '父菜单id',
  PRIMARY KEY (`menu_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of upms_menu
-- ----------------------------

-- ----------------------------
-- Table structure for upms_org
-- ----------------------------
DROP TABLE IF EXISTS `upms_org`;
CREATE TABLE `upms_org` (
  `org_id` int(6) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `org_parent_Id` int(6) DEFAULT 1 COMMENT '父级组织id',
  `org_name` varchar(100) DEFAULT NULL COMMENT '组织机构名称',
  `org_tag_id` int(3) DEFAULT NULL COMMENT '标签id',
  `tenant_id` int(3) DEFAULT 1 COMMENT '租户id',
  PRIMARY KEY (`org_id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of upms_org
-- ----------------------------
INSERT INTO `upms_org` VALUES ('1', '0', 'minicloud租户1集团', '1', '1');
INSERT INTO `upms_org` VALUES ('2', '0', 'minicloud租户2集团', '1', '2');
INSERT INTO `upms_org` VALUES ('8', '4', 'minicloud6', '1', '1');
INSERT INTO `upms_org` VALUES ('26', '1', '子公司', '2', '1');
INSERT INTO `upms_org` VALUES ('31', '26', '开发部', '3', '1');

-- ----------------------------
-- Table structure for upms_org_tag
-- ----------------------------
DROP TABLE IF EXISTS `upms_org_tag`;
CREATE TABLE `upms_org_tag` (
  `org_tag_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '组织标签id',
  `org_tag_name` varchar(255) DEFAULT NULL COMMENT '组织标签名称',
  PRIMARY KEY (`org_tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of upms_org_tag
-- ----------------------------
INSERT INTO `upms_org_tag` VALUES ('1', '集团');
INSERT INTO `upms_org_tag` VALUES ('2', '公司');
INSERT INTO `upms_org_tag` VALUES ('3', '部门');

-- ----------------------------
-- Table structure for upms_perm
-- ----------------------------
DROP TABLE IF EXISTS `upms_perm`;
CREATE TABLE `upms_perm` (
  `perm_id` int(6) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `perm_url` varchar(255) DEFAULT NULL COMMENT '权限url',
  `perm_method` varchar(10) DEFAULT NULL COMMENT '请求方式：get,post,put,delete 等',
  `perm_name` varchar(255) DEFAULT NULL COMMENT '权限名称',
  `perm_desc` varchar(500) DEFAULT NULL COMMENT '权限描述',
  `perm_server` varchar(20) DEFAULT NULL COMMENT '所属服务',
  `name` varchar(40) DEFAULT '' COMMENT '名称',
  `path` varchar(100) DEFAULT '' COMMENT '路由',
  `perm_role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `tenant_id` int(11) DEFAULT 1 COMMENT '租户id',
  PRIMARY KEY (`perm_id`)
) ENGINE=InnoDB AUTO_INCREMENT=153 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of upms_perm
-- ----------------------------
INSERT INTO `upms_perm` VALUES ('115', null, null, null, null, null, '角色列表', '/role/user_role_list', '34', '1');
INSERT INTO `upms_perm` VALUES ('116', null, null, null, null, null, '角色添加/编辑', '/role/user_role_add/:roleId', '34', '1');
INSERT INTO `upms_perm` VALUES ('117', null, null, null, null, null, '权限编辑', '/role/user_role_perms/:roleId', '34', '1');
INSERT INTO `upms_perm` VALUES ('118', null, null, null, null, null, '系统管理', '/sys-index', '34', '1');
INSERT INTO `upms_perm` VALUES ('119', null, null, null, null, null, '系统管理', '/sys', '34', '1');
INSERT INTO `upms_perm` VALUES ('141', null, null, null, null, null, '角色列表', '/role/user_role_list', '47', '2');
INSERT INTO `upms_perm` VALUES ('142', null, null, null, null, null, '角色添加/编辑', '/role/user_role_add/:roleId', '47', '2');
INSERT INTO `upms_perm` VALUES ('143', null, null, null, null, null, '权限编辑', '/role/user_role_perms/:roleId', '47', '2');
INSERT INTO `upms_perm` VALUES ('144', null, null, null, null, null, '系统监控', '/sys-monitor', '47', '2');
INSERT INTO `upms_perm` VALUES ('145', null, null, null, null, null, 'api管理', '/sys', '47', '2');
INSERT INTO `upms_perm` VALUES ('146', null, null, null, null, null, 'api列表', '/monitor/api_list', '47', '2');
INSERT INTO `upms_perm` VALUES ('147', null, null, null, null, null, '系统管理', '/sys-index', '47', '2');
INSERT INTO `upms_perm` VALUES ('148', null, null, null, null, null, '权限编辑', '/role/user_role_perms/:roleId', '35', '1');
INSERT INTO `upms_perm` VALUES ('149', null, null, null, null, null, '系统监控', '/sys-monitor', '35', '1');
INSERT INTO `upms_perm` VALUES ('150', null, null, null, null, null, 'api管理', '/sys', '35', '1');
INSERT INTO `upms_perm` VALUES ('151', null, null, null, null, null, 'api列表', '/monitor/api_list', '35', '1');
INSERT INTO `upms_perm` VALUES ('152', null, null, null, null, null, '系统管理', '/sys-index', '35', '1');

-- ----------------------------
-- Table structure for upms_role
-- ----------------------------
DROP TABLE IF EXISTS `upms_role`;
CREATE TABLE `upms_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` varchar(64) DEFAULT NULL COMMENT '角色名称',
  `role_code` varchar(64) DEFAULT NULL COMMENT '角色code',
  `role_desc` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `tenant_id` int(11) DEFAULT 1 COMMENT '租户id',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of upms_role
-- ----------------------------
INSERT INTO `upms_role` VALUES ('33', '超级管理员', 'SUPER_ADMIN', '最大权限', '1');
INSERT INTO `upms_role` VALUES ('34', '普通用户1', 'USER1', '普通用户1', '1');
INSERT INTO `upms_role` VALUES ('35', '普通用户2', 'USER2', '普通用户2', '1');
INSERT INTO `upms_role` VALUES ('36', '超级管理员', 'SUPER_ADMIN', '最大权限', '2');
INSERT INTO `upms_role` VALUES ('47', '普通用户1', 'ROLE_NORMAL_1', '普通用户1', '2');

-- ----------------------------
-- Table structure for upms_role_perm
-- ----------------------------
DROP TABLE IF EXISTS `upms_role_perm`;
CREATE TABLE `upms_role_perm` (
  `role_id` int(11) NOT NULL DEFAULT 0 COMMENT '角色id',
  `perm_id` int(5) NOT NULL COMMENT '权限url',
  `tenant_id` int(11) DEFAULT 1 COMMENT '租户id',
  PRIMARY KEY (`role_id`,`perm_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of upms_role_perm
-- ----------------------------

-- ----------------------------
-- Table structure for upms_user
-- ----------------------------
DROP TABLE IF EXISTS `upms_user`;
CREATE TABLE `upms_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(64) DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT '' COMMENT '密码',
  `realname` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `mobile` varchar(14) DEFAULT NULL COMMENT '手机号',
  `headimg` varchar(255) DEFAULT NULL COMMENT '头像',
  `dept_id` int(5) DEFAULT NULL COMMENT '部门id',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '已删除 0：未删除 ，1：已删除',
  `active` tinyint(1) DEFAULT 0 COMMENT '可用 0:可用，1：禁用',
  `expire_time` datetime DEFAULT NULL COMMENT '有效期',
  `tenant_id` int(11) DEFAULT 1 COMMENT '租户id',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of upms_user
-- ----------------------------
INSERT INTO `upms_user` VALUES ('36', 'admin', '{bcrypt}$2a$10$Hw1/JB6gI/42ml3CGBPS3eAIcrXohurz/EnAKZ2d9kHO209DIbpvi', '管理员', '租户1超级管理', '13888888889', '20220913D8FA480CE0EC4D52AD1E1A36881E0949.jpeg', '1', '0', '1', '2023-02-19 14:23:36', '1');
INSERT INTO `upms_user` VALUES ('37', 'user3', '{bcrypt}$2a$10$jqF7Xzp7BG6xixQ5vu.Cme5CfwG88W0UNt5rm7bUrfnnsolIPoDa.', '用户3', '普通用户3', '13655555555', '20220913A4010524CE214BF3840FD0AA8B266E78.jpg', '1', '0', '1', '2022-09-13 19:12:08', '1');
INSERT INTO `upms_user` VALUES ('38', 'admin', '{bcrypt}$2a$10$Hw1/JB6gI/42ml3CGBPS3eAIcrXohurz/EnAKZ2d9kHO209DIbpvi', '管理员', '租户2超级管理', '13888888889', '20220913D8FA480CE0EC4D52AD1E1A36881E0949.jpeg', '2', '0', '1', '2023-02-19 14:23:36', '2');
INSERT INTO `upms_user` VALUES ('65', 'user4', '{bcrypt}$2a$10$IS4i9c6Jt2imORva3zyQc.AUphf2TYkD67ez2auVOiI/z.Vfvc08u', '用户4', '用户4', '18998767988', '', '2', '0', '1', '2023-03-23 23:14:40', '2');

-- ----------------------------
-- Table structure for upms_user_role
-- ----------------------------
DROP TABLE IF EXISTS `upms_user_role`;
CREATE TABLE `upms_user_role` (
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `tenant_id` int(11) DEFAULT 1 COMMENT '租户id',
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of upms_user_role
-- ----------------------------
INSERT INTO `upms_user_role` VALUES ('36', '33', '1');
INSERT INTO `upms_user_role` VALUES ('36', '34', '1');
INSERT INTO `upms_user_role` VALUES ('36', '35', '1');
INSERT INTO `upms_user_role` VALUES ('37', '34', '1');
INSERT INTO `upms_user_role` VALUES ('37', '35', '1');
INSERT INTO `upms_user_role` VALUES ('38', '36', '2');
INSERT INTO `upms_user_role` VALUES ('50', '34', '1');
INSERT INTO `upms_user_role` VALUES ('50', '35', '1');
INSERT INTO `upms_user_role` VALUES ('57', '34', '1');
INSERT INTO `upms_user_role` VALUES ('57', '35', '1');
INSERT INTO `upms_user_role` VALUES ('58', '34', '1');
INSERT INTO `upms_user_role` VALUES ('58', '35', '1');
INSERT INTO `upms_user_role` VALUES ('60', '36', '2');
INSERT INTO `upms_user_role` VALUES ('61', '47', '2');
INSERT INTO `upms_user_role` VALUES ('62', '47', '2');
INSERT INTO `upms_user_role` VALUES ('63', '47', '2');
INSERT INTO `upms_user_role` VALUES ('64', '47', '2');
INSERT INTO `upms_user_role` VALUES ('65', '47', '2');
