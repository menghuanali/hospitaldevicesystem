package cn.pch.hospitaldevicesystem.enums;

/**
 * @program: hospitaldevicesystem
 * @description: 设备的状态
 * @author: 潘成花
 * @create: 2021-01-27 00:45
 **/
public enum DeviceStateEnums {
    WAIT_REPAIR("待维修",1),
    NORMAL("正常",2),
    FAULTING("故障中，申请维修中",3),
    SCRAPPED("报废了",4),
    COMFIRM("维修后待确定",5),
    OTHER("其他异常状态,请联系管理员",6);


    private final String name;
    private final Integer state;

    private DeviceStateEnums(String name,Integer state)
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

    public static DeviceStateEnums of(Integer state) {
        if (state == null) {
            return null;
        }
        for (DeviceStateEnums temp : values()) {
            if (temp.getState().equals(state)) {
                return temp;
            }
        }
        return null;
    }
}
