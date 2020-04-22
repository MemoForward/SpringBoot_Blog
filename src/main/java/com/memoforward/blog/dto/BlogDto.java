package com.memoforward.blog.dto;

import com.memoforward.blog.model.Blog;
import com.memoforward.blog.model.Tag;
import com.memoforward.blog.model.Type;
import com.memoforward.blog.model.User;

import java.util.ArrayList;
import java.util.List;

public class BlogDto {
    private Blog blog;
    private Type type;
    private User user;
    private List<Tag> tagList;

    public BlogDto() {
    }

    public BlogDto(int initialize) {
        this.blog = new Blog();
        this.type = new Type();
        this.user = new User();
        this.tagList = new ArrayList<>();
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    @Override
    public String toString() {
        return "BlogDto{" +
                "blog=" + blog +
                ", type=" + type +
                ", user=" + user +
                ", tagList=" + tagList +
                '}';
    }
}
