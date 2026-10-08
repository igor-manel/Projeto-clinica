package br.com.igormanel.clinica.profile.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PublicProfileResponseTest {

	@Test
	void formatsBrazilianMobileAndLandlineNumbers() {
		assertThat(PublicProfileResponse.formatPhone("5500900000000")).isEqualTo("+55 (00) 90000-0000");
		assertThat(PublicProfileResponse.formatPhone("550000000000")).isEqualTo("+55 (00) 0000-0000");
	}

	@Test
	void keepsOtherNumbersWithInternationalPrefix() {
		assertThat(PublicProfileResponse.formatPhone("1000000000")).isEqualTo("+1000000000");
	}

	@Test
	void formatsPostalCode() {
		assertThat(PublicProfileResponse.formatPostalCode("00000000")).isEqualTo("00000-000");
		assertThat(PublicProfileResponse.formatPostalCode(null)).isNull();
	}

}
