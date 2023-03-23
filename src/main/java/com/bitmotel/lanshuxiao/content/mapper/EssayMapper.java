package com.bitmotel.lanshuxiao.content.mapper;

import com.bitmotel.lanshuxiao.content.entity.Categories;
import com.bitmotel.lanshuxiao.content.entity.Essays;
import com.bitmotel.lanshuxiao.user.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EssayMapper {
    public Essays queryEssay(Integer passage_id);
    public List<Essays> queryAllEssays();
    public List<Integer> queryAllEssaysPassageId();
    public List<Essays> queryEssayByUser(String username);
    public List<Essays> queryEssayByCategory(String category);
    public List<Essays> queryEssayByCategoryId(Integer category_id);
    public List<Essays> queryEssayByPagination(Integer limit, Integer offset);
    // TODO
    public List<Essays> queryEssayByUserByTag(Integer tag_id, String username);
    // TODO
    public List<Essays> queryEssayByUserByCategory(String category, String username);
    // TODO
    public List<Essays> queryEssayByUserByCategoryId(Integer category_id, String username);
    // TODO
    public List<Essays> queryEssayByUserByPagination(Integer limit, Integer offset, String username);
    List<Integer> queryPassageIdByUserEntity(UserEntity entity);
    public List<Integer> queryPassageIdByUserId(Integer user_id);
    public List<Integer> queryPassageIdByCategories(Categories category);
    public List<Integer> queryPassageIdByCategoryId(Integer category_id);
    public Essays insertEssay(Essays essay);
    public Essays updateEssay(Essays essay);
    public boolean deleteEssay(Integer passage_id);
}