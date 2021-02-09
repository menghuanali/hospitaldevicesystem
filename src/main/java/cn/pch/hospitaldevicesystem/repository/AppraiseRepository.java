package cn.pch.hospitaldevicesystem.repository;

import cn.pch.hospitaldevicesystem.entity.Appraise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 潘成花
 * @name AppraiseRepository
 * @description
 * @date 2021/1/27 17:36
 **/
public interface AppraiseRepository extends JpaRepository<Appraise, Long> {
    List<Appraise> findAllByOrderIdOrderByIdDesc(Long orderId);
    List<Appraise> findAllByOrderIdAndTypeOrderByIdDesc(Long orderId,Integer type);
}
