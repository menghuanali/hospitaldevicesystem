package cn.pch.hospitaldevicesystem.model.response;

import lombok.Data;

/**
 * @program: hospitaldevicesystem
 * @description: 返回的用户模型
 * @author: Mr.Wang
 * @create: 2021-01-29 14:17
 **/
@Data
public class UserModel {
    private Long id;
    private String username;
    private String role;
    private String tel;
    private String headUrl;
    private String address;
    private String department;
    private Long hospitalId;
    private String updateName;
    private String updateTime;
}