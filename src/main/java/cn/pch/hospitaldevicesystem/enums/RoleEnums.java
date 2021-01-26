package cn.pch.hospitaldevicesystem.enums;

/**
 * @program: hospitaldevicesystem
 * @description: 角色权限的枚举类
 * @author: Mr.Pan
 * @create: 2021-01-24 13:29
 **/
public enum RoleEnums {
    ROLE_ADMIN("管理员","ROLE_ADMIN"),
    ROLE_YHUSER("医护人员","ROLE_YHUSER"),
    ROLE_WXUSER("维修人员","ROLE_WXUSER"),
    ROLE_KEUSER("普通客服人员","ROLE_KEUSER"),
    ROLE_KPJKEUSER("可评价客服人员","ROLE_KPJKEUSER"),
    ROLE_USER("访客人员","ROLE_USER");

    private final String name;
    private final String code;

    private RoleEnums(String name,String code)
    {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
    public static RoleEnums of(String code) {
        String rolecode = (code == null ? null : code.toUpperCase());

        if (rolecode == null) {
            return null;
        }
        for (RoleEnums temp : values()) {
            if (temp.getCode().equals(rolecode)) {
                return temp;
            }
        }

        return null;
    }
}