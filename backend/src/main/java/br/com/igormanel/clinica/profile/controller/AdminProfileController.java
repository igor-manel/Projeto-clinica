package br.com.igormanel.clinica.profile.controller;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.igormanel.clinica.profile.dto.ProfileUpdateRequest;
import br.com.igormanel.clinica.profile.dto.PublicProfileResponse;
import br.com.igormanel.clinica.profile.service.ProfessionalProfileService;
import jakarta.validation.Valid;

/**
 * Atualização das informações do perfil quando a cliente fornecer os dados
 * reais. Protegido por token (ver {@code AdminTokenInterceptor}).
 */
@RestController
@RequestMapping("/api/admin/profile")
public class AdminProfileController {

	private final ProfessionalProfileService service;

	public AdminProfileController(ProfessionalProfileService service) {
		this.service = service;
	}

	@PutMapping
	public PublicProfileResponse updateProfile(@Valid @RequestBody ProfileUpdateRequest request) {
		return service.updateProfile(request);
	}

}
