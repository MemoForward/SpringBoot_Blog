CREATE TABLE `t_blog`
(
    `id`                bigint   NOT NULL AUTO_INCREMENT,
    `title`             varchar(256)  NOT NULL,
    `content`           text     NOT NULL,
    `pic_url`           varchar(256),
    `mark`              varchar(20)  NOT NULL,
    `view_count`        bigint   NOT NULL DEFAULT 0,
    `show_appreciation` tinyint  NOT NULL DEFAULT 1,
    `show_comment`      tinyint  NOT NULL DEFAULT 1,
    `show_statement`    tinyint  NOT NULL DEFAULT 1,
    `is_published`      tinyint  NOT NULL DEFAULT 0,
    `create_time`       datetime NOT NULL,
    `modified_time`     datetime NOT NULL,
    `type_id`           bigint   NOT NULL,
    `user_id`           bigint   NOT NULL,
    PRIMARY KEY (`id`)
);