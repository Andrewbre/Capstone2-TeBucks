package com.techelevator.tebucks.services;

import com.techelevator.tebucks.model.*;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

public class LoginService {
    private static final String API_BASE_URL = "https://te-pgh-api.azurewebsites.net/";
    private final RestTemplate restTemplate = new RestTemplate();


    public String login() {
        IRSLoginDto login = new IRSLoginDto();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<IRSLoginDto>entity =new HttpEntity<>(login,headers);
        String token = null;
            ResponseEntity<TokenDTo> response = restTemplate.exchange(API_BASE_URL + "login", HttpMethod.POST, entity,TokenDTo.class);
            TokenDTo body = response.getBody();
            if (body != null) {
                token = body.getToken();
            }
        return token;
    }

    public IrsLog addTransfer (Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(login());
        IrsLog log = new IrsLog(null,transfer.getUserFrom().getUsername(),transfer.getUserTo().getUsername(),transfer.getAmount().doubleValue());
        HttpEntity<IrsLog> entity = new HttpEntity<>(log,headers);
        try {
            ResponseEntity<IrsLog> response = restTemplate.exchange(API_BASE_URL +"/api/TxLog", HttpMethod.POST,entity,IrsLog.class);
            return response.getBody();
        }catch (RestClientResponseException | ResourceAccessException e) {}
        return null;
    }
}
