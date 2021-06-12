package ch.unige.domain;

import java.security.SecureRandom;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.transaction.Transactional;

@Entity
@Table(name = "LOBBY") // Le nom de la table sera USER 
public class LobbyTable extends PanacheEntity {
	
	@Column(name = "token", length=10)
	private String token;
	
	@NotEmpty
	@Column(name="ownerID", length=50)
	private String ownerID;
	
	@NotEmpty
	@Column(name="inLobbyStatus", length=50)
	private boolean inLobbyStatus;
	
	@Column(name="lobbyPref")
	private String lobbyPref;
	
	private int nbMax = 5;
	private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static SecureRandom rnd = new SecureRandom();
	private static int token_len = 6;
	
	public int getNbMax() {
		return this.nbMax;
	}
	
	@Transactional
	public void setNbMax(int nb) {
		this.nbMax = nb;
	}
	
	private static String randomString(){
        StringBuilder sb = new StringBuilder(token_len);
        for(int i = 0; i < token_len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }

	public String getNewToken() {
		String token_tmp = randomString();
		while(!find("token", token_tmp).list().isEmpty()) {
			token_tmp = randomString();
		}
		return token_tmp;
	}
	
	@Override
    public String toString() {
        return "ownerID : " + this.getOwnerID() + " token: " + getToken();
    }

	public String getToken() {
		return token;
	}

	@Transactional
	public void setToken(String token) {
		this.token = token;
	}

	public String getOwnerID() {
		return ownerID;
	}

	@Transactional
	public void setOwnerID(String ownerID) {
		this.ownerID = ownerID;
	}

	public boolean getInLobbyStatus() {
		return inLobbyStatus;
	}

	@Transactional
	public void setInLobbyStatus(boolean inLobbyStatus) {
		this.inLobbyStatus = inLobbyStatus;
	}

	public String getLobbyPref() {
		return lobbyPref;
	}

	@Transactional
	public void setLobbyPref(String lobbyPref) {
		this.lobbyPref = lobbyPref;
	}
	
	
	
}
