package cn.pch.hospitaldevicesystem.controller;

import cn.pch.hospitaldevicesystem.service.HospitalService;
import cn.pch.hospitaldevicesystem.utils.RestResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by 潘成花 on 2021/01/23
 */
@RestController
@RequestMapping("/hospital")
public class HospitalController {

    @Resource
    HospitalService hospitalService;
    @PostMapping("/getAll")
    public RestResponse getAll(){
        return RestResponse.ok(hospitalService.queryAll());
    }
}
