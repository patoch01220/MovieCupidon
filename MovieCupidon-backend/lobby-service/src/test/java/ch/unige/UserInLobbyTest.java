package ch.unige;

import javax.inject.Inject;
import org.junit.jupiter.api.Test;

import ch.unige.dao.UserDB;
import ch.unige.dao.UserInLobbyDB;
import ch.unige.domain.UserInLobbyTable;
import io.quarkus.test.junit.QuarkusTest;
import junit.framework.TestCase;

@QuarkusTest
public class UserInLobbyTest  extends TestCase{
	
	@Inject
    private UserInLobbyDB userInLobbyDB;
	
	@Test
	public void userInLobbyTest() {
		UserInLobbyTable userLobby = userInLobbyDB.addUserInLobby("token", "userID");
		assertEquals(userLobby.getUserID(), "userID");
		assertEquals(userLobby.getToken(), "token");
		assertEquals(userLobby.getReadyStatus(), false);
		assertEquals(userLobby.toString(), "User: userID , lobbyId:token , Ready Status: false");

		userLobby.setReadyStatus(true);
		userLobby.setToken("newToken");
		userLobby.setUserID("newUserID");
		
		assertEquals(userLobby.getUserID(), "newUserID");
		assertEquals(userLobby.getToken(), "newToken");
		assertEquals(userLobby.getReadyStatus(), true);
	}
}
