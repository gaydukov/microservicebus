package com.microservice.microservicebus.service;

import com.microservice.microservicebus.entity.Sms;
import com.microservice.microservicebus.entity.SmsInfo;

public interface SmsService {

    String sendSms(SmsInfo smsInfo);

    Sms getReport(String mid);
}
