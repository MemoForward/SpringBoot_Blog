package com.memoforward.blog.enums;

public enum BlogError {
    RESOURCES_NOT_FOUND(5000,"资源不存在"),
    USER_NOT_FOUND(3001, "用户不存在"),
    PASSWORD_NOT_CORRECT(3002,"密码不正确"),
    REGISTER_FAILED(3003, "注册失败！"),
    USERNAME_CANNOT_BE_USED(3004,"用户名已存在！"),
    TOO_MANY_USERS(3005,"该用户名存在不止一个，请联系管理员！"),
    USER_PERMISSION_DENIED(3006, "用户无权限！"),
    BLOG_TITLE_EXISTS(3007, "博客标题已存在！"),
    BLOG_NOT_EXISTS(3008,"博客不存在");

    private final Integer errorCode;
    private final String message;

    BlogError(Integer errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
