package com.minicloud.upms;

import cn.org.atool.generator.FileGenerator;
import cn.org.atool.generator.annotation.Table;
import cn.org.atool.generator.annotation.Tables;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author alan.wang
 */
@RunWith(SpringRunner.class)
public class EntityGenerator {

    // 数据源 url
    static final String url =
            "jdbc:mysql://192.168.1.2:3306/mini_cloud_upms?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true";
    // 数据库用户名
    static final String username = "root";
    // 数据库密码
    static final String password = "root";

    @Test
    public void generate() throws Exception {
        // 引用配置类，build方法允许有多个配置类
        FileGenerator.build(Empty.class);
    }

    @Tables(
            // 设置数据库连接信息
            url = url,
            username = username,
            password = password,
            // 设置entity类生成src目录, 相对于 user.dir
            srcDir = "src/main/generator",
            // 设置entity类的package值
            basePack = "com.minicloud.upms.generator",
            // 设置dao接口和实现的src目录, 相对于 user.dir
            daoDir = "src/main/java",
            // 设置哪些表要生成Entity文件
            tables = {@Table(value = {"upms_user"})})
    static class Empty { // 类名随便取, 只是配置定义的一个载体
    }
}
