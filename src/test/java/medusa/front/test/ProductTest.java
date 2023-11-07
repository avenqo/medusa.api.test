package medusa.front.test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ProductTest {

	@BeforeClass
	public static void init() {
		RestAssured.baseURI = "http://localhost:9000/store/";
	}
	
	
	
	@Test
	public void getProducts() {
		 
		RestAssured
        	.given()
		        .contentType(ContentType.JSON)
		        .param("q", "Mug")
		        .log().all()

	        .when()
	        	.get("products")

	        .then()
	        	.assertThat().statusCode(200)
	        	.log().all()
	        	.body("products", notNullValue())
	        	.body("products.size()", equalTo(1))
	        	.body("products[0].title", containsString("Mug"))
	        	.body("products[0].title", equalTo("Medusa Coffee Mug"))
	        	.body("products[0].variants.size()", greaterThanOrEqualTo(1))
	        	.body("products[0].variants[0].tax_rates", nullValue());

	}
}
