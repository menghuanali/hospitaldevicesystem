package cn.pch.hospitaldevicesystem.repository;

import cn.pch.hospitaldevicesystem.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author 潘成花
 * @name NoticeRepository
 * @description
 * @date 2021/1/27 17:35
 **/
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    /*
        得到所有的公告id降序
    */
    @Query(value = "select * from notice order by id desc ", nativeQuery = true)
    List<Notice> findALLNoticeByIdDesc();
}
