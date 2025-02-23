package com.eduardodiamandisqs.app.aws_sqs_service;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;



public class SqsLis {
    @SqsListener("${aws.sqs.queue-name}")
    public void receiveMessage(String message) {
        System.out.println("Mensagem recebida da SQS: " + message);
    }


}
