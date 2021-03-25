package cn.pch.hospitaldevicesystem.controller;

import cn.pch.hospitaldevicesystem.entity.Message;
import cn.pch.hospitaldevicesystem.enums.MessageStateEnums;
import cn.pch.hospitaldevicesystem.model.response.MessageModel;
import cn.pch.hospitaldevicesystem.service.MessageService;
import cn.pch.hospitaldevicesystem.service.UserService;
import cn.pch.hospitaldevicesystem.utils.MyDateUtils;
import cn.pch.hospitaldevicesystem.utils.RestResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by 潘成花 on 2021/01/23
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    MessageService messageService;
    @Resource
    UserService userService;

    @PostMapping("/waitRead")
    public RestResponse getMessageWaitRead(){
        return RestResponse.ok("ok");
    }

    /**
     *发送短信 只给客服和管理员用
     */
    @PostMapping("/addMessage")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_KEUSER') or hasRole('ROLE_KPJKEUSER')")
    public RestResponse addMessage(Principal principal, @RequestBody Map<String,String> msgInfo){
        Message message = new Message();
        message.setUserId(Long.valueOf(msgInfo.get("toUserId")));
        message.setState(MessageStateEnums.WAIT_READ.getState());
        message.setContent(msgInfo.get("content"));
        message.setCreateName(principal.getName());
        message.setCreateTime(MyDateUtils.GetNowDate());
        messageService.insertOneMessage(message);
        return RestResponse.ok("发送成功");
    }
    /**
     * 已发短信 只给客服和管理员用 前端分类分页吧
     */
    @PostMapping("/listMessage")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_KEUSER') or hasRole('ROLE_KPJKEUSER')")
    public RestResponse listMessage(Principal principal){
        return RestResponse.ok(messageService.queryAllByCreateName(principal.getName()));
    }
    /**
     * 收到的短信
     */
    @PostMapping("/myMessage")
    public RestResponse myMessage(Principal principal){
        List<MessageModel> result = messageService.queryAllByUserId(userService.queryIdByUserName(principal.getName()));
        Collections.reverse(result);
        return RestResponse.ok(result);
    }
}
