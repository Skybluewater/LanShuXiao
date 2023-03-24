package com.bitmotel.lanshuxiao.content.services.tagServices;

import com.bitmotel.lanshuxiao.content.entity.TagEntity;
import com.bitmotel.lanshuxiao.content.entity.Tags;
import com.bitmotel.lanshuxiao.content.mapper.TagMapper;
import com.bitmotel.lanshuxiao.content.services.EditableI;
import com.bitmotel.lanshuxiao.exception.BusinessException;
import com.bitmotel.lanshuxiao.exception.PermissionException;
import com.bitmotel.lanshuxiao.user.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@Service("TagService")
public class TagServicesImpl implements TagServicesI, EditableI<Object> {
    @Autowired
    TagMapper tagMapper;

    @Override
    public Object queryByObjectByUserId(Object data, Integer user_id) {
        TagEntity tag = (TagEntity) data;
        if (tag.getTag_id() == null) {
            throw new BusinessException("No tag id parsed");
        }
        Tags tg = tagMapper.getTag(tag.getTag_id());
        return Objects.equals(tg.getUser_id(), user_id);
    }

    @Override
    public Object queryByObjectByUserId(Object data, Integer user_id, Integer offset, Integer limit) {
        return null;
    }

    @Override
    public TagEntity getTag(Integer tag_id) {
        try {
            return new TagEntity(tagMapper.getTag(tag_id));
        } catch (Exception e) {
            throw new BusinessException("Tag query by tag_id failed");
        }
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
                tagEntityList.add(new TagEntity(tag));
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
    public TagEntity updateTag(TagEntity tag) {
        try {
            return new TagEntity(tagMapper.updateTag(new Tags(tag.getTag_id(), null, tag.getTag_name())));
        } catch (Exception e) {
            throw new BusinessException("Tag update failed");
        }
    }

    @Override
    public TagEntity addTag(TagEntity tag, Integer user_id) {
        try {
            return new TagEntity(tagMapper.addTag(new Tags(null, user_id, tag.getTag_name())));
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

    @Override
    public Object create(Object data) {
        return null;
    }

    @Override
    public Object create(Object data, Integer user_id) {
        TagEntity tagEntity = (TagEntity) data;
        // check if tag with id
        if (tagEntity.getTag_id() != null) {
            return tagEntity;
        }
        // check if tag without id already in database
        Tags tag = tagMapper.getTagByUserIdByTagName(user_id, tagEntity.getTag_name());
        if (tag != null) {
            return new TagEntity(tag);
        }
        return addTag(tagEntity, user_id);
    }

    // return boolean
    @Override
    public Object delete(Object data) {
        TagEntity tagEntity = (TagEntity) data;
        return deleteTag(tagEntity.getTag_id());
    }

    @Override
    public Object update(Object data) {
        return updateTag((TagEntity) data);
    }

    @Override
    public Object query() {
        return null;
    }

    @Override
    public Object query(Integer id) {
        return getTag(id);
    }

    @Override
    public Object query(Integer offset, Integer limit) {
        return null;
    }

    @Override
    public Object queryByObject(Object data) {
        if (data instanceof TagEntity tagEntity) {
            return query(tagEntity.getTag_id());
        } else if (data instanceof UserEntity user) {
            return queryByUserId(user.getUser_id());
        }
        return null;
    }

    @Override
    public Object queryByUserId(Integer user_id) {
        return getTagsByUserId(user_id);
    }

    @Override
    public Object queryByObject(Object data, Integer offset, Integer limit) {
        return null;
    }
}
