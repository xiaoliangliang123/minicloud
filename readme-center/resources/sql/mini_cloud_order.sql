/*
Navicat MariaDB Data Transfer

Source Server         : mini_cloud
Source Server Version : 100603
Source Host           : localhost:3306
Source Database       : mini_cloud_order

Target Server Type    : MariaDB
Target Server Version : 100603
File Encoding         : 65001

Date: 2023-03-27 15:09:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `order_id` varchar(32) NOT NULL COMMENT '订单id',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('101572565793200');
INSERT INTO `orders` VALUES ('cccccccc');

-- ----------------------------
-- Table structure for order_content
-- ----------------------------
DROP TABLE IF EXISTS `order_content`;
CREATE TABLE `order_content` (
  `order_id` bigint(19) NOT NULL COMMENT '订单id',
  `order_detail` varchar(500) COLLATE utf8mb4_unicode_520_nopad_ci DEFAULT '' COMMENT '订单详情',
  `order_status` tinyint(1) DEFAULT 0 COMMENT '0:已创建，处理中，1：成功，2：失败',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL COMMENT '变更时间',
  `reason` varchar(255) COLLATE utf8mb4_unicode_520_nopad_ci DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_nopad_ci;

-- ----------------------------
-- Records of order_content
-- ----------------------------
INSERT INTO `order_content` VALUES ('167517205508239', '[\n    {\n        \"quantity\": 10,\n        \"orderId\": 167517205508239,\n        \"goodsId\": 1\n    },\n    {\n        \"quantity\": 10,\n        \"orderId\": 167517205508239,\n        \"goodsId\": 2\n    }\n]', '4', '2022-11-01 21:14:05', '2022-11-01 21:14:15', '');

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
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of undo_log
-- ----------------------------
