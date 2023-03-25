package com.bitmotel.lanshuxiao.content.controller;

import com.bitmotel.lanshuxiao.annotation.LoginRequired;
import com.bitmotel.lanshuxiao.content.entity.Categories;
import com.bitmotel.lanshuxiao.content.entity.EssayEntity;
import com.bitmotel.lanshuxiao.content.entity.TagEntity;
import com.bitmotel.lanshuxiao.content.services.EditableI;
import com.bitmotel.lanshuxiao.content.services.categoryServices.CategoryServicesI;
import com.bitmotel.lanshuxiao.content.services.categoryServices.CategoryServicesImpl;
import com.bitmotel.lanshuxiao.exception.PermissionException;
import com.bitmotel.lanshuxiao.response.Response;
import com.bitmotel.lanshuxiao.user.entity.UserEntity;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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

    @GetMapping("index")
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
            put("tagEntities", (List<TagEntity>) tagService.queryByObject(user));
//            put("tagEntities", (List<TagEntity>) tagService.queryByUserId(user.getUser_id()));
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

    @GetMapping("tag")
    @ResponseBody
    public Response<HashMap<String, Object>> getTagsByUser(
            UserEntity user
    ) {
        return Response.success(new HashMap<>(){{
            put("tagEntities", (List<TagEntity>) tagService.queryByObject(user));
        }});
    }

    @PostMapping("essay/delete")
    @ResponseBody
    @LoginRequired
    public Response<Boolean> deleteEssay(@RequestBody @Valid EssayEntity essay, HttpSession httpSession) {
        Object user_id = httpSession.getAttribute("userID");
        if (essay.getUser() == null || !Objects.equals(essay.getUser().getUser_id(), user_id)) {
            throw new PermissionException("Update essay request rejected");
        }
        return Response.success((Boolean) essayService.delete(essay));
    }

    @PostMapping("essay/update")
    @ResponseBody
    @LoginRequired
    public Response<HashMap<String, EssayEntity>> updateEssay(@RequestBody @Valid EssayEntity essay, HttpSession httpSession) {
        Object user_id = httpSession.getAttribute("userID");
        if (essay.getUser() == null || !Objects.equals(essay.getUser().getUser_id(), user_id)) {
            throw new PermissionException("Update essay request rejected");
        }
        return Response.success(new HashMap<>() {{
            put("essayEntity", (EssayEntity) essayService.update(essay));
        }});
    }

    @PostMapping("essay/create")
    @ResponseBody
    @LoginRequired
    public Response<HashMap<String, EssayEntity>> createEssay(@RequestBody @Valid EssayEntity essay, HttpSession httpSession) {
        Object user_id = httpSession.getAttribute("userID");
        if (essay.getUser() == null || !Objects.equals(essay.getUser().getUser_id(), user_id)) {
            throw new PermissionException("Create essay request rejected");
        }
        return Response.success(new HashMap<>() {{
            put("essayEntity", (EssayEntity) essayService.create(essay));
        }});
    }

    @PostMapping("essay/tag/create")
    @ResponseBody
    @LoginRequired
    public Response<HashMap<String, Object>> createTag(@RequestBody @Valid TagEntity tag, HttpSession httpSession) {
        Integer user_id = (Integer) httpSession.getAttribute("userID");
        tagService.create(tag, user_id);
        return Response.success(new HashMap<>() {{
            put("essayEntity", (List<TagEntity>) tagService.queryByUserId(user_id));
        }});
    }

    @PostMapping("essay/tag/update")
    @ResponseBody
    @LoginRequired
    public Response<HashMap<String, Object>> updateTag(@RequestBody @Valid TagEntity tag, HttpSession httpSession) {
        Integer user_id = (Integer) httpSession.getAttribute("userID");
        if (!(Boolean) tagService.queryByObjectByUserId(tag, user_id)) {
            throw new PermissionException("Trying to update other one's tag");
        }
        tagService.update(tag);
        return Response.success(new HashMap<>() {{
            put("essayEntity", (List<TagEntity>) tagService.queryByUserId(user_id));
        }});
    }

    @PostMapping("essay/tag/delete")
    @ResponseBody
    @LoginRequired
    public Response<HashMap<String, Object>> deleteTag(@RequestBody @Valid TagEntity tag, HttpSession httpSession) {
        Integer user_id = (Integer) httpSession.getAttribute("userID");
        if (!(Boolean) tagService.queryByObjectByUserId(tag, user_id)) {
            throw new PermissionException("Trying to delete other one's tag");
        }
        tagService.delete(tag);
        return Response.success(new HashMap<>() {{
            put("essayEntity", (List<TagEntity>) tagService.queryByUserId(user_id));
        }});
    }
}
