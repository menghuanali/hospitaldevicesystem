package cn.pch.hospitaldevicesystem.entity;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @program: hospitaldevicesystem
 * @description: 人员信息
 * @author: 潘成花
 * @create: 2021-01-27 00:12
 **/
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "user")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username",nullable=false,length=255)
    private String username;

    @Column(name = "password",nullable=false,length=255)
    private String password;

    /*
        角色权限
    */
    @Column(name = "role",nullable=false,length=100)
    private String role;

    @Column(name = "tel",nullable=false,length=100)
    private String tel;

    /*
        头像地址
    */
    @Column(name = "head_url",nullable=true,length=255)
    private String headUrl;

    @Column(name = "address",nullable=true,length=100)
    private String address;

    /*
        科室
    */
    @Column(name = "department",nullable=false,length=100)
    private String department;


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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", tel='" + tel + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", address='" + address + '\'' +
                ", department='" + department + '\'' +
                ", createName='" + createName + '\'' +
                ", createTime=" + createTime +
                ", updateName='" + updateName + '\'' +
                ", updateTime=" + updateTime +
                ", version=" + version +
                ", delete=" + delete +
                '}';
    }
}
