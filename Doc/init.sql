-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- Server version:               10.3.10-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL 版本:                  9.5.0.5338
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for young-board
DROP DATABASE IF EXISTS `young-board`;
CREATE DATABASE IF NOT EXISTS `young-board` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `young-board`;

-- Dumping structure for table young-board.sys_app
DROP TABLE IF EXISTS `sys_app`;
CREATE TABLE IF NOT EXISTS `sys_app` (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `name` varchar(100) NOT NULL COMMENT '应用名称',
  `code` varchar(50) NOT NULL COMMENT '编码',
  `active` varchar(1) DEFAULT NULL COMMENT '是否默认激活（Y-是，N-否）',
  `status` tinyint(4) NOT NULL COMMENT '状态（字典 0正常 1停用 2删除）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统应用表';

-- Dumping data for table young-board.sys_app: ~0 rows (approximately)
DELETE FROM `sys_app`;
/*!40000 ALTER TABLE `sys_app` DISABLE KEYS */;
INSERT INTO `sys_app` (`id`, `name`, `code`, `active`, `status`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES
	(1265476890672672821, '系统应用', 'system', 'Y', 0, '2020-03-25 19:07:00', 1265476890672672808, '2020-08-15 15:23:05', 1280709549107552257);
/*!40000 ALTER TABLE `sys_app` ENABLE KEYS */;

-- Dumping structure for table young-board.sys_area
DROP TABLE IF EXISTS `sys_area`;
CREATE TABLE IF NOT EXISTS `sys_area` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `level_code` tinyint(3) unsigned DEFAULT NULL COMMENT '层级',
  `parent_code` varchar(20) DEFAULT '0' COMMENT '父级行政代码',
  `area_code` varchar(20) DEFAULT '0' COMMENT '行政代码',
  `zip_code` varchar(6) DEFAULT '0' COMMENT '邮政编码',
  `city_code` varchar(6) DEFAULT '' COMMENT '区号',
  `name` varchar(50) DEFAULT '' COMMENT '名称',
  `short_name` varchar(50) DEFAULT '' COMMENT '简称',
  `merger_name` varchar(50) DEFAULT '' COMMENT '组合名',
  `pinyin` varchar(30) DEFAULT '' COMMENT '拼音',
  `lng` decimal(10,6) DEFAULT NULL COMMENT '经度',
  `lat` decimal(10,6) DEFAULT NULL COMMENT '纬度',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_code` (`area_code`) USING BTREE,
  KEY `idx_parent_code` (`parent_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='中国行政地区表';

-- Dumping data for table young-board.sys_area: ~0 rows (approximately)
DELETE FROM `sys_area`;
/*!40000 ALTER TABLE `sys_area` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_area` ENABLE KEYS */;

-- Dumping structure for table young-board.sys_code_generate
DROP TABLE IF EXISTS `sys_code_generate`;
CREATE TABLE IF NOT EXISTS `sys_code_generate` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `author_name` varchar(255) NOT NULL COMMENT '作者姓名',
  `class_name` varchar(255) NOT NULL COMMENT '类名',
  `table_prefix` varchar(255) NOT NULL COMMENT '是否移除表前缀',
  `generate_type` varchar(255) NOT NULL COMMENT '生成位置类型',
  `table_name` varchar(255) NOT NULL COMMENT '数据库表名',
  `package_name` varchar(255) DEFAULT NULL COMMENT '包名称',
  `bus_name` varchar(255) DEFAULT NULL COMMENT '业务名',
  `table_comment` varchar(255) DEFAULT NULL COMMENT '功能名',
  `app_code` varchar(255) DEFAULT NULL COMMENT '所属应用',
  `menu_pid` varchar(255) DEFAULT NULL COMMENT '菜单上级',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='代码生成基础配置';

-- Dumping data for table young-board.sys_code_generate: ~8 rows (approximately)
DELETE FROM `sys_code_generate`;
/*!40000 ALTER TABLE `sys_code_generate` DISABLE KEYS */;
INSERT INTO `sys_code_generate` (`id`, `author_name`, `class_name`, `table_prefix`, `generate_type`, `table_name`, `package_name`, `bus_name`, `table_comment`, `app_code`, `menu_pid`, `create_user`, `create_time`, `update_user`, `update_time`) VALUES
	(1537693479067422721, 'young-pastor', 'BoardDataSource', 'Y', '2', 'tbl_board_data_source', 'com.zhisida.board', 'boardDatasource', '数据源配置表', 'system', '1537693069254561793', 1265476890672672808, '2022-06-17 15:07:49', 1265476890672672808, '2022-06-20 11:34:15'),
	(1537701005573967874, 'young-pastor', 'BoardTable', 'Y', '2', 'tbl_board_table', 'com.zhisida.board', 'boardTable', '数据表配置', 'system', '1537693069254561793', 1265476890672672808, '2022-06-17 15:37:43', 1265476890672672808, '2022-06-20 11:34:12'),
	(1537701234742349825, 'young-pastor', 'BoardTableColumn', 'Y', '2', 'tbl_board_table_column', 'com.zhisida.board', 'boardTableColumn', '数据字段配置', 'system', '1537693069254561793', 1265476890672672808, '2022-06-17 15:38:38', 1265476890672672808, '2022-06-20 11:51:59'),
	(1537701429043482626, 'young-pastor', 'BoardTableConnect', 'Y', '2', 'tbl_board_table_connect', 'com.zhisida.board', 'boardTableConnect', '字段关联配置', 'system', '1537693069254561793', 1265476890672672808, '2022-06-17 15:39:24', 1265476890672672808, '2022-06-20 11:34:02'),
	(1537701841163210754, 'young-pastor', 'BoardEvent', 'Y', '2', 'tbl_board_event', 'com.zhisida.board', 'boardEvent', '元事件配置', 'system', '1537693069254561793', 1265476890672672808, '2022-06-17 15:41:02', 1265476890672672808, '2022-06-20 11:34:08'),
	(1537702183149981697, 'young-pastor', 'BoardEventGroup', 'Y', '2', 'tbl_board_event_group', 'com.zhisida.board', 'boardEventGroup', '元事件分组', 'system', '1537693069254561793', 1265476890672672808, '2022-06-17 15:42:24', 1265476890672672808, '2022-06-20 11:34:27'),
	(1537702828053581826, 'young-pastor', 'BoardProperty', 'Y', '2', 'tbl_board_property', 'com.zhisida.board', 'boardProperty', '属性配置', 'system', '1537693069254561793', 1265476890672672808, '2022-06-17 15:44:58', 1265476890672672808, '2022-06-20 11:58:25'),
	(1537703542846869506, 'young-pastor', 'BoardPropertyGroup', 'Y', '2', 'tbl_board_property_group', 'com.zhisida.board', 'boardPropertyGroup', '属性分组', 'system', '1537693069254561793', 1265476890672672808, '2022-06-17 15:47:48', 1265476890672672808, '2022-06-20 11:58:19'),
	(1537703612946272257, 'young-pastor', 'BoardPropertyValue', 'Y', '2', 'tbl_board_property_value', 'com.zhisida.board', 'boardPropertyValue', '属性值', 'system', '1537693069254561793', 1265476890672672808, '2022-06-17 15:48:05', 1265476890672672808, '2022-06-20 11:58:09'),
	(1540259046307590146, 'young-pastor', 'SysTimersLog', 'N', '2', 'sys_timers_log', 'com.zhisida.board', 'sysTimersLog', '任务日志', 'system', '1264622039642256611', 1265476890672672808, '2022-06-24 17:02:27', NULL, NULL);
/*!40000 ALTER TABLE `sys_code_generate` ENABLE KEYS */;

-- Dumping structure for table young-board.sys_code_generate_config
DROP TABLE IF EXISTS `sys_code_generate_config`;
CREATE TABLE IF NOT EXISTS `sys_code_generate_config` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `code_gen_id` bigint(20) DEFAULT NULL COMMENT '代码生成主表ID',
  `column_name` varchar(255) DEFAULT NULL COMMENT '数据库字段名',
  `java_name` varchar(255) DEFAULT NULL COMMENT 'java类字段名',
  `data_type` varchar(255) DEFAULT NULL COMMENT '物理类型',
  `column_comment` varchar(255) DEFAULT NULL COMMENT '字段描述',
  `java_type` varchar(255) DEFAULT NULL COMMENT 'java类型',
  `effect_type` varchar(255) DEFAULT NULL COMMENT '作用类型（字典）',
  `dict_type_code` varchar(255) DEFAULT NULL COMMENT '字典code',
  `whether_table` varchar(255) DEFAULT NULL COMMENT '列表展示',
  `whether_add_update` varchar(255) DEFAULT NULL COMMENT '增改',
  `whether_retract` varchar(255) DEFAULT NULL COMMENT '列表是否缩进（字典）',
  `whether_required` varchar(255) DEFAULT NULL COMMENT '是否必填（字典）',
  `query_whether` varchar(255) DEFAULT NULL COMMENT '是否是查询条件',
  `query_type` varchar(255) DEFAULT NULL COMMENT '查询方式',
  `column_key` varchar(255) DEFAULT NULL COMMENT '主键',
  `column_key_name` varchar(255) DEFAULT NULL COMMENT '主外键名称',
  `whether_common` varchar(255) DEFAULT NULL COMMENT '是否是通用字段',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='代码生成详细配置';

-- Dumping data for table young-board.sys_code_generate_config: ~54 rows (approximately)
DELETE FROM `sys_code_generate_config`;
/*!40000 ALTER TABLE `sys_code_generate_config` DISABLE KEYS */;
INSERT INTO `sys_code_generate_config` (`id`, `code_gen_id`, `column_name`, `java_name`, `data_type`, `column_comment`, `java_type`, `effect_type`, `dict_type_code`, `whether_table`, `whether_add_update`, `whether_retract`, `whether_required`, `query_whether`, `query_type`, `column_key`, `column_key_name`, `whether_common`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES
	(1537693479289720833, 1537693479067422721, 'ID', 'id', 'bigint', '主键ID', 'Long', 'input', NULL, 'N', 'N', 'N', 'N', 'N', 'eq', 'PRI', 'Id', 'N', '2022-06-17 15:07:49', 1265476890672672808, NULL, NULL),
	(1537693479314886658, 1537693479067422721, 'DISPLAY_NAME', 'displayName', 'varchar', '数据源名称', 'String', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'DisplayName', 'N', '2022-06-17 15:07:49', 1265476890672672808, NULL, NULL),
	(1537693479344246786, 1537693479067422721, 'GROUP', 'group', 'varchar', '分组', 'String', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'Group', 'N', '2022-06-17 15:07:49', 1265476890672672808, NULL, NULL),
	(1537693479365218306, 1537693479067422721, 'CONFIG', 'config', 'text', '数据库配置', 'String', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'Config', 'N', '2022-06-17 15:07:49', 1265476890672672808, NULL, NULL),
	(1537693479386189825, 1537693479067422721, 'TYPE', 'type', 'varchar', '数据库类型', 'String', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'Type', 'N', '2022-06-17 15:07:49', 1265476890672672808, NULL, NULL),
	(1537693479423938562, 1537693479067422721, 'REMARK', 'remark', 'varchar', '备注', 'String', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'Remark', 'N', '2022-06-17 15:07:49', 1265476890672672808, NULL, NULL),
	(1537701005741740034, 1537701005573967874, 'ID', 'id', 'bigint', '主键ID', 'Long', 'input', NULL, 'N', 'N', 'N', 'N', 'N', 'eq', 'PRI', 'Id', 'N', '2022-06-17 15:37:43', 1265476890672672808, NULL, NULL),
	(1537701005766905857, 1537701005573967874, 'DATA_SOURCE_ID', 'dataSourceId', 'bigint', '数据源ID', 'Long', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'DataSourceId', 'N', '2022-06-17 15:37:43', 1265476890672672808, NULL, NULL),
	(1537701005783683073, 1537701005573967874, 'TABLE_NAME', 'tableName', 'varchar', '表名称', 'String', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'TableName', 'N', '2022-06-17 15:37:43', 1265476890672672808, NULL, NULL),
	(1537701005808848898, 1537701005573967874, 'DISPLAY_NAME', 'displayName', 'varchar', '展示名称', 'String', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'DisplayName', 'N', '2022-06-17 15:37:43', 1265476890672672808, NULL, NULL),
	(1537701005834014721, 1537701005573967874, 'REFRESH_TYPE', 'refreshType', 'varchar', '刷新方式', 'String', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'RefreshType', 'N', '2022-06-17 15:37:43', 1265476890672672808, NULL, NULL),
	(1537701005871763457, 1537701005573967874, 'REMARK', 'remark', 'varchar', '备注', 'String', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'Remark', 'N', '2022-06-17 15:37:43', 1265476890672672808, NULL, NULL),
	(1537701234805264385, 1537701234742349825, 'ID', 'id', 'bigint', '主键ID', 'Long', 'input', NULL, 'N', 'N', 'N', 'N', 'N', 'eq', 'PRI', 'Id', 'N', '2022-06-17 15:38:38', 1265476890672672808, NULL, NULL),
	(1537701234872373249, 1537701234742349825, 'TABLE_ID', 'tableId', 'bigint', '数据表ID', 'Long', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'TableId', 'N', '2022-06-17 15:38:38', 1265476890672672808, NULL, NULL),
	(1537701234872373250, 1537701234742349825, 'COLUMN_NAME', 'columnName', 'varchar', '字段名称', 'String', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'ColumnName', 'N', '2022-06-17 15:38:38', 1265476890672672808, NULL, NULL),
	(1537701234939482114, 1537701234742349825, 'DISPLAY_NAME', 'displayName', 'varchar', '展示名称', 'String', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'DisplayName', 'N', '2022-06-17 15:38:38', 1265476890672672808, NULL, NULL),
	(1537701234939482115, 1537701234742349825, 'REFRESH_TYPE', 'refreshType', 'varchar', '刷新方式', 'String', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'RefreshType', 'N', '2022-06-17 15:38:38', 1265476890672672808, NULL, NULL),
	(1537701234939482116, 1537701234742349825, 'COLUMN_TYPE', 'columnType', 'varchar', '字段类型', 'String', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'ColumnType', 'N', '2022-06-17 15:38:38', 1265476890672672808, NULL, NULL),
	(1537701235002396673, 1537701234742349825, 'DATA_TYPE', 'dataType', 'varchar', '数据类型', 'String', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'DataType', 'N', '2022-06-17 15:38:38', 1265476890672672808, NULL, NULL),
	(1537701235002396674, 1537701234742349825, 'REMARK', 'remark', 'varchar', '备注', 'String', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'Remark', 'N', '2022-06-17 15:38:38', 1265476890672672808, NULL, NULL),
	(1537701429043482627, 1537701429043482626, 'ID', 'id', 'bigint', '主键ID', 'Long', 'input', NULL, 'N', 'N', 'N', 'N', 'N', 'eq', 'PRI', 'Id', 'N', '2022-06-17 15:39:24', 1265476890672672808, NULL, NULL),
	(1537701429110591490, 1537701429043482626, 'column_Id', 'columnId', 'bigint', '字段ID', 'Long', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'ColumnId', 'N', '2022-06-17 15:39:24', 1265476890672672808, NULL, NULL),
	(1537701429110591491, 1537701429043482626, 'connect_Column_Id', 'connectColumnId', 'bigint', '关联字段ID', 'Long', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'ConnectColumnId', 'N', '2022-06-17 15:39:24', 1265476890672672808, NULL, NULL),
	(1537701429110591492, 1537701429043482626, 'connect_Type', 'connectType', 'varchar', '关联类型', 'String', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'ConnectType', 'N', '2022-06-17 15:39:24', 1265476890672672808, NULL, NULL),
	(1537701841213542401, 1537701841163210754, 'ID', 'id', 'bigint', '主键ID', 'Long', 'input', NULL, 'N', 'N', 'N', 'N', 'N', 'eq', 'PRI', 'Id', 'N', '2022-06-17 15:41:02', 1265476890672672808, NULL, NULL),
	(1537701841234513921, 1537701841163210754, 'EVENT_GORUP_ID', 'eventGorupId', 'varchar', '事件分组', 'String', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'EventGorupId', 'N', '2022-06-17 15:41:02', 1265476890672672808, NULL, NULL),
	(1537701841255485441, 1537701841163210754, 'DISPLAY_NAME', 'displayName', 'varchar', '事件名称', 'String', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'DisplayName', 'N', '2022-06-17 15:41:02', 1265476890672672808, NULL, NULL),
	(1537701841280651266, 1537701841163210754, 'TABLE_COLUMN_ID', 'tableColumnId', 'bigint', '表字段ID', 'Long', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'TableColumnId', 'N', '2022-06-17 15:41:02', 1265476890672672808, NULL, NULL),
	(1537701841297428481, 1537701841163210754, 'MEASURE', 'measure', 'varchar', '计算方式', 'String', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'Measure', 'N', '2022-06-17 15:41:02', 1265476890672672808, NULL, NULL),
	(1537701841322594305, 1537701841163210754, 'VALUE', 'value', 'varchar', '事件值', 'String', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'Value', 'N', '2022-06-17 15:41:02', 1265476890672672808, NULL, NULL),
	(1537701841343565825, 1537701841163210754, 'VALUE_TYPE', 'valueType', 'varchar', '事件值类型', 'String', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'ValueType', 'N', '2022-06-17 15:41:02', 1265476890672672808, NULL, NULL),
	(1537702183212896257, 1537702183149981697, 'ID', 'id', 'bigint', '主键ID', 'Long', 'input', NULL, 'N', 'N', 'N', 'N', 'N', 'eq', 'PRI', 'Id', 'N', '2022-06-17 15:42:24', 1265476890672672808, NULL, NULL),
	(1537702183212896258, 1537702183149981697, 'DISPLAY_NAME', 'displayName', 'varchar', '事件名称', 'String', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'DisplayName', 'N', '2022-06-17 15:42:24', 1265476890672672808, NULL, NULL),
	(1537702828053581827, 1537702828053581826, 'ID', 'id', 'bigint', '主键ID', 'Long', 'input', NULL, 'N', 'N', 'N', 'N', 'N', 'eq', 'PRI', 'Id', 'N', '2022-06-17 15:44:58', 1265476890672672808, NULL, NULL),
	(1537702828133273602, 1537702828053581826, 'DISPLAY_NAME', 'displayName', 'varchar', '属性名称', 'String', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'DisplayName', 'N', '2022-06-17 15:44:58', 1265476890672672808, NULL, NULL),
	(1537702828158439426, 1537702828053581826, 'PROPERTY_GORUP_ID', 'propertyGorupId', 'bigint', '属性分组', 'Long', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'PropertyGorupId', 'N', '2022-06-17 15:44:58', 1265476890672672808, NULL, NULL),
	(1537702828179410945, 1537702828053581826, 'TABLE_COLUMN_ID', 'tableColumnId', 'bigint', '表字段ID', 'Long', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'TableColumnId', 'N', '2022-06-17 15:44:58', 1265476890672672808, NULL, NULL),
	(1537702828204576770, 1537702828053581826, 'MEASURE', 'measure', 'varchar', '计算方式', 'String', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'Measure', 'N', '2022-06-17 15:44:58', 1265476890672672808, NULL, NULL),
	(1537702828221353986, 1537702828053581826, 'VALUE', 'value', 'text', '属性值', 'String', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'Value', 'N', '2022-06-17 15:44:58', 1265476890672672808, NULL, NULL),
	(1537702828242325506, 1537702828053581826, 'VALUE_TYPE', 'valueType', 'varchar', '属性值类型', 'String', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'ValueType', 'N', '2022-06-17 15:44:58', 1265476890672672808, NULL, NULL),
	(1537702828271685634, 1537702828053581826, 'UNIT', 'unit', 'text', '属性值', 'String', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'Unit', 'N', '2022-06-17 15:44:58', 1265476890672672808, NULL, NULL),
	(1537702828288462850, 1537702828053581826, 'UNIT_TYPE', 'unitType', 'varchar', '属性值类型', 'String', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'UnitType', 'N', '2022-06-17 15:44:58', 1265476890672672808, NULL, NULL),
	(1537702828309434370, 1537702828053581826, 'IS_DEFAULT', 'isDefault', 'varchar', '属性值类型', 'String', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'IsDefault', 'N', '2022-06-17 15:44:58', 1265476890672672808, NULL, NULL),
	(1537702828334600193, 1537702828053581826, 'REMARK', 'remark', 'varchar', '属性值类型', 'String', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'Remark', 'N', '2022-06-17 15:44:58', 1265476890672672808, NULL, NULL),
	(1537703542981087233, 1537703542846869506, 'ID', 'id', 'bigint', '主键ID', 'Long', 'input', NULL, 'N', 'N', 'N', 'N', 'N', 'eq', 'PRI', 'Id', 'N', '2022-06-17 15:47:48', 1265476890672672808, NULL, NULL),
	(1537703542981087234, 1537703542846869506, 'DISPLAY_NAME', 'displayName', 'varchar', '分组名称', 'String', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'DisplayName', 'N', '2022-06-17 15:47:48', 1265476890672672808, NULL, NULL),
	(1537703613009186818, 1537703612946272257, 'ID', 'id', 'bigint', '主键ID', 'Long', 'input', NULL, 'N', 'N', 'N', 'N', 'N', 'eq', 'PRI', 'Id', 'N', '2022-06-17 15:48:05', 1265476890672672808, NULL, NULL),
	(1540259046466973697, 1540259046307590146, 'ID', 'id', 'bigint', '主键ID', 'Long', 'input', NULL, 'N', 'N', 'N', 'N', 'N', 'eq', 'PRI', 'Id', 'N', '2022-06-24 17:02:27', 1265476890672672808, NULL, NULL),
	(1540259046475362306, 1540259046307590146, 'TIMER_ID', 'timerId', 'bigint', '任务编号', 'Long', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'TimerId', 'N', '2022-06-24 17:02:27', 1265476890672672808, NULL, NULL),
	(1540259046492139522, 1540259046307590146, 'EXECUTE_START_TIME', 'executeStartTime', 'datetime', '执行开始时间', 'Date', 'datepicker', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'ExecuteStartTime', 'N', '2022-06-24 17:02:27', 1265476890672672808, NULL, NULL),
	(1540259046500528130, 1540259046307590146, 'EXECUTE_END_TIME', 'executeEndTime', 'datetime', '执行结束时间', 'Date', 'datepicker', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'ExecuteEndTime', 'N', '2022-06-24 17:02:27', 1265476890672672808, NULL, NULL),
	(1540259046508916737, 1540259046307590146, 'EXECUTE_CODE', 'executeCode', 'varchar', '执行结果', 'String', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'ExecuteCode', 'N', '2022-06-24 17:02:27', 1265476890672672808, NULL, NULL),
	(1540259046525693954, 1540259046307590146, 'EXECUTE_MSG', 'executeMsg', 'text', '执行信息', 'String', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'ExecuteMsg', 'N', '2022-06-24 17:02:27', 1265476890672672808, NULL, NULL),
	(1540259046534082562, 1540259046307590146, 'EXECUTE_PARAM', 'executeParam', 'text', '执行参数', 'String', 'input', NULL, 'Y', 'Y', 'N', 'Y', 'Y', 'eq', '', 'ExecuteParam', 'N', '2022-06-24 17:02:27', 1265476890672672808, NULL, NULL);
/*!40000 ALTER TABLE `sys_code_generate_config` ENABLE KEYS */;

-- Dumping structure for table young-board.sys_config
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE IF NOT EXISTS `sys_config` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `code` varchar(50) NOT NULL COMMENT '编码',
  `value` varchar(255) NOT NULL COMMENT '值',
  `sys_flag` char(1) NOT NULL COMMENT '是否是系统参数（Y-是，N-否）',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` tinyint(4) NOT NULL COMMENT '状态（字典 0正常 1停用 2删除）',
  `group_code` varchar(255) NOT NULL DEFAULT 'DEFAULT' COMMENT '常量所属分类的编码，来自于“常量的分类”字典',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统参数配置表';

-- Dumping data for table young-board.sys_config: ~37 rows (approximately)
DELETE FROM `sys_config`;
/*!40000 ALTER TABLE `sys_config` DISABLE KEYS */;
INSERT INTO `sys_config` (`id`, `name`, `code`, `value`, `sys_flag`, `remark`, `status`, `group_code`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES
	(1265117443880853506, 'jwt密钥', 'JWT_SECRET', 'snowy', 'Y', '（重要）jwt密钥，默认为空，自行设置', 0, 'DEFAULT', '2022-05-26 06:35:19', 1265476890672672808, NULL, NULL),
	(1265117443880853507, '默认密码', 'DEFAULT_PASSWORD', '123456', 'Y', '默认密码', 0, 'DEFAULT', '2022-05-26 06:37:56', 1265476890672672808, NULL, NULL),
	(1265117443880853508, 'token过期时间', 'TOKEN_EXPIRE', '86400', 'Y', 'token过期时间（单位：秒）', 0, 'DEFAULT', '2022-05-27 11:54:49', 1265476890672672808, NULL, NULL),
	(1265117443880853509, 'session会话过期时间', 'SESSION_EXPIRE', '7200', 'Y', 'session会话过期时间（单位：秒）', 0, 'DEFAULT', '2022-05-27 11:54:49', 1265476890672672808, NULL, NULL),
	(1265117443880853519, '阿里云短信keyId', 'ALIYUN_SMS_ACCESSKEY_ID', '你的keyId', 'Y', '阿里云短信keyId', 0, 'ALIYUN_SMS', '2022-06-07 16:27:11', 1265476890672672808, NULL, NULL),
	(1269547042242371585, '阿里云短信secret', 'ALIYUN_SMS_ACCESSKEY_SECRET', '你的secret', 'Y', '阿里云短信secret', 0, 'ALIYUN_SMS', '2022-06-07 16:29:37', 1265476890672672808, NULL, NULL),
	(1269547130041737217, '阿里云短信签名', 'ALIYUN_SMS_SIGN_NAME', 'Snowy快速开发平台', 'Y', '阿里云短信签名', 0, 'ALIYUN_SMS', '2022-06-07 16:29:58', 1265476890672672808, NULL, NULL),
	(1269547279530926081, '阿里云短信-登录模板号', 'ALIYUN_SMS_LOGIN_TEMPLATE_CODE', 'SMS_1877123456', 'Y', '阿里云短信-登录模板号', 0, 'ALIYUN_SMS', '2022-06-07 16:30:33', 1265476890672672808, NULL, NULL),
	(1269547410879750145, '阿里云短信默认失效时间', 'ALIYUN_SMS_INVALIDATE_MINUTES', '5', 'Y', '阿里云短信默认失效时间（单位：分钟）', 0, 'ALIYUN_SMS', '2022-06-07 16:31:04', 1265476890672672808, NULL, NULL),
	(1269575927357071361, '腾讯云短信secretId', 'TENCENT_SMS_SECRET_ID', '你的secretId', 'Y', '腾讯云短信secretId', 0, 'TENCENT_SMS', '2022-06-07 18:24:23', 1265476890672672808, NULL, NULL),
	(1269575991693500418, '腾讯云短信secretKey', 'TENCENT_SMS_SECRET_KEY', '你的secretkey', 'Y', '腾讯云短信secretKey', 0, 'TENCENT_SMS', '2022-06-07 18:24:39', 1265476890672672808, NULL, NULL),
	(1269576044084551682, '腾讯云短信sdkAppId', 'TENCENT_SMS_SDK_APP_ID', '1400375123', 'Y', '腾讯云短信sdkAppId', 0, 'TENCENT_SMS', '2022-06-07 18:24:51', 1265476890672672808, NULL, NULL),
	(1269576089294954497, '腾讯云短信签名', 'TENCENT_SMS_SIGN', 'Snowy快速开发平台', 'Y', '腾讯云短信签名', 0, 'TENCENT_SMS', '2022-06-07 18:25:02', 1265476890672672808, NULL, NULL),
	(1270378172860403713, '邮箱host', 'EMAIL_HOST', 'smtp.126.com', 'Y', '邮箱host', 0, 'EMAIL', '2022-06-09 23:32:14', 1265476890672672808, NULL, NULL),
	(1270378295543795714, '邮箱用户名', 'EMAIL_USERNAME', 'test@126.com', 'Y', '邮箱用户名', 0, 'EMAIL', '2022-06-09 23:32:43', 1265476890672672808, NULL, NULL),
	(1270378340510928897, '邮箱密码', 'EMAIL_PASSWORD', '你的邮箱密码', 'Y', '邮箱密码', 0, 'EMAIL', '2022-06-09 23:32:54', 1265476890672672808, NULL, NULL),
	(1270378527358783489, '邮箱端口', 'EMAIL_PORT', '465', 'Y', '邮箱端口', 0, 'EMAIL', '2022-06-09 23:33:38', 1265476890672672808, NULL, NULL),
	(1270378790035460097, '邮箱是否开启ssl', 'EMAIL_SSL', 'true', 'Y', '邮箱是否开启ssl', 0, 'EMAIL', '2022-06-09 23:34:41', 1265476890672672808, NULL, NULL),
	(1270380786649972737, '邮箱发件人', 'EMAIL_FROM', 'test@126.com', 'Y', '邮箱发件人', 0, 'EMAIL', '2022-06-09 23:42:37', 1265476890672672808, NULL, NULL),
	(1270380786649972738, 'win本地上传文件路径', 'FILE_UPLOAD_PATH_FOR_WINDOWS', 'd:/tmp', 'Y', 'win本地上传文件路径', 0, 'FILE_PATH', '2022-06-09 23:42:37', 1265476890672672808, NULL, NULL),
	(1270380786649972739, 'linux/mac本地上传文件路径', 'FILE_UPLOAD_PATH_FOR_LINUX', '/tmp', 'Y', 'linux/mac本地上传文件路径', 0, 'FILE_PATH', '2022-06-09 23:42:37', 1265476890672672808, NULL, NULL),
	(1270380786649982740, 'Snowy演示环境', 'DEMO_ENV_FLAG', 'false', 'Y', 'Snowy演示环境的开关，true-打开，false-关闭，如果演示环境开启，则只能读数据不能写数据', 0, 'DEFAULT', '2022-06-09 23:42:37', 1265476890672672808, '2022-09-03 14:38:17', 1265476890672672808),
	(1270380786649982741, 'Snowy放开XSS过滤的接口', 'UN_XSS_FILTER_URL', '/demo/xssfilter,/demo/unxss', 'Y', '多个url可以用英文逗号隔开', 0, 'DEFAULT', '2022-06-09 23:42:37', 1265476890672672808, NULL, NULL),
	(1270380786649982742, '单用户登陆的开关', 'ENABLE_SINGLE_LOGIN', 'false', 'Y', '单用户登陆的开关，true-打开，false-关闭，如果一个人登录两次，就会将上一次登陆挤下去', 0, 'DEFAULT', '2022-06-09 23:42:37', 1265476890672672808, NULL, NULL),
	(1270380786649982743, '登录验证码的开关', 'CAPTCHA_OPEN', 'true', 'Y', '登录验证码的开关，true-打开，false-关闭', 0, 'DEFAULT', '2022-06-09 23:42:37', 1265476890672672808, '2021-12-16 19:43:29', 1265476890672672808),
	(1280694281648070659, '阿里云定位api接口地址', 'IP_GEO_API', 'http://api01.aliyun.venuscn.com/ip?ip=%s', 'Y', '阿里云定位api接口地址', 0, 'DEFAULT', '2022-07-20 10:44:46', 1265476890672672808, NULL, NULL),
	(1280694281648070660, '阿里云定位appCode', 'IP_GEO_APP_CODE', '461535aabeae4f34861884d392f5d452', 'Y', '阿里云定位appCode', 0, 'DEFAULT', '2022-07-20 10:44:46', 1265476890672672808, NULL, NULL),
	(1288309751255412737, 'Oauth用户登录的开关', 'ENABLE_OAUTH_LOGIN', 'true', 'Y', 'Oauth用户登录的开关', 0, 'OAUTH', '2022-07-29 11:05:55', 1265476890672672808, NULL, NULL),
	(1288310043346743297, 'Oauth码云登录ClientId', 'OAUTH_GITEE_CLIENT_ID', '你的clientId', 'Y', 'Oauth码云登录ClientId', 0, 'OAUTH', '2022-07-29 11:07:05', 1265476890672672808, NULL, NULL),
	(1288310157876408321, 'Oauth码云登录ClientSecret', 'OAUTH_GITEE_CLIENT_SECRET', '你的clientSecret', 'Y', 'Oauth码云登录ClientSecret', 0, 'OAUTH', '2022-07-29 11:07:32', 1265476890672672808, NULL, NULL),
	(1288310280056483841, 'Oauth码云登录回调地址', 'OAUTH_GITEE_REDIRECT_URI', 'http://localhost:83/oauth/callback/gitee', 'Y', 'Oauth码云登录回调地址', 0, 'OAUTH', '2022-07-29 11:08:01', 1265476890672672808, NULL, NULL),
	(1410861340761313282, '在线文档地址', 'ONLY_OFFICE_SERVICE_URL', 'https://xiaonuo.vip/', 'N', 'beizhu', 0, 'DEFAULT', '2021-07-02 15:22:11', 1265476890672672808, NULL, NULL),
	(1471107941140008961, 'token是否加解密', 'TOKEN_ENCRYPTION_OPEN', 'true', 'Y', 'token是否加解密', 0, 'CRYPTOGRAM', '2021-12-15 21:20:40', 1265476890672672808, NULL, NULL),
	(1471108086246150145, '操作日志是否加密', 'VISLOG_ENCRYPTION_OPEN', 'true', 'Y', '操作日志是否加密，默认开启', 0, 'CRYPTOGRAM', '2021-12-15 21:21:14', 1265476890672672808, NULL, NULL),
	(1471108334985154562, '登录登出日志是否加密', 'OPLOG_ENCRYPTION_OPEN', 'true', 'Y', '登录登出日志是否加密', 0, 'CRYPTOGRAM', '2021-12-15 21:22:14', 1265476890672672808, NULL, NULL),
	(1471108457198784514, '铭感字段值是否加解密', 'FIELD_ENC_DEC_OPEN', 'true', 'Y', '铭感字段值是否加解密，默认开启', 0, 'CRYPTOGRAM', '2021-12-15 21:22:43', 1265476890672672808, NULL, NULL),
	(1471109091222482946, '是否开启租户功能', 'TENANT_OPEN', 'false', 'Y', '是否开启租户功能，默认关闭', 0, 'DEFAULT', '2021-12-15 21:25:14', 1265476890672672808, NULL, NULL);
/*!40000 ALTER TABLE `sys_config` ENABLE KEYS */;

-- Dumping structure for table young-board.sys_dict_data
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE IF NOT EXISTS `sys_dict_data` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `type_id` bigint(20) NOT NULL COMMENT '字典类型id',
  `value` text NOT NULL COMMENT '值',
  `code` varchar(50) NOT NULL COMMENT '编码',
  `sort` int(11) NOT NULL COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` tinyint(4) NOT NULL COMMENT '状态（字典 0正常 1停用 2删除）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统字典值表';

-- Dumping data for table young-board.sys_dict_data: ~103 rows (approximately)
DELETE FROM `sys_dict_data`;
/*!40000 ALTER TABLE `sys_dict_data` DISABLE KEYS */;
INSERT INTO `sys_dict_data` (`id`, `type_id`, `value`, `code`, `sort`, `remark`, `status`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES
	(1265216536659087357, 1265216211667636234, '男', '1', 100, '男性', 0, '2020-04-01 10:23:29', 1265476890672672808, NULL, NULL),
	(1265216536659087358, 1265216211667636234, '女', '2', 100, '女性', 0, '2020-04-01 10:23:49', 1265476890672672808, NULL, NULL),
	(1265216536659087359, 1265216211667636234, '未知', '3', 100, '未知性别', 0, '2020-04-01 10:24:01', 1265476890672672808, NULL, NULL),
	(1265216536659087361, 1265216211667636235, '默认常量', 'DEFAULT', 100, '默认常量，都以SNOWY_开头的', 0, '2020-04-14 23:25:45', 1265476890672672808, NULL, NULL),
	(1265216536659087363, 1265216211667636235, '阿里云短信', 'ALIYUN_SMS', 100, '阿里云短信配置', 0, '2020-04-14 23:25:45', 1265476890672672808, NULL, NULL),
	(1265216536659087364, 1265216211667636235, '腾讯云短信', 'TENCENT_SMS', 100, '腾讯云短信', 0, '2020-04-14 23:25:45', 1265476890672672808, NULL, NULL),
	(1265216536659087365, 1265216211667636235, '邮件配置', 'EMAIL', 100, '邮箱配置', 0, '2020-04-14 23:25:45', 1265476890672672808, NULL, NULL),
	(1265216536659087366, 1265216211667636235, '文件上传路径', 'FILE_PATH', 100, '文件上传路径', 0, '2020-04-14 23:25:45', 1265476890672672808, NULL, NULL),
	(1265216536659087367, 1265216211667636235, 'Oauth配置', 'OAUTH', 100, 'Oauth配置', 0, '2020-04-14 23:25:45', 1265476890672672808, NULL, NULL),
	(1265216617500102656, 1265216211667636226, '正常', '0', 100, '正常', 0, '2020-05-26 17:41:44', 1265476890672672808, NULL, NULL),
	(1265216617500102657, 1265216211667636226, '停用', '1', 100, '停用', 0, '2020-05-26 17:42:03', 1265476890672672808, NULL, NULL),
	(1265216938389524482, 1265216211667636226, '删除', '2', 100, '删除', 0, '2020-05-26 17:43:19', 1265476890672672808, NULL, NULL),
	(1265217669028892673, 1265217074079453185, '否', 'N', 100, '否', 0, '2020-05-26 17:46:14', 1265476890672672808, NULL, NULL),
	(1265217706584690689, 1265217074079453185, '是', 'Y', 100, '是', 0, '2020-05-26 17:46:23', 1265476890672672808, NULL, NULL),
	(1265220776437731330, 1265217846770913282, '登录', '1', 100, '登录', 0, '2020-05-26 17:58:34', 1265476890672672808, NULL, NULL),
	(1265220806070489090, 1265217846770913282, '登出', '2', 100, '登出', 0, '2020-05-26 17:58:41', 1265476890672672808, NULL, NULL),
	(1265221129564573697, 1265221049302372354, '目录', '0', 100, '目录', 0, '2020-05-26 17:59:59', 1265476890672672808, NULL, NULL),
	(1265221163119005697, 1265221049302372354, '菜单', '1', 100, '菜单', 0, '2020-05-26 18:00:07', 1265476890672672808, NULL, NULL),
	(1265221188091891713, 1265221049302372354, '按钮', '2', 100, '按钮', 0, '2020-05-26 18:00:13', 1265476890672672808, NULL, NULL),
	(1265466389204967426, 1265466149622128641, '未发送', '0', 100, '未发送', 0, '2020-05-27 10:14:33', 1265476890672672808, NULL, NULL),
	(1265466432670539778, 1265466149622128641, '发送成功', '1', 100, '发送成功', 0, '2020-05-27 10:14:43', 1265476890672672808, NULL, NULL),
	(1265466486097584130, 1265466149622128641, '发送失败', '2', 100, '发送失败', 0, '2020-05-27 10:14:56', 1265476890672672808, NULL, NULL),
	(1265466530477514754, 1265466149622128641, '失效', '3', 100, '失效', 0, '2020-05-27 10:15:07', 1265476890672672808, NULL, NULL),
	(1265466835009150978, 1265466752209395713, '无', '0', 100, '无', 0, '2020-05-27 10:16:19', 1265476890672672808, NULL, NULL),
	(1265466874758569986, 1265466752209395713, '组件', '1', 100, '组件', 0, '2020-05-27 10:16:29', 1265476890672672808, NULL, NULL),
	(1265466925476093953, 1265466752209395713, '内链', '2', 100, '内链', 0, '2020-05-27 10:16:41', 1265476890672672808, NULL, NULL),
	(1265466962209808385, 1265466752209395713, '外链', '3', 100, '外链', 0, '2020-05-27 10:16:50', 1265476890672672808, NULL, NULL),
	(1265467428423475202, 1265467337566461954, '系统权重', '1', 100, '系统权重', 0, '2020-05-27 10:18:41', 1265476890672672808, NULL, NULL),
	(1265467503090475009, 1265467337566461954, '业务权重', '2', 100, '业务权重', 0, '2020-05-27 10:18:59', 1265476890672672808, NULL, NULL),
	(1265468138431062018, 1265468028632571905, '全部数据', '1', 100, '全部数据', 0, '2020-05-27 10:21:30', 1265476890672672808, NULL, NULL),
	(1265468194928336897, 1265468028632571905, '本部门及以下数据', '2', 100, '本部门及以下数据', 0, '2020-05-27 10:21:44', 1265476890672672808, NULL, NULL),
	(1265468241992622082, 1265468028632571905, '本部门数据', '3', 100, '本部门数据', 0, '2020-05-27 10:21:55', 1265476890672672808, NULL, NULL),
	(1265468273634451457, 1265468028632571905, '仅本人数据', '4', 100, '仅本人数据', 0, '2020-05-27 10:22:02', 1265476890672672808, NULL, NULL),
	(1265468302046666753, 1265468028632571905, '自定义数据', '5', 100, '自定义数据', 0, '2020-05-27 10:22:09', 1265476890672672808, NULL, NULL),
	(1265468508100239362, 1265468437904367618, 'app', '1', 100, 'app', 0, '2020-05-27 10:22:58', 1265476890672672808, NULL, NULL),
	(1265468543433056258, 1265468437904367618, 'pc', '2', 100, 'pc', 0, '2020-05-27 10:23:07', 1265476890672672808, NULL, NULL),
	(1265468576874242050, 1265468437904367618, '其他', '3', 100, '其他', 0, '2020-05-27 10:23:15', 1265476890672672808, NULL, NULL),
	(1275617233011335170, 1275617093517172738, '其它', '0', 100, '其它', 0, '2020-06-24 10:30:23', 1265476890672672808, NULL, NULL),
	(1275617295355469826, 1275617093517172738, '增加', '1', 100, '增加', 0, '2020-06-24 10:30:38', 1265476890672672808, NULL, NULL),
	(1275617348610547714, 1275617093517172738, '删除', '2', 100, '删除', 0, '2020-06-24 10:30:50', 1265476890672672808, NULL, NULL),
	(1275617395515449346, 1275617093517172738, '编辑', '3', 100, '编辑', 0, '2020-06-24 10:31:02', 1265476890672672808, NULL, NULL),
	(1275617433612312577, 1275617093517172738, '更新', '4', 100, '更新', 0, '2020-06-24 10:31:11', 1265476890672672808, NULL, NULL),
	(1275617472707420161, 1275617093517172738, '查询', '5', 100, '查询', 0, '2020-06-24 10:31:20', 1265476890672672808, NULL, NULL),
	(1275617502973517826, 1275617093517172738, '详情', '6', 100, '详情', 0, '2020-06-24 10:31:27', 1265476890672672808, NULL, NULL),
	(1275617536959963137, 1275617093517172738, '树', '7', 100, '树', 0, '2020-06-24 10:31:35', 1265476890672672808, NULL, NULL),
	(1275617619524837377, 1275617093517172738, '导入', '8', 100, '导入', 0, '2020-06-24 10:31:55', 1265476890672672808, NULL, NULL),
	(1275617651816783873, 1275617093517172738, '导出', '9', 100, '导出', 0, '2020-06-24 10:32:03', 1265476890672672808, NULL, NULL),
	(1275617683475390465, 1275617093517172738, '授权', '10', 100, '授权', 0, '2020-06-24 10:32:10', 1265476890672672808, NULL, NULL),
	(1275617709928865793, 1275617093517172738, '强退', '11', 100, '强退', 0, '2020-06-24 10:32:17', 1265476890672672808, NULL, NULL),
	(1275617739091861505, 1275617093517172738, '清空', '12', 100, '清空', 0, '2020-06-24 10:32:23', 1265476890672672808, NULL, NULL),
	(1275617788601425921, 1275617093517172738, '修改状态', '13', 100, '修改状态', 0, '2020-06-24 10:32:35', 1265476890672672808, NULL, NULL),
	(1277774590944317441, 1277774529430654977, '阿里云', '1', 100, '阿里云', 0, '2020-06-30 09:22:57', 1265476890672672808, NULL, NULL),
	(1277774666055913474, 1277774529430654977, '腾讯云', '2', 100, '腾讯云', 0, '2020-06-30 09:23:15', 1265476890672672808, NULL, NULL),
	(1277774695168577538, 1277774529430654977, 'minio', '3', 100, 'minio', 0, '2020-06-30 09:23:22', 1265476890672672808, NULL, NULL),
	(1277774726835572737, 1277774529430654977, '本地', '4', 100, '本地', 0, '2020-06-30 09:23:29', 1265476890672672808, NULL, NULL),
	(1278607123583868929, 1278606951432855553, '运行', '1', 100, '运行', 0, '2020-07-02 16:31:08', 1265476890672672808, NULL, NULL),
	(1278607162943217666, 1278606951432855553, '停止', '2', 100, '停止', 0, '2020-07-02 16:31:18', 1265476890672672808, NULL, NULL),
	(1278939265862004738, 1278911800547147777, '通知', '1', 100, '通知', 0, '2020-07-03 14:30:57', 1265476890672672808, NULL, NULL),
	(1278939319922388994, 1278911800547147777, '公告', '2', 100, '公告', 0, '2020-07-03 14:31:10', 1265476890672672808, NULL, NULL),
	(1278939399001796609, 1278911952657776642, '草稿', '0', 100, '草稿', 0, '2020-07-03 14:31:29', 1265476890672672808, NULL, NULL),
	(1278939432686252034, 1278911952657776642, '发布', '1', 100, '发布', 0, '2020-07-03 14:31:37', 1265476890672672808, NULL, NULL),
	(1278939458804183041, 1278911952657776642, '撤回', '2', 100, '撤回', 0, '2020-07-03 14:31:43', 1265476890672672808, NULL, NULL),
	(1278939485878415362, 1278911952657776642, '删除', '3', 100, '删除', 0, '2020-07-03 14:31:50', 1265476890672672808, NULL, NULL),
	(1291390260160299009, 1291390159941599233, '是', 'true', 100, '是', 2, '2020-08-06 23:06:46', 1265476890672672808, NULL, NULL),
	(1291390315437031426, 1291390159941599233, '否', 'false', 100, '否', 2, '2020-08-06 23:06:59', 1265476890672672808, NULL, NULL),
	(1342446007168466945, 1342445962104864770, '下载压缩包', '1', 100, '下载压缩包', 0, '2020-12-25 20:24:04', 1265476890672672808, NULL, NULL),
	(1342446035433881601, 1342445962104864770, '生成到本项目', '2', 100, '生成到本项目', 0, '2020-12-25 20:24:11', 1265476890672672808, NULL, NULL),
	(1358094655567454210, 1358094419419750401, '输入框', 'input', 100, '输入框', 0, '2021-02-07 00:46:13', 1265476890672672808, '2021-02-08 01:01:28', 1265476890672672808),
	(1358094740510498817, 1358094419419750401, '时间选择', 'datepicker', 100, '时间选择', 0, '2021-02-07 00:46:33', 1265476890672672808, '2021-02-08 01:04:07', 1265476890672672808),
	(1358094793149014017, 1358094419419750401, '下拉框', 'select', 100, '下拉框', 0, '2021-02-07 00:46:46', 1265476890672672808, NULL, NULL),
	(1358095496009506817, 1358094419419750401, '单选框', 'radio', 100, '单选框', 0, '2021-02-07 00:49:33', 1265476890672672808, NULL, NULL),
	(1358095673084633090, 1358094419419750401, '开关', 'switch', 100, '开关', 2, '2021-02-07 00:50:15', 1265476890672672808, '2021-02-11 19:07:18', 1265476890672672808),
	(1358458689433190402, 1358457818733428737, '等于', 'eq', 1, '等于', 0, '2021-02-08 00:52:45', 1265476890672672808, '2021-02-13 23:35:36', 1265476890672672808),
	(1358458785168179202, 1358457818733428737, '模糊', 'like', 2, '模糊', 0, '2021-02-08 00:53:08', 1265476890672672808, '2021-02-13 23:35:46', 1265476890672672808),
	(1358460475682406401, 1358094419419750401, '多选框', 'checkbox', 100, '多选框', 0, '2021-02-08 00:59:51', 1265476890672672808, NULL, NULL),
	(1358460819019743233, 1358094419419750401, '数字输入框', 'inputnumber', 100, '数字输入框', 0, '2021-02-08 01:01:13', 1265476890672672808, NULL, NULL),
	(1358470210267725826, 1358470065111252994, 'Long', 'Long', 100, 'Long', 0, '2021-02-08 01:38:32', 1265476890672672808, NULL, NULL),
	(1358470239351029762, 1358470065111252994, 'String', 'String', 100, 'String', 0, '2021-02-08 01:38:39', 1265476890672672808, NULL, NULL),
	(1358470265640927233, 1358470065111252994, 'Date', 'Date', 100, 'Date', 0, '2021-02-08 01:38:45', 1265476890672672808, NULL, NULL),
	(1358470300168437761, 1358470065111252994, 'Integer', 'Integer', 100, 'Integer', 0, '2021-02-08 01:38:53', 1265476890672672808, NULL, NULL),
	(1358470697377415169, 1358470065111252994, 'boolean', 'boolean', 100, 'boolean', 0, '2021-02-08 01:40:28', 1265476890672672808, '2021-02-08 01:40:47', 1265476890672672808),
	(1358471133434036226, 1358470065111252994, 'int', 'int', 100, 'int', 0, '2021-02-08 01:42:12', 1265476890672672808, NULL, NULL),
	(1358471188291338241, 1358470065111252994, 'double', 'double', 100, 'double', 0, '2021-02-08 01:42:25', 1265476890672672808, NULL, NULL),
	(1358756511688761346, 1358457818733428737, '大于', 'gt', 3, '大于', 0, '2021-02-08 20:36:12', 1265476890672672808, '2021-02-13 23:45:24', 1265476890672672808),
	(1358756547159990274, 1358457818733428737, '小于', 'lt', 4, '大于', 0, '2021-02-08 20:36:20', 1265476890672672808, '2021-02-13 23:45:29', 1265476890672672808),
	(1358756609990664193, 1358457818733428737, '不等于', 'ne', 7, '不等于', 0, '2021-02-08 20:36:35', 1265476890672672808, '2021-02-13 23:45:46', 1265476890672672808),
	(1358756685030957057, 1358457818733428737, '大于等于', 'ge', 5, '大于等于', 0, '2021-02-08 20:36:53', 1265476890672672808, '2021-02-13 23:45:35', 1265476890672672808),
	(1358756800525312001, 1358457818733428737, '小于等于', 'le', 6, '小于等于', 0, '2021-02-08 20:37:20', 1265476890672672808, '2021-02-13 23:45:40', 1265476890672672808),
	(1360529773814083586, 1358094419419750401, '文本域', 'textarea', 100, '文本域', 0, '2021-02-13 18:02:30', 1265476890672672808, NULL, NULL),
	(1360606105914732545, 1358457818733428737, '不为空', 'isNotNull', 8, '不为空', 0, '2021-02-13 23:05:49', 1265476890672672808, '2021-02-13 23:45:50', 1265476890672672808),
	(1471107569499508737, 1265216211667636235, '加密配置', 'CRYPTOGRAM', 100, '加密配置，默认全开', 0, '2021-12-15 21:19:11', 1265476890672672808, '2021-12-15 21:19:24', 1265476890672672808),
	(1540152671325863938, 1540152428538576898, '厘秒', 'NANOSECONDS', 100, NULL, 0, '2022-06-24 09:59:46', 1265476890672672808, NULL, NULL),
	(1540152741135859714, 1540152428538576898, '微秒', 'MICROSECONDS', 100, NULL, 0, '2022-06-24 10:00:02', 1265476890672672808, NULL, NULL),
	(1540152780201607170, 1540152428538576898, '毫秒', 'MILLISECONDS', 100, NULL, 0, '2022-06-24 10:00:12', 1265476890672672808, NULL, NULL),
	(1540152823130308609, 1540152428538576898, '秒', 'SECONDS', 100, NULL, 0, '2022-06-24 10:00:22', 1265476890672672808, NULL, NULL),
	(1540152888209129474, 1540152428538576898, '分', 'MINUTES', 100, NULL, 0, '2022-06-24 10:00:37', 1265476890672672808, NULL, NULL),
	(1540152940105252865, 1540152428538576898, '小时', 'HOURS', 100, NULL, 0, '2022-06-24 10:00:50', 1265476890672672808, NULL, NULL),
	(1540152980425097218, 1540152428538576898, '天', 'DAYS', 100, NULL, 0, '2022-06-24 10:00:59', 1265476890672672808, NULL, NULL),
	(1540361092914491394, 1540357135957766146, 'JDBC', 'JDBC', 1, NULL, 0, '2022-06-24 23:47:57', 1265476890672672808, '2022-06-25 01:00:43', 1265476890672672808),
	(1540379372811276289, 1540357135957766146, '文件', 'FILE', 2, NULL, 0, '2022-06-25 01:00:36', 1265476890672672808, '2022-06-25 01:00:59', 1265476890672672808),
	(1540603511316701186, 1540603266612617218, '一对多', 'ONE_TO_MANY', 1, NULL, 0, '2022-06-25 15:51:14', 1265476890672672808, NULL, NULL),
	(1540603571324608513, 1540603266612617218, '一对一', 'ONE_TO_ONE', 2, NULL, 0, '2022-06-25 15:51:29', 1265476890672672808, NULL, NULL),
	(1540603705122906114, 1540603266612617218, '多对多', 'MANY_TO_MANY', 3, NULL, 0, '2022-06-25 15:52:01', 1265476890672672808, NULL, NULL),
	(1540603780159004673, 1540603266612617218, '多对一', 'MANY_TO_ONE', 4, NULL, 0, '2022-06-25 15:52:18', 1265476890672672808, NULL, NULL);
/*!40000 ALTER TABLE `sys_dict_data` ENABLE KEYS */;

-- Dumping structure for table young-board.sys_dict_type
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE IF NOT EXISTS `sys_dict_type` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `code` varchar(50) NOT NULL COMMENT '编码',
  `sort` int(11) NOT NULL COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` tinyint(4) NOT NULL COMMENT '状态（字典 0正常 1停用 2删除）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统字典类型表';

-- Dumping data for table young-board.sys_dict_type: ~22 rows (approximately)
DELETE FROM `sys_dict_type`;
/*!40000 ALTER TABLE `sys_dict_type` DISABLE KEYS */;
INSERT INTO `sys_dict_type` (`id`, `name`, `code`, `sort`, `remark`, `status`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES
	(1265216211667636226, '通用状态', 'common_status', 100, '通用状态', 0, '2020-05-26 17:40:26', 1265476890672672808, '2020-06-08 11:31:47', 1265476890672672808),
	(1265216211667636234, '性别', 'sex', 100, '性别字典', 0, '2020-04-01 10:12:30', 1265476890672672808, NULL, NULL),
	(1265216211667636235, '常量的分类', 'consts_type', 100, '常量的分类，用于区别一组配置', 0, '2020-04-14 23:24:13', 1265476890672672808, NULL, NULL),
	(1265217074079453185, '是否', 'yes_or_no', 100, '是否', 0, '2020-05-26 17:43:52', 1265476890672672808, NULL, NULL),
	(1265217846770913282, '访问类型', 'vis_type', 100, '访问类型', 0, '2020-05-26 17:46:56', 1265476890672672808, NULL, NULL),
	(1265221049302372354, '菜单类型', 'menu_type', 100, '菜单类型', 0, '2020-05-26 17:59:39', 1265476890672672808, NULL, NULL),
	(1265466149622128641, '发送类型', 'send_type', 100, '发送类型', 0, '2020-05-27 10:13:36', 1265476890672672808, NULL, NULL),
	(1265466752209395713, '打开方式', 'open_type', 100, '打开方式', 0, '2020-05-27 10:16:00', 1265476890672672808, NULL, NULL),
	(1265467337566461954, '菜单权重', 'menu_weight', 100, '菜单权重', 0, '2020-05-27 10:18:19', 1265476890672672808, NULL, NULL),
	(1265468028632571905, '数据范围类型', 'data_scope_type', 100, '数据范围类型', 0, '2020-05-27 10:21:04', 1265476890672672808, NULL, NULL),
	(1265468437904367618, '短信发送来源', 'sms_send_source', 100, '短信发送来源', 0, '2020-05-27 10:22:42', 1265476890672672808, NULL, NULL),
	(1275617093517172738, '操作类型', 'op_type', 100, '操作类型', 0, '2020-06-24 10:29:50', 1265476890672672808, NULL, NULL),
	(1277774529430654977, '文件存储位置', 'file_storage_location', 100, '文件存储位置', 0, '2020-06-30 09:22:42', 1265476890672672808, NULL, NULL),
	(1278606951432855553, '运行状态', 'run_status', 100, '定时任务运行状态', 0, '2020-07-02 16:30:27', 1265476890672672808, NULL, NULL),
	(1278911800547147777, '通知公告类型', 'notice_type', 100, '通知公告类型', 0, '2020-07-03 12:41:49', 1265476890672672808, NULL, NULL),
	(1278911952657776642, '通知公告状态', 'notice_status', 100, '通知公告状态', 0, '2020-07-03 12:42:25', 1265476890672672808, NULL, NULL),
	(1291390159941599233, '是否boolean', 'yes_true_false', 100, '是否boolean', 2, '2020-08-06 23:06:22', 1265476890672672808, NULL, NULL),
	(1342445962104864770, '代码生成方式', 'code_gen_create_type', 100, '代码生成方式', 0, '2020-12-25 20:23:53', 1265476890672672808, NULL, NULL),
	(1358094419419750401, '代码生成作用类型', 'code_gen_effect_type', 100, '代码生成作用类型', 0, '2021-02-07 00:45:16', 1265476890672672808, '2021-02-08 00:47:48', 1265476890672672808),
	(1358457818733428737, '代码生成查询类型', 'code_gen_query_type', 100, '代码生成查询类型', 0, '2021-02-08 00:49:18', 1265476890672672808, NULL, NULL),
	(1358470065111252994, '代码生成java类型', 'code_gen_java_type', 100, '代码生成java类型', 0, '2021-02-08 01:37:57', 1265476890672672808, NULL, NULL),
	(1540152428538576898, '缓存时间类型', 'cache_time_unit', 100, NULL, 0, '2022-06-24 09:58:48', 1265476890672672808, NULL, NULL),
	(1540357135957766146, '数据源类型', 'board_datasource_type', 1, NULL, 0, '2022-06-24 23:32:14', 1265476890672672808, NULL, NULL),
	(1540603266612617218, '表关联类型', 'board_connect_type', 2, NULL, 0, '2022-06-25 15:50:16', 1265476890672672808, '2022-06-25 15:50:31', 1265476890672672808);
/*!40000 ALTER TABLE `sys_dict_type` ENABLE KEYS */;

-- Dumping structure for table young-board.sys_emp
DROP TABLE IF EXISTS `sys_emp`;
CREATE TABLE IF NOT EXISTS `sys_emp` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `job_num` varchar(100) DEFAULT NULL COMMENT '工号',
  `org_id` bigint(20) NOT NULL COMMENT '所属机构id',
  `org_name` varchar(100) NOT NULL COMMENT '所属机构名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='员工表';

-- Dumping data for table young-board.sys_emp: ~0 rows (approximately)
DELETE FROM `sys_emp`;
/*!40000 ALTER TABLE `sys_emp` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_emp` ENABLE KEYS */;

-- Dumping structure for table young-board.sys_emp_ext_org_pos
DROP TABLE IF EXISTS `sys_emp_ext_org_pos`;
CREATE TABLE IF NOT EXISTS `sys_emp_ext_org_pos` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `emp_id` bigint(20) NOT NULL COMMENT '员工id',
  `org_id` bigint(20) NOT NULL COMMENT '机构id',
  `pos_id` bigint(20) NOT NULL COMMENT '岗位id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='员工附属机构岗位表';

-- Dumping data for table young-board.sys_emp_ext_org_pos: ~0 rows (approximately)
DELETE FROM `sys_emp_ext_org_pos`;
/*!40000 ALTER TABLE `sys_emp_ext_org_pos` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_emp_ext_org_pos` ENABLE KEYS */;

-- Dumping structure for table young-board.sys_emp_pos
DROP TABLE IF EXISTS `sys_emp_pos`;
CREATE TABLE IF NOT EXISTS `sys_emp_pos` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `emp_id` bigint(20) NOT NULL COMMENT '员工id',
  `pos_id` bigint(20) NOT NULL COMMENT '职位id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='员工职位关联表';

-- Dumping data for table young-board.sys_emp_pos: ~0 rows (approximately)
DELETE FROM `sys_emp_pos`;
/*!40000 ALTER TABLE `sys_emp_pos` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_emp_pos` ENABLE KEYS */;

-- Dumping structure for table young-board.sys_file_info
DROP TABLE IF EXISTS `sys_file_info`;
CREATE TABLE IF NOT EXISTS `sys_file_info` (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `file_location` tinyint(4) NOT NULL COMMENT '文件存储位置（1:阿里云，2:腾讯云，3:minio，4:本地）',
  `file_bucket` varchar(1000) DEFAULT NULL COMMENT '文件仓库',
  `file_origin_name` varchar(100) NOT NULL COMMENT '文件名称（上传时候的文件名）',
  `file_suffix` varchar(50) DEFAULT NULL COMMENT '文件后缀',
  `file_size_kb` bigint(20) DEFAULT NULL COMMENT '文件大小kb',
  `file_size_info` varchar(100) DEFAULT NULL COMMENT '文件大小信息，计算后的',
  `file_object_name` varchar(100) NOT NULL COMMENT '存储到bucket的名称（文件唯一标识id）',
  `file_path` varchar(1000) DEFAULT NULL COMMENT '存储路径',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '修改用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='文件信息表';

-- Dumping data for table young-board.sys_file_info: ~0 rows (approximately)
DELETE FROM `sys_file_info`;
/*!40000 ALTER TABLE `sys_file_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_file_info` ENABLE KEYS */;

-- Dumping structure for table young-board.sys_menu
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE IF NOT EXISTS `sys_menu` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `pid` bigint(20) NOT NULL COMMENT '父id',
  `pids` text NOT NULL COMMENT '父ids',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `code` varchar(50) NOT NULL COMMENT '编码',
  `type` tinyint(4) NOT NULL DEFAULT 1 COMMENT '菜单类型（字典 0目录 1菜单 2按钮）',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `router` varchar(255) DEFAULT NULL COMMENT '路由地址',
  `component` varchar(255) DEFAULT NULL COMMENT '组件地址',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限标识',
  `application` varchar(50) NOT NULL COMMENT '应用分类（应用编码）',
  `open_type` tinyint(4) NOT NULL COMMENT '打开方式（字典 0无 1组件 2内链 3外链）',
  `visible` char(1) NOT NULL COMMENT '是否可见（Y-是，N-否）',
  `link` varchar(255) DEFAULT NULL COMMENT '链接地址',
  `redirect` varchar(255) DEFAULT NULL COMMENT '重定向地址',
  `weight` tinyint(4) DEFAULT NULL COMMENT '权重（字典 1系统权重 2业务权重）',
  `sort` int(11) NOT NULL COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态（字典 0正常 1停用 2删除）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统菜单表';

-- Dumping data for table young-board.sys_menu: ~183 rows (approximately)
DELETE FROM `sys_menu`;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` (`id`, `pid`, `pids`, `name`, `code`, `type`, `icon`, `router`, `component`, `permission`, `application`, `open_type`, `visible`, `link`, `redirect`, `weight`, `sort`, `remark`, `status`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES
	(1264622039642255311, 0, '[0],', '主控面板', 'system_index', 0, 'home', '/', 'RouteView', NULL, 'system', 0, 'Y', NULL, '/analysis', 1, 1, NULL, 0, '2020-05-25 02:19:24', 1265476890672672808, '2022-06-22 18:03:29', 1265476890672672808),
	(1264622039642255321, 1264622039642255311, '[0],[1264622039642255311],', '分析页', 'system_index_dashboard', 1, NULL, 'analysis', 'system/dashboard/Analysis', NULL, 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-05-25 02:21:55', 1265476890672672808, NULL, NULL),
	(1264622039642255331, 1264622039642255311, '[0],[1264622039642255311],', '工作台', 'system_index_workplace', 1, NULL, 'workplace', 'system/dashboard/Workplace', NULL, 'system', 0, 'Y', NULL, NULL, 1, 2, NULL, 0, '2020-05-25 02:23:48', 1265476890672672808, NULL, NULL),
	(1264622039642255341, 0, '[0],', '组织架构', 'sys_mgr', 0, 'team', '/sys', 'PageView', NULL, 'system', 0, 'Y', NULL, NULL, 1, 3, NULL, 0, '2020-03-27 15:58:16', 1265476890672672808, '2022-06-22 18:03:55', 1265476890672672808),
	(1264622039642255351, 1264622039642255341, '[0],[1264622039642255341],', '用户管理', 'sys_user_mgr', 1, NULL, '/mgr_user', 'system/user/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 3, NULL, 0, '2020-03-27 16:10:21', 1265476890672672808, NULL, NULL),
	(1264622039642255361, 1264622039642255351, '[0],[1264622039642255341],[1264622039642255351],', '用户查询', 'sys_user_mgr_page', 2, NULL, NULL, NULL, 'sysUser:page', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 16:36:49', 1265476890672672808, NULL, NULL),
	(1264622039642255371, 1264622039642255351, '[0],[1264622039642255341],[1264622039642255351],', '用户编辑', 'sys_user_mgr_edit', 2, NULL, NULL, NULL, 'sysUser:edit', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 12:20:23', 1265476890672672808, NULL, NULL),
	(1264622039642255381, 1264622039642255351, '[0],[1264622039642255341],[1264622039642255351],', '用户增加', 'sys_user_mgr_add', 2, NULL, NULL, NULL, 'sysUser:add', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 16:37:35', 1265476890672672808, NULL, NULL),
	(1264622039642255391, 1264622039642255351, '[0],[1264622039642255341],[1264622039642255351],', '用户删除', 'sys_user_mgr_delete', 2, NULL, NULL, NULL, 'sysUser:delete', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 16:37:58', 1265476890672672808, NULL, NULL),
	(1264622039642255401, 1264622039642255351, '[0],[1264622039642255341],[1264622039642255351],', '用户详情', 'sys_user_mgr_detail', 2, NULL, NULL, NULL, 'sysUser:detail', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 16:38:25', 1265476890672672808, NULL, NULL),
	(1264622039642255411, 1264622039642255351, '[0],[1264622039642255341],[1264622039642255351],', '用户导出', 'sys_user_mgr_export', 2, NULL, NULL, NULL, 'sysUser:export', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 12:21:59', 1265476890672672808, NULL, NULL),
	(1264622039642255421, 1264622039642255351, '[0],[1264622039642255341],[1264622039642255351],', '用户选择器', 'sys_user_mgr_selector', 2, NULL, NULL, NULL, 'sysUser:selector', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-03 13:30:14', 1265476890672672808, NULL, NULL),
	(1264622039642255431, 1264622039642255351, '[0],[1264622039642255341],[1264622039642255351],', '用户授权角色', 'sys_user_mgr_grant_role', 2, NULL, NULL, NULL, 'sysUser:grantRole', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 09:22:01', 1265476890672672808, NULL, NULL),
	(1264622039642255441, 1264622039642255351, '[0],[1264622039642255341],[1264622039642255351],', '用户拥有角色', 'sys_user_mgr_own_role', 2, NULL, NULL, NULL, 'sysUser:ownRole', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-05-29 14:27:22', 1265476890672672808, NULL, NULL),
	(1264622039642255451, 1264622039642255351, '[0],[1264622039642255341],[1264622039642255351],', '用户授权数据', 'sys_user_mgr_grant_data', 2, NULL, NULL, NULL, 'sysUser:grantData', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 09:22:13', 1265476890672672808, NULL, NULL),
	(1264622039642255461, 1264622039642255351, '[0],[1264622039642255341],[1264622039642255351],', '用户拥有数据', 'sys_user_mgr_own_data', 2, NULL, NULL, NULL, 'sysUser:ownData', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-05-29 14:27:41', 1265476890672672808, NULL, NULL),
	(1264622039642255471, 1264622039642255351, '[0],[1264622039642255341],[1264622039642255351],', '用户更新信息', 'sys_user_mgr_update_info', 2, NULL, NULL, NULL, 'sysUser:updateInfo', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 16:19:32', 1265476890672672808, NULL, NULL),
	(1264622039642255481, 1264622039642255351, '[0],[1264622039642255341],[1264622039642255351],', '用户修改密码', 'sys_user_mgr_update_pwd', 2, NULL, NULL, NULL, 'sysUser:updatePwd', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 16:20:25', 1265476890672672808, NULL, NULL),
	(1264622039642255491, 1264622039642255351, '[0],[1264622039642255341],[1264622039642255351],', '用户修改状态', 'sys_user_mgr_change_status', 2, NULL, NULL, NULL, 'sysUser:changeStatus', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-23 11:13:14', 1265476890672672808, NULL, NULL),
	(1264622039642255501, 1264622039642255351, '[0],[1264622039642255341],[1264622039642255351],', '用户修改头像', 'sys_user_mgr_update_avatar', 2, NULL, NULL, NULL, 'sysUser:updateAvatar', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 12:21:42', 1265476890672672808, NULL, NULL),
	(1264622039642255511, 1264622039642255351, '[0],[1264622039642255341],[1264622039642255351],', '用户重置密码', 'sys_user_mgr_reset_pwd', 2, NULL, NULL, NULL, 'sysUser:resetPwd', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-05-29 15:01:51', 1265476890672672808, NULL, NULL),
	(1264622039642255521, 1264622039642255341, '[0],[1264622039642255341],', '机构管理', 'sys_org_mgr', 1, NULL, '/org', 'system/org/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 4, NULL, 0, '2020-03-27 17:15:39', 1265476890672672808, NULL, NULL),
	(1264622039642255531, 1264622039642255521, '[0],[1264622039642255341],[1264622039642255521]', '机构查询', 'sys_org_mgr_page', 2, NULL, NULL, NULL, 'sysOrg:page', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 17:17:37', 1265476890672672808, NULL, NULL),
	(1264622039642255541, 1264622039642255521, '[0],[1264622039642255341],[1264622039642255521]', '机构列表', 'sys_org_mgr_list', 2, NULL, NULL, NULL, 'sysOrg:list', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 11:54:26', 1265476890672672808, NULL, NULL),
	(1264622039642255551, 1264622039642255521, '[0],[1264622039642255341],[1264622039642255521]', '机构增加', 'sys_org_mgr_add', 2, NULL, NULL, NULL, 'sysOrg:add', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 17:19:53', 1265476890672672808, NULL, NULL),
	(1264622039642255561, 1264622039642255521, '[0],[1264622039642255341],[1264622039642255521]', '机构编辑', 'sys_org_mgr_edit', 2, NULL, NULL, NULL, 'sysOrg:edit', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 11:54:37', 1265476890672672808, NULL, NULL),
	(1264622039642255571, 1264622039642255521, '[0],[1264622039642255341],[1264622039642255521]', '机构删除', 'sys_org_mgr_delete', 2, NULL, NULL, NULL, 'sysOrg:delete', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 17:20:48', 1265476890672672808, NULL, NULL),
	(1264622039642255581, 1264622039642255521, '[0],[1264622039642255341],[1264622039642255521]', '机构详情', 'sys_org_mgr_detail', 2, NULL, NULL, NULL, 'sysOrg:detail', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 17:21:15', 1265476890672672808, NULL, NULL),
	(1264622039642255591, 1264622039642255521, '[0],[1264622039642255341],[1264622039642255521]', '机构树', 'sys_org_mgr_tree', 2, NULL, NULL, NULL, 'sysOrg:tree', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 17:21:58', 1265476890672672808, NULL, NULL),
	(1264622039642255601, 1264622039642255341, '[0],[1264622039642255341],', '职位管理', 'sys_pos_mgr', 1, NULL, '/pos', 'system/pos/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 5, NULL, 0, '2020-03-27 18:38:31', 1265476890672672808, NULL, NULL),
	(1264622039642255611, 1264622039642255601, '[0],[1264622039642255341],[1264622039642255601],', '职位查询', 'sys_pos_mgr_page', 2, NULL, NULL, NULL, 'sysPos:page', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 18:41:48', 1265476890672672808, NULL, NULL),
	(1264622039642255621, 1264622039642255601, '[0],[1264622039642255341],[1264622039642255601],', '职位列表', 'sys_pos_mgr_list', 2, NULL, NULL, NULL, 'sysPos:list', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 11:55:57', 1265476890672672808, NULL, NULL),
	(1264622039642255631, 1264622039642255601, '[0],[1264622039642255341],[1264622039642255601],', '职位增加', 'sys_pos_mgr_add', 2, NULL, NULL, NULL, 'sysPos:add', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 18:42:20', 1265476890672672808, NULL, NULL),
	(1264622039642255641, 1264622039642255601, '[0],[1264622039642255341],[1264622039642255601],', '职位编辑', 'sys_pos_mgr_edit', 2, NULL, NULL, NULL, 'sysPos:edit', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 11:56:08', 1265476890672672808, NULL, NULL),
	(1264622039642255651, 1264622039642255601, '[0],[1264622039642255341],[1264622039642255601],', '职位删除', 'sys_pos_mgr_delete', 2, NULL, NULL, NULL, 'sysPos:delete', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 18:42:39', 1265476890672672808, NULL, NULL),
	(1264622039642255661, 1264622039642255601, '[0],[1264622039642255341],[1264622039642255601],', '职位详情', 'sys_pos_mgr_detail', 2, NULL, NULL, NULL, 'sysPos:detail', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 18:43:00', 1265476890672672808, NULL, NULL),
	(1264622039642255671, 0, '[0],', '权限管理', 'auth_manager', 0, 'safety-certificate', '/auth', 'PageView', NULL, 'system', 0, 'Y', NULL, NULL, 1, 3, NULL, 0, '2020-07-15 15:51:57', 1265476890672672808, NULL, NULL),
	(1264622039642255681, 1264622039642255671, '[0],[1264622039642255671],', '应用管理', 'sys_app_mgr', 1, NULL, '/app', 'system/app/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 6, NULL, 0, '2020-03-27 16:40:21', 1265476890672672808, NULL, NULL),
	(1264622039642255691, 1264622039642255681, '[0],[1264622039642255671],[1264622039642255681],', '应用查询', 'sys_app_mgr_page', 2, NULL, NULL, NULL, 'sysApp:page', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 16:41:58', 1265476890672672808, NULL, NULL),
	(1264622039642255701, 1264622039642255681, '[0],[1264622039642255671],[1264622039642255681],', '应用列表', 'sys_app_mgr_list', 2, NULL, NULL, NULL, 'sysApp:list', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 10:04:59', 1265476890672672808, NULL, NULL),
	(1264622039642255711, 1264622039642255681, '[0],[1264622039642255671],[1264622039642255681],', '应用增加', 'sys_app_mgr_add', 2, NULL, NULL, NULL, 'sysApp:add', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 16:44:10', 1265476890672672808, NULL, NULL),
	(1264622039642255721, 1264622039642255681, '[0],[1264622039642255671],[1264622039642255681],', '应用编辑', 'sys_app_mgr_edit', 2, NULL, NULL, NULL, 'sysApp:edit', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 10:04:34', 1265476890672672808, NULL, NULL),
	(1264622039642255731, 1264622039642255681, '[0],[1264622039642255671],[1264622039642255681],', '应用删除', 'sys_app_mgr_delete', 2, NULL, NULL, NULL, 'sysApp:delete', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 17:14:29', 1265476890672672808, NULL, NULL),
	(1264622039642255741, 1264622039642255681, '[0],[1264622039642255671],[1264622039642255681],', '应用详情', 'sys_app_mgr_detail', 2, NULL, NULL, NULL, 'sysApp:detail', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 17:14:56', 1265476890672672808, NULL, NULL),
	(1264622039642255751, 1264622039642255681, '[0],[1264622039642255671],[1264622039642255681],', '设为默认应用', 'sys_app_mgr_set_as_default', 2, NULL, NULL, NULL, 'sysApp:setAsDefault', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 17:14:56', 1265476890672672808, NULL, NULL),
	(1264622039642255761, 1264622039642255671, '[0],[1264622039642255671],', '菜单管理', 'sys_menu_mgr', 1, NULL, '/menu', 'system/menu/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 7, NULL, 0, '2020-03-27 18:44:35', 1265476890672672808, NULL, NULL),
	(1264622039642255771, 1264622039642255761, '[0],[1264622039642255671],[1264622039642255761],', '菜单列表', 'sys_menu_mgr_list', 2, NULL, NULL, NULL, 'sysMenu:list', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 18:45:20', 1265476890672672808, NULL, NULL),
	(1264622039642255781, 1264622039642255761, '[0],[1264622039642255671],[1264622039642255761],', '菜单增加', 'sys_menu_mgr_add', 2, NULL, NULL, NULL, 'sysMenu:add', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 18:45:37', 1265476890672672808, NULL, NULL),
	(1264622039642255791, 1264622039642255761, '[0],[1264622039642255671],[1264622039642255761],', '菜单编辑', 'sys_menu_mgr_edit', 2, NULL, NULL, NULL, 'sysMenu:edit', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 11:52:00', 1265476890672672808, NULL, NULL),
	(1264622039642255801, 1264622039642255761, '[0],[1264622039642255671],[1264622039642255761],', '菜单删除', 'sys_menu_mgr_delete', 2, NULL, NULL, NULL, 'sysMenu:delete', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 18:46:01', 1265476890672672808, NULL, NULL),
	(1264622039642255811, 1264622039642255761, '[0],[1264622039642255671],[1264622039642255761],', '菜单详情', 'sys_menu_mgr_detail', 2, NULL, NULL, NULL, 'sysMenu:detail', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 18:46:22', 1265476890672672808, NULL, NULL),
	(1264622039642255821, 1264622039642255761, '[0],[1264622039642255671],[1264622039642255761],', '菜单授权树', 'sys_menu_mgr_grant_tree', 2, NULL, NULL, NULL, 'sysMenu:treeForGrant', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-03 09:50:31', 1265476890672672808, NULL, NULL),
	(1264622039642255831, 1264622039642255761, '[0],[1264622039642255671],[1264622039642255761],', '菜单树', 'sys_menu_mgr_tree', 2, NULL, NULL, NULL, 'sysMenu:tree', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-27 18:47:50', 1265476890672672808, NULL, NULL),
	(1264622039642255841, 1264622039642255761, '[0],[1264622039642255671],[1264622039642255761],', '菜单切换', 'sys_menu_mgr_change', 2, NULL, NULL, NULL, 'sysMenu:change', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-03 09:51:43', 1265476890672672808, NULL, NULL),
	(1264622039642255851, 1264622039642255671, '[0],[1264622039642255671],', '角色管理', 'sys_role_mgr', 1, NULL, '/role', 'system/role/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 8, NULL, 0, '2020-03-28 16:01:09', 1265476890672672808, NULL, NULL),
	(1264622039642255861, 1264622039642255851, '[0],[1264622039642255671],[1264622039642255851],', '角色查询', 'sys_role_mgr_page', 2, NULL, NULL, NULL, 'sysRole:page', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-28 16:02:09', 1265476890672672808, NULL, NULL),
	(1264622039642255871, 1264622039642255851, '[0],[1264622039642255671],[1264622039642255851],', '角色增加', 'sys_role_mgr_add', 2, NULL, NULL, NULL, 'sysRole:add', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-28 16:02:27', 1265476890672672808, NULL, NULL),
	(1264622039642255881, 1264622039642255851, '[0],[1264622039642255671],[1264622039642255851],', '角色编辑', 'sys_role_mgr_edit', 2, NULL, NULL, NULL, 'sysRole:edit', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 11:57:27', 1265476890672672808, NULL, NULL),
	(1264622039642255891, 1264622039642255851, '[0],[1264622039642255671],[1264622039642255851],', '角色删除', 'sys_role_mgr_delete', 2, NULL, NULL, NULL, 'sysRole:delete', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-28 16:02:46', 1265476890672672808, NULL, NULL),
	(1264622039642255901, 1264622039642255851, '[0],[1264622039642255671],[1264622039642255851],', '角色详情', 'sys_role_mgr_detail', 2, NULL, NULL, NULL, 'sysRole:detail', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-03-28 16:03:01', 1265476890672672808, NULL, NULL),
	(1264622039642255911, 1264622039642255851, '[0],[1264622039642255671],[1264622039642255851],', '角色下拉', 'sys_role_mgr_drop_down', 2, NULL, NULL, NULL, 'sysRole:dropDown', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-05-29 15:45:39', 1265476890672672808, NULL, NULL),
	(1264622039642255921, 1264622039642255851, '[0],[1264622039642255671],[1264622039642255851],', '角色授权菜单', 'sys_role_mgr_grant_menu', 2, NULL, NULL, NULL, 'sysRole:grantMenu', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 09:16:27', 1265476890672672808, NULL, NULL),
	(1264622039642255931, 1264622039642255851, '[0],[1264622039642255671],[1264622039642255851],', '角色拥有菜单', 'sys_role_mgr_own_menu', 2, NULL, NULL, NULL, 'sysRole:ownMenu', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-05-29 14:21:54', 1265476890672672808, NULL, NULL),
	(1264622039642255941, 1264622039642255851, '[0],[1264622039642255671],[1264622039642255851],', '角色授权数据', 'sys_role_mgr_grant_data', 2, NULL, NULL, NULL, 'sysRole:grantData', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 09:16:56', 1265476890672672808, NULL, NULL),
	(1264622039642255951, 1264622039642255851, '[0],[1264622039642255671],[1264622039642255851],', '角色拥有数据', 'sys_role_mgr_own_data', 2, NULL, NULL, NULL, 'sysRole:ownData', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-05-29 14:23:08', 1265476890672672808, NULL, NULL),
	(1264622039642255961, 0, '[0],', '开发管理', 'system_tools', 0, 'euro', '/tools', 'PageView', NULL, 'system', 1, 'Y', NULL, NULL, 1, 4, NULL, 0, '2020-05-25 02:10:55', 1265476890672672808, NULL, NULL),
	(1264622039642255971, 1264622039642255961, '[0],[1264622039642255961],', '系统配置', 'system_tools_config', 1, NULL, '/config', 'system/config/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 9, NULL, 0, '2020-05-25 02:12:56', 1265476890672672808, NULL, NULL),
	(1264622039642255981, 1264622039642255971, '[0],[1264622039642255961],[1264622039642255971],', '配置查询', 'system_tools_config_page', 2, NULL, NULL, NULL, 'sysConfig:page', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-05-27 17:02:22', 1265476890672672808, NULL, NULL),
	(1264622039642255991, 1264622039642255971, '[0],[1264622039642255961],[1264622039642255971],', '配置列表', 'system_tools_config_list', 2, NULL, NULL, NULL, 'sysConfig:list', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-05-27 17:02:42', 1265476890672672808, NULL, NULL),
	(1264622039642256001, 1264622039642255971, '[0],[1264622039642255961],[1264622039642255971],', '配置增加', 'system_tools_config_add', 2, NULL, NULL, NULL, 'sysConfig:add', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-05-27 17:03:31', 1265476890672672808, NULL, NULL),
	(1264622039642256011, 1264622039642255971, '[0],[1264622039642255961],[1264622039642255971],', '配置编辑', 'system_tools_config_edit', 2, NULL, NULL, NULL, 'sysConfig:edit', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-05-27 17:03:55', 1265476890672672808, NULL, NULL),
	(1264622039642256021, 1264622039642255971, '[0],[1264622039642255961],[1264622039642255971],', '配置删除', 'system_tools_config_delete', 2, NULL, NULL, NULL, 'sysConfig:delete', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-05-27 17:03:44', 1265476890672672808, NULL, NULL),
	(1264622039642256031, 1264622039642255971, '[0],[1264622039642255961],[1264622039642255971],', '配置详情', 'system_tools_config_detail', 2, NULL, NULL, NULL, 'sysConfig:detail', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-05-27 17:02:59', 1265476890672672808, NULL, NULL),
	(1264622039642256041, 1264622039642255961, '[0],[1264622039642255961],', '邮件发送', 'sys_email_mgr', 1, NULL, '/email', 'system/email/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 10, NULL, 0, '2020-07-02 11:44:21', 1265476890672672808, NULL, NULL),
	(1264622039642256051, 1264622039642256041, '[0],[1264622039642255961],[1264622039642256041],', '发送文本邮件', 'sys_email_mgr_send_email', 2, NULL, NULL, NULL, 'email:sendEmail', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 11:45:39', 1265476890672672808, NULL, NULL),
	(1264622039642256061, 1264622039642256041, '[0],[1264622039642255961],[1264622039642256041],', '发送html邮件', 'sys_email_mgr_send_email_html', 2, NULL, NULL, NULL, 'email:sendEmailHtml', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 11:45:57', 1265476890672672808, NULL, NULL),
	(1264622039642256071, 1264622039642255961, '[0],[1264622039642255961],', '短信管理', 'sys_sms_mgr', 1, NULL, '/sms', 'system/sms/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 11, NULL, 0, '2020-07-02 12:00:12', 1265476890672672808, NULL, NULL),
	(1264622039642256081, 1264622039642256071, '[0],[1264622039642255961],[1264622039642256071],', '短信发送查询', 'sys_sms_mgr_page', 2, NULL, NULL, NULL, 'sms:page', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 12:16:56', 1265476890672672808, NULL, NULL),
	(1264622039642256091, 1264622039642256071, '[0],[1264622039642255961],[1264622039642256071],', '发送验证码短信', 'sys_sms_mgr_send_login_message', 2, NULL, NULL, NULL, 'sms:sendLoginMessage', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 12:02:31', 1265476890672672808, NULL, NULL),
	(1264622039642256101, 1264622039642256071, '[0],[1264622039642255961],[1264622039642256071],', '验证短信验证码', 'sys_sms_mgr_validate_message', 2, NULL, NULL, NULL, 'sms:validateMessage', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 12:02:50', 1265476890672672808, NULL, NULL),
	(1264622039642256111, 1264622039642255961, '[0],[1264622039642255961],', '字典管理', 'sys_dict_mgr', 1, NULL, '/dict', 'system/dict/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 12, NULL, 0, '2020-04-01 11:17:26', 1265476890672672808, NULL, NULL),
	(1264622039642256121, 1264622039642256111, '[0],[1264622039642255961],[1264622039642256111],', '字典类型查询', 'sys_dict_mgr_dict_type_page', 2, NULL, NULL, NULL, 'sysDictType:page', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 11:20:22', 1265476890672672808, NULL, NULL),
	(1264622039642256131, 1264622039642256111, '[0],[1264622039642255961],[1264622039642256111],', '字典类型列表', 'sys_dict_mgr_dict_type_list', 2, NULL, NULL, NULL, 'sysDictType:list', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-05-29 15:12:35', 1265476890672672808, NULL, NULL),
	(1264622039642256141, 1264622039642256111, '[0],[1264622039642255961],[1264622039642256111],', '字典类型增加', 'sys_dict_mgr_dict_type_add', 2, NULL, NULL, NULL, 'sysDictType:add', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 11:19:58', 1265476890672672808, NULL, NULL),
	(1264622039642256151, 1264622039642256111, '[0],[1264622039642255961],[1264622039642256111],', '字典类型删除', 'sys_dict_mgr_dict_type_delete', 2, NULL, NULL, NULL, 'sysDictType:delete', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 11:21:30', 1265476890672672808, NULL, NULL),
	(1264622039642256161, 1264622039642256111, '[0],[1264622039642255961],[1264622039642256111],', '字典类型编辑', 'sys_dict_mgr_dict_type_edit', 2, NULL, NULL, NULL, 'sysDictType:edit', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 11:21:42', 1265476890672672808, NULL, NULL),
	(1264622039642256171, 1264622039642256111, '[0],[1264622039642255961],[1264622039642256111],', '字典类型详情', 'sys_dict_mgr_dict_type_detail', 2, NULL, NULL, NULL, 'sysDictType:detail', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 11:22:06', 1265476890672672808, NULL, NULL),
	(1264622039642256181, 1264622039642256111, '[0],[1264622039642255961],[1264622039642256111],', '字典类型下拉', 'sys_dict_mgr_dict_type_drop_down', 2, NULL, NULL, NULL, 'sysDictType:dropDown', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 11:22:23', 1265476890672672808, NULL, NULL),
	(1264622039642256191, 1264622039642256111, '[0],[1264622039642255961],[1264622039642256111],', '字典类型修改状态', 'sys_dict_mgr_dict_type_change_status', 2, NULL, NULL, NULL, 'sysDictType:changeStatus', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-23 11:15:50', 1265476890672672808, NULL, NULL),
	(1264622039642256201, 1264622039642256111, '[0],[1264622039642255961],[1264622039642256111],', '字典值查询', 'sys_dict_mgr_dict_page', 2, NULL, NULL, NULL, 'sysDictData:page', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 11:23:11', 1265476890672672808, NULL, NULL),
	(1264622039642256211, 1264622039642256111, '[0],[1264622039642255961],[1264622039642256111],', '字典值列表', 'sys_dict_mgr_dict_list', 2, NULL, NULL, NULL, 'sysDictData:list', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 11:24:58', 1265476890672672808, NULL, NULL),
	(1264622039642256221, 1264622039642256111, '[0],[1264622039642255961],[1264622039642256111],', '字典值增加', 'sys_dict_mgr_dict_add', 2, NULL, NULL, NULL, 'sysDictData:add', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 11:22:51', 1265476890672672808, NULL, NULL),
	(1264622039642256231, 1264622039642256111, '[0],[1264622039642255961],[1264622039642256111],', '字典值删除', 'sys_dict_mgr_dict_delete', 2, NULL, NULL, NULL, 'sysDictData:delete', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 11:23:26', 1265476890672672808, NULL, NULL),
	(1264622039642256241, 1264622039642256111, '[0],[1264622039642255961],[1264622039642256111],', '字典值编辑', 'sys_dict_mgr_dict_edit', 2, NULL, NULL, NULL, 'sysDictData:edit', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 11:24:21', 1265476890672672808, NULL, NULL),
	(1264622039642256251, 1264622039642256111, '[0],[1264622039642255961],[1264622039642256111],', '字典值详情', 'sys_dict_mgr_dict_detail', 2, NULL, NULL, NULL, 'sysDictData:detail', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-04-01 11:24:42', 1265476890672672808, NULL, NULL),
	(1264622039642256261, 1264622039642256111, '[0],[1264622039642255961],[1264622039642256111],', '字典值修改状态', 'sys_dict_mgr_dict_change_status', 2, NULL, NULL, NULL, 'sysDictData:changeStatus', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-23 11:17:53', 1265476890672672808, NULL, NULL),
	(1264622039642256271, 1264622039642255961, '[0],[1264622039642255961],', '接口文档', 'sys_swagger_mgr', 1, NULL, '/swagger', 'Iframe', NULL, 'system', 2, 'Y', 'http://localhost:82/doc.html', NULL, 1, 13, NULL, 0, '2020-07-02 12:16:56', 1265476890672672808, NULL, NULL),
	(1264622039642256281, 0, '[0],', '日志管理', 'sys_log_mgr', 0, 'read', '/log', 'PageView', NULL, 'system', 1, 'Y', NULL, NULL, 1, 5, NULL, 0, '2020-04-01 09:25:01', 1265476890672672808, NULL, NULL),
	(1264622039642256291, 1264622039642256281, '[0],[1264622039642256281],', '访问日志', 'sys_log_mgr_vis_log', 1, NULL, '/vislog', 'system/log/vislog/index', NULL, 'system', 0, 'Y', NULL, NULL, 1, 14, NULL, 0, '2020-04-01 09:26:40', 1265476890672672808, NULL, NULL),
	(1264622039642256301, 1264622039642256291, '[0],[1264622039642256281],[1264622039642256291],', '访问日志查询', 'sys_log_mgr_vis_log_page', 2, NULL, NULL, NULL, 'sysVisLog:page', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 09:55:51', 1265476890672672808, NULL, NULL),
	(1264622039642256311, 1264622039642256291, '[0],[1264622039642256281],[1264622039642256291],', '访问日志清空', 'sys_log_mgr_vis_log_delete', 2, NULL, NULL, NULL, 'sysVisLog:delete', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 09:56:57', 1265476890672672808, NULL, NULL),
	(1264622039642256321, 1264622039642256281, '[0],[1264622039642256281],', '操作日志', 'sys_log_mgr_op_log', 1, NULL, '/oplog', 'system/log/oplog/index', NULL, 'system', 0, 'Y', NULL, NULL, 1, 15, NULL, 0, '2020-04-01 09:26:59', 1265476890672672808, NULL, NULL),
	(1264622039642256331, 1264622039642256321, '[0],[1264622039642256281],[1264622039642256321],', '操作日志查询', 'sys_log_mgr_op_log_page', 2, NULL, NULL, NULL, 'sysOpLog:page', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 09:57:39', 1265476890672672808, NULL, NULL),
	(1264622039642256341, 1264622039642256321, '[0],[1264622039642256281],[1264622039642256321],', '操作日志清空', 'sys_log_mgr_op_log_delete', 2, NULL, NULL, NULL, 'sysOpLog:delete', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-02 09:58:13', 1265476890672672808, NULL, NULL),
	(1264622039642256351, 0, '[0],', '系统监控', 'sys_monitor_mgr', 0, 'deployment-unit', '/monitor', 'PageView', NULL, 'system', 1, 'Y', NULL, NULL, 1, 6, NULL, 0, '2020-06-05 16:00:50', 1265476890672672808, NULL, NULL),
	(1264622039642256361, 1264622039642256351, '[0],[1264622039642256351],', '服务监控', 'sys_monitor_mgr_machine_monitor', 1, NULL, '/machine', 'system/machine/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 16, NULL, 0, '2020-06-05 16:02:38', 1265476890672672808, NULL, NULL),
	(1264622039642256371, 1264622039642256361, '[0],[1264622039642256351],[1264622039642256361],', '服务监控查询', 'sys_monitor_mgr_machine_monitor_query', 2, NULL, NULL, NULL, 'sysMachine:query', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-05 16:05:33', 1265476890672672808, NULL, NULL),
	(1264622039642256381, 1264622039642256351, '[0],[1264622039642256351],', '在线用户', 'sys_monitor_mgr_online_user', 1, NULL, '/onlineUser', 'system/onlineUser/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 17, NULL, 0, '2020-06-05 16:01:55', 1265476890672672808, NULL, NULL),
	(1264622039642256391, 1264622039642256381, '[0],[1264622039642256351],[1264622039642256381],', '在线用户列表', 'sys_monitor_mgr_online_user_list', 2, NULL, NULL, NULL, 'sysOnlineUser:list', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-05 16:03:46', 1265476890672672808, NULL, NULL),
	(1264622039642256401, 1264622039642256381, '[0],[1264622039642256351],[1264622039642256381],', '在线用户强退', 'sys_monitor_mgr_online_user_force_exist', 2, NULL, NULL, NULL, 'sysOnlineUser:forceExist', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-05 16:04:16', 1265476890672672808, NULL, NULL),
	(1264622039642256411, 1264622039642256351, '[0],[1264622039642256351],', '数据监控', 'sys_monitor_mgr_druid', 1, NULL, '/druid', 'Iframe', NULL, 'system', 2, 'Y', 'http://localhost:82/druid', NULL, 1, 18, NULL, 0, '2020-06-28 16:15:07', 1265476890672672808, '2020-09-13 09:39:10', 1265476890672672808),
	(1264622039642256421, 0, '[0],', '通知公告', 'sys_notice', 0, 'sound', '/notice', 'PageView', NULL, 'system', 1, 'Y', NULL, NULL, 1, 7, NULL, 0, '2020-06-29 15:41:53', 1265476890672672808, NULL, NULL),
	(1264622039642256431, 1264622039642256421, '[0],[1264622039642256421],', '公告管理', 'sys_notice_mgr', 1, NULL, '/notice', 'system/notice/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 19, NULL, 0, '2020-06-29 15:44:24', 1265476890672672808, NULL, NULL),
	(1264622039642256441, 1264622039642256431, '[0],[1264622039642256421],[1264622039642256431],', '公告查询', 'sys_notice_mgr_page', 2, NULL, NULL, NULL, 'sysNotice:page', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-29 15:45:30', 1265476890672672808, NULL, NULL),
	(1264622039642256451, 1264622039642256431, '[0],[1264622039642256421],[1264622039642256431],', '公告增加', 'sys_notice_mgr_add', 2, NULL, NULL, NULL, 'sysNotice:add', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-29 15:45:57', 1265476890672672808, NULL, NULL),
	(1264622039642256461, 1264622039642256431, '[0],[1264622039642256421],[1264622039642256431],', '公告编辑', 'sys_notice_mgr_edit', 2, NULL, NULL, NULL, 'sysNotice:edit', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-29 15:46:22', 1265476890672672808, NULL, NULL),
	(1264622039642256471, 1264622039642256431, '[0],[1264622039642256421],[1264622039642256431],', '公告删除', 'sys_notice_mgr_delete', 2, NULL, NULL, NULL, 'sysNotice:delete', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-29 15:46:11', 1265476890672672808, NULL, NULL),
	(1264622039642256481, 1264622039642256431, '[0],[1264622039642256421],[1264622039642256431],', '公告查看', 'sys_notice_mgr_detail', 2, NULL, NULL, NULL, 'sysNotice:detail', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-29 15:46:33', 1265476890672672808, NULL, NULL),
	(1264622039642256491, 1264622039642256431, '[0],[1264622039642256421],[1264622039642256431],', '公告修改状态', 'sys_notice_mgr_changeStatus', 2, NULL, NULL, NULL, 'sysNotice:changeStatus', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-29 15:46:50', 1265476890672672808, NULL, NULL),
	(1264622039642256501, 1264622039642256421, '[0],[1264622039642256421],', '已收公告', 'sys_notice_mgr_received', 1, NULL, '/noticeReceived', 'system/noticeReceived/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 20, NULL, 0, '2020-06-29 16:32:53', 1265476890672672808, NULL, NULL),
	(1264622039642256511, 1264622039642256501, '[0],[1264622039642256421],[1264622039642256501],', '已收公告查询', 'sys_notice_mgr_received_page', 2, NULL, NULL, NULL, 'sysNotice:received', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-29 16:33:43', 1265476890672672808, NULL, NULL),
	(1264622039642256521, 0, '[0],', '文件管理', 'sys_file_mgr', 0, 'file', '/file', 'PageView', NULL, 'system', 1, 'Y', NULL, NULL, 1, 8, NULL, 0, '2020-06-24 17:31:10', 1265476890672672808, NULL, NULL),
	(1264622039642256531, 1264622039642256521, '[0],[1264622039642256521],', '系统文件', 'sys_file_mgr_sys_file', 1, NULL, '/file', 'system/file/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 21, NULL, 0, '2020-06-24 17:32:57', 1265476890672672808, NULL, NULL),
	(1264622039642256541, 1264622039642256531, '[0],[1264622039642256521],[1264622039642256531],', '文件查询', 'sys_file_mgr_sys_file_page', 2, NULL, NULL, NULL, 'sysFileInfo:page', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-24 17:35:38', 1265476890672672808, NULL, NULL),
	(1264622039642256551, 1264622039642256531, '[0],[1264622039642256521],[1264622039642256531],', '文件列表', 'sys_file_mgr_sys_file_list', 2, NULL, NULL, NULL, 'sysFileInfo:list', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-24 17:35:49', 1265476890672672808, NULL, NULL),
	(1264622039642256561, 1264622039642256531, '[0],[1264622039642256521],[1264622039642256531],', '文件删除', 'sys_file_mgr_sys_file_delete', 2, NULL, NULL, NULL, 'sysFileInfo:delete', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-24 17:36:11', 1265476890672672808, NULL, NULL),
	(1264622039642256571, 1264622039642256531, '[0],[1264622039642256521],[1264622039642256531],', '文件详情', 'sys_file_mgr_sys_file_detail', 2, NULL, NULL, NULL, 'sysFileInfo:detail', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-24 17:36:01', 1265476890672672808, NULL, NULL),
	(1264622039642256581, 1264622039642256531, '[0],[1264622039642256521],[1264622039642256531],', '文件上传', 'sys_file_mgr_sys_file_upload', 2, NULL, NULL, NULL, 'sysFileInfo:upload', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-24 17:34:29', 1265476890672672808, NULL, NULL),
	(1264622039642256591, 1264622039642256531, '[0],[1264622039642256521],[1264622039642256531],', '文件下载', 'sys_file_mgr_sys_file_download', 2, NULL, NULL, NULL, 'sysFileInfo:download', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-24 17:34:55', 1265476890672672808, NULL, NULL),
	(1264622039642256601, 1264622039642256531, '[0],[1264622039642256521],[1264622039642256531],', '图片预览', 'sys_file_mgr_sys_file_preview', 2, NULL, NULL, NULL, 'sysFileInfo:preview', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-06-24 17:35:19', 1265476890672672808, NULL, NULL),
	(1264622039642256611, 0, '[0],', '定时任务', 'sys_timers', 0, 'dashboard', '/timers', 'PageView', NULL, 'system', 1, 'Y', NULL, NULL, 1, 9, NULL, 0, '2020-07-01 17:17:20', 1265476890672672808, '2022-06-20 12:07:57', 1265476890672672808),
	(1264622039642256621, 1264622039642256611, '[0],[1264622039642256611],', '任务管理', 'sys_timers_mgr', 1, NULL, '/timers', 'system/timers/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 22, NULL, 0, '2020-07-01 17:18:53', 1265476890672672808, NULL, NULL),
	(1264622039642256631, 1264622039642256621, '[0],[1264622039642256611],[1264622039642256621],', '定时任务查询', 'sys_timers_mgr_page', 2, NULL, NULL, NULL, 'sysTimers:page', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-01 17:19:43', 1265476890672672808, NULL, NULL),
	(1264622039642256641, 1264622039642256621, '[0],[1264622039642256611],[1264622039642256621],', '定时任务列表', 'sys_timers_mgr_list', 2, NULL, NULL, NULL, 'sysTimers:list', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-01 17:19:56', 1265476890672672808, NULL, NULL),
	(1264622039642256651, 1264622039642256621, '[0],[1264622039642256611],[1264622039642256621],', '定时任务详情', 'sys_timers_mgr_detail', 2, NULL, NULL, NULL, 'sysTimers:detail', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-01 17:20:10', 1265476890672672808, NULL, NULL),
	(1264622039642256661, 1264622039642256621, '[0],[1264622039642256611],[1264622039642256621],', '定时任务增加', 'sys_timers_mgr_add', 2, NULL, NULL, NULL, 'sysTimers:add', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-01 17:20:23', 1265476890672672808, NULL, NULL),
	(1264622039642256671, 1264622039642256621, '[0],[1264622039642256611],[1264622039642256621],', '定时任务删除', 'sys_timers_mgr_delete', 2, NULL, NULL, NULL, 'sysTimers:delete', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-01 17:20:33', 1265476890672672808, NULL, NULL),
	(1264622039642256681, 1264622039642256621, '[0],[1264622039642256611],[1264622039642256621],', '定时任务编辑', 'sys_timers_mgr_edit', 2, NULL, NULL, NULL, 'sysTimers:edit', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-01 17:20:43', 1265476890672672808, NULL, NULL),
	(1264622039642256691, 1264622039642256621, '[0],[1264622039642256611],[1264622039642256621],', '定时任务可执行列表', 'sys_timers_mgr_get_action_classes', 2, NULL, NULL, NULL, 'sysTimers:getActionClasses', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-01 17:22:16', 1265476890672672808, NULL, NULL),
	(1264622039642256701, 1264622039642256621, '[0],[1264622039642256611],[1264622039642256621],', '定时任务启动', 'sys_timers_mgr_start', 2, NULL, NULL, NULL, 'sysTimers:start', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-01 17:22:32', 1265476890672672808, NULL, NULL),
	(1264622039642256711, 1264622039642256621, '[0],[1264622039642256611],[1264622039642256621],', '定时任务关闭', 'sys_timers_mgr_stop', 2, NULL, NULL, NULL, 'sysTimers:stop', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2020-07-01 17:22:43', 1265476890672672808, NULL, NULL),
	(1264622039642256721, 0, '[0],', '区域管理', 'sys_area', 0, 'environment', '/area', 'PageView', NULL, 'system', 1, 'Y', NULL, NULL, 1, 11, NULL, 0, '2021-05-19 13:55:40', 1265476890672672808, '2022-06-20 12:09:31', 1265476890672672808),
	(1264622039642256731, 1264622039642256721, '[0],[1264622039642256721],', '系统区域', 'sys_area_mgr', 1, NULL, '/area', 'system/area/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 100, NULL, 0, '2021-05-19 13:57:42', 1265476890672672808, '2022-06-20 12:09:25', 1265476890672672808),
	(1264622039642256741, 1264622039642256731, '[0],[1264622039642256721],[1264622039642256731],', '系统区域列表', 'sys_area_mgr_list', 2, NULL, NULL, NULL, 'sysArea:list', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, '2021-05-19 14:01:39', 1265476890672672808, NULL, NULL),
	(1342445437296771074, 1264622039642255961, '[0],[1264622039642255961],', '代码生成', 'code_gen', 1, NULL, '/sysCodeGenerate/index', 'system/sysCodeGenerate/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 10, NULL, 0, '2020-12-25 20:21:48', 1265476890672672808, '2022-06-22 18:00:58', 1265476890672672808),
	(1410859007809736705, 1264622039642256521, '[0],[1264622039642256521],', '在线文档', 'file_oline', 1, NULL, '/file_oline', 'system/fileOnline/index', '', 'system', 1, 'Y', NULL, '', 1, 100, NULL, 0, '2021-07-02 15:12:55', 1265476890672672808, '2021-08-25 20:02:46', 1265476890672672808),
	(1537693069254561793, 0, '[0],', '数据管理', 'board_metadata', 0, 'pic-left', '/board_metadata', 'PageView', '', 'system', 1, 'Y', NULL, '', 1, 3, NULL, 0, '2022-06-17 15:06:11', 1265476890672672808, '2022-06-22 18:04:08', 1265476890672672808),
	(1539187940297420802, 0, '[0],', '实时分析', 'board_analysis', 0, 'bar-chart', '/board_analysis', 'PageView', '', 'system', 1, 'Y', NULL, '', 1, 2, NULL, 0, '2022-06-21 18:06:16', 1265476890672672808, '2022-06-22 18:03:44', 1265476890672672808),
	(1539188385652813825, 1539187940297420802, '[0],[1539187940297420802],', '事件分析', 'evnent_analysis', 1, NULL, '/evnent_analysis', 'evnent_analysis', '', 'system', 1, 'Y', NULL, '', 1, 1, NULL, 0, '2022-06-21 18:08:02', 1265476890672672808, '2022-06-24 18:23:32', 1265476890672672808),
	(1539188990165266434, 1539187940297420802, '[0],[1539187940297420802],', '漏斗分析', 'funnel_analysis', 1, NULL, '/funnel_analysis', 'funnel_analysis', '', 'system', 1, 'Y', NULL, '', 1, 2, NULL, 0, '2022-06-21 18:10:26', 1265476890672672808, '2022-06-24 18:23:20', 1265476890672672808),
	(1539863115309432833, 1264622039642256351, '[0],[1264622039642256351],', '缓存管理', 'sys_cache_mgr', 1, NULL, '/cache', 'system/cache/index', '', 'system', 1, 'Y', NULL, '', 1, 100, NULL, 0, '2022-06-23 14:49:10', 1265476890672672808, NULL, NULL),
	(4747020348341702558, 6524320009635818047, '[0],[1537693069254561793],[6524320009635818047],', '数据源配置编辑', 'boardDatasource_index_edit', 2, NULL, NULL, NULL, 'boardDataSource:edit', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, NULL, NULL, '2022-06-17 15:35:27', 1265476890672672808),
	(4988880395349552904, 5053247396277601701, '[0],[1537693069254561793],[5053247396277601701],', '数据表配置导出', 'boardTable_index_export', 2, NULL, NULL, NULL, 'boardTable:export', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, NULL, NULL, '2022-06-20 11:26:15', 1265476890672672808),
	(4995513235449904549, 6524320009635818047, '[0],[1537693069254561793],[6524320009635818047],', '数据源配置导出', 'boardDatasource_index_export', 2, NULL, NULL, NULL, 'boardDataSource:export', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, NULL, NULL, '2022-06-17 15:35:32', 1265476890672672808),
	(5053247396277601701, 1537693069254561793, '[0],[1537693069254561793],', '数据表配置', 'boardTable_index', 1, NULL, '/boardTable', 'board/boardTable/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 2, NULL, 0, NULL, NULL, '2022-06-25 00:56:17', 1265476890672672808),
	(5200192712383772429, 6524320009635818047, '[0],[1537693069254561793],[6524320009635818047],', '数据源配置删除', 'boardDatasource_index_delete', 2, NULL, NULL, NULL, 'boardDataSource:delete', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, NULL, NULL, '2022-06-17 15:35:03', 1265476890672672808),
	(5203283721685123391, 5053247396277601701, '[0],[1537693069254561793],[5053247396277601701],', '数据表配置列表', 'boardTable_index_list', 2, NULL, NULL, NULL, 'boardTable:list', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, NULL, NULL, '2022-06-20 11:26:15', 1265476890672672808),
	(5655055115260975611, 6524320009635818047, '[0],[1537693069254561793],[6524320009635818047],', '数据源配置新增', 'boardDatasource_index_add', 2, NULL, NULL, NULL, 'boardDataSource:add', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, NULL, NULL, '2022-06-17 15:35:38', 1265476890672672808),
	(5666654336429911593, 6073322290452106986, '[0],[1537693069254561793],[6073322290452106986],', '属性配置查询', 'boardproperty_index_page', 2, NULL, NULL, NULL, 'boardProperty:page', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, NULL, NULL, '2022-06-20 12:07:35', 1265476890672672808),
	(5793876116800796479, 5053247396277601701, '[0],[1537693069254561793],[5053247396277601701],', '数据表配置删除', 'boardTable_index_delete', 2, NULL, NULL, NULL, 'boardTable:delete', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, NULL, NULL, '2022-06-20 11:26:15', 1265476890672672808),
	(6006143604125572910, 6524320009635818047, '[0],[1537693069254561793],[6524320009635818047],', '数据源配置查看', 'boardDatasource_index_detail', 2, NULL, NULL, NULL, 'boardDataSource:detail', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, NULL, NULL, '2022-06-17 15:35:14', 1265476890672672808),
	(6073322290452106986, 1537693069254561793, '[0],[1537693069254561793],', '属性配置', 'boardproperty_index', 1, NULL, '/boardProperty', 'board/boardProperty/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 5, NULL, 0, NULL, NULL, '2022-06-25 00:56:55', 1265476890672672808),
	(6472178080029648869, 6694311656187858919, '[0],[1537693069254561793],[6694311656187858919],', '元事件配置导出', 'boardEvent_index_export', 2, NULL, NULL, NULL, 'boardEvent:export', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, NULL, NULL, '2022-06-20 12:07:43', 1265476890672672808),
	(6524320009635818047, 1537693069254561793, '[0],[1537693069254561793],', '数据源配置', 'boardDatasource_index', 1, NULL, '/boardDataSource', 'board/boardDatasource/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 1, NULL, 0, NULL, NULL, '2022-06-25 00:55:59', 1265476890672672808),
	(6663073402820268833, 6524320009635818047, '[0],[1537693069254561793],[6524320009635818047],', '数据源配置查询', 'boardDatasource_index_page', 2, NULL, NULL, NULL, 'boardDataSource:page', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, NULL, NULL, '2022-06-17 15:35:09', 1265476890672672808),
	(6694311656187858919, 1537693069254561793, '[0],[1537693069254561793],', '元事件配置', 'boardEvent_index', 1, NULL, '/boardEvent', 'board/boardEvent/index', NULL, 'system', 1, 'Y', NULL, NULL, 1, 6, NULL, 0, NULL, NULL, '2022-06-25 00:58:10', 1265476890672672808),
	(7211086211602463135, 6694311656187858919, '[0],[1537693069254561793],[6694311656187858919],', '元事件配置编辑', 'boardEvent_index_edit', 2, NULL, NULL, NULL, 'boardEvent:edit', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, NULL, NULL, '2022-06-20 12:07:43', 1265476890672672808),
	(7216605245852275096, 6073322290452106986, '[0],[1537693069254561793],[6073322290452106986],', '属性配置列表', 'boardproperty_index_list', 2, NULL, NULL, NULL, 'boardProperty:list', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, NULL, NULL, '2022-06-20 12:07:35', 1265476890672672808),
	(7349757853101670456, 5053247396277601701, '[0],[1537693069254561793],[5053247396277601701],', '数据表配置查看', 'boardTable_index_detail', 2, NULL, NULL, NULL, 'boardTable:detail', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, NULL, NULL, '2022-06-20 11:26:15', 1265476890672672808),
	(7794104508022256665, 5053247396277601701, '[0],[1537693069254561793],[5053247396277601701],', '数据表配置新增', 'boardTable_index_add', 2, NULL, NULL, NULL, 'boardTable:add', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, NULL, NULL, '2022-06-20 11:26:15', 1265476890672672808),
	(7898333986559118153, 6073322290452106986, '[0],[1537693069254561793],[6073322290452106986],', '属性配置导出', 'boardproperty_index_export', 2, NULL, NULL, NULL, 'boardProperty:export', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, NULL, NULL, '2022-06-20 12:07:35', 1265476890672672808),
	(7965521868947401234, 6073322290452106986, '[0],[1537693069254561793],[6073322290452106986],', '属性配置查看', 'boardproperty_index_detail', 2, NULL, NULL, NULL, 'boardProperty:detail', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, NULL, NULL, '2022-06-20 12:07:35', 1265476890672672808),
	(8084802963647754758, 6073322290452106986, '[0],[1537693069254561793],[6073322290452106986],', '属性配置编辑', 'boardproperty_index_edit', 2, NULL, NULL, NULL, 'boardProperty:edit', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, NULL, NULL, '2022-06-20 12:07:35', 1265476890672672808),
	(8340380182708678278, 6524320009635818047, '[0],[1537693069254561793],[6524320009635818047],', '数据源配置列表', 'boardDatasource_index_list', 2, NULL, NULL, NULL, 'boardDataSource:list', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, NULL, NULL, '2022-06-17 15:35:21', 1265476890672672808),
	(8374229387129070322, 6694311656187858919, '[0],[1537693069254561793],[6694311656187858919],', '元事件配置查询', 'boardEvent_index_page', 2, NULL, NULL, NULL, 'boardEvent:page', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, NULL, NULL, '2022-06-20 12:07:43', 1265476890672672808),
	(8701495433161575054, 6073322290452106986, '[0],[1537693069254561793],[6073322290452106986],', '属性配置新增', 'boardproperty_index_add', 2, NULL, NULL, NULL, 'boardProperty:add', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, NULL, NULL, '2022-06-20 12:07:35', 1265476890672672808),
	(8730195498123373797, 6694311656187858919, '[0],[1537693069254561793],[6694311656187858919],', '元事件配置列表', 'boardEvent_index_list', 2, NULL, NULL, NULL, 'boardEvent:list', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, NULL, NULL, '2022-06-20 12:07:43', 1265476890672672808),
	(8732275111633295394, 6694311656187858919, '[0],[1537693069254561793],[6694311656187858919],', '元事件配置删除', 'boardEvent_index_delete', 2, NULL, NULL, NULL, 'boardEvent:delete', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, NULL, NULL, '2022-06-20 12:07:43', 1265476890672672808),
	(8756553564016389873, 6694311656187858919, '[0],[1537693069254561793],[6694311656187858919],', '元事件配置新增', 'boardEvent_index_add', 2, NULL, NULL, NULL, 'boardEvent:add', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, NULL, NULL, '2022-06-20 12:07:43', 1265476890672672808),
	(8770509580005161942, 6694311656187858919, '[0],[1537693069254561793],[6694311656187858919],', '元事件配置查看', 'boardEvent_index_detail', 2, NULL, NULL, NULL, 'boardEvent:detail', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, NULL, NULL, '2022-06-20 12:07:43', 1265476890672672808),
	(8858786329224373136, 5053247396277601701, '[0],[1537693069254561793],[5053247396277601701],', '数据表配置查询', 'boardTable_index_page', 2, NULL, NULL, NULL, 'boardTable:page', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, NULL, NULL, '2022-06-20 11:26:15', 1265476890672672808),
	(8904191812964444523, 5053247396277601701, '[0],[1537693069254561793],[5053247396277601701],', '数据表配置编辑', 'boardTable_index_edit', 2, NULL, NULL, NULL, 'boardTable:edit', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, NULL, NULL, '2022-06-20 11:26:15', 1265476890672672808),
	(9116501195565509393, 6073322290452106986, '[0],[1537693069254561793],[6073322290452106986],', '属性配置删除', 'boardproperty_index_delete', 2, NULL, NULL, NULL, 'boardProperty:delete', 'system', 0, 'Y', NULL, NULL, 1, 100, NULL, 0, NULL, NULL, '2022-06-20 12:07:35', 1265476890672672808);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;

-- Dumping structure for table young-board.sys_notice
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE IF NOT EXISTS `sys_notice` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `title` varchar(1000) DEFAULT NULL COMMENT '标题',
  `content` text DEFAULT NULL COMMENT '内容',
  `type` tinyint(4) NOT NULL COMMENT '类型（字典 1通知 2公告）',
  `public_user_id` bigint(20) NOT NULL COMMENT '发布人id',
  `public_user_name` varchar(100) NOT NULL COMMENT '发布人姓名',
  `public_org_id` bigint(20) DEFAULT NULL COMMENT '发布机构id',
  `public_org_name` varchar(50) DEFAULT NULL COMMENT '发布机构名称',
  `public_time` datetime DEFAULT NULL COMMENT '发布时间',
  `cancel_time` datetime DEFAULT NULL COMMENT '撤回时间',
  `status` tinyint(4) NOT NULL COMMENT '状态（字典 0草稿 1发布 2撤回 3删除）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='通知表';

-- Dumping data for table young-board.sys_notice: ~0 rows (approximately)
DELETE FROM `sys_notice`;
/*!40000 ALTER TABLE `sys_notice` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_notice` ENABLE KEYS */;

-- Dumping structure for table young-board.sys_notice_user
DROP TABLE IF EXISTS `sys_notice_user`;
CREATE TABLE IF NOT EXISTS `sys_notice_user` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `notice_id` bigint(20) NOT NULL COMMENT '通知公告id',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `status` tinyint(4) NOT NULL COMMENT '状态（字典 0未读 1已读）',
  `read_time` datetime DEFAULT NULL COMMENT '阅读时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统用户数据范围表';

-- Dumping data for table young-board.sys_notice_user: ~0 rows (approximately)
DELETE FROM `sys_notice_user`;
/*!40000 ALTER TABLE `sys_notice_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_notice_user` ENABLE KEYS */;

-- Dumping structure for table young-board.sys_oauth_user
DROP TABLE IF EXISTS `sys_oauth_user`;
CREATE TABLE IF NOT EXISTS `sys_oauth_user` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `uuid` varchar(255) NOT NULL COMMENT '第三方平台的用户唯一id',
  `access_token` varchar(255) DEFAULT NULL COMMENT '用户授权的token',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(500) DEFAULT NULL COMMENT '头像',
  `blog` varchar(255) DEFAULT NULL COMMENT '用户网址',
  `company` varchar(255) DEFAULT NULL COMMENT '所在公司',
  `location` varchar(255) DEFAULT NULL COMMENT '位置',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `gender` varchar(50) DEFAULT NULL COMMENT '性别',
  `source` varchar(255) DEFAULT NULL COMMENT '用户来源',
  `remark` varchar(255) DEFAULT NULL COMMENT '用户备注（各平台中的用户个人介绍）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建用户',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='第三方认证用户信息表';

-- Dumping data for table young-board.sys_oauth_user: ~0 rows (approximately)
DELETE FROM `sys_oauth_user`;
/*!40000 ALTER TABLE `sys_oauth_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_oauth_user` ENABLE KEYS */;

-- Dumping structure for table young-board.sys_op_log
DROP TABLE IF EXISTS `sys_op_log`;
CREATE TABLE IF NOT EXISTS `sys_op_log` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `op_type` tinyint(4) DEFAULT NULL COMMENT '操作类型',
  `success` char(1) DEFAULT NULL COMMENT '是否执行成功（Y-是，N-否）',
  `message` text DEFAULT NULL COMMENT '具体消息',
  `ip` varchar(255) DEFAULT NULL COMMENT 'ip',
  `location` varchar(255) DEFAULT NULL COMMENT '地址',
  `browser` varchar(255) DEFAULT NULL COMMENT '浏览器',
  `os` varchar(255) DEFAULT NULL COMMENT '操作系统',
  `url` varchar(500) DEFAULT NULL COMMENT '请求地址',
  `class_name` varchar(500) DEFAULT NULL COMMENT '类名称',
  `method_name` varchar(500) DEFAULT NULL COMMENT '方法名称',
  `req_method` varchar(255) DEFAULT NULL COMMENT '请求方式（GET POST PUT DELETE)',
  `param` text DEFAULT NULL COMMENT '请求参数',
  `result` longtext DEFAULT NULL COMMENT '返回结果',
  `op_time` datetime DEFAULT NULL COMMENT '操作时间',
  `account` varchar(50) DEFAULT NULL COMMENT '操作账号',
  `sign_value` varchar(500) DEFAULT NULL COMMENT '签名数据（除ID外）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统操作日志表';

-- Dumping data for table young-board.sys_op_log: ~0 rows (approximately)
DELETE FROM `sys_op_log`;
/*!40000 ALTER TABLE `sys_op_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_op_log` ENABLE KEYS */;

-- Dumping structure for table young-board.sys_org
DROP TABLE IF EXISTS `sys_org`;
CREATE TABLE IF NOT EXISTS `sys_org` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `pid` bigint(20) NOT NULL COMMENT '父id',
  `pids` text NOT NULL COMMENT '父ids',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `code` varchar(50) NOT NULL COMMENT '编码',
  `sort` int(11) NOT NULL COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '描述',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态（字典 0正常 1停用 2删除）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统组织机构表';

-- Dumping data for table young-board.sys_org: ~0 rows (approximately)
DELETE FROM `sys_org`;
/*!40000 ALTER TABLE `sys_org` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_org` ENABLE KEYS */;

-- Dumping structure for table young-board.sys_pos
DROP TABLE IF EXISTS `sys_pos`;
CREATE TABLE IF NOT EXISTS `sys_pos` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `code` varchar(50) NOT NULL COMMENT '编码',
  `sort` int(11) NOT NULL COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态（字典 0正常 1停用 2删除）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `CODE_UNI` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统职位表';

-- Dumping data for table young-board.sys_pos: ~0 rows (approximately)
DELETE FROM `sys_pos`;
/*!40000 ALTER TABLE `sys_pos` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_pos` ENABLE KEYS */;

-- Dumping structure for table young-board.sys_role
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE IF NOT EXISTS `sys_role` (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `code` varchar(50) NOT NULL COMMENT '编码',
  `sort` int(11) NOT NULL COMMENT '序号',
  `data_scope_type` tinyint(4) NOT NULL DEFAULT 1 COMMENT '数据范围类型（字典 1全部数据 2本部门及以下数据 3本部门数据 4仅本人数据 5自定义数据）',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态（字典 0正常 1停用 2删除）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统角色表';

-- Dumping data for table young-board.sys_role: ~0 rows (approximately)
DELETE FROM `sys_role`;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;

-- Dumping structure for table young-board.sys_role_data_scope
DROP TABLE IF EXISTS `sys_role_data_scope`;
CREATE TABLE IF NOT EXISTS `sys_role_data_scope` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `org_id` bigint(20) NOT NULL COMMENT '机构id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统角色数据范围表';

-- Dumping data for table young-board.sys_role_data_scope: ~0 rows (approximately)
DELETE FROM `sys_role_data_scope`;
/*!40000 ALTER TABLE `sys_role_data_scope` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role_data_scope` ENABLE KEYS */;

-- Dumping structure for table young-board.sys_role_menu
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE IF NOT EXISTS `sys_role_menu` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统角色菜单表';

-- Dumping data for table young-board.sys_role_menu: ~0 rows (approximately)
DELETE FROM `sys_role_menu`;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;

-- Dumping structure for table young-board.sys_sms
DROP TABLE IF EXISTS `sys_sms`;
CREATE TABLE IF NOT EXISTS `sys_sms` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `phone_numbers` varchar(200) NOT NULL COMMENT '手机号',
  `validate_code` varchar(255) DEFAULT NULL COMMENT '短信验证码',
  `template_code` varchar(255) DEFAULT NULL COMMENT '短信模板ID',
  `biz_id` varchar(255) DEFAULT NULL COMMENT '回执id，可根据该id查询具体的发送状态',
  `status` tinyint(4) NOT NULL COMMENT '发送状态（字典 0 未发送，1 发送成功，2 发送失败，3 失效）',
  `source` tinyint(4) NOT NULL COMMENT '来源（字典 1 app， 2 pc， 3 其他）',
  `invalid_time` datetime DEFAULT NULL COMMENT '失效时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='短信信息发送表';

-- Dumping data for table young-board.sys_sms: ~0 rows (approximately)
DELETE FROM `sys_sms`;
/*!40000 ALTER TABLE `sys_sms` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_sms` ENABLE KEYS */;

-- Dumping structure for table young-board.sys_timers
DROP TABLE IF EXISTS `sys_timers`;
CREATE TABLE IF NOT EXISTS `sys_timers` (
  `id` bigint(20) NOT NULL COMMENT '定时器id',
  `timer_name` varchar(255) DEFAULT '' COMMENT '任务名称',
  `action_class` varchar(255) DEFAULT NULL COMMENT '执行任务的class的类名（实现了TimerTaskRunner接口的类的全称）',
  `cron` varchar(255) DEFAULT '' COMMENT '定时任务表达式',
  `job_status` tinyint(4) DEFAULT 0 COMMENT '状态（字典 1运行  2停止）',
  `param` text DEFAULT NULL,
  `remark` varchar(1000) DEFAULT '' COMMENT '备注信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='定时任务';

-- Dumping data for table young-board.sys_timers: ~1 rows (approximately)
DELETE FROM `sys_timers`;
/*!40000 ALTER TABLE `sys_timers` DISABLE KEYS */;
INSERT INTO `sys_timers` (`id`, `timer_name`, `action_class`, `cron`, `job_status`, `param`, `remark`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES
	(1288760324837851137, '定时同步缓存常量', 'com.zhisida.tasks.RefreshConstantsTaskRunner', '0 0/1 * * * ?', 2, NULL, '定时同步sys_config表的数据到缓存常量中', '2020-07-30 16:56:20', 1265476890672672808, '2022-06-17 11:09:22', 1265476890672672808);
/*!40000 ALTER TABLE `sys_timers` ENABLE KEYS */;

-- Dumping structure for table young-board.sys_timers_log
DROP TABLE IF EXISTS `sys_timers_log`;
CREATE TABLE IF NOT EXISTS `sys_timers_log` (
  `ID` bigint(64) NOT NULL COMMENT '主键ID',
  `TIMER_ID` bigint(64) DEFAULT NULL COMMENT '任务编号',
  `EXECUTE_START_TIME` datetime DEFAULT NULL COMMENT '执行开始时间',
  `EXECUTE_END_TIME` datetime DEFAULT NULL COMMENT '执行结束时间',
  `EXECUTE_CODE` varchar(128) DEFAULT NULL COMMENT '执行结果',
  `EXECUTE_MSG` text DEFAULT NULL COMMENT '执行信息',
  `EXECUTE_PARAM` text DEFAULT NULL COMMENT '执行参数',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务日志表';

-- Dumping data for table young-board.sys_timers_log: ~0 rows (approximately)
DELETE FROM `sys_timers_log`;
/*!40000 ALTER TABLE `sys_timers_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_timers_log` ENABLE KEYS */;

-- Dumping structure for table young-board.sys_user
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `account` varchar(50) NOT NULL COMMENT '账号',
  `pwd_hash_value` varchar(100) NOT NULL COMMENT '密码',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '昵称',
  `name` varchar(100) NOT NULL COMMENT '姓名',
  `avatar` bigint(20) DEFAULT NULL COMMENT '头像',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `sex` tinyint(4) NOT NULL COMMENT '性别(字典 1男 2女 3未知)',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(500) DEFAULT NULL COMMENT '手机',
  `tel` varchar(50) DEFAULT NULL COMMENT '电话',
  `last_login_ip` varchar(100) DEFAULT NULL COMMENT '最后登陆IP',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `admin_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '管理员类型（0超级管理员 1非管理员）',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态（字典 0正常 1冻结 2删除）',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(20) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统用户表';

-- Dumping data for table young-board.sys_user: ~0 rows (approximately)
DELETE FROM `sys_user`;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` (`id`, `account`, `pwd_hash_value`, `nick_name`, `name`, `avatar`, `birthday`, `sex`, `email`, `phone`, `tel`, `last_login_ip`, `last_login_time`, `admin_type`, `status`, `create_time`, `create_user`, `update_time`, `update_user`) VALUES
	(1265476890672672808, 'admin', '207cf410532f92a47dee245ce9b11ff71f578ebd763eb3bbea44ebd043d018fb', '超级管理员', '超级管理员', NULL, '2020-03-18', 1, 'superAdmin@qq.com', '001757f43bd02871093cd7cbfed021f5', '1234567890', '127.0.0.1', '2022-06-25 15:58:50', 1, 0, '2020-05-29 16:39:28', -1, '2022-06-25 15:58:50', -1);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;

-- Dumping structure for table young-board.sys_user_data_scope
DROP TABLE IF EXISTS `sys_user_data_scope`;
CREATE TABLE IF NOT EXISTS `sys_user_data_scope` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `org_id` bigint(20) NOT NULL COMMENT '机构id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统用户数据范围表';

-- Dumping data for table young-board.sys_user_data_scope: ~0 rows (approximately)
DELETE FROM `sys_user_data_scope`;
/*!40000 ALTER TABLE `sys_user_data_scope` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_user_data_scope` ENABLE KEYS */;

-- Dumping structure for table young-board.sys_user_role
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE IF NOT EXISTS `sys_user_role` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统用户角色表';

-- Dumping data for table young-board.sys_user_role: ~0 rows (approximately)
DELETE FROM `sys_user_role`;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;

-- Dumping structure for table young-board.sys_vis_log
DROP TABLE IF EXISTS `sys_vis_log`;
CREATE TABLE IF NOT EXISTS `sys_vis_log` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(50) DEFAULT NULL COMMENT '名称',
  `success` char(1) DEFAULT NULL COMMENT '是否执行成功（Y-是，N-否）',
  `message` text DEFAULT NULL COMMENT '具体消息',
  `ip` varchar(255) DEFAULT NULL COMMENT 'ip',
  `location` varchar(255) DEFAULT NULL COMMENT '地址',
  `browser` varchar(255) DEFAULT NULL COMMENT '浏览器',
  `os` varchar(255) DEFAULT NULL COMMENT '操作系统',
  `vis_type` tinyint(4) NOT NULL COMMENT '操作类型（字典 1登入 2登出）',
  `vis_time` datetime DEFAULT NULL COMMENT '访问时间',
  `account` varchar(50) DEFAULT NULL COMMENT '访问账号',
  `sign_value` varchar(500) DEFAULT NULL COMMENT '签名数据（除ID外）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='系统访问日志表';

-- Dumping data for table young-board.sys_vis_log: ~0 rows (approximately)
DELETE FROM `sys_vis_log`;
/*!40000 ALTER TABLE `sys_vis_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_vis_log` ENABLE KEYS */;

-- Dumping structure for table young-board.tbl_board_analysis
DROP TABLE IF EXISTS `tbl_board_analysis`;
CREATE TABLE IF NOT EXISTS `tbl_board_analysis` (
  `ID` bigint(20) NOT NULL COMMENT '主键ID',
  `DISPLAY_NAME` varchar(256) NOT NULL COMMENT '展示名称',
  `TYPE` varchar(128) NOT NULL COMMENT '类型',
  `CHART_CONFIG` text DEFAULT NULL COMMENT '图标配置',
  `START_TIME` datetime DEFAULT NULL COMMENT '开始时间',
  `END_TIME` datetime DEFAULT NULL COMMENT '结束时间',
  `FILTER_LOGIC` varchar(20) DEFAULT NULL COMMENT '条件逻辑',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='实时分析表';

-- Dumping data for table young-board.tbl_board_analysis: ~0 rows (approximately)
DELETE FROM `tbl_board_analysis`;
/*!40000 ALTER TABLE `tbl_board_analysis` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_board_analysis` ENABLE KEYS */;

-- Dumping structure for table young-board.tbl_board_analysis_event
DROP TABLE IF EXISTS `tbl_board_analysis_event`;
CREATE TABLE IF NOT EXISTS `tbl_board_analysis_event` (
  `ID` bigint(20) NOT NULL COMMENT '主键ID',
  `ANALYSIS_ID` bigint(20) NOT NULL COMMENT '实时分析ID',
  `EVENT_ID` bigint(20) NOT NULL COMMENT '事件ID',
  `SORT` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='实时分析事件表';

-- Dumping data for table young-board.tbl_board_analysis_event: ~0 rows (approximately)
DELETE FROM `tbl_board_analysis_event`;
/*!40000 ALTER TABLE `tbl_board_analysis_event` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_board_analysis_event` ENABLE KEYS */;

-- Dumping structure for table young-board.tbl_board_analysis_event_filter
DROP TABLE IF EXISTS `tbl_board_analysis_event_filter`;
CREATE TABLE IF NOT EXISTS `tbl_board_analysis_event_filter` (
  `ID` bigint(20) NOT NULL COMMENT '主键ID',
  `PARENT_ID` bigint(20) DEFAULT NULL COMMENT '父级条件ID',
  `ANALYSIS_EVENT_ID` bigint(20) NOT NULL COMMENT '实时分析事件ID',
  `PROPERTY_ID` bigint(20) NOT NULL COMMENT '属性ID',
  `SUB_LOGIC` varchar(128) DEFAULT NULL COMMENT '子级条件逻辑',
  `MEASURE` varchar(128) DEFAULT NULL COMMENT '计算方式',
  `VALUE` text DEFAULT NULL COMMENT '条件值',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='实时分析事件条件表';

-- Dumping data for table young-board.tbl_board_analysis_event_filter: ~0 rows (approximately)
DELETE FROM `tbl_board_analysis_event_filter`;
/*!40000 ALTER TABLE `tbl_board_analysis_event_filter` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_board_analysis_event_filter` ENABLE KEYS */;

-- Dumping structure for table young-board.tbl_board_analysis_event_property
DROP TABLE IF EXISTS `tbl_board_analysis_event_property`;
CREATE TABLE IF NOT EXISTS `tbl_board_analysis_event_property` (
  `ID` bigint(20) NOT NULL COMMENT '主键ID',
  `ANALYSIS_EVENT_ID` bigint(20) NOT NULL COMMENT '实时分析ID',
  `property_Id` bigint(20) NOT NULL COMMENT '事件ID',
  `measure` varchar(11) DEFAULT NULL COMMENT '计算方式',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='实时分析事件计算属性表';

-- Dumping data for table young-board.tbl_board_analysis_event_property: ~0 rows (approximately)
DELETE FROM `tbl_board_analysis_event_property`;
/*!40000 ALTER TABLE `tbl_board_analysis_event_property` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_board_analysis_event_property` ENABLE KEYS */;

-- Dumping structure for table young-board.tbl_board_analysis_filter
DROP TABLE IF EXISTS `tbl_board_analysis_filter`;
CREATE TABLE IF NOT EXISTS `tbl_board_analysis_filter` (
  `ID` bigint(20) NOT NULL COMMENT '主键ID',
  `PARENT_ID` bigint(20) DEFAULT NULL COMMENT '父级条件ID',
  `ANALYSIS_ID` bigint(20) NOT NULL COMMENT '实时分析事件ID',
  `PROPERTY_ID` bigint(20) NOT NULL COMMENT '属性ID',
  `SUB_LOGIC` varchar(128) DEFAULT NULL COMMENT '子级条件逻辑',
  `MEASURE` varchar(128) DEFAULT NULL COMMENT '计算方式',
  `VALUE` text DEFAULT NULL COMMENT '条件值',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='实时分析条件表';

-- Dumping data for table young-board.tbl_board_analysis_filter: ~0 rows (approximately)
DELETE FROM `tbl_board_analysis_filter`;
/*!40000 ALTER TABLE `tbl_board_analysis_filter` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_board_analysis_filter` ENABLE KEYS */;

-- Dumping structure for table young-board.tbl_board_analysis_group
DROP TABLE IF EXISTS `tbl_board_analysis_group`;
CREATE TABLE IF NOT EXISTS `tbl_board_analysis_group` (
  `ID` bigint(20) NOT NULL COMMENT '主键ID',
  `PARENT_ID` bigint(20) DEFAULT NULL COMMENT '父级条件ID',
  `ANALYSIS_ID` bigint(20) NOT NULL COMMENT '实时分析ID',
  `PROPERTY_ID` bigint(20) NOT NULL COMMENT '属性ID',
  `unit` varchar(128) DEFAULT NULL COMMENT '分组单位',
  `unit_Type` text DEFAULT NULL COMMENT '分组单位类型',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='实时分析分组表';

-- Dumping data for table young-board.tbl_board_analysis_group: ~0 rows (approximately)
DELETE FROM `tbl_board_analysis_group`;
/*!40000 ALTER TABLE `tbl_board_analysis_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_board_analysis_group` ENABLE KEYS */;

-- Dumping structure for table young-board.tbl_board_dash
DROP TABLE IF EXISTS `tbl_board_dash`;
CREATE TABLE IF NOT EXISTS `tbl_board_dash` (
  `ID` bigint(20) NOT NULL COMMENT '主键ID',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='数据面板表';

-- Dumping data for table young-board.tbl_board_dash: ~0 rows (approximately)
DELETE FROM `tbl_board_dash`;
/*!40000 ALTER TABLE `tbl_board_dash` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_board_dash` ENABLE KEYS */;

-- Dumping structure for table young-board.tbl_board_dash_analysis
DROP TABLE IF EXISTS `tbl_board_dash_analysis`;
CREATE TABLE IF NOT EXISTS `tbl_board_dash_analysis` (
  `ID` bigint(20) NOT NULL COMMENT '主键ID',
  `ANALYSIS_ID` bigint(20) NOT NULL COMMENT '实时分析ID',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='数据面板实时分析表';

-- Dumping data for table young-board.tbl_board_dash_analysis: ~0 rows (approximately)
DELETE FROM `tbl_board_dash_analysis`;
/*!40000 ALTER TABLE `tbl_board_dash_analysis` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_board_dash_analysis` ENABLE KEYS */;

-- Dumping structure for table young-board.tbl_board_dash_group
DROP TABLE IF EXISTS `tbl_board_dash_group`;
CREATE TABLE IF NOT EXISTS `tbl_board_dash_group` (
  `ID` bigint(20) NOT NULL COMMENT '主键ID',
  `DISPLAY_NAME` varchar(256) NOT NULL COMMENT '分组描述',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='数据面板分组表';

-- Dumping data for table young-board.tbl_board_dash_group: ~0 rows (approximately)
DELETE FROM `tbl_board_dash_group`;
/*!40000 ALTER TABLE `tbl_board_dash_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_board_dash_group` ENABLE KEYS */;

-- Dumping structure for table young-board.tbl_board_data_source
DROP TABLE IF EXISTS `tbl_board_data_source`;
CREATE TABLE IF NOT EXISTS `tbl_board_data_source` (
  `ID` bigint(20) NOT NULL COMMENT '主键ID',
  `DISPLAY_NAME` varchar(256) DEFAULT NULL COMMENT '数据源名称',
  `CONFIG` text DEFAULT NULL COMMENT '数据库配置',
  `TYPE` varchar(128) DEFAULT NULL COMMENT '数据库类型',
  `REMARK` varchar(512) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='数据源配置表';

-- Dumping data for table young-board.tbl_board_data_source: ~1 rows (approximately)
DELETE FROM `tbl_board_data_source`;
/*!40000 ALTER TABLE `tbl_board_data_source` DISABLE KEYS */;
INSERT INTO `tbl_board_data_source` (`ID`, `DISPLAY_NAME`, `CONFIG`, `TYPE`, `REMARK`) VALUES
	(1540375348154290178, '基础数据库', '{"url":"jdbc:mysql://localhost:3306/young-board?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false&serverTimezone=CTT&nullCatalogMeansCurrent=true",             "username":"root",             "password":"123456"         }', 'JDBC', '1'),
	(1540521032094408705, '我的', '{}', 'FILE', NULL);
/*!40000 ALTER TABLE `tbl_board_data_source` ENABLE KEYS */;

-- Dumping structure for table young-board.tbl_board_event
DROP TABLE IF EXISTS `tbl_board_event`;
CREATE TABLE IF NOT EXISTS `tbl_board_event` (
  `ID` bigint(20) NOT NULL COMMENT '主键ID',
  `EVENT_GORUP_ID` varchar(256) NOT NULL COMMENT '事件分组',
  `DISPLAY_NAME` varchar(128) NOT NULL COMMENT '事件名称',
  `TABLE_COLUMN_ID` bigint(20) NOT NULL COMMENT '表字段ID',
  `MEASURE` varchar(128) NOT NULL COMMENT '计算方式',
  `VALUE` varchar(512) NOT NULL COMMENT '事件值',
  `VALUE_TYPE` varchar(512) NOT NULL COMMENT '事件值类型',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='元事件配置表';

-- Dumping data for table young-board.tbl_board_event: ~0 rows (approximately)
DELETE FROM `tbl_board_event`;
/*!40000 ALTER TABLE `tbl_board_event` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_board_event` ENABLE KEYS */;

-- Dumping structure for table young-board.tbl_board_event_group
DROP TABLE IF EXISTS `tbl_board_event_group`;
CREATE TABLE IF NOT EXISTS `tbl_board_event_group` (
  `ID` bigint(20) NOT NULL COMMENT '主键ID',
  `PID` bigint(20) DEFAULT NULL,
  `PIDS` text DEFAULT NULL,
  `DISPLAY_NAME` varchar(256) NOT NULL COMMENT '事件名称',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='元事件分组表';

-- Dumping data for table young-board.tbl_board_event_group: ~2 rows (approximately)
DELETE FROM `tbl_board_event_group`;
/*!40000 ALTER TABLE `tbl_board_event_group` DISABLE KEYS */;
INSERT INTO `tbl_board_event_group` (`ID`, `PID`, `PIDS`, `DISPLAY_NAME`) VALUES
	(1540523165199982593, 0, '[0],', '默认分组');
/*!40000 ALTER TABLE `tbl_board_event_group` ENABLE KEYS */;

-- Dumping structure for table young-board.tbl_board_event_property
DROP TABLE IF EXISTS `tbl_board_event_property`;
CREATE TABLE IF NOT EXISTS `tbl_board_event_property` (
  `ID` bigint(20) NOT NULL COMMENT '主键ID',
  `EVENT_ID` bigint(20) NOT NULL COMMENT '事件编号',
  `PROPERTY_ID` bigint(20) NOT NULL COMMENT '属性编号',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='元事件属性关联表';

-- Dumping data for table young-board.tbl_board_event_property: ~0 rows (approximately)
DELETE FROM `tbl_board_event_property`;
/*!40000 ALTER TABLE `tbl_board_event_property` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_board_event_property` ENABLE KEYS */;

-- Dumping structure for table young-board.tbl_board_indicator
DROP TABLE IF EXISTS `tbl_board_indicator`;
CREATE TABLE IF NOT EXISTS `tbl_board_indicator` (
  `ID` bigint(20) NOT NULL COMMENT '主键ID',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='指标数据';

-- Dumping data for table young-board.tbl_board_indicator: ~0 rows (approximately)
DELETE FROM `tbl_board_indicator`;
/*!40000 ALTER TABLE `tbl_board_indicator` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_board_indicator` ENABLE KEYS */;

-- Dumping structure for table young-board.tbl_board_property
DROP TABLE IF EXISTS `tbl_board_property`;
CREATE TABLE IF NOT EXISTS `tbl_board_property` (
  `ID` bigint(20) NOT NULL COMMENT '主键ID',
  `DISPLAY_NAME` varchar(128) DEFAULT NULL COMMENT '属性名称',
  `PROPERTY_GROUP_ID` bigint(20) DEFAULT NULL COMMENT '属性分组',
  `TABLE_COLUMN_ID` bigint(20) DEFAULT NULL COMMENT '表字段ID',
  `MEASURE` varchar(128) DEFAULT NULL COMMENT '计算方式',
  `VALUE` text DEFAULT NULL COMMENT '属性值',
  `VALUE_TYPE` varchar(128) DEFAULT NULL COMMENT '属性值类型',
  `UNIT` text DEFAULT NULL COMMENT '属性值',
  `UNIT_TYPE` varchar(128) DEFAULT NULL COMMENT '属性值类型',
  `IS_DEFAULT` varchar(128) DEFAULT NULL COMMENT '属性值类型',
  `REMARK` varchar(512) DEFAULT NULL COMMENT '属性值类型',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='属性配置表';

-- Dumping data for table young-board.tbl_board_property: ~0 rows (approximately)
DELETE FROM `tbl_board_property`;
/*!40000 ALTER TABLE `tbl_board_property` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_board_property` ENABLE KEYS */;

-- Dumping structure for table young-board.tbl_board_property_group
DROP TABLE IF EXISTS `tbl_board_property_group`;
CREATE TABLE IF NOT EXISTS `tbl_board_property_group` (
  `ID` bigint(20) NOT NULL COMMENT '主键ID',
  `PID` bigint(20) DEFAULT NULL,
  `PIDS` text DEFAULT NULL,
  `DISPLAY_NAME` varchar(256) DEFAULT NULL COMMENT '分组名称',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='属性分组表';

-- Dumping data for table young-board.tbl_board_property_group: ~1 rows (approximately)
DELETE FROM `tbl_board_property_group`;
/*!40000 ALTER TABLE `tbl_board_property_group` DISABLE KEYS */;
INSERT INTO `tbl_board_property_group` (`ID`, `PID`, `PIDS`, `DISPLAY_NAME`) VALUES
	(1540524362824769537, 0, '[0],', '默认分组');
/*!40000 ALTER TABLE `tbl_board_property_group` ENABLE KEYS */;

-- Dumping structure for table young-board.tbl_board_property_value
DROP TABLE IF EXISTS `tbl_board_property_value`;
CREATE TABLE IF NOT EXISTS `tbl_board_property_value` (
  `ID` bigint(20) NOT NULL COMMENT '主键ID',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='属性值表';

-- Dumping data for table young-board.tbl_board_property_value: ~0 rows (approximately)
DELETE FROM `tbl_board_property_value`;
/*!40000 ALTER TABLE `tbl_board_property_value` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_board_property_value` ENABLE KEYS */;

-- Dumping structure for table young-board.tbl_board_report
DROP TABLE IF EXISTS `tbl_board_report`;
CREATE TABLE IF NOT EXISTS `tbl_board_report` (
  `ID` bigint(20) NOT NULL COMMENT '主键ID',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='报表配置表';

-- Dumping data for table young-board.tbl_board_report: ~0 rows (approximately)
DELETE FROM `tbl_board_report`;
/*!40000 ALTER TABLE `tbl_board_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_board_report` ENABLE KEYS */;

-- Dumping structure for table young-board.tbl_board_report_indicator
DROP TABLE IF EXISTS `tbl_board_report_indicator`;
CREATE TABLE IF NOT EXISTS `tbl_board_report_indicator` (
  `ID` bigint(20) NOT NULL COMMENT '主键ID',
  `REPORT_ID` bigint(20) DEFAULT NULL COMMENT '报表ID',
  `INDICATOR_ID` bigint(20) DEFAULT NULL COMMENT '指标ID',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='报表指标表';

-- Dumping data for table young-board.tbl_board_report_indicator: ~0 rows (approximately)
DELETE FROM `tbl_board_report_indicator`;
/*!40000 ALTER TABLE `tbl_board_report_indicator` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_board_report_indicator` ENABLE KEYS */;

-- Dumping structure for table young-board.tbl_board_table
DROP TABLE IF EXISTS `tbl_board_table`;
CREATE TABLE IF NOT EXISTS `tbl_board_table` (
  `ID` bigint(20) NOT NULL COMMENT '主键ID',
  `DATA_SOURCE_ID` bigint(20) DEFAULT NULL COMMENT '数据源ID',
  `TABLE_NAME` varchar(256) DEFAULT NULL COMMENT '表名称',
  `DISPLAY_NAME` varchar(256) DEFAULT NULL COMMENT '展示名称',
  `REFRESH_TYPE` varchar(128) DEFAULT NULL COMMENT '刷新方式',
  `REMARK` varchar(512) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='数据表配置表';

-- Dumping data for table young-board.tbl_board_table: ~50 rows (approximately)
DELETE FROM `tbl_board_table`;
/*!40000 ALTER TABLE `tbl_board_table` DISABLE KEYS */;
INSERT INTO `tbl_board_table` (`ID`, `DATA_SOURCE_ID`, `TABLE_NAME`, `DISPLAY_NAME`, `REFRESH_TYPE`, `REMARK`) VALUES
	(1540526435444285441, 1540375348154290178, 'sys_app', '系统应用表', NULL, NULL),
	(1540526435477839883, 1540375348154290178, 'sys_area', '中国行政地区表', NULL, NULL),
	(1540526435540754434, 1540375348154290178, 'sys_code_generate', '代码生成基础配置', NULL, NULL),
	(1540526435574308876, 1540375348154290178, 'sys_code_generate_config', '代码生成详细配置', NULL, NULL),
	(1540526435616251927, 1540375348154290178, 'sys_config', '系统参数配置表', NULL, NULL),
	(1540526435687555073, 1540375348154290178, 'sys_dict_data', '系统字典值表', NULL, NULL),
	(1540526435721109516, 1540375348154290178, 'sys_dict_type', '系统字典类型表', NULL, NULL),
	(1540526435758858251, 1540375348154290178, 'sys_emp', '员工表', NULL, NULL),
	(1540526435758858256, 1540375348154290178, 'sys_emp_ext_org_pos', '员工附属机构岗位表', NULL, NULL),
	(1540526435758858261, 1540375348154290178, 'sys_emp_pos', '员工职位关联表', NULL, NULL),
	(1540526435825967108, 1540375348154290178, 'sys_file_info', '文件信息表', NULL, NULL),
	(1540526435893075969, 1540375348154290178, 'sys_menu', '系统菜单表', NULL, NULL),
	(1540526435964379137, 1540375348154290178, 'sys_notice', '通知表', NULL, NULL),
	(1540526436027293697, 1540375348154290178, 'sys_notice_user', '系统用户数据范围表', NULL, NULL),
	(1540526436065042433, 1540375348154290178, 'sys_oauth_user', '第三方认证用户信息表', NULL, NULL),
	(1540526436106985474, 1540375348154290178, 'sys_op_log', '系统操作日志表', NULL, NULL),
	(1540526436144734215, 1540375348154290178, 'sys_org', '系统组织机构表', NULL, NULL),
	(1540526436144734228, 1540375348154290178, 'sys_pos', '系统职位表', NULL, NULL),
	(1540526436241203202, 1540375348154290178, 'sys_role', '系统角色表', NULL, NULL),
	(1540526436241203214, 1540375348154290178, 'sys_role_data_scope', '系统角色数据范围表', NULL, NULL),
	(1540526436278951941, 1540375348154290178, 'sys_role_menu', '系统角色菜单表', NULL, NULL),
	(1540526436278951945, 1540375348154290178, 'sys_sms', '短信信息发送表', NULL, NULL),
	(1540526436316700682, 1540375348154290178, 'sys_timers', '定时任务', NULL, NULL),
	(1540526436316700694, 1540375348154290178, 'sys_timers_log', '定时任务日志表', NULL, NULL),
	(1540526436383809544, 1540375348154290178, 'sys_user', '系统用户表', NULL, NULL),
	(1540526436450918402, 1540375348154290178, 'sys_user_data_scope', '系统用户数据范围表', NULL, NULL),
	(1540526436450918406, 1540375348154290178, 'sys_user_role', '系统用户角色表', NULL, NULL),
	(1540526436450918410, 1540375348154290178, 'sys_vis_log', '系统访问日志表', NULL, NULL),
	(1540526436518027266, 1540375348154290178, 'tbl_board_analysis', '实时分析表', NULL, NULL),
	(1540526436518027274, 1540375348154290178, 'tbl_board_analysis_event', '实时分析事件表', NULL, NULL),
	(1540526436518027279, 1540375348154290178, 'tbl_board_analysis_event_filter', '实时分析事件条件表', NULL, NULL),
	(1540526436597719048, 1540375348154290178, 'tbl_board_analysis_event_property', '实时分析事件计算属性表', NULL, NULL),
	(1540526436631273477, 1540375348154290178, 'tbl_board_analysis_filter', '实时分析条件表', NULL, NULL),
	(1540526436631273485, 1540375348154290178, 'tbl_board_analysis_group', '实时分析分组表', NULL, NULL),
	(1540526436698382338, 1540375348154290178, 'tbl_board_dash', '数据面板表', NULL, NULL),
	(1540526436698382340, 1540375348154290178, 'tbl_board_dash_analysis', '数据面板实时分析表', NULL, NULL),
	(1540526436740325378, 1540375348154290178, 'tbl_board_dash_group', '数据面板分组表', NULL, NULL),
	(1540526436740325381, 1540375348154290178, 'tbl_board_data_source', '数据源配置表', NULL, NULL),
	(1540526436740325387, 1540375348154290178, 'tbl_board_event', '元事件配置表', NULL, NULL),
	(1540526436799045639, 1540375348154290178, 'tbl_board_event_group', '元事件分组表', NULL, NULL),
	(1540526436799045644, 1540375348154290178, 'tbl_board_event_property', '元事件属性关联表', NULL, NULL),
	(1540526436836794372, 1540375348154290178, 'tbl_board_indicator', '指标数据', NULL, NULL),
	(1540526436836794374, 1540375348154290178, 'tbl_board_property', '属性配置表', NULL, NULL),
	(1540526436874543113, 1540375348154290178, 'tbl_board_property_group', '属性分组表', NULL, NULL),
	(1540526436874543118, 1540375348154290178, 'tbl_board_property_value', '属性值表', NULL, NULL),
	(1540526436933263362, 1540375348154290178, 'tbl_board_report', '报表配置表', NULL, NULL),
	(1540526436933263364, 1540375348154290178, 'tbl_board_report_indicator', '报表指标表', NULL, NULL),
	(1540526436966817793, 1540375348154290178, 'tbl_board_table', '数据表配置表', NULL, NULL),
	(1540526436966817800, 1540375348154290178, 'tbl_board_table_column', '数据字段配置表', NULL, NULL),
	(1540526437000372233, 1540375348154290178, 'tbl_board_table_connect', '字段关联配置表', NULL, NULL);
/*!40000 ALTER TABLE `tbl_board_table` ENABLE KEYS */;

-- Dumping structure for table young-board.tbl_board_table_column
DROP TABLE IF EXISTS `tbl_board_table_column`;
CREATE TABLE IF NOT EXISTS `tbl_board_table_column` (
  `ID` bigint(20) NOT NULL COMMENT '主键ID',
  `TABLE_ID` bigint(20) NOT NULL COMMENT '数据表ID',
  `COLUMN_NAME` varchar(128) NOT NULL COMMENT '字段名称',
  `DISPLAY_NAME` varchar(256) DEFAULT NULL COMMENT '展示名称',
  `REFRESH_TYPE` varchar(128) DEFAULT NULL COMMENT '刷新方式',
  `COLUMN_TYPE` varchar(128) DEFAULT NULL COMMENT '字段类型',
  `DATA_TYPE` varchar(128) DEFAULT NULL COMMENT '数据类型',
  `REMARK` varchar(512) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='数据字段配置表';

-- Dumping data for table young-board.tbl_board_table_column: ~396 rows (approximately)
DELETE FROM `tbl_board_table_column`;
/*!40000 ALTER TABLE `tbl_board_table_column` DISABLE KEYS */;
INSERT INTO `tbl_board_table_column` (`ID`, `TABLE_ID`, `COLUMN_NAME`, `DISPLAY_NAME`, `REFRESH_TYPE`, `COLUMN_TYPE`, `DATA_TYPE`, `REMARK`) VALUES
	(1540526435477839884, 1540526435477839883, 'id', '', NULL, NULL, 'bigint', NULL),
	(1540526435477839885, 1540526435477839883, 'level_code', '层级', NULL, NULL, 'tinyint', NULL),
	(1540526435477839886, 1540526435477839883, 'parent_code', '父级行政代码', NULL, NULL, 'varchar', NULL),
	(1540526435477839887, 1540526435477839883, 'area_code', '行政代码', NULL, NULL, 'varchar', NULL),
	(1540526435477839888, 1540526435477839883, 'zip_code', '邮政编码', NULL, NULL, 'varchar', NULL),
	(1540526435477839889, 1540526435477839883, 'city_code', '区号', NULL, NULL, 'varchar', NULL),
	(1540526435477839890, 1540526435477839883, 'name', '名称', NULL, NULL, 'varchar', NULL),
	(1540526435477839891, 1540526435477839883, 'short_name', '简称', NULL, NULL, 'varchar', NULL),
	(1540526435477839892, 1540526435477839883, 'merger_name', '组合名', NULL, NULL, 'varchar', NULL),
	(1540526435477839893, 1540526435477839883, 'pinyin', '拼音', NULL, NULL, 'varchar', NULL),
	(1540526435477839894, 1540526435477839883, 'lng', '经度', NULL, NULL, 'decimal', NULL),
	(1540526435477839895, 1540526435477839883, 'lat', '纬度', NULL, NULL, 'decimal', NULL),
	(1540526435540754435, 1540526435540754434, 'id', '主键', NULL, NULL, 'bigint', NULL),
	(1540526435540754436, 1540526435540754434, 'author_name', '作者姓名', NULL, NULL, 'varchar', NULL),
	(1540526435540754437, 1540526435540754434, 'class_name', '类名', NULL, NULL, 'varchar', NULL),
	(1540526435540754438, 1540526435540754434, 'table_prefix', '是否移除表前缀', NULL, NULL, 'varchar', NULL),
	(1540526435574308865, 1540526435540754434, 'generate_type', '生成位置类型', NULL, NULL, 'varchar', NULL),
	(1540526435574308866, 1540526435540754434, 'table_name', '数据库表名', NULL, NULL, 'varchar', NULL),
	(1540526435574308867, 1540526435540754434, 'package_name', '包名称', NULL, NULL, 'varchar', NULL),
	(1540526435574308868, 1540526435540754434, 'bus_name', '业务名', NULL, NULL, 'varchar', NULL),
	(1540526435574308869, 1540526435540754434, 'table_comment', '功能名', NULL, NULL, 'varchar', NULL),
	(1540526435574308870, 1540526435540754434, 'app_code', '所属应用', NULL, NULL, 'varchar', NULL),
	(1540526435574308871, 1540526435540754434, 'menu_pid', '菜单上级', NULL, NULL, 'varchar', NULL),
	(1540526435574308872, 1540526435540754434, 'create_user', '创建人', NULL, NULL, 'bigint', NULL),
	(1540526435574308873, 1540526435540754434, 'create_time', '创建时间', NULL, NULL, 'datetime', NULL),
	(1540526435574308874, 1540526435540754434, 'update_user', '更新人', NULL, NULL, 'bigint', NULL),
	(1540526435574308875, 1540526435540754434, 'update_time', '更新时间', NULL, NULL, 'datetime', NULL),
	(1540526435616251905, 1540526435574308876, 'id', '主键', NULL, NULL, 'bigint', NULL),
	(1540526435616251906, 1540526435574308876, 'code_gen_id', '代码生成主表ID', NULL, NULL, 'bigint', NULL),
	(1540526435616251907, 1540526435574308876, 'column_name', '数据库字段名', NULL, NULL, 'varchar', NULL),
	(1540526435616251908, 1540526435574308876, 'java_name', 'java类字段名', NULL, NULL, 'varchar', NULL),
	(1540526435616251909, 1540526435574308876, 'data_type', '物理类型', NULL, NULL, 'varchar', NULL),
	(1540526435616251910, 1540526435574308876, 'column_comment', '字段描述', NULL, NULL, 'varchar', NULL),
	(1540526435616251911, 1540526435574308876, 'java_type', 'java类型', NULL, NULL, 'varchar', NULL),
	(1540526435616251912, 1540526435574308876, 'effect_type', '作用类型（字典）', NULL, NULL, 'varchar', NULL),
	(1540526435616251913, 1540526435574308876, 'dict_type_code', '字典code', NULL, NULL, 'varchar', NULL),
	(1540526435616251914, 1540526435574308876, 'whether_table', '列表展示', NULL, NULL, 'varchar', NULL),
	(1540526435616251915, 1540526435574308876, 'whether_add_update', '增改', NULL, NULL, 'varchar', NULL),
	(1540526435616251916, 1540526435574308876, 'whether_retract', '列表是否缩进（字典）', NULL, NULL, 'varchar', NULL),
	(1540526435616251917, 1540526435574308876, 'whether_required', '是否必填（字典）', NULL, NULL, 'varchar', NULL),
	(1540526435616251918, 1540526435574308876, 'query_whether', '是否是查询条件', NULL, NULL, 'varchar', NULL),
	(1540526435616251919, 1540526435574308876, 'query_type', '查询方式', NULL, NULL, 'varchar', NULL),
	(1540526435616251920, 1540526435574308876, 'column_key', '主键', NULL, NULL, 'varchar', NULL),
	(1540526435616251921, 1540526435574308876, 'column_key_name', '主外键名称', NULL, NULL, 'varchar', NULL),
	(1540526435616251922, 1540526435574308876, 'whether_common', '是否是通用字段', NULL, NULL, 'varchar', NULL),
	(1540526435616251923, 1540526435574308876, 'create_time', '创建时间', NULL, NULL, 'datetime', NULL),
	(1540526435616251924, 1540526435574308876, 'create_user', '创建人', NULL, NULL, 'bigint', NULL),
	(1540526435616251925, 1540526435574308876, 'update_time', '修改时间', NULL, NULL, 'datetime', NULL),
	(1540526435616251926, 1540526435574308876, 'update_user', '修改人', NULL, NULL, 'bigint', NULL),
	(1540526435616251928, 1540526435616251927, 'id', '主键', NULL, NULL, 'bigint', NULL),
	(1540526435616251929, 1540526435616251927, 'name', '名称', NULL, NULL, 'varchar', NULL),
	(1540526435616251930, 1540526435616251927, 'code', '编码', NULL, NULL, 'varchar', NULL),
	(1540526435616251931, 1540526435616251927, 'value', '值', NULL, NULL, 'varchar', NULL),
	(1540526435616251932, 1540526435616251927, 'sys_flag', '是否是系统参数（Y-是，N-否）', NULL, NULL, 'char', NULL),
	(1540526435616251933, 1540526435616251927, 'remark', '备注', NULL, NULL, 'varchar', NULL),
	(1540526435616251934, 1540526435616251927, 'status', '状态（字典 0正常 1停用 2删除）', NULL, NULL, 'tinyint', NULL),
	(1540526435616251935, 1540526435616251927, 'group_code', '常量所属分类的编码，来自于“常量的分类”字典', NULL, NULL, 'varchar', NULL),
	(1540526435616251936, 1540526435616251927, 'create_time', '创建时间', NULL, NULL, 'datetime', NULL),
	(1540526435616251937, 1540526435616251927, 'create_user', '创建人', NULL, NULL, 'bigint', NULL),
	(1540526435616251938, 1540526435616251927, 'update_time', '更新时间', NULL, NULL, 'datetime', NULL),
	(1540526435616251939, 1540526435616251927, 'update_user', '更新人', NULL, NULL, 'bigint', NULL),
	(1540526435687555074, 1540526435687555073, 'id', '主键', NULL, NULL, 'bigint', NULL),
	(1540526435721109506, 1540526435687555073, 'type_id', '字典类型id', NULL, NULL, 'bigint', NULL),
	(1540526435721109507, 1540526435687555073, 'value', '值', NULL, NULL, 'text', NULL),
	(1540526435721109508, 1540526435687555073, 'code', '编码', NULL, NULL, 'varchar', NULL),
	(1540526435721109509, 1540526435687555073, 'sort', '排序', NULL, NULL, 'int', NULL),
	(1540526435721109510, 1540526435687555073, 'remark', '备注', NULL, NULL, 'varchar', NULL),
	(1540526435721109511, 1540526435687555073, 'status', '状态（字典 0正常 1停用 2删除）', NULL, NULL, 'tinyint', NULL),
	(1540526435721109512, 1540526435687555073, 'create_time', '创建时间', NULL, NULL, 'datetime', NULL),
	(1540526435721109513, 1540526435687555073, 'create_user', '创建人', NULL, NULL, 'bigint', NULL),
	(1540526435721109514, 1540526435687555073, 'update_time', '更新时间', NULL, NULL, 'datetime', NULL),
	(1540526435721109515, 1540526435687555073, 'update_user', '更新人', NULL, NULL, 'bigint', NULL),
	(1540526435758858241, 1540526435721109516, 'id', '主键', NULL, NULL, 'bigint', NULL),
	(1540526435758858242, 1540526435721109516, 'name', '名称', NULL, NULL, 'varchar', NULL),
	(1540526435758858243, 1540526435721109516, 'code', '编码', NULL, NULL, 'varchar', NULL),
	(1540526435758858244, 1540526435721109516, 'sort', '排序', NULL, NULL, 'int', NULL),
	(1540526435758858245, 1540526435721109516, 'remark', '备注', NULL, NULL, 'varchar', NULL),
	(1540526435758858246, 1540526435721109516, 'status', '状态（字典 0正常 1停用 2删除）', NULL, NULL, 'tinyint', NULL),
	(1540526435758858247, 1540526435721109516, 'create_time', '创建时间', NULL, NULL, 'datetime', NULL),
	(1540526435758858248, 1540526435721109516, 'create_user', '创建人', NULL, NULL, 'bigint', NULL),
	(1540526435758858249, 1540526435721109516, 'update_time', '更新时间', NULL, NULL, 'datetime', NULL),
	(1540526435758858250, 1540526435721109516, 'update_user', '更新人', NULL, NULL, 'bigint', NULL),
	(1540526435758858252, 1540526435758858251, 'id', '主键', NULL, NULL, 'bigint', NULL),
	(1540526435758858253, 1540526435758858251, 'job_num', '工号', NULL, NULL, 'varchar', NULL),
	(1540526435758858254, 1540526435758858251, 'org_id', '所属机构id', NULL, NULL, 'bigint', NULL),
	(1540526435758858255, 1540526435758858251, 'org_name', '所属机构名称', NULL, NULL, 'varchar', NULL),
	(1540526435758858257, 1540526435758858256, 'id', '主键', NULL, NULL, 'bigint', NULL),
	(1540526435758858258, 1540526435758858256, 'emp_id', '员工id', NULL, NULL, 'bigint', NULL),
	(1540526435758858259, 1540526435758858256, 'org_id', '机构id', NULL, NULL, 'bigint', NULL),
	(1540526435758858260, 1540526435758858256, 'pos_id', '岗位id', NULL, NULL, 'bigint', NULL),
	(1540526435825967105, 1540526435758858261, 'id', '主键', NULL, NULL, 'bigint', NULL),
	(1540526435825967106, 1540526435758858261, 'emp_id', '员工id', NULL, NULL, 'bigint', NULL),
	(1540526435825967107, 1540526435758858261, 'pos_id', '职位id', NULL, NULL, 'bigint', NULL),
	(1540526435825967109, 1540526435825967108, 'id', '主键id', NULL, NULL, 'bigint', NULL),
	(1540526435825967110, 1540526435825967108, 'file_location', '文件存储位置（1:阿里云，2:腾讯云，3:minio，4:本地）', NULL, NULL, 'tinyint', NULL),
	(1540526435825967111, 1540526435825967108, 'file_bucket', '文件仓库', NULL, NULL, 'varchar', NULL),
	(1540526435825967112, 1540526435825967108, 'file_origin_name', '文件名称（上传时候的文件名）', NULL, NULL, 'varchar', NULL),
	(1540526435825967113, 1540526435825967108, 'file_suffix', '文件后缀', NULL, NULL, 'varchar', NULL),
	(1540526435825967114, 1540526435825967108, 'file_size_kb', '文件大小kb', NULL, NULL, 'bigint', NULL),
	(1540526435825967115, 1540526435825967108, 'file_size_info', '文件大小信息，计算后的', NULL, NULL, 'varchar', NULL),
	(1540526435825967116, 1540526435825967108, 'file_object_name', '存储到bucket的名称（文件唯一标识id）', NULL, NULL, 'varchar', NULL),
	(1540526435825967117, 1540526435825967108, 'file_path', '存储路径', NULL, NULL, 'varchar', NULL),
	(1540526435825967118, 1540526435825967108, 'create_time', '创建时间', NULL, NULL, 'datetime', NULL),
	(1540526435825967119, 1540526435825967108, 'create_user', '创建用户', NULL, NULL, 'bigint', NULL),
	(1540526435825967120, 1540526435825967108, 'update_time', '修改时间', NULL, NULL, 'datetime', NULL),
	(1540526435825967121, 1540526435825967108, 'update_user', '修改用户', NULL, NULL, 'bigint', NULL),
	(1540526435893075970, 1540526435893075969, 'id', '主键', NULL, NULL, 'bigint', NULL),
	(1540526435893075971, 1540526435893075969, 'pid', '父id', NULL, NULL, 'bigint', NULL),
	(1540526435930824706, 1540526435893075969, 'pids', '父ids', NULL, NULL, 'text', NULL),
	(1540526435930824707, 1540526435893075969, 'name', '名称', NULL, NULL, 'varchar', NULL),
	(1540526435930824708, 1540526435893075969, 'code', '编码', NULL, NULL, 'varchar', NULL),
	(1540526435930824709, 1540526435893075969, 'type', '菜单类型（字典 0目录 1菜单 2按钮）', NULL, NULL, 'tinyint', NULL),
	(1540526435930824710, 1540526435893075969, 'icon', '图标', NULL, NULL, 'varchar', NULL),
	(1540526435930824711, 1540526435893075969, 'router', '路由地址', NULL, NULL, 'varchar', NULL),
	(1540526435930824712, 1540526435893075969, 'component', '组件地址', NULL, NULL, 'varchar', NULL),
	(1540526435930824713, 1540526435893075969, 'permission', '权限标识', NULL, NULL, 'varchar', NULL),
	(1540526435930824714, 1540526435893075969, 'application', '应用分类（应用编码）', NULL, NULL, 'varchar', NULL),
	(1540526435930824715, 1540526435893075969, 'open_type', '打开方式（字典 0无 1组件 2内链 3外链）', NULL, NULL, 'tinyint', NULL),
	(1540526435930824716, 1540526435893075969, 'visible', '是否可见（Y-是，N-否）', NULL, NULL, 'char', NULL),
	(1540526435930824717, 1540526435893075969, 'link', '链接地址', NULL, NULL, 'varchar', NULL),
	(1540526435930824718, 1540526435893075969, 'redirect', '重定向地址', NULL, NULL, 'varchar', NULL),
	(1540526435930824719, 1540526435893075969, 'weight', '权重（字典 1系统权重 2业务权重）', NULL, NULL, 'tinyint', NULL),
	(1540526435930824720, 1540526435893075969, 'sort', '排序', NULL, NULL, 'int', NULL),
	(1540526435930824721, 1540526435893075969, 'remark', '备注', NULL, NULL, 'varchar', NULL),
	(1540526435930824722, 1540526435893075969, 'status', '状态（字典 0正常 1停用 2删除）', NULL, NULL, 'tinyint', NULL),
	(1540526435930824723, 1540526435893075969, 'create_time', '创建时间', NULL, NULL, 'datetime', NULL),
	(1540526435930824724, 1540526435893075969, 'create_user', '创建人', NULL, NULL, 'bigint', NULL),
	(1540526435930824725, 1540526435893075969, 'update_time', '修改时间', NULL, NULL, 'datetime', NULL),
	(1540526435930824726, 1540526435893075969, 'update_user', '修改人', NULL, NULL, 'bigint', NULL),
	(1540526435964379138, 1540526435964379137, 'id', '主键', NULL, NULL, 'bigint', NULL),
	(1540526435964379139, 1540526435964379137, 'title', '标题', NULL, NULL, 'varchar', NULL),
	(1540526435964379140, 1540526435964379137, 'content', '内容', NULL, NULL, 'text', NULL),
	(1540526435964379141, 1540526435964379137, 'type', '类型（字典 1通知 2公告）', NULL, NULL, 'tinyint', NULL),
	(1540526435964379142, 1540526435964379137, 'public_user_id', '发布人id', NULL, NULL, 'bigint', NULL),
	(1540526435964379143, 1540526435964379137, 'public_user_name', '发布人姓名', NULL, NULL, 'varchar', NULL),
	(1540526435964379144, 1540526435964379137, 'public_org_id', '发布机构id', NULL, NULL, 'bigint', NULL),
	(1540526435964379145, 1540526435964379137, 'public_org_name', '发布机构名称', NULL, NULL, 'varchar', NULL),
	(1540526435964379146, 1540526435964379137, 'public_time', '发布时间', NULL, NULL, 'datetime', NULL),
	(1540526435964379147, 1540526435964379137, 'cancel_time', '撤回时间', NULL, NULL, 'datetime', NULL),
	(1540526435964379148, 1540526435964379137, 'status', '状态（字典 0草稿 1发布 2撤回 3删除）', NULL, NULL, 'tinyint', NULL),
	(1540526435964379149, 1540526435964379137, 'create_time', '创建时间', NULL, NULL, 'datetime', NULL),
	(1540526435964379150, 1540526435964379137, 'create_user', '创建人', NULL, NULL, 'bigint', NULL),
	(1540526435964379151, 1540526435964379137, 'update_time', '修改时间', NULL, NULL, 'datetime', NULL),
	(1540526435964379152, 1540526435964379137, 'update_user', '修改人', NULL, NULL, 'bigint', NULL),
	(1540526436027293698, 1540526436027293697, 'id', '主键', NULL, NULL, 'bigint', NULL),
	(1540526436027293699, 1540526436027293697, 'notice_id', '通知公告id', NULL, NULL, 'bigint', NULL),
	(1540526436027293700, 1540526436027293697, 'user_id', '用户id', NULL, NULL, 'bigint', NULL),
	(1540526436027293701, 1540526436027293697, 'status', '状态（字典 0未读 1已读）', NULL, NULL, 'tinyint', NULL),
	(1540526436027293702, 1540526436027293697, 'read_time', '阅读时间', NULL, NULL, 'datetime', NULL),
	(1540526436065042434, 1540526436065042433, 'id', '主键', NULL, NULL, 'bigint', NULL),
	(1540526436065042435, 1540526436065042433, 'uuid', '第三方平台的用户唯一id', NULL, NULL, 'varchar', NULL),
	(1540526436065042436, 1540526436065042433, 'access_token', '用户授权的token', NULL, NULL, 'varchar', NULL),
	(1540526436065042437, 1540526436065042433, 'nick_name', '昵称', NULL, NULL, 'varchar', NULL),
	(1540526436065042438, 1540526436065042433, 'avatar', '头像', NULL, NULL, 'varchar', NULL),
	(1540526436065042439, 1540526436065042433, 'blog', '用户网址', NULL, NULL, 'varchar', NULL),
	(1540526436065042440, 1540526436065042433, 'company', '所在公司', NULL, NULL, 'varchar', NULL),
	(1540526436065042441, 1540526436065042433, 'location', '位置', NULL, NULL, 'varchar', NULL),
	(1540526436065042442, 1540526436065042433, 'email', '邮箱', NULL, NULL, 'varchar', NULL),
	(1540526436065042443, 1540526436065042433, 'gender', '性别', NULL, NULL, 'varchar', NULL),
	(1540526436065042444, 1540526436065042433, 'source', '用户来源', NULL, NULL, 'varchar', NULL),
	(1540526436065042445, 1540526436065042433, 'remark', '用户备注（各平台中的用户个人介绍）', NULL, NULL, 'varchar', NULL),
	(1540526436065042446, 1540526436065042433, 'create_time', '创建时间', NULL, NULL, 'datetime', NULL),
	(1540526436065042447, 1540526436065042433, 'create_user', '创建用户', NULL, NULL, 'bigint', NULL),
	(1540526436065042448, 1540526436065042433, 'update_time', '更新时间', NULL, NULL, 'datetime', NULL),
	(1540526436065042449, 1540526436065042433, 'update_user', '更新用户', NULL, NULL, 'bigint', NULL),
	(1540526436106985475, 1540526436106985474, 'id', '主键', NULL, NULL, 'bigint', NULL),
	(1540526436106985476, 1540526436106985474, 'name', '名称', NULL, NULL, 'varchar', NULL),
	(1540526436106985477, 1540526436106985474, 'op_type', '操作类型', NULL, NULL, 'tinyint', NULL),
	(1540526436106985478, 1540526436106985474, 'success', '是否执行成功（Y-是，N-否）', NULL, NULL, 'char', NULL),
	(1540526436106985479, 1540526436106985474, 'message', '具体消息', NULL, NULL, 'text', NULL),
	(1540526436106985480, 1540526436106985474, 'ip', 'ip', NULL, NULL, 'varchar', NULL),
	(1540526436106985481, 1540526436106985474, 'location', '地址', NULL, NULL, 'varchar', NULL),
	(1540526436106985482, 1540526436106985474, 'browser', '浏览器', NULL, NULL, 'varchar', NULL),
	(1540526436106985483, 1540526436106985474, 'os', '操作系统', NULL, NULL, 'varchar', NULL),
	(1540526436106985484, 1540526436106985474, 'url', '请求地址', NULL, NULL, 'varchar', NULL),
	(1540526436106985485, 1540526436106985474, 'class_name', '类名称', NULL, NULL, 'varchar', NULL),
	(1540526436106985486, 1540526436106985474, 'method_name', '方法名称', NULL, NULL, 'varchar', NULL),
	(1540526436144734209, 1540526436106985474, 'req_method', '请求方式（GET POST PUT DELETE)', NULL, NULL, 'varchar', NULL),
	(1540526436144734210, 1540526436106985474, 'param', '请求参数', NULL, NULL, 'text', NULL),
	(1540526436144734211, 1540526436106985474, 'result', '返回结果', NULL, NULL, 'longtext', NULL),
	(1540526436144734212, 1540526436106985474, 'op_time', '操作时间', NULL, NULL, 'datetime', NULL),
	(1540526436144734213, 1540526436106985474, 'account', '操作账号', NULL, NULL, 'varchar', NULL),
	(1540526436144734214, 1540526436106985474, 'sign_value', '签名数据（除ID外）', NULL, NULL, 'varchar', NULL),
	(1540526436144734216, 1540526436144734215, 'id', '主键', NULL, NULL, 'bigint', NULL),
	(1540526436144734217, 1540526436144734215, 'pid', '父id', NULL, NULL, 'bigint', NULL),
	(1540526436144734218, 1540526436144734215, 'pids', '父ids', NULL, NULL, 'text', NULL),
	(1540526436144734219, 1540526436144734215, 'name', '名称', NULL, NULL, 'varchar', NULL),
	(1540526436144734220, 1540526436144734215, 'code', '编码', NULL, NULL, 'varchar', NULL),
	(1540526436144734221, 1540526436144734215, 'sort', '排序', NULL, NULL, 'int', NULL),
	(1540526436144734222, 1540526436144734215, 'remark', '描述', NULL, NULL, 'varchar', NULL),
	(1540526436144734223, 1540526436144734215, 'status', '状态（字典 0正常 1停用 2删除）', NULL, NULL, 'tinyint', NULL),
	(1540526436144734224, 1540526436144734215, 'create_time', '创建时间', NULL, NULL, 'datetime', NULL),
	(1540526436144734225, 1540526436144734215, 'create_user', '创建人', NULL, NULL, 'bigint', NULL),
	(1540526436144734226, 1540526436144734215, 'update_time', '更新时间', NULL, NULL, 'datetime', NULL),
	(1540526436144734227, 1540526436144734215, 'update_user', '更新人', NULL, NULL, 'bigint', NULL),
	(1540526436144734229, 1540526436144734228, 'id', '主键', NULL, NULL, 'bigint', NULL),
	(1540526436144734230, 1540526436144734228, 'name', '名称', NULL, NULL, 'varchar', NULL),
	(1540526436144734231, 1540526436144734228, 'code', '编码', NULL, NULL, 'varchar', NULL),
	(1540526436144734232, 1540526436144734228, 'sort', '排序', NULL, NULL, 'int', NULL),
	(1540526436144734233, 1540526436144734228, 'remark', '备注', NULL, NULL, 'varchar', NULL),
	(1540526436144734234, 1540526436144734228, 'status', '状态（字典 0正常 1停用 2删除）', NULL, NULL, 'tinyint', NULL),
	(1540526436144734235, 1540526436144734228, 'create_time', '创建时间', NULL, NULL, 'datetime', NULL),
	(1540526436144734236, 1540526436144734228, 'create_user', '创建人', NULL, NULL, 'bigint', NULL),
	(1540526436144734237, 1540526436144734228, 'update_time', '更新时间', NULL, NULL, 'datetime', NULL),
	(1540526436144734238, 1540526436144734228, 'update_user', '更新人', NULL, NULL, 'bigint', NULL),
	(1540526436241203203, 1540526436241203202, 'id', '主键id', NULL, NULL, 'bigint', NULL),
	(1540526436241203204, 1540526436241203202, 'name', '名称', NULL, NULL, 'varchar', NULL),
	(1540526436241203205, 1540526436241203202, 'code', '编码', NULL, NULL, 'varchar', NULL),
	(1540526436241203206, 1540526436241203202, 'sort', '序号', NULL, NULL, 'int', NULL),
	(1540526436241203207, 1540526436241203202, 'data_scope_type', '数据范围类型（字典 1全部数据 2本部门及以下数据 3本部门数据 4仅本人数据 5自定义数据）', NULL, NULL, 'tinyint', NULL),
	(1540526436241203208, 1540526436241203202, 'remark', '备注', NULL, NULL, 'varchar', NULL),
	(1540526436241203209, 1540526436241203202, 'status', '状态（字典 0正常 1停用 2删除）', NULL, NULL, 'tinyint', NULL),
	(1540526436241203210, 1540526436241203202, 'create_time', '创建时间', NULL, NULL, 'datetime', NULL),
	(1540526436241203211, 1540526436241203202, 'create_user', '创建人', NULL, NULL, 'bigint', NULL),
	(1540526436241203212, 1540526436241203202, 'update_time', '更新时间', NULL, NULL, 'datetime', NULL),
	(1540526436241203213, 1540526436241203202, 'update_user', '更新人', NULL, NULL, 'bigint', NULL),
	(1540526436278951938, 1540526436241203214, 'id', '主键', NULL, NULL, 'bigint', NULL),
	(1540526436278951939, 1540526436241203214, 'role_id', '角色id', NULL, NULL, 'bigint', NULL),
	(1540526436278951940, 1540526436241203214, 'org_id', '机构id', NULL, NULL, 'bigint', NULL),
	(1540526436278951942, 1540526436278951941, 'id', '主键', NULL, NULL, 'bigint', NULL),
	(1540526436278951943, 1540526436278951941, 'role_id', '角色id', NULL, NULL, 'bigint', NULL),
	(1540526436278951944, 1540526436278951941, 'menu_id', '菜单id', NULL, NULL, 'bigint', NULL),
	(1540526436278951946, 1540526436278951945, 'id', '主键', NULL, NULL, 'bigint', NULL),
	(1540526436278951947, 1540526436278951945, 'phone_numbers', '手机号', NULL, NULL, 'varchar', NULL),
	(1540526436278951948, 1540526436278951945, 'validate_code', '短信验证码', NULL, NULL, 'varchar', NULL),
	(1540526436316700673, 1540526436278951945, 'template_code', '短信模板ID', NULL, NULL, 'varchar', NULL),
	(1540526436316700674, 1540526436278951945, 'biz_id', '回执id，可根据该id查询具体的发送状态', NULL, NULL, 'varchar', NULL),
	(1540526436316700675, 1540526436278951945, 'status', '发送状态（字典 0 未发送，1 发送成功，2 发送失败，3 失效）', NULL, NULL, 'tinyint', NULL),
	(1540526436316700676, 1540526436278951945, 'source', '来源（字典 1 app， 2 pc， 3 其他）', NULL, NULL, 'tinyint', NULL),
	(1540526436316700677, 1540526436278951945, 'invalid_time', '失效时间', NULL, NULL, 'datetime', NULL),
	(1540526436316700678, 1540526436278951945, 'create_time', '创建时间', NULL, NULL, 'datetime', NULL),
	(1540526436316700679, 1540526436278951945, 'create_user', '创建人', NULL, NULL, 'bigint', NULL),
	(1540526436316700680, 1540526436278951945, 'update_time', '更新时间', NULL, NULL, 'datetime', NULL),
	(1540526436316700681, 1540526436278951945, 'update_user', '更新人', NULL, NULL, 'bigint', NULL),
	(1540526436316700683, 1540526436316700682, 'id', '定时器id', NULL, NULL, 'bigint', NULL),
	(1540526436316700684, 1540526436316700682, 'timer_name', '任务名称', NULL, NULL, 'varchar', NULL),
	(1540526436316700685, 1540526436316700682, 'action_class', '执行任务的class的类名（实现了TimerTaskRunner接口的类的全称）', NULL, NULL, 'varchar', NULL),
	(1540526436316700686, 1540526436316700682, 'cron', '定时任务表达式', NULL, NULL, 'varchar', NULL),
	(1540526436316700687, 1540526436316700682, 'job_status', '状态（字典 1运行  2停止）', NULL, NULL, 'tinyint', NULL),
	(1540526436316700688, 1540526436316700682, 'param', '', NULL, NULL, 'text', NULL),
	(1540526436316700689, 1540526436316700682, 'remark', '备注信息', NULL, NULL, 'varchar', NULL),
	(1540526436316700690, 1540526436316700682, 'create_time', '创建时间', NULL, NULL, 'datetime', NULL),
	(1540526436316700691, 1540526436316700682, 'create_user', '创建人', NULL, NULL, 'bigint', NULL),
	(1540526436316700692, 1540526436316700682, 'update_time', '更新时间', NULL, NULL, 'datetime', NULL),
	(1540526436316700693, 1540526436316700682, 'update_user', '更新人', NULL, NULL, 'bigint', NULL),
	(1540526436383809537, 1540526436316700694, 'ID', '主键ID', NULL, NULL, 'bigint', NULL),
	(1540526436383809538, 1540526436316700694, 'TIMER_ID', '任务编号', NULL, NULL, 'bigint', NULL),
	(1540526436383809539, 1540526436316700694, 'EXECUTE_START_TIME', '执行开始时间', NULL, NULL, 'datetime', NULL),
	(1540526436383809540, 1540526436316700694, 'EXECUTE_END_TIME', '执行结束时间', NULL, NULL, 'datetime', NULL),
	(1540526436383809541, 1540526436316700694, 'EXECUTE_CODE', '执行结果', NULL, NULL, 'varchar', NULL),
	(1540526436383809542, 1540526436316700694, 'EXECUTE_MSG', '执行信息', NULL, NULL, 'text', NULL),
	(1540526436383809543, 1540526436316700694, 'EXECUTE_PARAM', '执行参数', NULL, NULL, 'text', NULL),
	(1540526436383809545, 1540526436383809544, 'id', '主键', NULL, NULL, 'bigint', NULL),
	(1540526436383809546, 1540526436383809544, 'account', '账号', NULL, NULL, 'varchar', NULL),
	(1540526436383809547, 1540526436383809544, 'pwd_hash_value', '密码', NULL, NULL, 'varchar', NULL),
	(1540526436383809548, 1540526436383809544, 'nick_name', '昵称', NULL, NULL, 'varchar', NULL),
	(1540526436383809549, 1540526436383809544, 'name', '姓名', NULL, NULL, 'varchar', NULL),
	(1540526436383809550, 1540526436383809544, 'avatar', '头像', NULL, NULL, 'bigint', NULL),
	(1540526436383809551, 1540526436383809544, 'birthday', '生日', NULL, NULL, 'date', NULL),
	(1540526436383809552, 1540526436383809544, 'sex', '性别(字典 1男 2女 3未知)', NULL, NULL, 'tinyint', NULL),
	(1540526436383809553, 1540526436383809544, 'email', '邮箱', NULL, NULL, 'varchar', NULL),
	(1540526436383809554, 1540526436383809544, 'phone', '手机', NULL, NULL, 'varchar', NULL),
	(1540526436383809555, 1540526436383809544, 'tel', '电话', NULL, NULL, 'varchar', NULL),
	(1540526436383809556, 1540526436383809544, 'last_login_ip', '最后登陆IP', NULL, NULL, 'varchar', NULL),
	(1540526436383809557, 1540526436383809544, 'last_login_time', '最后登陆时间', NULL, NULL, 'datetime', NULL),
	(1540526436383809558, 1540526436383809544, 'admin_type', '管理员类型（0超级管理员 1非管理员）', NULL, NULL, 'tinyint', NULL),
	(1540526436383809559, 1540526436383809544, 'status', '状态（字典 0正常 1冻结 2删除）', NULL, NULL, 'tinyint', NULL),
	(1540526436383809560, 1540526436383809544, 'create_time', '创建时间', NULL, NULL, 'datetime', NULL),
	(1540526436383809561, 1540526436383809544, 'create_user', '创建人', NULL, NULL, 'bigint', NULL),
	(1540526436383809562, 1540526436383809544, 'update_time', '更新时间', NULL, NULL, 'datetime', NULL),
	(1540526436383809563, 1540526436383809544, 'update_user', '更新人', NULL, NULL, 'bigint', NULL),
	(1540526436450918403, 1540526436450918402, 'id', '主键', NULL, NULL, 'bigint', NULL),
	(1540526436450918404, 1540526436450918402, 'user_id', '用户id', NULL, NULL, 'bigint', NULL),
	(1540526436450918405, 1540526436450918402, 'org_id', '机构id', NULL, NULL, 'bigint', NULL),
	(1540526436450918407, 1540526436450918406, 'id', '主键', NULL, NULL, 'bigint', NULL),
	(1540526436450918408, 1540526436450918406, 'user_id', '用户id', NULL, NULL, 'bigint', NULL),
	(1540526436450918409, 1540526436450918406, 'role_id', '角色id', NULL, NULL, 'bigint', NULL),
	(1540526436450918411, 1540526436450918410, 'id', '主键', NULL, NULL, 'bigint', NULL),
	(1540526436450918412, 1540526436450918410, 'name', '名称', NULL, NULL, 'varchar', NULL),
	(1540526436450918413, 1540526436450918410, 'success', '是否执行成功（Y-是，N-否）', NULL, NULL, 'char', NULL),
	(1540526436450918414, 1540526436450918410, 'message', '具体消息', NULL, NULL, 'text', NULL),
	(1540526436450918415, 1540526436450918410, 'ip', 'ip', NULL, NULL, 'varchar', NULL),
	(1540526436450918416, 1540526436450918410, 'location', '地址', NULL, NULL, 'varchar', NULL),
	(1540526436450918417, 1540526436450918410, 'browser', '浏览器', NULL, NULL, 'varchar', NULL),
	(1540526436450918418, 1540526436450918410, 'os', '操作系统', NULL, NULL, 'varchar', NULL),
	(1540526436450918419, 1540526436450918410, 'vis_type', '操作类型（字典 1登入 2登出）', NULL, NULL, 'tinyint', NULL),
	(1540526436450918420, 1540526436450918410, 'vis_time', '访问时间', NULL, NULL, 'datetime', NULL),
	(1540526436450918421, 1540526436450918410, 'account', '访问账号', NULL, NULL, 'varchar', NULL),
	(1540526436450918422, 1540526436450918410, 'sign_value', '签名数据（除ID外）', NULL, NULL, 'varchar', NULL),
	(1540526436518027267, 1540526436518027266, 'ID', '主键ID', NULL, NULL, 'bigint', NULL),
	(1540526436518027268, 1540526436518027266, 'DISPLAY_NAME', '展示名称', NULL, NULL, 'varchar', NULL),
	(1540526436518027269, 1540526436518027266, 'TYPE', '类型', NULL, NULL, 'varchar', NULL),
	(1540526436518027270, 1540526436518027266, 'CHART_CONFIG', '图标配置', NULL, NULL, 'text', NULL),
	(1540526436518027271, 1540526436518027266, 'START_TIME', '开始时间', NULL, NULL, 'datetime', NULL),
	(1540526436518027272, 1540526436518027266, 'END_TIME', '结束时间', NULL, NULL, 'datetime', NULL),
	(1540526436518027273, 1540526436518027266, 'FILTER_LOGIC', '条件逻辑', NULL, NULL, 'varchar', NULL),
	(1540526436518027275, 1540526436518027274, 'ID', '主键ID', NULL, NULL, 'bigint', NULL),
	(1540526436518027276, 1540526436518027274, 'ANALYSIS_ID', '实时分析ID', NULL, NULL, 'bigint', NULL),
	(1540526436518027277, 1540526436518027274, 'EVENT_ID', '事件ID', NULL, NULL, 'bigint', NULL),
	(1540526436518027278, 1540526436518027274, 'SORT', '排序', NULL, NULL, 'int', NULL),
	(1540526436597719041, 1540526436518027279, 'ID', '主键ID', NULL, NULL, 'bigint', NULL),
	(1540526436597719042, 1540526436518027279, 'PARENT_ID', '父级条件ID', NULL, NULL, 'bigint', NULL),
	(1540526436597719043, 1540526436518027279, 'ANALYSIS_EVENT_ID', '实时分析事件ID', NULL, NULL, 'bigint', NULL),
	(1540526436597719044, 1540526436518027279, 'PROPERTY_ID', '属性ID', NULL, NULL, 'bigint', NULL),
	(1540526436597719045, 1540526436518027279, 'SUB_LOGIC', '子级条件逻辑', NULL, NULL, 'varchar', NULL),
	(1540526436597719046, 1540526436518027279, 'MEASURE', '计算方式', NULL, NULL, 'varchar', NULL),
	(1540526436597719047, 1540526436518027279, 'VALUE', '条件值', NULL, NULL, 'text', NULL),
	(1540526436631273473, 1540526436597719048, 'ID', '主键ID', NULL, NULL, 'bigint', NULL),
	(1540526436631273474, 1540526436597719048, 'ANALYSIS_EVENT_ID', '实时分析ID', NULL, NULL, 'bigint', NULL),
	(1540526436631273475, 1540526436597719048, 'property_Id', '事件ID', NULL, NULL, 'bigint', NULL),
	(1540526436631273476, 1540526436597719048, 'measure', '计算方式', NULL, NULL, 'varchar', NULL),
	(1540526436631273478, 1540526436631273477, 'ID', '主键ID', NULL, NULL, 'bigint', NULL),
	(1540526436631273479, 1540526436631273477, 'PARENT_ID', '父级条件ID', NULL, NULL, 'bigint', NULL),
	(1540526436631273480, 1540526436631273477, 'ANALYSIS_ID', '实时分析事件ID', NULL, NULL, 'bigint', NULL),
	(1540526436631273481, 1540526436631273477, 'PROPERTY_ID', '属性ID', NULL, NULL, 'bigint', NULL),
	(1540526436631273482, 1540526436631273477, 'SUB_LOGIC', '子级条件逻辑', NULL, NULL, 'varchar', NULL),
	(1540526436631273483, 1540526436631273477, 'MEASURE', '计算方式', NULL, NULL, 'varchar', NULL),
	(1540526436631273484, 1540526436631273477, 'VALUE', '条件值', NULL, NULL, 'text', NULL),
	(1540526436631273486, 1540526436631273485, 'ID', '主键ID', NULL, NULL, 'bigint', NULL),
	(1540526436631273487, 1540526436631273485, 'PARENT_ID', '父级条件ID', NULL, NULL, 'bigint', NULL),
	(1540526436631273488, 1540526436631273485, 'ANALYSIS_ID', '实时分析ID', NULL, NULL, 'bigint', NULL),
	(1540526436631273489, 1540526436631273485, 'PROPERTY_ID', '属性ID', NULL, NULL, 'bigint', NULL),
	(1540526436631273490, 1540526436631273485, 'unit', '分组单位', NULL, NULL, 'varchar', NULL),
	(1540526436631273491, 1540526436631273485, 'unit_Type', '分组单位类型', NULL, NULL, 'text', NULL),
	(1540526436698382339, 1540526436698382338, 'ID', '主键ID', NULL, NULL, 'bigint', NULL),
	(1540526436698382341, 1540526436698382340, 'ID', '主键ID', NULL, NULL, 'bigint', NULL),
	(1540526436698382342, 1540526436698382340, 'ANALYSIS_ID', '实时分析ID', NULL, NULL, 'bigint', NULL),
	(1540526436740325379, 1540526436740325378, 'ID', '主键ID', NULL, NULL, 'bigint', NULL),
	(1540526436740325380, 1540526436740325378, 'DISPLAY_NAME', '分组描述', NULL, NULL, 'varchar', NULL),
	(1540526436740325382, 1540526436740325381, 'ID', '主键ID', NULL, NULL, 'bigint', NULL),
	(1540526436740325383, 1540526436740325381, 'DISPLAY_NAME', '数据源名称', NULL, NULL, 'varchar', NULL),
	(1540526436740325384, 1540526436740325381, 'CONFIG', '数据库配置', NULL, NULL, 'text', NULL),
	(1540526436740325385, 1540526436740325381, 'TYPE', '数据库类型', NULL, NULL, 'varchar', NULL),
	(1540526436740325386, 1540526436740325381, 'REMARK', '备注', NULL, NULL, 'varchar', NULL),
	(1540526436740325388, 1540526436740325387, 'ID', '主键ID', NULL, NULL, 'bigint', NULL),
	(1540526436799045633, 1540526436740325387, 'EVENT_GORUP_ID', '事件分组', NULL, NULL, 'varchar', NULL),
	(1540526436799045634, 1540526436740325387, 'DISPLAY_NAME', '事件名称', NULL, NULL, 'varchar', NULL),
	(1540526436799045635, 1540526436740325387, 'TABLE_COLUMN_ID', '表字段ID', NULL, NULL, 'bigint', NULL),
	(1540526436799045636, 1540526436740325387, 'MEASURE', '计算方式', NULL, NULL, 'varchar', NULL),
	(1540526436799045637, 1540526436740325387, 'VALUE', '事件值', NULL, NULL, 'varchar', NULL),
	(1540526436799045638, 1540526436740325387, 'VALUE_TYPE', '事件值类型', NULL, NULL, 'varchar', NULL),
	(1540526436799045640, 1540526436799045639, 'ID', '主键ID', NULL, NULL, 'bigint', NULL),
	(1540526436799045641, 1540526436799045639, 'PID', '', NULL, NULL, 'bigint', NULL),
	(1540526436799045642, 1540526436799045639, 'PIDS', '', NULL, NULL, 'text', NULL),
	(1540526436799045643, 1540526436799045639, 'DISPLAY_NAME', '事件名称', NULL, NULL, 'varchar', NULL),
	(1540526436836794369, 1540526436799045644, 'ID', '主键ID', NULL, NULL, 'bigint', NULL),
	(1540526436836794370, 1540526436799045644, 'EVENT_ID', '事件编号', NULL, NULL, 'bigint', NULL),
	(1540526436836794371, 1540526436799045644, 'PROPERTY_ID', '属性编号', NULL, NULL, 'bigint', NULL),
	(1540526436836794373, 1540526436836794372, 'ID', '主键ID', NULL, NULL, 'bigint', NULL),
	(1540526436836794375, 1540526436836794374, 'ID', '主键ID', NULL, NULL, 'bigint', NULL),
	(1540526436836794376, 1540526436836794374, 'DISPLAY_NAME', '属性名称', NULL, NULL, 'varchar', NULL),
	(1540526436836794377, 1540526436836794374, 'PROPERTY_GROUP_ID', '属性分组', NULL, NULL, 'bigint', NULL),
	(1540526436874543105, 1540526436836794374, 'TABLE_COLUMN_ID', '表字段ID', NULL, NULL, 'bigint', NULL),
	(1540526436874543106, 1540526436836794374, 'MEASURE', '计算方式', NULL, NULL, 'varchar', NULL),
	(1540526436874543107, 1540526436836794374, 'VALUE', '属性值', NULL, NULL, 'text', NULL),
	(1540526436874543108, 1540526436836794374, 'VALUE_TYPE', '属性值类型', NULL, NULL, 'varchar', NULL),
	(1540526436874543109, 1540526436836794374, 'UNIT', '属性值', NULL, NULL, 'text', NULL),
	(1540526436874543110, 1540526436836794374, 'UNIT_TYPE', '属性值类型', NULL, NULL, 'varchar', NULL),
	(1540526436874543111, 1540526436836794374, 'IS_DEFAULT', '属性值类型', NULL, NULL, 'varchar', NULL),
	(1540526436874543112, 1540526436836794374, 'REMARK', '属性值类型', NULL, NULL, 'varchar', NULL),
	(1540526436874543114, 1540526436874543113, 'ID', '主键ID', NULL, NULL, 'bigint', NULL),
	(1540526436874543115, 1540526436874543113, 'PID', '', NULL, NULL, 'bigint', NULL),
	(1540526436874543116, 1540526436874543113, 'PIDS', '', NULL, NULL, 'text', NULL),
	(1540526436874543117, 1540526436874543113, 'DISPLAY_NAME', '分组名称', NULL, NULL, 'varchar', NULL),
	(1540526436874543119, 1540526436874543118, 'ID', '主键ID', NULL, NULL, 'bigint', NULL),
	(1540526436933263363, 1540526436933263362, 'ID', '主键ID', NULL, NULL, 'bigint', NULL),
	(1540526436933263365, 1540526436933263364, 'ID', '主键ID', NULL, NULL, 'bigint', NULL),
	(1540526436933263366, 1540526436933263364, 'REPORT_ID', '报表ID', NULL, NULL, 'bigint', NULL),
	(1540526436933263367, 1540526436933263364, 'INDICATOR_ID', '指标ID', NULL, NULL, 'bigint', NULL),
	(1540526436966817794, 1540526436966817793, 'ID', '主键ID', NULL, NULL, 'bigint', NULL),
	(1540526436966817795, 1540526436966817793, 'DATA_SOURCE_ID', '数据源ID', NULL, NULL, 'bigint', NULL),
	(1540526436966817796, 1540526436966817793, 'TABLE_NAME', '表名称', NULL, NULL, 'varchar', NULL),
	(1540526436966817797, 1540526436966817793, 'DISPLAY_NAME', '展示名称', NULL, NULL, 'varchar', NULL),
	(1540526436966817798, 1540526436966817793, 'REFRESH_TYPE', '刷新方式', NULL, NULL, 'varchar', NULL),
	(1540526436966817799, 1540526436966817793, 'REMARK', '备注', NULL, NULL, 'varchar', NULL),
	(1540526437000372225, 1540526436966817800, 'ID', '主键ID', NULL, NULL, 'bigint', NULL),
	(1540526437000372226, 1540526436966817800, 'TABLE_ID', '数据表ID', NULL, NULL, 'bigint', NULL),
	(1540526437000372227, 1540526436966817800, 'COLUMN_NAME', '字段名称', NULL, NULL, 'varchar', NULL),
	(1540526437000372228, 1540526436966817800, 'DISPLAY_NAME', '展示名称', NULL, NULL, 'varchar', NULL),
	(1540526437000372229, 1540526436966817800, 'REFRESH_TYPE', '刷新方式', NULL, NULL, 'varchar', NULL),
	(1540526437000372230, 1540526436966817800, 'COLUMN_TYPE', '字段类型', NULL, NULL, 'varchar', NULL),
	(1540526437000372231, 1540526436966817800, 'DATA_TYPE', '数据类型', NULL, NULL, 'varchar', NULL),
	(1540526437000372232, 1540526436966817800, 'REMARK', '备注', NULL, NULL, 'varchar', NULL),
	(1540526437000372234, 1540526437000372233, 'ID', '主键ID', NULL, NULL, 'bigint', NULL),
	(1540526437000372235, 1540526437000372233, 'column_Id', '字段ID', NULL, NULL, 'bigint', NULL),
	(1540526437000372236, 1540526437000372233, 'connect_Column_Id', '关联字段ID', NULL, NULL, 'bigint', NULL),
	(1540526437000372237, 1540526437000372233, 'connect_Type', '关联类型', NULL, NULL, 'varchar', NULL),
	(1540556831401832449, 1540526435444285441, 'id', '主键id', NULL, NULL, 'bigint', NULL),
	(1540556831468941313, 1540526435444285441, 'name', '应用名称', NULL, NULL, 'varchar', NULL),
	(1540556831468941314, 1540526435444285441, 'code', '编码', NULL, NULL, 'varchar', NULL),
	(1540556831468941315, 1540526435444285441, 'active', '是否默认激活（Y-是，N-否）', NULL, NULL, 'varchar', NULL),
	(1540556831468941316, 1540526435444285441, 'status', '状态（字典 0正常 1停用 2删除）', NULL, NULL, 'tinyint', NULL),
	(1540556831468941317, 1540526435444285441, 'create_time', '创建时间', NULL, NULL, 'datetime', NULL),
	(1540556831468941318, 1540526435444285441, 'create_user', '创建人', NULL, NULL, 'bigint', NULL),
	(1540556831468941319, 1540526435444285441, 'update_time', '修改时间', NULL, NULL, 'datetime', NULL),
	(1540556831468941320, 1540526435444285441, 'update_user', '修改人', NULL, NULL, 'bigint', NULL);
/*!40000 ALTER TABLE `tbl_board_table_column` ENABLE KEYS */;

-- Dumping structure for table young-board.tbl_board_table_connect
DROP TABLE IF EXISTS `tbl_board_table_connect`;
CREATE TABLE IF NOT EXISTS `tbl_board_table_connect` (
  `ID` bigint(20) NOT NULL COMMENT '主键ID',
  `column_Id` bigint(20) DEFAULT NULL COMMENT '字段ID',
  `table_id` bigint(20) DEFAULT NULL,
  `connect_Column_Id` bigint(20) DEFAULT NULL COMMENT '关联字段ID',
  `connect_table_id` bigint(20) DEFAULT NULL,
  `connect_Type` varchar(128) DEFAULT NULL COMMENT '关联类型',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='字段关联配置表';

-- Dumping data for table young-board.tbl_board_table_connect: ~0 rows (approximately)
DELETE FROM `tbl_board_table_connect`;
/*!40000 ALTER TABLE `tbl_board_table_connect` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_board_table_connect` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
