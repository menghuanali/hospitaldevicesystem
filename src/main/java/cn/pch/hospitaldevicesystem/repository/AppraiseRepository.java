package cn.pch.hospitaldevicesystem.repository;

import cn.pch.hospitaldevicesystem.entity.Appraise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 潘成花
 * @name AppraiseRepository
 * @description
 * @date 2021/1/27 17:36
 **/
public interface AppraiseRepository extends JpaRepository<Appraise, Long> {
    Appraise findByOrderId(Long orderId);
    List<Appraise> findAllByOrderIdAndTypeOrderByIdDesc(Long orderId,Integer type);

    @Modifying
    @Transactional
    @Query(value = "DELETE from appraise where from_user_id NOT IN (SELECT id from user ) OR to_user_id NOT IN (SELECT id from user ) OR order_id NOT IN (SELECT id from myorder )",nativeQuery = true)
    void deleteAppraise();
}
