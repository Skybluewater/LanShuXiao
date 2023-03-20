package com.bitmotel.lanshuxiao.user.entity;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.sql.Time;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Id
    @Generated
    private Integer user_id;
    @NotBlank(message = "Username cannot be blank")
    private String username;
    @NotBlank(message = "Password cannot be blank")
    private String password;
    @Email(message = "This field must be email-liked-style")
    @Nullable
    private String email;
    private String avatar;
    private String sex;
    private Time createTime;
    private Time updateTime;
}

