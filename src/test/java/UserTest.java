import cn.hutool.core.util.RandomUtil;
import cn.pch.hospitaldevicesystem.entity.User;
import cn.pch.hospitaldevicesystem.enums.RoleEnums;
import cn.pch.hospitaldevicesystem.model.response.UserModel;
import cn.pch.hospitaldevicesystem.repository.UserRepository;
import cn.pch.hospitaldevicesystem.service.UserService;
import cn.pch.hospitaldevicesystem.utils.MyDateUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author 潘成花
 * @name UserTest
 * @description
 * @date 2021/1/27 15:12
 **/
@Slf4j
public class UserTest extends TmallApplicationTests{

    @Resource
    UserRepository userRepository;

    @Resource
    UserService userService;

    private static String xing = "赵钱孙李周吴郑王冯陈褚卫蒋沈韩杨朱秦尤许何吕施张孔曹严华金魏陶姜戚谢邹喻柏水窦章云苏潘葛奚范彭郎鲁韦昌马苗凤花方俞任袁柳酆鲍史唐费廉岑薛雷贺倪汤滕殷罗毕郝邬安常乐于时傅皮卞齐康伍余元卜顾孟平黄和穆萧尹姚邵湛汪祁毛禹狄米贝明臧计伏成戴谈宋茅庞熊纪舒屈项祝董梁杜阮蓝闵席季麻强贾路娄危江童颜郭梅盛林刁钟丘徐邱骆高夏蔡田樊胡凌霍虞万支柯昝管卢莫经房裘缪干解应宗丁宣贲邓单杭洪包诸左石崔吉钮龚程嵇邢滑裴陆荣翁荀羊於惠甄曲家封芮羿储靳汲邴糜松井段富巫乌焦巴弓牧隗山谷车侯宓蓬全郗班仰秋仲伊宫宁仇栾暴甘钭厉戎祖武符刘景詹束龙叶幸司韶郜黎蓟薄印宿白怀蒲台从鄂索咸籍赖卓蔺屠蒙池乔阴郁胥能苍双闻莘党翟谭贡劳逢逄姬申扶堵冉宰郦雍?S璩桑桂濮牛寿通边扈燕冀郏浦尚农温别庄晏柴瞿阎充慕连茹习宦艾鱼容向古易慎戈廖庚终暨居衡步都耿满弘匡国文寇广禄阙东欧殳沃利蔚越夔隆师巩厍聂晁勾敖融冷訾辛阚那简饶空曾毋沙乜养鞠须丰巢关蒯相查荆红游竺权逯盖益桓公万俟司马上官欧阳夏侯诸葛闻人东方赫连皇甫尉迟公羊澹台公冶宗政濮阳淳于单于太叔申屠公孙仲孙轩辕令狐钟离宇文长孙慕容鲜于闾丘司徒司空亓官司寇仉督子车颛孙端木巫马公西漆雕乐正壤驷公良拓拔夹谷宰父谷粱晋楚阎法汝鄢涂钦段干百里东郭南门呼延归海羊舌微生岳帅缑亢况后有琴梁丘左丘东门西门商牟佘佴伯赏南宫墨哈谯笪年爱阳佟第五言福";
    private static String ming1 = "盛朔淇炎飞辰楚语昂鸿澎云钦宣震鑫煜琛哲飞登弘睿曜寒浦海雄瑞河昆平彭超廷康鸿建磊迅邦材炎致晓梓辰炯高国靖明晋伦桦诚达学胜博佑昌朋中瑄景捷哲晗锦杰炜德昌君子善涛掣泽启涵先博光和泰坚祥亿悟胜晔兵为高钊理宏荣豪阳广才新登富向";
    List<String> Name_Lists = Lists.newArrayList() ;
    @Test
    public void saveUserTest(){
        for(int i=1;i<=5;i++){
            User user = new User();
            user.setUsername("花花");
            user.setPassword("$10$3gkQ24jYW0hWXvZjdneKuO5dg2HDDDihLe6Zo9HdV8fxg1JwAWmfi");
            user.setRole(RoleEnums.ROLE_ADMIN.getCode());
            user.setTel("132");
            user.setHeadUrl("www");
            user.setAddress("地址信息");
            user.setDepartment("科室");
            user.setCreateName("创建者");
            user.setCreateTime(MyDateUtils.GetNowDate());
            User save = userRepository.save(user);
            log.info(":{} --PCH", JSON.toJSONString(save));
        }
    }

    @Test
    public void updateUserTest(){
//        版本号+1 乐观锁得到控制 然后其他不改变的字段也必须为原来的值 否则会出现都为空了
        Optional<User> op = userRepository.findById(1L);
        if(op.isPresent()){//不为空才进来
            User user = op.get();
            user.setUsername("花花2号");
            User save = userRepository.save(user);
            log.info(":{} --PCH", JSON.toJSONString(save));
        }
    }

    @Test
    public void moreTableQuery(){
        List<Object> result = userRepository.findAllMessageByUserId(1L);
        log.info(":{} --PCH", JSON.toJSONString(result));
    }

    @Test
    public void getUserByRole(){
        List<UserModel> result = userService.queryAllByRole(RoleEnums.ROLE_ADMIN.getCode());
        log.info(":{} ", JSON.toJSONString(result));
    }
    //插入一些随机用户
    @Test
    public void insertSuijiUser(){
        for (int i=0;i<50;i++){
            User user = new User();
            String username = String.valueOf(xing.charAt(RandomUtil.randomInt(0,xing.length()-1)))+String.valueOf(ming1.charAt(RandomUtil.randomInt(0,ming1.length()-1)));
            int sort = 1;
            while(userRepository.findByUsername(username)!=null){
                username = username+sort;
            }
            user.setUsername(username);
            user.setPassword("123");
            if(i%7==0){
                user.setRole(RoleEnums.ROLE_USER.getCode());
            }else if(i%7==1){
                user.setRole(RoleEnums.ROLE_YHUSER.getCode());
            }else if(i%7==2){
                user.setRole(RoleEnums.ROLE_WXUSER.getCode());
            }else if(i%7==3){
                user.setRole(RoleEnums.ROLE_KEUSER.getCode());
            }else if(i%7==4){
                user.setRole(RoleEnums.ROLE_KPJKEUSER.getCode());
            }else if(i%7==5){
                user.setRole(RoleEnums.ROLE_YHUSER.getCode());
            }else if(i%7==6){
                user.setRole(RoleEnums.ROLE_USER.getCode());
            }
            user.setTel(String.valueOf(RandomUtil.randomInt(1840000000,1849999999)+i%10));
            user.setHeadUrl("www");
            user.setAddress("北京市朝阳区霄云路50号");
            user.setDepartment("科室2");
            user.setCreateName("创建者");
            user.setCreateTime(MyDateUtils.GetNowDate());
            User save = userRepository.save(user);
            log.info(":{} --PCH", JSON.toJSONString(save));
        }
    }
}
