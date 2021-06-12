package ch.unige;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ch.unige.domain.LobbyConfig;
import info.movito.themoviedbapi.*;
import info.movito.themoviedbapi.model.*;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.TmdbGenre;

import org.eclipse.microprofile.config.ConfigProvider;

import java.util.*;


@Path("/sample-selection")
public class SampleSelectionRessource {

    private static final String API_KEY = ConfigProvider.getConfig().getValue("tmdb.apiKey", String.class);
    private static final TmdbApi TMDB_API = new TmdbApi(API_KEY);

    private static final TmdbGenre TMDB_GENRE = TMDB_API.getGenre();

    private static final Map<String, Integer> GENRE_HASH_MAP = generateGenreHashMap();

    private int sizeSample = 20;

    private static Map<String, Integer> generateGenreHashMap(){
        Map<String, Integer> res = new HashMap<>();
        for (Genre g : TMDB_GENRE.getGenreList("en")){
            res.put(g.getName().toLowerCase(Locale.ROOT), g.getId());
        }
        return res;
    }

    @POST
    @Path("/get-sample")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getSample(LobbyConfig config){

        int sizeGenreList = config.getGenreList().length;
        if (sizeGenreList > 3 || sizeGenreList < 1){
            var message = "Please choose between 1 and 3 genres.";
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }

        int[] genreIdList = new int[sizeGenreList];

        int i = 0;
        for (String genreParam : config.getGenreList()){
            if (GENRE_HASH_MAP.get(genreParam.toLowerCase(Locale.ROOT)) != null){
                genreIdList[i] = GENRE_HASH_MAP.get(genreParam.toLowerCase(Locale.ROOT));
                i++;
            }
            else{
                String message = "The chosen gender " + genreParam +
                        " does not exist. Please select a valid genre from : "
                        + GENRE_HASH_MAP.keySet();
                return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
            }
        }

        int[] genreNumbersMovies = new int[sizeGenreList];
        int sum = 0;
        for (i = 0; i < sizeGenreList; i++) {
            if (i == sizeGenreList - 1){
                genreNumbersMovies[i] = sizeSample - sum;
            }
            else{
                genreNumbersMovies[i] = sizeSample / sizeGenreList;
                sum += sizeSample / sizeGenreList;
            }
        }

        ArrayList<MovieDb> sample = new ArrayList<>();
        TmdbDiscover disco = TMDB_API.getDiscover();
        i = 0;
        for (int genreID: genreIdList){
            String genreIDStr = Integer.toString(genreID);
            String yearDown = config.getRangeYear()[0] +"-01-01";
            String yearUp = config.getRangeYear()[1] +"-12-31";

            int pageCounter = 1;
            MovieResultsPage res = disco.getDiscover(pageCounter, "en-US", "vote_average.desc",
                    false, -1, -1, 35, 0, genreIDStr,
                    yearDown, yearUp,
                    "", "", "");

            int j = 0;
            for (Iterator<MovieDb> it = res.iterator(); it.hasNext(); ) {
                MovieDb movie = it.next();
                if (j == genreNumbersMovies[i]){
                    break;
                }

                // Si jamais le film est déjà présent dans la liste de sample
                while (sample.contains(movie)) {
                    movie = it.next();

                    // Si jamais il n'y a plus de films disponible dans les résultats, on prends ceux de la page suivante
                    if (!it.hasNext()){
                        pageCounter++;
                        res = disco.getDiscover(pageCounter, "en-US", "vote_average.desc",
                                false, -1, -1, 25, 0, genreIDStr,
                                yearDown, yearUp,
                                "", "", "");
                        it = res.iterator();
                    }
                }

                // Ajoute le film au sample
                sample.add(movie);
                j++;

                // Si jamais il faut encore selectionner des films pour le genre i
                if (!it.hasNext() && j != genreNumbersMovies[i]){
                    pageCounter++;
                    res = disco.getDiscover(pageCounter, "en-US", "vote_average.desc",
                            false, -1, -1, 25, 0, genreIDStr,
                            yearDown, yearUp,
                            "", "", "");
                    it = res.iterator();
                }
            }
            i++;
        }

        for (var j = 0; j < sample.size(); j++) {
            sample.get(j).setId(j);
            String finalPosterPath = "https://image.tmdb.org/t/p/original" + sample.get(j).getPosterPath();
            sample.get(j).setPosterPath(finalPosterPath);
        }

        return Response.ok(sample).build();
    }

    @GET
    @Path("/helloworld")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "This is sample selection service";
    }


    public void changeSizeSample(int newSize){
        this.sizeSample = newSize;
    }




}
