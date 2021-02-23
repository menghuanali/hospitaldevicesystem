package cn.pch.hospitaldevicesystem.controller;

import cn.pch.hospitaldevicesystem.service.MessageService;
import cn.pch.hospitaldevicesystem.utils.RestResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by 潘成花 on 2021/01/23
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Resource
    MessageService messageService;

    @PostMapping("/waitRead")
    public RestResponse getMessageWaitRead(){
        return RestResponse.ok("ok");
    }
}
