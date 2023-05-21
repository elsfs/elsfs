DROP DATABASE IF EXISTS `elsfs`;
CREATE DATABASE `elsfs` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;
SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;
USE `elsfs`;

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `user_id`     bigint unsigned NOT NULL,
    `username`    varchar(64)     NOT NULL COMMENT '用户名',
    `password`    varchar(255)    NOT NULL COMMENT '密码',
    `phone`       varchar(20)      DEFAULT NULL COMMENT '手机号',
    `avatar`      varchar(255)     DEFAULT NULL COMMENT '头像',
    `nickname`    varchar(255)     DEFAULT NULL COMMENT '用户昵称',
    `email`       varchar(255)     DEFAULT NULL COMMENT '用户邮箱',
    `is_deleted`  tinyint unsigned DEFAULT '0' COMMENT '0-正常，1-删除',
    `create_time` datetime         DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime         DEFAULT NULL COMMENT '修改时间',
    `create_by`   varchar(64)      DEFAULT NULL COMMENT '创建者',
    `update_by`   varchar(64)      DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY `pk_user_id` (`user_id`),
    UNIQUE KEY `uk_username` (`username`),
    INDEX `idx_phone` (`phone`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin
  ROW_FORMAT = DYNAMIC COMMENT ='用户表';

DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`
(
    /*---------菜单和meta公共部分---------*/
    `permission_id`       varchar(32)  NOT NULL COMMENT '主键id',
    `parent_id`           varchar(32)  NULL DEFAULT NULL COMMENT '父id',
    `order_no`            double(8, 2) NULL DEFAULT NULL COMMENT '菜单排序',
    `icon`                varchar(255) NULL DEFAULT NULL COMMENT '菜单图标',
    /*hideMenu*/
    `children`            tinyint           default 1 not null comment '隐藏',
    /*----------菜单部分--------------*/
    `component`           varchar(255) NULL DEFAULT NULL COMMENT '组件',
    `redirect`            varchar(255) NULL DEFAULT NULL COMMENT '一级菜单跳转地址',
    `name`                varchar(255) NULL DEFAULT NULL COMMENT '菜单标题',
    `path`                varchar(255) NULL DEFAULT NULL COMMENT '路径',
    /*--------------mate部分---------------------------*/
    `title`               varchar(255) NULL DEFAULT NULL COMMENT '标题',
    `current_active_menu` varchar(255) NULL DEFAULT NULL COMMENT '当前活动菜单',

    `is_hidden`           int(2)       NULL DEFAULT 0 COMMENT '是否隐藏路由: 0否,1是',
    `menu_type`           int(11)      NULL DEFAULT NULL COMMENT '菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)',

    `is_deleted`          tinyint unsigned  DEFAULT '0' COMMENT '0-正常，1-删除',
    `create_time`         datetime          DEFAULT NULL COMMENT '创建时间',
    `update_time`         datetime          DEFAULT NULL COMMENT '修改时间',
    `create_by`           varchar(64)       DEFAULT NULL COMMENT '创建者',
    `update_by`           varchar(64)       DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`permission_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin
  ROW_FORMAT = DYNAMIC COMMENT ='菜单权限表';

DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`
(
    `dept_id`     bigint unsigned NOT NULL,
    `parent_id`   bigint unsigned NULL     DEFAULT NULL COMMENT '父id',
    `dept_name`   varchar(50)     NULL     DEFAULT NULL COMMENT '部门名称',
    `is_status`   tinyint unsigned         DEFAULT '0' COMMENT '0-启用，1-停用',
    `remark`      varchar(255)    NULL     DEFAULT NULL COMMENT '备注',
    `order_no`    int unsigned    not null default 0 comment '排序',
    `create_time` datetime                 DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime                 DEFAULT NULL COMMENT '修改时间',
    `create_by`   varchar(64)              DEFAULT NULL COMMENT '创建者',
    `update_by`   varchar(64)              DEFAULT NULL COMMENT '更新人',
    `is_deleted`  tinyint unsigned         DEFAULT '0' COMMENT '0-正常，1-删除',
    PRIMARY KEY `pk_dept_id` (`dept_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_bin
  ROW_FORMAT = DYNAMIC COMMENT ='部门表';