package com.bitmotel.lanshuxiao.user.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginEntity {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}