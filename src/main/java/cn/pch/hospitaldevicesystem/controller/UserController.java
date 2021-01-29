package cn.pch.hospitaldevicesystem.controller;

import cn.pch.hospitaldevicesystem.model.response.UserModel;
import cn.pch.hospitaldevicesystem.service.UserService;
import cn.pch.hospitaldevicesystem.utils.RestResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by 潘成花 on 2021/01/23
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @PostMapping("/getUsersByRole")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RestResponse getUsersByRole(@RequestBody Map<String,String> registerUser){
        List<UserModel> result = userService.queryAllByRole(registerUser.get("role"));
        return RestResponse.ok(result).msg("请求成功");
    }

}
