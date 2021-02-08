package cn.pch.hospitaldevicesystem.model.request;

import cn.pch.hospitaldevicesystem.model.response.BaseModel;
import lombok.Data;

/**
 * @program: hospitaldevicesystem
 * @description: 创建申请单的模型
 * @author: 潘成花
 * @create: 2021-01-29 16:16
 **/
@Data
public class ApplyCreateModel extends BaseModel {
    private Long deviceId;
    private String content;
    private Long fromUserId;
    private Long fromHospitalId;
    private Integer fromType;
}