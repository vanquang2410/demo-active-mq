package com.facenet.vnpays.consumer;

import com.facenet.vnpays.model.File;
import com.facenet.vnpays.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class SeaBankConsumer extends  MessageConsumer{
    private  static final String FILE_NAME = "Seabank.xlsx";
    private static  final  String FILE_PATH ="C://Users//Admin//Documents//"+FILE_NAME;
    private final JmsTemplate queueJmsTemplate;

    public SeaBankConsumer(ObjectMapper objectMapper, JmsTemplate topicJmsTemplate, @Qualifier("queueJmsTemplate") JmsTemplate queueJmsTemplate) {
        super(objectMapper, topicJmsTemplate);
        this.queueJmsTemplate = queueJmsTemplate;
    }

    @Override
    @JmsListener(destination = "topic-request")
    public void listen(String jsonMessage ) {
        try{
            XSSFWorkbook workbook = createExcelWorkbook();
            saveExcelWorkbook(workbook, FILE_PATH);
            queueJmsTemplate.convertAndSend("queue-response", objectMapper.writeValueAsString(new File(FILE_NAME,FILE_PATH)));
        }
        catch (IOException e){
            log.error("listen failed");
        }
    }
}
