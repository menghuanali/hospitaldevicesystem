package cn.pch.hospitaldevicesystem.service;

import cn.pch.hospitaldevicesystem.entity.Message;

import java.util.List;

/**
 * @author 潘成花
 * @name MessageService
 * @description
 * @date 2021/1/27 17:53
 **/
public interface MessageService {
    /*
        插入修改一条短信
    */
    Message insertOneMessage(Message message);
    /*
        得到发给某人得所有短信
    */
    List<Message> queryAllByUserId(Long userId);
    /*
        得到发给某个人某个状态得所有短信
    */
    List<Message> queryAllByUserIdAndState(Long userId,Integer state);
    /*
        根据id查找一条短信
    */
    Message queryByid(Long id);
    /*
        根据id删除
    */
    void removeByid(Long id);
}
