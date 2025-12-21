import org.example.service.OrdersService;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class mytest {
    @Autowired
    private ApplicationContext context; // Spring 容器上下文

    @Test
    public void checkBean() {
        // 打印容器中所有 Bean 的名称，看是否包含 "ordersService"
        String[] beanNames = context.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            if (beanName.contains("ordersService")) {
                System.out.println("已自动注册的 Bean：" + beanName);
                // 打印 Bean 的实际类型（应为 OrdersServiceImpl）
                System.out.println("Bean 类型：" + context.getBean(beanName).getClass());
            }
        }
    }
}
