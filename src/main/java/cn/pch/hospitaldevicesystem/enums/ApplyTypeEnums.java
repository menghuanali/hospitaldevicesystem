package cn.pch.hospitaldevicesystem.enums;

/**
 * @program: hospitaldevicesystem
 * @description: 申请的类型
 * @author: 潘成花
 * @create: 2021-01-27 01:01
 **/
public enum ApplyTypeEnums {
    COMEFROM_SYSTEM("来自管理系统",1),
    COMEFROM_APP("来自移动APP",2),
    COMEFROM_PHONE("来自电话订单",3);

    private final String name;
    private final Integer state;

    private ApplyTypeEnums(String name,Integer state)
    {
        this.name = name;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public Integer getState() {
        return state;
    }

    public static ApplyTypeEnums of(Integer state) {
        if (state == null) {
            return null;
        }
        for (ApplyTypeEnums temp : values()) {
            if (temp.getState().equals(state)) {
                return temp;
            }
        }
        return null;
    }
}
