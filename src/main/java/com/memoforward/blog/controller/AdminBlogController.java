package com.memoforward.blog.controller;

import com.memoforward.blog.dto.BlogDto;
import com.memoforward.blog.dto.PageDto;
import com.memoforward.blog.dto.ResultDto;
import com.memoforward.blog.enums.BlogError;
import com.memoforward.blog.exception.CustomizedBlogException;
import com.memoforward.blog.model.Blog;
import com.memoforward.blog.model.Tag;
import com.memoforward.blog.model.Type;
import com.memoforward.blog.model.User;
import com.memoforward.blog.service.IBlogService;
import com.memoforward.blog.service.ITagService;
import com.memoforward.blog.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminBlogController {

    @Autowired
    IBlogService blogService;

    @Autowired
    ITagService tagService;

    @Autowired
    ITypeService typeService;

    @Value("${page.limit.admin.blog}")
    private Integer PAGELIMITBLOG;

    @GetMapping("/blogs")
    public String blog(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                       Model model) {
        PageDto<BlogDto> blogPage = blogService.listBlogs(pageNum, PAGELIMITBLOG, null);
        List<Type> typeList = typeService.listAllTypes();
        model.addAttribute("typeList", typeList);
        model.addAttribute("blogPage", blogPage);
        return "admin/blogs";
    }


    @PostMapping("/searchBlog")
    public String searchBlog(@RequestParam("title") String title,
                             @RequestParam(value = "typeId", defaultValue = "-1") Long typeId,
                             @RequestParam(value = "recommended", required = false) String recommended,
                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             Model model) {
        if ("".equals(title) || title == null) {
            model.addAttribute("error", "搜索失败！");
        } else {
//            System.out.println(mark);
            model.addAttribute("title", title);
            model.addAttribute("typeId", typeId);
            model.addAttribute("recommended", recommended);
            if (typeId == -1) typeId = null;
            PageDto<BlogDto> blogPage = blogService.listSelectiveBlogs(title, pageNum, PAGELIMITBLOG, typeId, recommended);
//            if (blogPage.isEmpty()) model.addAttribute("error", "没有搜索到任何相关内容！");
            List<Type> typeList = typeService.listAllTypes();
            model.addAttribute("typeList", typeList);
            model.addAttribute("blogPage", blogPage);
        }
        return "admin/search_blogs";
    }

    @GetMapping("/{id}/deleteBlog")
    public String deleteBlog(@PathVariable("id") Long id) {
        blogService.removeBlogById(id);
        return "redirect:/admin/blogs";
    }

    @GetMapping("/publish")
    public String publish(Model model,
                          HttpServletRequest request) {
        // 这个页面尤其重要，因此再做一次验证
        User user = (User) request.getSession().getAttribute("user");
        if (user == null || user.getType() != 0)
            throw new CustomizedBlogException(BlogError.USER_PERMISSION_DENIED);
        List<Type> typeList = typeService.listAllTypes();
        List<Tag> tagList = tagService.listAllTags();
        model.addAttribute("typeList", typeList);
        model.addAttribute("tagList", tagList);
        model.addAttribute("blogDto", new BlogDto(1));
        return "admin/publish";
    }

    @PostMapping("/doPublish")
    @ResponseBody
    public ResultDto doPublish(@RequestBody Blog blog,
                               HttpServletRequest request) {
//        System.out.println(blog);
        User user = (User) request.getSession().getAttribute("user");
        if(user == null || user.getType() != 0)
            throw new CustomizedBlogException(BlogError.USER_PERMISSION_DENIED);
        Long userId = user.getId();
        String redirectUrl = "/admin/blogs";
        // 不管是编辑还是新增， 不允许标题重复
        BlogDto _blog = blogService.findBlogByTitle(blog.getTitle());
        if(_blog != null){
            if(blog.getId() == null || !blog.getId().equals(_blog.getBlog().getId())){
                return ResultDto.errorOf(BlogError.BLOG_TITLE_EXISTS);
            }
        }
        if (blog.getId() != null) {
            // 编辑
            if (userId != blog.getUserId())
                throw new CustomizedBlogException(BlogError.USER_PERMISSION_DENIED);
            blog.setModifiedTime(System.currentTimeMillis());
//            System.out.println(blog);
            blogService.updateBlog(blog);
        } else {
            //新增
            blog.setUserId(userId);
            blog.setCreateTime(System.currentTimeMillis());
            blog.setModifiedTime(blog.getCreateTime());
            blog.setViewCount(0l);
            if (blog.getPicUrl() == null || "".equals(blog.getPicUrl()))
                blog.setPicUrl("/images/blogPic.jpg");
            blogService.saveBlog(blog);
        }
        return ResultDto.successOf(redirectUrl);
    }


    @GetMapping("/{blogId}/editBlog")
    public String editBlog(@PathVariable("blogId") Long blogId,
                           Model model) {
        BlogDto blogDto = blogService.findBlogById(blogId);
        String tags = blogDto.getBlog().getTagIds();
        model.addAttribute("blogTags", tags);
        List<Type> typeList = typeService.listAllTypes();
        List<Tag> tagList = tagService.listAllTags();
        model.addAttribute("typeList", typeList);
        model.addAttribute("tagList", tagList);
        model.addAttribute("blogDto", blogDto);
        return "admin/publish";
    }
}
