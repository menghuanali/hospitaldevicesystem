package cn.pch.hospitaldevicesystem.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.pch.hospitaldevicesystem.entity.User;
import cn.pch.hospitaldevicesystem.model.response.UserModel;
import cn.pch.hospitaldevicesystem.repository.UserRepository;
import cn.pch.hospitaldevicesystem.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 潘成花
 * @name UserServiceImpl
 * @description
 * @date 2021/1/27 17:59
 **/
@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserRepository userRepository;
    @Override
    public List<UserModel> queryAllByRole(String role) {
        List<User> users = userRepository.findAllByRoleAndDelete(role,0);
        List<UserModel> result = users.stream().map(user -> {
            UserModel userModel = new UserModel();
            BeanUtil.copyProperties(user,userModel);
            return userModel;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public User queryById(Long id) {
        return userRepository.getOne(id);
    }
}
