package com.example.demo.service;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.ServiceResponse;
import com.example.demo.entity.User;
import com.example.demo.exception.UsernameNotFoundException;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.Constants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImplementation implements AuthService {

    @Value("${keycloak.token.url}")
    private String tokenUrl;
    @Value("${keycloak.token.grantType}")
    private String grantType;
    @Value("${keycloak.token.clientId}")
    private String clientId;
    @Value("${keycloak.token.clientSecret}")
    private String clientSecret;
    
    private final RestTemplate restTemplate;
    private final UserRepository userRepo;
    private final MessageSource messageSource;

    
    

    @Override
    public ServiceResponse getAccessToken(User user) throws UsernameNotFoundException {

	try {
	    if (user.getUsername()==null|| !userRepo.existsById(user.getUsername())) {
		throw new UsernameNotFoundException();
	    }
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	    HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(setFormData(user), headers);
	    return new ServiceResponse(Constants.SUCCESS,
		    messageSource.getMessage("messages.val013", null, LocaleContextHolder.getLocale()),
		    List.of(restTemplate.exchange(tokenUrl, HttpMethod.POST, requestEntity, JSONObject.class)
			    .getBody()));
	} catch (UsernameNotFoundException e) {
	    log.error("Error during getAccessToken: " + e.getMessage());
	    throw e;
	} catch (HttpClientErrorException e) {
	    log.error("Error during getAccessToken: " + e.getMessage());
	    throw e;
	} catch (Exception e) {
	    log.error(e.getMessage());
	}
	return new ServiceResponse(Constants.FAILED,
		messageSource.getMessage("messages.val014", null, LocaleContextHolder.getLocale()), new JSONArray());
    }

    private MultiValueMap<String, String> setFormData(User user) {
	MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
	try {
	    formData.add(Constants.GRANT_TYPE, grantType);
	    formData.add(Constants.CLIENT_ID, clientId);
	    formData.add(Constants.USERNAME, user.getUsername());
	    formData.add(Constants.PASSWORD, user.getPassword());
	    formData.add(Constants.CLIENT_SECRET, clientSecret);
	    return formData;
	} catch (Exception e) {
	    log.error("Error during setFormData: " + e.getMessage());
	}
	return formData;
    }
}
