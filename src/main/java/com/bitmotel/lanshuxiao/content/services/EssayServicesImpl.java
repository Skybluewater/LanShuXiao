package com.bitmotel.lanshuxiao.content.services;

import com.bitmotel.lanshuxiao.content.entity.EssayEntity;
import com.bitmotel.lanshuxiao.content.entity.Essays;
import com.bitmotel.lanshuxiao.content.mapper.CategoryMapper;
import com.bitmotel.lanshuxiao.content.mapper.EssayMapper;
import com.bitmotel.lanshuxiao.content.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EssayServicesImpl implements EssayServicesI {
    @Autowired
    EssayMapper essayMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    TagMapper tagMapper;

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
        return false;
    }

    private Essays convertEssayEntityToEssays(EssayEntity entity) {
        return null;
    }
    private EssayEntity convertEssaysToEssayEntity(Essays essay) {
        return null;
    }
}
