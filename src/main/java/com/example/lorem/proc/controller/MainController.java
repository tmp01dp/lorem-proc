package com.example.lorem.proc.controller;

import com.example.lorem.proc.model.ParagraphType;
import com.example.lorem.proc.model.Result;
import com.example.lorem.proc.service.CalculateService;
import com.example.lorem.proc.service.KafkaService;
import com.example.lorem.proc.service.LoremService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class MainController {

    Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    LoremService loremService;

    @Autowired
    CalculateService calcService;

    @Autowired
    KafkaService kafkaService;

    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping
    @RequestMapping(value = "/betvictor/text")
    @Validated
    public Result getText(@RequestParam @Min(value = 1, message = "Number of paragraphs must be greater than 0") Integer p,
                          @RequestParam ParagraphType l) {
        long start = System.currentTimeMillis();
        List<String> text = loremService.loadText(p, l);
        Result result = calcService.process(text);
        result.setTotalProcessingTime(System.currentTimeMillis() - start);

        CompletableFuture.runAsync(() -> {
            try {
                kafkaService.send(objectMapper.writeValueAsString(result));
            } catch (Exception e) {
                logger.error("Error", e);
            }
        });

        return result;
    }
}
