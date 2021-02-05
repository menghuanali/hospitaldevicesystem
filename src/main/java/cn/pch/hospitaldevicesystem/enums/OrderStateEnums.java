package cn.pch.hospitaldevicesystem.enums;

/**
 * @program: hospitaldevicesystem
 * @description: 订单状态枚举
 * @author: 潘成花
 * @create: 2021-01-27 00:18
 **/
public enum OrderStateEnums {
    WAIT_ACCEPT("订单创建成功,待维修人员接单",1),
    PROCESSING("维修人员处理中",2),
    WAIT_OPINION("维修人员处理完成,待评价",3),
    COMPLETE("评价完成，已结单",4),
    OTHER("其他异常状态，请联系管理员",5);

    private final String name;
    private final Integer state;

    private OrderStateEnums(String name,Integer state)
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

    public static OrderStateEnums of(Integer state) {
        if (state == null) {
            return null;
        }
        for (OrderStateEnums temp : values()) {
            if (temp.getState().equals(state)) {
                return temp;
            }
        }
        return null;
    }

}
