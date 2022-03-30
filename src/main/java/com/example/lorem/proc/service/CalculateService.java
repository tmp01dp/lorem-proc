package com.example.lorem.proc.service;

import com.example.lorem.proc.model.Result;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

@Service
public class CalculateService {

    public Result process(List<String> list) {
        HashMap<String, Long> freqMap = new HashMap<>();
        List<ParagraphStat> statistics = new ArrayList<>(list.size());
        // process every Paragraph
        for (String s : list) {
            long start = System.currentTimeMillis();
            String[] words = s.split("\\s+");
            for (String word : words) {
                if (!(word.equalsIgnoreCase("<p>") || word.equalsIgnoreCase("</p>"))) {
                    long n = freqMap.getOrDefault(word, 0L);
                    freqMap.put(word, n + 1);
                }
            }
            statistics.add(new ParagraphStat(words.length, System.currentTimeMillis() - start));
        }
        // find most frequent word
        Map.Entry<String, Long> freqWord = freqMap.entrySet()
                .stream()
                .sorted((v1, v2) -> -1 * v1.getValue().compareTo(v2.getValue()))
                .findFirst()
                .orElse(null);
        if (freqWord == null) {
            return new Result("", 0, 0, 0);
        } else {
            // process avg values
            double avgSize = statistics.stream().mapToLong(v -> v.size).sum() / statistics.size();
            double avgTime = statistics.stream().mapToLong(v -> v.time).sum() / statistics.size();
            return new Result(freqWord.getKey(), avgSize, avgTime, 0);
        }
    }

    @Data
    class ParagraphStat {
        long size;
        long time;

        public ParagraphStat(Integer size, long time) {
            this.size = size;
            this.time = time;
        }
    }
}
