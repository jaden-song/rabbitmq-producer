package com.jaden.rabbitmqproducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

@Service
public class RabbitMessageProducer implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(RabbitMessageProducer.class);

    private static final String topicExchange = "rabbitmq-exchange";

    private final RabbitTemplate rabbitTemplate;

    public RabbitMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedDelay = 3000L)
    public void send() {
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Sending message...");
        IntStream.range(0, 1000)
                .forEach(i ->
                        rabbitTemplate.convertAndSend(
                                topicExchange,
                                "test.1.2",
                                new RabbitMessage(i, "Test"+i, i%2==0)
                        )
                );
    }
}
