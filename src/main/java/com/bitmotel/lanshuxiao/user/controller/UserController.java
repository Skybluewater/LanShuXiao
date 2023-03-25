package com.bitmotel.lanshuxiao.user.controller;

import com.bitmotel.lanshuxiao.annotation.LoginRequired;
import com.bitmotel.lanshuxiao.annotation.LogoutRequired;
import com.bitmotel.lanshuxiao.component.PasswordEncoderI;
import com.bitmotel.lanshuxiao.exception.BusinessException;
import com.bitmotel.lanshuxiao.response.Response;
import com.bitmotel.lanshuxiao.user.entity.UserInfoEntity;
import com.bitmotel.lanshuxiao.user.entity.UserLoginEntity;
import com.bitmotel.lanshuxiao.user.entity.Users;
import com.bitmotel.lanshuxiao.user.services.UserServicesI;
import jakarta.servlet.http.HttpSession;
// import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Scope("prototype")
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServicesI userServicesI;

    @Autowired
    PasswordEncoderI passwordEncoderI;

    @PostMapping("/login")
    @LogoutRequired
    @ResponseBody
    public Response<UserInfoEntity> login(@RequestBody @Validated UserLoginEntity user, HttpSession session) throws BusinessException {
        Users oldUser = userServicesI.queryByName(user.getUsername());
        if (oldUser == null) {
            throw new BusinessException("User info error");
        }
        if (passwordEncoderI.matches(user.getPassword(), oldUser.getPassword())) {
            session.setAttribute("userID", oldUser.getUser_id());
            return Response.success(new UserInfoEntity(oldUser));
        }
        throw new BusinessException("Password not matched");
    }

    @PostMapping("/register")
    @LogoutRequired
    @ResponseBody
    public Response<Boolean> register(@RequestBody @Validated Users user) {
        Users oldUser = userServicesI.queryByName(user.getUsername());
        if (oldUser != null) {
            throw new BusinessException("Username has existed");
        }
        user.setPassword(passwordEncoderI.encode(user.getPassword()));
        userServicesI.add(user);
        return Response.success(true);
    }

    @PostMapping("/logout")
    @LoginRequired
    @ResponseBody
    public Response<Boolean> logout(HttpSession session) {
        session.removeAttribute("userID");
        return Response.success(true);
    }

    // TODO: delete this test class
    // test sending list with json
    @PostMapping("/getAll")
    @ResponseBody
    public Response<List<Users>> getAll() {
        return Response.success(userServicesI.queryAll());
    }
}
