package com.memoforward.blog.dto;

import com.memoforward.blog.enums.BlogError;

public class ResultDto<T> {
    private Integer code;
    private String message;
    private T data;

    public static ResultDto errorOf(BlogError error){
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(error.getErrorCode());
        resultDto.setMessage(error.getMessage());
        return resultDto;
    }

    public static <T> ResultDto errorOf(BlogError error, T data){
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(error.getErrorCode());
        resultDto.setMessage(error.getMessage());
        resultDto.setData(data);
        return resultDto;
    }

    public static ResultDto successOf(){
        return new ResultDto(200, "请求成功");
    }

    public static <T> ResultDto successOf(T data){
        ResultDto resultDto = new ResultDto(200, "请求成功");
        resultDto.setData(data);
        return resultDto;
    }

    public ResultDto() {
    }

    public ResultDto(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResultDto{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
