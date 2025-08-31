package edu.remad.tutoring3.profiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

/**
 * Profiles' manager
 * 
 * @author edu.remad
 * @since 2025
 */
public class ProfileManager {

	@Value("${spring.profiles.active:}")
	private String activeProfiles;

	/**
	 * @return list of active profiles
	 */
	public List<String> getActivePfoileList() {
		ArrayList<String> profiles = new ArrayList<>();

		for (String profileName : Arrays.asList(activeProfiles.split(","))) {
			profiles.add(profileName);
		}

		return profiles;
	}

	/**
	 * @return String encoded active profile name
	 */
	public String getActiveProfile() {
		return activeProfiles.split(",")[0];
	}
}
