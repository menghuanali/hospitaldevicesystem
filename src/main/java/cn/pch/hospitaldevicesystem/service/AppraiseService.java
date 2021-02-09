package cn.pch.hospitaldevicesystem.service;

import cn.pch.hospitaldevicesystem.entity.Appraise;
import cn.pch.hospitaldevicesystem.model.response.AppraiseModel;

import java.util.List;

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
        得到某个订单得所有评价并且按照时间降序
    */
    List<AppraiseModel> queryAllByOrderId(Long orderId);
    /*
    得到某个订单得所有评价某个状态得评价并且按照时间降序
*/
    List<AppraiseModel> queryAllByOrderIdAndState(Long orderId,Integer type);
    /*
    根据id删除
    */
    void removeByid(Long id);
}
