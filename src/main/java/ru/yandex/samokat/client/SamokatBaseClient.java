package ru.yandex.samokat.client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;

    public class SamokatBaseClient {
        public final String BASE_URL = "http://qa-scooter.praktikum-services.ru";
        protected RequestSpecification getBaseSpec(){
            return new RequestSpecBuilder()
                    .setContentType(JSON)
                    .setBaseUri(BASE_URL)
                    .build();


        }
    }


