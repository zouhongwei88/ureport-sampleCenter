package com.bstek.samp;


import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.bstek.ureport.console.UReportServlet;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

@SpringBootApplication
@EnableCaching
@ImportResource({"classpath:ureport-console-context.xml"})
public class StartApplication {
	
	private DataSource dataSource;
	
	public static void main(String[] args) {
		SpringApplication.run(StartApplication.class, args);
	}
	
	@Bean
    public ServletRegistrationBean UReportServletRegistration() { 
        ServletRegistrationBean registration = new ServletRegistrationBean(new UReportServlet());  
        registration.addUrlMappings("/ureport/*");
        return registration;
    }
	
	@Bean 
 	public LocalSessionFactoryBean localSessionFactoryBean() {
 		LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
 		dataSource = DataSourceBuilder
			.create(StartApplication.class.getClassLoader())
			.driverClassName("com.mysql.jdbc.Driver")
			.url("jdbc:mysql://localhost:3306/bdf3uflo2ureport2")
			.username("root")
			.password("root")
			.build();
 		localSessionFactoryBean.setDataSource(dataSource);
 		localSessionFactoryBean.setPackagesToScan("com.bstek.uflo.model*");
 		Properties properties = new Properties();
 		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
 		properties.put("hibernate.hbm2ddl.auto", "update");
 		localSessionFactoryBean.setHibernateProperties(properties);
 		return localSessionFactoryBean;
 	}
 	
 	@Bean
 	public PlatformTransactionManager platformTransactionManager(SessionFactory sessionFactory) {
 		HibernateTransactionManager tm = new HibernateTransactionManager();
 		tm.setSessionFactory(sessionFactory);
 		return tm;
 	}
	


}
