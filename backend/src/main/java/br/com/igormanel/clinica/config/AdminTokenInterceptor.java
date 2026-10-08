package br.com.igormanel.clinica.config;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;

import br.com.igormanel.clinica.shared.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Exige "Authorization: Bearer &lt;ADMIN_API_TOKEN&gt;" nas rotas administrativas.
 * Solução mínima e provisória enquanto não houver requisito de autenticação
 * e painel administrativo definidos com a cliente.
 */
class AdminTokenInterceptor implements HandlerInterceptor {

	private static final String BEARER_PREFIX = "Bearer ";

	private final AdminProperties properties;

	AdminTokenInterceptor(AdminProperties properties) {
		this.properties = properties;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		if (!properties.adminApiEnabled()) {
			throw new UnauthorizedException();
		}

		String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (header == null || !header.startsWith(BEARER_PREFIX)) {
			throw new UnauthorizedException();
		}

		byte[] provided = header.substring(BEARER_PREFIX.length()).strip().getBytes(StandardCharsets.UTF_8);
		byte[] expected = properties.apiToken().strip().getBytes(StandardCharsets.UTF_8);
		// Comparação em tempo constante para não vazar o token por tempo de resposta
		if (!MessageDigest.isEqual(provided, expected)) {
			throw new UnauthorizedException();
		}
		return true;
	}

}
