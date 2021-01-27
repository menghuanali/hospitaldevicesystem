import cn.pch.hospitaldevicesystem.SpringbootPCHApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 潘成花
 * @name TmallApplicationTests
 * @description 测试类直接继承的父类
 * @date 2021/1/27 15:10
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
//由于是Web项目，Junit需要模拟ServletContext，SpringbootPCHApplication为主入口。
@ContextConfiguration(classes = SpringbootPCHApplication.class)
public class TmallApplicationTests {
    @Before
    public void init() {
        System.out.println("开始测试-----------------");
    }

    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }

}
