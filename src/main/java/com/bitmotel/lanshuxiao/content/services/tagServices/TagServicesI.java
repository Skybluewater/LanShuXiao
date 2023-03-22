package com.bitmotel.lanshuxiao.content.services.tagServices;

import com.bitmotel.lanshuxiao.content.entity.TagEntity;

import java.util.List;

public interface TagServicesI {
    public TagEntity getTag(Integer tag_id);
    public List<TagEntity> getAllTags();
    public List<TagEntity> getTagsByUserId(Integer user_id);
    public List<TagEntity> getTagsByTagName(String tag_name);
    public List<TagEntity> getTagByUserIdByTagName(Integer user_id, String tag_name);
    public Integer getTagIdByUserIdByTagName(Integer user_id, String tag_name);
    public TagEntity addTag(TagEntity tag, Integer user_id);
    public boolean deleteTag(Integer tag_id);
}
