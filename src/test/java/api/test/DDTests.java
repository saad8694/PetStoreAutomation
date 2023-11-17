package api.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.Dataproviders;
import io.restassured.response.Response;

public class DDTests {

	@Test(priority = 1, dataProvider = "Data", dataProviderClass = Dataproviders.class)
	public void testPostUser(String userID, String userName, String fname, String lname, String userEmail, String pwd, String ph) {
		
		User userPayload = new User();
		
		userPayload.setId(Integer.parseInt(userID));
		userPayload.setUsername(userName);
		userPayload.setFirstName(fname);
		userPayload.setLastName(lname);
		userPayload.setEmail(userEmail);
		userPayload.setPassword(pwd);
		userPayload.setPhone(ph);
		
		Response repsonse  = UserEndPoints.createUser(userPayload);
		Assert.assertEquals(repsonse.getStatusCode(), 200);
	}
	
	@Test(priority = 2, dataProvider = "UserNames", dataProviderClass = Dataproviders.class)
	public void testDeleteUser(String userName) {
		
		Response repsonse  = UserEndPoints.deleteUser(userName);
		Assert.assertEquals(repsonse.getStatusCode(), 200);
	}
}
