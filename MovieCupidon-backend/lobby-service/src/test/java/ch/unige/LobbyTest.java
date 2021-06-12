package ch.unige;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import ch.unige.dao.LobbyDB;
import ch.unige.domain.LobbyTable;
import io.quarkus.test.junit.QuarkusTest;
import junit.framework.*;

@QuarkusTest
public class LobbyTest extends TestCase{
	
	@Inject
    private LobbyDB lobbyDB;
	
	@Test
	public void createLobbyTest() {
		LobbyTable lobby = lobbyDB.add_lobby("OwnerID");
		lobby.setToken("tokenTest");
		
		assertEquals(lobby.getOwnerID(), "OwnerID");
		assertEquals(lobby.getInLobbyStatus(), true);
		assertEquals(lobby.getNbMax(), 5);
		assertEquals(lobby.getToken(), "tokenTest");
		assertEquals(lobby.toString(), "ownerID : OwnerID token: tokenTest");

		
		lobby.setOwnerID("newOwnerID");
		lobby.setNbMax(6);
		lobby.setInLobbyStatus(false);
		lobby.setLobbyPref("testLobbyPref");
		assertEquals(lobby.getOwnerID(), "newOwnerID");
		assertEquals(lobby.getNbMax(), 6);
		assertEquals(lobby.getInLobbyStatus(), false);
		assertEquals(lobby.getLobbyPref(), "testLobbyPref");
	}
	
}
