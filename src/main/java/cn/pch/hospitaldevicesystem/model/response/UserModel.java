package cn.pch.hospitaldevicesystem.model.response;

import lombok.Data;

/**
 * @program: hospitaldevicesystem
 * @description: 返回的用户模型
 * @author: 潘成花
 * @create: 2021-01-29 14:17
 **/
@Data
public class UserModel extends BaseModel{
    private String username;
    private String role;
    private String roleName;
    private String tel;
    private String headUrl;
    private String address;
    /*
    科室
    */
    private String department;
    private Long hospitalId;
    /**
     * 未读消息数量
     */
    private Integer messageNum;
}
