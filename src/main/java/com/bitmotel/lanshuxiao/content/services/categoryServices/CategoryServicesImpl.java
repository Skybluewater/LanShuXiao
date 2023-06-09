package com.bitmotel.lanshuxiao.content.services.categoryServices;

import com.bitmotel.lanshuxiao.content.entity.Categories;
import com.bitmotel.lanshuxiao.content.mapper.CategoryMapper;
import com.bitmotel.lanshuxiao.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository
@Service
public class CategoryServicesImpl implements CategoryServicesI {
    @Autowired
    CategoryMapper categoryMapper;
    @Override
    public List<Categories> getAllCategories() {
        try {
            return categoryMapper.getAllCategories();
        } catch (Exception e) {
            throw new BusinessException("No categories available");
        }
    }

    @Override
    public Categories getCategory(Integer id) {
        if (id == null) {
            return null;
        }
        try {
            return categoryMapper.getCategory(id);
        } catch (Exception e) {
            throw new BusinessException("Get category failed");
        }
    }

    @Override
    public Categories getCategoryByName(String category_name) {
        try {
            return categoryMapper.getCategoryByName(category_name);
        } catch (Exception e) {
            throw new BusinessException("Get category by category name failed");
        }
    }

    @Override
    public String getCategoryNameById(Integer id) {
        try {
            return categoryMapper.getCategoryNameById(id);
        } catch (Exception e) {
            throw new BusinessException("Get category name by id failed");
        }
    }

    @Override
    public Integer getCategoryIdByName(String category_name) {
        try {
            return categoryMapper.getCategoryIdByName(category_name);
        } catch (Exception e) {
            throw new BusinessException("Get id by category name failed");
        }
    }
}