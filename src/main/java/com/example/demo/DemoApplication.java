package com.example.demo;

import com.alibaba.druid.pool.DruidDataSource;
import com.example.demo.config.DruidConfigBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@SpringBootApplication
public class DemoApplication {

	@Autowired
	private Environment env;

	@Autowired
	private DruidConfigBean druidConfigBean;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUrl(env.getProperty("spring.datasource.url"));
		dataSource.setUsername(env.getProperty("spring.datasource.username"));//用户名
		dataSource.setPassword(env.getProperty("spring.datasource.password"));//密码
		dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
		dataSource.setInitialSize(druidConfigBean.getInitialSize());//初始化时建立物理连接的个数
		dataSource.setMaxActive(druidConfigBean.getMaxActive());//最大连接池数量
		dataSource.setMinIdle(druidConfigBean.getMinIdle());//最小连接池数量
		dataSource.setMaxWait(druidConfigBean.getMaxWait());//获取连接时最大等待时间，单位毫秒。
		dataSource.setValidationQuery(druidConfigBean.getValidationQuery());//用来检测连接是否有效的sql
		dataSource.setTestOnBorrow(druidConfigBean.getTestOnBorrow());//申请连接时执行validationQuery检测连接是否有效
		dataSource.setTestWhileIdle(druidConfigBean.getTestWhileIdle());//建议配置为true，不影响性能，并且保证安全性。
		dataSource.setPoolPreparedStatements(druidConfigBean.getPoolPreparedStatements());//是否缓存preparedStatement，也就是PSCache
		return dataSource;
	}

}
