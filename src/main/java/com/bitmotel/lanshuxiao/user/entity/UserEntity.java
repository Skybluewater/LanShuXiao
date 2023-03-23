package com.bitmotel.lanshuxiao.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    private Integer user_id;
    private String username;

    public UserEntity(UserInfoEntity userInfo) {
        user_id = userInfo.getUser_id();
        username = userInfo.getUsername();
    }

    public UserEntity(Users user) {
        user_id = user.getUser_id();
        username = user.getUsername();
    }
}
