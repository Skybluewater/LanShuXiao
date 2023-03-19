package com.bitmotel.lanshuxiao.user.controller;

import com.bitmotel.lanshuxiao.annotation.LoginRequired;
import com.bitmotel.lanshuxiao.annotation.LogoutRequired;
import com.bitmotel.lanshuxiao.exception.BusinessException;
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
    @LoginRequired
    @ResponseBody
    public Response<Boolean> login(@RequestBody @Validated Users user, HttpSession session) throws BusinessException {
        Users oldUser = userServicesI.queryByName(user.getUsername());
        if (oldUser == null) {
            throw new BusinessException("User info error");
        }
        if (oldUser.getPassword().equals(user.getPassword())) {
            session.setAttribute("userID", oldUser.getId());
            return Response.success(true);
        }
        throw new BusinessException("Password not matched");
    }

    @PostMapping("/register")
    @LogoutRequired
    @ResponseBody
    public Response<Users> register(@RequestBody @Validated Users user) {
        userServicesI.add(user);
        return Response.success(user);
    }

    @PostMapping("/logout")
    @LoginRequired
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