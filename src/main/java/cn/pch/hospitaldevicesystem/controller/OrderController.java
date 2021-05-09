package cn.pch.hospitaldevicesystem.controller;

import cn.hutool.core.util.EnumUtil;
import cn.hutool.core.util.StrUtil;
import cn.pch.hospitaldevicesystem.entity.Device;
import cn.pch.hospitaldevicesystem.entity.Message;
import cn.pch.hospitaldevicesystem.entity.Order;
import cn.pch.hospitaldevicesystem.enums.*;
import cn.pch.hospitaldevicesystem.model.response.OrderInfoModel;
import cn.pch.hospitaldevicesystem.model.response.OrderModel;
import cn.pch.hospitaldevicesystem.model.response.UserModel;
import cn.pch.hospitaldevicesystem.service.*;
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
    @Resource
    UserService userService;
    @Resource
    DeviceService deviceService;
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
        String log = "来自 "+ HospitalEnums.of(Long.valueOf(orderInfo.get("hospitalId"))).getName() +" 医院工作人员ID为: "+orderInfo.get("doctorUserId")+
                " 通过电话订单说明并创建了维修订单号: "+saveOrder.getId()+"等待派单";
        orderLogService.insertOneLog(order.getId(),principal.getName(),log);
        //给客户发消息说明电话订单申请成功
        Message message = new Message();
        message.setUserId(Long.valueOf(orderInfo.get("doctorUserId")));
        message.setState(MessageStateEnums.WAIT_READ.getState());
        String msg = "很高兴接到您的来电，你的故障申报已经通过电话申请成功，维修订单号为:"+saveOrder.getId()+"将尽快为您解决问题";
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
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_KEUSER') or hasRole('ROLE_KPJKEUSER')")
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
        log.info("查询到的:{} ", JSON.toJSONString(result));
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
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_KEUSER') or hasRole('ROLE_KPJKEUSER')")
    public RestResponse listProcessOrder(){
        return RestResponse.ok(orderService.queryProcessOrder());
    }

    /*
        分派订单 调单
    */
    @PostMapping("/portionOrder")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_KEUSER') or hasRole('ROLE_KPJKEUSER')")
    public RestResponse portionOrder(Principal principal, @RequestBody Map<String,String> orderInfo){
        Order order = orderService.queryById(Long.valueOf(orderInfo.get("id")));
        order.setWorkerUserId(Long.valueOf(orderInfo.get("workerUserId")));
        order.setState(OrderStateEnums.PROCESSING.getState());//维修人员待确认中
        orderService.insertOneOrder(order);
        //保存日志
        log.info("分派调单详情:{} ", JSON.toJSONString(orderInfo));
        String log = "";
        if(!StrUtil.hasBlank(orderInfo.get("portiondialogVisible"))&&orderInfo.get("portiondialogVisible").equals("true")){
            log = "订单ID "+ order.getId() +" 分派给 "+orderInfo.get("workerUserName")+" 等待确认中";
        }else{
            log = "订单ID "+ order.getId() +" 调度给 "+orderInfo.get("workerUserName")+" 等待确认中";
        }
        orderLogService.insertOneLog(order.getId(),principal.getName(),log);
        //给客户发消息说明
        Message message = new Message();
        message.setUserId(order.getDoctorUserId());
        message.setState(MessageStateEnums.WAIT_READ.getState());
        String msg = "你申请的维修订单号为:"+order.getId()+"已经分派了维修师傅："+orderInfo.get("workerUserName")+" 请耐心等待";
        message.setContent(msg);
        message.setCreateName(principal.getName());
        message.setCreateTime(MyDateUtils.GetNowDate());
        messageService.insertOneMessage(message);

        return RestResponse.ok("处理成功");
    }

    /*
        延误订单
    */
    @PostMapping("/delayOrder")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_KEUSER') or hasRole('ROLE_KPJKEUSER')")
    public RestResponse delayOrder(Principal principal, @RequestBody Map<String,String> orderInfo){
        Order order = orderService.queryById(Long.valueOf(orderInfo.get("id")));
        order.setState(OrderStateEnums.DETAL.getState());//订单延误
        orderService.insertOneOrder(order);
        //保存日志
        String log  = "订单ID "+ order.getId() +" 被延误 ";
        orderLogService.insertOneLog(order.getId(),principal.getName(),log);
        //给客户发消息说明订单延误
        Message message = new Message();
        message.setUserId(order.getDoctorUserId());
        message.setState(MessageStateEnums.WAIT_READ.getState());
        String msg = "你申请的维修订单号为:"+order.getId()+"非常抱歉延误了!请耐心等待";
        message.setContent(msg);
        message.setCreateName(principal.getName());
        message.setCreateTime(MyDateUtils.GetNowDate());
        messageService.insertOneMessage(message);
        //给维修人员发消息说明订单延误
        message.setUserId(order.getWorkerUserId());
        message.setState(MessageStateEnums.WAIT_READ.getState());
        msg = "你处理的维修订单号为:"+order.getId()+"延误了!请尽快处理";
        message.setContent(msg);
        message.setCreateName(principal.getName());
        message.setCreateTime(MyDateUtils.GetNowDate());
        messageService.insertOneMessage(message);
        return RestResponse.ok("延误成功");
    }

    /*
    删除订单
    */
    @PostMapping("/deleteOrder")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_KEUSER') or hasRole('ROLE_KPJKEUSER')")
    public RestResponse deleteOrder(Principal principal, @RequestBody Map<String,String> orderInfo){
        Order order = orderService.queryById(Long.valueOf(orderInfo.get("id")));
        orderService.removeByid(Long.valueOf(orderInfo.get("id")));
        //保存日志
        String log  = "订单ID "+ orderInfo.get("id") +" 被删除 ";
        orderLogService.insertOneLog(Long.valueOf(orderInfo.get("id")),principal.getName(),log);
        //给客户发消息说明订单延误
        Message message = new Message();
        message.setUserId(order.getDoctorUserId());
        message.setState(MessageStateEnums.WAIT_READ.getState());
        String msg = "你申请的维修订单号为:"+order.getId()+"已经被删除，如有疑问请联系客服或者重新申请。";
        message.setContent(msg);
        message.setCreateName(principal.getName());
        message.setCreateTime(MyDateUtils.GetNowDate());
        messageService.insertOneMessage(message);
        return RestResponse.ok("删除成功");
    }

    /*
    得到订单状态枚举
    */
    @PostMapping("/getStateEnums")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_KEUSER') or hasRole('ROLE_KPJKEUSER')")
    public RestResponse getStateEnums(){
        List<Object> names = EnumUtil.getFieldValues(OrderStateEnums.class, "name");
        List<Object> states = EnumUtil.getFieldValues(OrderStateEnums.class, "state");
        List<Object> result = new ArrayList<>();
        result.add(names);
        result.add(states);
        return RestResponse.ok(result);
    }

    /*
        订单详情
    */
    @PostMapping("/getOrderDetails")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_KEUSER') or hasRole('ROLE_KPJKEUSER')")
    public RestResponse getOrderDetails(@RequestBody Map<String,String> orderInfo){
        OrderModel orderModel = orderService.queryOrderModelById(Long.valueOf(orderInfo.get("id")));
        return RestResponse.ok(orderModel);
    }
    /*
        订单统计
    */
    @PostMapping("/getOrdersStatistics")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_KEUSER') or hasRole('ROLE_KPJKEUSER')")
    public RestResponse getOrdersStates(){
        List<List<Object>> result = new ArrayList<>();
        List<Object> hospitalName = new ArrayList<>();
        List<Object> hospitalNumber = new ArrayList<>();
        List<Object> applyName = new ArrayList<>();
        List<Object> applyNumber = new ArrayList<>();
        List<Object> timeName = new ArrayList<>();
        List<Object> timeNumber = new ArrayList<>();
        //得到各医院的订单总数
        List<OrderInfoModel> dbResultH = orderService.queryOrderNumGroupByHospital();
        for(int i=0;i<dbResultH.size();i++){
            hospitalName.add(HospitalEnums.of(Long.valueOf(dbResultH.get(i).getKeyName())).getName());
            hospitalNumber.add(dbResultH.get(i).getKeyValue());
        }
        result.add(hospitalName);
        result.add(hospitalNumber);
        //得到订单申请分类
        List<OrderInfoModel> dbResultA = orderService.queryOrderNumGroupByApplyType();
        for(int i=0;i<dbResultA.size();i++){
            applyName.add(ApplyTypeEnums.of(dbResultA.get(i).getKeyName()).getName());
            applyNumber.add(dbResultA.get(i).getKeyValue());
        }
        result.add(applyName);
        result.add(applyNumber);
        //得到近一周的订单详情
        List<Order> dbResultC = orderService.queryOrderNumByTime(MyDateUtils.GetNowDateRiQi(-6));
        log.info("近一周的订单量:{} ", JSON.toJSONString(dbResultC));
        for(int j=-6;j<=0;j++){
            String tempTime = MyDateUtils.GetNowDateRiQi(j);
            timeName.add(tempTime);
            int num = 0;
            for(int i=0;i<dbResultC.size();i++){
                if(dbResultC.get(i).getCreateTime().compareTo(MyDateUtils.GetNowDateRiQi(j+1))<=0){
                    num++;
                    dbResultC.remove(i);
                }
            }
            timeNumber.add(num);
        }
        result.add(timeName);
        result.add(timeNumber);
        return RestResponse.ok(result);
    }

    /*
    查询该用户下待签收的订单
    */
    @PostMapping("/getOrderSigning")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_WXUSER')")
    public RestResponse getOrderSigning(Principal principal){
        UserModel nowUser = userService.queryByUserName(principal.getName());
        List<OrderModel> result = orderService.queryOrderByUserNameAndState(nowUser.getId(),OrderStateEnums.PROCESSING.getState());
        List<OrderModel> yawuresult = orderService.queryOrderByUserNameAndState(nowUser.getId(),OrderStateEnums.DETAL.getState());
        yawuresult.addAll(result);
        return RestResponse.ok(yawuresult);
    }

    /*
    查询该用户下已经签收的订单
    */
    @PostMapping("/getOrderSigned")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_WXUSER')")
    public RestResponse getOrderSigned(Principal principal){
        UserModel nowUser = userService.queryByUserName(principal.getName());
        List<OrderModel> result = orderService.queryOrderByUserNameAndState(nowUser.getId(),OrderStateEnums.BECONFIRMED.getState());
        List<OrderModel> allresult = orderService.queryOrderByUserNameAndState(nowUser.getId(),OrderStateEnums.WAIT_OPINION.getState());
        result.addAll(allresult);
        return RestResponse.ok(result);
    }
    /*
    查询该用户下已经申请且没有完成的订单
    */
    @PostMapping("/getOrderGoingByUser")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_YHUSER')")
    public RestResponse getOrderGoingByUser(Principal principal){
        UserModel nowUser = userService.queryByUserName(principal.getName());
        return RestResponse.ok(orderService.queryOrderByUserIdAndGoing(nowUser.getId()));
    }
    /*
    查询该用户下已经历史的订单
    */
    @PostMapping("/getOrderHistoryByUser")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_WXUSER') or hasRole('ROLE_YHUSER')")
    public RestResponse getOrderHistoryByUser(Principal principal){
        UserModel nowUser = userService.queryByUserName(principal.getName());
        return RestResponse.ok(orderService.queryHistoryOrderByUserName(nowUser.getId()));
    }
    /*
        确认签收订单 其实就是修改状态为7
    */
    @PostMapping("/uptateOrderStateConfirm")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_WXUSER')")
    public RestResponse uptateOrderStateConfirm(Principal principal,@RequestBody Map<String,String> orderInfo){
        Order nowOrder = orderService.queryById(Long.valueOf(orderInfo.get("id")));
        nowOrder.setState(OrderStateEnums.BECONFIRMED.getState());
        orderService.updateOrder(nowOrder);
        //保存日志
        String log  = "订单ID "+ orderInfo.get("id") +" 被 "+principal.getName()+" 确认签收 ";
        orderLogService.insertOneLog(Long.valueOf(orderInfo.get("id")),principal.getName(),log);
        //给客户发消息说明
        Message message = new Message();
        message.setUserId(nowOrder.getDoctorUserId());
        message.setState(MessageStateEnums.WAIT_READ.getState());
        String msg = "你申请的维修订单号为:"+nowOrder.getId()+"被维修师傅："+principal.getName()+" 确认签收，请耐心等待维修";
        message.setContent(msg);
        message.setCreateName(principal.getName());
        message.setCreateTime(MyDateUtils.GetNowDate());
        messageService.insertOneMessage(message);
        //修改设备状态为 维修后待维修
        Device updateDevice = deviceService.queryByid(nowOrder.getDeviceId());
        updateDevice.setState(DeviceStateEnums.WAIT_REPAIR.getState());
        deviceService.insertOneDevice(updateDevice);
        return RestResponse.ok().msg("确认签收订单成功");
    }

    /*
    确认拒绝订单 其实就是修改状态为1 清空维修人员
    */
    @PostMapping("/uptateOrderStateReject")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_WXUSER')")
    public RestResponse uptateOrderStateReject(Principal principal,@RequestBody Map<String,String> orderInfo){
        Order nowOrder = orderService.queryById(Long.valueOf(orderInfo.get("id")));
        nowOrder.setState(OrderStateEnums.WAIT_ACCEPT.getState());
        nowOrder.setWorkerUserId(null);
        nowOrder.setUpdateName(principal.getName());
        nowOrder.setUpdateTime(MyDateUtils.GetNowDate());
        orderService.updateOrder(nowOrder);
        //保存日志
        String log  = "订单ID "+ orderInfo.get("id") +" 被 "+principal.getName()+" 拒绝签收 ";
        orderLogService.insertOneLog(Long.valueOf(orderInfo.get("id")),principal.getName(),log);
        //给客户发消息说明
        Message message = new Message();
        message.setUserId(nowOrder.getDoctorUserId());
        message.setState(MessageStateEnums.WAIT_READ.getState());
        String msg = "你申请的维修订单号为:"+nowOrder.getId()+"被维修师傅："+principal.getName()+" 拒绝签收，请耐心等待重新分派";
        message.setContent(msg);
        message.setCreateName(principal.getName());
        message.setCreateTime(MyDateUtils.GetNowDate());
        messageService.insertOneMessage(message);
        return RestResponse.ok().msg("拒绝签收订单成功");
    }
    /*
    确认完成订单 其实就是修改状态为3
    */
    @PostMapping("/uptateOrderStateComplete")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_WXUSER')")
    public RestResponse uptateOrderStateComplete(Principal principal,@RequestBody Map<String,String> orderInfo){
        Order nowOrder = orderService.queryById(Long.valueOf(orderInfo.get("id")));
        nowOrder.setState(OrderStateEnums.WAIT_OPINION.getState());
        nowOrder.setUpdateName(principal.getName());
        nowOrder.setUpdateTime(MyDateUtils.GetNowDate());
        orderService.updateOrder(nowOrder);
        //保存日志
        String log  = "订单ID "+ orderInfo.get("id") +" 被 "+principal.getName()+" 维修完成 维修情况:"+orderInfo.get("content");;
        orderLogService.insertOneLog(Long.valueOf(orderInfo.get("id")),principal.getName(),log);
        //给客户发消息说明
        Message message = new Message();
        message.setUserId(nowOrder.getDoctorUserId());
        message.setState(MessageStateEnums.WAIT_READ.getState());
        String msg = "你申请的维修订单号为:"+nowOrder.getId()+"被维修师傅："+principal.getName()+" 维修完成，请及时评价.维修情况:"+orderInfo.get("content");
        message.setContent(msg);
        message.setCreateName(principal.getName());
        message.setCreateTime(MyDateUtils.GetNowDate());
        messageService.insertOneMessage(message);
        //修改设备状态为 维修后待确定
        Device updateDevice = deviceService.queryByid(nowOrder.getDeviceId());
        updateDevice.setState(DeviceStateEnums.COMFIRM.getState());
        deviceService.insertOneDevice(updateDevice);
        return RestResponse.ok().msg("订单完成成功，设备修好了，待确认");
    }

    /*
    设备维修不了 报废
    */
    @PostMapping("/uptateOrderStateScrap")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_WXUSER')")
    public RestResponse uptateOrderStateScrap(Principal principal,@RequestBody Map<String,String> orderInfo){
        Order nowOrder = orderService.queryById(Long.valueOf(orderInfo.get("id")));
        nowOrder.setState(OrderStateEnums.FAILURE.getState());
        nowOrder.setUpdateName(principal.getName());
        nowOrder.setUpdateTime(MyDateUtils.GetNowDate());
        orderService.updateOrder(nowOrder);
        //保存日志
        String log  = "订单ID "+ orderInfo.get("id") +" 被 "+principal.getName()+" 确认无法维修，直接报废,维修情况:"+orderInfo.get("content");
        orderLogService.insertOneLog(Long.valueOf(orderInfo.get("id")),principal.getName(),log);
        //给客户发消息说明
        Message message = new Message();
        message.setUserId(nowOrder.getDoctorUserId());
        message.setState(MessageStateEnums.WAIT_READ.getState());
        String msg = "你申请的维修订单号为:"+nowOrder.getId()+"被维修师傅："+principal.getName()+" 确认无法维修，直接报废，请及时评价.维修情况:"+orderInfo.get("content");
        message.setContent(msg);
        message.setCreateName(principal.getName());
        message.setCreateTime(MyDateUtils.GetNowDate());
        messageService.insertOneMessage(message);
        //修改设备状态为 报废
        Device updateDevice = deviceService.queryByid(nowOrder.getDeviceId());
        updateDevice.setState(DeviceStateEnums.SCRAPPED.getState());
        deviceService.insertOneDevice(updateDevice);
        return RestResponse.ok().msg("订单完成成功，设备报废了");
    }
}
