package ch.unige.dao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;

import ch.unige.domain.UserInLobbyTable;
import ch.unige.domain.UserTable;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

@ApplicationScoped
public class UserDB implements UserDBInterface,PanacheRepository<UserTable>{
	
	@Override
	public int getUserDB_size() {
		return listAll().size();
	}
	
	@Override
	public List<UserTable> getFullUserDB() {
		return listAll();
	}

	@Transactional
	@Override
	public UserTable add_user(String userid, String username) {
		if(UserTable.validUsername(username)) {
			UserTable newUser = new UserTable();
			newUser.setUserID(userid);
			newUser.setUsername(username);
			newUser.persist();
			return newUser;
		}else {
			throw new BadRequestException();
		}
	
	}
	

	@Override
	@Transactional
	public boolean removeUser(String user_id) {
		UserTable userInstance = (UserTable) UserTable.find("userID", user_id).firstResult();
		
		if(userInstance.isPersistent()) {
			userInstance.delete();
			return true;
		}else {
			return false;
		}
	}

}
