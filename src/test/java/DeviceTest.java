import cn.pch.hospitaldevicesystem.entity.Device;
import cn.pch.hospitaldevicesystem.service.DeviceService;
import cn.pch.hospitaldevicesystem.utils.MyDateUtils;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @program: hospitaldevicesystem
 * @description: 设备的测试类
 * @author: Mr.Wang
 * @create: 2021-02-07 14:17
 **/
public class DeviceTest extends TmallApplicationTests{
    @Resource
    DeviceService deviceService;
    private static String[][] deviceInfos = {
                {"心脏除颤仪","/static/images/devices/1.jpg"}
            ,   {"医用冰箱","/static/images/devices/2.jpg"}
            ,   {"血压计","/static/images/devices/3.jpg"}
            ,   {"体重计","/static/images/devices/4.jpg"}
            ,   {"空气消毒机","/static/images/devices/5.jpg"}
            ,   {"移动紫外线灯","/static/images/devices/6.jpg"}
            ,   {"心电图机","/static/images/devices/7.jpg"}
            ,   {"脑电图监测仪","/static/images/devices/8.jpg"}
            ,   {"显微镜","/static/images/devices/9.jpg"}
            ,   {"激光治疗仪","/static/images/devices/10.jpg"}
    };
    @Test
    public void insertSuijiDevice(){
        for(int i=0;i<10;i++){
            Device device = new Device();
            device.setName(deviceInfos[i][0]);
            device.setPictureUrl(deviceInfos[i][1]);
            device.setState(2);//正常状态
            device.setAdminUserId(1L);
            device.setHospitalId(3L);
            device.setCreateName("系统");
            device.setCreateTime(MyDateUtils.GetNowDate());
            deviceService.insertOneDevice(device);
        }
    }
}
