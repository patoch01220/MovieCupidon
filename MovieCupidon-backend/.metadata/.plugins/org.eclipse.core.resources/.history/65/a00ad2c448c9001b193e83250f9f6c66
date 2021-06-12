package ch.unige;

import org.junit.jupiter.api.Test;

import ch.unige.dao.LobbyDB;
import ch.unige.dao.UserDB;
import ch.unige.dao.UserInLobbyDB;
import ch.unige.domain.LobbyConfig;
import ch.unige.domain.LobbyTable;
import ch.unige.domain.UserInLobbyTable;
import ch.unige.domain.UserTable;
import io.quarkus.test.junit.QuarkusTest;
import junit.framework.*;


import static io.restassured.RestAssured.given;

import static org.hamcrest.CoreMatchers.is;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.MediaType;

@QuarkusTest
public class LobbyRessourceTest extends TestCase{
	
	@Inject
    private UserDB userDB;
    
    @Inject
    private LobbyDB lobbyDB;
    
    @Inject 
    private UserInLobbyDB userLobbyDB;
	
    @Test
	public void isOwnerTest() {
    	UserTable Owner = userDB.add_user("OwnerID_isOwner", "OwnerUsername");
		
	    LobbyTable lobby = lobbyDB.add_lobby(Owner.getUserID());
		
	    UserInLobbyTable ownerInLobby = userLobbyDB.addUserInLobby(lobby.getToken(), Owner.getUserID());

        // ----------- Teste le fait que la Lobby est bien pleine ----------- // 
        
		given().header("X-User", Owner.getUserID())
			.when().get("/lobby/isOwner")
			.then()
				.statusCode(200)
				.body("isOwner", is(true));
	}
	
	@Test
	public void isOwner_wrongUserID_Test() {
		UserTable Owner = userDB.add_user("OwnerID_isOwner_wrongUserID", "OwnerUsername");
		
	    LobbyTable lobby = lobbyDB.add_lobby(Owner.getUserID());
		
	    UserInLobbyTable ownerInLobby = userLobbyDB.addUserInLobby(lobby.getToken(), Owner.getUserID());

        // ----------- Init des joiners ----------- // 
       
    	UserTable Joiner1 = userDB.add_user("JoinerID1_isOwner_wrongUserID", "JoinerUsername1");
    	
        // ----------- Ajout des Joiners à la Lobby ----------- // 
        
	    UserInLobbyTable joinerInLobby1 = userLobbyDB.addUserInLobby(lobby.getToken(), Joiner1.getUserID());
	     
        // ----------- Teste le fait que la Lobby est bien pleine ----------- // 
        
		given().header("X-User", Joiner1.getUserID())
			.when().get("/lobby/isOwner")
			.then()
				.statusCode(200)
				.body("isOwner", is(false));
	}	
	
	@Test
	public void isOwner_randomID() {
		given().header("X-User", "RandomID")
		.when().get("/lobby/isOwner")
		.then()
			.statusCode(404);
	}
	
	@Test
	public void whoIsTheOwnerTest() {
		UserTable Owner = userDB.add_user("OwnerID_whoIsTheOwner", "OwnerUsername");
		
	    LobbyTable lobby = lobbyDB.add_lobby(Owner.getUserID());
		
	    UserInLobbyTable ownerInLobby = userLobbyDB.addUserInLobby(lobby.getToken(), Owner.getUserID());

        // ----------- Init des joiners ----------- // 
       
    	UserTable Joiner1 = userDB.add_user("JoinerID1_whoIsTheOwner", "JoinerUsername1");
    	
        // ----------- Ajout des Joiners à la Lobby ----------- // 
        
	    UserInLobbyTable joinerInLobby1 = userLobbyDB.addUserInLobby(lobby.getToken(), Joiner1.getUserID());
	     
        // ----------- Teste le fait que la Lobby est bien pleine ----------- // 
        
		given().header("X-User", Joiner1.getUserID())
			.when().get("/lobby/whoIsTheOwner")
			.then()
				.statusCode(200)
				.body(is("{\"ownerID\": \""+Owner.getUserID()+"\"}"));
	}
	
	@Test
	public void whoIsTheOwner_RandomID() {
		given().header("X-User", "RandomID")
			.when().get("/lobby/whoIsTheOwner")
			.then()
				.statusCode(404);
	}
	
	@Test
	public void isEveryoneReadyTest() {
		UserTable Owner = userDB.add_user("OwnerID_isEveryoneReady", "OwnerUsername");
		
	    LobbyTable lobby = lobbyDB.add_lobby(Owner.getUserID());
		
	    UserInLobbyTable ownerInLobby = userLobbyDB.addUserInLobby(lobby.getToken(), Owner.getUserID());

        // ----------- Init des joiners ----------- // 
       
    	UserTable Joiner1 = userDB.add_user("JoinerID1_isEveryoneReady", "JoinerUsername1");
    	UserTable Joiner2 = userDB.add_user("JoinerID2_isEveryoneReady", "JoinerUsername2");
    	UserTable Joiner3 = userDB.add_user("JoinerID3_isEveryoneReady", "JoinerUsername3");
    	UserTable Joiner4 = userDB.add_user("JoinerID4_isEveryoneReady", "JoinerUsername4");

        // ----------- Ajout des Joiners à la Lobby ----------- // 
        
	    UserInLobbyTable joinerInLobby1 = userLobbyDB.addUserInLobby(lobby.getToken(), Joiner1.getUserID());
	    UserInLobbyTable joinerInLobby2 = userLobbyDB.addUserInLobby(lobby.getToken(), Joiner2.getUserID());
	    UserInLobbyTable joinerInLobby3 = userLobbyDB.addUserInLobby(lobby.getToken(), Joiner3.getUserID());
	    UserInLobbyTable joinerInLobby4 = userLobbyDB.addUserInLobby(lobby.getToken(), Joiner4.getUserID());
	     
	    userLobbyDB.toggleReadyStatus(lobby.getToken(), joinerInLobby1.getUserID());
	    userLobbyDB.toggleReadyStatus(lobby.getToken(), joinerInLobby2.getUserID());
	    userLobbyDB.toggleReadyStatus(lobby.getToken(), joinerInLobby3.getUserID());
	    userLobbyDB.toggleReadyStatus(lobby.getToken(), joinerInLobby4.getUserID());

	    given().header("X-User", Owner.getUserID())
			.when().get("/lobby/isEveryoneReady")
			.then()
				.statusCode(200)
				.body("isEveryoneReady", is(true));
	}
	
	@Test
	public void isEveryoneReady_NotEveryoneReady() {
		UserTable Owner = userDB.add_user("OwnerID_isEveryoneReady_NotEveryoneReady", "OwnerUsername");
		
	    LobbyTable lobby = lobbyDB.add_lobby(Owner.getUserID());
		
	    UserInLobbyTable ownerInLobby = userLobbyDB.addUserInLobby(lobby.getToken(), Owner.getUserID());

        // ----------- Init des joiners ----------- // 
       
    	UserTable Joiner1 = userDB.add_user("JoinerID1_isEveryoneReady_NotEveryoneReady", "JoinerUsername1");
    	UserTable Joiner2 = userDB.add_user("JoinerID2_isEveryoneReady_NotEveryoneReady", "JoinerUsername2");
    	UserTable Joiner3 = userDB.add_user("JoinerID3_isEveryoneReady_NotEveryoneReady", "JoinerUsername3");
    	UserTable Joiner4 = userDB.add_user("JoinerID4_isEveryoneReady_NotEveryoneReady", "JoinerUsername4");

        // ----------- Ajout des Joiners à la Lobby ----------- // 
        
	    UserInLobbyTable joinerInLobby1 = userLobbyDB.addUserInLobby(lobby.getToken(), Joiner1.getUserID());
	    UserInLobbyTable joinerInLobby2 = userLobbyDB.addUserInLobby(lobby.getToken(), Joiner2.getUserID());
	    UserInLobbyTable joinerInLobby3 = userLobbyDB.addUserInLobby(lobby.getToken(), Joiner3.getUserID());
	    UserInLobbyTable joinerInLobby4 = userLobbyDB.addUserInLobby(lobby.getToken(), Joiner4.getUserID());
	     
	    userLobbyDB.toggleReadyStatus(lobby.getToken(), joinerInLobby1.getUserID());
	    userLobbyDB.toggleReadyStatus(lobby.getToken(), joinerInLobby2.getUserID());
	    userLobbyDB.toggleReadyStatus(lobby.getToken(), joinerInLobby3.getUserID());

	    given().header("X-User", Owner.getUserID())
			.when().get("/lobby/isEveryoneReady")
			.then()
				.statusCode(200)
				.body("isEveryoneReady", is(false));
	}
	
	@Test
	public void isEveryoneReady_RandomID() {
	    given().header("X-User", "RandomID")
			.when().get("/lobby/isEveryoneReady")
			.then()
				.statusCode(404);
	}
	
	@Test
	public void hasTheGameStartYetTest() {
		UserTable Owner = userDB.add_user("OwnerID_hasTheGameStartYet", "OwnerUsername");
		
	    LobbyTable lobby = lobbyDB.add_lobby(Owner.getUserID());
		
	    UserInLobbyTable ownerInLobby = userLobbyDB.addUserInLobby(lobby.getToken(), Owner.getUserID());

        // ----------- Init des joiners ----------- // 
       
    	UserTable Joiner1 = userDB.add_user("JoinerID1_hasTheGameStartYet", "JoinerUsername1");
    	UserTable Joiner2 = userDB.add_user("JoinerID2_hasTheGameStartYet", "JoinerUsername2");
    	UserTable Joiner3 = userDB.add_user("JoinerID3_hasTheGameStartYet", "JoinerUsername3");
    	UserTable Joiner4 = userDB.add_user("JoinerID4_hasTheGameStartYet", "JoinerUsername4");

        // ----------- Ajout des Joiners à la Lobby ----------- // 
        
	    UserInLobbyTable joinerInLobby1 = userLobbyDB.addUserInLobby(lobby.getToken(), Joiner1.getUserID());
	    UserInLobbyTable joinerInLobby2 = userLobbyDB.addUserInLobby(lobby.getToken(), Joiner2.getUserID());
	    UserInLobbyTable joinerInLobby3 = userLobbyDB.addUserInLobby(lobby.getToken(), Joiner3.getUserID());
	    UserInLobbyTable joinerInLobby4 = userLobbyDB.addUserInLobby(lobby.getToken(), Joiner4.getUserID());
	     
	    userLobbyDB.toggleReadyStatus(lobby.getToken(), joinerInLobby1.getUserID());
	    userLobbyDB.toggleReadyStatus(lobby.getToken(), joinerInLobby2.getUserID());
	    userLobbyDB.toggleReadyStatus(lobby.getToken(), joinerInLobby3.getUserID());
	    userLobbyDB.toggleReadyStatus(lobby.getToken(), joinerInLobby4.getUserID());
	    
	    lobbyDB.setInLobbyStatus(lobby.getToken(), false);

	    given().header("X-User", Owner.getUserID())
			.when().get("/lobby/hasTheGameStartYet")
			.then()
				.statusCode(200)
				.body("hasTheGameStartYet", is(true));
	}
	
	@Test
	public void hasTheGameStartYet_notStartedTest() {
		UserTable Owner = userDB.add_user("OwnerID1_hasTheGameStartYet_not", "OwnerUsername1");
		
	    LobbyTable lobby = lobbyDB.add_lobby(Owner.getUserID());
		
	    UserInLobbyTable ownerInLobby = userLobbyDB.addUserInLobby(lobby.getToken(), Owner.getUserID());

        // ----------- Init des joiners ----------- // 
       
    	UserTable Joiner1 = userDB.add_user("JoinerID1_hasTheGameStartYet_not", "JoinerUsername1");
    	UserTable Joiner2 = userDB.add_user("JoinerID2_hasTheGameStartYet_not", "JoinerUsername2");
    	UserTable Joiner3 = userDB.add_user("JoinerID3_hasTheGameStartYet_not", "JoinerUsername3");
    	UserTable Joiner4 = userDB.add_user("JoinerID4_hasTheGameStartYet_not", "JoinerUsername4");

        // ----------- Ajout des Joiners à la Lobby ----------- // 
        
	    UserInLobbyTable joinerInLobby1 = userLobbyDB.addUserInLobby(lobby.getToken(), Joiner1.getUserID());
	    UserInLobbyTable joinerInLobby2 = userLobbyDB.addUserInLobby(lobby.getToken(), Joiner2.getUserID());
	    UserInLobbyTable joinerInLobby3 = userLobbyDB.addUserInLobby(lobby.getToken(), Joiner3.getUserID());
	    UserInLobbyTable joinerInLobby4 = userLobbyDB.addUserInLobby(lobby.getToken(), Joiner4.getUserID());
	     
	    userLobbyDB.toggleReadyStatus(lobby.getToken(), joinerInLobby1.getUserID());
	    userLobbyDB.toggleReadyStatus(lobby.getToken(), joinerInLobby2.getUserID());
	    userLobbyDB.toggleReadyStatus(lobby.getToken(), joinerInLobby3.getUserID());
	    userLobbyDB.toggleReadyStatus(lobby.getToken(), joinerInLobby4.getUserID());

	    given().header("X-User", Owner.getUserID())
			.when().get("/lobby/hasTheGameStartYet")
			.then()
				.statusCode(200)
				.body("hasTheGameStartYet", is(false));
	}
	
	@Test
	public void hasTheGameStartYet_RandomIDTest() {
	    given().header("X-User", "RandomID")
			.when().get("/lobby/hasTheGameStartYet")
			.then()
				.statusCode(404);
	}
	
	@Test
	public void getLobbyPrefTest() {
		UserTable Owner = userDB.add_user("OwnerID_getLobbyPref", "OwnerUsername");
		
	    LobbyTable lobby = lobbyDB.add_lobby(Owner.getUserID());
		
	    UserInLobbyTable ownerInLobby = userLobbyDB.addUserInLobby(lobby.getToken(), Owner.getUserID());
	    lobbyDB.setInLobbyStatus(lobby.getToken(), false);

	    lobby.setLobbyPref("{\"genreList\" : [\"action\", \"horror\", \"animation\"],\"rangeYear\" : [1900,2021]}");
	    given().header("X-User", Owner.getUserID())
			.when().get("/lobby/getLobbyPref")
			.then()
				.statusCode(200);
	    }
	
	@Test
	public void getLobbyPref_gameHasntStartTest() {
		UserTable Owner = userDB.add_user("OwnerID_getLobbyPref_notStarted", "OwnerUsername");
		
	    LobbyTable lobby = lobbyDB.add_lobby(Owner.getUserID());
		
	    UserInLobbyTable ownerInLobby = userLobbyDB.addUserInLobby(lobby.getToken(), Owner.getUserID());

	    lobby.setLobbyPref("{\"genreList\" : [\"action\", \"horror\", \"animation\"],\"rangeYear\" : [1900,2021]}");

	    given().header("X-User", Owner.getUserID())
			.when().get("/lobby/getLobbyPref")
			.then()
				.statusCode(401);	    	
	}
	
	
	@Test
	public void getLobbyPref_RandomIDTest() {
	    given().header("X-User", "RandomID")
			.when().get("/lobby/getLobbyPref")
			.then()
				.statusCode(404);
	}
	
	@Test
	public void sizeTest() {
		int size = lobbyDB.getlobbyDB_size();
		UserTable Owner = userDB.add_user("OwnerID_size", "OwnerUsername");
		
	    LobbyTable lobby = lobbyDB.add_lobby(Owner.getUserID());
		
	    UserInLobbyTable ownerInLobby = userLobbyDB.addUserInLobby(lobby.getToken(), Owner.getUserID());
	    
	    UserTable Owner2 = userDB.add_user("OwnerID_size2", "OwnerUsername");
		
	    LobbyTable lobby2 = lobbyDB.add_lobby(Owner2.getUserID());
		
	    UserInLobbyTable ownerInLobby2 = userLobbyDB.addUserInLobby(lobby2.getToken(), Owner2.getUserID());
	    
	    String sizeString = ""+(size+2);
	    given().header("X-User", Owner.getUserID())
			.when().get("/lobby/size")
			.then()
				.statusCode(200)
	    		.body(is(sizeString));

	}
	
	@Test
	public void TokkenTest() {
		UserTable Owner = userDB.add_user("OwnerID_size", "OwnerUsername");
		
	    LobbyTable lobby = lobbyDB.add_lobby(Owner.getUserID());
		
	    UserInLobbyTable ownerInLobby = userLobbyDB.addUserInLobby(lobby.getToken(), Owner.getUserID());
	    given().header("X-User", Owner.getUserID())
			.when().get("/lobby/"+lobby.getToken())
			.then()
				.statusCode(200);
	}
	
	@Test
	public void HelloWorldTest() {
		given()
			.when().get("/lobby/helloworld")
			.then()
				.statusCode(200);
	}
	@Test
	public void seeDBTest() {
		List<LobbyTable> db = lobbyDB.getFullDB();
		String dbString = ""+db;
		given()
			.when().get("/lobby/seeDB")
			.then()
				.statusCode(200)
				.body(is(dbString));
	}
}
