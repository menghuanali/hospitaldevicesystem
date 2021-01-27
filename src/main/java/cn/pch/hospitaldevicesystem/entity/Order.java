package cn.pch.hospitaldevicesystem.entity;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @program: hospitaldevicesystem
 * @description: 订单信息
 * @author: 潘成花
 * @create: 2021-01-27 00:12
 **/
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "myorder")//order,是mysql的关键字 这里用myorder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /*
        订单状态 具体看OrderStateEnums
    */
    @Column(name = "state",nullable=false,length=11)
    private Integer state;


    /*
        订单关连的设备id
    */
    @Column(name = "device_id",nullable=false,length=21)
    private Long DeviceId;


    /*
        哪个医护人员申请的订单
    */
    @Column(name = "doctor_userid",nullable=false,length=21)
    private Long doctorUserId;

    /*
        负责维修人员的id 可以为空还未分配
    */
    @Column(name = "worker_userid",nullable=true,length=21)
    private Long workerUserId;

    /*
        来自哪个医院的订单
    */
    @Column(name = "from_hospital_id",nullable=false,length=21)
    private Long hospitalId;

    /*
        订单的类型 具体看OrderTypeEnums
    */
    @Column(name = "type",nullable=false,length=11)
    private Integer type;
    /*
    下面是公共部分 由于jpa继承很麻烦 所以不用继承的父类
    */
    @Column(name = "create_name",nullable=false,length=100)
    private String createName;
    @Column(name = "create_time",nullable=false,length=100)
    private Date createTime;
    @Column(name = "update_name",nullable=true,length=100)
    private String updateName;
    @Column(name = "update_time",nullable=true,length=100)
    private Date updateTime;
    /*
        版本控制
    */
    @Column(name = "version",nullable=false,length=11)
    @ColumnDefault("1")
    @Version
    private Integer version;

    /*
        是否删除 逻辑删除 1表示删除了 0表示未删除 默认为0
    */
    @Column(name = "is_delete",nullable=false,length=11)
    @ColumnDefault("0")
    private Integer delete;
}
