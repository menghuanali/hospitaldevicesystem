package cn.pch.hospitaldevicesystem.controller;

import cn.pch.hospitaldevicesystem.model.request.ApplyCreateModel;
import cn.pch.hospitaldevicesystem.service.ApplyService;
import cn.pch.hospitaldevicesystem.service.UserService;
import cn.pch.hospitaldevicesystem.utils.RestResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Map;

/**
 * Created by 潘成花 on 2021/01/23
 */
@RestController
@RequestMapping("/apply")
public class ApplyController {
    @Resource
    ApplyService applyService;
    @Resource
    UserService userService;

    /**
     * 创建一个申请单也就是故障申报
     */
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_YHUSER')")
    public RestResponse createApply(Principal principal, @RequestBody Map<String,String> applyMapModel){
        ApplyCreateModel applyCreateModel = new ApplyCreateModel();
        applyCreateModel.setContent(applyMapModel.get("content"));
        applyCreateModel.setState(Integer.valueOf(applyMapModel.get("state")));
        applyCreateModel.setDeviceId(Long.valueOf(applyMapModel.get("deviceId")));
        applyCreateModel.setFromHospitalId(Long.valueOf(applyMapModel.get("fromHospitalId")));
        applyCreateModel.setFromType(Integer.valueOf(applyMapModel.get("fromType")));
        applyCreateModel.setFromUserId(userService.queryIdByUserName(principal.getName()));
        applyCreateModel.setCreateName(principal.getName());
        applyService.insertOneApply(applyCreateModel);
        return RestResponse.ok().msg("申请成功");
    }

    /*
        申报审核
    */
    @PostMapping("/queryApply")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_KEUSER') or hasRole('ROLE_KPJKEUSER')")
    public RestResponse queryApply(){
        return RestResponse.ok(applyService.queryAll());
    }
}
