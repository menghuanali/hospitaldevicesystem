import cn.pch.hospitaldevicesystem.entity.Message;
import cn.pch.hospitaldevicesystem.entity.User;
import cn.pch.hospitaldevicesystem.enums.RoleEnums;
import cn.pch.hospitaldevicesystem.repository.MessageRepository;
import cn.pch.hospitaldevicesystem.utils.MyDateUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author 潘成花
 * @name MessageTest
 * @description
 * @date 2021/1/27 16:31
 **/
@Slf4j
public class MessageTest extends TmallApplicationTests{
    @Resource
    MessageRepository messageRepository;

    @Test
    public void saveMessageTest(){
        Message message = new Message();
        message.setUserId(1L);
        message.setState(2);
        message.setContent("内容");
        message.setCreateName("创建者");
        message.setCreateTime(MyDateUtils.GetNowDate());
        Message save = messageRepository.save(message);
        log.info(":{} --PCH", JSON.toJSONString(save));
    }

}
