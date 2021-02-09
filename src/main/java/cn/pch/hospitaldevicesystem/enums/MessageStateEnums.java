package cn.pch.hospitaldevicesystem.enums;

/**
 * @program: hospitaldevicesystem
 * @description: 短信的状态
 * @author: 潘成花
 * @create: 2021-01-27 00:41
 **/
public enum MessageStateEnums {
    WAIT_READ("待阅读",1),
    READED("已阅读",2),
    UNUSUAL("异常状态",3);
    private final String name;
    private final Integer state;

    private MessageStateEnums(String name,Integer state)
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

    public static MessageStateEnums of(Integer state) {
        if (state == null) {
            return null;
        }
        for (MessageStateEnums temp : values()) {
            if (temp.getState().equals(state)) {
                return temp;
            }
        }
        return null;
    }
}
