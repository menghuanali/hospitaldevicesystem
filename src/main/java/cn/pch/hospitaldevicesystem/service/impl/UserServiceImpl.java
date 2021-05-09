package cn.pch.hospitaldevicesystem.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.pch.hospitaldevicesystem.entity.User;
import cn.pch.hospitaldevicesystem.enums.HospitalEnums;
import cn.pch.hospitaldevicesystem.enums.RoleEnums;
import cn.pch.hospitaldevicesystem.model.response.UserModel;
import cn.pch.hospitaldevicesystem.repository.UserRepository;
import cn.pch.hospitaldevicesystem.service.UserService;
import cn.pch.hospitaldevicesystem.utils.DeleteUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
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
    @Resource
    DeleteUtils deleteUtils;

    @Override
    public List<UserModel> queryAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserModel> result = users.stream().map(user -> {
            UserModel userModel = new UserModel();
            BeanUtil.copyProperties(user,userModel);
            userModel.setHospitalName(userModel.getHospitalId()==null?null:HospitalEnums.of(userModel.getHospitalId()).getName());
            userModel.setRoleName(RoleEnums.of(userModel.getRole()).getName());
            return userModel;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<UserModel> queryAllByRole(String role) {
        List<User> users = userRepository.findAllByRoleAndDelete(role,0);
        List<UserModel> result = users.stream().map(user -> {
            UserModel userModel = new UserModel();
            BeanUtil.copyProperties(user,userModel);
            userModel.setHospitalName(userModel.getHospitalId()==null?null:HospitalEnums.of(userModel.getHospitalId()).getName());
            userModel.setRoleName(RoleEnums.of(userModel.getRole()).getName());
            return userModel;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public User queryById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            User result = user.get();
            return result;
        }else{
            return null;
        }
//        return userRepository.getOne(id);
//getOne是懒加载，需要增加这个配置： spring.jpa.properties.hibernate.enable_lazy_load_no_trans=true，但这种方式不太友好，建议不要使用。
//每次初始化一个实体的关联就会创建一个临时的session来加载，每个临时的session都会获取一个临时的数据库连接，开启一个新的事物。这就导致对底层连接池压力很大，而且事物日志也会被每次flush.
//设想一下：假如我们查询了一个分页list每次查出1000条，这个实体有三个lazy关联对象,那么，恭喜你，你至少需要创建3000个临时session+connection+transaction.
    }

    @Override
    public User insertOneUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void removeByid(Long id) {
        userRepository.deleteById(id);
        deleteUtils.deleteDirtyData(id,0L);
    }

    @Override
    public User queryByTel(String tel) {
        List<User> dbResult = userRepository.findAllByTel(tel);
        if(dbResult.size()==1){
            User user = new User();
            user.setUsername(dbResult.get(0).getUsername());
            return user;
        }else {
            return null;
        }
    }

    @Override
    public UserModel queryByUserName(String username) {
        List<User> dbResult = userRepository.findAllByUsername(username);
        if(dbResult.size()==1){
            UserModel userModel = new UserModel();
            BeanUtil.copyProperties(queryById(dbResult.get(0).getId()),userModel);
            userModel.setRoleName(RoleEnums.of(userModel.getRole()).getName());
            userModel.setHospitalName(HospitalEnums.of(userModel.getHospitalId()).getName());
            return userModel;
        }else {
            return null;
        }
    }

    @Override
    public Long queryIdByUserName(String username) {
        List<User> dbResult = userRepository.findAllByUsername(username);
        if(dbResult.size()==1){
            return dbResult.get(0).getId();
        }else {
            return null;
        }
    }
}
