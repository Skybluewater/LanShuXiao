package com.bitmotel.lanshuxiao.user.entity;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoEntity {
    private Integer user_id;
    private String username;
    private String sex;
    private String avatar;
    @Email
    private String email;

    public UserInfoEntity(Users user) {
        user_id = user.getUser_id();
        username = user.getUsername();
        sex = user.getSex();
        avatar = user.getAvatar();
        email = user.getEmail();
    }
}
