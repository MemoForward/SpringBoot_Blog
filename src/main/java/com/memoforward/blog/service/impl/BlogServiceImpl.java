package com.memoforward.blog.service.impl;

import com.memoforward.blog.dto.BlogDto;
import com.memoforward.blog.dto.PageDto;
import com.memoforward.blog.mapper.*;
import com.memoforward.blog.model.*;
import com.memoforward.blog.service.IBlogService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogServiceImpl implements IBlogService {

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    TypeMapper typeMapper;

    @Autowired
    TagMapper tagMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    TagExtMapper tagExtMapper;

    @Autowired
    BlogExtMapper blogExtMapper;

    @Autowired
    BlogWithTagsExtMapper blogWithTagsExtMapper;

    @Autowired
    BlogWithTagsMapper blogWithTagsMapper;

    @Autowired
    BlogWithTypeMapper blogWithTypeMapper;

    @Override
    public PageDto<BlogDto> listBlogs(Integer pageNum, Integer pageLimit, String isPublished) {
        PageDto<BlogDto> pageDto = new PageDto<>();
        int totalNums = (int) blogMapper.countByExample(new BlogExample());
        pageDto.setTotalNums(totalNums);
        int _totalPage = totalNums / pageLimit;
        int totalPage = totalNums % pageLimit == 0 ? _totalPage : _totalPage + 1;
        pageDto.setTotalPage(totalPage);
        if (pageNum < 1) pageNum = 1;
        if (pageNum > totalPage) pageNum = totalPage;
        pageDto.setCurrentPage(pageNum);
        int offset = (pageNum - 1) * pageLimit;
        BlogExample blogExample = new BlogExample();
        blogExample.setOrderByClause("modified_time desc");
        if("on".equals(isPublished))
            blogExample.createCriteria().andIsPublishedEqualTo(isPublished);
        List<Blog> blogList = blogMapper.selectByExampleWithRowbounds(blogExample, new RowBounds(offset, pageLimit));
        List<BlogDto> blogDtoList = new ArrayList<>();
        for (Blog blog : blogList) {
            BlogDto blogDto = new BlogDto();
            blogDto.setBlog(blog);
            Type type = typeMapper.selectByPrimaryKey(blog.getTypeId());
            User user = userMapper.selectByPrimaryKey(blog.getUserId());
            List<Tag> tagList = tagExtMapper.listBlogTags(blog.getId());
            blogDto.setType(type);
            blogDto.setUser(user);
            blogDto.setTagList(tagList);
            blogDtoList.add(blogDto);
        }
        pageDto.setData(blogDtoList);
        return pageDto;
    }

    public PageDto<BlogDto> listSelectiveBlogs(String title, Integer pageNum, Integer pageLimit, Long typeId,
                                               String recommended) {
        PageDto<BlogDto> pageDto = new PageDto<>();
        BlogExample blogExample = new BlogExample();
        blogExample.setOrderByClause("modified_time desc");
        BlogExample.Criteria criteria = blogExample.createCriteria().andTitleLikeInsensitive("%" + title + "%");
        if (typeId != null) criteria.andTypeIdEqualTo(typeId);
        if (recommended != null && !"".equals(recommended)) criteria.andIsRecommendedEqualTo(recommended);
        int totalNums = (int) blogMapper.countByExample(blogExample);
        pageDto.setTotalNums(totalNums);
        int _totalPage = totalNums / pageLimit;
        int totalPage = totalNums % pageLimit == 0 ? _totalPage : _totalPage + 1;
        pageDto.setTotalPage(totalPage);
        if (pageNum < 1) pageNum = 1;
        if (pageNum > totalPage) pageNum = totalPage;
        pageDto.setCurrentPage(pageNum);
        int offset = (pageNum - 1) * pageLimit;
        List<Blog> blogList = blogMapper.selectByExampleWithRowbounds(blogExample, new RowBounds(offset, pageLimit));
        List<BlogDto> blogDtoList = new ArrayList<>();
        for (Blog blog : blogList) {
            BlogDto blogDto = new BlogDto();
            blogDto.setBlog(blog);
            Type type = typeMapper.selectByPrimaryKey(blog.getTypeId());
            User user = userMapper.selectByPrimaryKey(blog.getUserId());
            List<Tag> tagList = tagExtMapper.listBlogTags(blog.getId());
            blogDto.setType(type);
            blogDto.setUser(user);
            blogDto.setTagList(tagList);
            blogDtoList.add(blogDto);
        }
        pageDto.setData(blogDtoList);
        return pageDto;
    }

    @Override
    @Transactional
    public void removeBlogById(Long id) {
        // 我真的是傻逼，我这一个小项目，为什么不用外键！！！！！
        blogMapper.deleteByPrimaryKey(id);
        BlogWithTagsExample blogWithTagsExample = new BlogWithTagsExample();
        blogWithTagsExample.createCriteria().andBlogIdEqualTo(id);
        blogWithTagsMapper.deleteByExample(blogWithTagsExample);
        BlogWithTypeExample blogWithTypeExample = new BlogWithTypeExample();
        blogWithTypeExample.createCriteria().andBlogIdEqualTo(id);
        blogWithTypeMapper.deleteByExample(blogWithTypeExample);
    }


    @Override
    @Transactional
    public BlogDto findBlogById(Long blogId) {
        BlogDto blogDto = new BlogDto();
        Blog blog = blogMapper.selectByPrimaryKey(blogId);
        Type type = typeMapper.selectByPrimaryKey(blog.getTypeId());
        User user = userMapper.selectByPrimaryKey(blog.getUserId());
        List<Tag> tagList = tagExtMapper.listBlogTags(blog.getId());
        blogDto.setBlog(blog);
        blogDto.setType(type);
        blogDto.setUser(user);
        blogDto.setTagList(tagList);
        return blogDto;
    }


    @Override
    @Transactional
    public void saveBlog(Blog blog) {
        blogMapper.insert(blog);
        long blogId = blogExtMapper.findLastPrimaryKey();
//        System.out.println(blogId);
//        System.out.println(blog.getTagIds());
        BlogWithType blogWithType = new BlogWithType();
        blogWithType.setTypeId(blog.getTypeId());
        blogWithType.setBlogId(blogId);
        blogWithTypeMapper.insert(blogWithType);
        String[] tags = blog.getTagIds().split(",");
        if (tags.length > 0) {
            List<BlogWithTags> blogWithTagsList = new ArrayList<>();
            for (String tag : tags) {
                BlogWithTags blogWithTags = new BlogWithTags();
                blogWithTags.setBlogId(blogId);
                blogWithTags.setTagId(Long.parseLong(tag));
                blogWithTagsList.add(blogWithTags);
            }
            blogWithTagsExtMapper.insertBatch(blogWithTagsList);
        }
    }

    @Override
    public BlogDto findBlogByTitle(String title) {
        BlogExample blogExample = new BlogExample();
        blogExample.createCriteria().andTitleEqualTo(title);
        List<Blog> blogList = blogMapper.selectByExample(blogExample);
        if (blogList.size() == 0) return null;
        else{
            Blog blog =  blogList.get(0);
            BlogDto blogDto = new BlogDto();
            Type type = typeMapper.selectByPrimaryKey(blog.getTypeId());
            User user = userMapper.selectByPrimaryKey(blog.getUserId());
            List<Tag> tagList = tagExtMapper.listBlogTags(blog.getId());
            blogDto.setBlog(blog);
            blogDto.setType(type);
            blogDto.setUser(user);
            blogDto.setTagList(tagList);
            return blogDto;
        }
    }

    @Override
    @Transactional
    public void updateBlog(Blog blog) {
        String[] tags = blog.getTagIds().split(",");
        blogMapper.updateByPrimaryKeyWithBLOBs(blog);
        // 需要同步更新中间表
        // t_blog_type
        BlogWithTypeExample blogWithTypeExample = new BlogWithTypeExample();
        blogWithTypeExample.createCriteria().andBlogIdEqualTo(blog.getId());
        blogWithTypeMapper.deleteByExample(blogWithTypeExample);
        BlogWithType blogWithType = new BlogWithType();
        blogWithType.setTypeId(blog.getTypeId());
        blogWithType.setBlogId(blog.getId());
        blogWithTypeMapper.insert(blogWithType);
        // t_blog_tag
        BlogWithTagsExample blogWithTagsExample = new BlogWithTagsExample();
        blogWithTagsExample.createCriteria().andBlogIdEqualTo(blog.getId());
        blogWithTagsMapper.deleteByExample(blogWithTagsExample);
        List<BlogWithTags> tagsToAdd = new ArrayList<>();
        for (String tag : tags) {
            BlogWithTags blogWithTags = new BlogWithTags();
            blogWithTags.setBlogId(blog.getId());
            blogWithTags.setTagId(Long.parseLong(tag));
            tagsToAdd.add(blogWithTags);
        }
        blogWithTagsExtMapper.insertBatch(tagsToAdd);
    }


    @Override
    public List<Blog> listHotBlogs(Integer pageLimit) {
        BlogExample blogExample = new BlogExample();
        blogExample.setOrderByClause("view_count desc");
        return blogMapper.selectByExampleWithRowbounds(blogExample, new RowBounds(0,pageLimit));
    }
}

