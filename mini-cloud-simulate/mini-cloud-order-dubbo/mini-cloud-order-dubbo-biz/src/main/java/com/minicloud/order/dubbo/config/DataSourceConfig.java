package com.minicloud.order.dubbo.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @Author：alan.wang
 * @Package：com.minicloud.common.data.mybatis.plus
 * @Project：mini-cloud-gateaway-center
 * @name：DataSourceConfig
 * @Date：2023/4/5 18:36
 * @Filename：DataSourceConfig
 */
@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource dataSource(DatasourceProperties datasourceProperties) throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(datasourceProperties.getUrl());
        druidDataSource.setPassword(datasourceProperties.getPassword());
        druidDataSource.setUsername(datasourceProperties.getUsername());
        return druidDataSource;
    }
}
