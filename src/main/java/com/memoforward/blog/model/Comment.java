package com.memoforward.blog.model;

public class Comment {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_comment.id
     *
     * @mbg.generated Thu Apr 16 14:53:23 CST 2020
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_comment.user_id
     *
     * @mbg.generated Thu Apr 16 14:53:23 CST 2020
     */
    private Long userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_comment.content
     *
     * @mbg.generated Thu Apr 16 14:53:23 CST 2020
     */
    private String content;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_comment.create_time
     *
     * @mbg.generated Thu Apr 16 14:53:23 CST 2020
     */
    private Long createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_comment.id
     *
     * @return the value of t_comment.id
     *
     * @mbg.generated Thu Apr 16 14:53:23 CST 2020
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_comment.id
     *
     * @param id the value for t_comment.id
     *
     * @mbg.generated Thu Apr 16 14:53:23 CST 2020
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_comment.user_id
     *
     * @return the value of t_comment.user_id
     *
     * @mbg.generated Thu Apr 16 14:53:23 CST 2020
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_comment.user_id
     *
     * @param userId the value for t_comment.user_id
     *
     * @mbg.generated Thu Apr 16 14:53:23 CST 2020
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_comment.content
     *
     * @return the value of t_comment.content
     *
     * @mbg.generated Thu Apr 16 14:53:23 CST 2020
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_comment.content
     *
     * @param content the value for t_comment.content
     *
     * @mbg.generated Thu Apr 16 14:53:23 CST 2020
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_comment.create_time
     *
     * @return the value of t_comment.create_time
     *
     * @mbg.generated Thu Apr 16 14:53:23 CST 2020
     */
    public Long getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_comment.create_time
     *
     * @param createTime the value for t_comment.create_time
     *
     * @mbg.generated Thu Apr 16 14:53:23 CST 2020
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}