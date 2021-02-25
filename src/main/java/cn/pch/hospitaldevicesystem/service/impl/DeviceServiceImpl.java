package cn.pch.hospitaldevicesystem.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.pch.hospitaldevicesystem.entity.Device;
import cn.pch.hospitaldevicesystem.entity.Hospital;
import cn.pch.hospitaldevicesystem.entity.User;
import cn.pch.hospitaldevicesystem.enums.DeviceStateEnums;
import cn.pch.hospitaldevicesystem.model.response.DeviceModel;
import cn.pch.hospitaldevicesystem.repository.DeviceRepository;
import cn.pch.hospitaldevicesystem.service.DeviceService;
import cn.pch.hospitaldevicesystem.service.HospitalService;
import cn.pch.hospitaldevicesystem.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Resource
    UserService userService;
    @Resource
    HospitalService hospitalService;
    @Override
    public List<DeviceModel> queryAllDevice() {
        List<Device> dbResultLsit = deviceRepository.findAll();
        List<DeviceModel> result = dbResultLsit.stream().map(device -> {
            DeviceModel deviceModel = new DeviceModel();
            BeanUtil.copyProperties(device,deviceModel);
            deviceModel.setState(DeviceStateEnums.of(device.getState()).getName());
            User user = userService.queryById(device.getAdminUserId());
            deviceModel.setUsername(user.getUsername());
            deviceModel.setTel(user.getTel());
            Hospital hospital = hospitalService.queryByid(device.getHospitalId());
            deviceModel.setHospitalName(hospital.getName());
            return deviceModel;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public Device insertOneDevice(Device device) {
        return deviceRepository.save(device);
    }

    @Override
    public Device queryByid(Long id) {
        Optional<Device> device = deviceRepository.findById(id);
        if(device.isPresent()){//判空
            Device result = device.get();//得到返回的实体
            return result;
        }
        return null;
    }

    @Override
    public void removeByid(Long id) {
        deviceRepository.deleteById(id);
    }
}
