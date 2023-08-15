/*
 Navicat MySQL Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80023 (8.0.23)
 Source Host           : localhost:3306
 Source Schema         : xdb

 Target Server Type    : MySQL
 Target Server Version : 80023 (8.0.23)
 File Encoding         : 65001

 Date: 15/08/2023 20:21:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for conversation
-- ----------------------------
DROP TABLE IF EXISTS `conversation`;
CREATE TABLE `conversation`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '名称',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '主机',
  `port` int NULL DEFAULT NULL COMMENT '端口',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of conversation
-- ----------------------------
INSERT INTO `conversation` VALUES (1, '服务器23', '192.168.183.128', 22, 'zxb', '123456');
INSERT INTO `conversation` VALUES (2, '111', '111', 22, '111', '123456');
INSERT INTO `conversation` VALUES (3, '222', '111', 22, 'root', '123456');
INSERT INTO `conversation` VALUES (4, '222', '111', 22, 'root', '123456');
INSERT INTO `conversation` VALUES (6, '222', '111', 22, 'root', '123456');
INSERT INTO `conversation` VALUES (7, 'zxb', '0.0.0.0', 3306, 'root', NULL);
INSERT INTO `conversation` VALUES (8, '333', '111', 22, 'root', '123456');
INSERT INTO `conversation` VALUES (9, '服务器22', '192.168.183.128', 22, 'zxb', '123456');

-- ----------------------------
-- Table structure for x_log
-- ----------------------------
DROP TABLE IF EXISTS `x_log`;
CREATE TABLE `x_log`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '名称',
  `server` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '部署服务器名',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '服务器ip',
  `port` int NULL DEFAULT NULL COMMENT '端口',
  `command` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '查看日志需要执行的命令',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of x_log
-- ----------------------------
INSERT INTO `x_log` VALUES (1, 'vueadmin', '服务器2', '192.168.183.128', 22, 'docker logs -f --tail=100 vueadmin', 'zxb', '123456');
INSERT INTO `x_log` VALUES (2, 'tomcat', '222', '222', 65535, 'tail -n 200 -f catalina.out', 'root', '123456');
INSERT INTO `x_log` VALUES (3, 'ssh', '服务器2', '10.18.60.12', 8082, 'docker logs -f --tail=100 ssh', 'root', '123456');

-- ----------------------------
-- Table structure for x_menu
-- ----------------------------
DROP TABLE IF EXISTS `x_menu`;
CREATE TABLE `x_menu`  (
  `menu_id` int NOT NULL AUTO_INCREMENT,
  `component` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `redirect` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `parent_id` int NULL DEFAULT NULL,
  `is_leaf` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `hidden` tinyint(1) NULL DEFAULT NULL,
  `type` int NULL DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of x_menu
-- ----------------------------
INSERT INTO `x_menu` VALUES (1, 'Layout', '/sys', '/sys/user', 'sysManage', '系统管理', 'userManage', 0, 'N', 0, 0);
INSERT INTO `x_menu` VALUES (2, 'sys/user', 'user', NULL, 'userList', '用户列表', 'user', 1, 'Y', 0, 1);
INSERT INTO `x_menu` VALUES (3, 'sys/role', 'role', NULL, 'roleList', '角色列表', 'roleManage', 1, 'Y', 0, 1);
INSERT INTO `x_menu` VALUES (4, 'Layout', '/rtools', '/rtools/rt', 'sysTools', '系统工具', 'form', 0, 'N', 0, 0);
INSERT INTO `x_menu` VALUES (5, 'sys/menu', 'menu', NULL, 'menuList', '菜单列表', 'menuManage', 1, 'Y', 0, 1);
INSERT INTO `x_menu` VALUES (6, 'sys/conv', 'conv', NULL, 'convList', '会话管理', 'convManage', 1, 'Y', 0, 1);
INSERT INTO `x_menu` VALUES (7, 'sys/log', 'log', '', 'logList', '日志管理', 'form', 1, 'Y', 0, 1);
INSERT INTO `x_menu` VALUES (8, 'terminal/index', '/terminal', NULL, 'terminal', '远程终端', 'form', 4, 'Y', 0, 1);
INSERT INTO `x_menu` VALUES (9, 'test/test1', 'test1', NULL, 'test1', '测试点1', 'form', 4, 'Y', 0, 1);
INSERT INTO `x_menu` VALUES (10, NULL, NULL, NULL, 'addUser', '添加用户', '', 2, 'Y', 0, 2);
INSERT INTO `x_menu` VALUES (12, '', '', '', NULL, '修改用户', '', 9, 'Y', 0, 2);
INSERT INTO `x_menu` VALUES (21, 'Layout', '/info', '/info/userInfo', 'person', '个人中心', 'userInfo', 0, 'N', 0, 0);
INSERT INTO `x_menu` VALUES (22, 'info/changepassword', 'changepassword', NULL, 'changepassword', '修改密码', 'change', 21, 'Y', 0, 1);
INSERT INTO `x_menu` VALUES (23, 'info/changeuserinfo', 'changeuserinfo', NULL, 'changeuserinfo', '修改个人信息', 'change', 21, 'Y', 0, 1);

-- ----------------------------
-- Table structure for x_role
-- ----------------------------
DROP TABLE IF EXISTS `x_role`;
CREATE TABLE `x_role`  (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `role_desc` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of x_role
-- ----------------------------
INSERT INTO `x_role` VALUES (1, 'admin', '超级管理员');
INSERT INTO `x_role` VALUES (2, 'hr', '人事管理员');
INSERT INTO `x_role` VALUES (3, 'normal', '普通员工');

-- ----------------------------
-- Table structure for x_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `x_role_menu`;
CREATE TABLE `x_role_menu`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_id` int NULL DEFAULT NULL,
  `menu_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 76 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of x_role_menu
-- ----------------------------
INSERT INTO `x_role_menu` VALUES (11, 1, 1);
INSERT INTO `x_role_menu` VALUES (12, 1, 2);
INSERT INTO `x_role_menu` VALUES (13, 1, 3);
INSERT INTO `x_role_menu` VALUES (14, 1, 4);
INSERT INTO `x_role_menu` VALUES (15, 1, 5);
INSERT INTO `x_role_menu` VALUES (16, 1, 6);
INSERT INTO `x_role_menu` VALUES (17, 1, 7);
INSERT INTO `x_role_menu` VALUES (18, 1, 8);
INSERT INTO `x_role_menu` VALUES (19, 1, 9);
INSERT INTO `x_role_menu` VALUES (25, 2, 1);
INSERT INTO `x_role_menu` VALUES (26, 2, 2);
INSERT INTO `x_role_menu` VALUES (27, 2, 3);
INSERT INTO `x_role_menu` VALUES (28, 2, 5);
INSERT INTO `x_role_menu` VALUES (29, 2, 6);
INSERT INTO `x_role_menu` VALUES (30, 2, 7);
INSERT INTO `x_role_menu` VALUES (42, 2, 10);
INSERT INTO `x_role_menu` VALUES (52, 3, 1);
INSERT INTO `x_role_menu` VALUES (53, 3, 2);
INSERT INTO `x_role_menu` VALUES (58, 3, 3);
INSERT INTO `x_role_menu` VALUES (62, 3, 4);
INSERT INTO `x_role_menu` VALUES (67, 1, 21);
INSERT INTO `x_role_menu` VALUES (68, 1, 22);
INSERT INTO `x_role_menu` VALUES (69, 1, 23);
INSERT INTO `x_role_menu` VALUES (70, 2, 21);
INSERT INTO `x_role_menu` VALUES (71, 2, 22);
INSERT INTO `x_role_menu` VALUES (72, 2, 23);
INSERT INTO `x_role_menu` VALUES (73, 3, 21);
INSERT INTO `x_role_menu` VALUES (74, 3, 22);
INSERT INTO `x_role_menu` VALUES (75, 3, 23);

-- ----------------------------
-- Table structure for x_user
-- ----------------------------
DROP TABLE IF EXISTS `x_user`;
CREATE TABLE `x_user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL,
  `avatar` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `deleted` int NULL DEFAULT 0,
  `create_time` datetime NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of x_user
-- ----------------------------
INSERT INTO `x_user` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 'super@aliyun.com', '18677778889', 1, 'https://boluo12138.oss-cn-beijing.aliyuncs.com/3.jpg', 0, '2023-08-08 17:36:26', 'admin12');
INSERT INTO `x_user` VALUES (2, 'zhangsan', '$2a$10$3ZDHAfyo/P7tSOabB8X.DuVM1DN92YWgSd46ecVY.5WUAQNef7iUC', 'zhangsan@gmail.com', '13966667777', 1, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 0, NULL, NULL);
INSERT INTO `x_user` VALUES (3, 'lisi', '$2a$10$3ZDHAfyo/P7tSOabB8X.DuVM1DN92YWgSd46ecVY.5WUAQNef7iUC', 'lisi@gmail.com', '13966667778', 1, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 0, NULL, NULL);
INSERT INTO `x_user` VALUES (4, 'wangwu', '$2a$10$3ZDHAfyo/P7tSOabB8X.DuVM1DN92YWgSd46ecVY.5WUAQNef7iUC', 'wangwu@gmail.com', '13966667772', 1, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 0, NULL, NULL);
INSERT INTO `x_user` VALUES (5, 'zhaoer', '$2a$10$3ZDHAfyo/P7tSOabB8X.DuVM1DN92YWgSd46ecVY.5WUAQNef7iUC', 'zhaoer@gmail.com', '13966667776', 1, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 0, NULL, NULL);
INSERT INTO `x_user` VALUES (6, 'songliu', '$2a$10$3ZDHAfyo/P7tSOabB8X.DuVM1DN92YWgSd46ecVY.5WUAQNef7iUC', 'songliu@gmail.com', '13966667771', 1, 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', 0, NULL, NULL);
INSERT INTO `x_user` VALUES (7, 'aaa', '$2a$10$3ZDHAfyo/P7tSOabB8X.DuVM1DN92YWgSd46ecVY.5WUAQNef7iUC', 'aaa@aliyun.com', '18899998888', 1, NULL, 1, NULL, NULL);
INSERT INTO `x_user` VALUES (8, 'bbb', 'e10adc3949ba59abbe56e057f20f883e', 'bbb@qq.com', '188888889999', 1, NULL, 1, NULL, NULL);
INSERT INTO `x_user` VALUES (9, 'ccc', '$2a$10$3ZDHAfyo/P7tSOabB8X.DuVM1DN92YWgSd46ecVY.5WUAQNef7iUC', '1111@ali.com', '11111111', 1, NULL, 0, NULL, NULL);
INSERT INTO `x_user` VALUES (10, 'xxx222', '$2a$10$dQ1n957Q3qP2D0lHBSqdEeRHym9kAauXgxnIqAv9KDhnsJOC0Kooi', '1122@qq.com', '111111122', 0, NULL, 1, NULL, NULL);
INSERT INTO `x_user` VALUES (11, 'yyy', '$2a$10$hX03AhH9Yt//V.crSdqGP.nhj3EQi.cMwIEglT9Eqd.5KDkIGaCQC', '1111111@ali.com', '1111111', 1, NULL, 0, NULL, NULL);
INSERT INTO `x_user` VALUES (12, 'xxx6', '$2a$10$LTtlLYIPtDelUtP01FhcSe9JZkNuJmeXf66YMJyZee.04Q1yzNoj.', 'xxx6@aliyun.com', '18677778886', 1, NULL, 1, NULL, NULL);
INSERT INTO `x_user` VALUES (13, 'test', '$2a$10$jWewP9UNr.6AhrP2ofza1OaTDBq7nBL23C9zHnwBXh8Q/9Vd2zNSC', 'test@ali.com', '11111111111', 1, NULL, 0, NULL, NULL);
INSERT INTO `x_user` VALUES (14, 'xxx', '$2a$10$QpmItVrXHa5YWTfH25yCEuTu69VFjJbcxBxMYQTWb8qVNBSp0laxq', 'xxx@ali.com', '1111111111111', 1, NULL, 1, NULL, NULL);
INSERT INTO `x_user` VALUES (15, 'twh', '0ef6a0185dbfb0fbd8cea2c7eaa0d2a0', '2982681306@qq.com', NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `x_user` VALUES (16, 'bwh', 'dfeb3b7ba315eb9cc86dcb82cf9233d2', '2982681306@qq.com', NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `x_user` VALUES (17, 'atm', '32a347a83a0398706bfeb4ab52dc27fc', '2982681306@qq.com', NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `x_user` VALUES (18, 'zjm', '4b3b98720cacd0bab50e1fcca7f1e0cd', '2982681306@qq.com', NULL, 1, NULL, 0, NULL, NULL);
INSERT INTO `x_user` VALUES (19, 'zjm1', '4b3b98720cacd0bab50e1fcca7f1e0cd', '2982681306@qq.com', NULL, 1, NULL, 0, NULL, NULL);
INSERT INTO `x_user` VALUES (20, 'twhdsb', '0ef6a0185dbfb0fbd8cea2c7eaa0d2a0', '2982681306@qq.com', '1008611', NULL, 'https://boluo12138.oss-cn-beijing.aliyuncs.com/1.jpg', 0, NULL, '陶文会大傻逼');
INSERT INTO `x_user` VALUES (21, 'bwh2', 'dfeb3b7ba315eb9cc86dcb82cf9233d2', '2982681306@qq.com', NULL, 1, NULL, 0, '2023-08-14 20:11:21', NULL);

-- ----------------------------
-- Table structure for x_user_role
-- ----------------------------
DROP TABLE IF EXISTS `x_user_role`;
CREATE TABLE `x_user_role`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL,
  `role_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 106 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of x_user_role
-- ----------------------------
INSERT INTO `x_user_role` VALUES (7, 20, 3);
INSERT INTO `x_user_role` VALUES (8, 4, 3);
INSERT INTO `x_user_role` VALUES (87, 18, 1);
INSERT INTO `x_user_role` VALUES (88, 19, 1);
INSERT INTO `x_user_role` VALUES (98, 1, 3);
INSERT INTO `x_user_role` VALUES (99, 1, 1);
INSERT INTO `x_user_role` VALUES (100, 2, 3);
INSERT INTO `x_user_role` VALUES (101, 1, 3);
INSERT INTO `x_user_role` VALUES (102, 1, 1);
INSERT INTO `x_user_role` VALUES (103, 21, 3);
INSERT INTO `x_user_role` VALUES (104, 1, 3);
INSERT INTO `x_user_role` VALUES (105, 1, 1);

SET FOREIGN_KEY_CHECKS = 1;
