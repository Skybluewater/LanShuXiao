package com.bitmotel.lanshuxiao.content.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagEntity {
    private Integer tag_id;
    private String tag_name;
}
