package ch.unige.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.ws.rs.BadRequestException;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USERTABLE") // Le nom de la table sera USER
public class UserTable extends PanacheEntity {

	@NotEmpty
	@Column(name = "userID")
	private String userID;
	
	@NotEmpty
	@Column(name="username", length=50)
	private String username;

	public static boolean validUsername(String username) {
		if (username.isEmpty()) {		// Pas forcement besoin de cette partie mais permet de differencier si le username
										// est juste vide ou s'il contient uniquement des espace ou tabs
	        System.out.println("Username empty");
			return false;
		}
		
		if (username.isBlank()) {		// Test si un username contient que des espaces ou est nul
			System.out.println("Username blank");
			return false;
		}
		if (username.length() <= 2) {	// Test si le username est assez grand
			System.out.println("Username too short");
			return false;
		}

		for (int i=0; i<username.length(); i++) {	// Test si le username ne contient pas des caractère spéciaux
			if (!((username.charAt(i) >= 'a' && username.charAt(i) <= 'z')
		            || (username.charAt(i) >= 'A' && username.charAt(i) <= 'Z')
		            || (username.charAt(i) >= '0' && username.charAt(i) <= '9')
		            || username.charAt(i) == '_')) {
				return false;
			}
		}
		return true;	
	}
	
	@Override
	public String toString() { 
	    String result = "Username: "+ this.getUsername() + " , userId:" + this.getUserID(); 
	    return result;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	} 
    
}
