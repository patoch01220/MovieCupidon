package ch.unige.dao;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class LobbyDB extends PanacheEntity {

    public String token;
    @Column(length = 1024)
    public ArrayList<Integer> sumScores = new ArrayList<Integer>();
    @Column(length = 1024)
    public ArrayList<Integer> numberVotes = new ArrayList<Integer>();

    public static LobbyDB getLobby(String token) {
        return find("token", token).firstResult();
    }

    public static int getMovieWinner(String token) {
        LobbyDB L = getLobby(token);
        ArrayList<Float> resultList = new ArrayList<Float>();
        for (int i = 0; i < 20; i++) {
            resultList.add((float) L.sumScores.get(i) / L.numberVotes.get(i));
        }
        return calcMax(resultList);
    }

    // Calcul du maximum dans une arrayList de moyennes de scores
    public static int calcMax(ArrayList<Float> resultList) {
        float max = 0;
        int maxIndex = 0;
        for (int i = 0; i < 20; i++) {
            if (resultList.get(i) > max) {
                max = resultList.get(i);
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public static void deleteLobby(String token) {
        LobbyDB L = getLobby(token);
        L.delete();
    }
}
