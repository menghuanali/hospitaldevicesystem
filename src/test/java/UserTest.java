import cn.hutool.core.util.RandomUtil;
import cn.pch.hospitaldevicesystem.entity.User;
import cn.pch.hospitaldevicesystem.enums.RoleEnums;
import cn.pch.hospitaldevicesystem.model.response.UserModel;
import cn.pch.hospitaldevicesystem.repository.UserRepository;
import cn.pch.hospitaldevicesystem.service.UserService;
import cn.pch.hospitaldevicesystem.utils.MyDateUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;
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

    @Resource
    UserService userService;

    private static String xing = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static String ming1 = "abcdefghijklmnopqrstuvwxyz";
    List<String> Name_Lists = Lists.newArrayList() ;
    @Test
    public void saveUserTest(){
        for(int i=1;i<=5;i++){
            User user = new User();
            user.setUsername("花花");
            user.setPassword("$2a$10$rBOthrjdMzBVy8SPbu9lpOqeh7lRChBvjRa2X6XLxQeWhmHRlIVoK");
            user.setRole(RoleEnums.ROLE_ADMIN.getCode());
            user.setTel("132");
            user.setHeadUrl("www");
            user.setAddress("地址信息");
            user.setDepartment("科室");
            user.setCreateName("创建者");
            user.setCreateTime(MyDateUtils.GetNowDate());
            User save = userRepository.save(user);
            log.info(":{} --PCH", JSON.toJSONString(save));
        }
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

    @Test
    public void moreTableQuery(){
        List<Object> result = userRepository.findAllMessageByUserId(1L);
        log.info(":{} --PCH", JSON.toJSONString(result));
    }

    @Test
    public void getUserByRole(){
        List<UserModel> result = userService.queryAllByRole(RoleEnums.ROLE_ADMIN.getCode());
        log.info(":{} ", JSON.toJSONString(result));
    }
    //插入一些随机用户
    @Test
    public void insertSuijiUser(){
        for (int i=0;i<50;i++){
            User user = new User();
            String username = String.valueOf(xing.charAt(RandomUtil.randomInt(0,xing.length()-1)))+String.valueOf(ming1.charAt(RandomUtil.randomInt(0,ming1.length()-1)));
            int sort = 1;
            while(userRepository.findByUsername(username)!=null){
                username = username+sort;
            }
            user.setUsername(username);
            user.setPassword("$2a$10$rBOthrjdMzBVy8SPbu9lpOqeh7lRChBvjRa2X6XLxQeWhmHRlIVoK");
            if(i%7==0){
                user.setRole(RoleEnums.ROLE_USER.getCode());
            }else if(i%7==1){
                user.setRole(RoleEnums.ROLE_YHUSER.getCode());
            }else if(i%7==2){
                user.setRole(RoleEnums.ROLE_WXUSER.getCode());
            }else if(i%7==3){
                user.setRole(RoleEnums.ROLE_KEUSER.getCode());
            }else if(i%7==4){
                user.setRole(RoleEnums.ROLE_KPJKEUSER.getCode());
            }else if(i%7==5){
                user.setRole(RoleEnums.ROLE_YHUSER.getCode());
            }else if(i%7==6){
                user.setRole(RoleEnums.ROLE_USER.getCode());
            }
            user.setTel(String.valueOf(RandomUtil.randomInt(1840000000,1849999999)+i%10));
            user.setHeadUrl("static/images/mychushihead.jpg");
            user.setAddress("北京市朝阳区霄云路50号");
            user.setDepartment("科室2");
            user.setCreateName("创建者");
            user.setCreateTime(MyDateUtils.GetNowDate());
            User save = userRepository.save(user);
            log.info(":{} --PCH", JSON.toJSONString(save));
        }
    }
    @Test
    public void alterUserinfo(){
        List<User> dbResultLsit = userRepository.findAll();
        for(int i=0;i<dbResultLsit.size();i++){
            User temp = dbResultLsit.get(i);
            String username = String.valueOf(xing.charAt(RandomUtil.randomInt(0,xing.length()-1)))+String.valueOf(ming1.charAt(RandomUtil.randomInt(0,ming1.length()-1)));
            int sort = 1;
            while(userRepository.findByUsername(username)!=null){
                username = username+sort;
            }
            temp.setUsername(username);
            userService.insertOneUser(temp);
        }
    }
}
