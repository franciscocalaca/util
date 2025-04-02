package br.campotech.common.recuperarsenha;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.Base64;
import java.util.Map;

@Service
public class AuthBo {

    @Value("${campotech.security.url}")
    private String authUrl;

    @Value("${campotech.security.clientId}")
    private String clientId;

    @Value("${campotech.security.clientSecret}")
    private String clientSecret;

    public Map<String, Object> login(Map<String, String> loginRequest) {
        RestTemplate restTemplate = new RestTemplate();

        // Configura os headers com autenticação Basic
        HttpHeaders headers = new HttpHeaders();
        String auth = clientId + ":" + clientSecret;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        headers.add("Authorization", "Basic " + encodedAuth);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Cria o corpo da requisição (form-urlencoded)
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("username", loginRequest.get("email"));  // Usa "email" como "username"
        body.add("password", loginRequest.get("password"));

        // Cria a requisição HTTP com headers e corpo
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        try {
            // Faz a requisição POST para o servidor de autenticação OAuth2
            ResponseEntity<Map> response = restTemplate.exchange(authUrl, HttpMethod.POST, request, Map.class);

            // Retorna o corpo da resposta, que contém o token de acesso e outras informações
            return response.getBody();
        } catch (HttpClientErrorException e) {
            // Loga o erro para depuração
            System.out.println("Erro de autenticação: " + e.getMessage());
            System.out.println("Resposta do servidor: " + e.getResponseBodyAsString());
            throw e;
        }
    }
}




