package com.bitmotel.lanshuxiao.content.controller;

import com.bitmotel.lanshuxiao.content.entity.Categories;
import com.bitmotel.lanshuxiao.content.entity.EssayEntity;
import com.bitmotel.lanshuxiao.content.entity.TagEntity;
import com.bitmotel.lanshuxiao.content.services.EditableI;
import com.bitmotel.lanshuxiao.content.services.categoryServices.CategoryServicesI;
import com.bitmotel.lanshuxiao.content.services.categoryServices.CategoryServicesImpl;
import com.bitmotel.lanshuxiao.response.Response;
import com.bitmotel.lanshuxiao.user.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class ContentController {
    @Autowired
    @Qualifier("TagService")
    EditableI tagService;

    @Autowired
    @Qualifier("EssayService")
    EditableI essayService;

    @Autowired
    CategoryServicesI categoryService;

    @GetMapping("/index")
    @ResponseBody
    public Response<HashMap<String, Object>> getIndex(
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        List<Categories> categoriesList = categoryService.getAllCategories();
        List<EssayEntity> essayEntities = (List<EssayEntity>) essayService.query(offset, limit);
        HashMap<String, Object> map = new HashMap<>() {{
            put("categoriesList", categoriesList);
            put("essayEntities", essayEntities);
        }};
        return Response.success(map);
    }

    @GetMapping("essay/{id}")
    @ResponseBody
    public Response<HashMap<String, Object>> getEssayById(
            @PathVariable("id") Integer passage_id
    ) {
        return Response.success(new HashMap<String, Object>() {{
            put("essayEntity", essayService.query(passage_id));
        }});
    }

    @GetMapping("essay/category")
    @ResponseBody
    public Response<HashMap<String, Object>> getEssaysByCategory(
            Categories category,
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit
    ) {
        return Response.success(new HashMap<>() {{
            put("essayEntities", (List<EssayEntity>) essayService.queryByObject(category, offset, limit));
        }});
    }

    @GetMapping("essay/user")
    @ResponseBody
    public Response<HashMap<String, Object>> getEssaysByUser(
            UserEntity user,
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit
    ) {
        return Response.success(new HashMap<>(){{
//            put("tagEntities", (List<TagEntity>) tagService.queryByObject(user));
            put("tagEntities", (List<TagEntity>) tagService.queryByUserId(user.getUser_id()));
            put("essayEntities", (List<EssayEntity>) essayService.queryByObject(user, offset, limit));
        }});
    }

    @GetMapping("essay/user/category")
    @ResponseBody
    public Response<HashMap<String, Object>> getEssaysByUserByCategory(
            Categories category,
            UserEntity user,
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit
    ) {
        return Response.success(new HashMap<>() {{
            put("essayEntities", (List<EssayEntity>) essayService.queryByObjectByUserId(category, user.getUser_id(), offset, limit));
        }});
    }
    // cautions: Not tag but tg
    // for springboot will confuse /tag in routing with the name with same prefix in field of the tag entity
    // above fixed
    @GetMapping("essay/tag")
    @ResponseBody
    public Response<HashMap<String, Object>> getEssaysByTag(
            TagEntity tag,
            @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit
    ) {
        return Response.success(new HashMap<>() {{
            put("essayEntities", (List<EssayEntity>) essayService.queryByObject(tag, offset, limit));
        }});
    }
}
