package com.facenet.vnpays.producer;

import com.facenet.vnpays.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProducerService {
  @Autowired
  private final JmsTemplate topicJmsTemplate;
  @Value("topic-request")
  private String topic;

  private final ObjectMapper objectMapper;

  public void sendMessage(Message message) {
    try {
      String jsonMessage = objectMapper.writeValueAsString(message);
      topicJmsTemplate.convertAndSend(topic, jsonMessage);
    } catch (JsonProcessingException e) {
      log.error("Error converting message to JSON: {}", e.getMessage());
    }
  }



}



