package cn.pch.hospitaldevicesystem.entity;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

/**
 * @program: hospitaldevicesystem
 * @description: 审核信息
 * @author: 潘成花
 * @create: 2021-01-27 00:31
 **/
@Data
@Entity
@Table(name = "audit")
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /*
        申请单id
    */
    @Column(name = "apply_id",nullable=false,length=21)
    private Long applyId;
    /*
        审核的状态 具体看 AuditStateEnums
    */
    @Column(name = "state",nullable=false,length=11)
    private Integer state;

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
    private Integer version;

    /*
    是否删除 逻辑删除 1表示删除了 0表示未删除 默认为0
*/
    @Column(name = "is_delete",nullable=false,length=11)
    @ColumnDefault("0")
    private Integer delete;
}