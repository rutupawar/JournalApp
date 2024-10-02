package com.learning.journalApp.entity.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ExternResponse{

    private String id;
    private String name;
    private Data data;
    private String createdAt;

    @lombok.Data
    private class Data {
        private int year;
        private double price;
        @JsonProperty("CPU model")
        private String cpu_model;
        @JsonProperty("Hard disk size")
        private String hardDiskSize;
    }
}



