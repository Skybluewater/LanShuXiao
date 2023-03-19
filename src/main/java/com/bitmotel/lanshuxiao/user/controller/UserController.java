package com.bitmotel.lanshuxiao.user.controller;

import com.bitmotel.lanshuxiao.response.Response;
import com.bitmotel.lanshuxiao.user.entity.Users;
import com.bitmotel.lanshuxiao.user.services.UserServicesI;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Scope("prototype")
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServicesI userServicesI;

    @PostMapping("/login")
    @ResponseBody
    public Response<Boolean> login(@RequestBody @Validated Users user, HttpSession session) throws Exception {
        Users oldUser = userServicesI.queryByName(user.getUsername());
        if (oldUser == null) {
            throw new Exception("User info error");
        }
        if (oldUser.getPassword().equals(user.getPassword())) {
            session.setAttribute("userID", oldUser.getId());
            return Response.success(true);
        }
        return Response.fail(200, "password does not match", false);
    }

    @PostMapping("/register")
    @ResponseBody
    public Response<Users> register(@RequestBody @Validated Users user) {
        userServicesI.add(user);
        return Response.success(user);
    }

    @PostMapping("/logout")
    @ResponseBody
    public Response<Boolean> logout(HttpSession session) {
        Object userID = session.getAttribute("userID");
        if (userID == null) {
            return Response.fail(400, "user hasn't login", false);
        }
        session.removeAttribute("userID");
        return Response.success(true);
    }
}