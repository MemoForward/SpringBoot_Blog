package com.memoforward.blog.mapper;

import com.memoforward.blog.model.BlogWithTags;
import com.memoforward.blog.model.BlogWithTagsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface BlogWithTagsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_blog_tags
     *
     * @mbg.generated Thu Apr 16 14:53:23 CST 2020
     */
    long countByExample(BlogWithTagsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_blog_tags
     *
     * @mbg.generated Thu Apr 16 14:53:23 CST 2020
     */
    int deleteByExample(BlogWithTagsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_blog_tags
     *
     * @mbg.generated Thu Apr 16 14:53:23 CST 2020
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_blog_tags
     *
     * @mbg.generated Thu Apr 16 14:53:23 CST 2020
     */
    int insert(BlogWithTags record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_blog_tags
     *
     * @mbg.generated Thu Apr 16 14:53:23 CST 2020
     */
    int insertSelective(BlogWithTags record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_blog_tags
     *
     * @mbg.generated Thu Apr 16 14:53:23 CST 2020
     */
    List<BlogWithTags> selectByExampleWithRowbounds(BlogWithTagsExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_blog_tags
     *
     * @mbg.generated Thu Apr 16 14:53:23 CST 2020
     */
    List<BlogWithTags> selectByExample(BlogWithTagsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_blog_tags
     *
     * @mbg.generated Thu Apr 16 14:53:23 CST 2020
     */
    BlogWithTags selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_blog_tags
     *
     * @mbg.generated Thu Apr 16 14:53:23 CST 2020
     */
    int updateByExampleSelective(@Param("record") BlogWithTags record, @Param("example") BlogWithTagsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_blog_tags
     *
     * @mbg.generated Thu Apr 16 14:53:23 CST 2020
     */
    int updateByExample(@Param("record") BlogWithTags record, @Param("example") BlogWithTagsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_blog_tags
     *
     * @mbg.generated Thu Apr 16 14:53:23 CST 2020
     */
    int updateByPrimaryKeySelective(BlogWithTags record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_blog_tags
     *
     * @mbg.generated Thu Apr 16 14:53:23 CST 2020
     */
    int updateByPrimaryKey(BlogWithTags record);
}