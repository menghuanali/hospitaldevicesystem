package cn.pch.hospitaldevicesystem.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.EnumUtil;
import cn.pch.hospitaldevicesystem.enums.DeviceStateEnums;
import cn.pch.hospitaldevicesystem.service.DeviceService;
import cn.pch.hospitaldevicesystem.utils.RestResponse;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 潘成花 on 2021/01/23
 */
@RestController
@RequestMapping("/device")
public class DeviceController {

    @Resource
    DeviceService deviceService;


    /*
        得到所有的设备id降序
    */
    @PostMapping("/getAll")
    public RestResponse getAll(){
        return RestResponse.ok(deviceService.queryAllDevice());
    }
    /*
    得到设备状态枚举
    */
    @PostMapping("/getStateEnums")
    public RestResponse getStateEnums(){
        List<Object> names = EnumUtil.getFieldValues(DeviceStateEnums.class, "name");
        List<Object> states = EnumUtil.getFieldValues(DeviceStateEnums.class, "state");
        List<Object> result = new ArrayList<>();
        result.add(names);
        result.add(states);
        return RestResponse.ok(result);
    }
    /*
        上传设备图片
    */
    @PostMapping("/upDevicePicture")
    public RestResponse uploadDevicePicture(@RequestParam("devicePicture") MultipartFile uploadFile){
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
        System.out.println(fileName);
        //获取项目路径
        String path =  ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/images/devices";
        String realPath = path.replace('/', '\\').substring(1,path.length());
        File file = new File(realPath,fileName);
        try {
            uploadFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //用于查看路径是否正确
        System.out.println(realPath);
        return RestResponse.ok("static/images/devices/"+fileName);
    }
}
