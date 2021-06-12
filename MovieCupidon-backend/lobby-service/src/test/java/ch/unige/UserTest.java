package ch.unige;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import ch.unige.dao.UserDB;
import ch.unige.domain.UserTable;
import io.quarkus.test.junit.QuarkusTest;
import junit.framework.*;

@QuarkusTest
public class UserTest extends TestCase{
	
	@Inject
    private UserDB userDB;
	
	@Test
	public void createNormalUserTest() {
		UserTable user = userDB.add_user("userID", "username");
		assertEquals(user.getUserID(), "userID");
		assertEquals(user.getUsername(), "username");
		assertEquals(user.toString(), "Username: username , userId:userID");

		
		user.setUserID("newUserID");
		user.setUsername("newUsername");

		assertEquals(user.getUserID(), "newUserID");
		assertEquals(user.getUsername(), "newUsername");
		
		assertEquals(user.validUsername(""), false);
		assertEquals(user.validUsername(" "), false);
		assertEquals(user.validUsername("ajsd%"), false);
	}
	
}
