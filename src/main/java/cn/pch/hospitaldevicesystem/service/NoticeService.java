package cn.pch.hospitaldevicesystem.service;

import cn.pch.hospitaldevicesystem.entity.Notice;

import java.util.List;

/**
 * @author 潘成花
 * @name NoticeService
 * @description 系统公告
 * @date 2021/1/27 17:52
 **/
public interface NoticeService {
    /*
        插入修改一个公告
    */
    Notice insertOneNotice(Notice notice);
    /*
        根据id查找一个公告
    */
    Notice queryByid(Long id);
    /*
        得到所有的公告
    */
    List<Notice> queryAll();
    /*
        删除一个公告
    */
    void removeByid(Long id);
}
