package com.facenet.vnpays.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
@Component
public abstract class MessageConsumer {


  protected ObjectMapper objectMapper;

  protected  JmsTemplate topicJmsTemplate;


  @Value("topic-response")
  String topicResponse;


  @Value("topic-data")
  String topicData;

  public MessageConsumer(ObjectMapper objectMapper, JmsTemplate topicJmsTemplate) {
    this.objectMapper=objectMapper;
    this.topicJmsTemplate=topicJmsTemplate;
  }


  @JmsListener(destination = "topic-response")
  public void receiveResponse(String message) {
    System.out.println("Received response" + message);
  }

  public abstract void listen(String jsonMessage);

  public XSSFWorkbook createExcelWorkbook() {
    // Tạo workbook và điền dữ liệu vào
    XSSFWorkbook workbook = new XSSFWorkbook();
    Sheet sheet = workbook.createSheet("=)))))");
    Row headerRow = sheet.createRow(0);
    Cell headerCell = headerRow.createCell(0);
    headerCell.setCellValue("Data");

    // Điền dữ liệu mẫu vào file Excel
    Row dataRow = sheet.createRow(1);
    Cell dataCell = dataRow.createCell(0);
    dataCell.setCellValue("Sample data 1");

    log.info("create file excel successfully");
    return workbook;
  }

  public void saveExcelWorkbook(XSSFWorkbook workbook, String filePath) throws IOException {
    // Lưu workbook vào file
    FileOutputStream fileOutputStream = new FileOutputStream(filePath);
    workbook.write(fileOutputStream);
    fileOutputStream.close();
    workbook.close();
  }

}
