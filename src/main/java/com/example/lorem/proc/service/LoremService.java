package com.example.lorem.proc.service;

import com.example.lorem.proc.model.ParagraphType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

@Service
public class LoremService {

    Logger logger = LoggerFactory.getLogger(LoremService.class);

    public ArrayList<String> loadText(int maxCount, ParagraphType type) {
        int count = (int )(Math.random() * maxCount + 1);
        ArrayList<String> result = new ArrayList<String>();
        for (int i=1; i<=count; i++) {
            logger.info("Loop #"+i+" from total count "+count);
            result.add(callExternalService(i, type));
        }
        return result;
    }

    public String callExternalService(int i, ParagraphType type) {
        String uri = String.format("https://www.loripsum.net/api/%s/%s", i, type.toString().toLowerCase());
        HttpResponse<String> response = null;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(uri))
                    .version(HttpClient.Version.HTTP_1_1)
                    .GET()
                    .build();
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch(Exception e) {
            logger.error("Error loading data.", e);
        }
        if (response != null) {
            String s = response.body();
            logger.info("Lorem URI: "+uri+" Response length: "+s.length());
            return s;
        } else {
            return null;
        }
    }
}
