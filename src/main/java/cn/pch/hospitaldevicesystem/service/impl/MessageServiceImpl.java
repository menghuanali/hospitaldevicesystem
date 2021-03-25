package cn.pch.hospitaldevicesystem.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.pch.hospitaldevicesystem.entity.Device;
import cn.pch.hospitaldevicesystem.entity.Hospital;
import cn.pch.hospitaldevicesystem.entity.Message;
import cn.pch.hospitaldevicesystem.entity.User;
import cn.pch.hospitaldevicesystem.enums.DeviceStateEnums;
import cn.pch.hospitaldevicesystem.enums.MessageStateEnums;
import cn.pch.hospitaldevicesystem.model.response.DeviceModel;
import cn.pch.hospitaldevicesystem.model.response.MessageModel;
import cn.pch.hospitaldevicesystem.repository.MessageRepository;
import cn.pch.hospitaldevicesystem.service.MessageService;
import cn.pch.hospitaldevicesystem.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author 潘成花
 * @name MessageServiceImpl
 * @description
 * @date 2021/1/27 17:58
 **/
@Service
public class MessageServiceImpl implements MessageService {
    @Resource
    MessageRepository messageRepository;
    @Resource
    UserService userService;
    @Override
    public Message insertOneMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public List<MessageModel> queryAllByUserId(Long userId) {
        List<Message> dbResultLsit = messageRepository.findAllByUserId(userId);
        List<MessageModel> result = dbResultLsit.stream().map(message -> {
            MessageModel messageModel = new MessageModel();
            BeanUtil.copyProperties(message,messageModel);
            messageModel.setStateName(MessageStateEnums.of(message.getState()).getName());
            User user = userService.queryById(message.getUserId());
            messageModel.setUserName(user.getUsername());
            messageModel.setUserHeadUrl(user.getHeadUrl());
            return messageModel;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<MessageModel> queryAllByUserIdAndState(Long userId, Integer state) {
        List<Message> dbResultLsit = messageRepository.findAllByUserIdAndState(userId,state);
        List<MessageModel> result = dbResultLsit.stream().map(message -> {
            MessageModel messageModel = new MessageModel();
            BeanUtil.copyProperties(message,messageModel);
            messageModel.setStateName(MessageStateEnums.of(message.getState()).getName());
            User user = userService.queryById(message.getUserId());
            messageModel.setUserName(user.getUsername());
            return messageModel;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public Message queryByid(Long id) {
        Optional<Message> dbResult = messageRepository.findById(id);
        if(dbResult.isPresent()){
            Message message = dbResult.get();
            return message;
        }else{
            return null;
        }
    }

    @Override
    public void removeByid(Long id) {
        messageRepository.deleteById(id);
    }

    @Override
    public List<MessageModel> queryAllByCreateName(String createName) {

        List<Message> dbResultLsit = messageRepository.findAllByCreateName(createName);
        List<MessageModel> result = dbResultLsit.stream().map(message -> {
            MessageModel messageModel = new MessageModel();
            BeanUtil.copyProperties(message,messageModel);
            messageModel.setStateName(MessageStateEnums.of(message.getState()).getName());
            User user = userService.queryById(message.getUserId());
            messageModel.setUserName(user.getUsername());
            return messageModel;
        }).collect(Collectors.toList());
        return result;
    }
}
