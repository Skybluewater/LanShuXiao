package com.bitmotel.lanshuxiao;

import com.bitmotel.lanshuxiao.exception.BusinessException;
import com.bitmotel.lanshuxiao.exception.ForbiddenException;
import com.bitmotel.lanshuxiao.response.Response;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler({BusinessException.class})
    public Response<?> handlerBusinessException(BusinessException ex) {
        return Response.fail(ex.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Response<?> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

    }

    @ExceptionHandler({ForbiddenException.class})
    public Response<?> handlerForbiddenException(ForbiddenException ex) {
        return Response.fail(ex.getMessage());
    }
}
