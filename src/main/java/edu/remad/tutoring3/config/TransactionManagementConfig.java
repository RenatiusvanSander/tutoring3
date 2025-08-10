package edu.remad.tutoring3.config;

import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mysql.cj.jdbc.MysqlDataSource;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
public class TransactionManagementConfig {

	@Bean
	EntityManagerFactoryBuilder entityManagerFactoryBuilder(JpaProperties jpaProperties) {
		return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), jpaProperties.getProperties(), null);
	}

	@Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory(MysqlDataSource dataSource, EntityManagerFactoryBuilder builder) {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);

		return builder.dataSource(dataSource).packages("edu.remad.tutoring3.model").persistenceUnit("developmentTutoring3")
				.build();
	}

	@Bean
	PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerfactory) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerfactory);

		return txManager;
	}

}
