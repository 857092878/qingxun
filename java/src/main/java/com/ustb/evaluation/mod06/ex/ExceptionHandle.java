package com.ustb.evaluation.mod06.ex;

import com.baomidou.mybatisplus.extension.api.R;
import com.ustb.evaluation.mod06.content.Response;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @Author 蒲锐
 * @CreateTme 2022/12/16 23:55
 * Version1.0.0
 */

public class ExceptionHandle {
    @ExceptionHandler(RunException.class)
    public Response exceptionHandle(Exception exception){
        String message = exception.getMessage();
        return Response.fail(message);
    }

}
