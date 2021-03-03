package cn.pch.hospitaldevicesystem.controller;

import cn.pch.hospitaldevicesystem.entity.Appraise;
import cn.pch.hospitaldevicesystem.enums.AppraiseTypeEnums;
import cn.pch.hospitaldevicesystem.service.AppraiseService;
import cn.pch.hospitaldevicesystem.utils.MyDateUtils;
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
@RequestMapping("/appraise")
public class AppraiseController {

    @Resource
    AppraiseService appraiseService;
    /**
     * 给订单评价
     */
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_KPJKEUSER')")
    public RestResponse createApply(Principal principal, @RequestBody Map<String,String> appraiseInfo){
        Appraise appraise = new Appraise();
        appraise.setOrderId(Long.valueOf(appraiseInfo.get("orderId")));
        appraise.setFromUserId(Long.valueOf(appraiseInfo.get("fromUserId")));
        appraise.setToUserId(Long.valueOf(appraiseInfo.get("toUserId")));
        appraise.setContent(appraiseInfo.get("content"));
        appraise.setType(Integer.valueOf(appraiseInfo.get("type")));
        appraise.setAppraiseAdd(appraiseInfo.get("appraiseAdd"));
        appraise.setCreateName(principal.getName());
        appraise.setCreateTime(MyDateUtils.GetNowDate());
        appraiseService.insertOneAppraise(appraise);
        if(Integer.valueOf(appraiseInfo.get("type"))< AppraiseTypeEnums.ADD_GOOD_APPRAISE.getCode()){
            return RestResponse.ok().msg("评价成功");
        }else{
            return RestResponse.ok().msg("追价成功");
        }
    }

}
