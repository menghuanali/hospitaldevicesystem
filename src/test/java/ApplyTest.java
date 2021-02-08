import cn.pch.hospitaldevicesystem.entity.Apply;
import cn.pch.hospitaldevicesystem.entity.Device;
import cn.pch.hospitaldevicesystem.enums.ApplyStateEnums;
import cn.pch.hospitaldevicesystem.enums.ApplyTypeEnums;
import cn.pch.hospitaldevicesystem.service.ApplyService;
import cn.pch.hospitaldevicesystem.service.DeviceService;
import cn.pch.hospitaldevicesystem.service.UserService;
import cn.pch.hospitaldevicesystem.utils.MyDateUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author 潘成花
 * @name ApplyTest
 * @description
 * @date 2021/2/5 15:43
 **/
@Slf4j
public class ApplyTest extends TmallApplicationTests{

    @Resource
    ApplyService applyService;

    @Resource
    UserService userService;

    @Resource
    DeviceService deviceService;
    @Test
    public void insertApplys(){
        for(int i=4;i<=6;i++){
            Apply apply = new Apply();
            apply.setDeviceId(i+0L);
            apply.setContent("该设备需要维修了，麻烦尽快派人过来!!");
            apply.setState(ApplyStateEnums.WAIT_AUDIT.getState());
            apply.setFromUserId(1L);
            Device device = deviceService.queryOneDevice(i+0L);
            apply.setFromHospitalId(device.getHospitalId());
            apply.setFromType(ApplyTypeEnums.COMEFROM_SYSTEM.getState());
            apply.setCreateName("系统");
            apply.setCreateTime(MyDateUtils.GetNowDate());
            applyService.insertOneApply(apply);
        }
    }
}
