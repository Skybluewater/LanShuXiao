package com.bitmotel.lanshuxiao.content.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tags {
    @Id
    @Generated
    private Integer tag_id;
    @NotBlank
    private Integer user_id;
    private String tag_name;
}
