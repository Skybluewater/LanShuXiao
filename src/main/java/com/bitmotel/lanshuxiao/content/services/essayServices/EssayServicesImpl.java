package com.bitmotel.lanshuxiao.content.services.essayServices;

import com.bitmotel.lanshuxiao.content.entity.EssayEntity;
import com.bitmotel.lanshuxiao.content.entity.Essays;
import com.bitmotel.lanshuxiao.content.entity.TagEntity;
import com.bitmotel.lanshuxiao.content.mapper.EssayMapper;
import com.bitmotel.lanshuxiao.content.services.EditI;
import com.bitmotel.lanshuxiao.content.services.categoryServices.CategoryServicesI;
import com.bitmotel.lanshuxiao.content.services.tagServices.TagServicesI;
import com.bitmotel.lanshuxiao.exception.BusinessException;
import com.bitmotel.lanshuxiao.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Repository
@Service("EssayService")
public class EssayServicesImpl implements EssayServicesI, EditI<EssayEntity> {
    @Autowired
    EssayMapper essayMapper;

    @Qualifier("TagService")
    @Autowired
    EditI<TagEntity> tagServicesI;

    @Qualifier("CategoryService")
    @Autowired
    CategoryServicesI categoryServicesI;

    @Autowired
    UserMapper userMapper;

    @Override
    public EssayEntity queryEssay(Integer passage_id) {
        return null;
    }

    @Override
    public List<EssayEntity> queryAllEssays() {
        return null;
    }

    @Override
    public List<EssayEntity> queryEssayByUser(String username) {
        return null;
    }

    @Override
    public List<EssayEntity> queryEssayByCategory(String category) {
        return null;
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
    public boolean insertEssay(EssayEntity essay) {
        return false;
    }

    @Override
    public boolean updateEssay(EssayEntity essay) {
        return false;
    }

    @Override
    public boolean deleteEssay(Integer passage_id) {
        try {
            essayMapper.deleteEssay(passage_id);
            return true;
        } catch (Exception e) {
            throw new BusinessException("Delete Essay failed");
        }
    }

    private Essays convertEssayEntityToEssays(EssayEntity entity) {
        Essays essays = new Essays();
        essays.setPassage_id(entity.getPassage_id());
        essays.setAbs(entity.getAbs());
        essays.setContent(entity.getContent());
        essays.setTitle(entity.getTitle());
        if (entity.getCategory() != null) {
            essays.setCategory_id(entity.getCategory().getCategory_id());
        }
        essays.setPublished(entity.getPublished());
        essays.setCreateTime(entity.getCreateTime());
        essays.setUpdateTime(entity.getUpdateTime());
        essays.setCover_photo(entity.getCover_photo());
        essays.setAuthor_id(userMapper.queryIdByName(entity.getUsername()));
        return essays;
    }

    private EssayEntity convertEssaysToEssayEntity(Essays essay) {
        EssayEntity entity = new EssayEntity();
        entity.setPassage_id(essay.getPassage_id());
        entity.setUsername(userMapper.queryNameById(essay.getAuthor_id()));
        entity.setCategory(categoryServicesI.getCategory(essay.getCategory_id()));
        entity.setContent(essay.getContent());
        entity.setAbs(essay.getAbs());
        entity.setTitle(essay.getTitle());
        entity.setPublished(essay.getPublished());
        entity.setCreateTime(essay.getCreateTime());
        entity.setUpdateTime(essay.getUpdateTime());
        entity.setCover_photo(essay.getCover_photo());
        // TODO: set tag entity list
        return entity;
    }

    @Override
    public Object create(EssayEntity data) {
        return null;
    }

    @Override
    public Object delete(EssayEntity data) {
        return null;
    }

    @Override
    public Object update(EssayEntity data) {
        return null;
    }

    @Override
    public Object create(EssayEntity data, Integer user_id) {
        // update tags
        List<TagEntity> tagEntityList = new ArrayList<>();
        for (TagEntity tag: data.getTags()) {
            tagEntityList.add((TagEntity) tagServicesI.create(tag, user_id));
        }
        essayMapper.insertEssay(convertEssayEntityToEssays(data));
        data.setTags(tagEntityList);
        return data;
    }
}
