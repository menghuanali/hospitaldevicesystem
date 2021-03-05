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
    /**
     * 订单状态名
     */
    private String stateName;

    /*
        订单关连的设备id
    */
    private Long deviceId;
    /*
        订单关连的设备名
    */
    private String deviceName;
    /*
        哪个医护人员申请的订单
    */
    private Long doctorUserId;
    /*
        医护人员名
    */
    private String doctorUserName;
    /*
        负责维修人员的id 可以为空还未分配
    */
    private Long workerUserId;
    /**
     * 维修人员名字
     */
    private String workerUserName;
    /*
        来自哪个医院的订单
    */
    private Long hospitalId;
    /**
     * 医院名
     */
    private String hospitalName;
    /*
        订单的类型 具体看ApplyTypeEnums
    */
    private Integer type;
    /**
     * 订单的类型名字
     */
    private String typeName;
    /*
    预计到场修理时间
    */
    private String comeTime;
}
