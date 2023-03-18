package com.bitmotel.lanshuxiao.user.controller;

import com.bitmotel.lanshuxiao.response.response;
import com.bitmotel.lanshuxiao.user.entity.Users;
import com.bitmotel.lanshuxiao.user.services.UserServicesI;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServicesI userServicesI;

    @PostMapping("/login")
    @ResponseBody
    public response<Boolean> login(@RequestBody Users user, HttpSession session) throws Exception {
        Users oldUser = userServicesI.queryByName(user.getUsername());
        if (oldUser == null) {
            throw new Exception("User info error");
        }
        if (oldUser.getPassword().equals(user.getPassword())) {
            session.setAttribute("userID", oldUser.getId());
            return response.success(true);
        }
        return response.fail(200, "password does not match", false);
    }

    @PostMapping("/register")
    @ResponseBody
    public response<Users> register(@RequestBody Users user) {
        userServicesI.add(user);
        return response.success(user);
    }

    @PostMapping("/logout")
    @ResponseBody
    public response<Boolean> logout(HttpSession session) {
        Object userID = session.getAttribute("userID");
        if (userID == null) {
            return response.fail(400, "user hasn't login", false);
        }
        session.removeAttribute("userID");
        return response.success(true);
    }
}