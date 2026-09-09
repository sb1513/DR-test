package com.backend.drbackend.web.aop;

import com.backend.drbackend.web.R;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(Exception.class)
    public R handleException(Exception e){
        return new R (5001,e.getMessage(),null);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public R handleException2(DuplicateKeyException e){
        return new R (5002,"数据已存在",null);
    }

    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public R handleArrayIndexOutOfBoundsException(ArrayIndexOutOfBoundsException e){
        return new R (5002,e.getMessage(),null);
    }
}
