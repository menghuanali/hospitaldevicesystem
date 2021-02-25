package cn.pch.hospitaldevicesystem.service.impl;

import cn.pch.hospitaldevicesystem.entity.Hospital;
import cn.pch.hospitaldevicesystem.repository.HospitalRepository;
import cn.pch.hospitaldevicesystem.service.HospitalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

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

    @Override
    public void removeByid(Long id) {
        hospitalRepository.deleteById(id);
    }

    @Override
    public Hospital queryByid(Long id) {
        Optional<Hospital> dbResult = hospitalRepository.findById(id);
        if(dbResult.isPresent()){
            Hospital hospital = dbResult.get();
            return hospital;
        }else{
            return null;
        }
    }
}
