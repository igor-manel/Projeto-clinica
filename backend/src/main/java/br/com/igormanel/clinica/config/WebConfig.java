package br.com.igormanel.clinica.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private static final Logger log = LoggerFactory.getLogger(WebConfig.class);

	private final AdminProperties adminProperties;

	public WebConfig(AdminProperties adminProperties) {
		this.adminProperties = adminProperties;
		if (!adminProperties.adminApiEnabled()) {
			log.info("API administrativa desabilitada: defina ADMIN_API_TOKEN com pelo menos {} caracteres para habilitá-la.",
					AdminProperties.MIN_TOKEN_LENGTH);
		}
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AdminTokenInterceptor(adminProperties))
				.addPathPatterns("/api/admin/**");
	}

}
