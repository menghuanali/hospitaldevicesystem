package cn.pch.hospitaldevicesystem.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.EnumUtil;
import cn.pch.hospitaldevicesystem.entity.Device;
import cn.pch.hospitaldevicesystem.enums.DeviceStateEnums;
import cn.pch.hospitaldevicesystem.service.DeviceService;
import cn.pch.hospitaldevicesystem.utils.MyDateUtils;
import cn.pch.hospitaldevicesystem.utils.RestResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by 潘成花 on 2021/01/23
 */
@Slf4j
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
    新增加设备
    */
    @PostMapping("/addDevice")
    public RestResponse addDevice(@RequestBody Map<String,String> deviceInfo){
        log.info("新增加的设备信息:{} ", JSON.toJSONString(deviceInfo));
        Device device = new Device();
        device.setHospitalId(Long.valueOf(deviceInfo.get("hospitalId")));
        device.setAdminUserId(Long.valueOf(deviceInfo.get("adminUserId")));
        device.setState(Integer.valueOf(deviceInfo.get("stateValue")));
        device.setPictureUrl(deviceInfo.get("pictureUrl"));
        device.setName(deviceInfo.get("name"));
        device.setCreateName(deviceInfo.get("adminUserName"));
        device.setCreateTime(MyDateUtils.GetNowDate());
        deviceService.insertOneDevice(device);
        return RestResponse.ok("新增成功");
    }

    /*
    删除设备
    */
    @PostMapping("/deleteDevice")
    public RestResponse deleteDevice(@RequestBody Map<String,String> deviceInfo){
        log.info("删除设备主键id:{} ", JSON.toJSONString(deviceInfo));
        deviceService.removeByid(Long.valueOf(deviceInfo.get("deviceId")));
        return RestResponse.ok("删除成功");
    }
    /*
    修改设备状态
    */
    @PostMapping("/updateDevice")
    public RestResponse updateDevice(@RequestBody Map<String,String> deviceInfo){
        log.info("修改设备主键id和将要修改的状态:{} ", JSON.toJSONString(deviceInfo));
        Device updateDevice = deviceService.queryByid(Long.valueOf(deviceInfo.get("deviceId")));
        updateDevice.setState(Integer.valueOf(deviceInfo.get("state")));
        deviceService.insertOneDevice(updateDevice);
        return RestResponse.ok("修改成功");
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
        //获取项目路径
        String path =  ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/images/devices";
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
        return RestResponse.ok("static/images/devices/"+fileName);
    }
}
