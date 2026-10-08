package br.com.igormanel.clinica.profile.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Substitui integralmente as informações do perfil. Campos ausentes, nulos ou
 * em branco voltam a ser tratados como "não informados".
 */
public record ProfileUpdateRequest(
		@NotBlank(message = "O nome é obrigatório.")
		@Size(max = 120, message = "O nome deve ter no máximo 120 caracteres.")
		String name,

		@Size(max = 30, message = "O registro profissional deve ter no máximo 30 caracteres.")
		String professionalRegistry,

		@Size(max = 500, message = "A formação deve ter no máximo 500 caracteres.")
		String education,

		@Size(max = 200, message = "A abordagem deve ter no máximo 200 caracteres.")
		String approach,

		@Size(max = 500, message = "As informações do atendimento online devem ter no máximo 500 caracteres.")
		String onlineServiceInfo,

		@Pattern(regexp = "\\s*|\\d{10,15}",
				message = "Informe o WhatsApp somente com dígitos, incluindo DDI e DDD (10 a 15 dígitos).")
		String whatsappNumber,

		@Email(message = "Informe um e-mail válido.")
		@Size(max = 254, message = "O e-mail deve ter no máximo 254 caracteres.")
		String email,

		@Valid
		AddressRequest address) {

	public record AddressRequest(
			@Size(max = 200, message = "O logradouro deve ter no máximo 200 caracteres.")
			String street,

			@Size(max = 120, message = "O complemento deve ter no máximo 120 caracteres.")
			String complement,

			@Size(max = 120, message = "O bairro deve ter no máximo 120 caracteres.")
			String district,

			@Size(max = 120, message = "A cidade deve ter no máximo 120 caracteres.")
			String city,

			@Pattern(regexp = "\\s*|[A-Z]{2}", message = "Informe a UF com duas letras maiúsculas.")
			String state,

			@Pattern(regexp = "\\s*|\\d{8}", message = "Informe o CEP somente com os 8 dígitos.")
			String postalCode,

			@Size(max = 500, message = "As informações de acesso devem ter no máximo 500 caracteres.")
			String accessInfo) {
	}

}
