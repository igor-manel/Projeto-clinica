package br.com.igormanel.clinica.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @param apiToken token exigido em /api/admin/** (variável ADMIN_API_TOKEN).
 *                 Vazio ou curto demais desabilita a API administrativa.
 */
@ConfigurationProperties(prefix = "clinica.admin")
public record AdminProperties(String apiToken) {

	public static final int MIN_TOKEN_LENGTH = 32;

	public boolean adminApiEnabled() {
		return apiToken != null && apiToken.strip().length() >= MIN_TOKEN_LENGTH;
	}

}
