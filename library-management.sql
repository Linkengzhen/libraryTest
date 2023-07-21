/*
 Navicat Premium Data Transfer

 Source Server         : Project
 Source Server Type    : MySQL
 Source Server Version : 50737 (5.7.37-log)
 Source Host           : localhost:3306
 Source Schema         : library-management

 Target Server Type    : MySQL
 Target Server Version : 50737 (5.7.37-log)
 File Encoding         : 65001

 Date: 07/12/2022 15:41:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `createtime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` datetime NULL DEFAULT NULL,
  `status` tinyint(1) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username_key`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin1', '123456', '13674883399', '222227202@qq.com', '2022-11-06 20:12:10', '2022-11-10 22:00:23', 1);
INSERT INTO `admin` VALUES (2, 'zxc1234', '123456', '17787599333', '222227202@163.com', '2022-11-10 21:59:25', NULL, 1);
INSERT INTO `admin` VALUES (3, 'czh2834', '123456', '19900222121', '742227202@gmail.com', '2022-11-10 21:59:53', NULL, 1);
INSERT INTO `admin` VALUES (4, 'qyw1232', '123456', '17722998822', '20zhchen1@stu.cn', '2022-11-10 22:00:12', NULL, 1);
INSERT INTO `admin` VALUES (5, 'czh1789', '742227202', '17708644353', '742227202@qq.com', '2022-11-27 13:32:19', NULL, 1);
INSERT INTO `admin` VALUES (6, 'czh1879', '004232', '17708644353', '742227202@gmail.com', '2022-11-27 15:09:26', NULL, 1);

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bookName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '概述',
  `publishDate` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '出版日期',
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '作者',
  `publisher` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '出版社',
  `category` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '分类',
  `bookNo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '书籍标准码',
  `createtime` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` datetime NULL DEFAULT NULL,
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '封面',
  `score` int(10) NULL DEFAULT NULL,
  `bookNumber` int(10) NULL DEFAULT 0 COMMENT '书本数量',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `bookNoIndex`(`bookNo`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES (1, '三体', '《三体》第73届世界科幻雨果奖获奖作品，银河奖特别奖，《三体3》轨迹奖长篇科幻小说！2017年世界雨果奖提名作品。 ', '2010-11', '刘慈欣', '重庆出版社', '', '9787229124410', '2022-11-10 22:09:59', NULL, 'https://img0.baidu.com/it/u=78419484,644112756&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=738', 15, 10);
INSERT INTO `book` VALUES (2, '人间失格', '超燃！“丧文化”流行，《人间失格》受到读者高度欢迎与关注！收录作者绝笔之作《Good bye》。一个“丧失为人资格”少年的心灵独白，一个对村上春树影响至深的绝望凄美故事。', '2015-08', '太宰治', '作家出版社', '文学 > 现代文学', '9787506380263', '2022-11-10 22:11:34', NULL, 'https://img1.baidu.com/it/u=510415035,1625618669&fm=253&fmt=auto&app=138&f=JPEG?w=353&h=500', 20, 3);

-- ----------------------------
-- Table structure for borrow
-- ----------------------------
DROP TABLE IF EXISTS `borrow`;
CREATE TABLE `borrow`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bookName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图书名称',
  `bookNo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图书标准码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名称',
  `userName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名(唯一）',
  `userPhone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `createtime` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` datetime NULL DEFAULT NULL,
  `score` int(10) NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '已借出',
  `days` int(11) NULL DEFAULT 1,
  `returnDate` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `createtime`(`createtime`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of borrow
-- ----------------------------
INSERT INTO `borrow` VALUES (1, '人间失格', '9787506380263', 'chen', 'ccu1378', '18762255332', '2022-11-10 22:23:24', NULL, 20, '已归还', 1, '2022-11-11 22:23:24');
INSERT INTO `borrow` VALUES (2, '三体', '9787229124410', '123', 'zxc2345', '13376622281', '2022-11-10 22:24:31', NULL, 15, '已归还', 2, '2022-11-12 22:24:31');
INSERT INTO `borrow` VALUES (3, '人间失格', '9787506380263', '张三', 'gs6543', '13788866526', '2022-11-10 22:24:35', NULL, 20, '已归还', 3, '2022-11-13 22:24:35');
INSERT INTO `borrow` VALUES (4, '人间失格', '9787506380263', '111', 'ccu1872', '13825864005', '2022-11-10 22:24:40', NULL, 20, '已归还', 4, '2022-11-14 22:24:40');
INSERT INTO `borrow` VALUES (5, '人间失格', '9787506380263', '张三', 'czh1232', '13823256324', '2022-11-10 22:24:45', NULL, 20, '已归还', 5, '2022-11-15 22:24:45');
INSERT INTO `borrow` VALUES (6, '人间失格', '9787506380263', '123', 'zxc2345', '13376622281', '2022-11-10 22:24:49', NULL, 20, '已归还', 6, '2022-11-16 22:24:49');
INSERT INTO `borrow` VALUES (7, '三体', '9787229124410', '111', 'ccu1872', '13825864005', '2022-11-22 14:39:13', NULL, 15, '已归还', 3, '2022-11-25 00:00:00');
INSERT INTO `borrow` VALUES (8, '三体', '9787229124410', '杰克', 'ccu1872', '13825864005', '2022-11-22 14:42:39', NULL, 15, '已归还', 5, '2022-11-27 00:00:00');
INSERT INTO `borrow` VALUES (9, '三体', '9787229124410', '杰克', 'ccu1872', '13825864005', '2022-11-22 14:43:46', NULL, 15, '已归还', 3, '2022-11-25 00:00:00');
INSERT INTO `borrow` VALUES (10, '人间失格', '9787506380263', '李四', 'czh1234', '17708644353', '2022-11-23 16:43:27', NULL, 20, '已归还', 3, '2022-11-26 00:00:00');
INSERT INTO `borrow` VALUES (11, '人间失格', '9787506380263', '123', 'zxc2345', '13376622281', '2022-11-23 16:55:46', NULL, 20, '已归还', 1, '2022-11-24 00:00:00');
INSERT INTO `borrow` VALUES (12, '人间失格', '9787506380263', '李四', 'czh1234', '17708644353', '2022-11-23 17:05:28', NULL, 20, '已归还', 1, '2022-11-24 00:00:00');
INSERT INTO `borrow` VALUES (13, '人间失格', '9787506380263', '张三', 'gs6543', '13788866526', '2022-11-23 17:06:55', NULL, 60, '已归还', 3, '2022-11-10 00:00:00');
INSERT INTO `borrow` VALUES (14, '人间失格', '9787506380263', '123', 'zxc2345', '13376622281', '2022-11-23 17:09:38', NULL, 40, '已归还', 2, '2022-11-10 00:00:00');
INSERT INTO `borrow` VALUES (15, '人间失格', '9787506380263', '李四', 'czh1234', '17708644353', '2022-11-23 17:12:51', NULL, 40, '已归还', 2, '2022-11-20 00:00:00');
INSERT INTO `borrow` VALUES (16, '人间失格', '9787506380263', '李四', 'czh1234', '17708644353', '2022-11-23 17:17:17', NULL, 40, '已归还', 2, '2022-11-20 00:00:00');
INSERT INTO `borrow` VALUES (17, '人间失格', '9787506380263', '张三', 'czh1232', '13823256324', '2022-11-23 17:22:46', NULL, 60, '已归还', 3, '2022-11-20 00:00:00');
INSERT INTO `borrow` VALUES (18, '人间失格', '9787506380263', '陈', 'ccy1719', '13825864005', '2022-12-07 14:19:13', NULL, 20, '已借出', 1, '2022-12-08 00:00:00');
INSERT INTO `borrow` VALUES (19, '三体', '9787229124410', '陈', 'ccy1719', '13825864005', '2022-12-07 14:20:14', NULL, 15, '已借出', 1, '2022-12-08 00:00:00');
INSERT INTO `borrow` VALUES (20, '三体', '9787229124410', '陈', 'ccy1719', '13825864005', '2022-12-07 14:20:52', NULL, 15, '已借出', 1, '2022-12-08 00:00:00');

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `pid` int(11) NULL DEFAULT NULL COMMENT '父id',
  `createtime` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `updatetime` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '小说', NULL, NULL, '2022-11-06 20:12:30', NULL);
INSERT INTO `category` VALUES (2, '社会小说', NULL, 1, '2022-11-06 20:12:35', NULL);
INSERT INTO `category` VALUES (3, '科幻小说', NULL, 1, '2022-11-06 20:12:40', '2022-11-06 00:00:00');
INSERT INTO `category` VALUES (4, '文学', NULL, NULL, '2022-11-06 20:12:49', NULL);
INSERT INTO `category` VALUES (5, '古典文学', NULL, 4, '2022-11-06 20:12:59', NULL);
INSERT INTO `category` VALUES (6, '现代文学', NULL, 4, '2022-11-06 20:13:05', NULL);

-- ----------------------------
-- Table structure for returnbook
-- ----------------------------
DROP TABLE IF EXISTS `returnbook`;
CREATE TABLE `returnbook`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bookName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图书名称',
  `bookNo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图书标准码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名称',
  `userName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名(唯一）',
  `userPhone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `score` int(10) NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `days` int(11) NULL DEFAULT 1,
  `returnDate` datetime NULL DEFAULT NULL,
  `realDate` datetime NULL DEFAULT NULL,
  `borrowDate` datetime NULL DEFAULT NULL,
  `createtime` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of returnbook
-- ----------------------------
INSERT INTO `returnbook` VALUES (1, '三体', '9787229124410', '杰克', 'ccu1872', '13825864005', 15, NULL, 3, '2022-11-25 00:00:00', NULL, NULL, '2022-11-25 00:00:00');
INSERT INTO `returnbook` VALUES (2, '人间失格', '9787506380263', '张三', 'czh1232', '13823256324', 20, '已归还', 5, '2022-11-15 00:00:00', '2022-11-23 15:01:17', NULL, '2022-11-15 00:00:00');
INSERT INTO `returnbook` VALUES (5, '三体', '9787229124410', '111', 'ccu1872', '13825864005', 15, '已归还', 3, '2022-11-25 00:00:00', '2022-11-23 15:28:00', NULL, '2022-11-25 00:00:00');
INSERT INTO `returnbook` VALUES (8, '人间失格', '9787506380263', 'chen', 'ccu1378', '18762255332', 20, '已归还', 1, '2022-11-11 00:00:00', '2022-11-23 16:41:31', '2022-11-10 00:00:00', '2022-11-11 00:00:00');
INSERT INTO `returnbook` VALUES (9, '人间失格', '9787506380263', '李四', 'czh1234', '17708644353', 20, '已归还', 3, '2022-11-26 00:00:00', '2022-11-23 16:43:41', '2022-11-23 00:00:00', '2022-11-26 00:00:00');
INSERT INTO `returnbook` VALUES (10, '三体', '9787229124410', '123', 'zxc2345', '13376622281', 15, '已归还', 2, '2022-11-12 00:00:00', '2022-11-23 16:50:42', '2022-11-10 00:00:00', '2022-11-12 00:00:00');
INSERT INTO `returnbook` VALUES (11, '人间失格', '9787506380263', '123', 'zxc2345', '13376622281', 20, '已归还', 1, '2022-11-24 00:00:00', '2022-11-23 16:58:14', '2022-11-23 00:00:00', '2022-11-24 00:00:00');
INSERT INTO `returnbook` VALUES (12, '人间失格', '9787506380263', '李四', 'czh1234', '17708644353', 20, '已归还', 1, '2022-11-24 00:00:00', NULL, '2022-11-23 00:00:00', '2022-11-24 00:00:00');
INSERT INTO `returnbook` VALUES (13, '人间失格', '9787506380263', '张三', 'gs6543', '13788866526', 60, '已归还', 3, '2022-11-22 00:00:00', NULL, '2022-11-19 00:00:00', '2022-11-22 00:00:00');
INSERT INTO `returnbook` VALUES (14, '人间失格', '9787506380263', '123', 'zxc2345', '13376622281', 40, '已归还', 2, '2022-11-10 00:00:00', NULL, '2022-11-08 00:00:00', '2022-11-10 00:00:00');
INSERT INTO `returnbook` VALUES (15, '人间失格', '9787506380263', '李四', 'czh1234', '17708644353', 40, '已归还', 2, '2022-11-20 00:00:00', NULL, '2022-11-18 00:00:00', '2022-11-20 00:00:00');
INSERT INTO `returnbook` VALUES (16, '人间失格', '9787506380263', '李四', 'czh1234', '17708644353', 40, '已归还', 2, '2022-11-20 00:00:00', NULL, '2022-11-18 00:00:00', '2022-11-20 00:00:00');
INSERT INTO `returnbook` VALUES (17, '人间失格', '9787506380263', '张三', 'czh1232', '13823256324', 60, '已归还', 3, '2022-11-20 00:00:00', NULL, '2022-11-17 00:00:00', '2022-11-20 00:00:00');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `age` int(11) NULL DEFAULT NULL,
  `sex` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '男',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `updatetime` datetime NULL DEFAULT NULL,
  `createtime` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `account` int(11) NULL DEFAULT 100 COMMENT '借书积分',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '禁用状态 1为不禁用，0为禁用',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username_key1`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'chen', 'ccu1378', 23, '男', '18762255332', 'guang', NULL, '2022-11-10 22:23:24', 10, 1, NULL, NULL);
INSERT INTO `user` VALUES (2, '张三', 'gs6543', 23, '男', '13788866526', 'shanghai', NULL, '2022-11-23 17:08:49', 30, 1, NULL, NULL);
INSERT INTO `user` VALUES (3, '123', 'zxc2345', 32, '男', '13376622281', NULL, NULL, '2022-11-23 17:10:41', 265, 1, NULL, NULL);
INSERT INTO `user` VALUES (6, '杰克', 'ccu1872', 12, '男', '13825864005', '广东', '2022-11-22 14:42:20', '2022-11-22 14:43:46', 15, 1, NULL, NULL);
INSERT INTO `user` VALUES (7, '张三', 'czh1232', 22, '男', '13823256324', '汕头', '2022-11-23 16:15:28', '2022-11-27 18:09:34', 45, 1, NULL, NULL);
INSERT INTO `user` VALUES (8, '李四', 'czh1234', 20, '男', '17708644353', NULL, NULL, '2022-11-23 17:17:17', 20, 1, NULL, NULL);
INSERT INTO `user` VALUES (9, NULL, 'ccw2684', NULL, NULL, '13825864005', NULL, NULL, '2022-11-27 15:24:03', NULL, 1, '742227202', NULL);
INSERT INTO `user` VALUES (10, '陈', 'ccy1719', 20, '男', '13825864005', '广', NULL, '2022-12-07 14:20:52', 70, 1, '742227202', '222227202@qq.com');

SET FOREIGN_KEY_CHECKS = 1;
