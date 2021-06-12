package ch.unige;

import ch.unige.dao.LobbyDB;
import ch.unige.dao.UserDB;
import ch.unige.dao.UserInLobbyDB;
import ch.unige.domain.LobbyTable;
import ch.unige.domain.UserInLobbyTable;
import ch.unige.domain.UserTable;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/create-lobby")
public class CreateLobbyRessource {

    @Inject
    private UserDB userDB;
    
    @Inject
    private LobbyDB lobbyDB;
    
    @Inject
    private UserInLobbyDB userInLobbyDB;
    
    @POST
    @Path("/new-lobby")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON) 
    public Response createlobby(@FormParam("username") String username, @Context HttpHeaders headers) {
    	
    	String userid = headers.getHeaderString("X-User");
    	
    	if(userInLobbyDB.isUserInALobby(userid)) {
    		String message = "User already in a lobby";
    		return Response.status(Response.Status.UNAUTHORIZED)
    			.entity(message)
    			.build();
    	}
    	
    	UserTable owner = userDB.add_user(userid, username);
    	

    	LobbyTable lobby = lobbyDB.add_lobby(userid);
    	String token = lobby.getToken();
    	
    	
    	UserInLobbyTable userInLobby = userInLobbyDB.addUserInLobby(token, userid);

        // Message JSON envoyé
        String message = "{\"token\": \""+token+"\"}";
        
        // Retourne 200 en cas de succès et le body "{"ownerID": ownerID}"
        return Response.status(Response.Status.OK)
        		.entity(message)
        		.type(MediaType.APPLICATION_JSON)
        		.build(); 
    }

}

