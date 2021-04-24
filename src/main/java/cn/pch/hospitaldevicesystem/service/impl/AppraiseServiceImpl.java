package cn.pch.hospitaldevicesystem.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.pch.hospitaldevicesystem.entity.Appraise;
import cn.pch.hospitaldevicesystem.model.response.AppraiseModel;
import cn.pch.hospitaldevicesystem.repository.AppraiseRepository;
import cn.pch.hospitaldevicesystem.service.AppraiseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author 潘成花
 * @name AppraiseServiceImpl
 * @description
 * @date 2021/1/27 17:57
 **/
@Service
public class AppraiseServiceImpl implements AppraiseService {
    @Resource
    AppraiseRepository appraiseRepository;
    @Override
    public Appraise insertOneAppraise(Appraise appraise) {
        return appraiseRepository.save(appraise);
    }

    @Override
    public Appraise queryByid(Long id) {
        Optional<Appraise> dbResult = appraiseRepository.findById(id);
        if(dbResult.isPresent()){
            Appraise result = dbResult.get();
            return result;
        }else {
            return null;
        }
    }

    @Override
    public Appraise queryAllByOrderId(Long orderId) {
        Appraise dbResult = appraiseRepository.findByOrderId(orderId);
        return  dbResult;
    }


    @Override
    public void removeByid(Long id) {
        appraiseRepository.deleteById(id);
    }
}
