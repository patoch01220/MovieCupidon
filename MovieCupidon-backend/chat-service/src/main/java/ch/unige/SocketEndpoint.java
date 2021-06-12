package ch.unige;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.Session;

@ServerEndpoint("/chat/{token}/{username}")         
@ApplicationScoped
public class SocketEndpoint {
	private static SessionsPerLobbyDB  sessionsPerLobbyDB = SessionsPerLobbyDB.getInstance();
	
	private Map<String, Session> sessions;
	private Map<String,Map<String, Session>> sessionsList = sessionsPerLobbyDB.getSessionsListDB();

    @OnOpen
    public void onOpen(Session session,@PathParam("token") String token, @PathParam("username") String username) {
    		sessions = sessionsList.get(token);
    	
    	if (sessions == null) {
    		sessions = new ConcurrentHashMap<String, Session>();
    		sessionsList.put(token, sessions);
		}
    	
        sessions.put(username, session);
        broadcast(token,"User " + username + " joined");
    }

    @OnClose
    public void onClose(Session session,@PathParam("token") String token, @PathParam("username") String username) {
    	sessions = sessionsList.get(token);

        sessions.remove(username);
        broadcast(token,"User " + username + " left");
    }

    @OnError
    public void onError(Session session,@PathParam("token") String token, @PathParam("username") String username, Throwable throwable) {
    	sessions = sessionsList.get(token);

        sessions.remove(username);
        broadcast(token,"User " + username + " left on error: " + throwable);
    }

    @OnMessage
    public void onMessage(String message,@PathParam("token") String token, @PathParam("username") String username) {
        broadcast(token,">> " + username + ": " + message);
    }

    private void broadcast(String token,String message) {
    	sessions = sessionsList.get(token);
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(message, result ->  {
                if (result.getException() != null) {
                    System.out.println("Unable to send message: " + result.getException());
                }
            });
        });
    }

}
