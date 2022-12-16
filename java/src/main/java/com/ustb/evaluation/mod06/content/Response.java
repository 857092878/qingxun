package com.ustb.evaluation.mod06.content;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author 蒲锐
 * @CreateTme 2022/12/14 15:12
 * Version1.0.0
 * 处理类别
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> {

    private Integer code;

    private String msg;

    private T data;

    public Response(Integer code){
        this.code = code;
    }

    public Response(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public Response(Integer code,T data){
        this.code = code;
        this.data = data;
    }

    public static Response OK(){
        return new Response(constant.CODE_200,"成功");
    }
    public static Response OK(String msg){
        return new Response(constant.CODE_200,msg);
    }
    public static<T> Response OK(String msg,T data){
        return new Response(constant.CODE_200,data);
    }

    public static<T> Response OK(T data){
        return new Response(constant.CODE_200,data);
    }

    public static Response fail(String msg){
        return new Response(constant.CODE_400,msg);
    }
    public static Response fail(Integer code,String msg){
        return new Response(code, msg);
    }


}
