package com.bitmotel.lanshuxiao.content.entity;

import com.bitmotel.lanshuxiao.user.entity.UserEntity;
import lombok.*;

import java.sql.Time;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EssayEntity {
     private Integer passage_id;
     private UserEntity user;
     private String content;
     private String abs;
     private String title;
     private Integer published;
     private Categories category;
     private List<TagEntity> tags;
     private Time createTime;
     private Time updateTime;
     private String cover_photo;

     public EssayEntity(Essays essay, List<TagEntity> tags, Categories category, UserEntity user) {
          passage_id = essay.getPassage_id();
          this.user = user;
          content = essay.getContent();
          abs = essay.getAbs();
          title = essay.getTitle();
          published = essay.getPublished();
          this.category = category;
          this.tags = tags;
          createTime = essay.getCreateTime();
          updateTime = essay.getUpdateTime();
          cover_photo = essay.getCover_photo();
     }
}
