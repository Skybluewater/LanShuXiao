package com.bitmotel.lanshuxiao.content.services.tagServices;

import com.bitmotel.lanshuxiao.content.entity.TagEntity;
import com.bitmotel.lanshuxiao.content.entity.Tags;
import com.bitmotel.lanshuxiao.content.mapper.TagMapper;
import com.bitmotel.lanshuxiao.content.services.EditI;
import com.bitmotel.lanshuxiao.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Repository
@Service("TagService")
public class TagServicesImpl implements TagServicesI, EditI<TagEntity> {
    @Autowired
    TagMapper tagMapper;

    @Override
    public TagEntity getTag(Integer tag_id) {
        return null;
    }

    @Override
    public List<TagEntity> getAllTags() {
        return null;
    }

    @Override
    public List<TagEntity> getTagsByUserId(Integer user_id) {
        try {
            List<Tags> tagsList = tagMapper.getTagsByUserId(user_id);
            List<TagEntity> tagEntityList = new ArrayList<>();
            for (Tags tag: tagsList) {
                tagEntityList.add(convertTagsToTagEntity(tag));
            }
            return tagEntityList;
        } catch (Exception e) {
            throw new BusinessException("Query tags by user failed");
        }
    }

    @Override
    public List<TagEntity> getTagsByTagName(String tag_name) {
        return null;
    }

    @Override
    public List<TagEntity> getTagByUserIdByTagName(Integer user_id, String tag_name) {
        return null;
    }

    @Override
    public Integer getTagIdByUserIdByTagName(Integer user_id, String tag_name) {
        try {
            return tagMapper.getTagIdByUserIdByTagName(user_id, tag_name);
        } catch (Exception e) {
            throw new BusinessException("Tag query failed");
        }
    }

    @Override
    public TagEntity addTag(TagEntity tag, Integer user_id) {
        try {
            return convertTagsToTagEntity(tagMapper.addTag(new Tags(null, user_id, tag.getTag_name())));
        } catch (Exception e) {
            throw new BusinessException("Tag creation failed");
        }
    }

    @Override
    public boolean deleteTag(Integer tag_id) {
        try {
            tagMapper.deleteTag(tag_id);
            return true;
        } catch (Exception e) {
            throw new BusinessException("Tag deletion failed");
        }
    }

    public TagEntity convertTagsToTagEntity(Tags tag) {
        return new TagEntity(tag.getTag_id(), tag.getTag_name());
    }

    public Tags convertTagEntityToTags(TagEntity entity) {
        return new Tags(entity.getTag_id(), null, entity.getTag_name());
    }

    @Override
    public Object create(TagEntity data) {
        return null;
    }

    @Override
    public Object create(TagEntity data, Integer user_id) {
        if (data.getTag_id() != null) {
            return data;
        }
        return addTag(data, user_id);
    }

    // return boolean
    @Override
    public Object delete(TagEntity data) {
        return deleteTag(data.getTag_id());
    }

    @Override
    public Object update(TagEntity data) {
        return null;
    }
}
