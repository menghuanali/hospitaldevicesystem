package cn.pch.hospitaldevicesystem.repository;

import cn.pch.hospitaldevicesystem.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 潘成花
 * @name DeviceRepository
 * @description
 * @date 2021/1/27 17:36
 **/
public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findAllByHospitalId(Long hospitalId);
}
