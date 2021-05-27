package cn.pch.hospitaldevicesystem.enums;

/**
 * @program: hospitaldevicesystem
 * @description: 评价的类别
 * @author: 潘成花
 * @create: 2021-01-27 00:55
 **/
public enum AppraiseTypeEnums {
    GOOD_APPRAISE("好",3),
    MEDIUM_APPRAISE("中评",2),
    BAD_APPRAISE("差评",1),
    ADD_GOOD_APPRAISE("追加好评",6),
    ADD_MEDIUM_APPRAISE("追加中评",5),
    ADD_BAD_APPRAISE("追加差评",4);
    private final String name;
    private final Integer code;

    private AppraiseTypeEnums(String name,Integer code)
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

    public static AppraiseTypeEnums of(Integer state) {
        if (state == null) {
            return null;
        }
        for (AppraiseTypeEnums temp : values()) {
            if (temp.getCode().equals(state)) {
                return temp;
            }
        }
        return null;
    }
}
