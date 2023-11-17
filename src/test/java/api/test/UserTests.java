package api.test;

import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import groovyjarjarantlr4.v4.runtime.misc.LogManager;
import io.restassured.response.Response;

public class UserTests {
	
	Faker faker;
	User userPayload;
	
	public Logger logger;
	
	@BeforeClass
	public void setup() {
		
		faker = new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5 , 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//logs
		
		logger = org.apache.logging.log4j.LogManager.getLogger(this.getClass());
		
		
	}
	
	@Test(priority = 1)
	public void testPostUser() {
		
		logger.info("***** Creating User ******");
		
		Response response = UserEndPoints.createUser(userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);

		logger.info("***** User is Created ********");
	}
	
	@Test(priority = 2)
	public void testGetUserByName() {
		
		logger.info("***** Reading User Info ******");
		
		Response response = UserEndPoints.readUser(this.userPayload.getUsername());
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("***** User info is displayed ******");
	}
	
	@Test(priority = 3)
	public void testUpdateUserByName() {
		
		logger.info("***** Updating User Info ******");
		
		//Update data using payload
		
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		
		Response response = UserEndPoints.updateUser(this.userPayload.getUsername(), userPayload);
		response.then().log().body();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//Checking data after update
		
		Response responseAfterUpdate = UserEndPoints.readUser(this.userPayload.getUsername());		
		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
		
		logger.info("***** User Updated ******");
	}
	
	@Test(priority = 4)
	public void testDeleteUser() {
	
		logger.info("***** Deleting User ******");
		
		Response response = UserEndPoints.deleteUser(this.userPayload.getUsername());
		response.then().statusCode(200);
		
		logger.info("***** User Deleted ******");
	}
}
