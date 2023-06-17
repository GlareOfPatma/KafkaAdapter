package com.example.kafkaadapter.data;


import lombok.Data;

@Data
public class JsonData {
    private String verb, url, body, header, parametres;
}
