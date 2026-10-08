package br.com.igormanel.clinica.config;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/** Cabeçalhos básicos de segurança para a página pública e para a API. */
@Component
public class SecurityHeadersFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		response.setHeader("X-Content-Type-Options", "nosniff");
		response.setHeader("X-Frame-Options", "DENY");
		response.setHeader("Referrer-Policy", "strict-origin-when-cross-origin");
		if (request.getRequestURI().startsWith("/api/")) {
			response.setHeader("Cache-Control", "no-store");
		}
		chain.doFilter(request, response);
	}

}
