package br.com.igormanel.clinica.profile.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

/** Sem ADMIN_API_TOKEN configurado, nenhuma requisição administrativa é aceita. */
@SpringBootTest(properties = "clinica.admin.api-token=")
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AdminApiDisabledTest {

	@Autowired
	private MockMvc mvc;

	@Test
	void rejectsEveryRequestWhenTokenIsNotConfigured() throws Exception {
		mvc.perform(put("/api/admin/profile")
				.header("Authorization", "Bearer ")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\": \"Crislane Soares\"}"))
				.andExpect(status().isUnauthorized());
	}

}
