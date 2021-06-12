package ch.unige;

import io.quarkus.test.junit.QuarkusTest;

import ch.unige.dao.LobbyDB;
import ch.unige.dao.UserDB;
import ch.unige.dao.UserInLobbyDB;
import ch.unige.domain.LobbyTable;
import ch.unige.domain.UserInLobbyTable;
import ch.unige.domain.UserTable;
import io.quarkus.test.junit.QuarkusTest;
import junit.framework.TestCase;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

@QuarkusTest
class UserInLobbyDBTest extends TestCase{

	@Inject
    private UserDB userDB;
    
    @Inject
    private LobbyDB lobbyDB;
    
    @Inject 
    private UserInLobbyDB userLobbyDB;
	 
    @Test
    void TestUserInLobbyDB() {
    	int init_Size = userLobbyDB.getUserInLobbyDBSize();
    	
    	UserTable Owner = userDB.add_user("OwnerID_userInLobbyDB", "OwnerUsername");
		
	    LobbyTable lobby = lobbyDB.add_lobby(Owner.getUserID());
		
	    UserInLobbyTable ownerInLobby = userLobbyDB.addUserInLobby(lobby.getToken(), Owner.getUserID());
	    	    
		assertEquals(userLobbyDB.getUserInLobbyDBSize(),init_Size+1);
		assertEquals(userLobbyDB.isUserInLobby(lobby.getToken(), Owner.getUserID()),true);
		assertEquals(userLobbyDB.findUserInLobbyById(Owner.getUserID()).getUserID(), ownerInLobby.getUserID());
    }

}
