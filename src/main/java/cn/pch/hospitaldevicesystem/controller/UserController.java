package cn.pch.hospitaldevicesystem.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.StrUtil;
import cn.pch.hospitaldevicesystem.entity.User;
import cn.pch.hospitaldevicesystem.enums.MessageStateEnums;
import cn.pch.hospitaldevicesystem.enums.RoleEnums;
import cn.pch.hospitaldevicesystem.model.response.UserModel;
import cn.pch.hospitaldevicesystem.service.MessageService;
import cn.pch.hospitaldevicesystem.service.UserService;
import cn.pch.hospitaldevicesystem.utils.MyDateUtils;
import cn.pch.hospitaldevicesystem.utils.RestResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
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
    public RestResponse checkUserByName(@RequestBody Map<String,String> userInfo){
        log.info(":{} ", JSON.toJSONString(userInfo));
        UserModel result = userService.queryByUserName(userInfo.get("name"));
        if(result==null){
            return RestResponse.ok().msg("0");
        }else{
            return RestResponse.ok().msg(String.valueOf(result.getId()));
        }
    }

    /*
        根据token查询用户信息
    */
    @PostMapping("/getUserInfo")
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
    public RestResponse updateById(Principal principal, @RequestBody Map<String,String> userInfo){
        User user = userService.queryById(Long.valueOf(userInfo.get("id")));
        user.setPassword(StrUtil.hasBlank(userInfo.get("password"))?user.getPassword():bCryptPasswordEncoder.encode(userInfo.get("password")));
        user.setTel(StrUtil.hasBlank(userInfo.get("tel"))?user.getTel():userInfo.get("tel"));
        user.setHeadUrl(StrUtil.hasBlank(userInfo.get("headUrl"))?user.getHeadUrl():userInfo.get("headUrl"));
        user.setAddress(StrUtil.hasBlank(userInfo.get("address"))?user.getAddress():userInfo.get("address"));
        user.setUsername(StrUtil.hasBlank(userInfo.get("username"))?user.getUsername():userInfo.get("username"));
        user.setUpdateTime(MyDateUtils.GetNowDate());
        user.setUpdateName(principal.getName());
        userService.insertOneUser(user);
        return RestResponse.ok().msg("修改成功");
    }

    /**
     * 修改用户权限单独拿出来
     */
    @PostMapping("/updateRoleById")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RestResponse updateRoleById(Principal principal, @RequestBody Map<String,String> userInfo){
        User user = userService.queryById(Long.valueOf(userInfo.get("id")));
        user.setRole(StrUtil.hasBlank(userInfo.get("role"))?user.getRole():userInfo.get("role"));
        user.setPassword(StrUtil.hasBlank(userInfo.get("password"))?user.getPassword():bCryptPasswordEncoder.encode(userInfo.get("password")));
        user.setTel(StrUtil.hasBlank(userInfo.get("tel"))?user.getTel():userInfo.get("tel"));
        user.setHeadUrl(StrUtil.hasBlank(userInfo.get("headUrl"))?user.getHeadUrl():userInfo.get("headUrl"));
        user.setAddress(StrUtil.hasBlank(userInfo.get("address"))?user.getAddress():userInfo.get("address"));
        user.setDepartment(StrUtil.hasBlank(userInfo.get("department"))?user.getDepartment():userInfo.get("department"));
        user.setHospitalId(StrUtil.hasBlank(userInfo.get("hospitalId"))?user.getHospitalId():Long.valueOf(userInfo.get("hospitalId")));
        user.setUpdateTime(MyDateUtils.GetNowDate());
        user.setUpdateName(principal.getName());
        userService.insertOneUser(user);
        return RestResponse.ok().msg("管理员修改成功");
    }

    /*
        得到权限枚举信息
    */
    @PostMapping("/getRoleEnums")
    public RestResponse getRoleEnums(){
        List<Object> names = EnumUtil.getFieldValues(RoleEnums.class, "name");
        List<Object> states = EnumUtil.getFieldValues(RoleEnums.class, "code");
        List<Object> result = new ArrayList<>();
        result.add(names);
        result.add(states);
        return RestResponse.ok(result);
    }

    /*
        物理删除信息
    */
    @PostMapping("/deleteUserById")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RestResponse deleteUserById(@RequestBody Map<String,String> userInfo){
        userService.removeByid(Long.valueOf(userInfo.get("id")));
        return RestResponse.ok().msg("删除成功");
    }

    /*
    上传用户图片
*/
    @PostMapping("/upHeadPicture")
    public RestResponse uploadHeadPicture(@RequestParam("personPicture") MultipartFile uploadFile){
        if (uploadFile.isEmpty()) {
            return RestResponse.fail("上传文件不能为空");
        }
        //获取原文件名
        String name=uploadFile.getOriginalFilename();
        //获取文件后缀
        String subffix=name.substring(name.lastIndexOf(".")+1,name.length());
        //控制格式
        if(subffix.equals("")&&!subffix.equals("jpg")&&!subffix.equals("jpeg")&&!subffix.equals("png"))
        {
            return RestResponse.fail("上传文件格式不对");
        }
        //新的文件名以日期命名
        String fileName = DateUtil.format(new Date(), "yyyyMMddHHmmss")+"."+subffix;
        //获取项目路径
        String path =  ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/images/headimgs";
        //“windows下使用的是“\”作为分隔符,而linux则反其道而行之使用"/"作为分隔符。
        String realPath = path.replace('/', '\\').substring(1,path.length());
        File file = new File(realPath,fileName);
        try {
            uploadFile.transferTo(file);
            System.out.println("图片上传成功 路径:");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //用于查看路径是否正确
        System.out.println(realPath+"\\"+fileName);
        return RestResponse.ok("static/images/headimgs/"+fileName);
    }

    /*
        检查密码正确性
    */
    @PostMapping("/checkPassword")
    public RestResponse checkPassword(@RequestBody Map<String,String> userInfo){
        log.info(":{} ", JSON.toJSONString(userInfo));
        User user = userService.queryById(Long.valueOf(userInfo.get("id")));
        log.info(":{} ", JSON.toJSONString(user));
        if(user==null||!bCryptPasswordEncoder.matches(userInfo.get("password"),user.getPassword())){
            return RestResponse.ok().msg("0");
        }else{
            return RestResponse.ok().msg(String.valueOf(user.getId()));
        }
    }

}
