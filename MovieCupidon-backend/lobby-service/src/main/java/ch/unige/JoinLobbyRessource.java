package ch.unige;

import ch.unige.dao.LobbyDB;
import ch.unige.dao.UserDB;
import ch.unige.dao.UserInLobbyDB;
import ch.unige.domain.UserInLobbyTable;
import ch.unige.domain.UserTable;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/join-lobby")
public class JoinLobbyRessource {
	
	@Inject
    private UserDB userDB;
    
    @Inject
    private LobbyDB lobbyDB;
    
    @Inject
    private UserInLobbyDB userInLobbyDB;
	
	@POST
    @Path("/join")
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response joinLobby(@FormParam("username") String username, @FormParam("token") String token, @Context HttpHeaders headers){
    	
		String userID = headers.getHeaderString("X-User");
    	
    	if(userInLobbyDB.isUserInALobby(userID)) {
    		String message = "User already in a lobby";
    		return Response.status(Response.Status.UNAUTHORIZED)
    			.entity(message)
    			.build();
    	}
    	
        if (!lobbyDB.lobbyExist(token)){
            // Check if lobby exist
            return Response.status(Response.Status.NOT_FOUND).entity("lobby inexistante ou mauvais token.").build();
        }
        
        if (!userInLobbyDB.isTherePlaceInLobby(token)){
            //check if there is space in the looby
        	return Response.status(Response.Status.UNAUTHORIZED).entity("Aucune place dans le lobby restante.").build(); 
        }
        
        if (lobbyDB.hasItStart(token)) {
        	// Check si la game à déja été lancée
            return Response.status(Response.Status.UNAUTHORIZED).entity("La partie a déjà été lancée.").build(); 
        }
        
        UserTable joiner = userDB.add_user(userID, username);

        UserInLobbyTable userInLobby = userInLobbyDB.addUserInLobby(token, userID);

        String message = "{\"token\": \""+token+"\"}";
        
        // Retourne 200 en cas de succès et le body "{"ownerID": ownerID}"
        return Response.status(Response.Status.OK)
        		.entity(message)
        		.type(MediaType.APPLICATION_JSON)
        		.build(); 
    }
	
}
