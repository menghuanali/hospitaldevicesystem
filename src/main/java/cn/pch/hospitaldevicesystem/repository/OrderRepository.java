package cn.pch.hospitaldevicesystem.repository;

import cn.pch.hospitaldevicesystem.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 潘成花
 * @name OrderRepository
 * @description
 * @date 2021/1/27 17:35
 **/
public interface OrderRepository extends JpaRepository<Order, Long> {
}
