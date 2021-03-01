package cn.pch.hospitaldevicesystem.controller;

import cn.pch.hospitaldevicesystem.service.OrderLogService;
import cn.pch.hospitaldevicesystem.utils.RestResponse;
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
@RequestMapping("/orderlog")
public class OrderLogController {

    @Resource
    OrderLogService orderLogService;

    /**
     * 得到订单的所有日志
     */
    @PostMapping("getLogsById")
    public RestResponse getLogsById(@RequestBody Map<String,String> logInfo){
        return RestResponse.ok(orderLogService.queryAllByOrderId(Long.valueOf(logInfo.get("orderId"))));
    }

}
