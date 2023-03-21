package com.bitmotel.lanshuxiao.content.mapper;

import com.bitmotel.lanshuxiao.content.entity.Essays;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EssayMapper {
    public Essays queryEssay(Integer passage_id);
    public List<Essays> queryAllEssays();
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
    public boolean insertEssay(Essays essay);
    public boolean updateEssay(Essays essay);
    public boolean deleteEssay(Integer passage_id);
}
