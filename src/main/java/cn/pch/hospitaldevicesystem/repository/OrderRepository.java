package cn.pch.hospitaldevicesystem.repository;

import cn.pch.hospitaldevicesystem.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author 潘成花
 * @name OrderRepository
 * @description
 * @date 2021/1/27 17:35
 **/
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByState(int state);
    List<Order> findAllByHospitalId(Long hospitalId);
    /*
        按照时间排序
    */
    @Query(value = "select * from myorder order by create_time desc ", nativeQuery = true)
    List<Order> findALLOrderByCreateTimeDesc();

    /*
    查询出指定1 2 5 6 状态下的订单
    */
    @Query(value = "select * from myorder where state=1 OR state=2 OR state=5 OR state=6 ", nativeQuery = true)
    List<Order> findALLProcessOrder();

    /*
    得到医院分组的各订单总数
    */
    @Query(value = "SELECT COUNT( * ) as KeyValue,from_hospital_id as KeyName FROM myorder GROUP BY from_hospital_id", nativeQuery = true)
    List<Object[]> findOrderNumGroupByHospital();

    /*
    得到订单分类总数
    */
    @Query(value = "SELECT COUNT( * ) as KeyValue,type as KeyName FROM myorder GROUP BY type", nativeQuery = true)
    List<Object[]> findOrderNumGroupByApplyType();

    /*
        查询大于某个时间的订单数
    */
    List<Order> findByCreateTimeGreaterThanEqual(String time);

    /*
        删除某个人或者某台设备相关的订单信息
    */
    void deleteByDoctorUserIdOrWorkerUserIdOrDeviceId(Long a,Long b,Long c);
    /*
        查询该用户下待签收的订单
    */
    List<Order> findAllByWorkerUserIdAndState(Long workerId,Integer state);

    /*
    查询出指定人3 4 8状态下的订单
    */
    @Query(value = "select * from myorder where state in (4,8) AND worker_userid = :id OR doctor_userid = :id", nativeQuery = true)
    List<Order> findALLUserHistoryOrder(@Param("id")Long id);

    /*
    查询出医生状态1 2 7 3 5 6的订单
    */
    @Query(value = "select * from myorder where state in (1,2,3,5,6,7) AND doctor_userid = :id", nativeQuery = true)
    List<Order> findAllOrderByUserIdAndGoing(@Param("id")Long id);


}
