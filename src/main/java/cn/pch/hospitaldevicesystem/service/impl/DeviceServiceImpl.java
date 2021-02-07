package cn.pch.hospitaldevicesystem.service.impl;

import cn.pch.hospitaldevicesystem.entity.Device;
import cn.pch.hospitaldevicesystem.repository.DeviceRepository;
import cn.pch.hospitaldevicesystem.service.DeviceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author 潘成花
 * @name DeviceServiceImpl
 * @description
 * @date 2021/1/27 17:57
 **/
@Service
public class DeviceServiceImpl implements DeviceService {
    @Resource
    DeviceRepository deviceRepository;
    @Override
    public List<Device> queryAllDevice() {
        return deviceRepository.findAll();
    }

    @Override
    public Device insertOneDevice(Device device) {
        return deviceRepository.save(device);
    }

    @Override
    public Device queryOneDevice(Long id) {
        Optional<Device> device = deviceRepository.findById(id);
        if(device.isPresent()){//判空
            Device result = device.get();//得到返回的实体
            return result;
        }
        return null;
    }
}
