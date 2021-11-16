package com.microservice.microservicebus.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.routingkey}")
    private String routingKey;
    @Value("${rabbitmq.routingkey1}")
    private String routingKey1;
    @Value("${rabbitmq.routingkey2}")
    private String routingKey2;
    @Value("${rabbitmq.routingkey3}")
    private String routingKey3;
    @Value("${rabbitmq.queue}")
    private String queue;
    @Value("${rabbitmq.queue1}")
    private String queue1;
    @Value("${rabbitmq.queue2}")
    private String queue2;
    @Value("${rabbitmq.queue3}")
    private String queue3;

    @Bean
    public Queue queue() {
        return new Queue(queue, false);
    }

    @Bean
    public Queue queue1() {
        return new Queue(queue1, false);
    }

    @Bean
    public Queue queue2() {
        return new Queue(queue2, false);
    }

    @Bean
    public Queue queue3() {
        return new Queue(queue3, false);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding binding(@Qualifier("queue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    @Bean
    public Binding binding1(@Qualifier("queue1") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey1);
    }

    @Bean
    public Binding binding2(@Qualifier("queue2") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey2);
    }

    @Bean
    public Binding binding3(@Qualifier("queue3") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey3);
    }

    @Bean
    public MessageConverter convertor() {
        return new Jackson2JsonMessageConverter();
    }
}
