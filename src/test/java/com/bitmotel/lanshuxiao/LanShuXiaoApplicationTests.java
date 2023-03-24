package com.bitmotel.lanshuxiao;

import com.bitmotel.lanshuxiao.content.entity.*;
import com.bitmotel.lanshuxiao.content.mapper.EssayMapper;
import com.bitmotel.lanshuxiao.content.mapper.TagEssayMapper;
import com.bitmotel.lanshuxiao.content.mapper.TagMapper;
import com.bitmotel.lanshuxiao.content.services.EditableI;
import com.bitmotel.lanshuxiao.user.entity.UserEntity;
import com.bitmotel.lanshuxiao.user.entity.Users;
import com.bitmotel.lanshuxiao.user.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class LanShuXiaoApplicationTests {
    @Autowired
    DataSource dataSource;
    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        connection.close();
    }

    @Autowired
    UserMapper userMapper;
    @Test
    public void toTest() {
        List<Users> userInfoList = userMapper.queryAll();
        userInfoList.forEach(System.out::println);
    }

    @Autowired
    EssayMapper essayMapper;
    @Test
    public void testEssayMapper() {
        List<Essays> essaysInfoList = essayMapper.queryEssayByUser("123");
        for (Essays essays : essaysInfoList) {
            System.out.println(essays);
        }
        essaysInfoList = essayMapper.queryAllEssays();
        System.out.println(essaysInfoList);
        List<Integer> passage_ids = essayMapper.queryPassageIdByUserId(1);
        System.out.println(passage_ids);
        passage_ids = essayMapper.queryPassageIdByCategories(new Categories(1, "123"));
        System.out.println(passage_ids);
        passage_ids = essayMapper.queryPassageIdByCategoryId(1);
        System.out.println(passage_ids);
        passage_ids = essayMapper.queryPassageIdByUserEntity(new UserEntity(1, "123"));
        System.out.println(passage_ids);
    }

    @Test
    public void testEssayMapperWithPagination() {
        List<Integer> passage_ids = essayMapper.queryAllPassageIdWithPagination(0, 10);
        System.out.println(passage_ids);
        passage_ids = essayMapper.queryPassageIdByUserIdWithPagination(1, 0, 10);
        System.out.println(passage_ids);
        passage_ids = essayMapper.queryPassageIdByUserEntityWithPaginationTest(new HashMap<>(){{
            put("author_id", 1);
            put("limit", 10);
            put("offset", 10);
        }});
        System.out.println(passage_ids);
        // these will cause error for mybatis can't resolve parameter type mixed with class or basic data types
//        passage_ids = essayMapper.queryPassageIdByUserEntityWithPagination(new UserEntity(1, "123"), 0, 10);
//        System.out.println(passage_ids);
    }

    @Autowired
    @Qualifier("EssayService")
    EditableI essayI;

    @Test
    public void testEssayServiceQuery() {
        System.out.println(essayI.query(1));
    }

    @Test
    public void testEssayServiceQueryByObject() {
        TagEntity tag = new TagEntity(1, "123tag");
        List<EssayEntity> entities = (List<EssayEntity>) essayI.queryByObject(tag);
        entities.forEach(System.out::println);
        Categories category = new Categories(1, "123");
        entities = (List<EssayEntity>) essayI.queryByObject(category);
        entities.forEach(System.out::println);
        UserEntity userEntity = new UserEntity(1, "123");
        entities = (List<EssayEntity>) essayI.queryByObject(userEntity);
        for (EssayEntity entity : entities) {
            System.out.println(entity);
        }
    }

    @Autowired
    TagEssayMapper tagEssayMapper;

    @Test
    public void testTagEssayMapper() {
        List<TagEssayEntity> ids = tagEssayMapper.getAll();
        System.out.println(ids);
        List<Integer> tag_ids = tagEssayMapper.getTagIdByPassageId(1);
        System.out.print(tag_ids);
    }

    @Autowired
    TagMapper tagMapper;

    @Test
    public void testTagMapper() {
        List<Tags> tags = tagMapper.getAllTags();
        System.out.println(tags);
    }

    @Test
    public void testUserMapper() {
        userMapper.queryNameById(1);
    }
}
