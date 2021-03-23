package cn.pch.hospitaldevicesystem.repository;

import cn.pch.hospitaldevicesystem.entity.Apply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 潘成花
 * @name ApplyRepository
 * @description
 * @date 2021/1/27 17:36
 **/
public interface ApplyRepository extends JpaRepository<Apply, Long> {
    /*
        根据状态得到申请单
    */
    List<Apply> findAllByStateAndDelete(int state,int delete);

    void deleteByFromUserIdOrDeviceId(Long a,Long b);
}
