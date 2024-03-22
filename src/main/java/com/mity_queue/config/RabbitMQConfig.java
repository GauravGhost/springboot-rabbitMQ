package com.mity_queue.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  @Value("${rabbitmq.queue.name}")
  private String queue;

  @Value("${rabbitmq.exchange.name}")
  private String exchange;

  @Value("${rabbitmq.routing.key}")
  private String routingKey;
  // Spring bean for rabbitmq queue
  @Bean
  public Queue queue() {
    return new Queue(queue);
  }

  // Spring bean for rabbitmq exchange
  @Bean
  public TopicExchange exchange() {
    return new TopicExchange(exchange);
  }

  // Spring bean for binding exchange with queue using routing key.
  @Bean
  public Binding bind() {
    return BindingBuilder.bind(queue()).to(exchange()).with(routingKey);
  }
  // Spring boot will autoconfigure these beans
  // ConnectionFactory
  // RabbitTemplate
  // RabbitAdmin
}
