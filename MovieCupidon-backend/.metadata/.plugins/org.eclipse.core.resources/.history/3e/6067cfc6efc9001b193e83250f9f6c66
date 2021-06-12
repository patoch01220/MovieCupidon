package ch.unige;

import javax.inject.Inject;

import ch.unige.dao.LobbyDB;
import ch.unige.dao.UserDB;
import ch.unige.dao.UserInLobbyDB;
import ch.unige.domain.LobbyTable;
import ch.unige.domain.UserInLobbyTable;
import ch.unige.domain.UserTable;
import io.quarkus.test.junit.QuarkusTest;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class LobbyDBTest extends TestCase{

	@Inject
    private UserDB userDB;
    
    @Inject
    private LobbyDB lobbyDB;
    
    @Inject 
    private UserInLobbyDB userLobbyDB;
	 
	@Test
	public void TestlobbyDB() {
		int LobbyDBSizeInit = lobbyDB.getlobbyDB_size();
		UserTable Owner = userDB.add_user("OwnerID_isOwner", "OwnerUsername");
			
		LobbyTable lobby = lobbyDB.add_lobby(Owner.getUserID());
			
		UserInLobbyTable ownerInLobby = userLobbyDB.addUserInLobby(lobby.getToken(), Owner.getUserID());
				
		assertEquals(lobbyDB.lobbySize(lobby.getToken()),5);
		assertEquals(lobbyDB.getlobbyDB_size(),LobbyDBSizeInit+1);
		assertEquals(lobbyDB.getInLobbyStatus(lobby.getToken()),true);

		

	}
}
