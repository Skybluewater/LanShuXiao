package com.bitmotel.lanshuxiao.content.services.essayServices;

import com.bitmotel.lanshuxiao.content.entity.Categories;
import com.bitmotel.lanshuxiao.content.entity.EssayEntity;
import com.bitmotel.lanshuxiao.content.entity.Essays;
import com.bitmotel.lanshuxiao.user.entity.UserEntity;
import java.util.List;

public interface EssayServicesI {
    public Essays queryEssay(Integer passage_id);
    public List<Integer> queryPassageIdByCategory(Categories Categories);
    public List<Integer> queryPassageIdByUserEntity(UserEntity UserEntity);
    public List<Integer> queryAllPassageId();
    public List<Integer> queryAllPassageIdWithPagination(Integer offset, Integer limit);
    public List<Integer> queryPassageIdByUserIdWithPagination(Integer user_id, Integer offset, Integer limit);
    public List<Integer> queryPassageIdByUserEntityWithPagination(UserEntity entity, Integer offset, Integer limit);
    public List<Integer> queryPassageIdByCategoryIdWithPagination(Integer category_id, Integer offset, Integer limit);
    public List<Integer> queryPassageIdByCategoriesWithPagination(Categories category, Integer offset, Integer limit);
    public List<Integer> queryPassageIdByCategoryIdByUserId(Integer category_id, Integer user_id);
    public List<Integer> queryPassageIdByCategoryIdByUserIdWithPagination(Integer category_id, Integer user_id, Integer offset, Integer limit);
    public Essays insertEssay(EssayEntity essay);
    public Essays updateEssay(EssayEntity essay);
    public boolean deleteEssay(Integer passage_id);
}
