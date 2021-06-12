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
public class EndGameDeletionTest extends TestCase{

	@Inject
    private UserDB userDB;
    
    @Inject
    private LobbyDB lobbyDB;
    
    @Inject 
    private UserInLobbyDB userLobbyDB;
    
    @Test
    public void endGameDeletion() {
    	UserTable Owner = userDB.add_user("OwnerID_endGame", "OwnerUsername");
		
	    LobbyTable lobby = lobbyDB.add_lobby(Owner.getUserID());
		
	    UserInLobbyTable ownerInLobby = userLobbyDB.addUserInLobby(lobby.getToken(), Owner.getUserID());

        // ----------- Init des joiners ----------- // 
       
    	UserTable Joiner1 = userDB.add_user("JoinerID1_endGame", "JoinerUsername1");
    	
        // ----------- Ajout des Joiners à la Lobby ----------- // 
        
	    UserInLobbyTable joinerInLobby1 = userLobbyDB.addUserInLobby(lobby.getToken(), Joiner1.getUserID());
	     
	    given().header("X-User", Joiner1.getUserID())
		.when().delete("/lobby/endGameDeletion")
		.then()
			.statusCode(200);
    }
    
    @Test
    public void endGameDeletion_owner() {
    	UserTable Owner = userDB.add_user("OwnerID_endGame", "OwnerUsername");
		
	    LobbyTable lobby = lobbyDB.add_lobby(Owner.getUserID());
		
	    UserInLobbyTable ownerInLobby = userLobbyDB.addUserInLobby(lobby.getToken(), Owner.getUserID());

	    given().header("X-User", Owner.getUserID())
		.when().delete("/lobby/endGameDeletion")
		.then()
			.statusCode(200);
	    
    }
    
    @Test
    public void endGameDeletion_RandomID() {
    	given().header("X-User", "RandomID")
		.when().delete("/lobby/endGameDeletion")
		.then()
			.statusCode(404);
    }
    
}
