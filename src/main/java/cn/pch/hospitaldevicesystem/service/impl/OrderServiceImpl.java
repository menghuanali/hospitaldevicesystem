package cn.pch.hospitaldevicesystem.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.pch.hospitaldevicesystem.entity.Order;
import cn.pch.hospitaldevicesystem.model.response.OrderModel;
import cn.pch.hospitaldevicesystem.repository.OrderRepository;
import cn.pch.hospitaldevicesystem.service.OrderService;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 潘成花
 * @name OrderServiceImpl
 * @description
 * @date 2021/1/27 17:59
 **/
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    OrderRepository orderRepository;
    @Override
    public List<OrderModel> queryAll() {
        List<Order> dbResultLsit =  orderRepository.findAll();
        List<OrderModel> result = dbResultLsit.stream().map(order -> {
                OrderModel orderModel = new OrderModel();
                BeanUtil.copyProperties(order,orderModel);
                return orderModel;
            }).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<OrderModel> queryAllByState(int state) {
        List<Order> dbResultLsit = orderRepository.findAllByState(state);
        List<OrderModel> result = dbResultLsit.stream().map(order -> {
            OrderModel orderModel = new OrderModel();
            BeanUtil.copyProperties(order,orderModel);
            return orderModel;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<OrderModel> queryAllByHospital(Long hospitalId) {
        List<Order> dbResultLsit = orderRepository.findAllByHospitalId(hospitalId);
        List<OrderModel> result = dbResultLsit.stream().map(order -> {
            OrderModel orderModel = new OrderModel();
            BeanUtil.copyProperties(order,orderModel);
            return orderModel;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<OrderModel> queryAllOrderByCreateTimeDesc() {
        List<Order> dbResultLsit = orderRepository.findALLOrderByCreateTimeDesc();
        List<OrderModel> result = dbResultLsit.stream().map(order -> {
            OrderModel orderModel = new OrderModel();
            BeanUtil.copyProperties(order,orderModel);
            return orderModel;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public Order insertOneOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order queryById(Long id) {
        Optional<Order> dbResult = orderRepository.findById(id);
        if(dbResult.isPresent()){
            Order result = dbResult.get();
            return result;
        }else {
            return null;
        }
    }

    @Override
    public void removeByid(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public List<OrderModel> queryAllByExample(Order orderExample) {
        Example<Order> example = Example.of(orderExample);
        List<Order> dbResultLsit = orderRepository.findAll(example);
        List<OrderModel> result = dbResultLsit.stream().map(order -> {
            OrderModel orderModel = new OrderModel();
            BeanUtil.copyProperties(order,orderModel);
            return orderModel;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<OrderModel> queryProcessOrder() {
        List<Order> dbResultLsit = orderRepository.findALLProcessOrder();
        List<OrderModel> result = dbResultLsit.stream().map(order -> {
            OrderModel orderModel = new OrderModel();
            BeanUtil.copyProperties(order,orderModel);
            return orderModel;
        }).collect(Collectors.toList());
        return result;
    }

}
