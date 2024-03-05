SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP DATABASE IF EXISTS collect;
CREATE DATABASE IF NOT EXISTS collect DEFAULT CHARACTER SET utf8;
USE collect;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
                         `uid` int(11) NOT NULL AUTO_INCREMENT,
                         `uname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                         `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                         `user_role` int(11) NOT NULL,
                         `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                         `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                         `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                         `ability` double(16,2) NOT NULL,
                         `preference` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                         `activity` int(11) NOT NULL,
                         `equipment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
                         `cooperation` double(16,2) NOT NULL,
                         `evaluation` double(16,2) NOT NULL,
                         `similarity` double(16,2) NOT NULL,
                         PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
INSERT INTO `user` values (1,"admin1","741236",0,"","","",0,"",1,"",0,0,0);
INSERT INTO `user` values (2,"admin2","999999",0,"","","",0,"",1,"",0,0,0);
INSERT INTO `user` values (3,"admin3","666666",0,"","","",0,"",1,"",0,0,0);
INSERT INTO `user` values (4,"admin4","333333",0,"","","",0,"",1,"",0,0,0);
INSERT INTO `user` values (5,"admin5","admin5",0,"","","",0,"",1,"",0,0,0);
INSERT INTO `user` values (6,"小明","123456",1,"18865188306","231250001@smail.nju.edu.cn","明明很爱你",1.234,"功能测试",1,"Android设备",0,0,0);
INSERT INTO `user` values (7,"小淼","654321",2,"18865188407","231250002@smail.nju.edu.cn","水沝淼㵘",0,"性能测试",1,"Linux设备",0,0,0);
INSERT INTO `user` values (8,"小晨","654321",2,"18865188602","231250003@smail.nju.edu.cn","晨晨和凛sir",0,"功能测试",1,"鸿蒙设备",0,0,0);
INSERT INTO `user` values (9,"小花","654123",2,"18865188606","231250004@smail.nju.edu.cn","那就寄",0,"功能测试",1,"IOS设备",0,0,0);
-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task`  (
     `id` int(11) NOT NULL AUTO_INCREMENT,
     `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
     `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL NULL,
     `end_time` datetime(0) NULL NULL,
     `worker_num` int(11) NOT NULL,
     `difficulty` int(11) NOT NULL,
     `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
     `equipment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
     `others` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
     `user_id` int(11) NOT NULL,
     `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
     `executable_file` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
     `doc_file` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
     `executable_file_uuid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
     `doc_file_uuid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
     PRIMARY KEY (`id`) USING BTREE,
     INDEX `user_task`(`user_id`) USING BTREE,
     CONSTRAINT `user_task` FOREIGN KEY (`user_id`) REFERENCES `user` (`uid`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
-- ----------------------------
-- Table structure for task_order
-- ----------------------------
DROP TABLE IF EXISTS `task_order`;
CREATE TABLE `task_order`  (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `user_id` int(11) NOT NULL,
   `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
   `task_id` int(11) NOT NULL,
   `task_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
   `create_time` datetime(0) NOT NULL,
   `status` int(11) NOT NULL,
   PRIMARY KEY (`id`) USING BTREE,
   INDEX `user_order`(`user_id`) USING BTREE,
   INDEX `task_order`(`task_id`) USING BTREE,
   CONSTRAINT `user_order` FOREIGN KEY (`user_id`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT `task_order` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for report
-- ----------------------------
DROP TABLE IF EXISTS `report`;
CREATE TABLE `report`  (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `user_id` int(11) NOT NULL,
   `task_id` int(11) NOT NULL,
   `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
   `step` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
   `equipment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
   `post_time` datetime(0) NOT NULL,
   `score` double(16,1) NOT NULL,
   PRIMARY KEY(`id`) USING BTREE,
   INDEX `user_report`(`user_id`) USING BTREE,
   INDEX `task_report`(`task_id`) USING BTREE,
   CONSTRAINT `user_report` FOREIGN KEY (`user_id`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT `task_report` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for supplement
-- ----------------------------
DROP TABLE IF EXISTS `supplement`;
CREATE TABLE `supplement`  (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `user_id` int(11) NOT NULL,
   `report_id` int(11) NOT NULL,
   `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
   `step` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
   `equipment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
   `post_time` datetime(0) NOT NULL,
   PRIMARY KEY(`id`) USING BTREE,
   INDEX `user_supplement`(`user_id`) USING BTREE,
   INDEX `report_supplement`(`report_id`) USING BTREE,
   CONSTRAINT `user_supplement` FOREIGN KEY (`user_id`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT `report_supplement` FOREIGN KEY (`report_id`) REFERENCES `report` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `user_id` int(11) NOT NULL,
    `report_id` int(11) NOT NULL,
    `score` int(11) NOT NULL,
    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
    `post_time` datetime(0) NOT NULL,
    PRIMARY KEY(`id`) USING BTREE,
    INDEX `user_comment`(`user_id`) USING BTREE,
    INDEX `report_comment`(`report_id`) USING BTREE,
    CONSTRAINT `user_comment` FOREIGN KEY (`user_id`) REFERENCES `user` (`uid`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `report_comment` FOREIGN KEY (`report_id`) REFERENCES `report` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rule
-- ----------------------------
DROP TABLE IF EXISTS `rule`;
CREATE TABLE `rule`  (
                            `id` int(11) NOT NULL AUTO_INCREMENT,
                            `easy_min` int(11) NOT NULL,
                            `easy_max` int(11) NOT NULL,
                            `medium_min` int(11) NOT NULL,
                            `medium_max` int(11) NOT NULL,
                            `hard_min` int(11) NOT NULL,
                            `hard_max` int(11) NOT NULL,
                            `easy_increase` double(16,1) NOT NULL,
                            `medium_increase` double(16,1) NOT NULL,
                            `hard_increase` double(16,1) NOT NULL,
                            PRIMARY KEY(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;
INSERT INTO `rule` values (1,1,4,5,7,8,10,0.0,0.1,0.2);