package cn.pch.hospitaldevicesystem.service;

import cn.pch.hospitaldevicesystem.entity.Order;
import cn.pch.hospitaldevicesystem.model.response.OrderModel;

import java.util.List;

/**
 * @author 潘成花
 * @name OrderService
 * @description
 * @date 2021/1/27 17:51
 **/
public interface OrderService {
    /*
        得到所有的订单
    */
    List<OrderModel> queryAll();

    /*
        根据状态筛选订单
    */
    List<OrderModel> queryAllByState(int state);
    /*
        根据医院筛选订单
    */
    List<OrderModel> queryAllByHospital(Long hospitalId);
    /*
        按照时间降序排列
    */
    List<OrderModel> queryAllOrderByCreateTimeDesc();

    /*
        插入一个新的订单
    */
    Order insertOneOrder(Order order);
    /*
        根据id查找某个订单
    */
    Order queryById(Long id);
    /*
    根据id查找某个订单详情 包括日志信息
    */
    OrderModel queryOrderModelById(Long id);
    /*
        根据id删除
    */
    void removeByid(Long id);
    /*
        根据条件查询订单
    */
    List<OrderModel> queryAllByExample(Order order);
    /*
    查询出1256的状态下的订单
    */
    List<OrderModel> queryProcessOrder();
}
