package ch.unige;

import org.junit.jupiter.api.Test;

import ch.unige.dao.LobbyDB;
import ch.unige.dao.UserDB;
import ch.unige.dao.UserInLobbyDB;
import ch.unige.domain.LobbyTable;
import ch.unige.domain.UserInLobbyTable;
import ch.unige.domain.UserTable;
import io.quarkus.test.junit.QuarkusTest;
import junit.framework.*;

import static io.restassured.RestAssured.given;

import javax.inject.Inject;

@QuarkusTest
public class CreateLobbyRessourceTest extends TestCase{

	@Inject
    private UserDB userDB;
	
	@Inject
    private LobbyDB lobbyDB;
	
	@Inject 
    private UserInLobbyDB userLobbyDB;
	
	@Test
	public void createValidLobbyTest() {
		given().formParam("username", "OwnerUsername").header("X-User", "OwnerID_valid")
			.when().post("/create-lobby/new-lobby")
			.then()
				.statusCode(200);
	}
	
	@Test
	public void createNonValidLobby_EmptyUsername_Test() {
		given().formParam("username", "").header("X-User", "OwnerID")
			.when().post("/create-lobby/new-lobby")
			.then()
				.statusCode(400);
	}
	
	@Test
	public void createNonValidLobby_BlankUsername_Test() {
		given().formParam("username", " ").header("X-User", "OwnerID")
			.when().post("/create-lobby/new-lobby")
			.then()
				.statusCode(400);
	}
	
	@Test
	public void createNonValidLobby_WrongUsername_Test() {
		given().formParam("username", "username_test%").header("X-User", "OwnerID")
			.when().post("/create-lobby/new-lobby")
			.then()
				.statusCode(400);
	}
	
	@Test
	public void createUserAlreadyInALobby() {
		UserTable Owner = userDB.add_user("OwnerID_alreadyInLobby", "OwnerUsername");
		
	    LobbyTable lobby = lobbyDB.add_lobby(Owner.getUserID());
		
	    UserInLobbyTable ownerInLobby = userLobbyDB.addUserInLobby(lobby.getToken(), Owner.getUserID());

	    given().formParam("username", "OwnerUsername").header("X-User", Owner.getUserID())
			.when().post("/create-lobby/new-lobby")
			.then()
				.statusCode(401);
	}
}
