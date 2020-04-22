ALTER TABLE `t_blog`
    MODIFY COLUMN `create_time` bigint NOT NULL default 0,
    MODIFY COLUMN `modified_time` bigint NOT NULL default 0;

ALTER TABLE `t_comment`
    MODIFY COLUMN `create_time` bigint NOT NULL DEFAULT 0;

ALTER TABLE `t_user`
    MODIFY COLUMN `create_time` bigint NOT NULL default 0,
    MODIFY COLUMN `modified_time` bigint NOT NULL default 0;
