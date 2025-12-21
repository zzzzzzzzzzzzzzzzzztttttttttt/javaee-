import org.example.Config;
import org.example.dao.BookstoreDao;
import org.example.service.BookstoreService;
import org.example.service.BookstoreServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class springtr {
    @Test
    public void test01(){
        BookstoreServiceImpl bookstoreService = new BookstoreServiceImpl();
        bookstoreService.remove(1);
    }
}
