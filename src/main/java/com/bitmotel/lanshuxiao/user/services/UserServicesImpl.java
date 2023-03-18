package com.bitmotel.lanshuxiao.user.services;

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
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Users queryByName(String username) {
        Optional<Users> user = Optional.ofNullable(userMapper.queryByName(username));
        return user.orElse(null);
    }

    @Override
    public boolean update(Users user) {
        try {
            userMapper.update(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Users user) {
        try {
            userMapper.delete(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}