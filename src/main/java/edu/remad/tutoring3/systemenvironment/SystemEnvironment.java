package edu.remad.tutoring3.systemenvironment;

import java.util.HashMap;
import java.util.Map;

public class SystemEnvironment {

	private final Map<String, String> properties;

	public SystemEnvironment() {
		this.properties = new HashMap<>();
	}

	public SystemEnvironment(final Map<String, String> properties) {
		this.properties = properties;
	}

	public String getAppAdmin() {
		return properties.get("TUTOR_ADMIN");
	}

	public String getAppAdminPassword() {
		return properties.get("TUTOR_ADMIN_PASSWORD");
	}

	public String getAppUser() {
		return properties.get("TUTOR_USER");
	}

	public String getAppStage() {
		return properties.get("TUTOR_STAGE");
	}

	public String getAppUserPassword() {
		return properties.get("TUTOR_USER_PASSWORD");
	}

	public String getSmtpPassword() {
		return properties.get("SMTP_PASSWORD");
	}

	public String getSmtpUsername() {
		return properties.get("SMTP_USER");
	}

	public String getAppDataSourcesMysqlUrl() {
		return properties.get("TUTORING3_DATA_SOURCES_MYSQL_URL");
	}

	public String getAppDataSourcesMysqlUsername() {
		return properties.get("TUTORING3_DATA_SOURCES_MYSQL_USERNAME");
	}

	public String getAppDataSourcesMysqlPassword() {
		return properties.get("TUTORING3_DATA_SOURCES_MYSQL_PASSWORD");
	}

	public String getAppDataDevelopmentSourcesMysqlUrl() {
		return properties.get("TUTORING3_DEVELOPMENT_DATA_SOURCES_MYSQL_URL");
	}

	public String getAppDevelopmentDataSourcesMysqlUsername() {
		return properties.get("TUTORING3_DEVELOPMENT_DATA_SOURCES_MYSQL_USERNAME");
	}

	public String getAppDevelopmentDataSourcesMysqlPassword() {
		return properties.get("TUTORING3_DEVELOPMENT_DATA_SOURCES_MYSQL_PASSWORD");
	}
}