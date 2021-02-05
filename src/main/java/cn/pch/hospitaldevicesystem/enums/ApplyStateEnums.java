package cn.pch.hospitaldevicesystem.enums;

/**
 * @program: hospitaldevicesystem
 * @description: 申请状态
 * @author: 潘成花
 * @create: 2021-01-27 00:59
 **/
public enum ApplyStateEnums {
    WAIT_AUDIT("申请成功,待审核",1),
    AUDIT_PASS("审核通过,创建了订单",2),
    AUDIT_REJECT("审核拒绝",3),
    CANCEL("申请已取消",4),
    OTHER("其他异常状态,请联系管理员",5);

    private final String name;
    private final Integer state;

    private ApplyStateEnums(String name,Integer state)
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

    public static ApplyStateEnums of(Integer state) {
        if (state == null) {
            return null;
        }
        for (ApplyStateEnums temp : values()) {
            if (temp.getState().equals(state)) {
                return temp;
            }
        }
        return null;
    }
}
