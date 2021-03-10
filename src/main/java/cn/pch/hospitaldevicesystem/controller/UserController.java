package cn.pch.hospitaldevicesystem.controller;

import cn.pch.hospitaldevicesystem.entity.User;
import cn.pch.hospitaldevicesystem.enums.MessageStateEnums;
import cn.pch.hospitaldevicesystem.model.response.UserModel;
import cn.pch.hospitaldevicesystem.service.MessageService;
import cn.pch.hospitaldevicesystem.service.UserService;
import cn.pch.hospitaldevicesystem.utils.MyDateUtils;
import cn.pch.hospitaldevicesystem.utils.RestResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * Created by 潘成花 on 2021/01/23
 * @author ccxm
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;
    @Resource
    MessageService messageService;
/**
人员审核和人员列表
人员审核的参数：role=ROLE_USER
 */
    @PostMapping("/getUsersByRole")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RestResponse getUsersByRole(@RequestBody Map<String,String> registerUser){
        List<UserModel> result = userService.queryAllByRole(registerUser.get("role"));
        return RestResponse.ok(result).msg("请求成功");
    }
/**
所有人员列表
 */
    @PostMapping("/getAllUsers")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RestResponse getAllUsers(){
        List<UserModel> result = userService.queryAllUsers();
        return RestResponse.ok(result).msg("请求成功");
    }
    /**
        检查是否有此人
    */
    @PostMapping("/checkUserByName")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasAnyRole('ROLE_KEUSER') or hasAnyRole('ROLE_KPJKEUSER')")
    public RestResponse checkUserByName(@RequestBody Map<String,String> userInfo){
        log.info(":{} ", JSON.toJSONString(userInfo));
        UserModel result = userService.queryByUserName(userInfo.get("name"));
        if(result==null){
            return RestResponse.ok().msg("0");
        }else{
            return RestResponse.ok().msg(String.valueOf(result.getId()));
        }
    }

    @PostMapping("/getUserInfo")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_KEUSER') or hasRole('ROLE_KPJKEUSER') or hasRole('ROLE_USER')")
    public RestResponse getUserInfoByToken(Principal principal){
        UserModel result = userService.queryByUserName(principal.getName());
        Integer messageNum = messageService.queryAllByUserIdAndState(result.getId(), MessageStateEnums.WAIT_READ.getState()).size();
        result.setMessageNum(messageNum);
        return RestResponse.ok(result);
    }

    /**
     * 修改用户信息
     */
    @PostMapping("/updateById")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasAnyRole('ROLE_KEUSER') or hasAnyRole('ROLE_KPJKEUSER') or hasAnyRole('ROLE_USER')")
    public RestResponse updateById(Principal principal, @RequestBody Map<String,String> userInfo){
        User user = userService.queryById(Long.valueOf(userInfo.get("id")));
        user.setPassword(userInfo.get("password")==null?user.getPassword():userInfo.get("password"));
        user.setTel(userInfo.get("tel")==null?user.getTel():userInfo.get("tel"));
        user.setHeadUrl(userInfo.get("headUrl")==null?user.getHeadUrl():userInfo.get("headUrl"));
        user.setAddress(userInfo.get("address")==null?user.getAddress():userInfo.get("address"));
        user.setDepartment(userInfo.get("department")==null?user.getDepartment():userInfo.get("department"));
        user.setHospitalId(userInfo.get("hospitalId")==null?user.getHospitalId():Long.valueOf(userInfo.get("hospitalId")));
        user.setUpdateTime(MyDateUtils.GetNowDate());
        user.setUpdateName(principal.getName());
        List<UserModel> result = userService.queryAllByRole(userInfo.get("id"));
        return RestResponse.ok(result).msg("请求成功");
    }

}
