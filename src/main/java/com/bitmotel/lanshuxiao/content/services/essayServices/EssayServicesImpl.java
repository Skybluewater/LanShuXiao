package com.bitmotel.lanshuxiao.content.services.essayServices;

import com.bitmotel.lanshuxiao.content.entity.*;
import com.bitmotel.lanshuxiao.content.mapper.EssayMapper;
import com.bitmotel.lanshuxiao.content.services.EditableI;
import com.bitmotel.lanshuxiao.content.services.categoryServices.CategoryServicesImpl;
import com.bitmotel.lanshuxiao.content.services.tagEssayMapperServices.TagEssayMapperServicesI;
import com.bitmotel.lanshuxiao.exception.BusinessException;
import com.bitmotel.lanshuxiao.exception.PermissionException;
import com.bitmotel.lanshuxiao.user.entity.UserEntity;
import com.bitmotel.lanshuxiao.user.services.UserServicesI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.lang.Object;
import java.util.*;

@Repository
@Service("EssayService")
public class EssayServicesImpl implements EssayServicesI, EditableI<Object> {

    @Autowired
    EssayMapper essayMapper;

    @Autowired
    TagEssayMapperServicesI tagEssayMapper;

    @Autowired
    @Qualifier("CategoryService")
    CategoryServicesImpl categoryServices;

    @Autowired
    @Qualifier("TagService")
    EditableI<TagEntity> tagServices;

    @Autowired
    UserServicesI userServicesI;

    @Override
    public Object create(Object data) {
        EssayEntity essayEntity = (EssayEntity) data;
        // handle tags first
        List<TagEntity> tags = new ArrayList<>();
        if (essayEntity.getTags() != null) {
            for (TagEntity tag : essayEntity.getTags()) {
                tags.add((TagEntity) tagServices.create(tag, essayEntity.getUser().getUser_id()));
            }
        }
        Essays essay = insertEssay(essayEntity);
        for (TagEntity tag: tags) {
            tagEssayMapper.mapEssayWithTag(tag.getTag_id(), essay.getPassage_id());
        }
        essayEntity.setTags(tags);
        essayEntity.setPassage_id(essay.getPassage_id());
        return essayEntity;
    }

    @Override
    public Object create(Object data, Integer user_id) {
        EssayEntity essayEntity = (EssayEntity) data;
        if (essayEntity.getUser() == null || !Objects.equals(essayEntity.getUser().getUser_id(), user_id)) {
            throw new PermissionException("Request rejected");
        }
        return create(data);
    }

    @Override
    public Object delete(Object data) {
        EssayEntity essayEntity = (EssayEntity) data;
        EssayEntity oldEssayEntity = (EssayEntity) query(essayEntity.getPassage_id());
        if (oldEssayEntity != null && !Objects.equals(oldEssayEntity.getUser().getUser_id(), essayEntity.getUser().getUser_id())) {
            throw new PermissionException("Trying to delete other one's essay");
        }
        return deleteEssay(essayEntity.getPassage_id());
    }

    @Override
    // TODO: check if the author_id in database of the essay to be updated equals to the user_id from HttpRequest; checked
    public Object update(Object data) {
        EssayEntity essayEntity = (EssayEntity) data;
        EssayEntity oldEssayEntity = (EssayEntity) query(essayEntity.getPassage_id());
        if (oldEssayEntity == null) {
            throw new BusinessException("Essay to be updated do not exist");
        }
        if (!Objects.equals(oldEssayEntity.getUser().getUser_id(), essayEntity.getUser().getUser_id())) {
            throw new PermissionException("Trying to modify other one's essay");
        }
        updateEssay(essayEntity);
        // handle tags
        Set<TagEntity> tags = new HashSet<>();
        if (essayEntity.getTags() != null) {
            for (TagEntity tag: essayEntity.getTags()) {
                tags.add((TagEntity) tagServices.create(tag, essayEntity.getUser().getUser_id()));
            }
        }
        Set<TagEntity> oldTags = new HashSet<>(oldEssayEntity.getTags());
        Set<TagEntity> temp = new HashSet<>(tags);
        // get tags to be mapped
        tags.removeAll(oldTags);
        // get tags to be unmapped
        oldTags.removeAll(temp);
        essayEntity.setTags(new ArrayList<>(temp));
        for (TagEntity tag: tags) {
            tagEssayMapper.mapEssayWithTag(tag.getTag_id(), essayEntity.getPassage_id());
        }
        for (TagEntity oldTag: oldTags) {
            tagEssayMapper.unmapEssayWithTag(oldTag.getTag_id(), essayEntity.getPassage_id());
        }
        return essayEntity;
    }

    @Override
    public Object query(Integer id) {
        // get tag entity list
        List<Integer> tag_id_list = tagEssayMapper.getTagIdByPassageId(id);
        List<TagEntity> tags = new ArrayList<>();
        if (tag_id_list != null) {
            for (Integer tag_id : tag_id_list) {
                tags.add((TagEntity) tagServices.query(tag_id));
            }
        }
        Essays essay = queryEssay(id);
        // get categories
        Categories category = categoryServices.getCategory(essay.getCategory_id());
        // get user entity
        UserEntity user = userServicesI.queryUserEntity(essay.getAuthor_id());
        return new EssayEntity(essay, tags, category, user);
    }

    @Override
    public List<?> queryByObject(Object data) {
        List<EssayEntity> essayEntities = new ArrayList<>();
        List<Integer> passage_id_list = null;
        if (data instanceof TagEntity) {
            passage_id_list = tagEssayMapper.getPassageIdByTagId(((TagEntity) data).getTag_id());
        } else if (data instanceof Categories) {
            passage_id_list = queryPassageIdByCategory((Categories) data);
        } else if (data instanceof UserEntity) {
            passage_id_list = queryPassageIdByUserEntity((UserEntity) data);
        }
        if (passage_id_list != null) {
            for (Integer passage_id: passage_id_list) {
                essayEntities.add((EssayEntity) query(passage_id));
            }
        }
        return essayEntities;
    }

    @Override
    public Object queryByUserId(Integer user_id) {
        return queryByObject(new UserEntity(user_id, null));
    }

    @Override
    public Object queryByPagination(Object data, Integer offset, Integer limit) {
        return null;
    }

    @Override
    public Essays queryEssay(Integer passage_id) {
        try {
            return essayMapper.queryEssay(passage_id);
        } catch (Exception e) {
            throw new BusinessException("Query essay failed");
        }
    }

    @Override
    public List<EssayEntity> queryAllEssays() {
        List<Integer> passage_ids = essayMapper.queryAllEssaysPassageId();
        List<EssayEntity> essayEntities = new ArrayList<>();
        for (Integer passage_id: passage_ids) {
            essayEntities.add((EssayEntity) query(passage_id));
        }
        return essayEntities;
    }

    @Override
    public List<EssayEntity> queryEssayByUser(String username) {
        return null;
    }

    @Override
    public List<Integer> queryPassageIdByCategory(Categories category) {
        try {
            return essayMapper.queryPassageIdByCategories(category);
        } catch (Exception e) {
            throw new BusinessException("Query passage_id by category failed");
        }
    }

    @Override
    public List<Integer> queryPassageIdByUserEntity(UserEntity entity) {
        try {
            return essayMapper.queryPassageIdByUserId(entity.getUser_id());
        } catch (Exception e) {
            throw new BusinessException("Query passage_id by user_id failed");
        }
    }

    @Override
    public List<EssayEntity> queryEssayByPagination(Integer limit, Integer offset) {
        return null;
    }

    @Override
    public List<EssayEntity> queryEssayByUserByTag(Integer tag_id, String username) {
        return null;
    }

    @Override
    public List<EssayEntity> queryEssayByUserByCategory(String category, String username) {
        return null;
    }

    @Override
    public List<EssayEntity> queryEssayByUserByCategoryId(Integer category_id, String username) {
        return null;
    }

    @Override
    public List<EssayEntity> queryEssayByUserByPagination(Integer limit, Integer offset, String username) {
        return null;
    }

    @Override
    public Essays insertEssay(EssayEntity essay) {
        try {
            return essayMapper.insertEssay(new Essays(essay));
        } catch (Exception e) {
            throw new BusinessException("Insert essay failed");
        }
    }

    @Override
    public Essays updateEssay(EssayEntity essay) {
        try {
            return essayMapper.updateEssay(new Essays(essay));
        } catch (Exception e) {
            throw new BusinessException("Update essay failed");
        }
    }

    @Override
    public boolean deleteEssay(Integer passage_id) {
        try {
            return essayMapper.deleteEssay(passage_id);
        } catch (Exception e) {
            throw new BusinessException("Delete essay failed");
        }
    }
}

