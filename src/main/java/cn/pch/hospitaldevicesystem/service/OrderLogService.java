package cn.pch.hospitaldevicesystem.service;

import cn.pch.hospitaldevicesystem.entity.OrderLog;

import java.util.List;

/**
 * @author 潘成花
 * @name OrderLogService
 * @description
 * @date 2021/1/27 17:52
 **/
public interface OrderLogService {
    /*
        插入一条订单日志 日志包括 订单创建，订单状态变化，订单评价。
    */
    OrderLog insertOneLog(Long orderId,String operator,String content);

    /*
        得到某个订单的所有日志 并按照时间降序排列
    */
    List<OrderLog> queryAllByOrderId(Long orderId);
}
