/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : ansheng

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 17/12/2022 00:24:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for des_group
-- ----------------------------
DROP TABLE IF EXISTS `des_group`;
CREATE TABLE `des_group`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `chnName` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '英文名称',
  `prefix` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '前缀',
  `sortNumber` int(0) NOT NULL COMMENT '排序(决定排列顺序)',
  `memo` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_name`(`name`) USING BTREE,
  UNIQUE INDEX `idx_chnName`(`chnName`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '分组表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of des_group
-- ----------------------------
INSERT INTO `des_group` VALUES (7, 'test', '用于测试的分组', 'tst', 1, '');

-- ----------------------------
-- Table structure for des_table
-- ----------------------------
DROP TABLE IF EXISTS `des_table`;
CREATE TABLE `des_table`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `chnName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '中文名称',
  `belong` bigint(0) NOT NULL COMMENT '分组',
  `sortNumber` int(0) NOT NULL COMMENT '排列序号',
  `treatment` tinyint(0) NOT NULL COMMENT '处理方式（1.树形数据；2.全部显示；3.分页显示）',
  `treatmentField` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '处理字段（分页给出的是查询条件字段，树形给出的是形成树形结构的字段，树形默认字段为code，分页默认字段为name）',
  `camelName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '驼峰名称',
  `pascalName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '帕斯卡名称',
  `sortField` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `memo` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_name`(`name`) USING BTREE,
  UNIQUE INDEX `idx_chnName`(`chnName`) USING BTREE,
  INDEX `fk_blong_group`(`belong`) USING BTREE,
  CONSTRAINT `des_table_ibfk_1` FOREIGN KEY (`belong`) REFERENCES `des_group` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 78 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '表单管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of des_table
-- ----------------------------
INSERT INTO `des_table` VALUES (73, 'tst_allshow', '显示所有信息', 7, 1, 2, '', 'tstAllshow', 'TstAllshow', 'fsort', '');
INSERT INTO `des_table` VALUES (76, 'tst_pageshow', '分页显示信息', 7, 5, 3, '', 'tstPageshow', 'TstPageshow', '', '');
INSERT INTO `des_table` VALUES (77, 'tst_treeshow', '树形显示信息', 7, 3, 1, 'fcode', 'tstTreeshow', 'TstTreeshow', 'fsort', '');

-- ----------------------------
-- Table structure for des_table_field
-- ----------------------------
DROP TABLE IF EXISTS `des_table_field`;
CREATE TABLE `des_table_field`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `table_id` bigint(0) NOT NULL COMMENT '表单id',
  `fldName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字段名称',
  `fldNameChn` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '中文名称',
  `fldType` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '字段类型',
  `fldLength` int(0) NOT NULL COMMENT '字段长度（整型这些长度是指的显示长度，在fillzero有效的前提下）',
  `fldPoint` int(0) NOT NULL COMMENT '小数位数',
  `fldNull` bit(1) NOT NULL COMMENT '是否可空（1可空，0不可空）',
  `fldAlias` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '别名（从哪个表来的？a表示从本表中来，b表示从第一个外键表来，c表示从第二个外键表来，以此类推，a后面跟.字段表示从哪个表的哪个字段来，b、c等要与外键关联）',
  `fldAliasField` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '别名字段（这个字段从别名里取出的字段名称）\\n',
  `javaType` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'java类型（转换成java的数据表达，比如Long，Integer,double,float,string 等等）',
  `treatment` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '处理方式（1、自动增长，2、外表选择，3、直接选择，4、弹出选择，5、隐藏）',
  `directChoice` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '直选选项（如果是直接选择的话，给出选项，用英文”;“分隔开）',
  `choiceTable` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '选择表单',
  `choiceAlias` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '选择别名（要选择的表的别名）',
  `choiceFieldShow` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '显示字段（给用户看的字段，用于选择）',
  `choiceFieldData` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '取值字段（选择的时候，这个字段代表所取的值）',
  `onUpdate` tinyint(0) NULL DEFAULT NULL,
  `onDelete` tinyint(0) NULL DEFAULT NULL,
  `fkName` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sortNumber` int(0) NOT NULL,
  `memo` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '备注（如果是直接选择的话，给出选项，用英文”;“分隔开）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_table_id_fldName`(`table_id`, `fldName`) USING BTREE,
  CONSTRAINT `des_table_field_ibfk_1` FOREIGN KEY (`table_id`) REFERENCES `des_table` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 666 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '表单字段管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of des_table_field
-- ----------------------------
INSERT INTO `des_table_field` VALUES (642, 73, 'id', 'id', 'BIGINT', 20, 0, b'0', 'a', '', 'Long', '自动增长', '', '', '', '', '', 0, 0, '', 1, '');
INSERT INTO `des_table_field` VALUES (643, 73, 'memo', '备注', 'VARCHAR', 50, 0, b'1', 'a', '', 'String', '数据输入', '', '', '', '', '', 0, 0, '', 200, '');
INSERT INTO `des_table_field` VALUES (644, 73, 'fname', '民族名称', 'VARCHAR', 20, 0, b'0', 'a', '', 'String', '数据输入', '', '', '', '', '', 0, 0, '', 5, '');
INSERT INTO `des_table_field` VALUES (646, 73, 'fcode', '民族编码', 'CHAR', 2, 0, b'0', 'a', '', 'String', '数据输入', '', '', '', '', '', 0, 0, '', 3, '');
INSERT INTO `des_table_field` VALUES (648, 76, 'id', 'id', 'BIGINT', 20, 0, b'0', 'a', '', 'Long', '自动增长', '', '', '', '', '', 0, 0, '', 1, '');
INSERT INTO `des_table_field` VALUES (650, 76, 'fname', '姓名', 'VARCHAR', 20, 0, b'0', 'a', '', 'String', '数据输入', '', '', '', '', '', 0, 0, '', 5, '');
INSERT INTO `des_table_field` VALUES (651, 76, 'fgender', '性别', 'CHAR', 1, 0, b'0', 'a', '', 'String', '直接选择', '男;女', '', '', '', '', 0, 0, '', 10, '');
INSERT INTO `des_table_field` VALUES (652, 76, 'fage', '年龄', 'BIGINT', 0, 0, b'1', 'a', '', 'String', '数据输入', '', '', '', '', '', 0, 0, '', 20, '');
INSERT INTO `des_table_field` VALUES (653, 76, 'memo', '备注', 'VARCHAR', 50, 0, b'1', 'a', '', 'String', '数据输入', '', '', '', '', '', 0, 0, '', 200, '');
INSERT INTO `des_table_field` VALUES (654, 77, 'id', 'id', 'BIGINT', 20, 0, b'0', 'a', '', 'Long', '自动增长', '', '', '', '', '', 0, 0, '', 1, '');
INSERT INTO `des_table_field` VALUES (655, 77, 'fcode', '部门编码', 'VARCHAR', 20, 0, b'0', 'a', '', 'String', '数据输入', '', '', '', '', '', 0, 0, '', 3, '');
INSERT INTO `des_table_field` VALUES (656, 77, 'fname', '部门名称', 'VARCHAR', 20, 0, b'0', 'a', '', 'String', '数据输入', '', '', '', '', '', 0, 0, '', 5, '');
INSERT INTO `des_table_field` VALUES (658, 77, 'fsort', '排序数值', 'BIGINT', 0, 0, b'0', 'a', '', 'Long', '数据输入', '', '', '', '', '', 0, 0, '', 20, '');
INSERT INTO `des_table_field` VALUES (659, 77, 'memo', '备注', 'VARCHAR', 50, 0, b'1', 'a', '', 'String', '数据输入', '', '', '', '', '', 0, 0, '', 200, '');
INSERT INTO `des_table_field` VALUES (660, 73, 'fsort', '排序数值', 'BIGINT', 0, 0, b'0', 'a', '', 'Long', '数据输入', '', '', '', '', '', 0, 0, '', 20, '');
INSERT INTO `des_table_field` VALUES (661, 76, 'deptId', '部门Id', 'BIGINT', 0, 0, b'0', 'a', '', 'Long', '数据输入', '', 'tst_treeshow', 'b', 'fname', 'id', 1, 1, 'fk_tst_pageshow_deptId', 2, '');
INSERT INTO `des_table_field` VALUES (662, 76, 'deptId_fname', '部门名称', 'VARCHAR', 20, 0, b'0', 'b', 'fname', 'String', '数据输入', '', '', '', '', '', 0, 0, '', 3, '');
INSERT INTO `des_table_field` VALUES (663, 76, 'deptId_fcode', '部门编码', 'VARCHAR', 0, 0, b'1', 'b', 'fcode', 'String', '数据输入', '', '', '', '', '', 0, 0, '', 4, '');
INSERT INTO `des_table_field` VALUES (664, 76, 'fnation', '民族', 'BIGINT', 0, 0, b'0', 'a', '', 'Long', '外表选择', '', 'tst_allshow', 'c', 'fname', 'id', 1, 1, 'fk_tst_pageshow_fnation', 30, '');
INSERT INTO `des_table_field` VALUES (665, 76, 'fnation_fname', '民族名称', 'VARCHAR', 20, 0, b'0', 'c', 'fname', 'String', '数据输入', '', '', '', '', '', 0, 0, '', 31, '');

-- ----------------------------
-- Table structure for des_table_index
-- ----------------------------
DROP TABLE IF EXISTS `des_table_index`;
CREATE TABLE `des_table_index`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `table_id` bigint(0) NOT NULL,
  `indexName` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '索引名称',
  `IndexType` tinyint(0) NOT NULL COMMENT '索引类型（1表示主键，2表示唯一索引，3表示普通索引，4表示……）',
  `field1` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '第一字段',
  `field2` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '第二字段',
  `field3` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '第三字段',
  `field4` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '第四字段',
  `field5` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '第五字段',
  `sortNumber` int(0) NOT NULL,
  `memo` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_table_id_indexName`(`table_id`, `indexName`) USING BTREE,
  CONSTRAINT `des_table_index_ibfk_1` FOREIGN KEY (`table_id`) REFERENCES `des_table` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 113 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '表单索引管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of des_table_index
-- ----------------------------
INSERT INTO `des_table_index` VALUES (105, 73, 'pk_id', 1, 'id', '', '', '', '', 1, '');
INSERT INTO `des_table_index` VALUES (106, 73, 'idx_fcode', 2, 'fcode', '', '', '', '', 5, '');
INSERT INTO `des_table_index` VALUES (107, 73, 'idx_fname', 2, 'fname', '', '', '', '', 10, '');
INSERT INTO `des_table_index` VALUES (108, 77, 'pk_id', 1, 'id', '', '', '', '', 1, '');
INSERT INTO `des_table_index` VALUES (109, 77, 'idx_fcode', 2, 'fcode', '', '', '', '', 2, '');
INSERT INTO `des_table_index` VALUES (110, 77, 'idx_fname', 2, 'fname', '', '', '', '', 3, '');
INSERT INTO `des_table_index` VALUES (111, 76, 'pk_id', 1, 'id', '', '', '', '', 1, '');
INSERT INTO `des_table_index` VALUES (112, 76, 'idx_fname', 2, 'fname', '', '', '', '', 2, '');

-- ----------------------------
-- Table structure for des_table_select
-- ----------------------------
DROP TABLE IF EXISTS `des_table_select`;
CREATE TABLE `des_table_select`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `table_id` bigint(0) NOT NULL,
  `selectType` tinyint(0) NOT NULL COMMENT '查询类型（1表示普通，2表示极简，3表示复杂（最多的字段））',
  `selectSQL` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '选择列表',
  `selectFrom` varchar(800) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '选择出处',
  `memo` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_table_id_type`(`table_id`, `selectType`) USING BTREE,
  CONSTRAINT `des_table_select_ibfk_1` FOREIGN KEY (`table_id`) REFERENCES `des_table` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 109 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '表单查询语句管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of des_table_select
-- ----------------------------
INSERT INTO `des_table_select` VALUES (105, 73, 1, 'select a.id id,a.fcode fcode,a.fname fname,a.fsort fsort,a.memo memo', ' from tst_allshow a', '');
INSERT INTO `des_table_select` VALUES (106, 77, 1, 'select a.id id,a.fcode fcode,a.fname fname,a.fsort fsort,a.memo memo', ' from tst_treeshow a', '');
INSERT INTO `des_table_select` VALUES (108, 76, 1, 'select a.id id,a.deptId deptId,b.fname deptId_fname,b.fcode deptId_fcode,a.fname fname,a.fgender fgender,a.fage fage,a.fnation fnation,c.fname fnation_fname,a.memo memo', ' from tst_pageshow a left outer join tst_treeshow b on a.deptId = b.id left outer join tst_allshow c on a.fnation = c.id', '');

-- ----------------------------
-- Table structure for dic_classrole
-- ----------------------------
DROP TABLE IF EXISTS `dic_classrole`;
CREATE TABLE `dic_classrole`  (
  `fclass_role_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '班级干部编号',
  `class_role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '班级干部名称',
  `memo` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`fclass_role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dic_classrole
-- ----------------------------
INSERT INTO `dic_classrole` VALUES (1, '班长', NULL);
INSERT INTO `dic_classrole` VALUES (2, '团支书', NULL);
INSERT INTO `dic_classrole` VALUES (3, '学习委员', NULL);
INSERT INTO `dic_classrole` VALUES (4, '生活委员', NULL);
INSERT INTO `dic_classrole` VALUES (5, '体育委员', NULL);
INSERT INTO `dic_classrole` VALUES (6, '信息委员', NULL);
INSERT INTO `dic_classrole` VALUES (7, '纪检委员', NULL);
INSERT INTO `dic_classrole` VALUES (8, '文艺委员', NULL);
INSERT INTO `dic_classrole` VALUES (10, '普通学生', NULL);

-- ----------------------------
-- Table structure for dic_healthgrade
-- ----------------------------
DROP TABLE IF EXISTS `dic_healthgrade`;
CREATE TABLE `dic_healthgrade`  (
  `fhealth_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '健康等级编号',
  `fhealth` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '健康等级',
  PRIMARY KEY (`fhealth_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dic_healthgrade
-- ----------------------------
INSERT INTO `dic_healthgrade` VALUES (1, '健康');
INSERT INTO `dic_healthgrade` VALUES (2, '良好');
INSERT INTO `dic_healthgrade` VALUES (3, '生病');
INSERT INTO `dic_healthgrade` VALUES (4, '病危');

-- ----------------------------
-- Table structure for dic_healthstatus
-- ----------------------------
DROP TABLE IF EXISTS `dic_healthstatus`;
CREATE TABLE `dic_healthstatus`  (
  `fstatus_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '健康码编号',
  `fstatus_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '健康码',
  PRIMARY KEY (`fstatus_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dic_healthstatus
-- ----------------------------
INSERT INTO `dic_healthstatus` VALUES (1, '绿码');
INSERT INTO `dic_healthstatus` VALUES (2, '黄码');
INSERT INTO `dic_healthstatus` VALUES (3, '红码');
INSERT INTO `dic_healthstatus` VALUES (4, '弹窗');

-- ----------------------------
-- Table structure for dic_nationality
-- ----------------------------
DROP TABLE IF EXISTS `dic_nationality`;
CREATE TABLE `dic_nationality`  (
  `fnationality_id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '名族编号',
  `fnationality_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名族名称',
  `memo` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`fnationality_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dic_nationality
-- ----------------------------
INSERT INTO `dic_nationality` VALUES (1, '汉族', NULL);
INSERT INTO `dic_nationality` VALUES (2, '苗族', NULL);

-- ----------------------------
-- Table structure for dic_politics
-- ----------------------------
DROP TABLE IF EXISTS `dic_politics`;
CREATE TABLE `dic_politics`  (
  `fcode` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '政治面貌编号',
  `fname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '政治面貌名称',
  `fsort_number` int(0) NULL DEFAULT NULL COMMENT '排序',
  `memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`fcode`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dic_politics
-- ----------------------------
INSERT INTO `dic_politics` VALUES (1, '人民群众', 1, NULL);
INSERT INTO `dic_politics` VALUES (2, '共青团员', 2, NULL);
INSERT INTO `dic_politics` VALUES (3, '入党积极分子', 3, NULL);
INSERT INTO `dic_politics` VALUES (4, '预备党员', 4, NULL);
INSERT INTO `dic_politics` VALUES (5, '党员', 5, NULL);

-- ----------------------------
-- Table structure for stu_accommodation
-- ----------------------------
DROP TABLE IF EXISTS `stu_accommodation`;
CREATE TABLE `stu_accommodation`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `fstudent_id` bigint(0) NOT NULL COMMENT '学生id',
  `fdormitory_id` bigint(0) NOT NULL COMMENT '宿舍id',
  `fbed` int(0) NOT NULL COMMENT '床位',
  `fin` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '是否在宿舍',
  `memo` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fdormitoryId_2`(`fdormitory_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu_accommodation
-- ----------------------------
INSERT INTO `stu_accommodation` VALUES (2, 3, 1, 101, '1', NULL);

-- ----------------------------
-- Table structure for stu_acid
-- ----------------------------
DROP TABLE IF EXISTS `stu_acid`;
CREATE TABLE `stu_acid`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `fstudent_id` bigint(0) NOT NULL COMMENT '学生id',
  `fsample_time` datetime(0) NOT NULL COMMENT '采样时间',
  `ftest_time` datetime(0) NOT NULL COMMENT '检测时间',
  `ftest_unit` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '检测单位',
  `fresult` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '检测结果',
  `ftest_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '采样地点',
  `memo` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu_acid
-- ----------------------------
INSERT INTO `stu_acid` VALUES (1, 1, '2022-11-26 16:24:59', '2022-11-26 16:25:05', '重庆财经学院', '正常', '重庆财经学院', NULL);

-- ----------------------------
-- Table structure for stu_antiepidemic
-- ----------------------------
DROP TABLE IF EXISTS `stu_antiepidemic`;
CREATE TABLE `stu_antiepidemic`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `fstudent_id` int(0) NOT NULL COMMENT '学生id',
  `fin_campus` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '是否在校',
  `flocation` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '目前位置',
  `fleave_time` datetime(0) NULL DEFAULT NULL COMMENT '离校时间',
  `fhealth_id` bigint(0) NULL DEFAULT NULL COMMENT '健康等级',
  `fhealth_memo` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '健康描述',
  `fstatus_id` bigint(0) NULL DEFAULT NULL COMMENT '健康码',
  `fvaccin_completed` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '接种完全',
  `fvaccin_memo` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '接种描述',
  `fjourney` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '行程',
  `memo` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fhealthId_2`(`fhealth_id`) USING BTREE,
  INDEX `fstatusID_2`(`fstatus_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu_antiepidemic
-- ----------------------------
INSERT INTO `stu_antiepidemic` VALUES (1, 1, '是', '重庆财经学院', '2022-12-22 18:34:08', 1, NULL, 1, '是', NULL, NULL, '');

-- ----------------------------
-- Table structure for stu_dormitory
-- ----------------------------
DROP TABLE IF EXISTS `stu_dormitory`;
CREATE TABLE `stu_dormitory`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `fcode` bigint(0) NOT NULL COMMENT '宿舍编码',
  `fname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '宿舍名称',
  `fcapacity` int(0) NOT NULL COMMENT '宿舍容量',
  `memo` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fcode`(`fcode`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu_dormitory
-- ----------------------------
INSERT INTO `stu_dormitory` VALUES (1, 204, '软件班', 6, NULL);
INSERT INTO `stu_dormitory` VALUES (2, 12, '123', 3, NULL);

-- ----------------------------
-- Table structure for stu_student
-- ----------------------------
DROP TABLE IF EXISTS `stu_student`;
CREATE TABLE `stu_student`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '学生Id',
  `fcode` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学号',
  `fname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名',
  `fgender` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '性别',
  `fmobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机',
  `fcontact` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系方式',
  `faddress` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '家庭住址',
  `ffather_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '父亲姓名',
  `ffather_mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '父亲电话',
  `ffather_memo` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '父亲备注',
  `fid_code` char(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '身份证',
  `fpolitics_id` bigint(0) NOT NULL COMMENT '政治面貌编号',
  `fclass_role_id` bigint(0) NOT NULL COMMENT '班级干部编号',
  `fnationality_id` bigint(0) NOT NULL COMMENT '民族编号',
  `memo` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fstudentId_acco`(`id`) USING BTREE,
  INDEX `fcode_1`(`fpolitics_id`) USING BTREE,
  INDEX `fclassRoleId_1`(`fclass_role_id`) USING BTREE,
  INDEX `fnationalityId_1`(`fnationality_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu_student
-- ----------------------------
INSERT INTO `stu_student` VALUES (2, '432432', '423432', '男', '23432', '32423', '4234', '23432', '342423', '4234', 'eqweqw', 3, 4, 2, '2432');
INSERT INTO `stu_student` VALUES (3, '2020750235', '蒲锐', '男', '12321', '123', '1231', '123123', '13213', '123123', '1121212', 1, 1, 1, '2131');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '部门编码（通过机构编码形成层次结构，每层占2位编码）',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门名称',
  `memo` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '备注',
  `createBy` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建人',
  `createTime` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateBy` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新人',
  `lastUpdateTime` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted` tinyint(0) NOT NULL DEFAULT 0 COMMENT '是否删除（逻辑删除，保留历史记录，但是为逻辑删除标志，1删除，0未删除）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `code_UNIQUE`(`code`) USING BTREE,
  UNIQUE INDEX `name_UNIQUE`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 173 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '系统部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (12, '01', 'XXX学院', '顶级部门', '', '2021-07-21 02:01:50', 'admin', '2021-12-27 15:45:25', 0);
INSERT INTO `sys_dept` VALUES (154, '0101', 'XXX大队', '', 'admin', '2021-08-25 21:16:56', 'admin', '2021-12-27 15:45:33', 0);
INSERT INTO `sys_dept` VALUES (165, '010101', 'XXX室', '', 'admin', '2021-08-29 15:38:12', 'admin', '2021-12-27 15:45:49', 0);

-- ----------------------------
-- Table structure for sys_function
-- ----------------------------
DROP TABLE IF EXISTS `sys_function`;
CREATE TABLE `sys_function`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '功能编码（两位形成一个层次，中间用:分隔开，每个controller占据2位,这个也是授权的验证值）',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '功能名称',
  `authority` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `type` tinyint(0) NULL DEFAULT NULL COMMENT '功能类型（0为目录1为功能）',
  `memo` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  `createBy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人',
  `createTime` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateBy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新人',
  `lastUpdateTime` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `code_UNIQUE`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '系统功能表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_function
-- ----------------------------
INSERT INTO `sys_function` VALUES (8, '01', '系统功能', '', 1, '', 'admin', '2021-09-03 21:18:14', 'admin', '2021-09-14 22:31:46');
INSERT INTO `sys_function` VALUES (9, '0101', '用户管理1', '', 1, '', 'admin', '2021-09-03 21:19:57', 'admin', '2021-09-20 10:36:34');
INSERT INTO `sys_function` VALUES (10, '010101', '增加', 'sys:user:add', 0, '', 'admin', '2021-09-03 21:20:11', 'admin', '2021-09-14 22:32:10');
INSERT INTO `sys_function` VALUES (11, '010102', '修改', 'sys:user:edit', 0, '', 'admin', '2021-09-03 21:20:31', 'admin', '2021-09-14 22:32:51');
INSERT INTO `sys_function` VALUES (12, '010103', '删除', 'sys:user:delete', 0, '', 'admin', '2021-09-03 21:20:40', 'admin', '2021-09-14 22:32:51');
INSERT INTO `sys_function` VALUES (14, '02', '产品管理', '', 1, '', 'admin', '2021-09-03 21:21:18', 'admin', '2021-09-14 22:31:51');
INSERT INTO `sys_function` VALUES (15, '0201', '基础信息', '', 1, '', 'admin', '2021-09-06 16:46:45', 'admin', '2021-09-14 22:32:51');
INSERT INTO `sys_function` VALUES (16, '020101', '新增', '', 0, '', 'admin', '2021-09-06 16:46:58', NULL, '2021-09-14 22:32:51');
INSERT INTO `sys_function` VALUES (17, '020102', '修改', '', 0, '', 'admin', '2021-09-06 16:47:09', NULL, '2021-09-14 22:32:51');
INSERT INTO `sys_function` VALUES (18, '010203', '删除', '', 0, '', 'admin', '2021-09-06 16:47:36', NULL, '2021-09-14 22:32:51');
INSERT INTO `sys_function` VALUES (21, '020103', '删除', '', 0, '', 'admin', '2021-09-06 23:46:36', NULL, '2021-09-14 22:32:51');
INSERT INTO `sys_function` VALUES (23, '03', '合法回复', '', 1, '', 'admin', '2021-09-08 23:56:58', 'admin', '2021-09-14 22:31:57');
INSERT INTO `sys_function` VALUES (24, '0301', '系统阿发', '', 1, '', 'admin', '2021-09-08 23:57:18', NULL, '2021-09-14 22:33:55');
INSERT INTO `sys_function` VALUES (25, '030101', '是发发', '', 0, '', 'admin', '2021-09-08 23:57:32', NULL, '2021-09-14 22:33:55');
INSERT INTO `sys_function` VALUES (26, '010104', '查询', 'sys:user:findTree', 0, '', 'admin', '2021-09-20 10:37:20', NULL, NULL);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `code` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '菜单编号（两位一级，形成层次结构）',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '菜单名称',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '菜单图标',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '菜单URL（某些地方可能用得上，类型：1.普通页面（如用户管理， /sys/user） 2.嵌套完整外部页面，以http(s)开头的链接 3.嵌套服务器页面，使用iframe:前缀+目标URL(如SQL监控， iframe:/druid/login.html, iframe:前缀会替换成服务器地址)）',
  `type` tinyint(0) NULL DEFAULT NULL COMMENT '菜单类型（0目录1功能2链接）',
  `memo` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `createBy` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人',
  `createTime` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateBy` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新人',
  `lastUpdateTime` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `code_UNIQUE`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 59 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '系统菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (48, '05', '学生成绩信息', 'el-icon-s-custom', '', 0, '', 'admin', '2021-12-27 16:17:06', NULL, '2021-12-27 16:17:06');
INSERT INTO `sys_menu` VALUES (54, '06', '系统管理', 'el-icon-setting', '', 0, '', 'admin', '2021-12-27 16:19:16', NULL, '2021-12-27 16:19:16');
INSERT INTO `sys_menu` VALUES (55, '0601', '部门管理', 'el-icon-grape', '/sys/dept', 2, '', 'admin', '2021-12-27 16:19:51', 'admin', '2021-12-27 16:23:44');
INSERT INTO `sys_menu` VALUES (56, '0602', '用户管理', 'el-icon-refrigerator', '/sys/user', 2, '', 'admin', '2021-12-27 16:20:19', 'admin', '2021-12-27 16:23:30');
INSERT INTO `sys_menu` VALUES (57, '0603', '角色管理', 'el-icon-goblet-square-full', '/sys/role', 2, '', 'admin', '2021-12-27 16:20:48', 'admin', '2021-12-27 16:22:58');
INSERT INTO `sys_menu` VALUES (58, '0604', '菜单管理', 'el-icon-orange', '/sys/menu', 2, '', 'admin', '2021-12-27 16:21:44', 'admin', '2021-12-27 16:22:29');

-- ----------------------------
-- Table structure for sys_menu_function
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_function`;
CREATE TABLE `sys_menu_function`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `menu_id` bigint(0) NOT NULL COMMENT '菜单id',
  `func_id` bigint(0) NOT NULL COMMENT '功能id',
  `memo` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  `createBy` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人',
  `createTime` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateBy` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新人',
  `lastUpdateTime` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `menuid_funcid_qunique`(`menu_id`, `func_id`) USING BTREE,
  INDEX `fk_sys_function_idx`(`func_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 76 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '系统菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu_function
-- ----------------------------
INSERT INTO `sys_menu_function` VALUES (75, 7, 10, '', 'admin', '2021-09-20 11:12:06', NULL, NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `code` char(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色编码（形成排序）',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色名称',
  `memo` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  `createBy` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人',
  `createTime` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateBy` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新人',
  `lastUpdateTime` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `code_UNIQUE`(`code`) USING BTREE,
  UNIQUE INDEX `rolename_UNIQUE`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '系统角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (5, '0001', '系统管理员', '', 'admin', '2021-09-21 19:48:11', 'admin', '2021-09-21 23:48:26');
INSERT INTO `sys_role` VALUES (6, '0002', '一般管理员', '', 'admin', '2021-09-21 19:48:25', NULL, NULL);
INSERT INTO `sys_role` VALUES (7, '0003', '操作员', '', 'admin', '2021-12-27 16:41:34', NULL, NULL);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` bigint(0) NOT NULL COMMENT '角色id',
  `menu_id` bigint(0) NOT NULL COMMENT '菜单id',
  `memo` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  `createBy` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人',
  `createTime` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateBy` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新人',
  `lastUpdateTime` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sys_role_menu_sys_role_role_id_idx`(`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 229 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '系统菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (105, 2, 12, '', 'admin', '2021-09-21 19:47:25', NULL, '2021-09-21 19:47:25');
INSERT INTO `sys_role_menu` VALUES (106, 2, 13, '', 'admin', '2021-09-21 19:47:25', NULL, '2021-09-21 19:47:25');
INSERT INTO `sys_role_menu` VALUES (107, 2, 14, '', 'admin', '2021-09-21 19:47:25', NULL, '2021-09-21 19:47:25');
INSERT INTO `sys_role_menu` VALUES (108, 2, 15, '', 'admin', '2021-09-21 19:47:25', NULL, '2021-09-21 19:47:25');
INSERT INTO `sys_role_menu` VALUES (109, 2, 6, '', 'admin', '2021-09-21 19:47:25', NULL, '2021-09-21 19:47:25');
INSERT INTO `sys_role_menu` VALUES (110, 2, 11, '', 'admin', '2021-09-21 19:47:25', NULL, '2021-09-21 19:47:25');
INSERT INTO `sys_role_menu` VALUES (111, 3, 15, '', 'admin', '2021-09-21 19:47:29', NULL, '2021-09-21 19:47:29');
INSERT INTO `sys_role_menu` VALUES (112, 3, 6, '', 'admin', '2021-09-21 19:47:29', NULL, '2021-09-21 19:47:29');
INSERT INTO `sys_role_menu` VALUES (123, 5, 6, '', 'admin', '2021-12-27 16:40:52', NULL, '2021-12-27 16:40:52');
INSERT INTO `sys_role_menu` VALUES (124, 5, 11, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (125, 5, 15, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (126, 5, 17, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (127, 5, 18, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (128, 5, 19, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (129, 5, 20, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (130, 5, 21, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (131, 5, 22, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (132, 5, 23, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (133, 5, 24, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (134, 5, 25, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (135, 5, 26, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (136, 5, 27, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (137, 5, 28, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (138, 5, 29, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (139, 5, 30, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (140, 5, 31, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (141, 5, 32, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (142, 5, 33, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (143, 5, 34, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (144, 5, 35, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (145, 5, 36, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (146, 5, 37, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (147, 5, 38, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (148, 5, 39, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (149, 5, 40, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (150, 5, 41, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (151, 5, 42, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (152, 5, 43, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (153, 5, 44, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (154, 5, 45, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (155, 5, 46, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (156, 5, 47, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (157, 5, 48, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (158, 5, 49, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (159, 5, 50, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (160, 5, 51, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (161, 5, 52, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (162, 5, 53, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (163, 5, 54, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (164, 5, 55, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (165, 5, 56, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (166, 5, 57, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (167, 5, 58, '', 'admin', '2021-12-27 16:40:53', NULL, '2021-12-27 16:40:53');
INSERT INTO `sys_role_menu` VALUES (168, 6, 6, '', 'admin', '2021-12-27 16:41:15', NULL, '2021-12-27 16:41:15');
INSERT INTO `sys_role_menu` VALUES (169, 6, 11, '', 'admin', '2021-12-27 16:41:15', NULL, '2021-12-27 16:41:15');
INSERT INTO `sys_role_menu` VALUES (170, 6, 15, '', 'admin', '2021-12-27 16:41:15', NULL, '2021-12-27 16:41:15');
INSERT INTO `sys_role_menu` VALUES (171, 6, 17, '', 'admin', '2021-12-27 16:41:15', NULL, '2021-12-27 16:41:15');
INSERT INTO `sys_role_menu` VALUES (172, 6, 18, '', 'admin', '2021-12-27 16:41:15', NULL, '2021-12-27 16:41:15');
INSERT INTO `sys_role_menu` VALUES (173, 6, 19, '', 'admin', '2021-12-27 16:41:15', NULL, '2021-12-27 16:41:15');
INSERT INTO `sys_role_menu` VALUES (174, 6, 20, '', 'admin', '2021-12-27 16:41:16', NULL, '2021-12-27 16:41:16');
INSERT INTO `sys_role_menu` VALUES (175, 6, 21, '', 'admin', '2021-12-27 16:41:16', NULL, '2021-12-27 16:41:16');
INSERT INTO `sys_role_menu` VALUES (176, 6, 22, '', 'admin', '2021-12-27 16:41:16', NULL, '2021-12-27 16:41:16');
INSERT INTO `sys_role_menu` VALUES (177, 6, 23, '', 'admin', '2021-12-27 16:41:16', NULL, '2021-12-27 16:41:16');
INSERT INTO `sys_role_menu` VALUES (178, 6, 24, '', 'admin', '2021-12-27 16:41:16', NULL, '2021-12-27 16:41:16');
INSERT INTO `sys_role_menu` VALUES (179, 6, 25, '', 'admin', '2021-12-27 16:41:16', NULL, '2021-12-27 16:41:16');
INSERT INTO `sys_role_menu` VALUES (180, 6, 26, '', 'admin', '2021-12-27 16:41:16', NULL, '2021-12-27 16:41:16');
INSERT INTO `sys_role_menu` VALUES (181, 6, 27, '', 'admin', '2021-12-27 16:41:16', NULL, '2021-12-27 16:41:16');
INSERT INTO `sys_role_menu` VALUES (182, 6, 28, '', 'admin', '2021-12-27 16:41:16', NULL, '2021-12-27 16:41:16');
INSERT INTO `sys_role_menu` VALUES (183, 6, 29, '', 'admin', '2021-12-27 16:41:16', NULL, '2021-12-27 16:41:16');
INSERT INTO `sys_role_menu` VALUES (184, 6, 30, '', 'admin', '2021-12-27 16:41:16', NULL, '2021-12-27 16:41:16');
INSERT INTO `sys_role_menu` VALUES (185, 6, 31, '', 'admin', '2021-12-27 16:41:16', NULL, '2021-12-27 16:41:16');
INSERT INTO `sys_role_menu` VALUES (186, 6, 32, '', 'admin', '2021-12-27 16:41:16', NULL, '2021-12-27 16:41:16');
INSERT INTO `sys_role_menu` VALUES (187, 6, 33, '', 'admin', '2021-12-27 16:41:16', NULL, '2021-12-27 16:41:16');
INSERT INTO `sys_role_menu` VALUES (188, 6, 34, '', 'admin', '2021-12-27 16:41:16', NULL, '2021-12-27 16:41:16');
INSERT INTO `sys_role_menu` VALUES (189, 6, 35, '', 'admin', '2021-12-27 16:41:16', NULL, '2021-12-27 16:41:16');
INSERT INTO `sys_role_menu` VALUES (190, 6, 36, '', 'admin', '2021-12-27 16:41:16', NULL, '2021-12-27 16:41:16');
INSERT INTO `sys_role_menu` VALUES (191, 6, 37, '', 'admin', '2021-12-27 16:41:16', NULL, '2021-12-27 16:41:16');
INSERT INTO `sys_role_menu` VALUES (192, 6, 38, '', 'admin', '2021-12-27 16:41:16', NULL, '2021-12-27 16:41:16');
INSERT INTO `sys_role_menu` VALUES (193, 6, 39, '', 'admin', '2021-12-27 16:41:16', NULL, '2021-12-27 16:41:16');
INSERT INTO `sys_role_menu` VALUES (194, 6, 40, '', 'admin', '2021-12-27 16:41:16', NULL, '2021-12-27 16:41:16');
INSERT INTO `sys_role_menu` VALUES (195, 6, 41, '', 'admin', '2021-12-27 16:41:16', NULL, '2021-12-27 16:41:16');
INSERT INTO `sys_role_menu` VALUES (196, 6, 42, '', 'admin', '2021-12-27 16:41:16', NULL, '2021-12-27 16:41:16');
INSERT INTO `sys_role_menu` VALUES (197, 6, 43, '', 'admin', '2021-12-27 16:41:16', NULL, '2021-12-27 16:41:16');
INSERT INTO `sys_role_menu` VALUES (198, 6, 44, '', 'admin', '2021-12-27 16:41:16', NULL, '2021-12-27 16:41:16');
INSERT INTO `sys_role_menu` VALUES (199, 6, 45, '', 'admin', '2021-12-27 16:41:16', NULL, '2021-12-27 16:41:16');
INSERT INTO `sys_role_menu` VALUES (200, 6, 46, '', 'admin', '2021-12-27 16:41:16', NULL, '2021-12-27 16:41:16');
INSERT INTO `sys_role_menu` VALUES (201, 6, 47, '', 'admin', '2021-12-27 16:41:16', NULL, '2021-12-27 16:41:16');
INSERT INTO `sys_role_menu` VALUES (202, 6, 48, '', 'admin', '2021-12-27 16:41:16', NULL, '2021-12-27 16:41:16');
INSERT INTO `sys_role_menu` VALUES (203, 6, 49, '', 'admin', '2021-12-27 16:41:16', NULL, '2021-12-27 16:41:16');
INSERT INTO `sys_role_menu` VALUES (204, 6, 50, '', 'admin', '2021-12-27 16:41:16', NULL, '2021-12-27 16:41:16');
INSERT INTO `sys_role_menu` VALUES (205, 6, 51, '', 'admin', '2021-12-27 16:41:16', NULL, '2021-12-27 16:41:16');
INSERT INTO `sys_role_menu` VALUES (206, 6, 52, '', 'admin', '2021-12-27 16:41:16', NULL, '2021-12-27 16:41:16');
INSERT INTO `sys_role_menu` VALUES (207, 6, 53, '', 'admin', '2021-12-27 16:41:16', NULL, '2021-12-27 16:41:16');
INSERT INTO `sys_role_menu` VALUES (208, 7, 33, '', 'admin', '2021-12-27 16:41:47', NULL, '2021-12-27 16:41:47');
INSERT INTO `sys_role_menu` VALUES (209, 7, 34, '', 'admin', '2021-12-27 16:41:47', NULL, '2021-12-27 16:41:47');
INSERT INTO `sys_role_menu` VALUES (210, 7, 35, '', 'admin', '2021-12-27 16:41:47', NULL, '2021-12-27 16:41:47');
INSERT INTO `sys_role_menu` VALUES (211, 7, 36, '', 'admin', '2021-12-27 16:41:47', NULL, '2021-12-27 16:41:47');
INSERT INTO `sys_role_menu` VALUES (212, 7, 37, '', 'admin', '2021-12-27 16:41:47', NULL, '2021-12-27 16:41:47');
INSERT INTO `sys_role_menu` VALUES (213, 7, 38, '', 'admin', '2021-12-27 16:41:47', NULL, '2021-12-27 16:41:47');
INSERT INTO `sys_role_menu` VALUES (214, 7, 39, '', 'admin', '2021-12-27 16:41:47', NULL, '2021-12-27 16:41:47');
INSERT INTO `sys_role_menu` VALUES (215, 7, 40, '', 'admin', '2021-12-27 16:41:47', NULL, '2021-12-27 16:41:47');
INSERT INTO `sys_role_menu` VALUES (216, 7, 41, '', 'admin', '2021-12-27 16:41:48', NULL, '2021-12-27 16:41:48');
INSERT INTO `sys_role_menu` VALUES (217, 7, 42, '', 'admin', '2021-12-27 16:41:48', NULL, '2021-12-27 16:41:48');
INSERT INTO `sys_role_menu` VALUES (218, 7, 43, '', 'admin', '2021-12-27 16:41:48', NULL, '2021-12-27 16:41:48');
INSERT INTO `sys_role_menu` VALUES (219, 7, 44, '', 'admin', '2021-12-27 16:41:48', NULL, '2021-12-27 16:41:48');
INSERT INTO `sys_role_menu` VALUES (220, 7, 45, '', 'admin', '2021-12-27 16:41:48', NULL, '2021-12-27 16:41:48');
INSERT INTO `sys_role_menu` VALUES (221, 7, 46, '', 'admin', '2021-12-27 16:41:48', NULL, '2021-12-27 16:41:48');
INSERT INTO `sys_role_menu` VALUES (222, 7, 47, '', 'admin', '2021-12-27 16:41:48', NULL, '2021-12-27 16:41:48');
INSERT INTO `sys_role_menu` VALUES (223, 7, 48, '', 'admin', '2021-12-27 16:41:48', NULL, '2021-12-27 16:41:48');
INSERT INTO `sys_role_menu` VALUES (224, 7, 49, '', 'admin', '2021-12-27 16:41:48', NULL, '2021-12-27 16:41:48');
INSERT INTO `sys_role_menu` VALUES (225, 7, 50, '', 'admin', '2021-12-27 16:41:48', NULL, '2021-12-27 16:41:48');
INSERT INTO `sys_role_menu` VALUES (226, 7, 51, '', 'admin', '2021-12-27 16:41:48', NULL, '2021-12-27 16:41:48');
INSERT INTO `sys_role_menu` VALUES (227, 7, 52, '', 'admin', '2021-12-27 16:41:48', NULL, '2021-12-27 16:41:48');
INSERT INTO `sys_role_menu` VALUES (228, 7, 53, '', 'admin', '2021-12-27 16:41:48', NULL, '2021-12-27 16:41:48');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `dept_id` bigint(0) NOT NULL COMMENT '部门id',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '姓名',
  `nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '昵称',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '密码',
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '邮箱',
  `mobile` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '手机号',
  `avatar` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '',
  `status` tinyint(0) NOT NULL DEFAULT 1 COMMENT '状态（1表正常,2表示锁定，3表示禁用）',
  `memo` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '',
  `createBy` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建人',
  `createTime` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateBy` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新人',
  `lastUpdateTime` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `deleted` tinyint(0) NOT NULL DEFAULT 0 COMMENT '是否删除（逻辑删除，保留历史记录，但是为逻辑删除标志，1删除，0未删除）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username_UNIQUE`(`username`, `deleted`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '系统用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (6, 154, 'user2', '第二个用户', '12', 'wtiger@163.net', '13909090990', NULL, 1, '普通用户', 'admin', '2021-09-22 20:39:12', 'admin', '2021-12-27 16:42:36', 0);
INSERT INTO `sys_user` VALUES (8, 154, 'user1', '第一个用户', '12', '', '', NULL, 1, NULL, 'admin', '2021-11-08 22:07:37', 'admin', '2021-12-27 15:53:38', 0);
INSERT INTO `sys_user` VALUES (9, 154, 'user3', '第三个用户', '11', '', '', NULL, 1, NULL, 'admin', '2021-12-27 16:44:01', '', NULL, 0);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(0) NOT NULL COMMENT '用户id',
  `role_id` bigint(0) NOT NULL COMMENT '角色id',
  `memo` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  `createBy` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '创建人',
  `createTime` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `lastUpdateBy` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '更新人',
  `lastUpdateTime` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id_role_idUnique`(`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (7, 3, 5, '', 'admin', '2021-09-22 15:20:03', NULL, NULL);
INSERT INTO `sys_user_role` VALUES (8, 2, 5, '', 'admin', '2021-09-22 15:35:52', NULL, NULL);
INSERT INTO `sys_user_role` VALUES (9, 2, 6, '', 'admin', '2021-09-22 15:35:52', NULL, NULL);
INSERT INTO `sys_user_role` VALUES (15, 6, 6, '', 'admin', '2021-12-27 16:43:21', NULL, NULL);
INSERT INTO `sys_user_role` VALUES (17, 8, 5, '', 'admin', '2021-12-27 16:43:34', NULL, NULL);
INSERT INTO `sys_user_role` VALUES (18, 9, 7, '', 'admin', '2021-12-27 16:44:38', NULL, NULL);

-- ----------------------------
-- Table structure for tst_allshow
-- ----------------------------
DROP TABLE IF EXISTS `tst_allshow`;
CREATE TABLE `tst_allshow`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `fcode` char(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `fname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `fsort` bigint(0) NOT NULL,
  `memo` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_fcode`(`fcode`) USING BTREE,
  UNIQUE INDEX `idx_fname`(`fname`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tst_allshow
-- ----------------------------
INSERT INTO `tst_allshow` VALUES (1, '01', '汉族', 1, '');
INSERT INTO `tst_allshow` VALUES (2, '02', '藏族', 2, '');
INSERT INTO `tst_allshow` VALUES (3, '03', '维吾尔族', 3, '');
INSERT INTO `tst_allshow` VALUES (4, '04', '32424', 234, '');

-- ----------------------------
-- Table structure for tst_pageshow
-- ----------------------------
DROP TABLE IF EXISTS `tst_pageshow`;
CREATE TABLE `tst_pageshow`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `deptId` bigint(0) NOT NULL,
  `fname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `fgender` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `fage` bigint(0) NULL DEFAULT NULL,
  `fnation` bigint(0) NOT NULL,
  `memo` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_fname`(`fname`) USING BTREE,
  INDEX `fk_tst_pageshow_deptId`(`deptId`) USING BTREE,
  INDEX `fk_tst_pageshow_fnation`(`fnation`) USING BTREE,
  CONSTRAINT `tst_pageshow_ibfk_1` FOREIGN KEY (`deptId`) REFERENCES `tst_treeshow` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `tst_pageshow_ibfk_2` FOREIGN KEY (`fnation`) REFERENCES `tst_allshow` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tst_pageshow
-- ----------------------------
INSERT INTO `tst_pageshow` VALUES (1, 3, '软件工程', '女', 12, 1, '');
INSERT INTO `tst_pageshow` VALUES (2, 1, '软件工程概论', '男', 45, 1, '');
INSERT INTO `tst_pageshow` VALUES (3, 1, '机器学习', '男', 67, 1, '');
INSERT INTO `tst_pageshow` VALUES (4, 3, '223', '男', 23424, 4, '');

-- ----------------------------
-- Table structure for tst_treeshow
-- ----------------------------
DROP TABLE IF EXISTS `tst_treeshow`;
CREATE TABLE `tst_treeshow`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `fcode` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `fname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `fsort` bigint(0) NOT NULL,
  `memo` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_fcode`(`fcode`) USING BTREE,
  UNIQUE INDEX `idx_fname`(`fname`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tst_treeshow
-- ----------------------------
INSERT INTO `tst_treeshow` VALUES (1, '01', '北京科技大学', 1, '');
INSERT INTO `tst_treeshow` VALUES (2, '0101', '技术部', 2, '');
INSERT INTO `tst_treeshow` VALUES (3, '0102', '客户维护部', 3, '');
INSERT INTO `tst_treeshow` VALUES (4, '010101', '前端开发', 0, '');
INSERT INTO `tst_treeshow` VALUES (5, '010102', '后端开发', 0, '');
INSERT INTO `tst_treeshow` VALUES (6, '010103', 'C++服务器开发', 0, '');

SET FOREIGN_KEY_CHECKS = 1;
