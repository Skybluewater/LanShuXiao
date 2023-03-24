package com.bitmotel.lanshuxiao.content.mapper;

import com.bitmotel.lanshuxiao.content.entity.Categories;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CategoryMapper {
    public Categories getCategory(Integer id);
    public Categories getCategoryByName(String category_name);
    public String getCategoryNameById(Integer id);
    public Integer getCategoryIdByName(String category_name);
    public List<Categories> getAllCategories();
}
