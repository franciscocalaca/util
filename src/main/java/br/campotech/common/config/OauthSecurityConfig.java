package br.campotech.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class OauthSecurityConfig {

	@Value("${campotech.security.urlCheckToken}")
	private String url;

	@Value("${campotech.security.clientId}")
	private String clientId;

	@Value("${campotech.security.clientSecret}")
	private String clientSecret;

    private final SecurityPathProvider pathProvider;

    public OauthSecurityConfig(SecurityPathProvider pathProvider) {
        this.pathProvider = pathProvider;
    }	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors(cors -> {
					cors.configurationSource(corsConfigurationSource());
				}).csrf(csrf -> csrf.disable())  // Desabilita CSRF
				.authorizeHttpRequests(
						authorize -> authorize
						.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
		                .requestMatchers(pathProvider.getPublicAnyPaths()).permitAll()
		                .requestMatchers(pathProvider.getAuthenticatedPaths()).authenticated()
				)
				.oauth2ResourceServer(oauth2 -> oauth2
						.opaqueToken(opaqueToken -> opaqueToken
								.introspector(introspector())));

		return http.build();
	}

	@Bean
	public OpaqueTokenIntrospector introspector() {
		return new CustomAuthoritiesOpaqueTokenIntrospector(url, clientId, clientSecret);
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.addAllowedMethod("*");
		configuration.addAllowedHeader("*");
		configuration.addAllowedOriginPattern("*");
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
