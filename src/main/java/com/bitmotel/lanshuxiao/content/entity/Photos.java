package com.bitmotel.lanshuxiao.content.entity;

import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Photos {
    @Id
    @Generated
    private Integer photo_id;
    private String photo_content;
    private Integer user_id;
    private Integer passage_id;
}
