package cn.pch.hospitaldevicesystem.service.impl;

import cn.pch.hospitaldevicesystem.entity.Message;
import cn.pch.hospitaldevicesystem.repository.MessageRepository;
import cn.pch.hospitaldevicesystem.service.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

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
    @Override
    public Message insertOneMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public List<Message> queryAllByUserId(Long userId) {
        return messageRepository.findAllByUserId(userId);
    }

    @Override
    public List<Message> queryAllByUserIdAndState(Long userId, Integer state) {
        return messageRepository.findAllByUserIdAndState(userId,state);
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
}
