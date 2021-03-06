package cn.pch.hospitaldevicesystem.entity;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @program: hospitaldevicesystem
 * @description: 医院信息
 * @author: 潘成花
 * @create: 2021-01-27 01:02
 **/
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "hospital")
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name",nullable=false,length=255)
    private String name;

    /*
        医院的地址
    */
    @Column(name = "address",nullable=true,length=255)
    private String address;

    /*
        医院的电话
    */
    @Column(name = "tel",nullable=true,length=50)
    private String tel;

    /*
        医院的图片地址
    */
    @Column(name = "picture_url",nullable=true,length=255)
    private String pictureUrl;

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
