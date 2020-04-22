package com.memoforward.blog.controller;

import com.memoforward.blog.dto.BlogDto;
import com.memoforward.blog.enums.BlogError;
import com.memoforward.blog.exception.CustomizedBlogException;
import com.memoforward.blog.model.Blog;
import com.memoforward.blog.service.IBlogService;
import com.memoforward.blog.utils.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BlogController {

    @Autowired
    IBlogService blogService;

    @GetMapping("/{id}/blog")
    public String blog(@PathVariable("id") Long blogId,
                       Model model) {
        BlogDto blogDto = blogService.findBlogById(blogId);
        if(blogDto == null) throw new CustomizedBlogException(BlogError.BLOG_NOT_EXISTS);
        // markdown渲染
        Blog blog = blogDto.getBlog();
        String content = MarkdownUtils.markdownToHTMLExtensions(blog.getContent());
        blog.setContent(content);
        blogDto.setBlog(blog);
        model.addAttribute("blogDto", blogDto);
        return "blog";
    }
}
