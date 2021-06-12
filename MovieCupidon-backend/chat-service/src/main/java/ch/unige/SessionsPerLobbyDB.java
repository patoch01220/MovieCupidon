package ch.unige;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;



public class SessionsPerLobbyDB {

	  	private static SessionsPerLobbyDB instance;
	    private static Map<String,Map<String, Session>> sessionsListDB = new ConcurrentHashMap<>();


	    public static SessionsPerLobbyDB getInstance(){
	        if(instance == null){
	            synchronized (SessionsPerLobbyDB.class) {
	                if(instance == null){
	                    instance = new SessionsPerLobbyDB();
	                }
	            }
	        }
	        return instance;
	    }
	   public Map<String,Map<String, Session>> getSessionsListDB() {
		   return(sessionsListDB);
	   }
}
