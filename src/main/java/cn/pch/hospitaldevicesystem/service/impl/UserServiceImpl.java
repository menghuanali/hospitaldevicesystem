package cn.pch.hospitaldevicesystem.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.pch.hospitaldevicesystem.entity.User;
import cn.pch.hospitaldevicesystem.model.response.UserModel;
import cn.pch.hospitaldevicesystem.repository.UserRepository;
import cn.pch.hospitaldevicesystem.service.UserService;
import org.springframework.stereotype.Service;

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
    public void removeByid(Long id) {
        userRepository.deleteById(id);
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
}
