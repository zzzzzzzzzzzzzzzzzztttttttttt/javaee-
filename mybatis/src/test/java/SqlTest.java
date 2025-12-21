import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.Mappers.SqlMapper;
import org.example.pojo.Book;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class SqlTest {
    private SqlMapper sqlmapper;
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
        sqlmapper=sqlSession.getMapper(SqlMapper.class);
    }
    @Test
    public void find()
    {
       Book book1=new Book();
      // book1.setAuthor("1");
       //book1.setPress("西华师范大学");
       List<Book> bookList=sqlmapper.find(book1);
       for(Book book:bookList){
           System.out.println(book);
       }
    }
    @Test
    public void findByPress()
    {
        List<String> press = new ArrayList<>();
        press.add("西");
        press.add("3");

        List<Book> bookList=sqlmapper.findByPress(press);
        for(Book book:bookList){
            System.out.println(book);
        }
    }
    @After
    public void end()
    {
        sqlSession.commit();
        sqlSession.close();
    }
}
