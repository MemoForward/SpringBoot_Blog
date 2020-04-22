package com.memoforward.blog;

import com.memoforward.blog.dto.BlogDto;
import com.memoforward.blog.dto.PageDto;
import com.memoforward.blog.dto.TagDto;
import com.memoforward.blog.dto.TypeDto;
import com.memoforward.blog.mapper.*;
import com.memoforward.blog.model.BlogExample;
import com.memoforward.blog.model.BlogWithTags;
import com.memoforward.blog.model.BlogWithTagsExample;
import com.memoforward.blog.model.Tag;
import com.memoforward.blog.service.IBlogService;
import com.memoforward.blog.service.IUserService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@MapperScan("com.memoforward.blog.mapper")
class BlogApplicationTests {

    @Autowired
    IUserService userService;

    @Autowired
    TagExtMapper tagExtMapper;

    @Autowired
    TypeExtMapper typeExtMapper;

    @Autowired
    BlogExtMapper blogExtMapper;

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    IBlogService blogService;

    @Autowired
    BlogWithTagsExtMapper blogWithTagsExtMapper;

    @Autowired
    BlogWithTagsMapper blogWithTagsMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void testDate() {
        System.out.println(new Date());
    }

    @Test
    void testTagExtMapper01() {
        List<Tag> tagList = tagExtMapper.listBlogTags(1l);
        for (Tag tag : tagList) {
            System.out.println(tag.getName());
        }
    }

    @Test
    void testTagExtMapper02() {
        List<TagDto> tagList = tagExtMapper.listTopTags(10);
        for(TagDto tag: tagList){
            System.out.println(tag);
        }
    }

    @Test
    void testTypeExtMapper01() {
        List<TypeDto> typeList = typeExtMapper.listTopTypes(10);
        for (TypeDto type : typeList) {
            System.out.println(type);
        }
    }


    @Test
    void testBlogSearchAPI01(){
        String title = "%est0%";
        String mark = "original";
        String isRecommend = "on";
        BlogExample blogExample = new BlogExample();
        blogExample.createCriteria().
                andTitleLike(title).
                andMarkEqualTo(mark).
                andIsRecommendedEqualTo(isRecommend);
        long totalNum = blogMapper.countByExample(blogExample);
        System.out.println(totalNum);
    }

    @Test
    void testBlogSearchAPI02(){
        String title = "%est0%";
        PageDto<BlogDto> pageDto = blogService.listSelectiveBlogs(title, 0, 5, null, null);
        System.out.println(pageDto);
    }



    @Test
    void testBlogWithMapBatchInsert(){
        ArrayList<BlogWithTags> blogWithTagsList = new ArrayList<>();
        for(int i = 0, j = 8; i < 3 && j > 5; i++, j--){
            BlogWithTags blogWithTags = new BlogWithTags();
            blogWithTags.setBlogId((long) i);
            blogWithTags.setTagId((long) j);
            blogWithTagsList.add(blogWithTags);
        }
        blogWithTagsExtMapper.insertBatch(blogWithTagsList);
    }

    @Test
    void testBlogExtMapper(){
        Long key = blogExtMapper.findLastPrimaryKey();
        System.out.println(key);
    }

    @Test
    void deleteData(){
        blogWithTagsMapper.deleteByExample(new BlogWithTagsExample());
    }
}
