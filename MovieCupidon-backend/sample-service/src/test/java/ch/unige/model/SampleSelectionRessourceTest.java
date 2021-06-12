package ch.unige.model;

import ch.unige.SampleSelectionRessource;
import ch.unige.domain.LobbyConfig;
import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import junit.framework.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class SampleSelectionRessourceTest extends TestCase {

    private static SampleSelectionRessource ressource;
    private static LobbyConfig lobbyConfig;

    @BeforeAll
    public static void setup(){
        ressource = new SampleSelectionRessource();
        lobbyConfig = new LobbyConfig();
    }

    @AfterAll
    public static void cleanAll(){
        ressource.changeSizeSample(20);
    }

    @Test
    public void getValidSample(){
        String json = "{\n" +
                "\t\"genreList\" : [\"action\", \"horror\", \"animation\"],\n" +
                "\t\"rangeYear\" : [1900,2021]\n" +
                "}";

        given().contentType(MediaType.APPLICATION_JSON).body(json)
                .when().post("/sample-selection/get-sample")
                .then()
                .statusCode(200);

    }

    @Test
    public void getNonValidSampleWrongGender(){
        String json = "{\n" +
                "\t\"genreList\" : [\"ThisGenreDoesntExist\", \"horror\"],\n" +
                "\t\"rangeYear\" : [1900,2021]\n" +
                "}";

        given().contentType(MediaType.APPLICATION_JSON).body(json)
                .when().post("/sample-selection/get-sample")
                .then()
                .statusCode(400);
    }

    @Test
    void getNonValidSampleTooManyGendersGiven() {
        String json = "{\n" +
                "\t\"genreList\" : [\"action\", \"horror\", \"animation\",\"drama\"],\n" +
                "\t\"rangeYear\" : [1900,2021]\n" +
                "}";

        given().contentType(MediaType.APPLICATION_JSON).body(json)
                .when().post("/sample-selection/get-sample")
                .then()
                .statusCode(400);
    }

    @Test
    void getNonValidSampleNotAnyGenderGiven() {
        String json = "{\n" +
                "\t\"genreList\" : [],\n" +
                "\t\"rangeYear\" : [1900,2021]\n" +
                "}";

        given().contentType(MediaType.APPLICATION_JSON).body(json)
                .when().post("/sample-selection/get-sample")
                .then()
                .statusCode(400);
    }

    @Test
    void getMoviesWithoutDuplicate(){

        // Change size sample in order to have enough movies
        int size = 50;
        ressource.changeSizeSample(size);

        String[] genre = {"action", "action", "action"};
        int[] rangeYear = {1900, 2021};
        lobbyConfig.setGenreList(genre);
        lobbyConfig.setRangeYear(rangeYear);

        ArrayList res = ressource.getSample(lobbyConfig).readEntity(ArrayList.class);

        Set res_set = new HashSet(res);

        assertEquals(size, res_set.size());

    }

    @Test
    void helloWorld() {
        given().when().get("/sample-selection/helloworld")
                .then()
                .statusCode(200);
    }
}
