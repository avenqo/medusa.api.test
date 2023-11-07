package medusa.store.back;

import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;

public class OrdersTest {

	private static Cookie myCookie;

	@BeforeClass
	public static void init() {
		RestAssured.baseURI = "http://localhost:9000/";
		
		JSONObject data = new JSONObject(); 
		data.put("password", "supersecret");
		data.put("email", "admin@medusa-test.com"); 
		
		String cookie = RestAssured
    	.given()
	        .contentType(ContentType.JSON)
	        .body(data.toString())
	        
	        .log().all()

        .when()
        	.post("admin/auth")
        
        .then()
        	.log().all()
        	
        	.assertThat()
        	.statusCode(200)
        	.extract().cookie("connect.sid");
		
		System.out.println("Cookie '" + cookie + "'");
		
		myCookie = new Cookie.Builder("connect.sid", cookie)
			      .setSecured(true)
			      .setComment("Medusa admin cookie")
			      .build();
	}
	
	@Test
	public void getOrders() {
		
		// http://localhost:9000/admin/orders?expand=customer%2Cshipping_address&fields=id%2Cstatus%2Cdisplay_id%2Ccreated_at%2Cemail%2Cfulfillment_status%2Cpayment_status%2Ctotal%2Ccurrency_code&offset=0&limit=15
		RestAssured
				.given()
			        .contentType(ContentType.JSON)
			        .cookie(myCookie)
			        //.param(null, null)
			        
			        .log().all()

		        .when()
		        	.get("admin/orders")
		        
		        .then()
		        	.log().all()
		        	.assertThat()
		        	.statusCode(200);
		        	
	}
	
	@Test
	public void login() {
		
	}
}
