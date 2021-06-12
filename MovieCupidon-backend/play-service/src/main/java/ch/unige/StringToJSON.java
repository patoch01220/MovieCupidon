package ch.unige;

import java.util.ArrayList;

public class StringToJSON {
    String token;
    ArrayList<String> listPlayer = new ArrayList<String>(); 

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ArrayList<String> getListPlayer() {
        return this.listPlayer;
    }

    public void setListPlayer(ArrayList<String> listPlayer) {
        this.listPlayer = listPlayer;
    }
}
