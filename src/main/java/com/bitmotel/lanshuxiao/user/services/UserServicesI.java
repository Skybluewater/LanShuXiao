package com.bitmotel.lanshuxiao.user.services;

import com.bitmotel.lanshuxiao.user.entity.Users;

import java.util.List;

public interface UserServicesI {
    public List<Users> queryAll();
    public boolean add(Users user);
    public Users queryByName(String username);
    public boolean update(Users user);
    public boolean delete(Users user);
}
