package com.example.kafkaadapter;


import com.example.kafkaadapter.data.JsonData;
import com.example.kafkaadapter.data.JsonRepository;
import com.example.kafkaadapter.model.JsonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;


@Controller
public class JsonController {
    private final JsonService jsonService;
    private final JsonRepository jsonRepository;

    @Value("classpath:templates/test1.json")
    Resource resourceFile;
    ObjectMapper obj = new ObjectMapper();

    public JsonController(JsonService jsonService, JsonRepository jsonRepository) {
        this.jsonService = jsonService;
        this.jsonRepository = jsonRepository;
    }



    @GetMapping("/test")
    @ResponseBody
    public String jsonParse() throws IOException {
        JsonData js;
        js = obj.readValue(resourceFile.getFile(), JsonData.class);
        jsonService.addToRep(1L, js);
        return jsonService.rest(jsonRepository.get(1L).getUrl(),
                jsonRepository.get(1L).getParametres(), jsonRepository.get(1L).getVerb());
    }
    @GetMapping("/books/{id}")
    @ResponseBody
    public String getBook(@PathVariable Long id){
        return jsonRepository.get(id).getBody();
    }


    @SneakyThrows
    @KafkaListener(topics = "myTopic", groupId = "spring-boot-kafka")
    public void consume(ConsumerRecord<String, String> record){
        JsonData js;
        String idMessage = record.key();
        String KafkaMessage = record.value();
        Long id = Long.parseLong(idMessage);
        js = obj.readValue(KafkaMessage, JsonData.class);
        jsonService.addToRep(id, js);
    }
}

