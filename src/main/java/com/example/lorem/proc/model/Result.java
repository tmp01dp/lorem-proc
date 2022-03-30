package com.example.lorem.proc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Result {
    @JsonProperty("freq_word")
    String freqWord;
    @JsonProperty("avg_paragraph_size")
    double avgParagraphSize;
    @JsonProperty("avg_paragraph_processing_time")
    double avgParagraphProcessing_time;
    @JsonProperty("total_processing_time")
    long totalProcessingTime;

    public Result(String freqWord, double avgParagraphSize, double avgParagraphProcessing_time, long totalProcessingTime) {
        this.freqWord = freqWord;
        this.avgParagraphSize = avgParagraphSize;
        this.avgParagraphProcessing_time = avgParagraphProcessing_time;
        this.totalProcessingTime = totalProcessingTime;
    }
}
