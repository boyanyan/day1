package com.zr.rabbit;

import com.zr.inquiry.model.Parts;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 生产者
 * Created by Lx on 2018/10/29.
 */
@RestController
public class RabbitMQ {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @GetMapping("/testRabbitMQ")
    public void testRabbitMQ(){
        Parts parts = new Parts();
        parts.setCode("123");
        parts.setName("小明");
        parts.setPartsId(1);
        amqpTemplate.convertAndSend("testRabbitMQ001",parts);
    }
    @RabbitListener(queuesToDeclare = @Queue("returnTestRabbitMQ001"))
    public void returnTest(Parts parts){
        System.out.println(parts.toString());
    }

    @GetMapping("/testRabbitMQExchange")
    public void testRabbitMQExchange(@RequestParam("fruit") String fruit,@RequestParam("class") String CLASS){
        amqpTemplate.convertAndSend("myMQExchange1029","fruit",fruit);
        amqpTemplate.convertAndSend("myMQExchange1029","class",CLASS);
    }
}
