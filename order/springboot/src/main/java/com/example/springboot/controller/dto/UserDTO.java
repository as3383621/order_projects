package com.example.springboot.controller.dto;

import lombok.Data;

/**
 * 用于接受登入请求的参数
 */
@Data
public class UserDTO {
    private String username;
    private String password;
    private String token;
}
