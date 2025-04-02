package br.campotech.common.config;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.DefaultOAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.resource.introspection.NimbusOpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

public class CustomAuthoritiesOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

	private String url;

	private String clientId;

	private String clientSecret;

	private OpaqueTokenIntrospector delegate;

	public CustomAuthoritiesOpaqueTokenIntrospector(String url, String clientId, String clientSecret) {
		this.url = url;
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		delegate = new NimbusOpaqueTokenIntrospector(url, clientId, clientSecret);
	}

	public OAuth2AuthenticatedPrincipal introspect(String token) {
		OAuth2AuthenticatedPrincipal principal = this.delegate.introspect(token);
		String name = principal.getName();
		if (name == null) {
			name = principal.getAttribute("user_name");
		}
		return new DefaultOAuth2AuthenticatedPrincipal(name, principal.getAttributes(), extractAuthorities(principal));
	}

	private Collection<GrantedAuthority> extractAuthorities(OAuth2AuthenticatedPrincipal principal) {
		List<String> scopes = principal.getAttribute("scope");
		return scopes.stream()
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}

	public String getUrl() {
		return url;
	}

	public String getClientId() {
		return clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

}