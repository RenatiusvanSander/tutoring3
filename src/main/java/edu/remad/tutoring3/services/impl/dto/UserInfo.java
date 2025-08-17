package edu.remad.tutoring3.services.impl.dto;

import java.util.Objects;

public class UserInfo {

	private String sub;
	private String preferred_username;
	private String DOB;
	private String organization;

	public String getSub() {
		return sub;
	}

	public String getPreferred_username() {
		return preferred_username;
	}

	public String getDOB() {
		return DOB;
	}

	public String getOrganization() {
		return organization;
	}

	@Override
	public int hashCode() {
		return Objects.hash(DOB, organization, preferred_username, sub);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserInfo other = (UserInfo) obj;
		return Objects.equals(DOB, other.DOB) && Objects.equals(organization, other.organization)
				&& Objects.equals(preferred_username, other.preferred_username) && Objects.equals(sub, other.sub);
	}

	@Override
	public String toString() {
		return "UserInfo [sub=" + sub + ", preferred_username=" + preferred_username + ", DOB=" + DOB
				+ ", organization=" + organization + "]";
	}

}
