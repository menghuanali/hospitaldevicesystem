package cn.pch.hospitaldevicesystem.enums;

/**
 * @program: hospitaldevicesystem
 * @description: 公告类型
 * @author: 潘成花
 * @create: 2021-01-27 00:37
 **/
public enum NoticeTypeEnums {
    HOSPITAL_NOTICE("医院动态",1),
    DEFEND_NOTICE("维护动态",2),
    HOLIDAY_NOTICE("节假动态",3),
    SYSTEM_NOTICE("系统动态",4),
    KEFU_NOTICE("客服动态",5),
    YIHU_NOTICE("医护动态",6),
    DEVICE_NOTICE("设备动态",7);

    private final String name;
    private final Integer code;

    private NoticeTypeEnums(String name,Integer code)
    {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Integer getCode() {
        return code;
    }
    public static NoticeTypeEnums of(String code) {
        String rolecode = (code == null ? null : code.toUpperCase());

        if (rolecode == null) {
            return null;
        }
        for (NoticeTypeEnums temp : values()) {
            if (temp.getCode().equals(rolecode)) {
                return temp;
            }
        }

        return null;
    }
}
