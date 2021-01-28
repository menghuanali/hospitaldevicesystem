package cn.pch.hospitaldevicesystem.entity;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @program: hospitaldevicesystem
 * @description: 申请信息
 * @author: 潘成花
 * @create: 2021-01-27 00:50
 **/
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "apply")
public class Apply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /*
        设备id
    */
    @Column(name = "device_id",nullable=false,length=21)
    private Long deviceId;
    /*
        申请的文字内容
    */
    @Column(name = "content",nullable=true,length=255)
    private String content;

    /*
        申请的状态 具体看ApplyStateEnums
    */
    @Column(name = "state",nullable=false,length=11)
    private Integer state;

    /*
        何人申请
    */
    @Column(name = "from_user_id",nullable=false,length=21)
    private Long fromUserId;

    /*
        来自哪个医院的设备申请
    */
    @Column(name = "from_hospital_id",nullable=false,length=21)
    private Long fromHospitalId;

    /*
        申请的类型 具体看 ApplyTypeEnums
    */
    @Column(name = "from_type",nullable=false,length=11)
    private Integer fromType;
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
    private Integer version;
    /*
    是否删除 逻辑删除 1表示删除了 0表示未删除 默认为0
*/
    @Column(name = "is_delete",nullable=false,length=11)
    @ColumnDefault("0")
    private Integer delete;
}
