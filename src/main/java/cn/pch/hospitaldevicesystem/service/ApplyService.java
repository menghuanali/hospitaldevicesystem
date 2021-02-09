package cn.pch.hospitaldevicesystem.service;

import cn.pch.hospitaldevicesystem.entity.Apply;
import cn.pch.hospitaldevicesystem.model.request.ApplyCreateModel;
import cn.pch.hospitaldevicesystem.model.response.ApplyModel;

import java.util.List;

/**
 * @author 潘成花
 * @name ApplyService
 * @description
 * @date 2021/1/27 17:54
 **/
public interface ApplyService {
    /*
        保存修改申请单
    */
    Apply insertOneApply(ApplyCreateModel apply);

    /*
        得到所有的申请单
    */
    List<ApplyModel> queryAll();

    /*
        根据状态得到申请单
    */
    List<ApplyModel> queryAllByState(int state);

    /*
        根据主键的到一个申请单
    */
    ApplyModel queryById(Long id);
}
