package com.bitmotel.lanshuxiao.content.entity;

import lombok.*;

//@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagEntity {
    private Integer tag_id;
    private String tag_name;

    public TagEntity(Tags tag) {
        tag_id = tag.getTag_id();
        tag_name = tag.getTag_name();
    }
}
