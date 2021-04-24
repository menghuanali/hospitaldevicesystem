package cn.pch.hospitaldevicesystem.controller;

import cn.pch.hospitaldevicesystem.entity.Apply;
import cn.pch.hospitaldevicesystem.entity.Device;
import cn.pch.hospitaldevicesystem.entity.Message;
import cn.pch.hospitaldevicesystem.entity.Order;
import cn.pch.hospitaldevicesystem.enums.*;
import cn.pch.hospitaldevicesystem.model.request.ApplyCreateModel;
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
@RequestMapping("/apply")
public class ApplyController {
    @Resource
    ApplyService applyService;
    @Resource
    UserService userService;
    @Resource
    OrderService orderService;
    @Resource
    OrderLogService orderLogService;
    @Resource
    MessageService messageService;
    @Resource
    DeviceService deviceService;
    /**
     * 创建一个申请单也就是故障申报
     */
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_YHUSER')")
    public RestResponse createApply(Principal principal, @RequestBody Map<String,String> applyMapModel){
        ApplyCreateModel applyCreateModel = new ApplyCreateModel();
        applyCreateModel.setContent(applyMapModel.get("content"));
        applyCreateModel.setState(ApplyStateEnums.WAIT_AUDIT.getState());
        applyCreateModel.setDeviceId(Long.valueOf(applyMapModel.get("deviceId")));
        applyCreateModel.setFromHospitalId(Long.valueOf(applyMapModel.get("fromHospitalId")));
        applyCreateModel.setFromType(Integer.valueOf(applyMapModel.get("fromType")));
        applyCreateModel.setFromUserId(userService.queryIdByUserName(principal.getName()));
        applyCreateModel.setCreateName(principal.getName());
        applyService.insertOneApply(applyCreateModel);
        //修改设备状态为 故障中，申请维修中
        Device updateDevice = deviceService.queryByid(Long.valueOf(applyMapModel.get("deviceId")));
        updateDevice.setState(DeviceStateEnums.FAULTING.getState());
        deviceService.insertOneDevice(updateDevice);
        return RestResponse.ok().msg("申请成功");
    }

    /*
        申报审核
    */
    @PostMapping("/queryAuditApply")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_KEUSER') or hasRole('ROLE_KPJKEUSER')")
    public RestResponse queryAuditApply(){
        return RestResponse.ok(applyService.queryAllByState(ApplyStateEnums.WAIT_AUDIT.getState()));
    }


    /*
    得到所有的申请
    */
    @PostMapping("/queryAllApply")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_KEUSER') or hasRole('ROLE_KPJKEUSER')")
    public RestResponse queryAllApply(){
        return RestResponse.ok(applyService.queryAll());
    }

    /**
     *申报审核通过
     */
    @PostMapping("/passApply")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_KEUSER') or hasRole('ROLE_KPJKEUSER')")
    public RestResponse passApply(Principal principal, @RequestBody Map<String,String> applyMapModel){
        Apply apply = applyService.queryById(Long.valueOf(applyMapModel.get("id")));
        apply.setState(ApplyStateEnums.AUDIT_PASS.getState());
        apply.setUpdateName(principal.getName());
        apply.setUpdateTime(MyDateUtils.GetNowDate());
        applyService.updateApply(apply);
        Order order = new Order();
        order.setState(OrderStateEnums.WAIT_ACCEPT.getState());
        order.setDeviceId(apply.getDeviceId());
        order.setDoctorUserId(apply.getFromUserId());
        order.setHospitalId(apply.getFromHospitalId());
        order.setType(ApplyTypeEnums.COMEFROM_SYSTEM.getState());
        order.setCreateName(principal.getName());
        order.setCreateTime(MyDateUtils.GetNowDate());
        Order saveOrder = orderService.insertOneOrder(order);
        //保存日志
        String log = "来自 "+ HospitalEnums.of(apply.getFromHospitalId()).getName() +" 医院工作人员ID为: "+apply.getFromUserId()+
                " 通过申报故障并由客服人员 "+principal.getName()+" 通过审核创建了维修订单号: "+saveOrder.getId()+"等待派单";
        orderLogService.insertOneLog(order.getId(),principal.getName(),log);
        //给客户发消息说明申报审核订单申请成功
        Message message = new Message();
        message.setUserId(apply.getFromUserId());
        message.setState(MessageStateEnums.WAIT_READ.getState());
        String msg = "尊敬的客服您好，你的故障申报已经通过客服人员审核通过，维修订单号为:"+saveOrder.getId()+"将尽快为您解决问题";
        message.setContent(msg);
        message.setCreateName(principal.getName());
        message.setCreateTime(MyDateUtils.GetNowDate());
        messageService.insertOneMessage(message);
        return RestResponse.ok("审核通过成功！创建了新订单");
    }

    /**
     *申报审核拒绝
     */
    @PostMapping("/rejectApply")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_KEUSER') or hasRole('ROLE_KPJKEUSER')")
    public RestResponse rejectApply(Principal principal, @RequestBody Map<String,String> applyMapModel){
        Apply apply = applyService.queryById(Long.valueOf(applyMapModel.get("id")));
        apply.setState(ApplyStateEnums.AUDIT_REJECT.getState());
        apply.setUpdateName(principal.getName());
        apply.setUpdateTime(MyDateUtils.GetNowDate());
        applyService.updateApply(apply);
        //给客户发消息说明申报审核订单申请拒绝
        Message message = new Message();
        message.setUserId(apply.getFromUserId());
        message.setState(MessageStateEnums.WAIT_READ.getState());
        String msg = "尊敬的客服您好，你的故障申报已经客服人员审核，很抱歉没有通过审核，如有疑问请致电或者再次申请，祝您工作顺利";
        message.setContent(msg);
        message.setCreateName(principal.getName());
        message.setCreateTime(MyDateUtils.GetNowDate());
        messageService.insertOneMessage(message);
        return RestResponse.ok("审核通过拒绝！已经通知用户");
    }
}
