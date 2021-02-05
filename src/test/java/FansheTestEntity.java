import cn.hutool.core.util.StrUtil;
import cn.pch.hospitaldevicesystem.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wanggang317
 * @name FansheTestEntity
 * @description
 * @date 2021/2/5 14:10
 **/

@Slf4j
public class FansheTestEntity {
    private static Map<String, String> map = new HashMap<>();
    private static List<String> K_V_WG = Lists.newArrayList
            (
                    "sysVersion", "版本号"
                    , "id", "主键"
                    , "createTime", "创建时间"
                    , "updateDate", "更新时间"
                    , "createDate", "创建时间"
                    , "createUser", "创建人"
                    , "createName", "创建人"
                    , "updateUser", "更新人"
                    , "isDelete", "是否删除"
                    , "updateName", "更新人"
                    , "operateName", "操作人"
                    , "operatePin", "操作人"
                    , "updateDate", "更新时间"
                    , "updateTime", "更新时间"
                    , "ts", "时间戳"
            );
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
//        AfsServiceQuery o = initMockPojo(AfsServiceQuery.class);
//        log.info("" + JSONObject.toJSONString(o));
        printPojo(User.class, 0);
    }

    public static Map<String, String> listToMap(List<String> list) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < list.size() / 2; i++) {
            map.put(list.get(2 * i), list.get(2 * i + 1));
        }
        return map;
    }

    public static <T> void printPojo(Class<T> clazz, int level) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        //参数名,是否必须,类型,说明,备注

        if (CLASS_LIST.contains(clazz.getName())) {
            return;
        }

        List<String> xx=Lists.newArrayList(
                "char","long","int","boolean","byte","float","double","[Ljava.lang.Long;","[Ljava.lang.Integer;","[Ljava.lang.Float;","[Ljava.lang.Double;","com.jd.order.sdk.domain.vo.FeeVo","java.util.Locale","com.jd.common.util.Money","java.lang.reflect.Constructor","com.jd.global.trade.sdk.export.cart.param.domain.ProductSetVO","com.jd.global.trade.sdk.export.cart.param.domain.SuitVO"
                ,"com.jd.global.trade.sdk.export.cart.param.domain.ManFanSuitVO","com.jd.global.trade.sdk.export.cart.param.domain.ManZengSuitVO","com.jd.global.trade.sdk.export.cart.param.domain.SkuSetVO","com.jd.global.trade.sdk.export.shipment.param.CombinationShipmentParam","com.jd.purchase.domain.old.bean.Freight"
        );
        if(xx.contains(clazz.getName())){
            return;
        }
        T t = clazz.newInstance();
        if (t.getClass().getName().equals("java.lang.Object")) {
            return;
        }
        Field[] fields = t.getClass().getDeclaredFields();
        List list = Lists.newArrayList("serialVersionUID");
        for (Field f : fields) {
            String name = f.getName();
            if (list.contains(name)) {
                continue;
            }
//            String typeName = f.getGenericType().getTypeName();
            String typeName = f.getType().getName();
            String[] xxx = typeName.split("\\.");
            String type = "";
            if (xxx.length == 0) {
                type = xxx[0];
            } else {
                type = xxx[xxx.length - 1];
            }
            if (!CLASS_LIST.contains(typeName)) {
                if(typeName.equals("java.util.Set")){
                    type = "Set";
                }else if(typeName.equals("java.util.List")){
                    type = "List";
                }else if(typeName.equals("java.util.Map")){
                    type = "Map";
                }else if(typeName.equals("java.util.Long[]")){
                    type = "Long[]";
                }else if(typeName.equals("java.util.Integer[]")){
                    type = "Integer[]";
                }else if(typeName.equals("java.util.Double[]")){
                    type = "Double[]";
                }else if(typeName.equals("java.util.Float[]")){
                    type = "Float[]";
                }else if(typeName.equals("java.util.Locale")){
                    type = "Locale";
                }else if(typeName.equals("long")){
                    type = "long";
                }else if(typeName.equals("double")){
                    type = "double";
                }else if(typeName.equals("float")){
                    type = "float";
                }else if(typeName.equals("int")){
                    type = "int";
                }else if(typeName.equals("boolean")){
                    type = "boolean";
                }else if(typeName.equals("byte")){
                    type = "byte";
                }else if(typeName.equals("char")){
                    type = "char";
                }else{
                    type = "Object";
                }
            }
            System.out.println(left(level) + name + "\t" + type + "\t" + getRemark(name));
            if (f.getType().getTypeName().equals("java.util.List")||f.getType().getTypeName().equals("java.util.Set")) {
                printPojo(getList(f.getGenericType().getTypeName()), level + 1);
            }else if(!CLASS_LIST.contains(typeName)){
                printPojo(f.getType(), level + 1);
            }

        }
        printPojo(t.getClass().getSuperclass(), level);
    }

    public static String getRemark(String key) {
        Map<String, String> map = listToMap(K_V_WG);
        String value = map.get(key);
        return value == null ? "" : value;
    }

    public static String camel(String type) {
        StringBuffer sb = new StringBuffer();
        sb.append(type);
        int c = (int) sb.charAt(0);
        sb.setCharAt(0, (char) (c + 32));
        return sb.toString();
    }

    public static <T> T initMockPojo(Class<T> clazz) throws IllegalAccessException, InstantiationException {
        T t = clazz.newInstance();
        Field[] fields = t.getClass().getDeclaredFields();
        int i = 1;
        long l = 1;
        byte b = 0x01;
        short s = 1;
        BigDecimal bigDecimal = new BigDecimal(1);
        for (Field f : fields) {
            String typeName = f.getGenericType().getTypeName();
            f.setAccessible(true);
            if (typeName.equals(String.class.getName())) {
                f.set(t, f.getName());
            } else if (typeName.equals(Integer.class.getName())) {
                f.set(t, i++);
            } else if (typeName.equals(Long.class.getName())) {
                f.set(t, l++);
            } else if (typeName.equals(Date.class.getName())) {
                f.set(t, new Date());
            } else if (typeName.equals(Short.class.getName())) {
                f.set(t, s++);
            } else if (typeName.equals(Byte.class.getName())) {
                f.set(t, b++);
            } else if (typeName.equals(Boolean.class.getName())) {
                f.set(t, true);
            } else if (typeName.equals(BigDecimal.class.getName())) {
                f.set(t, bigDecimal);
                bigDecimal = bigDecimal.add(new BigDecimal(1));
            }
        }
        return (T) t;
    }

    private static String left(int level) {
        if (level == 0) {
            return "";
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            while (level>0){
                level--;
                stringBuffer.append("   ");
            }
            stringBuffer.append("-");
            return stringBuffer.toString();
        }

    }

    private static Class getList(String genericType) throws ClassNotFoundException {
        List<String> xx = Lists.newArrayList(String.class.getName()
                , Integer.class.getName()
        );
        String clz = genericType.split("<")[1].replace(">", "");
        if (xx.contains(clz)) {
            return Object.class;
        }
        return Class.forName(clz);

    }
    private static final List<String> CLASS_LIST = Lists.newArrayList(
            Long.class.getName(),
            Integer.class.getName(),
            Double.class.getName(),
            Float.class.getName(),
            Byte.class.getName(),
            BigDecimal.class.getName(),
            Date.class.getName(),
            String.class.getName(),
            Boolean.class.getName(),
            Map.class.getName()
    );

}
