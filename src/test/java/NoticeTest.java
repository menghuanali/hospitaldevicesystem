import cn.pch.hospitaldevicesystem.entity.Notice;
import cn.pch.hospitaldevicesystem.enums.NoticeTypeEnums;
import cn.pch.hospitaldevicesystem.service.NoticeService;
import cn.pch.hospitaldevicesystem.utils.MyDateUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author 潘成花
 * @name NoticeTest
 * @description
 * @date 2021/1/27 16:30
 **/
@Slf4j
public class NoticeTest extends TmallApplicationTests{

    @Resource
    NoticeService noticeService;

    @Test
    public void insertAnyNotice(){
        for(int i=1;i<10;i++){
            Notice notice = new Notice();
            notice.setType(NoticeTypeEnums.DEVICE_NOTICE.getCode());
            notice.setTitle("公告的标题公告的标题公告的标题"+i);
            notice.setContent("公告的内容公告的内容公告的内容公告的内容公告的内容公告的内容公告的内容公告的内容公告的内容"+i);
            notice.setPictureUrl("/static/images/notice/"+i+".jpg");
            notice.setCreateName("系统");
            notice.setCreateTime(MyDateUtils.GetNowDate());
            noticeService.insertOneNotice(notice);
        }
    }
}
