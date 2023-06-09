package com.bitmotel.lanshuxiao.user.mapper;

import com.bitmotel.lanshuxiao.user.entity.UserEntity;
import com.bitmotel.lanshuxiao.user.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    public List<Users> queryAll();
    public boolean add(Users userInfo);
    public Users queryByName(String username);
    public boolean update(Users userInfo);
    public boolean delete(Users userInfo);
    public String queryNameById(Integer user_id);
    public Integer queryIdByName(String username);
    public UserEntity queryUserEntity(Integer user_id);
}
