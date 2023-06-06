create database if not exists friends;
use friends;
CREATE TABLE `card` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `about_me` text NOT NULL COMMENT '关于我',
                        `goal` int NOT NULL COMMENT '交友目标',
                        `poster_id` varchar(10) NOT NULL COMMENT '发布者学号',
                        `expect` text NOT NULL COMMENT '征友期望',
                        `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
                        PRIMARY KEY (`id`),
                        KEY `card_user_null_fk` (`poster_id`),
                        CONSTRAINT `card_user_null_fk` FOREIGN KEY (`poster_id`) REFERENCES `user` (`stu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3 COMMENT='卡片墙卡片'

CREATE TABLE `chat` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `user1` varchar(10) NOT NULL COMMENT '聊天中一方的学号',
                        `user2` varchar(10) NOT NULL COMMENT '聊天中另一方的学号',
                        PRIMARY KEY (`id`),
                        KEY `chat_user_null_fk` (`user1`),
                        KEY `chat_user_stu_id_fk` (`user2`),
                        CONSTRAINT `chat_user_null_fk` FOREIGN KEY (`user1`) REFERENCES `user` (`stu_id`),
                        CONSTRAINT `chat_user_stu_id_fk` FOREIGN KEY (`user2`) REFERENCES `user` (`stu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COMMENT='聊天表（一个chat有若干message组成）'

CREATE TABLE `comment` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `poster_id` varchar(10) NOT NULL COMMENT '评论人学号',
                           `entity_type` int NOT NULL DEFAULT '1' COMMENT '被评论对象的类型：1-card，2-comment',
                           `entity_id` int NOT NULL COMMENT '被评论对象的id',
                           `content` text NOT NULL COMMENT '回帖内容',
                           `create_time` timestamp NOT NULL COMMENT '回帖时间',
                           `owner_type` int DEFAULT NULL COMMENT '评论所属实体的类型',
                           PRIMARY KEY (`id`),
                           KEY `stu_id` (`poster_id`),
                           CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`poster_id`) REFERENCES `user` (`stu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb3 COMMENT='评论表'

CREATE TABLE `hole_nickname` (
                                 `hole_id` int NOT NULL COMMENT '树洞帖子的id',
                                 `poster_id` varchar(10) NOT NULL COMMENT '发帖人id',
                                 `nickname` varchar(7) NOT NULL COMMENT '由两个数字组成的字符，表示在两个字符集中的字符索引，由两个字符集中对应的两个字符拼接为昵称',
                                 KEY `stu_id` (`poster_id`),
                                 KEY `hole_nickname_post_null_fk` (`hole_id`),
                                 CONSTRAINT `hole_nickname_ibfk_1` FOREIGN KEY (`poster_id`) REFERENCES `user` (`stu_id`),
                                 CONSTRAINT `hole_nickname_post_null_fk` FOREIGN KEY (`hole_id`) REFERENCES `post` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='存储随机昵称，每个树洞帖子中的一个用户将拥有一个唯一的随机昵称'

CREATE TABLE `image` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `entity_id` int NOT NULL COMMENT '图片所属的实体id',
                         `url` varchar(120) NOT NULL COMMENT '图片url',
                         `entity_type` int DEFAULT NULL COMMENT '图片所属实体的类型，帖子和树洞均视作帖子',
                         PRIMARY KEY (`id`),
                         KEY `image_post_null_fk` (`entity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='存储帖子中的图片'

CREATE TABLE `login_ticket` (
                                `stu_id` varchar(10) NOT NULL COMMENT '学号',
                                `ticket` char(32) NOT NULL COMMENT '登录凭证（唯一标识符）',
                                `status` int NOT NULL COMMENT '登录凭证状态： 1 可用， 0 不可用',
                                `expired` timestamp NOT NULL COMMENT '登录凭证过期时间',
                                PRIMARY KEY (`stu_id`),
                                CONSTRAINT `login_ticket_ibfk_1` FOREIGN KEY (`stu_id`) REFERENCES `user` (`stu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='登录凭证表'

CREATE TABLE `manager` (
                           `stu_id` varchar(10) NOT NULL COMMENT '学号',
                           `level` int NOT NULL DEFAULT '0' COMMENT '管理员权限',
                           PRIMARY KEY (`stu_id`),
                           CONSTRAINT `manager_user_null_fk` FOREIGN KEY (`stu_id`) REFERENCES `user` (`stu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='管理员表'

CREATE TABLE `message` (
                           `poster_id` varchar(10) NOT NULL COMMENT '消息发布者学号',
                           `id` int NOT NULL AUTO_INCREMENT,
                           `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '消息内容：当消息为文字时存储文字，当消息为图片时存储图片url',
                           `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '信息发布时间',
                           `status` int NOT NULL DEFAULT '0' COMMENT '信息状态：1-已读，0-未读',
                           `chat_id` int NOT NULL COMMENT '消息所属的聊天id',
                           `type` int DEFAULT NULL COMMENT '消息类型：0-文字，1-图片',
                           PRIMARY KEY (`id`),
                           KEY `message_user_null_fk` (`poster_id`),
                           KEY `message_chat_null_fk` (`chat_id`),
                           CONSTRAINT `message_chat_null_fk` FOREIGN KEY (`chat_id`) REFERENCES `chat` (`id`),
                           CONSTRAINT `message_user_null_fk` FOREIGN KEY (`poster_id`) REFERENCES `user` (`stu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3 COMMENT='站内私信表'

CREATE TABLE `notice` (
                          `id` int NOT NULL AUTO_INCREMENT,
                          `receiving_user_id` varchar(10) NOT NULL COMMENT '接收通知用户的id',
                          `entity_type` int NOT NULL COMMENT '动作指向实体的实体类型',
                          `entity_id` int DEFAULT NULL COMMENT '动作指向实体的id',
                          `action_user_id` varchar(10) NOT NULL COMMENT '做出动作的用户的id',
                          `action_type` int NOT NULL COMMENT '动作类型',
                          `status` int NOT NULL DEFAULT '0' COMMENT '通知状态：0-未读，1-已读，2-删除',
                          `action_id` int DEFAULT NULL COMMENT '动作id',
                          `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '通知创建时间',
                          `owner_type` int DEFAULT NULL COMMENT '动作发生所属实体的类型',
                          PRIMARY KEY (`id`),
                          KEY `action_user_id` (`action_user_id`),
                          KEY `receiving_user_id` (`receiving_user_id`),
                          CONSTRAINT `notice_ibfk_1` FOREIGN KEY (`receiving_user_id`) REFERENCES `user` (`stu_id`),
                          CONSTRAINT `notice_ibfk_2` FOREIGN KEY (`action_user_id`) REFERENCES `user` (`stu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb3 COMMENT='通知数据表：通知谁，通知内容（哪个实体对另一个实体做了什么动作）'

CREATE TABLE `post` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `poster_id` varchar(10) DEFAULT NULL,
                        `title` varchar(100) DEFAULT NULL COMMENT '卡片名',
                        `content` text COMMENT '卡片内容',
                        `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发帖时间',
                        `comment_num` smallint DEFAULT '0' COMMENT '评论数量',
                        `hot` smallint DEFAULT '0' COMMENT '帖子 热度',
                        `type` int DEFAULT NULL COMMENT '帖子类型， 1-card，2-hole',
                        PRIMARY KEY (`id`),
                        KEY `stu_id` (`poster_id`),
                        CONSTRAINT `post_ibfk_1` FOREIGN KEY (`poster_id`) REFERENCES `user` (`stu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb3 COMMENT='帖子表'

CREATE TABLE `user` (
                        `stu_id` varchar(10) NOT NULL COMMENT '学号',
                        `username` varchar(30) NOT NULL COMMENT '用户名',
                        `password` varchar(32) NOT NULL COMMENT '密码',
                        `create_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
                        `header_url` varchar(100) DEFAULT NULL COMMENT '用户头像地址',
                        `salt` char(6) NOT NULL COMMENT 'password中存储用户密码 + salt经过MD5算法加密后的字符串',
                        `gender` int DEFAULT NULL COMMENT '性别: 0-男 1-女',
                        PRIMARY KEY (`stu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3

