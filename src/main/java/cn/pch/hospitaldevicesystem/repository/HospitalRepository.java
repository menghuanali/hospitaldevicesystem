package cn.pch.hospitaldevicesystem.repository;

import cn.pch.hospitaldevicesystem.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 潘成花
 * @name HospitalRepository
 * @description
 * @date 2021/1/27 17:35
 **/
public interface HospitalRepository extends JpaRepository<Hospital, Long> {
}
