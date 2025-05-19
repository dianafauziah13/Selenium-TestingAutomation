package restassured;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RestAssuredTest {
    String token;
    
    @Test()
    public void Register(){
        RestAssured.baseURI = "https://whitesmokehouse.com";
         String bodyRegister = "{\n" + //
                    "  \"email\": \"dianafauziah.dev@gmail.com\",\n" + //
                    "  \"full_name\":\"Diana Fauziah\",\n" + //
                    "  \"password\": \"@dmin123\",\n" + //
                    "  \"department\":\"Technology\",\n" + //
                    "  \"phone_number\":\"0877080808\"\n" + //
                    "}";

        // Send POST request to employee endpoint
        Response responseRegister = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(bodyRegister)
                .log().all()
                .when()
                .post("/webhook/api/register");
        // Print the response
        System.out.println("Response: " + responseRegister.asPrettyString());
        
        // Validate the response

        Assert.assertEquals(responseRegister.getStatusCode(), 200,
                "Expected status code 200 but got " + responseRegister.getStatusCode());
        
        Assert.assertEquals(responseRegister.jsonPath().get("email"),"dianafauziah.dev@gmail.com",
                "Expected email dianafauziah.dev@gmail.com but got " + responseRegister.jsonPath().get("email"));
        Assert.assertEquals(responseRegister.jsonPath().get("full_name"),"Diana Fauziah",
                "Expected full_name Diana Fauziah but got " + responseRegister.jsonPath().get("full_name"));
        Assert.assertEquals(responseRegister.jsonPath().get("password"),"@dmin123",
                "Expected password @dmin123 but got " + responseRegister.jsonPath().get("password"));
        Assert.assertEquals(responseRegister.jsonPath().get("department"),"Technology",
                "Expected department Technology but got " + responseRegister.jsonPath().get("department"));
        Assert.assertEquals(responseRegister.jsonPath().get("phone_number"),"0877080808",
                "Expected phone_number 0877080808 but got " + responseRegister.jsonPath().get("phone_number"));
    }

    @Test()
    public void loginTest(){
 /*
         * Define the base URL for the API
         * String baseUrl = "https://whitesmokehouse.com";
         */
        RestAssured.baseURI = "https://whitesmokehouse.com";

        // Create login request
        String requestBody = "{\n" + //
                        "  \"email\": \"dianafauziah.dev@gmail.com\",\n" + //
                        "  \"password\": \"@dmin123\"\n" + //
                        "}";
        // Send POST request to login endpoint
        Response response = RestAssured.given()
                .contentType("application/json")
                .header("Content-Type", "application/json")
                .body(requestBody)
                .log().all()
                .when()
                .post("/webhook/api/login");
        // Print the response
        System.out.println("Response: " + response.asPrettyString()); 
        token = response.jsonPath().getString("token");  
        System.out.println("Token: " + token);      
    }


    @Test(dependsOnMethods = "loginTest")
    public void AddObjects(){

        RestAssured.baseURI = "https://whitesmokehouse.com";

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

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(requestBody)
                .log().all()
                .post("/webhook/api/objects");

        System.out.println("Response: " + response.asPrettyString());
        assert response.getStatusCode() == 200 : "Expected status code 200 but got " + response.getStatusCode();

        // Validasi detail properti
        assert response.jsonPath().getString("name").equals("[Apple MacBook Pro 16 Diana Punya]") 
                : "Expected name is Apple MacBook Pro 16 Diana Punya but got " + response.jsonPath().getString("name");
        assert response.jsonPath().getString("data.year").equals("[2025]") 
                : "Expected year is 2025 but got " + response.jsonPath().getString("data.year");
        assert response.jsonPath().getString("data.price").equals("[1849.99]") 
                : "Expected price is 1849.99 but got " + response.jsonPath().getString("data.price");
        assert response.jsonPath().getString("data.cpu_model").equals("[Intel Core i9]") 
                : "Expected cpu_model is Intel Core i9 but got " + response.jsonPath().getString("data.cpu_model");
        assert response.jsonPath().getString("data.color").equals("[red]") 
                : "Expected color is red but got " + response.jsonPath().getString("data.color");
        assert response.jsonPath().getString("data.capacity").equals("[2]") 
                : "Expected capacity is 2 but got " + response.jsonPath().getString("data.capacity");
        assert response.jsonPath().getString("data.screen_size").equals("[14]") 
                : "Expected screen_size is 14 but got " + response.jsonPath().getString("data.screen_size");

    }


    @Test(dependsOnMethods = "loginTest")
    public void updateObject(){
          RestAssured.baseURI = "https://whitesmokehouse.com";
        // Create Update objects request
        String bodyUpdate = "{\n" +
                "    \"name\": \"Apple MacBook Pro 16 Diana Punya Updated\",\n" +
                "    \"data\": {\n" +
                "        \"year\": 2027,\n" +
                "        \"price\": 9384765,\n" +
                "        \"cpu_model\": \"Intel Core i9 plus\",\n" +
                "        \"hard_disk_size\": \"3 TB\",\n" +
                "        \"capacity\": \"5\",\n" +
                "        \"screen_size\": \"15,5\",\n" +
                "        \"color\": \"blue\"\n" +
                "    }\n" +
                "}";
                         
        // Send update request to object endpoint
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(bodyUpdate)
                .log().all()
                .when()
                .put("/webhook/37777abe-a5ef-4570-a383-c99b5f5f7906/api/objects/202");

        System.out.println("Response: " + response.asPrettyString());
        assert response.getStatusCode() == 200 : "Expected status code 200 but got " + response.getStatusCode();

        // Validasi detail properti
        assert response.jsonPath().getString("name").equals("[Apple MacBook Pro 16 Diana Punya Updated]") 
                : "Expected name is Apple MacBook Pro 16 Diana Punya Updated but got " + response.jsonPath().getString("name");
        assert response.jsonPath().getString("data.year").equals("[2027]") 
                : "Expected year is 2027 but got " + response.jsonPath().getString("data.year");
        assert response.jsonPath().getString("data.price").equals("[9384765]") 
                : "Expected price is 1849.99 but got " + response.jsonPath().getString("data.price");
        // assert response.jsonPath().getString("data.cpu_model").equals("[Intel Core i9 plus]") 
        //         : "Expected cpu_model is Intel Core i9 plus but got " + response.jsonPath().getString("data.cpu_model");
        assert response.jsonPath().getString("data.color").equals("[blue]") 
                : "Expected color is blue but got " + response.jsonPath().getString("data.color");
        assert response.jsonPath().getString("data.capacity").equals("[5]") 
                : "Expected capacity is 3 but got " + response.jsonPath().getString("data.capacity");
        assert response.jsonPath().getString("data.screen_size").equals("[15,5]") 
                : "Expected screen_size is 15,5 but got " + response.jsonPath().getString("data.screen_size");
    }


    @Test(dependsOnMethods = "loginTest")
    public void partiallyUpdateObject(){
        RestAssured.baseURI = "https://whitesmokehouse.com";
        // Create Update objects request
        String bodyUpdatepartial = "{\n" +
                "    \"name\": \"Perbarui Diana Sebagian\",\n" +
                "    \"data\": {\n" +
                "        \"year\": 2030\n" +
                "    }\n" +
                "}";
                         
        // Send update request to object endpoint
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(bodyUpdatepartial)
                .log().all()
                .when()
                .patch("/webhook/39a0f904-b0f2-4428-80a3-391cea5d7d04/api/object/212");

        System.out.println("Response: " + response.asPrettyString());
        assert response.getStatusCode() == 200 : "Expected status code 200 but got " + response.getStatusCode();

        // Validasi detail properti
        assert response.jsonPath().getString("name").equals("Perbarui Diana Sebagian") 
                : "Expected name is Perbarui Diana Sebagian but got " + response.jsonPath().getString("name");
        assert response.jsonPath().getString("data.year").equals("2025") 
                : "Expected year is 2025 but got " + response.jsonPath().getString("data.year");
    }


     @Test(dependsOnMethods = "loginTest")
     public void deletedObject() {

                // Send DELETE request to object endpoint
                Response response = RestAssured.given()
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + token)
                        .log().all()
                        .when()
                        .delete("/webhook/d79a30ed-1066-48b6-83f5-556120afc46f/api/objects/205");
                // Print the response
                System.out.println("Response: " + response.asPrettyString()); 

                // Validate the response
                Assert.assertEquals(response.getStatusCode(), 200,
                        "Expected status code 200 but got " + response.getStatusCode());
        }

    @Test(dependsOnMethods = "loginTest")
    public void getSingleObject(){
         RestAssured.baseURI = "https://whitesmokehouse.com";
        // Create Get Employee request
        // Send GET request to employee endpoint
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .get("/webhook/8749129e-f5f7-4ae6-9b03-93be7252443c/api/objects/200");
        // Print the response
        System.out.println("Response: " + response.asPrettyString());
        // Validate the response
        // Assert.assertEquals(response.getStatusCode(), 200);
        assert response.getStatusCode() == 200 : "Expected status code 200 but got " + response.getStatusCode();
        assert response.jsonPath().getString("id").equals("200") : "Expected id is 200 but got " + response.jsonPath().getString("id");
        assert response.jsonPath().getString("name").equals("Apple MacBook Pro 16 Diana Punya") : "Expected Name is Apple MacBook Pro 16 Diana Punya but got " + response.jsonPath().getString("name");        
        assert response.jsonPath().getString("data.year").equals("2025") : "Expected Year is 2025 but got " + response.jsonPath().getString("data.year");       
        assert response.jsonPath().getString("data.price").equals("1849.99") : "Expected price is 1849.99 but got " + response.jsonPath().getString("data.price");       
        assert response.jsonPath().getString("data.cpu_model").equals("Intel Core i9") : "Expected cpu_model is Intel Core i9 but got " + response.jsonPath().getString("data.cpu_model");
        assert response.jsonPath().getString("data.hard_disk_size").equals("1 TB") : "Expected hard_disk_size is 1 TB but got " + response.jsonPath().getString("data.hard_disk_size");
        assert response.jsonPath().getString("data.color").equals("red") : "Expected color is red but got " + response.jsonPath().getString("data.color");
        assert response.jsonPath().getString("data.capacity").equals("2") : "Expected capacity is 2 but got " + response.jsonPath().getString("data.capacity");
        assert response.jsonPath().getString("data.screen_size").equals("14") : "Expected screen_size is 2 but got " + response.jsonPath().getString("data.screen_size");
    }

    @Test(dependsOnMethods = "loginTest")
        public void getListAllObjects() {
        RestAssured.baseURI = "https://whitesmokehouse.com";

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .get("/webhook/api/objects");

        // Cek status code
        assert response.getStatusCode() == 200 : "Expected status code 200 but got " + response.getStatusCode();

        // Ambil seluruh objek sebagai List of Map
        List<Map<String, Object>> objects = response.jsonPath().getList("");

        // Validasi bahwa list tidak kosong
        assertThat("Object list should not be empty", objects, is(not(empty())));

        for (Map<String, Object> obj : objects) {
            // Validasi field utama
            assertThat(obj, hasKey("id"));
            assertThat(obj.get("id"), instanceOf(Integer.class));

            assertThat(obj, hasKey("name"));
            assertThat(obj.get("name"), instanceOf(String.class));
            assertThat(((String) obj.get("name")).length(), greaterThan(0));

            // Validasi data nested
            Map<String, Object> data = (Map<String, Object>) obj.get("data");

            assertThat("Data field should not be null", data, is(notNullValue()));

            // Validasi fields di dalam "data"
            assertThat(data, hasKey("year"));
            assertThat(data.get("year").toString(), matchesPattern("\\d{4}")); // Harus 4 digit tahun

            assertThat(data, hasKey("price"));
            assertThat(data.get("price").toString(), matchesPattern("^[\\d.]+$")); // Validasi format harga

            assertThat(data, hasKey("capacity"));
            assertThat(Integer.parseInt(data.get("capacity").toString()), greaterThanOrEqualTo(1));

            assertThat(data, hasKey("screen_size"));
            assertThat(data.get("screen_size"), instanceOf(Number.class));
        }

        System.out.println("Dynamic validation completed for " + objects.size() + " items.");
    }


    @Test(dependsOnMethods = "loginTest")
        public void getAllDepartement() {
        RestAssured.baseURI = "https://whitesmokehouse.com";

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .log().all()
                .when()
                .get("/webhook/api/department");

        // Cek status code
        assert response.getStatusCode() == 200 : "Expected status code 200 but got " + response.getStatusCode();

         // Validasi detail properti
        assert response.jsonPath().getString("[0].id").equals("1") 
                : "Expected id is 1 but got " + response.jsonPath().getString("[0].id");
        assert response.jsonPath().getString("[0].department").equals("Technology") 
                : "Expected department is Technology but got " + response.jsonPath().getString("[0].department");
        assert response.jsonPath().getString("[1].id").equals("2") 
                : "Expected id is 2 Updated but got " + response.jsonPath().getString("[1].id");
        assert response.jsonPath().getString("[1].department").equals("Human Resource") 
                : "Expected department is Human Resource but got " + response.jsonPath().getString("[1].department");
        assert response.jsonPath().getString("[2].id").equals("3") 
                : "Expected id is 3 but got " + response.jsonPath().getString("[2].id");
        assert response.jsonPath().getString("[2].department").equals("Finance") 
                : "Expected department is Finance but got " + response.jsonPath().getString("[2].department");

    }


}
