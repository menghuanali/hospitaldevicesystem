import cn.hutool.core.date.DateUtil;
import cn.pch.hospitaldevicesystem.entity.User;
import cn.pch.hospitaldevicesystem.enums.RoleEnums;
import cn.pch.hospitaldevicesystem.repository.UserRepository;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author 潘成花
 * @name UserTest
 * @description
 * @date 2021/1/27 15:12
 **/
@Slf4j
public class UserTest extends TmallApplicationTests{

    @Resource
    UserRepository userRepository;

    @Test
    public void saveUserTest(){
        User user = new User();
        user.setUsername("花花");
        user.setPassword("123");
        user.setRole(RoleEnums.ROLE_ADMIN.getCode());
        user.setTel("132");
        user.setHeadUrl("www");
        user.setAddress("地址信息");
        user.setDepartment("科室");
        user.setCreateName("创建者");
        user.setCreateTime(DateUtil.date());
        User save = userRepository.save(user);
        log.info(":{} --PCH", JSON.toJSONString(save));
    }

    @Test
    public void updateUserTest(){
//        版本号+1 乐观锁得到控制 然后其他不改变的字段也必须为原来的值 否则会出现都为空了
        Optional<User> op = userRepository.findById(1L);
        if(op.isPresent()){//不为空才进来
            User user = op.get();
            user.setUsername("花花2号");
            User save = userRepository.save(user);
            log.info(":{} --PCH", JSON.toJSONString(save));
        }

    }
}
