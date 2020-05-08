CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(10) NOT NULL,
  `password` varchar(100) NOT NULL,
  `sex` tinyint(3) unsigned DEFAULT NULL,
  `nickname` varchar(100) DEFAULT NULL,
  `created_date` datetime NOT NULL,
  `created_by` int(10) unsigned NOT NULL,
  `last_modified_by` int(10) unsigned DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `role` VALUES (1, '超级管理员');
INSERT INTO `role` VALUES (2, '管理员');
INSERT INTO `role` VALUES (3, '用户');

CREATE TABLE `user_role` (
  `user_id` int(10) unsigned NOT NULL,
  `role_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `user_role` VALUES (1, 1);

CREATE TABLE `menu` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `pid` int(10) unsigned NOT NULL,
  `is_menu` tinyint(3) unsigned NOT NULL,
  `name` varchar(50) NOT NULL,
  `link` varchar(50) NOT NULL,
  `path` varchar(255) NOT NULL,
  `icon` varchar(255) NOT NULL,
  `sort` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `menu` VALUES (1, 0, 1, '用户管理', 'users', '/users', 'supervisor_account', 0);
INSERT INTO `menu` VALUES (2, 0, 1, '角色管理', 'roles', '/roles', 'person', 0);
INSERT INTO `menu` VALUES (3, 0, 1, '菜单管理', 'menus', '/menus', 'menu', 0);
INSERT INTO `menu` VALUES (4, 0, 0, '用户中心', '', '/auth/**', '', 0);

CREATE TABLE `role_menu` (
  `role_id` int(10) unsigned NOT NULL,
  `menu_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `role_menu` VALUES (1, 1);
INSERT INTO `role_menu` VALUES (1, 2);
