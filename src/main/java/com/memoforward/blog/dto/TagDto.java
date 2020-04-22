package com.memoforward.blog.dto;

public class TagDto {
    //使用了多少次
    private Long count;

    private String name;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TagDto{" +
                "count=" + count +
                ", name='" + name + '\'' +
                '}';
    }
}
