package com.bitmotel.lanshuxiao.content.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.sql.Time;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Essays {
    @Id
    @Generated
    private Integer passage_id;
    private String content;
    private String abs;
    @NotBlank(message = "Title cannot be blank")
    private String title;
    private Integer author_id;
    private Integer published;
    private Integer category_id;
    private Time createTime;
    private Time updateTime;
    private String cover_photo;
}
