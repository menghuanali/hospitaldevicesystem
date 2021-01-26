package cn.pch.hospitaldevicesystem.controller;

import cn.pch.hospitaldevicesystem.utils.RestResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by 潘成花 on 2021/01/23
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {
//
//    @PreAuthorize如果有多个权限设置，则使用如下：
//    //必须有全部的权限才可以访问
//    @PreAuthorize("hasRole('ROLE_admin') and hasAnyRole('ROLE_user')")
//    //至少有一个即可访问
//    @PreAuthorize("hasRole('ROLE_admin') or hasAnyRole('ROLE_user')")

    @GetMapping
    public String listTasks(){
        return "任务列表";
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public String newTasks(){
        return "创建了一个新的任务";
    }

    @PostMapping("/welcome")
    @PreAuthorize("hasRole('ROLE_ADMIN2')")
    public RestResponse welcome(){
        return RestResponse.ok("欢迎~~~");
    }

    @PutMapping("/{taskId}")
    public String updateTasks(@PathVariable("taskId")Integer id){
        return "更新了一下id为:"+id+"的任务";
    }

    @DeleteMapping("/{taskId}")
    public String deleteTasks(@PathVariable("taskId")Integer id){
        return "删除了id为:"+id+"的任务";
    }
}
