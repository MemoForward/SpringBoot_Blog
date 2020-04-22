CREATE TABLE `t_blog_type`
(
    `id`      bigint NOT NULL AUTO_INCREMENT,
    `blog_id` bigint NOT NULL,
    `type_id`  bigint NOT NULL,
    PRIMARY KEY (`id`)
);