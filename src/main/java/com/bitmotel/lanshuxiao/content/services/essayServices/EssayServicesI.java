package com.bitmotel.lanshuxiao.content.services.essayServices;

import com.bitmotel.lanshuxiao.content.entity.EssayEntity;

import java.util.List;

public interface EssayServicesI {
    public EssayEntity queryEssay(Integer passage_id);
    public List<EssayEntity> queryAllEssays();
    public List<EssayEntity> queryEssayByUser(String username);
    public List<EssayEntity> queryEssayByCategory(String category);
    public List<EssayEntity> queryEssayByPagination(Integer limit, Integer offset);
    public List<EssayEntity> queryEssayByUserByTag(Integer tag_id, String username);
    public List<EssayEntity> queryEssayByUserByCategory(String category, String username);
    public List<EssayEntity> queryEssayByUserByCategoryId(Integer category_id, String username);
    public List<EssayEntity> queryEssayByUserByPagination(Integer limit, Integer offset, String username);
    public boolean insertEssay(EssayEntity essay);
    public boolean updateEssay(EssayEntity essay);
    public boolean deleteEssay(Integer passage_id);
}
