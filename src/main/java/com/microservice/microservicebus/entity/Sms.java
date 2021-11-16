package com.microservice.microservicebus.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sms {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String mid;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String source;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String destination;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String serviceType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String bearerType;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime date;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String content;

}
