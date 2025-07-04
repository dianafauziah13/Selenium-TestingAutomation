package scenario;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import selenium.model.ResponseProducts;

public class RestAssuredE2EPOJO {
      /*
    * Scenario : E2E Testing Add product
    * Test Case - 001: Add data object
        * 1. Hit the endpoint add object with valid data
        * 2. Hit the endpoint get object data with valid data
    * Test Case - 002: Update data object     
    *   * 1. Hit the endpoint login with valid data
        * 2. Hit the endpoint update with valid data
        * 3. Hit the endpoint get object with valid data
    * Test Case - 003: Delete data object     
    *   * 1. Hit the endpoint login with valid data
        * 2. Hit the endpoint delete with valid data
        * 3. Hit the endpoint get object with valid data and result must be null
    */
    String tokenLogin, IdProduct;

    @BeforeClass
    public void setup(){
         /*
         * Define the base URL for the API
         * String baseUrl = "https://whitesmokehouse.com";
         */
        RestAssured.baseURI = "https://whitesmokehouse.com";

         // Login to the API and get the token
        String jsonLogin = "{\n" + //
                        "  \"email\": \"dianafauziah.dev@gmail.com\",\n" + //
                        "  \"password\": \"@dmin123\"\n" + //
                        "}";
        // Send POST request to login endpoint
        Response responseLogin = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(jsonLogin)
                .log().all()
                .when()
                .post("/webhook/api/login");
        Assert.assertEquals(responseLogin.getStatusCode(), 200,
                "Expected status code 200 but got " + responseLogin.getStatusCode());
        tokenLogin = responseLogin.jsonPath().getString("token");
        // System.out.println("Token Login: " + tokenLogin);     
    }

    @Test (priority = 1)
    public void addData(){
        /*
        * Test Case - 001: Add data object
        * 1. Hit the endpoint add object with valid data
        * 2. Hit the endpoint get object data with valid data
        */
        String requestBody = "{\n" +
                "    \"name\": \"Apple MacBook Pro 16 Diana Punya\",\n" +
                "    \"data\": {\n" +
                "        \"year\": 2025,\n" +
                "        \"price\": 1849.99,\n" +
                "        \"cpu_model\": \"Intel Core i9\",\n" +
                "        \"hard_disk_size\": \"1 TB\",\n" +
                "        \"capacity\": \"2\",\n" +
                "        \"screen_size\": \"14\",\n" +
                "        \"color\": \"red\"\n" +
                "    }\n" +
                "}";

        // Hit the endpoint add object with valid data
        Response responseAddData = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + tokenLogin)
                .body(requestBody)
                .log().all()
                .post("/webhook/api/objects");

        System.out.println("Response: " + responseAddData.asPrettyString());
        assert responseAddData.getStatusCode() == 200 : "Expected status code 200 but got " + responseAddData.getStatusCode();

        // Ambil list ID product yang ditambahkan
        List<Integer> ids = responseAddData.jsonPath().getList("id");
        IdProduct = ids.get(0).toString();
        System.out.println("ID Prodcut: " + IdProduct);

         // Hit the endpoint get object data with valid data
        Response responseGetObject = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + tokenLogin)
                .log().all()
                .when()
                .get("/webhook/8749129e-f5f7-4ae6-9b03-93be7252443c/api/objects/"+ IdProduct );

        // Print the response
        System.out.println("Response: " + responseGetObject.asPrettyString());
        ResponseProducts responseProducts = responseGetObject.as(ResponseProducts.class);
        // System.out.println("hasil panggil:" + responseProducts);
        Assert.assertEquals(responseProducts.name, "Apple MacBook Pro 16 Diana Punya");
        Assert.assertEquals(responseProducts.data.year, "2025");
        Assert.assertEquals(responseProducts.data.price, 1849.99);
        Assert.assertEquals(responseProducts.data.cpuModel, "Intel Core i9");
        Assert.assertEquals(responseProducts.data.hardDiskSize, "1 TB");
        Assert.assertEquals(responseProducts.data.capacity, "2");
        Assert.assertEquals(responseProducts.data.screenSize, "14");
        Assert.assertEquals(responseProducts.data.color, "red");

    }

 @Test(priority = 2)
    public void updateData(){
    /*
        * Test Case - 002: Update data object     
        * 1. Hit the endpoint login with valid data
        * 2. Hit the endpoint update with valid data
        * 3. Hit the endpoint get object with valid data
    */

        String bodyUpdate = "{\n" +
                "    \"name\": \"Apple MacBook Pro 16 Diana Punya Updated\",\n" +
                "    \"data\": {\n" +
                "        \"year\": 2027,\n" +
                "        \"price\": 9384765,\n" +
                "        \"cpu_model\": \"Intel Core i9 plus\",\n" +
                "        \"hard_disk_size\": \"3 TB\",\n" +
                "        \"capacity\": \"5\",\n" +
                "        \"screen_size\": \"15\",\n" +
                "        \"color\": \"blue\"\n" +
                "    }\n" +
                "}";
                         
        // Send update request to object endpoint
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + tokenLogin)
                .body(bodyUpdate)
                .log().all()
                .when()
                .put("/webhook/37777abe-a5ef-4570-a383-c99b5f5f7906/api/objects/"+IdProduct);

        System.out.println("Response: " + response.asPrettyString());
        assert response.getStatusCode() == 200 : "Expected status code 200 but got " + response.getStatusCode();

        // Hit the endpoint get object data with valid data
        Response responseGetUpdatedObject = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + tokenLogin)
                .log().all()
                .when()
                .get("/webhook/8749129e-f5f7-4ae6-9b03-93be7252443c/api/objects/"+ IdProduct );
        // Print the response
        System.out.println("Response: " + responseGetUpdatedObject.asPrettyString());

        // Validate the response
       Response responseGetObject = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + tokenLogin)
                .log().all()
                .when()
                .get("/webhook/8749129e-f5f7-4ae6-9b03-93be7252443c/api/objects/"+ IdProduct );

        // Print the response
        System.out.println("Response: " + responseGetObject.asPrettyString());
        ResponseProducts responseProducts = responseGetObject.as(ResponseProducts.class);
        // System.out.println("hasil panggil:" + responseProducts);
        Assert.assertEquals(responseProducts.name, "Apple MacBook Pro 16 Diana Punya Updated");
        Assert.assertEquals(responseProducts.data.year, "2027");
        Assert.assertEquals(responseProducts.data.price, 9384765);
        Assert.assertEquals(responseProducts.data.cpuModel, "Intel Core i9 plus");
        Assert.assertEquals(responseProducts.data.hardDiskSize, "3 TB");
        Assert.assertEquals(responseProducts.data.capacity, "5");
        Assert.assertEquals(responseProducts.data.screenSize, "15");
        Assert.assertEquals(responseProducts.data.color, "blue");
    }

@Test(priority = 3)
    public void deleteData(){
    /*
    * Test Case - 003: Delete data object     
    *   * 1. Hit the endpoint login with valid data
        * 2. Hit the endpoint delete with valid data
        * 3. Hit the endpoint get object with valid data and result must be null
    */

        // Send DELETE request to object endpoint
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + tokenLogin)
                .log().all()
                .when()
                .delete("/webhook/d79a30ed-1066-48b6-83f5-556120afc46f/api/objects/"+ IdProduct);
        // Print the response
         System.out.println("Response: " + response.asPrettyString()); 

        // Validate the response
        Assert.assertEquals(response.getStatusCode(), 200,
                "Expected status code 200 but got " + response.getStatusCode());
       
        // Hit the endpoint get object data with valid data
        Response responseGetsObject = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + tokenLogin)
                .log().all()
                .when()
                .get("/webhook/8749129e-f5f7-4ae6-9b03-93be7252443c/api/objects/"+ IdProduct );
        // Print the response
        System.out.println("Response: " + responseGetsObject.asPrettyString());
                Assert.assertEquals(responseGetsObject.getStatusCode(), 200,
                "Expected status code 200 but got " + responseGetsObject.getStatusCode());

        ResponseProducts responseProducts = responseGetsObject.as(ResponseProducts.class);
        // System.out.println("hasil panggil:" + responseProducts);
        Assert.assertNull(responseProducts.name);
    }



}
