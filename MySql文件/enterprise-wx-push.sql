/*
 Navicat Premium Data Transfer

 Source Server         : 阿里云
 Source Server Type    : MySQL
 Source Server Version : 80026 (8.0.26)
 Source Host           : 47.113.216.83:3306
 Source Schema         : enterprise-wx-push

 Target Server Type    : MySQL
 Target Server Version : 80026 (8.0.26)
 File Encoding         : 65001

 Date: 21/03/2023 12:56:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for course_info
-- ----------------------------
DROP TABLE IF EXISTS `course_info`;
CREATE TABLE `course_info`  (
  `course_id` int NOT NULL AUTO_INCREMENT COMMENT '课程ID',
  `course_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '课程名称',
  `course_venue` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '上课地点',
  `course_period` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '课程周期',
  `course_week` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '课程星期',
  `course_section` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '课程节次',
  `course_specialized` int NULL DEFAULT NULL COMMENT '是否为专业课程',
  PRIMARY KEY (`course_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of course_info
-- ----------------------------
INSERT INTO `course_info` VALUES (1, '⚽体育', '🏟田径场、篮球场', '1-18', '1', '2', 0);
INSERT INTO `course_info` VALUES (2, '🥇HTML5与移动应用开发', '🏢信息楼305', '1-18', '1', '3-4', 1);
INSERT INTO `course_info` VALUES (3, '🥈企业级项目实战', '🏢信息楼302', '9-18', '2', '1-2', 1);
INSERT INTO `course_info` VALUES (4, '🥉Java Web应用开发', '🏢信息楼309', '1-18', '2', '3-4', 1);
INSERT INTO `course_info` VALUES (5, '💫习近平新时代中国特色社会主义思想概论', '🏗汽车楼203-B', '2-13', '4', '4', 0);
INSERT INTO `course_info` VALUES (6, '🥈企业级项目实战', '🏢信息楼302', '9-18', '4', '1-2', 1);
INSERT INTO `course_info` VALUES (7, '💤形势与政策', '🏗汽车楼401', '3-6', '4', '3', 0);
INSERT INTO `course_info` VALUES (8, '💬创业基础', '🏗机电楼401-1', '1-18', '3', '2', 0);
INSERT INTO `course_info` VALUES (9, '🥇HTML5与移动应用开发', '🏢信息楼305', '1-18', '5', '1-2', 1);
INSERT INTO `course_info` VALUES (10, '🥉Java Web应用开发', '🏢信息楼309', '1-18', '3', '3-4', 1);
INSERT INTO `course_info` VALUES (11, '🤡选修课', '🌍全校范围', '1-18', '5', '3', 0);

-- ----------------------------
-- Table structure for enterprise_data
-- ----------------------------
DROP TABLE IF EXISTS `enterprise_data`;
CREATE TABLE `enterprise_data`  (
  `data_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '企业微信配置项名称',
  `data_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '配置项数据',
  `data_annotation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`data_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of enterprise_data
-- ----------------------------
INSERT INTO `enterprise_data` VALUES ('agentId', '', '自建应用id(此参数为身份唯一标识，请保密)');
INSERT INTO `enterprise_data` VALUES ('apiKey', '', '天行数据api密钥(此参数为密钥，请保密)');
INSERT INTO `enterprise_data` VALUES ('corpId', '', '企业id(此参数为身份唯一标识，请保密)');
INSERT INTO `enterprise_data` VALUES ('dateEnding', '2023-07-31', '放假日期');
INSERT INTO `enterprise_data` VALUES ('dateStarting', '2023-02-20', '开学日期');
INSERT INTO `enterprise_data` VALUES ('debugPeriod', '', '调试周期(如不启用调试，请置空)');
INSERT INTO `enterprise_data` VALUES ('debugPushMode', '2', '调试模式下的部门推送ID，使用此推送部门ID时，不会将课程计入次数');
INSERT INTO `enterprise_data` VALUES ('debugWeek', '', '调试星期(如不启用调试，请置空)');
INSERT INTO `enterprise_data` VALUES ('departmentId', '1', '推送部门ID(此参数用于控制被推送的部门成员)');
INSERT INTO `enterprise_data` VALUES ('imgUrl', 'http://prefersmin.xyz/images/image5.jpg', '图文消息的图片url');
INSERT INTO `enterprise_data` VALUES ('morningClassDays', '3', '上过的早课天数(此项不可为空)');
INSERT INTO `enterprise_data` VALUES ('nightClassDays', '0', '上过的晚课天数(此项不可为空)');
INSERT INTO `enterprise_data` VALUES ('pushTime', '1', '推送时间(为 0 则每日早晨推送当日课程与天气，为 1 则每日夜间推送明日课程与天气)');
INSERT INTO `enterprise_data` VALUES ('secret', '', '自建应用secret(此参数为密钥，请保密)');
INSERT INTO `enterprise_data` VALUES ('totalClassTimes', '185', '累计上过的课程次数(此项不可为空)');
INSERT INTO `enterprise_data` VALUES ('totalSpecializedClassTimes', '132', '累计上过的专业课程次数(此项不可为空)');
INSERT INTO `enterprise_data` VALUES ('url', 'http://prefersmin.xyz/', '卡片消息的跳转url');
INSERT INTO `enterprise_data` VALUES ('weatherValue', '101250301', '天气推送位置(详见天行数据天气预报api文档)');

SET FOREIGN_KEY_CHECKS = 1;
