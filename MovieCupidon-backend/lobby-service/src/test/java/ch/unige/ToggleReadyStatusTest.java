package ch.unige;


import ch.unige.dao.LobbyDB;
import ch.unige.dao.UserDB;
import ch.unige.dao.UserInLobbyDB;
import ch.unige.domain.LobbyTable;
import ch.unige.domain.UserInLobbyTable;
import ch.unige.domain.UserTable;
import io.quarkus.test.junit.QuarkusTest;
import junit.framework.*;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;


import javax.inject.Inject;

@QuarkusTest
public class ToggleReadyStatusTest extends TestCase{
	
	@Inject
    private UserDB userDB;
    
    @Inject
    private LobbyDB lobbyDB;
    
    @Inject 
    private UserInLobbyDB userLobbyDB;

	@Test
	public void togglingReadyStatusProg() {

		UserTable Owner = userDB.add_user("OwnerID1_togglingReady", "OwnerUsername");

    	UserTable Joiner1 = userDB.add_user("JoinerID1_togglingReady", "JoinerUsername1");
		
	    LobbyTable lobby = lobbyDB.add_lobby(Owner.getUserID());
		
	    UserInLobbyTable ownerInLobby = userLobbyDB.addUserInLobby(lobby.getToken(), Owner.getUserID());
	    UserInLobbyTable joinerInLobby1 = userLobbyDB.addUserInLobby(lobby.getToken(), Joiner1.getUserID());

	    given().headers("X-User", Joiner1.getUserID())
			.when().post("/lobby/toggleready")
			.then()
				.statusCode(200)
	    		.body("Status", is(true));
	}
	
	@Test
	public void ToggleReady_userIsNotInAnyLobbyTest() {
		given().headers("X-User", "RandomID")
			.when()
			.post("/lobby/toggleready")
			.then()
				.statusCode(404);
	}
}
