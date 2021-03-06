package cn.pch.hospitaldevicesystem.service;

import cn.pch.hospitaldevicesystem.entity.Hospital;
import cn.pch.hospitaldevicesystem.entity.Message;

import java.util.List;

/**
 * @author 潘成花
 * @name HospitalService
 * @description
 * @date 2021/1/27 17:53
 **/
public interface HospitalService {
    List<Hospital> queryAll();
    /*
        根据id删除
    */
    void removeByid(Long id);
    /*
    根据id查找一条短信
    */
    Hospital queryByid(Long id);
}
