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
    /*
    名字
 */
    private String username;

    private String role;
    /*
    权限名称
   */
    private String roleName;
    /*
        电话号码
    */

    private String tel;
    private String headUrl;
    /*地址信息*/
    private String address;
    /*
    科室
    */
    private String department;
    /*医院id*/
    private Long hospitalId;
    /*医院名称*/
    private  String hospitalName;
    /**
     * 未读消息数量
     */
    private Integer messageNum;

}
