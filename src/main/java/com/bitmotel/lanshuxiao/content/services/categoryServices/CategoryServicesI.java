package com.bitmotel.lanshuxiao.content.services.categoryServices;

import com.bitmotel.lanshuxiao.content.entity.Categories;

public interface CategoryServicesI {
    public Categories getCategory(Integer id);
    public Categories getCategoryByName(String category_name);
    public String getCategoryNameById(Integer id);
    public Integer getCategoryIdByName(String category_name);
}
