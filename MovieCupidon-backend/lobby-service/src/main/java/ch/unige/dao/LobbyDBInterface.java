package ch.unige.dao;

import java.util.List;

import ch.unige.domain.LobbyConfig;
import ch.unige.domain.LobbyTable;

public interface LobbyDBInterface {
	
	int getlobbyDB_size();
	List<LobbyTable> getFullDB();
    boolean lobbyExist(String token);
	int lobbySize(String token) ;
	LobbyTable add_lobby(String ownerid);
	boolean hasItStart(String token);
	boolean isHeTheOwner(String token, String userid);
	void addLobbyPref(String token, LobbyConfig config);
	public void setInLobbyStatus(String token, boolean newStatus);
	public String getLobbyPreferences(String token);
	public String getOwnerID(String token);
	boolean getInLobbyStatus(String token);
	boolean removeLobby(String token);	
}
