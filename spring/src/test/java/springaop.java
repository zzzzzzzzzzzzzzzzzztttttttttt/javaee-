import org.example.Config;
import org.example.Corp;
import org.example.Employee;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class springaop {
    @Test
    public void test01(){
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        Corp crop=context.getBean(Corp.class);
        Employee e=new Employee("aaa","111");
        crop.addWorker(e);
    }

    @Test
    public void test02(){
        ApplicationContext  context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Corp corp1= (Corp) context.getBean("corp1");
        Employee e=new Employee("bbb","222");
        corp1.addWorker2(e);
    }
}
