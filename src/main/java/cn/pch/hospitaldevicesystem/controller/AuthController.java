package cn.pch.hospitaldevicesystem.controller;

import cn.pch.hospitaldevicesystem.entity.User;
import cn.pch.hospitaldevicesystem.repository.UserRepository;
import cn.pch.hospitaldevicesystem.utils.RestResponse;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * 注册控制层
 * Created by 潘成花 on 2021/01/23
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public RestResponse registerUser(@RequestBody Map<String,String> registerUser){
        User user = new User();
        user.setUsername(registerUser.get("username"));
        //对密码进行编码
        user.setPassword(bCryptPasswordEncoder.encode(registerUser.get("password")));
        //不对密码进行编码，存储明文
        //user.setPassword(registerUser.get("password"));
        user.setRole("ROLE_USER");
        ArrayList<String> list = new ArrayList();
        list.add("潘成花");
        return RestResponse.ok(user).list(list).msg("跨域成功");
//        User save = userRepository.save(user);
//        return save.toString();
    }
}
