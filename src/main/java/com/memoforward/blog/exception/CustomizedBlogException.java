package com.memoforward.blog.exception;

import com.memoforward.blog.enums.BlogError;

public class CustomizedBlogException extends RuntimeException{
    private Integer code;
    private String message;

    public CustomizedBlogException() {
    }

    public CustomizedBlogException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public CustomizedBlogException(BlogError e){
        this.code = e.getErrorCode();
        this.message = e.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
