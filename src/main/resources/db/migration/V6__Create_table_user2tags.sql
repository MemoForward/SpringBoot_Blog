CREATE TABLE `t_user_tags`
(
    `id`      bigint NOT NULL AUTO_INCREMENT,
    `user_id` bigint NOT NULL,
    `tag_id`  bigint NOT NULL,
    PRIMARY KEY (`id`)
);