package com.example.springboot.exception;

import lombok.Getter;

/**
 * 自定义异常
 * RuntimeException是java提供的运行时异常
 */
@Getter
public class ServiceException extends RuntimeException{
    private String code;

    public ServiceException(String code, String msg) {
        super(msg); //调用父类构造方法
        this.code = code;
    }
}
