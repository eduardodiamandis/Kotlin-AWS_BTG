package com.eduardodiamandisqs.app.aws_sqs_service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SqsSender {

    @Value("${aws.sqs.queue-name}")
    private String queueUrl;

    private final AmazonSQS sqsClient;

    public SqsSender() {
        sqsClient = AmazonSQSClientBuilder.standard()
                .withRegion("sa-east-1") // Região de sua fila
                .build();
    }

    // Método para enviar a mensagem
    public void sendMessage(String message) {
        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(message);

        sqsClient.sendMessage(sendMessageRequest);
        System.out.println("Mensagem enviada: " + message);
    }
}
