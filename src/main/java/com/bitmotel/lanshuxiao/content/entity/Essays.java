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

    public Essays(EssayEntity entity) {
        passage_id = entity.getPassage_id();
        content = entity.getContent();
        abs = entity.getAbs();
        title = entity.getTitle();
        author_id = entity.getUser().getUser_id();
        published = entity.getPublished();
        category_id = entity.getCategory().getCategory_id();
        createTime = entity.getCreateTime();
        updateTime = entity.getUpdateTime();
        cover_photo = entity.getCover_photo();
    }
}
