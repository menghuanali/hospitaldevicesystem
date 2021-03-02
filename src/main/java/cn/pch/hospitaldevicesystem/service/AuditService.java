package cn.pch.hospitaldevicesystem.service;

import cn.pch.hospitaldevicesystem.entity.Audit;

/**
 * @author 潘成花
 * @name AuditService
 * @description 审核
 * @date 2021/1/27 17:53
 **/
public interface AuditService {
    /**
     * 审核通过
     */
    void auditPass(Audit audit);

    /**
     * 审核拒绝
     */
    void auditReject(Audit audit);
}
