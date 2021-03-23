import cn.pch.hospitaldevicesystem.service.DeviceService;
import cn.pch.hospitaldevicesystem.service.UserService;
import cn.pch.hospitaldevicesystem.utils.DeleteUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @program: hospitaldevicesystem
 * @description: 删除脏数据的测试
 * @author: 潘成花
 * @create: 2021-03-23 22:56
 **/
@Slf4j
public class DeleteTest extends TmallApplicationTests{

    @Resource
    DeleteUtils deleteUtils;
    @Resource
    UserService userService;
    @Resource
    DeviceService deviceService;
    @Test
    public void deletetest(){
        userService.removeByid(100L);
        deviceService.removeByid(100L);
    }
}