package ch.unige;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.unige.dao.LobbyDB;
import ch.unige.dao.UserInLobbyDB;

@Path("/play")
public class PlayResource {

    @GET
    @Path("users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        List<UserInLobbyDB> uil = UserInLobbyDB.listAll();
        return Response.ok(uil).build();
    }

    @GET
    @Path("lobbies")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLobbies() {
        List<LobbyDB> L = LobbyDB.listAll();
        return Response.ok(L).build();
    }

    @GET
    @Path("gameStarted")
    public Response getStarted(@Context HttpHeaders headers) {

        String userid = headers.getHeaderString("X-User");

        var userExistant = UserInLobbyDB.getUser(userid);
        if (userExistant == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.status(Response.Status.ACCEPTED).build();
        }
    }

    @POST
    @Transactional
    @Path("initGame/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response initGame(String initString) {

        String initJSON = "";
        try {
            initJSON = SecurityUtility.decrypt(initString);
        } catch (Exception e) {
            var message = "Encryption_non_valide";
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }

        try {
            StringToJSON jsonObj = new ObjectMapper().readValue(initJSON, StringToJSON.class);

            // Vérifier que le lobby n'est pas déjà en DB
            LobbyDB lobbyExistant = LobbyDB.getLobby(jsonObj.token);
            if (lobbyExistant != null) {
                var message = "Lobby_déjà_enregistré_!";
                return Response.status(Response.Status.UNAUTHORIZED).entity(message).build();
            }

            // Create players
            for (String userID : jsonObj.listPlayer) {
                UserInLobbyDB player = new UserInLobbyDB();
                player.token = jsonObj.token;
                player.userID = userID;
                // add player to lobby
                UserInLobbyDB.persist(player);
            }

            // Create and init lobby
            LobbyDB lobby = new LobbyDB();
            lobby.token = jsonObj.token;
            for (int i = 0; i < 20; i++) {
                lobby.sumScores.add(0);
                lobby.numberVotes.add(0);
            }

            // Add lobby dans la DB
            LobbyDB.persist(lobby);
            return Response.created(null).build();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        var message = "Erreur_avec_le_message_JSON_!";
        return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
    }

    @POST
    @Transactional
    @Path("/send/{MOVIEID}/{SCORE}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response GameScore(@PathParam("MOVIEID") int movie_id, @PathParam("SCORE") int score,
            @Context HttpHeaders headers) {

        var userid = headers.getHeaderString("X-User");
        // movie_id valide ?
        if (movie_id < 0 || movie_id > 19 || movie_id != (int) movie_id) {
            var message = "Le_id_du_film_n'est_pas_valide !";
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
        // score valide ?
        if (score < 0 || score > 100 || score != (int) score) {
            var message = "Le_score_attribué_n'est_pas_valide !";
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
        // Regarder si le sender est dans une partie
        var uil = UserInLobbyDB.getUser(userid);
        if (uil == null) {
            var message = "Le_joueur_n'est_pas_dans_une_partie_!";
            return Response.status(Response.Status.UNAUTHORIZED).entity(message).build();
        }
        // Get les attributs du sender
        String token = uil.token;
        ArrayList<Integer> votesID = uil.votesID;

        // Le joueur a déjà joué toutes ses cartes ?
        if (votesID.size() >= 20) {
            var message = "Le_joueur_a_déjà_épuisé_son_nombre_de_votes_!";
            return Response.status(Response.Status.UNAUTHORIZED).entity(message).build();
        }

        // Le joueur a déjà voté pour ce même film ?
        if (votesID.contains(movie_id)) {
            var message = "Le_joueur_a_déjà_voté_pour_ce_film_!";
            return Response.status(Response.Status.UNAUTHORIZED).entity(message).build();
        }

        // Get le LobbyDB correspondant au token
        LobbyDB L = LobbyDB.getLobby(token);

        // Add le score côté user
        votesID.add(movie_id);
        uil.votesID = votesID;

        // Add le score côté lobby
        int currentScore = L.sumScores.get(movie_id);
        L.sumScores.set(movie_id, currentScore + score);
        int currentVotes = L.numberVotes.get(movie_id);
        L.numberVotes.set(movie_id, currentVotes + 1);

        return Response.ok().build();
    }

    @DELETE
    @Transactional
    @Path("/quit")
    @Produces(MediaType.TEXT_PLAIN)
    public Response UserQuitLobby(@Context HttpHeaders headers) {

        var userID = headers.getHeaderString("X-User");

        // Regarder si le joueur est dans une partie
        var uil = UserInLobbyDB.getUser(userID);
        if (uil == null) {
            var message = "Le_joueur_n'est_pas_dans_une_partie_!";
            return Response.status(Response.Status.NO_CONTENT).entity(message).build();
        }
        UserInLobbyDB.deleteUser(userID);
        var message = "Joueur_retiré_de_la_partie";
        // vérifier si il y a toujours qqn dans le lobby sinon delete lobby
        String token = uil.token;
        var user = UserInLobbyDB.getUserFromToken(token);
        if (user == null) {
            LobbyDB.deleteLobby(token);
        }
        return Response.status(Response.Status.OK).entity(message).build();
    }

    @GET
    @Transactional
    @Path("/getResult")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getResult(@Context HttpHeaders headers) {

        var userid = headers.getHeaderString("X-User");

        // Vérifier si le user est dans un lobby et get le token
        String token;
        try {
            var uil = UserInLobbyDB.getUser(userid);
            token = uil.token;
        } catch (Exception e) {
            var message = "La_requête_n'est_pas_valide_!";
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }

        if (UserInLobbyDB.getStatus(token)) {
            var uil = UserInLobbyDB.getUser(userid);
            int movieWinnerID;
            if (uil.result == -1) {
                // get result
                movieWinnerID = LobbyDB.getMovieWinner(token);
                // write results to all users
                UserInLobbyDB.writeResultToDB(movieWinnerID, token);
            } else {
                movieWinnerID = uil.result;
            }
            // delete le user
            UserInLobbyDB.deleteUser(userid);
            // vérifier si il y a toujours qqn dans le lobby sinon delete lobby
            var user = UserInLobbyDB.getUserFromToken(token);
            if (user == null) {
                LobbyDB.deleteLobby(token);
            }
            // return result
            String message = "{\"id\":" + movieWinnerID + "}";
            return Response.status(Response.Status.OK).entity(message).type(MediaType.APPLICATION_JSON).build();
        } else {
            var message = "La_partie_n'est_pas_terminée";
            return Response.status(Response.Status.NO_CONTENT).entity(message).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/checkAllFinish")

    public Response gameDone(@Context HttpHeaders headers) {
        var userid = headers.getHeaderString("X-User");

        // Vérifier si le user est dans un lobby et get le token
        String token;
        try {
            var uil = UserInLobbyDB.getUser(userid);
            token = uil.token;
        } catch (Exception e) {
            var message = "La_requête_n'est_pas_valide_!";
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }
        boolean status = UserInLobbyDB.getStatus(token);
        String message = "{\"fin\":" + status + "}";
        return Response.status(Response.Status.OK).entity(message).type(MediaType.APPLICATION_JSON).build();
    }
}