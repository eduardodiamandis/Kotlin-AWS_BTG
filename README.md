# AWS SQS Service with Spring Boot

Este projeto utiliza o **Spring Boot** para criar um serviço simples que envia e recebe mensagens usando o **AWS SQS (Simple Queue Service)**. A autenticação é feita utilizando o **IAM** (Identity and Access Management) da AWS.

## Requisitos

Antes de rodar o projeto, é necessário ter:

- **Java 17** ou superior.
- **Maven** para gerenciamento de dependências.
- Uma **conta AWS** com permissões para usar o SQS e IAM.
- **Credenciais AWS** configuradas localmente (via arquivo `~/.aws/credentials`, variáveis de ambiente ou IAM Role).

## Configuração do Projeto

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/aws-sqs-service.git
cd aws-sqs-service
```
 ### 2 Configuração do application.yml
 ```
aws:
  sqs:
    queue-name: "https://sqs.sa-east-1.amazonaws.com/SEU_ID/minha-fila" # URL da sua fila SQS
    region: "sa-east-1" # Região onde sua fila está localizada
    access-key-id: "SEU_ACCESS_KEY_ID"
    secret-access-key: "SEU_SECRET_ACCESS_KEY"
 ```
###  3 Dependências no pom.xml
```bash
<dependencies>
  <!-- Spring Boot Web -->
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-web</artifactId>
  </dependency>

  <!-- AWS SDK 2.x para SQS -->
  <dependency>
      <groupId>software.amazon.awssdk</groupId>
      <artifactId>sqs</artifactId>
      <version>2.20.20</version>
  </dependency>

  <!-- Spring Cloud AWS Messaging para SQS -->
  <dependency>
      <groupId>io.awspring.cloud</groupId>
      <artifactId>spring-cloud-starter-aws-messaging</artifactId>
  </dependency>
</dependencies>
```
### 4. Classe SqsSender para Envio de Mensagens
```
package com.eduardodiamandisqs.app.aws_sqs_service;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SqsSender {

    @Value("${aws.sqs.queue-name}")
    private String queueUrl;

    private final SqsClient sqsClient;

    public SqsSender() {
        sqsClient = SqsClient.builder()
                .region(Region.of("sa-east-1")) // Região da sua fila
                .build();
    }

    public void sendMessage(String message) {
        SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(message)
                .build();

        sqsClient.sendMessage(sendMessageRequest);
        System.out.println("Mensagem enviada: " + message);
    }
}


```
### Classe SqsListener para Recebimento de Mensagens
Esta classe consome mensagens da fila SQS utilizando a anotação @SqsListener:

###  Controlador para Testar o Envio de Mensagens
Crie um controlador simples para testar o envio de mensagens:

### Executando o Projeto
```
mvn spring-boot:run

```
### Testando o envio das mensagens acessando o endpoint:
```
GET http://localhost:8080/test-send-message

```
Se tudo estiver configurado corretamente, você verá no console:

No envio: Mensagem enviada: Mensagem de teste para SQS
No recebimento (pelo listener): Mensagem recebida da SQS: Mensagem de teste para SQS


### Permissões do IAM
Certifique-se de que as credenciais do IAM possuem as permissões necessárias para acessar a fila SQS. Por exemplo, a seguinte política pode ser aplicada:
```
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": "sqs:*",
      "Resource": "arn:aws:sqs:sa-east-1:SEU_ID:minha-fila"
    }
  ]
}

```
