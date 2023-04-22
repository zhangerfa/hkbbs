create database if not exists friends;
use friends;

create table user
(
    stu_id     varchar(10)                         not null comment '学号'
        primary key,
    username   varchar(30)                         not null comment '用户名',
    password   varchar(32)                         not null comment '密码',
    create_at  timestamp default CURRENT_TIMESTAMP not null comment '注册时间',
    header_url varchar(100)                        null comment '用户头像地址',
    salt       char(6)                             not null comment 'password中存储用户密码 + salt经过MD5算法加密后的字符串',
    gender     int                                 null comment '性别: 0-男 1-女'
)
    charset = utf8mb3;

create table if not exists post
(
    id          int auto_increment
        primary key,
    poster_id   varchar(10)                         null,
    title       varchar(100)                        null comment '卡片名',
    content     text                                null comment '卡片内容',
    create_at   timestamp default CURRENT_TIMESTAMP not null comment '发帖时间',
    comment_num smallint  default 0                 null comment '评论数量',
    hot         smallint  default 0                 null comment '帖子 热度',
    type        int                                 null comment '帖子类型， 1-card，2-hole',
    constraint post_ibfk_1
        foreign key (poster_id) references user (stu_id)
)
    comment '帖子表' charset = utf8mb3;

create index stu_id
    on post (poster_id);

create table if not exists comment
(
    id          int auto_increment
        primary key,
    poster_id   varchar(10)   not null comment '评论人学号',
    entity_type int default 1 not null comment '被评论对象的类型：1-card，2-comment',
    entity_id   int           not null comment '被评论对象的id',
    content     text          not null comment '回帖内容',
    create_time timestamp     not null comment '回帖时间',
    constraint comment_ibfk_1
        foreign key (poster_id) references user (stu_id)
)
    comment '评论表' charset = utf8mb3;

create index stu_id
    on comment (poster_id);

create table if not exists hole_nickname
(
    hole_id   int         not null comment '树洞帖子的id',
    poster_id varchar(10) not null comment '发帖人id',
    nickname  varchar(7)  not null comment '由两个数字组成的字符，表示在两个字符集中的字符索引，由两个字符集中对应的两个字符拼接为昵称',
    constraint hole_nickname_ibfk_1
        foreign key (poster_id) references user (stu_id),
    constraint hole_nickname_post_null_fk
        foreign key (hole_id) references post (id)
)
    comment '存储随机昵称，每个树洞帖子中的一个用户将拥有一个唯一的随机昵称' charset = utf8mb3;

create index stu_id
    on hole_nickname (poster_id);

create table if not exists loggin_ticket
(
    stu_id  varchar(10) not null comment '学号'
        primary key,
    ticket  char(32)    not null comment '登录凭证（唯一标识符）',
    status  int         not null comment '登录凭证状态： 1 可用， 0 不可用',
    expired timestamp   not null comment '登录凭证过期时间',
    constraint loggin_ticket_ibfk_1
        foreign key (stu_id) references friends.user (stu_id)
)
    comment '登录凭证表' charset = utf8mb3;

create table if not exists notice
(
    id                int auto_increment
        primary key,
    receiving_user_id varchar(10)   not null comment '接收通知用户的id',
    entity_type       int           not null comment '动作指向实体的实体类型',
    entity_id         int           null comment '动作指向实体的id',
    action_user_id    varchar(10)   not null comment '做出动作的用户的id',
    action_type       int           not null comment '动作类型',
    comment_id        int           null comment '当动作为评论时，该列存储评论内容的id',
    entity_owner_id   int           null comment '动作指向实体属于（是）哪个帖子，当动作为关注时为空',
    status            int default 0 not null comment '通知状态：0-未读，1-已读，2-删除',
    constraint notice_ibfk_1
        foreign key (receiving_user_id) references friends.user (stu_id),
    constraint notice_ibfk_2
        foreign key (action_user_id) references friends.user (stu_id)
)
    comment '通知数据表：通知谁，通知内容（哪个实体对另一个实体做了什么动作）' charset = utf8mb3;

create index action_user_id
    on friends.notice (action_user_id);

create index notice_comment_null_fk
    on friends.notice (comment_id);

create index receiving_user_id
    on friends.notice (receiving_user_id);

create table if not exists image
(
    id          int auto_increment
        primary key,
    entity_id   int          not null comment '图片所属的实体id',
    url         varchar(120) not null comment '图片url',
    entity_type int          null comment '图片所属实体的类型',
)
    comment '存储帖子中的图片';


create table if not exists card
(
    id          int auto_increment
        primary key,
    about_me    text                                not null comment '关于我',
    goal        int                                 not null comment '交友目标',
    poster_id     varchar(10)                         not null comment '发布者学号',
    expect      text                                not null comment '征友期望',
    create_time timestamp default CURRENT_TIMESTAMP null comment '发布时间',
    constraint card_user_null_fk
        foreign key (post_id) references user (stu_id)
)
    comment '卡片墙卡片' charset = utf8mb3;

create table chat
(
    id    int auto_increment
        primary key,
    user1 varchar(10) not null comment '聊天中一方的学号',
    user2 varchar(10) not null comment '聊天中另一方的学号',
    constraint chat_user_null_fk
        foreign key (user1) references user (stu_id),
    constraint chat_user_stu_id_fk
        foreign key (user2) references user (stu_id)
)
    comment '聊天表（一个chat有若干message组成）' charset = utf8mb3;



create table if not exists message
(
    poster_id   varchar(10)                         not null comment '消息发布者学号',
    id          int auto_increment
        primary key,
    content     text charset utf8mb4                null comment '消息内容',
    create_time timestamp default CURRENT_TIMESTAMP not null comment '信息发布时间',
    status      int       default 0                 not null comment '信息状态：1-已读，0-未读',
    chat_id     int                                 not null comment '消息所属的聊天id',
    constraint message_chat_null_fk
        foreign key (chat_id) references chat (id),
    constraint message_user_null_fk
        foreign key (poster_id) references user (stu_id)
)
    comment '站内私信表' charset = utf8mb3;



