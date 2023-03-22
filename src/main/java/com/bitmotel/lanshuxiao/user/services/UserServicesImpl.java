package com.bitmotel.lanshuxiao.user.services;

import com.bitmotel.lanshuxiao.exception.BusinessException;
import com.bitmotel.lanshuxiao.user.entity.Users;
import com.bitmotel.lanshuxiao.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServicesImpl implements UserServicesI {
    @Autowired
    UserMapper userMapper;
    @Override
    public List<Users> queryAll() {
        return userMapper.queryAll();
    }

    @Override
    public boolean add(Users user) {
        try {
            userMapper.add(user);
            return true;
        } catch (Exception e) {
            throw new BusinessException("Insert failed");
        }
    }

    @Override
    public Users queryByName(String username) {
        Optional<Users> user = Optional.ofNullable(userMapper.queryByName(username));
        if (user.isPresent()) {
            return user.get();
        }
        throw new BusinessException("Query failed");
    }

    @Override
    public boolean update(Users user) {
        try {
            userMapper.update(user);
            return true;
        } catch(Exception e) {
            throw new BusinessException("Update failed");
        }
    }

    @Override
    public boolean delete(Users user) {
        try {
            userMapper.delete(user);
            return true;
        } catch (Exception e) {
            throw new BusinessException("Delete failed");
        }
    }

    @Override
    public String queryNameById(Integer user_id) {
        try {
            return userMapper.queryNameById(user_id);
        } catch (Exception e) {
            throw new BusinessException("Query username by user_id failed");
        }
    }

    @Override
    public String queryIdByName(String username) {
        return null;
    }
}