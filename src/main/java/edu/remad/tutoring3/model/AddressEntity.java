package edu.remad.tutoring3.model;

import static jakarta.persistence.GenerationType.IDENTITY;

import edu.remad.tutoring3.model.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * address data of an {@link UserEntity}
 * 
 * @author edu.remad
 * @since 2025
 */
@Entity
@Table(name = "addressentity", uniqueConstraints = { @UniqueConstraint(columnNames = {"address_id"})})
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Builder
public class AddressEntity {

	/**
	 * the primary key for an address
	 */
	@Id @GeneratedValue(strategy = IDENTITY)
	@Column(name="address_id", unique = true)
	private long id;

	/**
	 * the street
	 */
	@NotNull
	@Column(name = "address_street")
	private String addressStreet;

	/**
	 * house number
	 */
	@NotNull
	@Column(name = "address_house_number")
	private String addressHouseNo;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "user")
//	private UserEntity user;

	/**
	 * the zip code
	 */
	@NotNull
	@Column(name = "address_zip_code")
	private int addressZipCode;
	
	/**
	 * the place of living
	 */
	@NotNull
	@Column(name = "place")
	private String place;

	@Override
	public String toString() {
		return "AddressEntity [id=" + id + ", addressStreet=" + addressStreet + ", addressHouseNo=" + addressHouseNo
				+ ", addressZipCode=" + addressZipCode + ", place=" + place + "]";
	}
	
}

