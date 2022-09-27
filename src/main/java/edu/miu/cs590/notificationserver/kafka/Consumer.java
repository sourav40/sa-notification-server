package edu.miu.cs590.notificationserver.kafka;

import edu.miu.cs590.notificationserver.dto.EmailDto;
import edu.miu.cs590.notificationserver.service.EmailSender;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

@Slf4j
@Service
public class Consumer {
    @Autowired
    private EmailSender emailSender;

    @Value("${kafka-server-url}")
    private String kafkaUrl;

    @Value("${kafka-server-port}")
    private String kafkaPort;

    public void consumeMessage() {

        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUrl + ":" + kafkaPort);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, " edu.miu.cs590.notificationserver.deserializer.EmailDeserializer");
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "email-payment-consumer-group"); // consumer needs to be in certain group
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // read for the first offset

        KafkaConsumer<String, EmailDto> consumer = new KafkaConsumer<>(properties);

        final Thread mainThread = Thread.currentThread();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                log.info("Detected a shutdown, let's exit by calling consumer.wakeup()...");
                consumer.wakeup();

                try {
                    mainThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        try {
            consumer.subscribe(Arrays.asList("email_payment"));

            while (true) {
                log.info("polling...");
                ConsumerRecords<String, EmailDto> records = consumer.poll(Duration.ofMillis(1000));

                for (ConsumerRecord<String, EmailDto> record : records) {
                    log.info("Value: " + record.value() + "\n" +
                            "Partition: " + record.partition() + "\n" +
                            "Offset: " + record.offset() + "\n"
                    );
                    emailSender.sendEmail(record.value());
                }
            }
        } catch (WakeupException e) {
            log.info("Wake up exception!!!");
            // this is an expected exception while trying to close the consumer
        } catch (Exception e) {
            log.error("Unexpected exception.");
        } finally {
            consumer.close();
            log.info("The consumer in now gracefully closed.");
        }
    }
}
