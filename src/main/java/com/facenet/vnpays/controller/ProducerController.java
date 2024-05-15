package com.facenet.vnpays.controller;

import com.facenet.vnpays.consumer.ACBConsumer;
import com.facenet.vnpays.consumer.SHBConsumer;
import com.facenet.vnpays.consumer.SeaBankConsumer;
import com.facenet.vnpays.model.Message;
import com.facenet.vnpays.producer.ProducerService;
import com.facenet.vnpays.producer.QueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/producers")
public class ProducerController {
  private final ProducerService producerService;
  @Autowired
  private QueueService queueService;

  @PostMapping("/send")
  public ResponseEntity<String> send(@RequestBody Message message){
    producerService.sendMessage(message);
    queueService.response(message.getMessage());
    return ResponseEntity.ok("OK");
  }
}
