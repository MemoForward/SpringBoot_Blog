package com.memoforward.blog.service.impl;

import com.memoforward.blog.dto.PageDto;
import com.memoforward.blog.mapper.BlogWithTagsMapper;
import com.memoforward.blog.mapper.TagMapper;
import com.memoforward.blog.model.BlogWithTagsExample;
import com.memoforward.blog.model.Tag;
import com.memoforward.blog.model.TagExample;
import com.memoforward.blog.service.ITagService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagServiceImpl implements ITagService {

    @Autowired
    TagMapper tagMapper;

    @Autowired
    BlogWithTagsMapper blogWithTagsMapper;

    @Override
    public PageDto<Tag> listTags(Integer pageNum, Integer pageLimit) {
        PageDto<Tag> pageDto = new PageDto<>();
        int totalNums = (int) tagMapper.countByExample(new TagExample());
        pageDto.setTotalNums(totalNums);
        int _totalPage = totalNums / pageLimit;
        int totalPage = totalNums % pageLimit == 0 ? _totalPage : _totalPage + 1;
        pageDto.setTotalPage(totalPage);
        if (pageNum < 1) pageNum = 1;
        if (pageNum > totalPage) pageNum = totalPage;
        pageDto.setCurrentPage(pageNum);
        int offset = (pageNum - 1) * pageLimit;
        List<Tag> tagList = tagMapper.selectByExampleWithRowbounds(new TagExample(), new RowBounds(offset, pageLimit));
        pageDto.setData(tagList);
        return pageDto;
    }

    @Override
    public Tag findTagByTagName(String newTag) {
        TagExample tagExample = new TagExample();
        tagExample.createCriteria().andNameEqualTo(newTag);
        List<Tag> tagList = tagMapper.selectByExample(tagExample);
        if (tagList.size() == 0) return null;
        else return tagList.get(0);
    }

    @Override
    public void saveTag(String newTag) {
        Tag record = new Tag();
        record.setName(newTag);
        tagMapper.insert(record);
    }

    @Override
    @Transactional
    public void removeTagById(Long tagId) {
        tagMapper.deleteByPrimaryKey(tagId);
        BlogWithTagsExample blogWithTagsExample = new BlogWithTagsExample();
        blogWithTagsExample.createCriteria().andTagIdEqualTo(tagId);
        blogWithTagsMapper.deleteByExample(blogWithTagsExample);
    }

    @Override
    public List<Tag> listAllTags() {
        return tagMapper.selectByExample(new TagExample());
    }
}
