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
class JoinLobbyRessourceTest extends TestCase{
	
	@Inject
    private static UserDB userDB;
    
    @Inject
    private static LobbyDB lobbyDB;
    
    @Inject
    private static UserInLobbyDB userLobbyDB;
    
    
    @Test 
	public void validLobby_Test() {
    	UserTable Owner = userDB.add_user("OwnerID1_validLobby", "OwnerUsername");

    	UserTable Joiner1 = userDB.add_user("JoinerID1_validLobby", "JoinerUsername1");
		
	    LobbyTable lobby = lobbyDB.add_lobby(Owner.getUserID());
		
	    UserInLobbyTable ownerInLobby = userLobbyDB.addUserInLobby(lobby.getToken(), Owner.getUserID());
	    	    
		given().formParam("username", Joiner1.getUsername()).formParam("token", lobby.getToken())
			.headers("X-User", Joiner1.getUserID())
			.when().post("/join-lobby/join")
			.then()
				.statusCode(200);
	}
	
	@Test
	public void lobby_WrongToken_Test() {
		given().formParam("username", "username_test").formParam("token", "ABCD")
			.headers("X-User", "UserIDTest")
			.when().post("/join-lobby/join")
			.then()
				.statusCode(404);
	}
	
	
	@Test
	public void lobby_EmptyUsername_Test() {  
		UserTable Owner = userDB.add_user("OwnerID1_emptyUsername", "OwnerUsername");

    	UserTable Joiner1 = userDB.add_user("JoinerID1_emptyUsername", "JoinerUsername1");
		
	    LobbyTable lobby = lobbyDB.add_lobby(Owner.getUserID());
		
	    UserInLobbyTable ownerInLobby = userLobbyDB.addUserInLobby(lobby.getToken(), Owner.getUserID());
	    
		given().formParam("username", "").formParam("token", lobby.getToken())
			.headers("X-User", "UserIDTest")
			.when().post("/join-lobby/join")
			.then()
				.statusCode(400);
	}
	
	@Test
	public void lobby_BlankUsername_Test() {
		UserTable Owner = userDB.add_user("OwnerID1_blankUsername", "OwnerUsername");

    	UserTable Joiner1 = userDB.add_user("JoinerID1_blankUsername", "JoinerUsername1");
		
	    LobbyTable lobby = lobbyDB.add_lobby(Owner.getUserID());
		
	    UserInLobbyTable ownerInLobby = userLobbyDB.addUserInLobby(lobby.getToken(), Owner.getUserID());
	    
		given().formParam("username", " ").formParam("token", lobby.getToken())
			.headers("X-User", "UserIDTest")
			.when().post("/join-lobby/join")
			.then()
				.statusCode(400);
	}
	
	@Test
	public void lobby_WrongUsername_Test() {
		UserTable Owner = userDB.add_user("OwnerID1_wrongUsername", "OwnerUsername");

    	UserTable Joiner1 = userDB.add_user("JoinerID1_wrongUsername", "JoinerUsername1");
		
	    LobbyTable lobby = lobbyDB.add_lobby(Owner.getUserID());
		
	    UserInLobbyTable ownerInLobby = userLobbyDB.addUserInLobby(lobby.getToken(), Owner.getUserID());
	    
		given().formParam("username", "username_test%").formParam("token", lobby.getToken())
			.headers("X-User", "UserIDTest")
			.when().post("/join-lobby/join")
			.then()
				.statusCode(400);
	}
	
	@Test
	public void lobby_LobbyFull_Test() {
		UserTable Owner = userDB.add_user("OwnerID_lobbyfull", "OwnerUsername");

    	UserTable Joiner1 = userDB.add_user("JoinerID1_lobbyfull", "JoinerUsername1");
		
	    LobbyTable lobby = lobbyDB.add_lobby(Owner.getUserID());
		
	    UserInLobbyTable ownerInLobby = userLobbyDB.addUserInLobby(lobby.getToken(), Owner.getUserID());
	    
	    UserTable Joiner2 = userDB.add_user("JoinerID2_lobbyfull", "JoinerUsername2");
	    UserTable Joiner3 = userDB.add_user("JoinerID3_lobbyfull", "JoinerUsername3");
	    UserTable Joiner4 = userDB.add_user("JoinerID4_lobbyfull", "JoinerUsername4");

        // ----------- Ajout des Joiners à la Lobby --------- //
        UserInLobbyTable userInLobbyJoiner1 = userLobbyDB.addUserInLobby(lobby.getToken(), Joiner1.getUserID());
        UserInLobbyTable userInLobbyJoiner2 = userLobbyDB.addUserInLobby(lobby.getToken(), Joiner2.getUserID());
        UserInLobbyTable userInLobbyJoiner3 = userLobbyDB.addUserInLobby(lobby.getToken(), Joiner3.getUserID());
        UserInLobbyTable userInLobbyJoiner4 = userLobbyDB.addUserInLobby(lobby.getToken(), Joiner4.getUserID());
        
        
        // ----------- Teste le fait que la Lobby est bien pleine ----------- // 
        
		given().formParam("username", "joiner5").formParam("token", lobby.getToken())
			.headers("X-User", "UserIDTest")
			.when().post("/join-lobby/join")
			.then()
				.statusCode(401);
	}
	
	@Test
	public void joinLobby_gameAlreadyStarted() {
		UserTable Owner = userDB.add_user("OwnerID_lobbyStart", "OwnerUsername");

    	UserTable Joiner1 = userDB.add_user("JoinerID1_lobbyStart", "JoinerUsername1");
		
	    LobbyTable lobby = lobbyDB.add_lobby(Owner.getUserID());
		
	    UserInLobbyTable ownerInLobby = userLobbyDB.addUserInLobby(lobby.getToken(), Owner.getUserID());
	    
	    lobbyDB.setInLobbyStatus(lobby.getToken(), false);
	    
	    given().formParam("username", Joiner1.getUsername()).formParam("token", lobby.getToken())
		.headers("X-User", Joiner1.getUserID())
		.when().post("/join-lobby/join")
		.then()
			.statusCode(401);
	}
	
	@Test
	public void joinLobby_UserInLobby() {
		UserTable Owner = userDB.add_user("OwnerID_userInLobby", "OwnerUsername");

    	UserTable Joiner1 = userDB.add_user("JoinerID1_userInLobby", "JoinerUsername1");
		
	    LobbyTable lobby = lobbyDB.add_lobby(Owner.getUserID());
		
	    UserInLobbyTable ownerInLobby = userLobbyDB.addUserInLobby(lobby.getToken(), Owner.getUserID());
	    UserInLobbyTable joinerInLobby = userLobbyDB.addUserInLobby(lobby.getToken(), Joiner1.getUserID());

	    UserTable Owner2 = userDB.add_user("OwnerID2_userInLobby", "OwnerUsername2");
		
	    LobbyTable lobby2 = lobbyDB.add_lobby(Owner.getUserID());
	    
	    given().formParam("username", Joiner1.getUsername()).formParam("token", lobby2.getToken())
			.headers("X-User", Joiner1.getUserID())
			.when().post("/join-lobby/join")
			.then()
				.statusCode(401);
	}
}
