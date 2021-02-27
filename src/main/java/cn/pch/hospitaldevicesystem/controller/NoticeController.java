package cn.pch.hospitaldevicesystem.controller;

import cn.pch.hospitaldevicesystem.service.NoticeService;
import cn.pch.hospitaldevicesystem.utils.RestResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by 潘成花 on 2021/01/23
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Resource
    NoticeService noticeService;

    /*
        得到所有的公告id降序
    */
    @PostMapping("/getAll")
    public RestResponse getAll(){
        return RestResponse.ok(noticeService.queryAll());
    }

    /*
        得到一个公告的详细信息
    */
    @GetMapping("/getOneNotice")
    public RestResponse getOneNotice(@RequestParam(name = "id") String id){
        return RestResponse.ok(noticeService.queryByid(Long.valueOf(id)));
    }
}
