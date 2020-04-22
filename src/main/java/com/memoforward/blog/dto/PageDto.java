package com.memoforward.blog.dto;

import java.util.List;

public class PageDto<T>{
    private List<T> data;
    private int currentPage;
    private int totalPage;
    private int totalNums;

    public boolean isEmpty(){
        if(data.size() == 0) return true;
        return false;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getTotalNums() {
        return totalNums;
    }

    public void setTotalNums(Integer totalNums) {
        this.totalNums = totalNums;
    }

    @Override
    public String toString() {
        return "PageDto{" +
                "data=" + data +
                ", currentPage=" + currentPage +
                ", totalPage=" + totalPage +
                ", totalNums=" + totalNums +
                '}';
    }
}
