package com.bitmotel.lanshuxiao.content.services.essayServices;

import com.bitmotel.lanshuxiao.content.entity.*;
import com.bitmotel.lanshuxiao.content.mapper.EssayMapper;
import com.bitmotel.lanshuxiao.content.services.EditableI;
import com.bitmotel.lanshuxiao.content.services.categoryServices.CategoryServicesI;
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
    CategoryServicesI categoryServices;

    @Override
    public Object query() {
        List<EssayEntity> essayEntities = new ArrayList<>();
        List<Integer> passage_id_list = queryAllPassageId();
        if (passage_id_list != null) {
            for (Integer passage_id: passage_id_list) {
                essayEntities.add((EssayEntity) query(passage_id));
            }
        }
        return essayEntities;
    }

    @Autowired
    @Qualifier("TagService")
    EditableI<Object> tagServices;

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
        if (oldEssayEntity == null) {
            throw new BusinessException("Essay to be deleted do not exist in database");
        }
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
    public Object query(Integer offset, Integer limit) {
        List<EssayEntity> essayEntities = new ArrayList<>();
        List<Integer> passage_id_list = queryAllPassageIdWithPagination(offset, limit);
        if (passage_id_list != null) {
            for (Integer passage_id: passage_id_list) {
                essayEntities.add((EssayEntity) query(passage_id));
            }
        }
        return essayEntities;
    }

    @Override
    public List<Integer> queryAllPassageId() {
        try {
            return essayMapper.queryAllEssaysPassageId();
        } catch (Exception e) {
            throw new BusinessException("Query all passage_id failed");
        }
    }

    @Override
    public List<?> queryByObject(Object data) {
        List<EssayEntity> essayEntities = new ArrayList<>();
        List<Integer> passage_id_list = null;
        if (data == null) {
            passage_id_list = queryAllPassageId();
        } else if (data instanceof TagEntity) {
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
    public List<Integer> queryPassageIdByCategoryIdByUserId(Integer category_id, Integer user_id) {
        try {
            return essayMapper.queryPassageIdByCategoryIdByUserId(category_id, user_id);
        } catch (Exception e) {
            throw new BusinessException("Query passage_id by category_id by user_id failed");
        }
    }

    @Override
    public List<Integer> queryPassageIdByCategoryIdByUserIdWithPagination(Integer category_id, Integer user_id, Integer offset, Integer limit) {
        try {
            return essayMapper.queryPassageIdByCategoryIdByUserIdWithPagination(category_id, user_id, offset, limit);
        } catch (Exception e) {
            throw new BusinessException("Query passage_id by category_id by user_id with pagination failed");
        }
    }

    @Override
    public List<?> queryByObjectByUserId(Object data, Integer user_id) {
        List<EssayEntity> essayEntities = new ArrayList<>();
        List<Integer> passage_id_list = null;
        if (data == null) {
            return queryByObject(new UserEntity(user_id, null));
        } else if (data instanceof Categories) {
            passage_id_list = queryPassageIdByCategoryIdByUserId(((Categories) data).getCategory_id(), user_id);
        }
        if (passage_id_list != null) {
            for (Integer passage_id: passage_id_list) {
                essayEntities.add((EssayEntity) query(passage_id));
            }
        }
        return essayEntities;
    }

    @Override
    public List<?> queryByObjectByUserId(Object data, Integer user_id, Integer offset, Integer limit) {
        List<EssayEntity> essayEntities = new ArrayList<>();
        List<Integer> passage_id_list = null;
        if (data == null) {
            return queryByObject(new UserEntity(user_id, null), offset, limit);
        } else if (data instanceof Categories) {
            passage_id_list = queryPassageIdByCategoryIdByUserIdWithPagination(((Categories) data).getCategory_id(), user_id, offset, limit);
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
    public List<Integer> queryAllPassageIdWithPagination(Integer offset, Integer limit) {
        try {
            return essayMapper.queryAllPassageIdWithPagination(offset, limit);
        } catch (Exception e) {
            throw new BusinessException("Query passage_id with pagination failed");
        }
    }

    @Override
    public List<Integer> queryPassageIdByUserIdWithPagination(Integer user_id, Integer offset, Integer limit) {
        try {
            return essayMapper.queryPassageIdByUserIdWithPagination(user_id, offset, limit);
        } catch (Exception e) {
            throw new BusinessException("Query passage_id by user_id with pagination failed");
        }
    }

    @Override
    public List<Integer> queryPassageIdByUserEntityWithPagination(UserEntity entity, Integer offset, Integer limit) {
        try {
            return queryPassageIdByUserIdWithPagination(entity.getUser_id(), offset, limit);
        } catch (Exception e) {
            throw new BusinessException("Query passage_id by user_entity with pagination failed");
        }
    }

    @Override
    public List<Integer> queryPassageIdByCategoryIdWithPagination(Integer category_id, Integer offset, Integer limit) {
        try {
            return essayMapper.queryPassageIdByCategoryIdWithPagination(category_id, offset, limit);
        } catch (Exception e) {
            throw new BusinessException("Query passage_id by category_id with pagination failed");
        }
    }

    @Override
    public List<Integer> queryPassageIdByCategoriesWithPagination(Categories category, Integer offset, Integer limit) {
        return queryPassageIdByCategoryIdWithPagination(category.getCategory_id(), offset, limit);
    }
    // TODO
    @Override
    public List<?> queryByObject(Object data, Integer offset, Integer limit) {
        List<EssayEntity> essayEntities = new ArrayList<>();
        List<Integer> passage_id_list = null;
        if (data == null) {
            passage_id_list = queryAllPassageIdWithPagination(offset, limit);
        } else if (data instanceof TagEntity) {
            passage_id_list = tagEssayMapper.getPassageIdByTagId(((TagEntity) data).getTag_id(), offset, limit);
        } else if (data instanceof Categories) {
            passage_id_list = queryPassageIdByCategoriesWithPagination((Categories) data, offset, limit);
        } else if (data instanceof UserEntity) {
            passage_id_list = queryPassageIdByUserEntityWithPagination((UserEntity) data, offset, limit);
        }
        if (passage_id_list != null) {
            for (Integer passage_id: passage_id_list) {
                essayEntities.add((EssayEntity) query(passage_id));
            }
        }
        return essayEntities;
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


