package convertValue;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import selenium.model.RequestLogin;

public class Serialize {

    @Test
    public void loginTest() throws JsonProcessingException{
        RestAssured.baseURI = "https://whitesmokehouse.com";
        RequestLogin requestLogin = new RequestLogin("dianafauziah.dev@gmail.com", "@admin123");
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonLogin = objectMapper.writeValueAsString(requestLogin);

         // Send POST request to login endpoint
        Response responseLogin = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(jsonLogin)
                .log().all()
                .when()
                .post("/webhook/api/login");
        Assert.assertEquals(responseLogin.getStatusCode(), 200,
                "Expected status code 200 but got " + responseLogin.getStatusCode());

        // Lihat hasilnya
        System.out.println("Response: " + responseLogin.asPrettyString());
        System.out.println("jsonLogin: " + requestLogin);

    }
}
