package cn.pch.hospitaldevicesystem.entity;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @program: hospitaldevicesystem
 * @description: 设备信息
 * @author: 潘成花
 * @create: 2021-01-27 00:36
 **/
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "device")
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name",nullable=false,length=255)
    private String name;

    /*
        设备的图片地址
    */
    @Column(name = "picture_url",nullable=true,length=255)
    private String pictureUrl;

    /*
        设备的类别 具体看DeviceTypeEnums
    */
    @Column(name = "type",nullable=true,length=11)
    private Integer type;

    /*
        设备的状态 具体看 DeviceStateEnums
    */
    @Column(name = "state",nullable=false,length=11)
    private Integer state;

    /*
        设备的负责人
    */
    @Column(name = "admin_user_id",nullable=true,length=21)
    private Long adminUserId;
    /*
        设备所属的医院id
    */
    @Column(name = "hospital_id",nullable=false,length=21)
    private Long hospitalId;
    /*
    下面是公共部分 由于jpa继承很麻烦 所以不用继承的父类
    */
    @Column(name = "create_name",nullable=false,length=100)
    private String createName;
    @Column(name = "create_time",nullable=false,length=100)
    private String createTime;
    @Column(name = "update_name",nullable=true,length=100)
    private String updateName;
    @Column(name = "update_time",nullable=true,length=100)
    private String updateTime;
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

