package com.bitmotel.lanshuxiao;

import com.bitmotel.lanshuxiao.content.entity.Essays;
import com.bitmotel.lanshuxiao.content.mapper.EssayMapper;
import com.bitmotel.lanshuxiao.user.entity.Users;
import com.bitmotel.lanshuxiao.user.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
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
    }
}
