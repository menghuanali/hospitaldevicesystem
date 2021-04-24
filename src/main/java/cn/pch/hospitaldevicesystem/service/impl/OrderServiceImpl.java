package cn.pch.hospitaldevicesystem.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.pch.hospitaldevicesystem.entity.Order;
import cn.pch.hospitaldevicesystem.enums.ApplyTypeEnums;
import cn.pch.hospitaldevicesystem.enums.OrderStateEnums;
import cn.pch.hospitaldevicesystem.model.response.OrderInfoModel;
import cn.pch.hospitaldevicesystem.model.response.OrderModel;
import cn.pch.hospitaldevicesystem.repository.OrderRepository;
import cn.pch.hospitaldevicesystem.service.*;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 潘成花
 * @name OrderServiceImpl
 * @description
 * @date 2021/1/27 17:59
 **/
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    OrderRepository orderRepository;
    @Resource
    UserService userService;
    @Resource
    DeviceService deviceService;
    @Resource
    HospitalService hospitalService;
    @Resource
    OrderLogService orderLogService;
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
    public OrderModel queryOrderModelById(Long id) {
        Order order = queryById(id);
        OrderModel orderModel = new OrderModel();
        BeanUtil.copyProperties(order,orderModel);
        if(orderModel.getWorkerUserId()!=null){
            orderModel.setWorkerUserName(userService.queryById(orderModel.getWorkerUserId()).getUsername());
        }
        orderModel.setDeviceName(deviceService.queryByid(orderModel.getDeviceId()).getName());
        orderModel.setDoctorUserName(userService.queryById(orderModel.getDoctorUserId()).getUsername());
        orderModel.setHospitalName(hospitalService.queryByid(orderModel.getHospitalId()).getName());
        orderModel.setTypeName(ApplyTypeEnums.of(orderModel.getType()).getName());
        orderModel.setStateName(OrderStateEnums.of(orderModel.getState()).getName());
        //写入日志信息
        orderModel.setLogs(orderLogService.queryAllByOrderId(id));
        return orderModel;
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
            if(orderModel.getWorkerUserId()!=null){
                orderModel.setWorkerUserName(userService.queryById(orderModel.getWorkerUserId()).getUsername());
            }
            orderModel.setDeviceName(deviceService.queryByid(orderModel.getDeviceId()).getName());
            orderModel.setDoctorUserName(userService.queryById(orderModel.getDoctorUserId()).getUsername());
            orderModel.setHospitalName(hospitalService.queryByid(orderModel.getHospitalId()).getName());
            orderModel.setTypeName(ApplyTypeEnums.of(orderModel.getType()).getName());
            orderModel.setStateName(OrderStateEnums.of(orderModel.getState()).getName());
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
            if(orderModel.getWorkerUserId()!=null){
                orderModel.setWorkerUserName(userService.queryById(orderModel.getWorkerUserId()).getUsername());
            }
            orderModel.setDeviceName(deviceService.queryByid(orderModel.getDeviceId()).getName());
            orderModel.setDoctorUserName(userService.queryById(orderModel.getDoctorUserId()).getUsername());
            orderModel.setHospitalName(hospitalService.queryByid(orderModel.getHospitalId()).getName());
            orderModel.setTypeName(ApplyTypeEnums.of(orderModel.getType()).getName());
            orderModel.setStateName(OrderStateEnums.of(orderModel.getState()).getName());
            return orderModel;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<OrderInfoModel> queryOrderNumGroupByHospital() {
        List<Object[]> dbresult = orderRepository.findOrderNumGroupByHospital();
        List<OrderInfoModel> result = new ArrayList<>();
        for(int i=0;i<dbresult.size();i++){
            Object[] obs=dbresult.get(i);
            OrderInfoModel orderInfoModel = new OrderInfoModel();
            log.info(":{} ", JSON.toJSONString(obs));
            orderInfoModel.setKeyValue(Integer.valueOf(obs[0].toString()));
            orderInfoModel.setKeyName(Integer.valueOf(obs[1].toString()));
            result.add(orderInfoModel);
        }
        return result;
    }

    @Override
    public List<OrderInfoModel> queryOrderNumGroupByApplyType() {
        List<Object[]> dbresult = orderRepository.findOrderNumGroupByApplyType();
        List<OrderInfoModel> result = new ArrayList<>();
        for(int i=0;i<dbresult.size();i++){
            Object[] obs=dbresult.get(i);
            OrderInfoModel orderInfoModel = new OrderInfoModel();
            orderInfoModel.setKeyValue(Integer.valueOf(obs[0].toString()));
            orderInfoModel.setKeyName(Integer.valueOf(obs[1].toString()));
            result.add(orderInfoModel);
        }
        return result;
    }

    @Override
    public List<Order> queryOrderNumByTime(String time) {
        return orderRepository.findByCreateTimeGreaterThanEqual(time);
    }

    @Override
    public List<OrderModel> queryOrderByUserNameAndState(Long userId, Integer state) {
        List<Order> dbResultLsit = orderRepository.findAllByWorkerUserIdAndState(userId,state);
        List<OrderModel> result = dbResultLsit.stream().map(order -> {
            OrderModel orderModel = new OrderModel();
            BeanUtil.copyProperties(order,orderModel);
            orderModel.setDeviceName(deviceService.queryByid(orderModel.getDeviceId()).getName());
            orderModel.setDoctorUserName(userService.queryById(orderModel.getDoctorUserId()).getUsername());
            orderModel.setWorkerUserName(userService.queryById(orderModel.getWorkerUserId()).getUsername());
            orderModel.setHospitalName(hospitalService.queryByid(orderModel.getHospitalId()).getName());
            orderModel.setTypeName(ApplyTypeEnums.of(orderModel.getType()).getName());
            orderModel.setStateName(OrderStateEnums.of(orderModel.getState()).getName());
            return orderModel;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }

}
