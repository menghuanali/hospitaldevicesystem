package cn.pch.hospitaldevicesystem.repository;

import cn.pch.hospitaldevicesystem.entity.OrderLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wanggang317
 * @name OrderLogRepository
 * @description
 * @date 2021/1/27 17:34
 **/
public interface OrderLogRepository extends JpaRepository<OrderLog, Long> {
}
