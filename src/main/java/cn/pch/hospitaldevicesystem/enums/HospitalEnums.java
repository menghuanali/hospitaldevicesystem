package cn.pch.hospitaldevicesystem.enums;

/**
 * @program: hospitaldevicesystem
 * @description: 医院的枚举类
 * @author: 潘成花
 * @create: 2021-01-29 12:41
 **/
public enum HospitalEnums {
    HOSPITAL_HUAXI("华西医院",1L),
    HOSPITAL_ZHONGLIU("肿瘤医院",2L),
    HOSPITAL_ZHONGZHENG("重症疾病医院",3L),
    HOSPITAL_HUABEI("华北医院",4L),
    HOSPITAL_RENMING("人民医院",5L);

    private final String name;
    private final Long id;

    private HospitalEnums(String name,Long id)
    {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public static HospitalEnums of(Long id) {
        if (id == null) {
            return null;
        }
        for (HospitalEnums temp : values()) {
            if (temp.getId().equals(id)) {
                return temp;
            }
        }
        return null;
    }
}