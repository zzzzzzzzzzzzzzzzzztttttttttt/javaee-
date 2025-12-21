import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.Mappers.MyMapper;
import org.example.pojo.Book;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;

public class MyTest
{
//    private SqlSession sqlSession;
//    @Before
//    public void init()
//    {
//        String re="mybatis_confg.xml";
//        Reader reader=null;
//        try
//        {
//            reader= Resources.getResourceAsReader(re);
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//        SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(reader);
//         sqlSession=sqlSessionFactory.openSession();
//    }
//    @Test
//    public void find()
//    {
//        book book1=sqlSession.selectOne("find",1);
//        System.out.println(book1);
//    }
//    @Test
//    public void add()
//    {
//        book book1=new book( "语文","1","西华师范大学","1");
//        System.out.println( sqlSession.insert("add",book1));
//    }
//    @Test
//    public void update()
//    {
//        book book1=new book( 3,"asdads","1","1","1");
//        System.out.println( sqlSession.update("update",book1));
//    }
//    @Test
//    public void delete()
//    {
//        System.out.println(sqlSession.delete("delete",3));
//    }
//    @After
//    public void end()
//    {
//
//        sqlSession.commit();
//        sqlSession.close();
//    }
    private MyMapper myMapper;
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
        myMapper=sqlSession.getMapper(MyMapper.class);
    }
    @Test
    public void find()
    {
        Book book1=myMapper.find(1);
        System.out.println(book1);
    }
    @Test

    public void add()
    {
        Book book1=new Book( "语文","1","西华师范大学","1");
        System.out.println(myMapper.add(book1));
    }
    @Test
    public void update()
    {
        Book book1=new Book( 1,"asdads","1","1","1");
      System.out.println(myMapper.update(book1));
    }
    @Test
    public void delete(){
        System.out.println(myMapper.delete(3));
    }
    @After
    public void end()
    {
      sqlSession.commit();
      sqlSession.close();
    }
}
