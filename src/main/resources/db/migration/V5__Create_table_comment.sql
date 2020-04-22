CREATE TABLE `t_comment`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT,
    `user_id`     bigint       NOT NULL,
    `content`     varchar(255) NOT NULL,
    `create_time` datetime     NOT NULL,
    PRIMARY KEY (`id`)
);