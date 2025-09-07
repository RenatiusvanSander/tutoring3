package edu.remad.tutoring3.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mysql.cj.jdbc.MysqlDataSource;

import jakarta.persistence.EntityManagerFactory;

/**
 * Configures entity manager factory, PlatformTransactionManager and
 * LocalContainerEntityManagerFactoryBean for Transaction Management
 * 
 * @author edu.remad
 * @since 2025
 */
@Configuration
@EnableTransactionManagement
public class TransactionManagementConfig {

	@Bean
	EntityManagerFactoryBuilder entityManagerFactoryBuilder(JpaProperties jpaProperties) {
		return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), jpaProperties.getProperties(), null);
	}

	@Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory(MysqlDataSource dataSource,
			EntityManagerFactoryBuilder builder) {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);

		LocalContainerEntityManagerFactoryBean entityManagerFactory = builder.dataSource(dataSource)
				.packages("edu.remad.tutoring3.model", "edu.remad.tutoring3.persistence.models")
				.persistenceUnit("developmentTutoring3").build();
		entityManagerFactory.setJpaProperties(additionalProperties());

		return entityManagerFactory;
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
	PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerfactory) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerfactory);

		return txManager;
	}

}
