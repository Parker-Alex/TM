package com.tmall.web.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = {"com.tmall.web"})
public class MyAdvice {

    @ExceptionHandler(RuntimeException.class)
    public String myexception() {
        return "fore/exception";
    }
}
