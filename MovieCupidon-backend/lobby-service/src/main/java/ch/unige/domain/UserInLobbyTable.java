package ch.unige.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "USERINLOBBY") // Le nom de la table sera USER
public class UserInLobbyTable extends PanacheEntity {
	
	@NotEmpty
	@Column(name="UserID", length=50)
	private String userID;
	
	@NotEmpty
	@JoinColumn(name = "token")  
	private String token;
	
	@NotEmpty
	@Column(name="readyStatus", length=50)
	private boolean readyStatus;

	@Override
	public String toString() { 
	    String result = "User: "+ String.valueOf(this.getUserID()) + " , lobbyId:" + this.getToken() + " , Ready Status: " + String.valueOf(this.getReadyStatus()); 
	    return result;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean getReadyStatus() {
		return readyStatus;
	}

	public void setReadyStatus(boolean readyStatus) {
		this.readyStatus = readyStatus;
	} 
	
}
