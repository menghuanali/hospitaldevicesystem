package cn.pch.hospitaldevicesystem.controller;

import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.StrUtil;
import cn.pch.hospitaldevicesystem.entity.Message;
import cn.pch.hospitaldevicesystem.entity.Order;
import cn.pch.hospitaldevicesystem.enums.ApplyTypeEnums;
import cn.pch.hospitaldevicesystem.enums.HospitalEnums;
import cn.pch.hospitaldevicesystem.enums.MessageStateEnums;
import cn.pch.hospitaldevicesystem.enums.OrderStateEnums;
import cn.pch.hospitaldevicesystem.model.response.OrderModel;
import cn.pch.hospitaldevicesystem.service.MessageService;
import cn.pch.hospitaldevicesystem.service.OrderLogService;
import cn.pch.hospitaldevicesystem.service.OrderService;
import cn.pch.hospitaldevicesystem.utils.MyDateUtils;
import cn.pch.hospitaldevicesystem.utils.RestResponse;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by 潘成花 on 2021/01/23
 */
@Slf4j
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
    /*
        查询出指定条件的订单
    */
    @PostMapping("/listOrder")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RestResponse listOrder(Principal principal, @RequestBody Map<String,String> orderInfo){
        Order order = new Order();
        order.setState(StrUtil.hasBlank(orderInfo.get("state"))?null:Integer.valueOf(orderInfo.get("state")));
        order.setId(StrUtil.hasBlank(orderInfo.get("orderId"))?null:Long.valueOf(orderInfo.get("orderId")));
        order.setDoctorUserId(StrUtil.hasBlank(orderInfo.get("userId"))?null:Long.valueOf(orderInfo.get("userId")));
        order.setWorkerUserId(StrUtil.hasBlank(orderInfo.get("workerUserId"))?null:Long.valueOf(orderInfo.get("workerUserId")));
        order.setHospitalId(StrUtil.hasBlank(orderInfo.get("hospitalId"))?null:Long.valueOf(orderInfo.get("hospitalId")));
        order.setCreateName(StrUtil.hasBlank(orderInfo.get("createName"))?null:orderInfo.get("createName"));
        order.setDelete(0);
        List<OrderModel> result = orderService.queryAllByExample(order);
        log.info(":{} ", JSON.toJSONString(result));
        if(orderInfo.get("startTime")==null){
            return RestResponse.ok(result);
        }else{
            result = result.stream().filter(o -> o.getCreateTime().compareTo(orderInfo.get("startTime"))>=0&&o.getCreateTime().compareTo(orderInfo.get("endTime"))<=0).collect(Collectors.toList());
            log.info("过滤后:{} ", JSON.toJSONString(result));
            return RestResponse.ok(result);
        }

    }

    /*
    订单处理 查询出指定1 2 5 6 状态下的订单
    */
    @PostMapping("/listProcessOrder")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RestResponse listProcessOrder(){
        return RestResponse.ok(orderService.queryProcessOrder());
    }

    /*
        分派订单 调单
    */
    @PostMapping("/portionOrder")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RestResponse portionOrder(Principal principal, @RequestBody Map<String,String> orderInfo){
        Order order = orderService.queryById(Long.valueOf(orderInfo.get("id")));
        order.setWorkerUserId(Long.valueOf(orderInfo.get("workerUserId")));
        order.setState(OrderStateEnums.PROCESSING.getState());//维修人员处理中
        orderService.insertOneOrder(order);
        return RestResponse.ok("处理成功");
    }

    /*
        延误订单
    */
    @PostMapping("/delayOrder")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RestResponse delayOrder(Principal principal, @RequestBody Map<String,String> orderInfo){
        Order order = orderService.queryById(Long.valueOf(orderInfo.get("id")));
        order.setState(OrderStateEnums.DETAL.getState());//维修人员处理中
        orderService.insertOneOrder(order);
        return RestResponse.ok("延误成功");
    }

    /*
    删除订单
    */
    @PostMapping("/deleteOrder")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RestResponse deleteOrder(Principal principal, @RequestBody Map<String,String> orderInfo){
        orderService.removeByid(Long.valueOf(orderInfo.get("id")));
        return RestResponse.ok("删除成功");
    }

    /*
    得到订单状态枚举
    */
    @PostMapping("/getStateEnums")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RestResponse getStateEnums(){
        List<Object> names = EnumUtil.getFieldValues(OrderStateEnums.class, "name");
        List<Object> states = EnumUtil.getFieldValues(OrderStateEnums.class, "state");
        List<Object> result = new ArrayList<>();
        result.add(names);
        result.add(states);
        return RestResponse.ok(result);
    }



}
