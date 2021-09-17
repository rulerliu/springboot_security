package com.mayikt.user.model;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class User {
    @TableId("id")
    private Long id;
    private String username;
    private String password;
}
