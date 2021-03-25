package cn.pch.hospitaldevicesystem.model.response;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

/**
 * @author 潘成花
 * @name MessageModel
 * @description 消息模型
 * @date 2021/3/25 17:29
 **/
@Data
public class MessageModel extends BaseModel{

    private Long userId;
    private String userName;
    private String userHeadUrl;

    /*
        短信的状态 具体看MessageStateEnums
    */
    private Integer state;
    /*
        短信的状态名字 具体看MessageStateEnums
    */
    private String stateName;
    /*
        短信内容
    */
    private String content;
}
