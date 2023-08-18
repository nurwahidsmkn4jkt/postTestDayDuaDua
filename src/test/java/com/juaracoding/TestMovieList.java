package com.juaracoding;

import io.restassured.http.ContentType;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestMovieList {
    String apiToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2NzFhNmU2NmNjZmNhZDc4MzUxYmJlYzg4MDM4NzkxOCIsInN1YiI6IjY0ZGI3MGQ2Yjc3ZDRiMTE0MDE4NmFiOCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.lpW4MEZY3QFKgJITc29lGoce5vUPtSJZ9HFIfsyj3cM";
    String endpointOne = "https://api.themoviedb.org/3/movie/now_playing";
    String endpointTwo = "https://api.themoviedb.org/3/movie/popular";
    String endpointThree = "https://api.themoviedb.org/3/movie/569094/rating";

    @Test
    public void getNowPlaying(){
        given().header("Authorization", apiToken)
                .queryParam("language", "en-Us")
                .queryParam("page", "1")
                .get(endpointOne)
                .then()
                .statusCode(200)
                .body("results.id[0]", equalTo(976573));
    }

    @Test
    public void getMoviePopular(){
        given().header("Authorization", apiToken)
                .queryParam("language", "en-Us")
                .queryParam("page", "1")
                .get(endpointTwo)
                .then()
                .statusCode(200)
                .body("results.id[0]", equalTo(569094));
    }

    @Test
    public void postMovieRating(){
        JSONObject request = new JSONObject();
        request.put("value", "8.50");
        System.out.println(request.toJSONString());

        given().header("Authorization", apiToken)
                .header("Content-Type", "application/json")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(request.toJSONString())
                .when()
                .post(endpointThree)
                .then()
                .statusCode(201)
                .body("status_message", equalTo("The item/record was updated successfully."))
                .log().all();
    }
}
