package com.microservice.microservicebus.exception;

public class CarrierServerErrorException extends RuntimeException {
    public CarrierServerErrorException(String message) {
        super(message);
    }

}
