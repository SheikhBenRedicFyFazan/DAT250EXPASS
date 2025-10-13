package no.hvl.pollapp.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EventListener {

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void handleEvent(String eventJson) {
        System.out.println("Received event: " + eventJson);
    }
}
