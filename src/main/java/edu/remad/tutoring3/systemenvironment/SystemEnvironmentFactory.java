package edu.remad.tutoring3.systemenvironment;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.naming.OperationNotSupportedException;

public class SystemEnvironmentFactory {

	private static SystemEnvironment SYSTEM_ENVIRONMENT;
	private static String info = "System environment properties for this app";

	private SystemEnvironmentFactory() throws OperationNotSupportedException {
		throw new OperationNotSupportedException("You shall not craete such instance!");
	}

	public static SystemEnvironment getInstance() {
		if (SYSTEM_ENVIRONMENT == null) {
			SYSTEM_ENVIRONMENT = createSystemEnvironment();
		}

		return SYSTEM_ENVIRONMENT;
	}

	private static SystemEnvironment createSystemEnvironment() {
		Map<String, String> systemVaries = System.getenv();
		SystemEnvironment systemEnvironment = createAndPopulateProperties(systemVaries);

		return systemEnvironment;
	}

	private static SystemEnvironment createAndPopulateProperties(Map<String, String> systemVaries) {
		SystemEnvironment systemEnvironment = new SystemEnvironment();

		if (systemVaries != null && !systemVaries.isEmpty()) {
			Map<String, String> properties = new HashMap<>();

			String[] configProperties = { "TUTOR_ADMIN", "TUTOR_ADMIN_PASSWORD", "TUTOR_USER", "TUTOR_STAGE",
					"TUTOR_USER_PASSWORD", "SMTP_PASSWORD", "SMTP_USER", "TUTORING3_DATA_SOURCES_MYSQL_URL",
					"TUTORING3_DATA_SOURCES_MYSQL_USERNAME", "TUTORING3_DATA_SOURCES_MYSQL_PASSWORD",
					"TUTORING3_DEVELOPMENT_DATA_SOURCES_MYSQL_URL", "TUTORING3_DEVELOPMENT_DATA_SOURCES_MYSQL_USERNAME",
					"TUTORING3_DEVELOPMENT_DATA_SOURCES_MYSQL_PASSWORD" };
			for (String configProperty : configProperties) {
				properties.put(configProperty, systemVaries.get(configProperty));
			}

			systemEnvironment = new SystemEnvironment(properties);
		}

		return systemEnvironment;
	}

	public static String getInfo() {
		return info;
	}

}
