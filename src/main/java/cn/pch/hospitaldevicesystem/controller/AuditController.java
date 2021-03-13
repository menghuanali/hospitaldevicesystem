package cn.pch.hospitaldevicesystem.controller;

import cn.pch.hospitaldevicesystem.entity.Apply;
import cn.pch.hospitaldevicesystem.entity.Audit;
import cn.pch.hospitaldevicesystem.entity.Message;
import cn.pch.hospitaldevicesystem.entity.Order;
import cn.pch.hospitaldevicesystem.enums.ApplyStateEnums;
import cn.pch.hospitaldevicesystem.enums.HospitalEnums;
import cn.pch.hospitaldevicesystem.enums.MessageStateEnums;
import cn.pch.hospitaldevicesystem.enums.OrderStateEnums;
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
 * 审核
 * Created by 潘成花 on 2021/01/23
 */
@RestController
@RequestMapping("/audit")
public class AuditController {

    @Resource
    AuditService auditService;
    @Resource
    OrderLogService orderLogService;
    @Resource
    OrderService orderService;
    @Resource
    ApplyService applyService;
    @Resource
    MessageService messageService;
    /**
     * 审核通过
     */
    @PostMapping("/passed")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_KEUSER')")
    public RestResponse auditPass(Principal principal, @RequestBody Map<String,String> auditInfo){
        //修改申请单状态
        Apply apply = applyService.queryById(Long.valueOf(auditInfo.get("applyId")));
        apply.setState(ApplyStateEnums.AUDIT_PASS.getState());
        apply.setUpdateName(principal.getName());
        apply.setUpdateTime(MyDateUtils.GetNowDate());
        applyService.updateApply(apply);
        //保存审核信息
        Audit audit = new Audit();
        audit.setApplyId(Long.valueOf(auditInfo.get("applyId")));
        audit.setState(Integer.valueOf(auditInfo.get("state")));
        audit.setReason(auditInfo.get("reason"));
        audit.setCreateName(principal.getName());
        audit.setCreateTime(MyDateUtils.GetNowDate());
        auditService.auditPass(audit);
        //新增维修订单
        Order order = new Order();
        order.setState(OrderStateEnums.WAIT_ACCEPT.getState());
        order.setDeviceId(Long.valueOf(auditInfo.get("deviceId")));
        order.setDoctorUserId(Long.valueOf(auditInfo.get("doctorUserId")));
        order.setHospitalId(Long.valueOf(auditInfo.get("hospitalId")));
        order.setType(Integer.valueOf(auditInfo.get("type")));
        order.setCreateName(principal.getName());
        order.setCreateTime(MyDateUtils.GetNowDate());
        Order saveOrder = orderService.insertOneOrder(order);
         //保存日志
         String log = "来自 "+ HospitalEnums.of(Long.valueOf(auditInfo.get("hospitalId"))) +" 医院工作人员ID为: "+auditInfo.get("doctorUserId")+
                 " 的申请单已经审核通过并创建了维修单号: "+saveOrder.getId()+"等待派单";
        orderLogService.insertOneLog(order.getId(),principal.getName(),log);
        //给客户发消息说明审核通过
        Message message = new Message();
        message.setUserId(Long.valueOf(auditInfo.get("doctorUserId")));
        message.setState(MessageStateEnums.WAIT_READ.getState());
        String msg = "您的申请单号为: "+Long.valueOf(auditInfo.get("applyId"))+" 已经通过申请，将尽快为您解决问题";
        message.setContent(msg);
        message.setCreateName(principal.getName());
        message.setCreateTime(MyDateUtils.GetNowDate());
        messageService.insertOneMessage(message);
        return RestResponse.ok("审核通过");
    }


    /**
     * 审核通过
     */
    @PostMapping("/rejected")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_KEUSER')")
    public RestResponse auditReject(Principal principal, @RequestBody Map<String,String> auditInfo){
        //修改申请单状态
        Apply apply = applyService.queryById(Long.valueOf(auditInfo.get("applyId")));
        apply.setState(ApplyStateEnums.AUDIT_REJECT.getState());
        apply.setUpdateName(principal.getName());
        apply.setUpdateTime(MyDateUtils.GetNowDate());
        applyService.updateApply(apply);
        //保存审核信息
        Audit audit = new Audit();
        audit.setApplyId(Long.valueOf(auditInfo.get("applyId")));
        audit.setState(Integer.valueOf(auditInfo.get("state")));
        audit.setReason(auditInfo.get("reason"));
        audit.setCreateName(principal.getName());
        audit.setCreateTime(MyDateUtils.GetNowDate());
        auditService.auditReject(audit);
        //给客户发消息说明审核拒绝和原因
        Message message = new Message();
        message.setUserId(Long.valueOf(auditInfo.get("doctorUserId")));
        message.setState(MessageStateEnums.WAIT_READ.getState());
        String msg = "您的申请单号为: "+Long.valueOf(auditInfo.get("applyId"))+" 申请被拒绝，原因: "+auditInfo.get("reason")+" 更多请咨询客服人员，感谢你的支持和谅解！";
        message.setContent(msg);
        message.setCreateName(principal.getName());
        message.setCreateTime(MyDateUtils.GetNowDate());
        messageService.insertOneMessage(message);
        return RestResponse.ok("审核拒绝");
    }
}
