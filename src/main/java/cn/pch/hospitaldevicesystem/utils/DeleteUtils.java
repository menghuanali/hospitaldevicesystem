package cn.pch.hospitaldevicesystem.utils;

import cn.pch.hospitaldevicesystem.repository.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @program: hospitaldevicesystem
 * @description: 删除脏数据的工具
 * @author: 潘成花
 * @create: 2021-03-23 22:57
 **/
@Service
public class DeleteUtils {
    @Resource
    ApplyRepository applyRepository;
    @Resource
    AppraiseRepository appraiseRepository;
    @Resource
    AuditRepository auditRepository;
    @Resource
    MessageRepository messageRepository;
    @Resource
    OrderLogRepository orderLogRepository;
    @Resource
    OrderRepository orderRepository;
    public void deleteDirtyData(Long userId,Long deviceId){
        messageRepository.findAllByUserId(userId);
        orderRepository.deleteByDoctorUserIdOrWorkerUserIdOrDeviceId(userId,userId,deviceId);
        applyRepository.deleteByFromUserIdOrDeviceId(userId,deviceId);
        auditRepository.deleteAudit();
        orderLogRepository.deleteOrderLog();
        appraiseRepository.deleteAppraise();
    }
}