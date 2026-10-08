package br.com.igormanel.clinica;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Valida a aplicação contra o PostgreSQL local (banco "clinica"), aplicando as
 * migrations reais. Executa somente quando DB_USERNAME e DB_PASSWORD estão
 * definidas; apenas lê dados, não altera o perfil existente.
 */
@SpringBootTest
@AutoConfigureMockMvc
@EnabledIfEnvironmentVariable(named = "DB_USERNAME", matches = ".+")
@EnabledIfEnvironmentVariable(named = "DB_PASSWORD", matches = ".+")
class PostgresIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Test
	void readsProfileFromPostgres() throws Exception {
		mvc.perform(get("/api/public/profile"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").isNotEmpty());
	}

}
