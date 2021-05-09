package cn.pch.hospitaldevicesystem.repository;

import cn.pch.hospitaldevicesystem.entity.OrderLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 潘成花
 * @name OrderLogRepository
 * @description
 * @date 2021/1/27 17:34
 **/
public interface OrderLogRepository extends JpaRepository<OrderLog, Long> {
    List<OrderLog> findAllByOrderIdOrderByIdAsc(Long orderId);

    @Modifying
    @Transactional
    @Query(value = "DELETE from order_log where order_id NOT IN (SELECT id from myorder ) ",nativeQuery = true)
    void deleteOrderLog();
}
