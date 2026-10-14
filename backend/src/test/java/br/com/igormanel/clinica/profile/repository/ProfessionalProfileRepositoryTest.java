package br.com.igormanel.clinica.profile.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import br.com.igormanel.clinica.profile.entity.Address;
import br.com.igormanel.clinica.profile.entity.ProfessionalProfile;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProfessionalProfileRepositoryTest {

	@Autowired
	private ProfessionalProfileRepository repository;

	@Test
	void initialProfileHasFictitiousSeedData() {
		ProfessionalProfile profile = repository.findById(ProfessionalProfile.SINGLETON_ID).orElseThrow();

		assertThat(profile.getDisplayName()).isEqualTo("Helena Duarte");
		assertThat(profile.getProfessionalRegistry()).isEqualTo("CRP 00/12345");
		assertThat(profile.getWhatsappNumber()).isEqualTo("5511900000000");
		assertThat(profile.getEmail()).isEqualTo("contato@helenaduarte.example");
		assertThat(profile.getAddress().getCity()).isEqualTo("São Paulo");
		assertThat(profile.getUpdatedAt()).isNotNull();
	}

	@Test
	void persistsUpdatedFieldsAndRefreshesTimestamp() {
		ProfessionalProfile profile = repository.findById(ProfessionalProfile.SINGLETON_ID).orElseThrow();
		Instant before = profile.getUpdatedAt();

		profile.update("Nome Teste", "00/00000", null, null, null, "5500000000000", "teste@example.com",
				new Address("Rua Exemplo, 0", null, "Bairro", "Cidade", "UF", "00000000", null));
		repository.saveAndFlush(profile);

		ProfessionalProfile reloaded = repository.findById(ProfessionalProfile.SINGLETON_ID).orElseThrow();
		assertThat(reloaded.getProfessionalRegistry()).isEqualTo("00/00000");
		assertThat(reloaded.getAddress().getCity()).isEqualTo("Cidade");
		assertThat(reloaded.getUpdatedAt()).isAfterOrEqualTo(before);
		assertThat(repository.count()).isEqualTo(1);
	}

}
