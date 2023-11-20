package api.endpoints;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.ResourceBundle;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
//UserEndpoints
//Created for crud operations

public class UserEndPoints2 {
	
//	static ResourceBundle getURL(){
//		
//		ResourceBundle routes = ResourceBundle.getBundle("routes"); //load properties file
//		return routes;
//	}

	public static Response createUser(User payload) {
		
		//String post_url = getURL().getString("post_url");
		 String postUrl = AppConfig.getBaseUrl() + "/user";

		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON).body(payload)

				//.when().post(post_url);
				.when().post(postUrl);

		return response;
	}

	public static Response readUser(String userName) {
		
		//String get_url = getURL().getString("get_url");
		String getUrl = AppConfig.getBaseUrl() + "/user/{username}";

		Response response = given().pathParam("username", userName)

				//.when().get(get_url);
				.when().get(getUrl);

		return response;
	}

	public static Response updateUser(String userName, User payload) {
		
		//String update_url = getURL().getString("update_url");
		String updateUrl = AppConfig.getBaseUrl() + "/user/{username}";

		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.pathParam("username", userName).body(payload)

			//	.when().put(update_url);
				.when().put(updateUrl);

		return response;
	}

	public static Response deleteUser(String userName) {

		//String delete_url = getURL().getString("delete_url");
		String deleteUrl = AppConfig.getBaseUrl() + "/user/{username}";
		
		Response response = given().pathParam("username", userName)

			//	.when().delete(delete_url);
				.when().delete(deleteUrl);

		return response;
	}
}
