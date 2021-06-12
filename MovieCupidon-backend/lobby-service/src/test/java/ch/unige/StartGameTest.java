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
import static org.hamcrest.CoreMatchers.is;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

@QuarkusTest
public class StartGameTest extends TestCase{
	
	@Inject
    private UserDB userDB;
    
    @Inject
    private LobbyDB lobbyDB;
    
    @Inject 
    private UserInLobbyDB userLobbyDB;

	@Test
	public void startGame_Test() {
		UserTable Owner = userDB.add_user("OwnerID1_startGame", "OwnerUsername");
		
	    LobbyTable lobby = lobbyDB.add_lobby(Owner.getUserID());
		
	    UserInLobbyTable ownerInLobby = userLobbyDB.addUserInLobby(lobby.getToken(), Owner.getUserID());

        // ----------- Init des joiners ----------- // 
       
    	UserTable Joiner1 = userDB.add_user("JoinerID1_startGame", "JoinerUsername1");
    	UserTable Joiner2 = userDB.add_user("JoinerID2_startGame", "JoinerUsername2");
    	UserTable Joiner3 = userDB.add_user("JoinerID3_startGame", "JoinerUsername3");
    	UserTable Joiner4 = userDB.add_user("JoinerID4_startGame", "JoinerUsername4");

        // ----------- Ajout des Joiners à la Lobby ----------- // 
        
	    UserInLobbyTable joinerInLobby1 = userLobbyDB.addUserInLobby(lobby.getToken(), Joiner1.getUserID());
	    UserInLobbyTable joinerInLobby2 = userLobbyDB.addUserInLobby(lobby.getToken(), Joiner2.getUserID());
	    UserInLobbyTable joinerInLobby3 = userLobbyDB.addUserInLobby(lobby.getToken(), Joiner3.getUserID());
	    UserInLobbyTable joinerInLobby4 = userLobbyDB.addUserInLobby(lobby.getToken(), Joiner4.getUserID());
        
	    userLobbyDB.toggleReadyStatus(lobby.getToken(), joinerInLobby1.getUserID());
	    userLobbyDB.toggleReadyStatus(lobby.getToken(), joinerInLobby2.getUserID());
	    userLobbyDB.toggleReadyStatus(lobby.getToken(), joinerInLobby3.getUserID());
	    userLobbyDB.toggleReadyStatus(lobby.getToken(), joinerInLobby4.getUserID());
	    
	    // Recup du body de retour
	    String msgInit = userLobbyDB.getAllUserInALobbyToString(lobby.getToken());
	    String msgEncrypt = SecurityUtility.encrypt(msgInit);
	    
        // ----------- Test que toutes les conditions sont réunis pour pouvoir lancer la game ----------- //
        given().header("X-User", Owner.getUserID())
        	.body("{\"genreList\" : [\"action\", \"horror\", \"animation\"],\"rangeYear\" : [1900,2021]}")
        	.contentType(MediaType.APPLICATION_JSON)
			.when().post("/lobby/start")
			.then()
				.statusCode(200)
				.body(is(msgEncrypt));
	}
	
	
	@Test
	public void startGame_UserIsNotInAnyLobby() {
		given().header("X-User", "RandomID")
	    	.body("{\"genreList\" : [\"action\", \"horror\", \"animation\"],\"rangeYear\" : [1900,2021]}")
	    	.contentType(MediaType.APPLICATION_JSON)
			.when().post("/lobby/start")
			.then()
				.statusCode(404);
	}
	
	@Test 
	public void startGame_userIsNotTheOwner() {
		UserTable Owner = userDB.add_user("OwnerID1_startGame_isNotTheOwner", "OwnerUsername");
		
	    LobbyTable lobby = lobbyDB.add_lobby(Owner.getUserID());
		
	    UserInLobbyTable ownerInLobby = userLobbyDB.addUserInLobby(lobby.getToken(), Owner.getUserID());

        // ----------- Init des joiners ----------- // 
       
    	UserTable Joiner1 = userDB.add_user("JoinerID1_startGame_isNotTheOwner", "JoinerUsername1");

        // ----------- Ajout des Joiners à la Lobby ----------- // 
        
	    UserInLobbyTable joinerInLobby1 = userLobbyDB.addUserInLobby(lobby.getToken(), Joiner1.getUserID());
        
	    userLobbyDB.toggleReadyStatus(lobby.getToken(), joinerInLobby1.getUserID());

        // ----------- Test que toutes les conditions sont réunis pour pouvoir lancer la game ----------- //
        given().header("X-User", Joiner1.getUserID())
        	.body("{\"genreList\" : [\"action\", \"horror\", \"animation\"],\"rangeYear\" : [1900,2021]}")
        	.contentType(MediaType.APPLICATION_JSON)
			.when().post("/lobby/start")
			.then()
				.statusCode(409);
	}
	
	@Test 
	public void startGame_NotEveryoneReady() {
		UserTable Owner = userDB.add_user("OwnerID1_startGame_notEveryoneReady", "OwnerUsername");
		
	    LobbyTable lobby = lobbyDB.add_lobby(Owner.getUserID());
		
	    UserInLobbyTable ownerInLobby = userLobbyDB.addUserInLobby(lobby.getToken(), Owner.getUserID());

        // ----------- Init des joiners ----------- // 
       
    	UserTable Joiner1 = userDB.add_user("JoinerID1_notEveryoneReady", "JoinerUsername1");

        // ----------- Ajout des Joiners à la Lobby ----------- // 
        
	    UserInLobbyTable joinerInLobby1 = userLobbyDB.addUserInLobby(lobby.getToken(), Joiner1.getUserID());
        
        // ----------- Test que toutes les conditions sont réunis pour pouvoir lancer la game ----------- //
        given().header("X-User", Owner.getUserID())
        	.body("{\"genreList\" : [\"action\", \"horror\", \"animation\"],\"rangeYear\" : [1900,2021]}")
        	.contentType(MediaType.APPLICATION_JSON)
			.when().post("/lobby/start")
			.then()
				.statusCode(409);
	}
	
	@Test
	public void startGame_tooManyMovieGender() {
		UserTable Owner = userDB.add_user("OwnerID1_startGame_tooManyMovieGender", "OwnerUsername");
		
	    LobbyTable lobby = lobbyDB.add_lobby(Owner.getUserID());
		
	    UserInLobbyTable ownerInLobby = userLobbyDB.addUserInLobby(lobby.getToken(), Owner.getUserID());

        // ----------- Test que toutes les conditions sont réunis pour pouvoir lancer la game ----------- //
        given().header("X-User", Owner.getUserID())
        	.body("{\"genreList\" : [\"action\", \"aventure\", \"horror\", \"animation\"],\"rangeYear\" : [1900,2021]}")
        	.contentType(MediaType.APPLICATION_JSON)
			.when().post("/lobby/start")
			.then()
				.statusCode(400);
	}
	
	@Test
	public void startGame_tooFewMovieGender() {
		UserTable Owner = userDB.add_user("OwnerID1_startGame_tooFewMovieGender", "OwnerUsername");
		
	    LobbyTable lobby = lobbyDB.add_lobby(Owner.getUserID());
		
	    UserInLobbyTable ownerInLobby = userLobbyDB.addUserInLobby(lobby.getToken(), Owner.getUserID());

        // ----------- Test que toutes les conditions sont réunis pour pouvoir lancer la game ----------- //
        given().header("X-User", Owner.getUserID())
        	.body("{\"genreList\" : [],\"rangeYear\" : [1900,2021]}")
        	.contentType(MediaType.APPLICATION_JSON)
			.when().post("/lobby/start")
			.then()
				.statusCode(400);
	}
	
}
