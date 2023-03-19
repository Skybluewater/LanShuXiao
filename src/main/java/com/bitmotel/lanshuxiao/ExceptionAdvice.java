package com.bitmotel.lanshuxiao;

import com.bitmotel.lanshuxiao.exception.BusinessException;
import com.bitmotel.lanshuxiao.exception.ForbiddenException;
import com.bitmotel.lanshuxiao.exception.PermissionException;
import com.bitmotel.lanshuxiao.response.Response;
import com.bitmotel.lanshuxiao.response.ResponseCode;
import jakarta.validation.ConstraintViolationException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler({BusinessException.class})
    public Response<?> handlerBusinessException(BusinessException ex) {
        return Response.fail(ex.getMessage());
    }

    @ExceptionHandler({BusinessException.class})
    public Response<?> handlerPermissionException(PermissionException ex) {
        return Response.fail(ex.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Response<?> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder str = new StringBuilder("Validation failed: ");
        for (FieldError fieldError: bindingResult.getFieldErrors()) {
            str.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage()).append(", ");
        }
        String msg = str.toString();
        if (StringUtils.hasText(msg)) {
            return Response.fail(ResponseCode.FORBIDDEN.code, msg);
        }
        return Response.fail(ResponseCode.FORBIDDEN);
    }

    @ExceptionHandler({ForbiddenException.class})
    public Response<?> handlerForbiddenException(ForbiddenException ex) {
        return Response.fail(ex.getMessage());
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public Response<?> handleConstraintViolationException(ConstraintViolationException ex) {
        if (StringUtils.hasText(ex.getMessage())) {
            return Response.fail(ResponseCode.FORBIDDEN.code, ex.getMessage());
        }
        return Response.fail(ResponseCode.FORBIDDEN);
    }

    @ExceptionHandler({Exception.class})
    public Response<?> handleGeneralException(Exception ex) {
        return Response.fail(ex.getMessage());
    }
}
