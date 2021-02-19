package bean.xml;

import bean.xml.service.FooService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * 加载通过xml装配的bean
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:bean/xml/applicationContext.xml"})
public class FooServiceImplTest {

    //region 使用默认的xml装配Bean Instantiation with a constructor/properties

    @Resource(name = "fooService")
    private FooService fooService;

    @Test
    public void testDefaultXmlBeanDefinition() {
        fooService.foo();
    }

    //endregion

    //region 使用xml装配Bean 实例工厂 Instantiation using an instance factory method
    @Resource(name = "fooService2")
    private FooService fooService2;

    @Test
    public void testXmlBeanDefinitionWithInstanceFactoryMethod() {
        fooService2.foo();
    }

    //endregion

    //region 使用xml装配Bean 静态工厂 Instantiation with a static factory method

    @Resource(name = "fooService3")
    private FooService fooService3;

    @Test
    public void testXmlBeanDefinitionWithStaticFactoryMethod() {
        fooService3.foo();
    }

    //endregion
}
