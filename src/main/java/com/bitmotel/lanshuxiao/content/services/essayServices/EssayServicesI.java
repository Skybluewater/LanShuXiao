package com.bitmotel.lanshuxiao.content.services.essayServices;

import com.bitmotel.lanshuxiao.content.entity.Categories;
import com.bitmotel.lanshuxiao.content.entity.EssayEntity;
import com.bitmotel.lanshuxiao.content.entity.Essays;
import com.bitmotel.lanshuxiao.user.entity.UserEntity;
import java.util.List;

public interface EssayServicesI {
    public Essays queryEssay(Integer passage_id);
    public List<EssayEntity> queryAllEssays();
    public List<EssayEntity> queryEssayByUser(String username);
    public List<Integer> queryPassageIdByCategory(Categories Categories);
    public List<Integer> queryPassageIdByUserEntity(UserEntity UserEntity);
    public List<EssayEntity> queryEssayByPagination(Integer limit, Integer offset);
    public List<EssayEntity> queryEssayByUserByTag(Integer tag_id, String username);
    public List<EssayEntity> queryEssayByUserByCategory(String category, String username);
    public List<EssayEntity> queryEssayByUserByCategoryId(Integer category_id, String username);
    public List<EssayEntity> queryEssayByUserByPagination(Integer limit, Integer offset, String username);
    public Essays insertEssay(EssayEntity essay);
    public Essays updateEssay(EssayEntity essay);
    public boolean deleteEssay(Integer passage_id);
}
