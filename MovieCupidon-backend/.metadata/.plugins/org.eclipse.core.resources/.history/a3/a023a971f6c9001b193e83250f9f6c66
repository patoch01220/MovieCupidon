package ch.unige;

import ch.unige.dao.LobbyDB;
import ch.unige.dao.UserDB;
import ch.unige.dao.UserInLobbyDB;
import ch.unige.domain.LobbyConfig;
import ch.unige.domain.LobbyTable;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/lobby")
public class LobbyRessource {
	
	@Inject
    private UserDB userDB;
    
    @Inject
    private LobbyDB lobbyDB;
    
    @Inject 
    private UserInLobbyDB userLobbyDB;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/size")
    public Integer countlobbys(){
    	return lobbyDB.getlobbyDB_size();
    }

    @POST
    @Transactional
    @Path("/start")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response startGame(LobbyConfig config, @Context HttpHeaders headers) {

    	String userID = headers.getHeaderString("X-User");
    	
    	if(!userLobbyDB.isUserInALobby(userID)) {
    		String message = "This User isn't in any lobby";
        	
        	return Response.status(Response.Status.NOT_FOUND)
        			.entity(message)
        			.build();
    	}
    		
    	String token = userLobbyDB.getTokenFromUserID(userID);

    	if(!lobbyDB.isHeTheOwner(token, userID)) {
    		// Check que le user est bien le owner
    		return Response.status(Response.Status.CONFLICT).entity("Ce user n'est pas le owner").build();
    	}
    	
    	if(!userLobbyDB.isEveryoneReady(token)) {
    		// Check si tous le monde est ready
    		return Response.status(Response.Status.CONFLICT).entity("Tous les joiner ne sont pas ready.").build();
    	}	

    	int sizeGenreList = config.getGenreList().length;
        if (sizeGenreList > 3 || sizeGenreList < 1){
        	// Check s'il y a entre 1 et 3 genres
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        lobbyDB.addLobbyPref(token, config);
    	lobbyDB.setInLobbyStatus(token, false);
        
    	String msgInit = userLobbyDB.getAllUserInALobbyToString(token);
    	String msgEncrypt = SecurityUtility.encrypt(msgInit);
    	  
    	return Response.status(Response.Status.OK)
    			.entity(msgEncrypt)
    			.type(MediaType.TEXT_PLAIN)
    			.build();
    }

    
    @GET
    @Path("/helloworld")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "This is lobby service in lobbyRessource";
    }

    @POST
    @Transactional
    @Path("/toggleready")
    @Produces(MediaType.APPLICATION_JSON)
    public Response toggleReady(@Context HttpHeaders headers) {

    	String userID = headers.getHeaderString("X-User");
    	
    	if(!userLobbyDB.isUserInALobby(userID)) {
    		String message = "This User isn't in any lobby";
        	
        	return Response.status(Response.Status.NOT_FOUND)
        			.entity(message)
        			.build();
    	}    	
    		
    	String token = userLobbyDB.getTokenFromUserID(userID);

        userLobbyDB.toggleReadyStatus(token, userID);
        
        String message = "{\"Status\": "+userLobbyDB.getReadyStatusFromUserID(userID)+"}";
        
        return Response.status(Response.Status.OK)
    			.entity(message)
    			.type(MediaType.APPLICATION_JSON)
    			.build();
    }

    @DELETE
    @Transactional
    @Path("/quit")
    @Produces(MediaType.TEXT_PLAIN)
    public Response UserQuitLobby(@Context HttpHeaders headers) {
        String message;

    	String userID = headers.getHeaderString("X-User");
    	
    	if(!userLobbyDB.isUserInALobby(userID)) {
    		message = "This User isn't in any lobby";
        	
        	return Response.status(Response.Status.NOT_FOUND)
        			.entity(message)
        			.build();
    	}
    		
    	String token = userLobbyDB.getTokenFromUserID(userID);
           
        if(!(userLobbyDB.removeUserFromLobby(token, userID) && userDB.removeUser(userID))){
            message = "User or Lobby not found";
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }else {
        	 message = "User removed end deleted";
             return Response.status(Response.Status.OK).entity(message).build();
        }
    }
    
    @GET
    @Path("/isOwner")
    @Produces(MediaType.APPLICATION_JSON)
    public Response isOwner(@Context HttpHeaders headers) {
    	
    	String userID = headers.getHeaderString("X-User");
    	
    	if(!userLobbyDB.isUserInALobby(userID)) {
    		String message = "This User isn't in any lobby";
        	
        	return Response.status(Response.Status.NOT_FOUND)
        			.entity(message)
        			.build();
    	}
    	
    	String token = userLobbyDB.getTokenFromUserID(userID);
    	
    	// La valeur de retour si le user est bien le owner ou non
    	boolean isHeTheOwner = lobbyDB.isHeTheOwner(token, userID);
    	
    	// Creation du string convertit en JSON
    	String message = "{\"isOwner\":"+isHeTheOwner+"}";
    	
    	return Response.status(Response.Status.OK)
    			.entity(message)
    			.type(MediaType.APPLICATION_JSON)
    			.build();
    }
    
    @GET
    @Path("/whoIsTheOwner")
    @Produces(MediaType.APPLICATION_JSON)
    public Response whoIsTheOwner(@Context HttpHeaders headers) {
    	
    	String userId = headers.getHeaderString("X-User");
    	
    	if(!userLobbyDB.isUserInALobby(userId)) {
    		String message = "This User isn't in any lobby";
        	
        	return Response.status(Response.Status.NOT_FOUND)
        			.entity(message)
        			.build();
    	}
    	
    	String token = userLobbyDB.getTokenFromUserID(userId);
    	
    	String ownerID = lobbyDB.getOwnerID(token);
    	String message = "{\"ownerID\": \""+ownerID+"\"}";
    	return Response.status(Response.Status.OK)
    			.entity(message)
    			.type(MediaType.APPLICATION_JSON)
    			.build();
    }
    
    @GET
    @Path("/isEveryoneReady")
    @Produces(MediaType.APPLICATION_JSON)
    public Response isEveryoneReady(@Context HttpHeaders headers) {
    	
    	String userId = headers.getHeaderString("X-User");
    	
    	if(!userLobbyDB.isUserInALobby(userId)) {
    		String message = "This User isn't in any lobby";
        	
        	return Response.status(Response.Status.NOT_FOUND)
        			.entity(message)
        			.build();
    	}
    	
    	String token = userLobbyDB.getTokenFromUserID(userId);
    	
    	String message =  "{\"isEveryoneReady\":"+userLobbyDB.isEveryoneReady(token)+"}";
    	return Response.status(Response.Status.OK)
    			.entity(message)
    			.type(MediaType.APPLICATION_JSON)
    			.build();
    }
    
    @GET
    @Path("/hasTheGameStartYet")
    @Produces(MediaType.APPLICATION_JSON)
    public Response hasTheGameStartYet(@Context HttpHeaders headers) {
    	
    	String userId = headers.getHeaderString("X-User");
    	
    	if(!userLobbyDB.isUserInALobby(userId)) {
    		String message = "This User isn't in any lobby";
        	
        	return Response.status(Response.Status.NOT_FOUND)
        			.entity(message)
        			.build();
    	}
    	
    	String token = userLobbyDB.getTokenFromUserID(userId);
    	
    	String message =  "{\"hasTheGameStartYet\":"+lobbyDB.hasItStart(token)+"}";
    	return Response.status(Response.Status.OK)
    			.entity(message)
    			.type(MediaType.APPLICATION_JSON)
    			.build();
    	
    }
    
    @GET
    @Path("/getLobbyPref")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLobbyPref(@Context HttpHeaders headers) {
    	
    	String userId = headers.getHeaderString("X-User");
    	
    	if(!userLobbyDB.isUserInALobby(userId)) {
    		String message = "This User isn't in any lobby";
        	
        	return Response.status(Response.Status.NOT_FOUND)
        			.entity(message)
        			.build();
    	}
    	
    	String token = userLobbyDB.getTokenFromUserID(userId);
    	
    	if(!lobbyDB.hasItStart(token)) {
    		String message = "The game hasn't start now";
        	
        	return Response.status(Response.Status.UNAUTHORIZED)
        			.entity(message)
        			.build();
    	}
    	
    	String message = lobbyDB.getLobbyPreferences(token);
    	
    	return Response.status(Response.Status.OK)
    			.entity(message)
    			.type(MediaType.APPLICATION_JSON)
    			.build();
    }
    
    @DELETE
    @Transactional
    @Path("/endGameDeletion")
    @Produces(MediaType.TEXT_PLAIN)
    public Response endGameDeletion(@Context HttpHeaders headers) {

    	String userId = headers.getHeaderString("X-User");
    	
    	if(!userLobbyDB.isUserInALobby(userId)) {
    		String message = "This User isn't in any lobby";
        	
        	return Response.status(Response.Status.NOT_FOUND)
        			.entity(message)
        			.build();
    	}

    	String token = userLobbyDB.getTokenFromUserID(userId);
    	    	    
    	var message = "";
    	if(userLobbyDB.getNumberOfUserInALobby(token)==1) {
    		if(lobbyDB.removeLobby(token)) {
    			message += "Lobby deleted ";
    		}else {
    			message = "Lobby didn't deleted";
                return Response.status(Response.Status.NOT_FOUND).entity(message).build();
    		}
    	}
    	if(!(userLobbyDB.removeUserFromLobby(token, userId) && userDB.removeUser(userId))){
            message = "User or Lobby not found";
            return Response.status(Response.Status.NOT_FOUND).entity(message).build();
        }else {
    		message += "User deleted";
            return Response.status(Response.Status.OK).entity(message).build();
        }
    	
    }
    @GET
    @Path("/seeUserInLobby")
    @Produces(MediaType.APPLICATION_JSON)
    public Response seeUserInLobby(@Context HttpHeaders headers) {
    	
    	var userId = headers.getHeaderString("X-User");
    	
    	if(!userLobbyDB.isUserInALobby(userId)) {
    		var message = "This User isn't in any lobby";
        	
        	return Response.status(Response.Status.NOT_FOUND)
        			.entity(message)
        			.build();
    	}
    	
    	String token = userLobbyDB.getTokenFromUserID(userId);

    	
    	var message = userLobbyDB.getAllUserInALobbyUsernameToString(token);
    	
    	return Response.status(Response.Status.OK)
    			.entity(message)
    			.type(MediaType.APPLICATION_JSON)
    			.build();
    }
    
    @GET
    @Path("getToken")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getToken(@Context HttpHeaders headers) {
    	
    	var userId = headers.getHeaderString("X-User");
    	
    	if(!userLobbyDB.isUserInALobby(userId)) {
    		var message = "This User isn't in any lobby";
        	
        	return Response.status(Response.Status.NOT_FOUND)
        			.entity(message)
        			.build();
    	}
    	
    	String message = "{\"token\": \""+userLobbyDB.getTokenFromUserID(userId)+"\"}";
    	
    	return Response.status(Response.Status.OK)
    			.entity(message)
    			.type(MediaType.APPLICATION_JSON)
    			.build();
    }

    
  
    @GET
    @Path("/seeDB")
    @Produces(MediaType.TEXT_PLAIN)
    public List<LobbyTable> seeDatabaseFull(){
        return lobbyDB.getFullDB();
    }
    
    /*TODO: This is for dev purposes only: */
    
    @GET
    @Path("/seeUserInLobbyDB")
    public String seeUserInLobbyDB() {
        return String.valueOf(userLobbyDB);
    }
    
}

