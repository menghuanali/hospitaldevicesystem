package cn.pch.hospitaldevicesystem.entity;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @program: hospitaldevicesystem
 * @description: 公告信息
 * @author: 潘成花
 * @create: 2021-01-27 00:09
 **/
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "notice")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /*
        公告类型 具体看NoticeTypeEnums
    */
    @Column(name = "type",nullable=false,length=11)
    private Integer type;

    /*
    公告的标题
    */
    @Column(name = "title",nullable=false,length=200)
    private String title;
    /*
        公告的内容
    */
    @Column(name = "content",nullable=false,length=500)
    private String content;

    /*
        公告的图片 可为空
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
