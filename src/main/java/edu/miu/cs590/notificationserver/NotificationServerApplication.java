package edu.miu.cs590.notificationserver;

import edu.miu.cs590.notificationserver.kafka.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class NotificationServerApplication implements CommandLineRunner {

    @Autowired
    private Consumer consumer;

    public static void main(String[] args) {
        new SpringApplicationBuilder(NotificationServerApplication.class)
                .sources(NotificationServerApplication.class)
                .run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        consumer.consumeMessage();
    }

}
