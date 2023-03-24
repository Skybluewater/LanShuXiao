package com.bitmotel.lanshuxiao.content.mapper;

import com.bitmotel.lanshuxiao.content.entity.Tags;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface TagMapper {
    public Tags getTag(Integer tag_id);
    public List<Tags> getAllTags();
    public List<Tags> getTagsByUserId(Integer user_id);
    public List<Tags> getTagsByTagName(String tag_name);
    public Tags getTagByUserIdByTagName(Integer user_id, String tag_name);
    public Integer getTagIdByUserIdByTagName(Integer user_id, String tag_name);
    public Tags addTag(Tags tag);
    // TODO: public Tags updateTag(Tags tag);
    public Tags updateTag(Tags tag);
    public boolean deleteTag(Integer tag_id);
}