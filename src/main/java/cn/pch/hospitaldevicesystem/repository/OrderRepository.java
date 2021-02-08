package cn.pch.hospitaldevicesystem.repository;

import cn.pch.hospitaldevicesystem.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 潘成花
 * @name OrderRepository
 * @description
 * @date 2021/1/27 17:35
 **/
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByState(int state);
    List<Order> findAllByHospitalId(Long hospitalId);
    /*
        按照时间排序
    */
    List<Order> findOrderByCreateTimeDesc();
}
