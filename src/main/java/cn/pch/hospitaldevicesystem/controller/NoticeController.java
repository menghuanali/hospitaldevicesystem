package cn.pch.hospitaldevicesystem.controller;

import cn.hutool.core.util.EnumUtil;
import cn.pch.hospitaldevicesystem.enums.NoticeTypeEnums;
import cn.pch.hospitaldevicesystem.service.NoticeService;
import cn.pch.hospitaldevicesystem.utils.RestResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
    /*
    得到公告类型枚举
    */
    @PostMapping("/getTypeEnums")
    public RestResponse getTypeEnums(){
        List<Object> names = EnumUtil.getFieldValues(NoticeTypeEnums.class, "name");
        List<Object> types = EnumUtil.getFieldValues(NoticeTypeEnums.class, "code");
        List<Object> result = new ArrayList<>();
        result.add(names);
        result.add(types);
        return RestResponse.ok(result);
    }
}
