package com.mity_queue.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  @Value("${rabbitmq.queue.name}")
  private String queue;

  @Value("${rabbitmq.queue.json.name}")
  private String jsonQueue;

  @Value("${rabbitmq.exchange.name}")
  private String exchange;

  @Value("${rabbitmq.routing.key}")
  private String routingKey;

  @Value("${rabbitmq.routing.json.key}")
  private String jsonRoutingKey;

  // Spring bean for rabbitmq queue
  @Bean
  public Queue queue() {
    return new Queue(queue);
  }

  @Bean
  public Queue jsonQueue() {
    return new Queue(jsonQueue);
  }

  // Spring bean for rabbitmq exchange
  @Bean
  public TopicExchange exchange() {
    return new TopicExchange(exchange);
  }

  // Binding exchange with queue using routing key.
  @Bean
  public Binding bind() {
    return BindingBuilder.bind(queue()).to(exchange()).with(routingKey);
  }

  // Binding exchange with jsonQueue using queue routing key.
  @Bean
  public Binding jsonBind() {
    return BindingBuilder.bind(jsonQueue()).to(exchange()).with(jsonRoutingKey);
  }

  @Bean
  public MessageConverter converter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(converter());
    return rabbitTemplate;
  }

  // Spring boot will autoconfigure these beans
  // ConnectionFactory
  // RabbitTemplate
  // RabbitAdmin
}
