package com.facenet.vnpays.producer;

import com.facenet.vnpays.consumer.ACBConsumer;
import com.facenet.vnpays.consumer.SHBConsumer;
import com.facenet.vnpays.consumer.SeaBankConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueueService {
    private final ACBConsumer acbConsumer;
    private final SeaBankConsumer seaBankConsumer;
    private final SHBConsumer shbConsumer;


    public QueueService(ACBConsumer acbConsumer, SeaBankConsumer seaBankConsumer, SHBConsumer shbConsumer) {
        this.acbConsumer = acbConsumer;
        this.seaBankConsumer = seaBankConsumer;
        this.shbConsumer = shbConsumer;
    }
    public void response( String message){
        acbConsumer.listen(message);
        seaBankConsumer.listen(message);
        shbConsumer.listen(message);

    }
}
