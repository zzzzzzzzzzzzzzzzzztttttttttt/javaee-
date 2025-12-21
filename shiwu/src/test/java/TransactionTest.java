import com.itheima.service.BookService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TransactionTest {
    @Test
    public void testXmlTransaction() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BookService service = (BookService) context.getBean("bookService");
        service.deleteByIdxml(16);
        System.out.println("删除成功");

    }

    @Test
    public void testAnnotationTransaction() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-anno.xml");
        BookService service = (BookService) context.getBean("bookService");
        service.deleteByIdxmlannotation(1);
        System.out.println("删除成功");

    }
}