package api.test;

import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.endpoints.UserEndPoints2;
import api.payload.User;
import groovyjarjarantlr4.v4.runtime.misc.LogManager;
import io.restassured.response.Response;

public class UserTests2 {

	Faker faker;
	User userPayload;
	 private String environment;

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
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());

		// logs

		logger = org.apache.logging.log4j.LogManager.getLogger(this.getClass());

	}
	
	

	    @BeforeClass
	    @Parameters("env")
	    public void setUp(String env) {
	        this.environment = env;
	        // Use the 'environment' variable in your setup logic
	    }

	@Test(priority = 1)
	public void testPostUser() {

		logger.info("***** Creating User ******");

		Response response = UserEndPoints2.createUser(userPayload);
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200);
		
		// Assert additional details in the response body
        int code = response.jsonPath().getInt("code");
        String type = response.jsonPath().getString("type");
        //String message = response.jsonPath().getString("message");

        int idFromPayload = userPayload.getId();
        String messageFromResponse = response.jsonPath().getString("message");
        
        Assert.assertEquals(code, 200, "Incorrect status code");
        Assert.assertEquals(type, "unknown", "Incorrect type");
        Assert.assertEquals(messageFromResponse, String.valueOf(idFromPayload), "Incorrect user ID in the response");


		logger.info("***** User is Created ********");
	}

	@Test(priority = 2)
	public void testGetUserByName() {

		logger.info("***** Reading User Info ******");

		Response response = UserEndPoints2.readUser(this.userPayload.getUsername());
		response.then().log().all();

		Assert.assertEquals(response.getStatusCode(), 200);
		
		 // Assert additional details in the response body
        int idFromResponse = response.jsonPath().getInt("id");
        String usernameFromResponse = response.jsonPath().getString("username");
        String firstNameFromResponse = response.jsonPath().getString("firstName");
        String lastNameFromResponse = response.jsonPath().getString("lastName");
        String emailFromResponse = response.jsonPath().getString("email");
        String phoneFromResponse = response.jsonPath().getString("phone");
        int userStatusFromResponse = response.jsonPath().getInt("userStatus");

        // Add assertions for each field
        Assert.assertNotNull(idFromResponse, "User ID is null");
        Assert.assertTrue(idFromResponse > 0, "User ID is not greater than 0");
        Assert.assertEquals(usernameFromResponse, userPayload.getUsername(), "Incorrect username");
        Assert.assertEquals(firstNameFromResponse, userPayload.getFirstName(), "Incorrect first name");
        Assert.assertEquals(lastNameFromResponse, userPayload.getLastName(), "Incorrect last name");
        Assert.assertEquals(emailFromResponse, userPayload.getEmail(), "Incorrect email");
        Assert.assertEquals(phoneFromResponse, userPayload.getPhone(), "Incorrect phone");
        Assert.assertEquals(userStatusFromResponse, 0, "Incorrect user status");

		logger.info("***** User info is displayed ******");
	}

	@Test(priority = 3)
	public void testUpdateUserByName() {

		logger.info("***** Updating User Info ******");

		// Update data using payload

		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());

		Response response = UserEndPoints2.updateUser(this.userPayload.getUsername(), userPayload);
		response.then().log().body();

		Assert.assertEquals(response.getStatusCode(), 200);

		// Checking data after update

		Response responseAfterUpdate = UserEndPoints2.readUser(this.userPayload.getUsername());
		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);

		 // Assert updated data
        String firstNameAfterUpdate = responseAfterUpdate.jsonPath().getString("firstName");
        String lastNameAfterUpdate = responseAfterUpdate.jsonPath().getString("lastName");
        String emailAfterUpdate = responseAfterUpdate.jsonPath().getString("email");

        Assert.assertEquals(firstNameAfterUpdate, userPayload.getFirstName(), "Incorrect first name after update");
        Assert.assertEquals(lastNameAfterUpdate, userPayload.getLastName(), "Incorrect last name after update");
        Assert.assertEquals(emailAfterUpdate, userPayload.getEmail(), "Incorrect email after update");

		
		logger.info("***** User Updated ******");
	}

	@Test(priority = 4)
	public void testDeleteUser() {

		logger.info("***** Deleting User ******");

		Response response = UserEndPoints2.deleteUser(this.userPayload.getUsername());
		response.then().statusCode(200);

		 // Verify that the user does not exist after deletion
        Response responseAfterDelete = UserEndPoints2.readUser(this.userPayload.getUsername());
        Assert.assertEquals(responseAfterDelete.getStatusCode(), 404, "User still exists after deletion");
		
		logger.info("***** User Deleted ******");
	}
}
