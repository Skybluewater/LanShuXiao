package com.bitmotel.lanshuxiao.content.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Categories {
    @Id
    private Integer category_id;
    private String category_name;
}
