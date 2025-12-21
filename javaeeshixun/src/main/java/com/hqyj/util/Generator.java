package com.hqyj.util;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.Collections;

/**
 * @Description
 * @Autor 伍军
 * @Date 2022/9/30 10:10
 * @Version 1.0
 **/
public class Generator {
    /*
     * 数据源配置
     * */
    private static final String URL = "jdbc:mysql://localhost:3306/xhsf?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
    private static final String username = "root";
    private static final String password = "123456";

    private static final String projectPath = System.getProperty("user.dir");

    public static void main(String[] args) {
        System.out.println(projectPath);
        FastAutoGenerator.create(URL, username, password)
                // 全局配置
                .globalConfig(builder -> {
                    builder.author("rock")   // 作者
                            .outputDir(projectPath + "/src/main/java")     // 指定输出目录
                            .disableOpenDir()  // 禁止打开输出目录
                            .dateType(DateType.ONLY_DATE) // 时间策略
                            .commentDate("yyyy-MM-dd hh:mm:ss") //注释日期
                            .build();
                })
                // 包配置
                .packageConfig(builder -> {
                    builder.parent("com.hqyj")   // 父包名
                            .entity("pojo")               // Entity 包名
                            .service("service")             // service 包名
                            .serviceImpl("service.impl")    //
                            .mapper("mapper")               //Mapper包名
                            .xml("mapper.xml")              // MapperXML 包名
                            .controller("controller")       //controller 包名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, projectPath + "/src/main/resources/mapper")) // 自定义mapper.xml文件的路径
                            .build();
                })
                // 策略配置
                .strategyConfig(builder -> {
                    builder.addInclude("employee_contract")//生成的表名
                            .entityBuilder()  // 1 开启实体类配置
                            .enableTableFieldAnnotation()//开启实体类生成注解
                            .enableLombok()//开启Lombox注解配置
                            .idType(IdType.AUTO)//设置主键自动递增
                            .logicDeleteColumnName("deleted")//逻辑删除数据库列名
                            .logicDeletePropertyName("deleteFlag")//逻辑删除实体属性名
                            .controllerBuilder() // 2 开启controller 策略配置
                            .enableHyphenStyle() // 开启驼峰转连字符
                            .enableRestStyle()//开启生成@Restcontroller控制器
                            .serviceBuilder() // 3 开启service 配置
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl")
                            .mapperBuilder()  //4 卡其mapper(dao) 配置
                            .superClass(BaseMapper.class)//设置父类
                            .enableMapperAnnotation()//开启@Mapper注解
                            .enableBaseResultMap()//启用 sql映射文件BaseResultMap生成
                            .enableBaseColumnList()//启用 sql映射文件BaseColumnList生成
                            .formatMapperFileName("%sMapper")//设置dao接口包名
                            .formatXmlFileName("%sMapperXml")//设置dao的sql文件命名

                            .build();
                })
                // 模板配置
                .templateEngine(new VelocityTemplateEngine()) // 默认

                .execute();
    }
}

