package cn.pch.hospitaldevicesystem.controller;

import cn.pch.hospitaldevicesystem.entity.Device;
import cn.pch.hospitaldevicesystem.model.response.DeviceModel;
import cn.pch.hospitaldevicesystem.service.DeviceService;
import cn.pch.hospitaldevicesystem.utils.RestResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
}
