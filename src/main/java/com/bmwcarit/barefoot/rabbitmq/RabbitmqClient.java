package com.bmwcarit.barefoot.rabbitmq;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @Author: Wang Qi
 * @Date: Created in 2023/03/23
 * @Time: 11:18
 * @Description
 */
public class RabbitmqClient {
    private Channel channel;

    public RabbitmqClient() {
        try {
            init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    private void init() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("admin");
        factory.setPassword("7GzAY_2EvWyt92");
        factory.setVirtualHost("/");
        factory.setHost("10.188.15.180");
        factory.setPort(5672);
        Connection conn = factory.newConnection();
        channel = conn.createChannel();

        channel.exchangeDeclare("barefoot_test", "topic", true);
    }

    public void publish(String message) throws IOException {
        this.channel.basicPublish("barefoot_test", "#", null, message.getBytes(StandardCharsets.UTF_8));
    }


    public static void main(String[] args) throws IOException {
        RabbitmqClient rabbitmqClient = new RabbitmqClient();
        rabbitmqClient.publish("hello...");
        System.out.println("发送消息...");
    }

}
