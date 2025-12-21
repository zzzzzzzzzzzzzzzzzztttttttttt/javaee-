import org.example.config.SpringConfig;
import org.example.config.SpringMvcConfig;
import org.example.service.BuyService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@org.springframework.test.context.web.WebAppConfiguration
@SpringJUnitConfig(classes = {SpringConfig.class, SpringMvcConfig.class})
public class test {
    @Autowired
    BuyService buyService;
    @Test
    public void t() {
        System.out.println(buyService.findById(1));
    }
}
