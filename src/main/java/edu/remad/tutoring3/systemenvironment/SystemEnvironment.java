package edu.remad.tutoring3.systemenvironment;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Reads system environment and offers them to app environment
 * 
 * @author edu.remad
 * @since 2025
 */
public class SystemEnvironment {

	private final Map<String, String> properties;

	/**
	 * Constructor
	 */
	public SystemEnvironment() {
		this.properties = new HashMap<>();
	}

	/**
	 * Constructor
	 * 
	 * @param properties a map string keys and values are objects
	 */
	public SystemEnvironment(final Map<String, String> properties) {
		this.properties = properties;
	}

	/**
	 * @return App Admin username
	 */
	public String getAppAdmin() {
		return properties.get("TUTOR_ADMIN");
	}

	/**
	 * @return App Admin Password
	 */
	public String getAppAdminPassword() {
		return properties.get("TUTOR_ADMIN_PASSWORD");
	}

	/**
	 * @return App username
	 */
	public String getAppUser() {
		return properties.get("TUTOR_USER");
	}

	/**
	 * @return tutoring app stage
	 */
	public String getAppStage() {
		return properties.get("TUTOR_STAGE");
	}

	/**
	 * @return app user's password
	 */
	public String getAppUserPassword() {
		return properties.get("TUTOR_USER_PASSWORD");
	}

	/**
	 * @return smtp password
	 */
	public String getSmtpPassword() {
		return properties.get("SMTP_PASSWORD");
	}

	/**
	 * @return smtp user
	 */
	public String getSmtpUsername() {
		return properties.get("SMTP_USER");
	}

	/**
	 * @return app data sources mysql url
	 */
	public String getAppDataSourcesMysqlUrl() {
		return properties.get("TUTORING3_DATA_SOURCES_MYSQL_URL");
	}

	/**
	 * @return app data sources mysql username
	 */
	public String getAppDataSourcesMysqlUsername() {
		return properties.get("TUTORING3_DATA_SOURCES_MYSQL_USERNAME");
	}

	/**
	 * @return app data sources mysql password
	 */
	public String getAppDataSourcesMysqlPassword() {
		return properties.get("TUTORING3_DATA_SOURCES_MYSQL_PASSWORD");
	}

	/**
	 * @return app data development source mysql url
	 */
	public String getAppDataDevelopmentSourcesMysqlUrl() {
		return properties.get("TUTORING3_DEVELOPMENT_DATA_SOURCES_MYSQL_URL");
	}

	/**
	 * @return app development data sources mysql username
	 */
	public String getAppDevelopmentDataSourcesMysqlUsername() {
		return properties.get("TUTORING3_DEVELOPMENT_DATA_SOURCES_MYSQL_USERNAME");
	}

	/**
	 * @return app development data sources mysql password
	 */
	public String getAppDevelopmentDataSourcesMysqlPassword() {
		return properties.get("TUTORING3_DEVELOPMENT_DATA_SOURCES_MYSQL_PASSWORD");
	}

	/**
	 * @return map of properties
	 */
	public Map<String, String> getProperties() {
		return new HashMap<String, String>(properties);
	}

	/**
	 * Prints system environment properties
	 */
	public void printSystemEnvironments() {
		System.out.println(
				"#######################################################################################################");
		System.out.println(
				"#                                         System Environment Begin                                    #");
		System.out.println(
				"#######################################################################################################");
		for (Entry<String, String> systemProperty : properties.entrySet()) {
			System.out.println("Property: " + systemProperty.getKey() + " Value: " + systemProperty.getValue());
		}
		System.out.println(
				"#######################################################################################################");
		System.out.println(
				"#                                         System Environment End                                      #");
		System.out.println(
				"#######################################################################################################");
	}
}
