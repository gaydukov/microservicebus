package com.microservice.microservicebus.handlerexception;

import com.microservice.microservicebus.exception.ClientErrorException;
import com.microservice.microservicebus.exception.CarrierServerErrorException;
import com.microservice.microservicebus.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;
@Slf4j
@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return (httpResponse.getStatusCode().series() == CLIENT_ERROR
                || httpResponse.getStatusCode().series() == SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        if (httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
            log.error("Server unavailable " + httpResponse.getStatusText()+" "+httpResponse.getRawStatusCode());
            throw new CarrierServerErrorException("Server unavailable " + httpResponse.getStatusText()+" "+httpResponse.getRawStatusCode());
        } else if (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
            log.error("Bad credentials " + httpResponse.getStatusText()+" "+httpResponse.getRawStatusCode());
            throw new ClientErrorException("Bad credentials " + httpResponse.getStatusText()+" "+httpResponse.getRawStatusCode());
        }
    }

}

