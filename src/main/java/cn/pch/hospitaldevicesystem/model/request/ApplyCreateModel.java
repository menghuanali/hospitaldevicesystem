package cn.pch.hospitaldevicesystem.model.request;

import lombok.Data;

/**
 * @program: hospitaldevicesystem
 * @description: 创建申请单的模型
 * @author: Mr.Wang
 * @create: 2021-01-29 16:16
 **/
@Data
public class ApplyCreateModel {
    private Long deviceId;
    private String content;
    private Integer state;
    private Long fromUserId;
    private Long fromHospitalId;
    private Integer fromType;
}