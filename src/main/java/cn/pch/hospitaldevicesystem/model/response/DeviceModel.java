package cn.pch.hospitaldevicesystem.model.response;

import lombok.Data;

/**
 * @program: hospitaldevicesystem
 * @description: 设备的返回模型
 * @author: 潘成花
 * @create: 2021-02-07 15:36
 **/
@Data
public class DeviceModel extends BaseModel{
    /**
     * 设备名
     */
    private String name;
    /**
     * 设备的图片地址
     */
    private String pictureUrl;
    /**
     * 设备的状态 具体看 DeviceStateEnums
     */
    private Integer state;
    /**
     * 设备的状态 具体看 DeviceStateEnums
     */
    private String stateName;
    /**
     * 设备负责人姓名
     */
    private String username;
    /**
     * 设备负责人电话
     */
    private String tel;
    /**
     * 设备所属的医院名字
     */
    private String hospitalName;


}
