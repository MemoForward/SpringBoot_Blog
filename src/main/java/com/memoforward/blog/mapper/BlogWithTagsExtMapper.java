package com.memoforward.blog.mapper;

import com.memoforward.blog.model.BlogWithTags;
import com.memoforward.blog.model.BlogWithTagsExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface BlogWithTagsExtMapper {

    void insertBatch(@Param("list") List<BlogWithTags> list);
}