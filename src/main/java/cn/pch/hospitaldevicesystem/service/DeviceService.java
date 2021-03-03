package cn.pch.hospitaldevicesystem.service;

import cn.pch.hospitaldevicesystem.entity.Device;
import cn.pch.hospitaldevicesystem.model.response.DeviceModel;

import java.util.List;

/**
 * @author 潘成花
 * @name DeviceService
 * @description
 * @date 2021/1/27 17:53
 **/
public interface DeviceService {
    /*
        得到所有的设备
    */
    List<DeviceModel> queryAllDevice();

    /*
        插入或者修改一个设备
    */
    Device insertOneDevice(Device device);

    /*
        根据id得到一个设备
    */
    Device queryByid(Long id);
    /*
        根据id删除
    */
    void removeByid(Long id);
    /*
        得到所属医院的所有设备
    */
    List<Device> queryAllDeviceByHospitalId(Long hospitalId);
}
