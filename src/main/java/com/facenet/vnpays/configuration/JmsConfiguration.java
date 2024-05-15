package com.facenet.vnpays.configuration;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@EnableJms
public class JmsConfiguration {

  String BROKER_URL = "tcp://localhost:61616";
  String BROKER_USERNAME = "admin";
  String BROKER_PASSWORD = "admin";

  @Bean
  public ActiveMQConnectionFactory connectionFactory(){
    ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
    connectionFactory.setBrokerURL(BROKER_URL);
    connectionFactory.setPassword(BROKER_USERNAME);
    connectionFactory.setUserName(BROKER_PASSWORD);

    return connectionFactory;
  }

  @Bean 
  public JmsTemplate topicJmsTemplate(){
    JmsTemplate template = new JmsTemplate();
    template.setConnectionFactory(connectionFactory());
    template.setPubSubDomain(true);
    return template;
  }

  @Bean
  public JmsTemplate queueJmsTemplate() {
    JmsTemplate template = new JmsTemplate();
    template.setConnectionFactory(connectionFactory());
    template.setPubSubDomain(false); // Gửi đến hàng đợi
    return template;
  }
  @Bean
  public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory());
    factory.setConcurrency("1-1");
    factory.setPubSubDomain(true);
    return factory;
  }
}
