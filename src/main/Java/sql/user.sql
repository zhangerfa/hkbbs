use friends;

drop table if exists user;
create table user(
    stu_id varchar(10) primary key not null comment '学号',
    username varchar(30) not null comment '用户名',
    password varchar(16) not null comment '密码',
    create_at timestamp DEFAULT current_timestamp not null comment '注册时间'
    )DEFAULT CHARSET=utf8;

insert into `user`(stu_id, username, password)
values ('M123456', '管理员', 123456);

drop table if exists card;
create table card(
    id smallint primary key auto_increment,
    stu_id varchar(10),
    title varchar(100) comment '卡片名',
    `content` text comment '卡片内容',
    create_at timestamp DEFAULT current_timestamp not null comment '发帖时间',
    foreign key (stu_id) references user(stu_id)
)DEFAULT CHARSET=utf8;