package cn.pch.hospitaldevicesystem.repository;

import cn.pch.hospitaldevicesystem.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 潘成花
 * @name AuditRepository
 * @description
 * @date 2021/1/27 17:36
 **/
public interface AuditRepository extends JpaRepository<Audit, Long> {
}
