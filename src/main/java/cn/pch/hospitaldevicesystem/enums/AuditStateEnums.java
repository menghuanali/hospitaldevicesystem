package cn.pch.hospitaldevicesystem.enums;

/**
 * @program: hospitaldevicesystem
 * @description: 审核状态
 * @author: 潘成花
 * @create: 2021-01-27 00:49
 **/
public enum AuditStateEnums {
    AUDIT_PASSED("审核通过",1),
    AUDIT_REJECTED("审核拒绝",2);
    //以后还可加升级处理等状态
    private final String name;
    private final Integer state;

    private AuditStateEnums(String name,Integer state)
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

    public static AuditStateEnums of(Integer state) {
        if (state == null) {
            return null;
        }
        for (AuditStateEnums temp : values()) {
            if (temp.getState().equals(state)) {
                return temp;
            }
        }
        return null;
    }
}
