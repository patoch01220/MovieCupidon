package ch.unige.dao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import ch.unige.domain.UserInLobbyTable;
import ch.unige.domain.LobbyTable;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

@ApplicationScoped
public class UserInLobbyDB implements UserInLobbyDBInterface,PanacheRepository<UserInLobbyTable>{

	@Inject
    private UserDB userDB;
	
	@Override
	public int getUserInLobbyDBSize() {
		return listAll().size();
	}

	@Override
	public List<UserInLobbyTable> getFullUserInLobbyDB() {
		return listAll();
	}

	@Override
	public boolean isTherePlaceInLobby(String token) {
		// Nombre de place max dans le lobby
		int nbMax = ((LobbyTable) LobbyTable.find("token", token).firstResult()).getNbMax();
				
		if(UserInLobbyTable.find("token", token).list().size() < nbMax) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean isEveryoneReady(String token) {
		// Nombre de user dans le lobby
		int nbUserTot = UserInLobbyTable.find("token", token).list().size();
		
		// Nombre de user ready dans le lobby
		int nbUserReady = UserInLobbyTable.find("token = :token and readyStatus = :ready_status",
		         Parameters.with("token", token).and("ready_status", true).map()).list().size();

		if (nbUserTot-1 == nbUserReady) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean toggleReadyStatus(String token, String userid) {
		UserInLobbyTable user = ((UserInLobbyTable) UserInLobbyTable.find("token = :token and userID = :userid",
		         Parameters.with("token", token).and("userid", userid).map()).firstResult());
		
		boolean last_Status = user.getReadyStatus();
		
		user.setReadyStatus(!last_Status);
		
		return true;
	}
	
	@Override
	public boolean isUserInLobby(String token, String userID) {
		return !(UserInLobbyTable.find("token = :token and userID = :userid",
		         Parameters.with("token", token).and("userid", userID).map()).list().isEmpty());
	}

	@Override
	@Transactional
	public UserInLobbyTable addUserInLobby(String token, String userid) {
		UserInLobbyTable newUserInLobby = new UserInLobbyTable();
		newUserInLobby.setToken(token);
		newUserInLobby.setUserID(userid);
		newUserInLobby.persist();
		return newUserInLobby;
	}

	@Override
	public UserInLobbyTable findUserInLobbyById(String id) {
		List<UserInLobbyTable> userInLobbyInstance = find("userID", id).list();
		if(userInLobbyInstance.size() == 1) {
			return find("userID", id).firstResult();
		}
		return null;
	}

	@Override
	@Transactional
	public boolean removeUserFromLobby(String token, String user_id) {
		UserInLobbyTable userInLobbyInstance = (UserInLobbyTable) UserInLobbyTable.find("token = :token and userID = :userid",
		         Parameters.with("token", token).and("userid", user_id).map()).firstResult();
		
		if(userInLobbyInstance.isPersistent()) {
			userInLobbyInstance.delete();
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public String getTokenFromUserID(String userid) {
		return find("userID", userid).firstResult().getToken();
	}
	
	@Override
	public boolean getReadyStatusFromUserID(String userid) {
		return find("userID", userid).firstResult().getReadyStatus();
	}

	@Override
	public boolean isUserInALobby(String userid) {
		return !find("userid", userid).list().isEmpty();
	}
	
	@Override
	public String getAllUserInALobbyToString(String token) {
		int length = find("token", token).list().size();
		var msg = new StringBuilder();
		msg.append("{\n"
				+ "\"token\": \""+token+"\",\n"
				+ "\"listPlayer\": [");
		for(int i = 0; i<length;i++) {
			msg.append("\""+find("token", token).list().get(i).getUserID()+"\"");
			if(i != length-1) {
					msg.append(", ");
			}
		}
		msg = msg.append("]\n}");
		return msg.toString();
	}
	
	@Override
	public String getAllUserInALobbyUsernameToString(String token) {
		int length = find("token", token).list().size();
		var msg = new StringBuilder();

		msg.append("{\n"
				+ "\"listPlayer\": [");
		for(var i = 0; i<length;i++) {
			
			String userID = find("token", token).list().get(i).getUserID();
			
			msg.append("\""+userDB.find("userID", userID).firstResult().getUsername()+"\"");
			if(i != length-1) {
					msg = msg.append(", ");
			}
		}
		msg = msg.append("]\n}");
		return msg.toString();
	}
	
	@Override
	public int getNumberOfUserInALobby(String token) {
		return find("token", token).list().size();
	}
}
