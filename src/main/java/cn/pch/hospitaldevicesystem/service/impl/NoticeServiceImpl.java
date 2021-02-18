package cn.pch.hospitaldevicesystem.service.impl;

import cn.pch.hospitaldevicesystem.entity.Notice;
import cn.pch.hospitaldevicesystem.repository.NoticeRepository;
import cn.pch.hospitaldevicesystem.service.NoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author 潘成花
 * @name NoticeServiceImpl
 * @description
 * @date 2021/1/27 17:58
 **/
@Service
public class NoticeServiceImpl implements NoticeService {
    @Resource
    NoticeRepository noticeRepository;
    @Override
    public Notice insertOneNotice(Notice notice) {
        return noticeRepository.save(notice);
    }

    @Override
    public Notice queryByid(Long id) {
        Optional<Notice> dbResult = noticeRepository.findById(id);
        if(dbResult.isPresent()){
            Notice notice = dbResult.get();
            return notice;
        }else {
            return null;
        }
    }

    @Override
    public List<Notice> queryAll() {
        return noticeRepository.findALLNoticeByIdDesc();
    }

    @Override
    public void removeByid(Long id) {
        noticeRepository.deleteById(id);
    }
}
