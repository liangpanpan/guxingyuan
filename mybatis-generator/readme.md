前言
本文我们主要实战Mybatis官方的代码生成器：Mybatis Generator(MBG)，掌握它以后，可以简化大部分手写代码，我们只需要写复杂逻辑代码！

通过前几篇，我们掌握了在SpringBoot下Mybatis的基本用法，操作步骤回顾一下：

创建与MySQL表对应的Java PO对象，字段一一对应；
创建Mapper接口，和对应的XML映射文件(或注解)。
在实际开发中，你会发现有很多重复的工作：

首先是PO对象，我们往往创建与数据库表字段一一对应的PO对象;
其次在Mapper里，通常都会写基本的增删改查代码。
重复不符合我们程序员的风格：

其实对于PO对象，只需要读取数据库表字段，然后做SQL列类型与Java字段类型转换，再将列名转成骆驼命名就可以实现，所以能实现的代码生成器也非常多！
再者对于单表的基本增删改查是ORM的基本功能，像JPA对单表的基本增删改查都进行了封装！
那么在Mybatis里，这些当然也不在话下，Mybatis官方为我们提供了非常好用的代码生成器：Mybatis Generator (MBG) 。
本文就带大家实战一下Mybatis Generator，用它生成实战项目全部表的PO对象和基本增删改查代码！

一、Mybatis Generator简介
Mybatis Generator（MBG）是Mybatis官方提供的一个代码生成工具，它可以根据数据库表自动生成对应的Java Model、Mapper和XML文件，甚至还可以生成一些高级查询功能，使用Mybatis-Generator可以大大降低开发者的工作量，提高开发效率！

支持使用Maven插件生成，也支持编写Java代码生成，还有命令行等等多种方式！
支持生成XML映射文件风格的代码，也支持生成全注解风格的代码！

二、Maven插件运行方式
首先在工程tg-book-dal的pom.xml中，根节点project下添加build节点，如下：

```
<build>
    <resources>
        <resource>
            <directory>src/main/resources</directory>
            <filtering>true</filtering>
        </resource>
    </resources>
    <plugins>
        <plugin>
            <groupId>org.mybatis.generator</groupId>
            <artifactId>mybatis-generator-maven-plugin</artifactId>
            <version>1.3.7</version>
            <configuration>
                <verbose>true</verbose>
                <overwrite>true</overwrite>
            </configuration>
            <dependencies>
                <dependency>
                    <groupId>mysql</groupId>
                    <artifactId>mysql-connector-java</artifactId>
                    <version>8.0.21</version>
                </dependency>
            </dependencies>
        </plugin>
    </plugins>
</build>
```
然后刷新tg-book-dal的Maven依赖，你就能看到mybatis-generator插件了，如下图：


注意，这时生成时会报错，因为我们还需要添加配置文件，接着向下看.

三、生成配置 generatorConfig.xml
在tg-book-dal 的 src/main/resources 下 添加配置文件： generatorConfig.xml ，用于配置生成风格，数据库连接、生成代码包路径、生成哪些表等等，各配置参数详解参考注释：
注意：==我增加了几个实用的可选配置，像生成时覆盖原文件、去除注释、自增主键列、columnOverride ==
```
<!DOCTYPE generatorConfiguration PUBLIC
        "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>
    <!-- MyBatis3Simple风格 -->
    <context id="simple" targetRuntime="MyBatis3Simple">

        <property name="javaFileEncoding" value="UTF-8"/>
        <!--生成mapper.xml时覆盖原文件-->
        <plugin type="org.mybatis.generator.plugins.UnmergeableXmlMappersPlugin"/>

        <commentGenerator>
            <!-- 是否去除自动生成的注释 true：是 ： false:否。 自动生成注释太啰嗦，可以编码扩展CommentGenerator -->
            <property name="suppressAllComments" value="true"/>
        </commentGenerator>

        <!-- 数据库连接 -->
        <jdbcConnection driverClass="com.mysql.cj.jdbc.Driver"
                        connectionURL="jdbc:mysql://localhost:3306/db_book?useUnicode=true&amp;characterEncoding=utf8&amp;tinyInt1isBit=true&amp;serverTimezone=UTC"
                        userId="root" password="root">
            <!-- 设置为true就只读取db_book下的表, 否则会优先读取到mysql的user表  -->
            <property name="nullCatalogMeansCurrent" value="true"/>
        </jdbcConnection>

        <!-- 生成PO的包名和位置 -->
        <javaModelGenerator targetPackage="org.tg.book.dal.po.mbg" targetProject="src/main/java"/>

        <!-- 生成XML映射文件的包名和位置 -->
        <sqlMapGenerator targetPackage="org.tg.book.dal.mapper.mbg" targetProject="src/main/resources"/>

        <!-- 生成Mapper接口的包名和位置 -->
        <javaClientGenerator type="XMLMAPPER" targetPackage="org.tg.book.dal.mapper.mbg" targetProject="src/main/java"/>

        <!-- 要生成对应表配置 -->
        <table tableName="user" domainObjectName="User" >
            <!-- 自增主键列 -->
            <generatedKey column="id" sqlStatement="MYSQL" identity="true"/>
            <!-- tinyint映射为Integer -->
            <columnOverride column="role" javaType="Integer" jdbcType="TINYINT"/>
        </table>
    </context>
</generatorConfiguration>
```

MyBatis3Simple风格
上面配置的是MyBatis3Simple风格，只配置了一个表：user，双击插件的mybatis-generator:generate以后，生成的代码如下：


MyBatis3Simple风格生成的代码比较精简，看一看UserMapper的接口方法，只有基本的增删改查方法：
```
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);
}
```
MyBatis3风格
我们再看看MyBatis3风格，只需要将 targetRuntime=“MyBatis3Simple” 修改为 targetRuntime=“MyBatis3”，然后再双击插件的mybatis-generator:generate，就可以看到增加了 “by example” 和 “selective” 方法


selective方法：选择性插入或更新，判断PO字段不为空才插入或修改。在只需要插入或修改个别字段值时使用，对应生成的SQL不包括=null的字段，但同样也需要注意，你无法用它将字段设置为null。 ??
by example方法：这个东西有点小强大，可以动态生成各种查询条件，在后面的接口开发实战中我们再使用。但就是生成的代码有点多，没有实现example代码的复用。对于单表复杂查询喜欢直接SQL的，可以禁用生成example，可以通过table节点的如下配置来指定是否启用。
属性

说明

enableSelectByExample

是否启用生成selectByExample

enableDeleteByExample

是否启用生成deleteByExample

enableCountByExample

是否启用生成countByExample

enableUpdateByExample

是否启用生成updateByExample和updateByExampleSelective

MyBatis3DynamicSql风格
只需要将 targetRuntime=“MyBatis3” 修改为 targetRuntime=“MyBatis3DynamicSql”。
生成的代码是全注解风格的，也就不会生成UserMapper.xml和UserExample类，而是新生成了UserDynamicSqlSupport类。

需要增加mybatis-dynamic-sql依赖包：
```
<!--mybatis-dynamic-sql-->
<dependency>
    <groupId>org.mybatis.dynamic-sql</groupId>
    <artifactId>mybatis-dynamic-sql</artifactId>
    <version>1.1.4</version>
</dependency>
```
四、Java代码运行方式
除了使用插件运行方式来生成，我们还可以以Java代码方式运行，添加mybatis-generator-core依赖包，如下：

```
<dependency>
    <groupId>org.mybatis.generator</groupId>
    <artifactId>mybatis-generator-core</artifactId>
    <version>1.3.7</version>
</dependency>
```
添加main方法，如下：

```package org.tg.book.dal;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MybatisGeneratorRunner {
    public static void main(String[] args) throws XMLParserException, IOException, InvalidConfigurationException, SQLException, InterruptedException {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        // 使用xml配置文件的方式
        File configFile = new File(MybatisGeneratorRunner.class.getClassLoader().getResource("generatorConfig.xml").getPath());
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        // 使用纯Java代码配置的方式, 相当于把 generatorConfig.xml配置的都用java代码配置到config中
        // config = new Configuration();

        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        System.out.println(warnings);
    }
}
```
注意：我执行后生成到父项目下了??，需要修改generatorConfig.xml 将targetProject加上项目名称，如下：

```
<!-- 生成PO的包名和位置 -->
<javaModelGenerator targetPackage="org.tg.book.dal.po.mbg" targetProject="tg-book-dal/src/main/java"/>

<!-- 生成XML映射文件的包名和位置 -->
<sqlMapGenerator targetPackage="org.tg.book.dal.mapper.mbg" targetProject="tg-book-dal/src/main/resources"/>

<!-- 生成Mapper接口的包名和位置 -->
<javaClientGenerator type="XMLMAPPER" targetPackage="org.tg.book.dal.mapper.mbg" targetProject="tg-book-dal/src/main/java"/>
```
targetProject加上tg-book-dal以后用插件生成，又提示目录找不到，汗(⊙﹏⊙)b
```
[WARNING] The specified target project directory tg-book-dal/src/main/resources does not exist
[WARNING] The specified target project directory tg-book-dal/src/main/java does not exist
[WARNING] The specified target project directory tg-book-dal/src/main/java does not exist
[WARNING] The specified target project directory tg-book-dal/src/main/java does not exist
```
五、MGB生成全部表
一共8张表，主要注意点：

generatedKey：配置自增id
columnOverride：指定tinyint列映射到Java类型是Integer还是Boolean
除了user的另外7张表配置：
```
<table tableName="book" domainObjectName="Book" >
    <generatedKey column="id" sqlStatement="MYSQL" identity="true"/>
    <columnOverride column="status" javaType="Integer" jdbcType="TINYINT"/>
    <columnOverride column="is_recommend" javaType="Boolean" jdbcType="TINYINT"/>
</table>
<table tableName="book_borrowing" domainObjectName="BookBorrowing" >
    <generatedKey column="id" sqlStatement="MYSQL" identity="true"/>
    <columnOverride column="status" javaType="Integer" jdbcType="TINYINT"/>
</table>
<table tableName="book_comment" domainObjectName="BookComment" >
    <generatedKey column="id" sqlStatement="MYSQL" identity="true"/>
    <columnOverride column="score" javaType="Integer" jdbcType="TINYINT"/>
</table>
<table tableName="book_favorite" domainObjectName="BookFavorite" >
    <generatedKey column="id" sqlStatement="MYSQL" identity="true"/>
    <columnOverride column="is_cancelled" javaType="Boolean" jdbcType="TINYINT"/>
</table>
<table tableName="notice" domainObjectName="Notice" >
    <generatedKey column="id" sqlStatement="MYSQL" identity="true"/>
    <columnOverride column="is_deleted" javaType="Boolean" jdbcType="TINYINT"/>
</table>
<table tableName="qualification" domainObjectName="Qualification" >
    <generatedKey column="id" sqlStatement="MYSQL" identity="true"/>
    <columnOverride column="status" javaType="Integer" jdbcType="TINYINT"/>
</table>
<table tableName="student" domainObjectName="Student" >
    <generatedKey column="id" sqlStatement="MYSQL" identity="true"/>
    <columnOverride column="is_approved" javaType="Boolean" jdbcType="TINYINT"/>
    <columnOverride column="is_frozen" javaType="Boolean" jdbcType="TINYINT"/>
</table>
```
六、增加Ext包
mbg包的代码我们不做任何修改，为了便于区分，我们将自定义的PO和Mapper单独放到ext包下，命名以Ext结尾。

原来的UserMapper修改为UserMapperExt
删除原来的BookMapper和BookMapperImpl，用MBG生成的BookMapper替代，原来引用的关联代码都修改一下
删除原来的PO对象：User和Book，用MBG生成的User和Book替代，原来引用的关联代码都修改一下
AuthServiceImpl、BookServiceImpl、UserServiceImpl都有修改的点
最终结构dal的 mapper和po包，如下：

最终的mapper XML映射文件，如下：


七、Git提交
养成好习惯，一步一提交！


总结
本文主要介绍了Mybatis Generator (MBG)生成代码的2种生成方式(当然还支持其它方式)：

Maven插件方式
Java代码方式
以及generatorConfig.xml配置的3种生成风格：

MyBatis3Simple风格
MyBatis3风格
MyBatis3DynamicSql风格
