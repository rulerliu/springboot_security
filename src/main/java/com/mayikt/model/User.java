package com.mayikt.model;

import lombok.Data;

/**
 * 用户模型
 */
@Data
public class User {

    private Long id;
    private String username;
    private String password;

}