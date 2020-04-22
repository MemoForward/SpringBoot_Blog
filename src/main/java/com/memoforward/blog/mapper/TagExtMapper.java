package com.memoforward.blog.mapper;

import com.memoforward.blog.dto.TagDto;
import com.memoforward.blog.model.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagExtMapper {
    List<Tag> listBlogTags(@Param("blogId") Long blogId);

    List<TagDto> listTopTags(@Param("limit") Integer limit);

}
