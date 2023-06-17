package com.example.kafkaadapter.model;

import com.example.kafkaadapter.data.JsonData;
import com.example.kafkaadapter.data.JsonRepository;
import io.restassured.http.ContentType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static io.restassured.RestAssured.given;


@Service
@AllArgsConstructor
public class JsonService {
    private final JsonRepository jsonRepository;

    public String parseToString(JsonData js) {
        return js.getVerb().toUpperCase() + " " + "http://" + js.getUrl() + js.getParametres();
    }

    public void addToRep(Long id, JsonData file) {
        jsonRepository.save(id, file);
    }

    public String rest(String url, String param, String verb) {
       return given()
                .baseUri("http://" + url)
                .basePath(param)
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(200).extract().asString();

    }

}
