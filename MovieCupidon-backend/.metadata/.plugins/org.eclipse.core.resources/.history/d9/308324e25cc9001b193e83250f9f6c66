package ch.unige.dao;

import java.util.List;

import ch.unige.domain.UserInLobbyTable;

public interface UserInLobbyDBInterface {

	int getUserInLobbyDBSize();
	List<UserInLobbyTable> getFullUserInLobbyDB();
	boolean isTherePlaceInLobby(String token);
	boolean isEveryoneReady(String token);
	boolean isUserInLobby(String token, String userID);
	UserInLobbyTable addUserInLobby(String token, String userid);
	UserInLobbyTable findUserInLobbyById(String id);
	public boolean toggleReadyStatus(String token, String userid);
	boolean removeUserFromLobby(String token, String user_id);
	public String getTokenFromUserID(String userid);
	public boolean getReadyStatusFromUserID(String userid);
	public boolean isUserInALobby(String userid);
	String getAllUserInALobby_toString(String token);
	String getAllUserInALobbyUsername_toString(String token);

}
