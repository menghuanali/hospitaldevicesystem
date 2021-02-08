package cn.pch.hospitaldevicesystem.model.response;

/**
 * @program: hospitaldevicesystem
 * @description: 返回的申请单模型
 * @author: 潘成花
 * @create: 2021-02-07 15:34
 **/
public class ApplyModel extends BaseModel{
    /*
    设备id
    */
    private Long deviceId;
    /*
    申请的文字内容
    */
    private String content;
    /*
    申请的状态 具体看ApplyStateEnums
    */
    private Integer state;
    /*
        何人申请
    */
    private Long fromUserId;
    /*
    来自哪个医院的设备申请
    */
    private Long fromHospitalId;

    /*
    申请的类型 具体看 ApplyTypeEnums
*/
    private Integer fromType;
}