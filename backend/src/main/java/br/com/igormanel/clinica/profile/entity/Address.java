package br.com.igormanel.clinica.profile.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Endereço do consultório. Todos os campos são opcionais enquanto a cliente
 * não fornecer as informações reais.
 */
@Embeddable
public class Address {

	@Column(name = "address_street", length = 200)
	private String street;

	@Column(name = "address_complement", length = 120)
	private String complement;

	@Column(name = "address_district", length = 120)
	private String district;

	@Column(name = "address_city", length = 120)
	private String city;

	@Column(name = "address_state", length = 2)
	private String state;

	@Column(name = "address_postal_code", length = 8)
	private String postalCode;

	@Column(name = "address_access_info", length = 500)
	private String accessInfo;

	protected Address() {
	}

	public Address(String street, String complement, String district, String city, String state,
			String postalCode, String accessInfo) {
		this.street = street;
		this.complement = complement;
		this.district = district;
		this.city = city;
		this.state = state;
		this.postalCode = postalCode;
		this.accessInfo = accessInfo;
	}

	public String getStreet() {
		return street;
	}

	public String getComplement() {
		return complement;
	}

	public String getDistrict() {
		return district;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getAccessInfo() {
		return accessInfo;
	}

}
