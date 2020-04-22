CREATE TABLE `t_user`
(
    `id`            bigint       NOT NULL AUTO_INCREMENT,
    `nickname`      varchar(20)  NOT NULL,
    `username`      varchar(30)  NULL,
    `password`      varchar(255) NULL,
    `type`          int          NOT NULL,
    `avatar`        varchar(255) NULL,
    `create_time`   datetime     NOT NULL,
    `modified_time` datetime     NOT NULL,
    `email`         varchar(255) NULL,
    PRIMARY KEY (`id`)
);