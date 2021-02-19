package bean.beanregister;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class FiiBeanRegisterTest {

    @Test
    public void testBeanDefinitationWithBeanRegister() {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(FiiBeanRegister.class);
        Fii fii = ctx.getBean(Fii.class);
        System.out.println(fii.getName());
    }
}
