package com.eduardodiamandisqs.app.aws_sqs_service;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sqs")
public class SqsController {

    private final SqsService sqsService;

    public SqsController(SqsService sqsService) {
        this.sqsService = sqsService;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestParam String message) {
        sqsService.sendMessage(message);
        return "Mensagem enviada para a fila!";
    }
}
