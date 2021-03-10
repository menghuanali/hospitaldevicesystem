package cn.pch.hospitaldevicesystem.model.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: hospitaldevicesystem
 * @description: 基类的模型
 * @author: 潘成花
 * @create: 2021-02-07 15:36
 **/
@Data
public class BaseModel implements Serializable {
//    当一个父类实现序列化，子类自动实现序列化，不需要显式实现Serializable接口；
    /*
        主键id
    */
    private Long id;
    private String createName;
    /*
        创建时间
    */
    private String createTime;
    private String updateName;
    private String updateTime;
    /*
        是否删除 逻辑删除 1表示删除了 0表示未删除 默认为0
    */
    private Integer delete;
}