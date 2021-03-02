package cn.pch.hospitaldevicesystem.service.impl;

import cn.pch.hospitaldevicesystem.entity.Audit;
import cn.pch.hospitaldevicesystem.repository.AuditRepository;
import cn.pch.hospitaldevicesystem.service.AuditService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 潘成花
 * @name AuditServiceImpl
 * @description
 * @date 2021/1/27 17:57
 **/
@Service
public class AuditServiceImpl implements AuditService {
    @Resource
    AuditRepository auditRepository;


    @Override
    public void auditPass(Audit audit) {
        auditRepository.save(audit);
    }

    @Override
    public void auditReject(Audit audit) {
        auditRepository.save(audit);
    }
}
