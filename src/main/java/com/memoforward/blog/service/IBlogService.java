package com.memoforward.blog.service;

import com.memoforward.blog.dto.BlogDto;
import com.memoforward.blog.dto.PageDto;
import com.memoforward.blog.model.Blog;
import org.springframework.lang.Nullable;

import java.util.List;

public interface IBlogService {
    PageDto<BlogDto> listBlogs(Integer pageNum, Integer pageLimit, @Nullable String isPublished);

    PageDto<BlogDto> listSelectiveBlogs(String title, Integer pageNum, Integer pageLimit,
                                        @Nullable Long typeId, @Nullable String recommended);

    void removeBlogById(Long id);

    BlogDto findBlogById(Long blogId);

    void saveBlog(Blog blog);

    BlogDto findBlogByTitle(String title);

    void updateBlog(Blog blog);

    List<Blog> listHotBlogs(Integer pageLimit);
}
