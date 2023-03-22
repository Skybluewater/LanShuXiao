package com.bitmotel.lanshuxiao.content.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EssayEntity {
     private Integer passage_id;
     private String username;
     private String content;
     private String abs;
     private String title;
     private Integer published;
     private Categories category;
     private List<TagEntity> tags;
     private Time createTime;
     private Time updateTime;
     private String cover_photo;
}
