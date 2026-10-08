package br.com.igormanel.clinica.profile.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.igormanel.clinica.profile.dto.PublicProfileResponse;
import br.com.igormanel.clinica.profile.service.ProfessionalProfileService;

@RestController
@RequestMapping("/api/public/profile")
public class PublicProfileController {

	private final ProfessionalProfileService service;

	public PublicProfileController(ProfessionalProfileService service) {
		this.service = service;
	}

	@GetMapping
	public PublicProfileResponse getProfile() {
		return service.getPublicProfile();
	}

}
