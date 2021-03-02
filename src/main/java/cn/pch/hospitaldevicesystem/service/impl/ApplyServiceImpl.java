package cn.pch.hospitaldevicesystem.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.pch.hospitaldevicesystem.entity.Apply;
import cn.pch.hospitaldevicesystem.model.request.ApplyCreateModel;
import cn.pch.hospitaldevicesystem.model.response.ApplyModel;
import cn.pch.hospitaldevicesystem.repository.ApplyRepository;
import cn.pch.hospitaldevicesystem.service.ApplyService;
import cn.pch.hospitaldevicesystem.utils.MyDateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 潘成花
 * @name ApplyServiceImpl
 * @description
 * @date 2021/1/27 17:57
 **/
@Service
public class ApplyServiceImpl implements ApplyService {

    @Resource
    ApplyRepository applyRepository;
    @Override
    public Apply insertOneApply(ApplyCreateModel apply) {
        Apply apply1 = new Apply();
        BeanUtil.copyProperties(apply,apply1);
        apply1.setCreateTime(MyDateUtils.GetNowDate());
        return applyRepository.save(apply1);
    }

    @Override
    public Apply updateApply(Apply apply) {
        return applyRepository.save(apply);
    }

    @Override
    public List<ApplyModel> queryAll() {
        List<Apply> dbResultLsit = applyRepository.findAll();
        List<ApplyModel> result = dbResultLsit.stream().map(apply -> {
            ApplyModel applyModel = new ApplyModel();
            BeanUtil.copyProperties(apply,applyModel);
            return applyModel;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<ApplyModel> queryAllByState(int state) {
        List<Apply> dbResultLsit = applyRepository.findAllByStateAndDelete(state,0);
        List<ApplyModel> result = dbResultLsit.stream().map(apply -> {
                ApplyModel applyModel = new ApplyModel();
                BeanUtil.copyProperties(apply,applyModel);
                return applyModel;
            }).collect(Collectors.toList());
        return result;
    }

    @Override
    public Apply queryById(Long id) {
        Optional<Apply> dbApply = applyRepository.findById(id);
        if(dbApply.isPresent()){
            Apply apply = dbApply.get();
            return apply;
        }else{
            return null;
        }
    }

    @Override
    public void removeByid(Long id) {
        applyRepository.deleteById(id);
    }
}
