package com.microservice.microservicebus.service.serviceimpl;

import com.microservice.microservicebus.entity.Sms;
import com.microservice.microservicebus.entity.SmsInfo;
import com.microservice.microservicebus.exception.ClientErrorException;
import com.microservice.microservicebus.exception.CarrierServerErrorException;
import com.microservice.microservicebus.handlerexception.RestTemplateResponseErrorHandler;
import com.microservice.microservicebus.service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@Service
@Slf4j
@RequiredArgsConstructor
public class SmsServiceImpl implements SmsService {

    @Value("${sms.username}")
    private String username;
    @Value("${sms.password}")
    private String password;
    @Value("${sms.source}")
    private String source;
    @Value("${sms.servicetype}")
    private String serviceType;
    @Value("${sms.bearertype}")
    private String bearerType;
    @Value("${sms.urlpost}")
    private String URL_POST;
    @Value("${sms.urlget}")
    private String URL_GET;
    private final RestTemplateResponseErrorHandler restTemplateResponseErrorHandler;

    @Override
    @Retryable(value = CarrierServerErrorException.class)
    public String sendSms(SmsInfo smsInfo) {
        if (smsInfo.getPhoneNumber() == null || smsInfo.getMessage() == null) {
            log.error("Incorrect phone number or message");
            throw new ClientErrorException("Incorrect phone number or message");
        }
        HttpHeaders headers = new HttpHeaders();
        String authHeader = getAuthHeader();
        headers.set("Authorization", authHeader); // Add auth to header
        headers.setContentType(MediaType.APPLICATION_JSON);
        Sms sms = createSms(smsInfo);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(restTemplateResponseErrorHandler);
        HttpEntity<Sms> requestBody = new HttpEntity<>(sms, headers);
        Sms postSms;
        try {
            postSms = restTemplate.postForObject(URL_POST, requestBody, Sms.class);  //send SMS
        } catch (ResourceAccessException exception) {
            log.error(exception.getMessage());
            throw new CarrierServerErrorException(exception.getMessage());
        } catch (RestClientException exception){
            log.error(exception.getMessage());
            throw new ClientErrorException(exception.getMessage());
        }
        log.info("SMS is sending on number " + smsInfo.getPhoneNumber());
        if (postSms == null || postSms.getMid() == null) {
            log.warn("SMS does not delivery");
            throw new CarrierServerErrorException("SMS does not delivery");
        }
        return postSms.getMid();
    }

    @Override
    public Sms getReport(String mid) {
        HttpHeaders headers = new HttpHeaders();
        String authHeader = getAuthHeader();
        headers.set("Authorization", authHeader); // Add auth to header
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Sms> result;
        try {
            result = restTemplate.exchange(URL_GET + mid, HttpMethod.GET, entity, Sms.class); //get report
        } catch (ResourceAccessException exception) {
            log.error(exception.getMessage());
            throw new CarrierServerErrorException(exception.getMessage());
        }
        log.info("Report delivery");
        if (result.getBody() == null) {
            log.warn("Report not found");
            throw new CarrierServerErrorException("Report not found");
        }
        return result.getBody();
    }

    private String getAuthHeader() {
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII));
        return "Basic " + new String(encodedAuth); // Create basic auth
    }

    private Sms createSms(SmsInfo smsInfo) {
        return  Sms.builder()
                .source(source)
                .destination(smsInfo.getPhoneNumber())
                .serviceType(serviceType)
                .bearerType(bearerType)
                .content(smsInfo.getMessage())
                .build();
    }

}
