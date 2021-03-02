package cn.pch.hospitaldevicesystem.controller;

import cn.pch.hospitaldevicesystem.entity.Message;
import cn.pch.hospitaldevicesystem.entity.Order;
import cn.pch.hospitaldevicesystem.enums.ApplyTypeEnums;
import cn.pch.hospitaldevicesystem.enums.HospitalEnums;
import cn.pch.hospitaldevicesystem.enums.MessageStateEnums;
import cn.pch.hospitaldevicesystem.enums.OrderStateEnums;
import cn.pch.hospitaldevicesystem.service.MessageService;
import cn.pch.hospitaldevicesystem.service.OrderLogService;
import cn.pch.hospitaldevicesystem.service.OrderService;
import cn.pch.hospitaldevicesystem.utils.MyDateUtils;
import cn.pch.hospitaldevicesystem.utils.RestResponse;
import org.aspectj.weaver.ast.Or;
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
@RequestMapping("/order")
public class OrderController {

    @Resource
    OrderService orderService;
    @Resource
    OrderLogService orderLogService;
    @Resource
    MessageService messageService;
    /**
     * 根据医护人员的电话新增一个订单
     * 权限客服和管理员
     */
    @PostMapping("/addOrder")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_KEUSER') or hasRole('ROLE_KPJKEUSER')")
    public RestResponse addOrder(Principal principal, @RequestBody Map<String,String> orderInfo){
        Order order = new Order();
        order.setState(OrderStateEnums.WAIT_ACCEPT.getState());
        order.setDeviceId(Long.valueOf(orderInfo.get("deviceId")));
        order.setDoctorUserId(Long.valueOf(orderInfo.get("doctorUserId")));
        order.setHospitalId(Long.valueOf(orderInfo.get("hospitalId")));
        order.setType(ApplyTypeEnums.COMEFROM_PHONE.getState());
        order.setCreateName(principal.getName());
        order.setCreateTime(MyDateUtils.GetNowDate());
        Order saveOrder = orderService.insertOneOrder(order);
        //保存日志
        String log = "来自 "+ HospitalEnums.of(Long.valueOf(orderInfo.get("hospitalId"))) +" 医院工作人员ID为: "+orderInfo.get("doctorUserId")+
                " 通过电话订单说明并创建了维修单号: "+saveOrder.getId()+"等待派单";
        orderLogService.insertOneLog(order.getId(),log,principal.getName());
        //给客户发消息说明电话订单申请成功
        Message message = new Message();
        message.setUserId(Long.valueOf(orderInfo.get("doctorUserId")));
        message.setState(MessageStateEnums.WAIT_READ.getState());
        String msg = "很高兴接到您的来电，你的故障申报已经通过电话申请成功，维修单号为:"+saveOrder.getId()+"将尽快为您解决问题";
        message.setContent(msg);
        message.setCreateName(principal.getName());
        message.setCreateTime(MyDateUtils.GetNowDate());
        messageService.insertOneMessage(message);
        return RestResponse.ok("电话新增订单成功");
    }



}
