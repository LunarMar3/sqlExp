package org.simple.dbms.Config;

import cn.hutool.core.io.resource.ClassPathResource;
import com.baomidou.mybatisplus.annotation.DbType;

import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;

@Configuration
public class MybatisConfig {
    @Autowired
    private DataSource dataSource;

    @Bean("sqlSessionFactory")
    public MybatisSqlSessionFactoryBean bean() {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        bean.setPlugins(interceptor);
        return bean;
    }

}
