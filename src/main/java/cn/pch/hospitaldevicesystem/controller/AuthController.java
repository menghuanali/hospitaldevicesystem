package cn.pch.hospitaldevicesystem.controller;

import cn.pch.hospitaldevicesystem.entity.User;
import cn.pch.hospitaldevicesystem.enums.RoleEnums;
import cn.pch.hospitaldevicesystem.repository.UserRepository;
import cn.pch.hospitaldevicesystem.utils.MyDateUtils;
import cn.pch.hospitaldevicesystem.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
        user.setRole(RoleEnums.ROLE_USER.getCode());//最开始是访客人员
        user.setTel(registerUser.get("tel"));
        user.setHeadUrl("/static/images/mychushihead.jpg");
        user.setAddress(registerUser.get("address"));
        user.setDepartment(registerUser.get("department"));
        user.setHospitalId(Long.valueOf(registerUser.get("hospitalId")));
        user.setCreateName("系统");
        user.setCreateTime(MyDateUtils.GetNowDate());
        userRepository.save(user);
        return RestResponse.ok().msg("注册成功,耐心等待审核!");
    }
}
