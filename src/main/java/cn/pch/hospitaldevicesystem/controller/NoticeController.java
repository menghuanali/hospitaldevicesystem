package cn.pch.hospitaldevicesystem.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.EnumUtil;
import cn.pch.hospitaldevicesystem.entity.Notice;
import cn.pch.hospitaldevicesystem.enums.NoticeTypeEnums;
import cn.pch.hospitaldevicesystem.service.NoticeService;
import cn.pch.hospitaldevicesystem.utils.MyDateUtils;
import cn.pch.hospitaldevicesystem.utils.RestResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    /*
    插入和修改一个公告
    */
    @PostMapping("/addNotice")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RestResponse addNotice(@RequestBody Map<String,String> noticeInfo){
        Notice notice = new Notice();
        notice.setId(noticeInfo.get("id")==null?0:Long.valueOf(noticeInfo.get("id")));
        notice.setType(Integer.valueOf(noticeInfo.get("type")));
        notice.setTitle(noticeInfo.get("title"));
        notice.setContent(noticeInfo.get("content"));
        notice.setPictureUrl(noticeInfo.get("pictureUrl"));
        notice.setCreateName("系统");
        notice.setCreateTime(MyDateUtils.GetNowDate());
        Notice newNotice = noticeService.insertOneNotice(notice);
        return RestResponse.ok(newNotice);
        //return RestResponse.ok("发布成功");

    }

    /*
    上传公告图片
    */
    @PostMapping("/upNoticePicture")
    public RestResponse uploadDevicePicture(@RequestParam("noticePicture") MultipartFile uploadFile){
        if (uploadFile.isEmpty()) {
            return RestResponse.fail("上传文件不能为空");
        }
        //获取原文件名
        String name=uploadFile.getOriginalFilename();
        //获取文件后缀
        String subffix=name.substring(name.lastIndexOf(".")+1,name.length());
        //控制格式
        if(subffix.equals("")&&!subffix.equals("jpg")&&!subffix.equals("jpeg")&&!subffix.equals("png"))
        {
            return RestResponse.fail("上传文件格式不对");
        }
        //新的文件名以日期命名
        String fileName = DateUtil.format(new Date(), "yyyyMMddHHmmss")+"."+subffix;
        //获取项目路径
        String path =  ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/images/notice";
        //“windows下使用的是“\”作为分隔符,而linux则反其道而行之使用"/"作为分隔符。
        String realPath = path.replace('/', '\\').substring(1,path.length());
        File file = new File(realPath,fileName);
        try {
            uploadFile.transferTo(file);
            System.out.println("图片上传成功 路径:");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //用于查看路径是否正确
        System.out.println(realPath+"\\"+fileName);
        return RestResponse.ok("static/images/notice/"+fileName);
    }
}
