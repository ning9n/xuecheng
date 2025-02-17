package com.dp.dto;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public static Result<String> success(){
        return new Result<>(200, null, null);
    }
    public static <T> Result<T> success(T data){
        return new Result<>(200,null,data);
    }
    public static Result<String> fail(String msg){
        return Result.fail(500,msg);
    }
    public static Result<String> fail(Integer code,String msg){
        return new Result<>(code,msg,null);
    }
}
