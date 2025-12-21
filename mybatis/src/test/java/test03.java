import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.Mappers.CustomerMapper;
import org.example.Mappers.SqlMapper;
import org.example.pojo.Book;
import org.example.pojo.Customer;
import org.example.pojo.Orders;
import org.example.pojo.buy;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class test03 {
    private CustomerMapper customerMapper;
    private SqlSession sqlSession;
    @Before
    public void init()
    {
        String re="mybatis_confg.xml";
        Reader reader=null;
        try
        {
            reader= Resources.getResourceAsReader(re);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(reader);
        sqlSession=sqlSessionFactory.openSession();
        customerMapper=sqlSession.getMapper(CustomerMapper.class);
    }
   @Test
   public void find01()
   {
       Customer customer=customerMapper.findCustomerOrders1(1);
       System.out.println("顾客："+ customer.getName());
       System.out.println("购书记录：");
       for(buy b:customer.getBuy())
       {
           System.out.println("书名："+ b.getBookName()+" 数量："+ b.getNumber());
       }
   }
   @Test
   public void find02()
   {
       Customer customer=customerMapper.findCustomerOrders2(3);
       System.out.println("顾客："+ customer.getName());
       System.out.println("购书记录：");
       for(buy b:customer.getBuy())
       {
           System.out.println("书名："+ b.getBookName()+" 数量："+ b.getNumber());
       }
   }
    @After
    public void end()
    {
        sqlSession.commit();
        sqlSession.close();
    }
}
