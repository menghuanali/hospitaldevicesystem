package cn.pch.hospitaldevicesystem.controller;

import cn.pch.hospitaldevicesystem.entity.Appraise;
import cn.pch.hospitaldevicesystem.entity.Device;
import cn.pch.hospitaldevicesystem.entity.Message;
import cn.pch.hospitaldevicesystem.entity.Order;
import cn.pch.hospitaldevicesystem.enums.AppraiseTypeEnums;
import cn.pch.hospitaldevicesystem.enums.DeviceStateEnums;
import cn.pch.hospitaldevicesystem.enums.MessageStateEnums;
import cn.pch.hospitaldevicesystem.enums.OrderStateEnums;
import cn.pch.hospitaldevicesystem.model.response.UserModel;
import cn.pch.hospitaldevicesystem.service.*;
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
    @Resource
    OrderService orderService;
    @Resource
    UserService userService;
    @Resource
    DeviceService deviceService;
    @Resource
    MessageService messageService;
    @Resource
    OrderLogService orderLogService;
    /**
     * 给订单评价
     */
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_KPJKEUSER') or hasRole('ROLE_YHUSER')")
    public RestResponse createAppraise(Principal principal, @RequestBody Map<String,String> appraiseInfo){
        Appraise appraise = new Appraise();
        appraise.setOrderId(Long.valueOf(appraiseInfo.get("orderId")));
        UserModel nowUser = userService.queryByUserName(principal.getName());
        appraise.setFromUserId(nowUser.getId());
        appraise.setToUserId(Long.valueOf(appraiseInfo.get("toUserId")));
        appraise.setContent(appraiseInfo.get("content"));
        appraise.setType(Integer.valueOf(appraiseInfo.get("type")));
        appraise.setCreateName(principal.getName());
        appraise.setCreateTime(MyDateUtils.GetNowDate());
        appraiseService.insertOneAppraise(appraise);
        //修改订单的状态
        Order order = orderService.queryById(Long.valueOf(appraiseInfo.get("orderId")));
        order.setState(OrderStateEnums.COMPLETE.getState());
        orderService.insertOneOrder(order);
        //修改设备的状态 如果不是报废就恢复为正常
        Device updateDevice = deviceService.queryByid(order.getDeviceId());
        if(updateDevice.getState()==DeviceStateEnums.SCRAPPED.getState()){
        }else{
            updateDevice.setState(DeviceStateEnums.NORMAL.getState());
            deviceService.insertOneDevice(updateDevice);
        }
        //给维修人员发消息
        Message message = new Message();
        message.setUserId(order.getWorkerUserId());
        message.setState(MessageStateEnums.WAIT_READ.getState());
        String msg = "你维修的订单号为:"+order.getId()+" 完成评价，评价情况为: "+ AppraiseTypeEnums.of(Integer.valueOf(appraiseInfo.get("type"))).getName()+" 评价内容为: "+appraiseInfo.get("content");
        message.setContent(msg);
        message.setCreateName(principal.getName());
        message.setCreateTime(MyDateUtils.GetNowDate());
        messageService.insertOneMessage(message);
        //保存日志
        String log  = "订单ID "+ order.getId() +" 被 "+principal.getName()+" 评价完成，评价情况为: "+ AppraiseTypeEnums.of(Integer.valueOf(appraiseInfo.get("type"))).getName()+" 评价内容为: "+appraiseInfo.get("content");
        orderLogService.insertOneLog(order.getId(),principal.getName(),log);
        return RestResponse.ok().msg("评价成功");
    }

    /**
     * 给订单评价
     */
    @PostMapping("/addcreate")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_KPJKEUSER') or hasRole('ROLE_YHUSER')")
    public RestResponse createAddAppraise(Principal principal, @RequestBody Map<String,String> appraiseInfo){
        Appraise appraise = appraiseService.queryAllByOrderId(Long.valueOf(appraiseInfo.get("orderId")));
        UserModel nowUser = userService.queryByUserName(principal.getName());
        if(nowUser.getId()!=appraise.getFromUserId()){
            return RestResponse.fail();
        }
        appraise.setType(Integer.valueOf(appraiseInfo.get("type")));
        appraise.setAppraiseAdd(appraiseInfo.get("appraiseAdd"));
        appraise.setUpdateName(principal.getName());
        appraise.setUpdateTime(MyDateUtils.GetNowDate());
        appraiseService.insertOneAppraise(appraise);
        //给维修人员发消息
        Message message = new Message();
        message.setUserId(appraise.getToUserId());
        message.setState(MessageStateEnums.WAIT_READ.getState());
        String msg = "你维修的订单号为:"+appraise.getOrderId()+" 完成追加评价，评价情况为: "+ AppraiseTypeEnums.of(Integer.valueOf(appraiseInfo.get("type"))).getName()+" 评价内容为: "+appraiseInfo.get("appraiseAdd");
        message.setContent(msg);
        message.setCreateName(principal.getName());
        message.setCreateTime(MyDateUtils.GetNowDate());
        messageService.insertOneMessage(message);
        //保存日志
        String log  = "订单ID "+ appraise.getOrderId() +" 被 "+principal.getName()+" 追评完成，评价情况为: "+ AppraiseTypeEnums.of(Integer.valueOf(appraiseInfo.get("type"))).getName()+" 评价内容为: "+appraiseInfo.get("appraiseAdd");
        orderLogService.insertOneLog(appraise.getOrderId(),principal.getName(),log);
        return RestResponse.ok().msg("追价成功");
    }

}
