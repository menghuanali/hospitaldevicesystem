package cn.pch.hospitaldevicesystem.model.response;

import lombok.Data;

/**
 * @program: hospitaldevicesystem
 * @description: 返回得评价模型
 * @author: Mr.Wang
 * @create: 2021-02-09 14:44
 **/
@Data
public class AppraiseModel extends BaseModel{
    private Long orderId;
    /*
        何人评价
    */
    private Long fromUserId;
    /*
        对何人评价
    */
    private Long toUserId;
    private String content;
    /*
        好评中评还是差评还是追加好评追加差评等 具体看 AppraiseTypeEnums
    */
    private Integer type;
    /*
        追加评价
    */
    private String appraiseAdd;
}