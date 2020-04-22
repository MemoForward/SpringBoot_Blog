package com.memoforward.blog.service;

import com.memoforward.blog.dto.PageDto;
import com.memoforward.blog.model.Tag;

import java.util.List;

public interface ITagService {

    PageDto<Tag> listTags(Integer pageNum, Integer pageLimit);

    Tag findTagByTagName(String newTag);

    void saveTag(String newTag);

    void removeTagById(Long tagId);

    List<Tag> listAllTags();
}
