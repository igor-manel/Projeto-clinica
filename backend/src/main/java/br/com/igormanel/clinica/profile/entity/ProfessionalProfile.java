package br.com.igormanel.clinica.profile.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

/**
 * Informações profissionais e de contato exibidas na área pública.
 * Existe um único registro (id = 1), criado pela migration inicial.
 * Campos nulos representam informações ainda não fornecidas pela cliente.
 */
@Entity
@Table(name = "professional_profile")
public class ProfessionalProfile {

	public static final int SINGLETON_ID = 1;

	@Id
	private Integer id;

	@Column(name = "display_name", nullable = false, length = 120)
	private String displayName;

	@Column(name = "professional_registry", length = 30)
	private String professionalRegistry;

	@Column(name = "education", length = 500)
	private String education;

	@Column(name = "approach", length = 200)
	private String approach;

	@Column(name = "online_service_info", length = 500)
	private String onlineServiceInfo;

	@Column(name = "whatsapp_number", length = 15)
	private String whatsappNumber;

	@Column(name = "email", length = 254)
	private String email;

	@Embedded
	private Address address;

	@Column(name = "updated_at", nullable = false)
	private Instant updatedAt;

	protected ProfessionalProfile() {
	}

	public ProfessionalProfile(String displayName) {
		this.id = SINGLETON_ID;
		this.displayName = displayName;
	}

	public void update(String displayName, String professionalRegistry, String education, String approach,
			String onlineServiceInfo, String whatsappNumber, String email, Address address) {
		this.displayName = displayName;
		this.professionalRegistry = professionalRegistry;
		this.education = education;
		this.approach = approach;
		this.onlineServiceInfo = onlineServiceInfo;
		this.whatsappNumber = whatsappNumber;
		this.email = email;
		this.address = address;
	}

	@PrePersist
	@PreUpdate
	void touch() {
		this.updatedAt = Instant.now();
	}

	public Integer getId() {
		return id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getProfessionalRegistry() {
		return professionalRegistry;
	}

	public String getEducation() {
		return education;
	}

	public String getApproach() {
		return approach;
	}

	public String getOnlineServiceInfo() {
		return onlineServiceInfo;
	}

	public String getWhatsappNumber() {
		return whatsappNumber;
	}

	public String getEmail() {
		return email;
	}

	/** Pode ser nulo quando nenhum campo do endereço foi informado. */
	public Address getAddress() {
		return address;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

}
