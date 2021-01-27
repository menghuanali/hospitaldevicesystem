package cn.pch.hospitaldevicesystem.entity;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @program: hospitaldevicesystem
 * @description: 订单日志信息
 * @author: 潘成花
 * @create: 2021-01-27 00:12
 **/
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "order_log")
public class OrderLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "order_id",nullable=false,length=21)
    private Long orderId;

    /*
        日志内容
    */
    @Column(name = "content",nullable=false,length=500)
    private String content;

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
