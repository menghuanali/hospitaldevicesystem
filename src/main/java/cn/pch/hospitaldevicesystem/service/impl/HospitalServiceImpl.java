package cn.pch.hospitaldevicesystem.service.impl;

import cn.pch.hospitaldevicesystem.entity.Hospital;
import cn.pch.hospitaldevicesystem.repository.HospitalRepository;
import cn.pch.hospitaldevicesystem.service.HospitalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 潘成花
 * @name HospitalServiceImpl
 * @description
 * @date 2021/1/27 17:58
 **/
@Service
public class HospitalServiceImpl implements HospitalService {
    @Resource
    HospitalRepository hospitalRepository;
    @Override
    public List<Hospital> queryAll() {
        return hospitalRepository.findAll();
    }
}
