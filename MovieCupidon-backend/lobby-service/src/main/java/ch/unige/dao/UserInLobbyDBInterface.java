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
	boolean toggleReadyStatus(String token, String userid);
	boolean removeUserFromLobby(String token, String user_id);
	String getTokenFromUserID(String userid);
	boolean getReadyStatusFromUserID(String userid);
	boolean isUserInALobby(String userid);
	String getAllUserInALobbyToString(String token);
	String getAllUserInALobbyUsernameToString(String token);
	int getNumberOfUserInALobby(String token);
}
