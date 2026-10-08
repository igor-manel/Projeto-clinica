package br.com.igormanel.clinica;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.igormanel.clinica.profile.entity.ProfessionalProfile;
import br.com.igormanel.clinica.profile.repository.ProfessionalProfileRepository;

@SpringBootTest
@ActiveProfiles("test")
class ClinicaBackendApplicationTests {

	@Autowired
	private ProfessionalProfileRepository repository;

	@Test
	void contextLoadsAndMigrationCreatesProfile() {
		assertThat(repository.findById(ProfessionalProfile.SINGLETON_ID))
				.hasValueSatisfying(profile -> assertThat(profile.getDisplayName()).isEqualTo("Crislane Soares"));
	}

}
