package cn.pch.hospitaldevicesystem.repository;

import cn.pch.hospitaldevicesystem.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wanggang317
 * @name NoticeRepository
 * @description
 * @date 2021/1/27 17:35
 **/
public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
