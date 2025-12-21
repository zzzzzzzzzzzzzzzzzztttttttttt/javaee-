import org.example.Config;
import org.example.Corp;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class spring01 {
    @Test
    public void test01(){
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        Corp crop=context.getBean(Corp.class);
        System.out.println(crop);
    }
    @Test
    public void test02(){
        ApplicationContext  context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Corp corp1= (Corp) context.getBean("corp1");
        System.out.println("设值注入结果：" + corp1);

        Corp corp2 = (Corp) context.getBean("corp2");
        System.out.println("构造注入结果：" + corp2);
    }
}
