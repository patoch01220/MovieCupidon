package ch.unige.dao;

import java.util.List;

import ch.unige.domain.UserTable;

public interface UserDBInterface {
	
	int getUserDB_size();
	List<UserTable> getFullUserDB();
	public UserTable add_user(String userid, String username);
	boolean removeUser(String user_id);
}
