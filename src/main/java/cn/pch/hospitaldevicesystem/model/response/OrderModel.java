package cn.pch.hospitaldevicesystem.model.response;

import lombok.Data;

/**
 * @program: hospitaldevicesystem
 * @description: 返回的订单模型
 * @author: 潘成花
 * @create: 2021-02-08 17:26
 **/
@Data
public class OrderModel extends BaseModel{
    /*
        订单状态 具体看OrderStateEnums
    */
    private Integer state;

    /*
        订单关连的设备id
    */
    private Long DeviceId;
    /*
        哪个医护人员申请的订单
    */
    private Long doctorUserId;
    /*
        负责维修人员的id 可以为空还未分配
    */
    private Long workerUserId;
    /*
        来自哪个医院的订单
    */
    private Long hospitalId;
    /*
        订单的类型 具体看OrderTypeEnums
    */
    private Integer type;
    /*
    预计到场修理时间
    */
    private String comeTime;
}