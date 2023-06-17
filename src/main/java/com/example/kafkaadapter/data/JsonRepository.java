package com.example.kafkaadapter.data;


import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class JsonRepository {
    private final Map<Long, JsonData> storage = new HashMap<>();

    public void save(Long id, JsonData file) {
        storage.put(id, file);
    }
    public JsonData get(Long id){
        return storage.get(id);
    }
}
