package com.eduardodiamandisqs.app.aws_sqs_service;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SqsService {

    private final QueueMessagingTemplate queueMessagingTemplate;

    @Value("${aws.sqs.queue-name}")
    private String queueName;

    public SqsService(QueueMessagingTemplate queueMessagingTemplate) {
        this.queueMessagingTemplate = queueMessagingTemplate;
    }

    public void sendMessage(String message) {
        queueMessagingTemplate.convertAndSend(queueName, message);
        System.out.println("Mensagem enviada para SQS: " + message);
    }
}