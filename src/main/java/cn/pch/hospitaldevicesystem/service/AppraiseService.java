package cn.pch.hospitaldevicesystem.service;

import cn.pch.hospitaldevicesystem.entity.Appraise;

/**
 * @author 潘成花
 * @name AppraiseService
 * @description 评价
 * @date 2021/1/27 17:54
 **/
public interface AppraiseService {
    /*
        插入修改一条评价
    */
    Appraise insertOneAppraise(Appraise appraise);
    /*
        根据id查找某一条评价
    */
    Appraise queryByid(Long id);
    /*
        得到某个订单的评价，因为是1对1的关系
    */
    Appraise queryAllByOrderId(Long orderId);

    /*
    根据id删除
    */
    void removeByid(Long id);
}
