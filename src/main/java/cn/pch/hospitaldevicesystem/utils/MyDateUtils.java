package cn.pch.hospitaldevicesystem.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * @author 潘成花
 * @name MyDateUtils
 * @description
 * @date 2021/1/27 15:24
 **/
public class MyDateUtils {
    /**
     * 得到当前的时间 格式 2021-01-27 15:25:25
     */
    public static String GetNowDate(){
        return DateUtil.now();
    }

    /*
        得到当前的时间 格式 2021-01-27
    */
    public static String GetNowDateRiQi(Integer number){
        Date date = DateUtil.date();
        DateTime newDate2 = DateUtil.offsetDay(date, number);
        return DateUtil.formatDate(newDate2);
    }

}
