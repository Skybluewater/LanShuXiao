package com.bitmotel.lanshuxiao.content.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagEssayMapper {
    private Integer tag_id;
    private Integer passage_id;
}
