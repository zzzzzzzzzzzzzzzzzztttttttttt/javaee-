package org.example.javaee;


import org.example.javaee.entity.User;
import org.example.javaee.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// 1. @MybatisTest：这是关键注解，它会告诉 Spring Boot 这是一个 MyBatis 的测试。
//    它会自动配置 MyBatis、SqlSessionFactory、DataSource 等核心组件，
//    并只扫描 @Mapper 接口，使得测试环境非常轻量化。
@MybatisTest
// 2. @AutoConfigureTestDatabase：默认情况下，@MybatisTest 会使用内存数据库（如 H2）。
//    如果你想使用真实的数据库（如 MySQL），需要用这个注解来禁用自动替换。
//    Replace.NONE 表示不替换任何数据源，使用你在 application.properties/yml 中配置的数据源。
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserMapperTest {

    // 3. @Autowired：直接注入你想要测试的 Mapper 接口。
    //    Spring Boot Test 会自动为你创建一个代理实现。
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectById_Success() {
        // 假设你的数据库中已经有一条 id=1 的用户记录
        Integer existingUserId = 1;

        // 调用 Mapper 方法
        User user = userMapper.selectById(existingUserId);

        // 验证结果
        assertNotNull(user, "查询到的用户不应为 null");
        assertEquals(existingUserId, user.getId());
        // 可以根据你数据库中的实际数据进行更多断言
        // assertEquals("zhangsan", user.getUsername());
        // assertEquals("123456", user.getPassword());
    }

    @Test
    public void testSelectById_NotFound() {
        // 查询一个不存在的 ID
        Integer nonExistingUserId = 999;

        User user = userMapper.selectById(nonExistingUserId);

        // 验证结果为 null
        assertNull(user, "查询不存在的用户应返回 null");
    }

    @Test
    public void testSelectAll() {
        // 调用查询所有用户的方法
        List<User> users = userMapper.selectAll();

        // 验证结果列表不为 null 且至少有一条数据
        assertNotNull(users);
        assertTrue(users.size() > 0, "数据库中至少应有一条用户数据");

        // 打印出所有用户，方便调试
        users.forEach(System.out::println);
    }

    @Test
    public void testInsertAndDelete() {
        // 1. 准备一个新用户
        User newUser = new User();
        newUser.setUsername("test_user");
        newUser.setPassword("test_pass");
        newUser.setMoney(1000);

        // 2. 测试插入
        int insertCount = userMapper.insert(newUser);
        assertEquals(1, insertCount, "插入操作应影响 1 行数据");
        assertNotNull(newUser.getId(), "插入后，ID 应被自动回填");
        System.out.println("插入的新用户 ID: " + newUser.getId());

        // 3. 测试查询刚插入的用户
        User insertedUser = userMapper.selectById(newUser.getId());
        assertNotNull(insertedUser);
        assertEquals(newUser.getUsername(), insertedUser.getUsername());

        // 4. 测试删除
        int deleteCount = userMapper.deleteById(newUser.getId());
        assertEquals(1, deleteCount, "删除操作应影响 1 行数据");

        // 5. 验证删除成功
        User deletedUser = userMapper.selectById(newUser.getId());
        assertNull(deletedUser);
    }
}