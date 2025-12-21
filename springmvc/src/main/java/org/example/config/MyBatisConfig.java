package org.example.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

// 标记为Spring配置类
@Configuration
// 扫描Mapper接口所在包（对应你目录里的org.example.mapper）
@MapperScan("org.example.mapper")
public class MyBatisConfig {

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) throws IOException {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();

        // 1. 设置数据源（从Spring容器中注入）
        factoryBean.setDataSource(dataSource);

        // 2. 设置实体类别名包（对应你目录里的org.example.pojo）
        factoryBean.setTypeAliasesPackage("org.example.pojo");

        // 3. 配置Mapper XML文件路径
        // 你的XML在resources/mapper/下，所以路径是classpath:mapper/*.xml
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factoryBean.setMapperLocations(
                resolver.getResources("classpath:mapper/*.xml")
        );

        return factoryBean;
    }
}