package cn.pch.hospitaldevicesystem.model.response;

import lombok.Data;

/**
 * @program: hospitaldevicesystem
 * @description: 返回的申请单模型
 * @author: 潘成花
 * @create: 2021-02-07 15:34
 **/
@Data
public class ApplyModel extends BaseModel{
    /*
    设备id
    */
    private Long deviceId;
    /*
    设备名
    */
    private String deviceName;
    /*
    申请的文字内容
    */
    private String content;
    /*
    申请的状态 具体看ApplyStateEnums
    */
    private Integer state;
    /*
    申请的状态名字 具体看ApplyStateEnums
    */
    private String stateName;
    /*
        何人申请
    */
    private Long fromUserId;
    /*
    何人申请名字
    */
    private String fromUserIdName;
    /*
    来自哪个医院的设备申请
    */
    private Long fromHospitalId;
    /*
    来自哪个医院的设备申请名字
    */
    private String fromHospitalIdName;

    /*
    申请的类型 具体看 ApplyTypeEnums
    */
    private Integer fromType;
    /*
    申请的类型名字 具体看 ApplyTypeEnums
    */
    private String fromTypeName;
}