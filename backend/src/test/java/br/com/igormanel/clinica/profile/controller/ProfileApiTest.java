package br.com.igormanel.clinica.profile.controller;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/** Dados usados aqui são fictícios; nenhum corresponde a informações reais da cliente. */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class ProfileApiTest {

	private static final String TOKEN = "Bearer token-de-teste-com-pelo-menos-32-caracteres";

	private static final String VALID_BODY = """
			{
			  "name": "Crislane Soares",
			  "professionalRegistry": "00/00000",
			  "education": "Formação fictícia de teste",
			  "approach": "Abordagem fictícia de teste",
			  "onlineServiceInfo": "Plataforma fictícia de teste",
			  "whatsappNumber": "5500900000000",
			  "email": "contato@example.com",
			  "address": {
			    "street": "Rua Exemplo, 0",
			    "complement": "  ",
			    "district": "Bairro Exemplo",
			    "city": "Cidade Exemplo",
			    "state": "UF",
			    "postalCode": "00000000",
			    "accessInfo": null
			  }
			}
			""";

	@Autowired
	private MockMvc mvc;

	@Test
	void publicProfileReturnsNameAndPendingFieldsAsNull() throws Exception {
		mvc.perform(get("/api/public/profile"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(header().string("Cache-Control", "no-store"))
				.andExpect(header().string("X-Content-Type-Options", "nosniff"))
				.andExpect(jsonPath("$.name").value("Crislane Soares"))
				.andExpect(jsonPath("$.professionalRegistry").value(nullValue()))
				.andExpect(jsonPath("$.contact.whatsapp").value(nullValue()))
				.andExpect(jsonPath("$.contact.email").value(nullValue()))
				.andExpect(jsonPath("$.location.street").value(nullValue()));
	}

	@Test
	void updateRequiresToken() throws Exception {
		mvc.perform(put("/api/admin/profile").contentType(MediaType.APPLICATION_JSON).content(VALID_BODY))
				.andExpect(status().isUnauthorized())
				.andExpect(header().string("WWW-Authenticate", "Bearer"))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
				.andExpect(jsonPath("$.status").value(401));
	}

	@Test
	void updateRejectsWrongToken() throws Exception {
		mvc.perform(put("/api/admin/profile")
				.header("Authorization", "Bearer token-errado-com-pelo-menos-32-caracteres")
				.contentType(MediaType.APPLICATION_JSON).content(VALID_BODY))
				.andExpect(status().isUnauthorized());
	}

	@Test
	void updatePersistsDataAndPublicProfileReflectsIt() throws Exception {
		mvc.perform(put("/api/admin/profile").header("Authorization", TOKEN)
				.contentType(MediaType.APPLICATION_JSON).content(VALID_BODY))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.professionalRegistry").value("00/00000"));

		mvc.perform(get("/api/public/profile"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.contact.whatsapp.display").value("+55 (00) 90000-0000"))
				.andExpect(jsonPath("$.contact.whatsapp.url").value("https://wa.me/5500900000000"))
				.andExpect(jsonPath("$.contact.email.url").value("mailto:contato@example.com"))
				.andExpect(jsonPath("$.location.complement").value(nullValue()))
				.andExpect(jsonPath("$.location.postalCode").value("00000-000"));
	}

	@Test
	void updateReturnsFieldErrorsForInvalidData() throws Exception {
		String invalid = """
				{
				  "name": " ",
				  "whatsappNumber": "(00) 0000-0000",
				  "email": "nao-e-email",
				  "address": { "state": "uf", "postalCode": "123" }
				}
				""";

		mvc.perform(put("/api/admin/profile").header("Authorization", TOKEN)
				.contentType(MediaType.APPLICATION_JSON).content(invalid))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
				.andExpect(jsonPath("$.title").value("Dados inválidos"))
				.andExpect(jsonPath("$.errors[*].field").value(hasItem("name")))
				.andExpect(jsonPath("$.errors[*].field").value(hasItem("whatsappNumber")))
				.andExpect(jsonPath("$.errors[*].field").value(hasItem("email")))
				.andExpect(jsonPath("$.errors[*].field").value(hasItem("address.state")))
				.andExpect(jsonPath("$.errors[*].field").value(hasItem("address.postalCode")));
	}

	@Test
	void updateRejectsMalformedJsonWithoutLeakingDetails() throws Exception {
		mvc.perform(put("/api/admin/profile").header("Authorization", TOKEN)
				.contentType(MediaType.APPLICATION_JSON).content("{ invalido"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.detail").value("O corpo da requisição está ausente ou não é um JSON válido."));
	}

	@Test
	void unsupportedMethodReturnsProblemDetail() throws Exception {
		mvc.perform(put("/api/public/profile").contentType(MediaType.APPLICATION_JSON).content("{}"))
				.andExpect(status().isMethodNotAllowed())
				.andExpect(jsonPath("$.status").value(405));
	}

	@Test
	void unknownApiRouteReturnsNotFound() throws Exception {
		mvc.perform(get("/api/inexistente"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.detail").value("O recurso solicitado não existe."));
	}

	@Test
	void servesFrontendAndClientImage() throws Exception {
		mvc.perform(get("/index.html"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML));
		mvc.perform(get("/assets/images/crislane.jpg"))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.IMAGE_JPEG));
	}

}
