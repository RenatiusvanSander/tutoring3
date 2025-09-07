package edu.remad.tutoring3.datasources;

import java.sql.SQLException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

import edu.remad.tutoring3.systemenvironment.SystemEnvironment;

/**
 * Configures connections to MySQL database like {@link MysqlDataSource},
 * {@link DriverManagerDataSource} and {@link MysqlConnectionPoolDataSource}
 * 
 * @author edu.remad
 * @since 2025
 */
@Configuration
public class MySQLDataSourcesConfig {

	@Bean(name = "dataSource")
	MysqlDataSource dataSource(SystemEnvironment sysEnv) throws SQLException {
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setDatabaseName("developmenttutoring003");
		dataSource.setServerName("localhost");
		dataSource.setPort(3306);
		dataSource.setUser(sysEnv.getAppDevelopmentDataSourcesMysqlUsername());
		dataSource.setPassword(sysEnv.getAppDevelopmentDataSourcesMysqlPassword());
		dataSource.setServerTimezone("UTC");
		dataSource.setURL(sysEnv.getAppDataDevelopmentSourcesMysqlUrl());

		return dataSource;
	}

	@Bean
	DriverManagerDataSource mysqlDriverManagerDataSource(SystemEnvironment sysEnv) {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl(sysEnv.getAppDataDevelopmentSourcesMysqlUrl() + "?serverTimezone=UTC");
		dataSource.setUsername(sysEnv.getAppDevelopmentDataSourcesMysqlUsername());
		dataSource.setPassword(sysEnv.getAppDevelopmentDataSourcesMysqlPassword());

		return dataSource;
	}

	@Bean
	MysqlConnectionPoolDataSource mysqlConnectionPoolDataSource(SystemEnvironment sysEnv) {
		MysqlConnectionPoolDataSource mysqlConnectionPoolDataSource = new MysqlConnectionPoolDataSource();
		mysqlConnectionPoolDataSource.setUrl(sysEnv.getAppDataDevelopmentSourcesMysqlUrl());
		mysqlConnectionPoolDataSource.setPassword(sysEnv.getAppDevelopmentDataSourcesMysqlPassword());
		mysqlConnectionPoolDataSource.setUser(sysEnv.getAppDevelopmentDataSourcesMysqlUsername());

		return mysqlConnectionPoolDataSource;
	}
}
