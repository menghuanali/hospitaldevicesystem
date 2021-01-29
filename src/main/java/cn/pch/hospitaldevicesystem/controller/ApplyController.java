package cn.pch.hospitaldevicesystem.controller;

import cn.pch.hospitaldevicesystem.model.request.ApplyCreateModel;
import cn.pch.hospitaldevicesystem.service.ApplyService;
import cn.pch.hospitaldevicesystem.utils.RestResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by 潘成花 on 2021/01/23
 */
@RestController
@RequestMapping("/apply")
public class ApplyController {
    @Resource
    ApplyService applyService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_YHUSER')")
    public RestResponse createApply(@RequestBody Map<String,String> tempMapModel){
        ApplyCreateModel applyCreateModel = new ApplyCreateModel();


        return RestResponse.ok().msg("请求成功");
    }
}
