package com.microservice.microservicebus.controller;

import com.microservice.microservicebus.entity.Sms;
import com.microservice.microservicebus.entity.SmsInfo;
import com.microservice.microservicebus.service.SmsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sms")
@AllArgsConstructor
public class SmsController {
    private final SmsService smsService;

    @GetMapping("/{mid}")
    public ResponseEntity<Sms> getReport(@PathVariable String mid){
        return  new ResponseEntity<Sms>(smsService.getReport(mid), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> sendSms(@RequestBody SmsInfo smsInfo){
        return new ResponseEntity<String>(smsService.sendSms(smsInfo),HttpStatus.ACCEPTED);
    }

}
