package com.bitmotel.lanshuxiao.user.entity;

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
    private Integer id;
    @NonNull
    private String username;
    @NonNull
    private String password;
    private String email;
    private String avatar;
    private String sex;
    private Time createTime;
    private Time updateTime;
}
