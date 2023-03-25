package com.bitmotel.lanshuxiao.content.mapper;

import com.bitmotel.lanshuxiao.content.entity.TagEntity;
import com.bitmotel.lanshuxiao.content.entity.TagEssayEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TagEssayMapper {
    public boolean insertTagEssayMapper(Integer tag_id, Integer passage_id);
    public boolean deleteTagEssayMapper(Integer tag_id, Integer passage_id);
    public List<Integer> getPassageIdByTagId(Integer tag_id);
    public List<Integer> getPassageIdByTagIdWithPagination(Integer tag_id, Integer offset, Integer limit);
    public List<Integer> getTagIdByPassageId(Integer passage_id);
    public List<TagEssayEntity> getAll();
}
