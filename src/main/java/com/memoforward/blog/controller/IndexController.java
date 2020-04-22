package com.memoforward.blog.controller;

import com.memoforward.blog.dto.BlogDto;
import com.memoforward.blog.dto.PageDto;
import com.memoforward.blog.dto.TagDto;
import com.memoforward.blog.dto.TypeDto;
import com.memoforward.blog.mapper.BlogWithTagsExtMapper;
import com.memoforward.blog.mapper.BlogWithTypeExtMapper;
import com.memoforward.blog.mapper.TagExtMapper;
import com.memoforward.blog.mapper.TypeExtMapper;
import com.memoforward.blog.model.Blog;
import com.memoforward.blog.service.IBlogService;
import com.memoforward.blog.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;


@Controller
public class IndexController {

    @Autowired
    IBlogService blogService;

    @Autowired
    TagExtMapper tagExtMapper;

    @Autowired
    TypeExtMapper typeExtMapper;

    @Autowired
    BlogWithTagsExtMapper blogWithTagsExtMapper;


    @Value("${page.limit.index}")
    private Integer PAGEINDEXLIMIT;

    @Value("${index.limit.type}")
    private Integer TYPECOUNTS;

    @Value("${index.limit.tag}")
    private Integer TAGCOUNTS;

    @Value("${index.limit.hotBlog}")
    private Integer HOTBLOGCOUNTS;


    @GetMapping("/")
    public String index(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                        Model model) {
        // 博客
        String isPublished = "on";
        PageDto<BlogDto> blogPage = blogService.listBlogs(pageNum, PAGEINDEXLIMIT, isPublished);
        model.addAttribute("blogPage", blogPage);
        // 常用分类标签
        List<TypeDto> types = typeExtMapper.listTopTypes(TYPECOUNTS);
        List<TagDto> tags = tagExtMapper.listTopTags(TAGCOUNTS);
        model.addAttribute("types", types);
        model.addAttribute("tags", tags);
        // 最热推荐
        List<Blog> hotBlogs = blogService.listHotBlogs(HOTBLOGCOUNTS);
        model.addAttribute("hotBlogs", hotBlogs);
        return "index";
    }
}
