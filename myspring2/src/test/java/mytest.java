import org.example.pojo.buy;
import org.example.service.BuyService;
import org.example.service.OrdersService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@ContextConfiguration(locations = "classpath:application.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class mytest {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private BuyService buyService;
    @Test
    public void buyBook(){

        ordersService.byBook(1,3,60);
    }
    @Test
    public void b(){
        List<buy> by=buyService.findById(1);
        for (buy b:by) {
            System.out.println("书名："+b.getBookName()+"数量"+b.getNumber());
        }

    }
}
