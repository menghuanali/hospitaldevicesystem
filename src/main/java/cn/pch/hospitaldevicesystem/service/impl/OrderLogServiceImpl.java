package cn.pch.hospitaldevicesystem.service.impl;

import cn.pch.hospitaldevicesystem.entity.OrderLog;
import cn.pch.hospitaldevicesystem.repository.OrderLogRepository;
import cn.pch.hospitaldevicesystem.service.OrderLogService;
import cn.pch.hospitaldevicesystem.utils.MyDateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 潘成花
 * @name OrderLogServiceImpl
 * @description
 * @date 2021/1/27 17:59
 **/
@Service
public class OrderLogServiceImpl implements OrderLogService {
    @Resource
    OrderLogRepository orderLogRepository;
    @Override
    public OrderLog insertOneLog(Long orderId, String operator, String content) {
        OrderLog orderLog = new OrderLog();
        orderLog.setContent(content);
        orderLog.setCreateName(operator);
        orderLog.setOrderId(orderId);
        orderLog.setCreateTime(MyDateUtils.GetNowDate());
        return orderLogRepository.save(orderLog);
    }

    @Override
    public List<OrderLog> queryAllByOrderId(Long orderId) {
        return orderLogRepository.findAllByOrderIdOrderByIdDesc(orderId);
    }

}
