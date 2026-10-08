package br.com.igormanel.clinica.profile.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.igormanel.clinica.profile.dto.ProfileUpdateRequest;
import br.com.igormanel.clinica.profile.dto.ProfileUpdateRequest.AddressRequest;
import br.com.igormanel.clinica.profile.dto.PublicProfileResponse;
import br.com.igormanel.clinica.profile.entity.Address;
import br.com.igormanel.clinica.profile.entity.ProfessionalProfile;
import br.com.igormanel.clinica.profile.repository.ProfessionalProfileRepository;
import br.com.igormanel.clinica.shared.exception.ResourceNotFoundException;

@Service
public class ProfessionalProfileService {

	private final ProfessionalProfileRepository repository;

	public ProfessionalProfileService(ProfessionalProfileRepository repository) {
		this.repository = repository;
	}

	@Transactional(readOnly = true)
	public PublicProfileResponse getPublicProfile() {
		return PublicProfileResponse.from(findProfile());
	}

	@Transactional
	public PublicProfileResponse updateProfile(ProfileUpdateRequest request) {
		ProfessionalProfile profile = findProfile();
		profile.update(
				clean(request.name()),
				clean(request.professionalRegistry()),
				clean(request.education()),
				clean(request.approach()),
				clean(request.onlineServiceInfo()),
				clean(request.whatsappNumber()),
				clean(request.email()),
				toAddress(request.address()));
		return PublicProfileResponse.from(repository.saveAndFlush(profile));
	}

	private ProfessionalProfile findProfile() {
		return repository.findById(ProfessionalProfile.SINGLETON_ID)
				.orElseThrow(() -> new ResourceNotFoundException("Perfil profissional não cadastrado."));
	}

	private static Address toAddress(AddressRequest request) {
		if (request == null) {
			return null;
		}
		return new Address(
				clean(request.street()),
				clean(request.complement()),
				clean(request.district()),
				clean(request.city()),
				clean(request.state()),
				clean(request.postalCode()),
				clean(request.accessInfo()));
	}

	/** Remove espaços nas pontas e trata texto em branco como "não informado". */
	private static String clean(String value) {
		if (value == null || value.isBlank()) {
			return null;
		}
		return value.strip();
	}

}
