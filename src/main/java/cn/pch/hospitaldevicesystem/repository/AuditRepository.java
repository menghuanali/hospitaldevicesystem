package cn.pch.hospitaldevicesystem.repository;

import cn.pch.hospitaldevicesystem.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 潘成花
 * @name AuditRepository
 * @description
 * @date 2021/1/27 17:36
 **/
public interface AuditRepository extends JpaRepository<Audit, Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE from audit where apply_id NOT IN (SELECT id from apply ) ",nativeQuery = true)
    void deleteAudit();
}
