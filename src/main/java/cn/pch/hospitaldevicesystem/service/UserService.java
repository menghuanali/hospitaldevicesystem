package cn.pch.hospitaldevicesystem.service;

import cn.pch.hospitaldevicesystem.entity.User;
import cn.pch.hospitaldevicesystem.model.response.UserModel;

import java.util.List;

/**
 * @author 潘成花
 * @name UserService
 * @description
 * @date 2021/1/27 17:50
 **/
public interface UserService {
    /*
    得到所有用户信息
     */
    List<UserModel> queryAllUsers();
    /*
        得到所有用户，用户权限为role
    */
    List<UserModel> queryAllByRole(String role);

    /*
        根据用户id查用户信息
    */
    User queryById(Long id);
    /*
        保存更改用户的信息
    */
    User insertOneUser(User user);
    /*
        根据id删除
    */
    void removeByid(Long id);
    /*
        根据电话号码查询用户信息
    */
    User queryByTel(String tel);
    /*
    根据用户名查询用户信息
    */
    UserModel queryByUserName(String username);
    /*
    根据用户名查询用户Id
    */
    Long queryIdByUserName(String username);
}
