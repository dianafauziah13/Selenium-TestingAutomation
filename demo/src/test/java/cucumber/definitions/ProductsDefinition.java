package cucumber.definitions;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import selenium.model.ResponseProducts;

import java.util.List;

public class ProductsDefinition {

    String tokenLogin, idProduct;

    @Given("I am logged in with valid credentials")
    public void i_am_logged_in_with_valid_credentials() {
        RestAssured.baseURI = "https://whitesmokehouse.com";

        String jsonLogin = """
            {
              "email": "dianafauziah.dev@gmail.com",
              "password": "@dmin123"
            }
            """;

        Response responseLogin = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(jsonLogin)
                .post("/webhook/api/login");

        Assert.assertEquals(responseLogin.getStatusCode(), 200);
        tokenLogin = responseLogin.jsonPath().getString("token");
    }

    @When("I add a product with valid data")
    public void i_add_a_product_with_valid_data() {
        String requestBody = """
            {
              "name": "Apple MacBook 15 Diana Fauziah Punya banget",
              "data": {
                "year": 2027,
                "price": 53456,
                "cpu_model": "Intel Core i9",
                "hard_disk_size": "2 TB",
                "capacity": "5",
                "screen_size": "15",
                "color": "red"
              }
            }
            """;

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + tokenLogin)
                .body(requestBody)
                .post("/webhook/api/objects");

        Assert.assertEquals(response.getStatusCode(), 200);
        List<Integer> ids = response.jsonPath().getList("id");
        idProduct = ids.get(0).toString();
    }

    @Then("The product should be created successfully")
    public void the_product_should_be_created_successfully() {
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + tokenLogin)
                .get("/webhook/8749129e-f5f7-4ae6-9b03-93be7252443c/api/objects/" + idProduct);

        ResponseProducts product = response.as(ResponseProducts.class);
        Assert.assertEquals(product.name, "Apple MacBook 15 Diana Fauziah Punya banget");
    }

    @And("I should be able to retrieve the product with correct details")
    public void i_should_be_able_to_retrieve_the_product() {
        // already covered above
    }

    @And("A product exists")
    public void a_product_exists() {
        i_add_a_product_with_valid_data();
    }

    @When("I update the product with new data")
    public void i_update_the_product_with_new_data() {
        String bodyUpdate = """
            {
              "name": "Apple MacBook 15 Diana Fauziah Punya",
              "data": {
                "year": 2027,
                "price": 53456,
                "cpu_model": "Intel Core i9",
                "hard_disk_size": "2 TB",
                "capacity": "5",
                "screen_size": "15",
                "color": "red"
              }
            }
            """;

        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + tokenLogin)
                .body(bodyUpdate)
                .put("/webhook/37777abe-a5ef-4570-a383-c99b5f5f7906/api/objects/" + idProduct);

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Then("The product should be updated successfully")
    public void the_product_should_be_updated_successfully() {
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + tokenLogin)
                .get("/webhook/8749129e-f5f7-4ae6-9b03-93be7252443c/api/objects/" + idProduct);

        ResponseProducts product = response.as(ResponseProducts.class);
        Assert.assertEquals(product.name, "Apple MacBook 15 Diana Fauziah Punya");
    }

    @When("I delete the product")
    public void i_delete_the_product() {
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + tokenLogin)
                .delete("/webhook/d79a30ed-1066-48b6-83f5-556120afc46f/api/objects/" + idProduct);

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Then("The product should not be retrievable")
    public void the_product_should_not_be_retrievable() {
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + tokenLogin)
                .get("/webhook/8749129e-f5f7-4ae6-9b03-93be7252443c/api/objects/" + idProduct);

        ResponseProducts product = response.as(ResponseProducts.class);
        Assert.assertNull(product.name);
    }
}
