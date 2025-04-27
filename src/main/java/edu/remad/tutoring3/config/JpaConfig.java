package edu.remad.tutoring3.config;

import java.util.Properties;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import edu.remad.tutoring3.systemenvironment.SystemEnvironment;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan({ "edu.remad.tutoring3.persistence.models" })
@EnableJpaRepositories(basePackages = "edu.remad.tutoring3.persistence.repositories")
public class JpaConfig {

	@Bean
	DataSource dataSource(SystemEnvironment systemEnvironment) {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

		if (systemEnvironment.getAppStage().equals("dev")) {
			dataSource.setUrl(systemEnvironment.getAppDataDevelopmentSourcesMysqlUrl());
			dataSource.setUsername(systemEnvironment.getAppDevelopmentDataSourcesMysqlUsername());
			dataSource.setPassword(systemEnvironment.getAppDevelopmentDataSourcesMysqlPassword());
		} else {
			dataSource.setUrl(systemEnvironment.getAppDataSourcesMysqlUrl());
			dataSource.setUsername(systemEnvironment.getAppDataSourcesMysqlUsername());
			dataSource.setPassword(systemEnvironment.getAppDataSourcesMysqlPassword());
		}

		return dataSource;
	}

	@Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory(SystemEnvironment systemEnvironment) {
		final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource(systemEnvironment));
		entityManagerFactoryBean.setPackagesToScan(new String[] { "edu.remad.tutoring3.persistence.models" });

		final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
		entityManagerFactoryBean.setJpaProperties(additionalProperties());

		return entityManagerFactoryBean;
	}

	private final Properties additionalProperties() {
		final Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", "false");
		hibernateProperties.setProperty("hibernate.cache.use_query_cache", "false");
		hibernateProperties.setProperty("hibernate.show_sql", "true");
		hibernateProperties.setProperty("hibernate.format_sql", "true");

		return hibernateProperties;
	}

	@Bean
	PlatformTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory,
			DataSource dataSource) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);

		return transactionManager;
	}

	@Bean
	PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
}
