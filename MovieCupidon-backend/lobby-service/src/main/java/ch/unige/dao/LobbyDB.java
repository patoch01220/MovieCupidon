package ch.unige.dao;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;
import javax.transaction.Transactional;

import org.hibernate.loader.plan.build.internal.returns.CollectionFetchableIndexAnyGraph;

import ch.unige.domain.LobbyConfig;
import ch.unige.domain.LobbyTable;
import ch.unige.domain.UserInLobbyTable;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

@ApplicationScoped
public class LobbyDB implements LobbyDBInterface,PanacheRepository<LobbyTable>{
	
	@Override
	public boolean lobbyExist(String token) {
		return !(find("token", token).list().isEmpty());
	}

	@Override
	public int lobbySize(String token) {
		List<LobbyTable> lobbyInstance = find("token", token).list();
		if(lobbyInstance.size() == 1) {
			return find("token", token).firstResult().getNbMax();
		}else { // Liste vide, le lobby existe pas
			return -1;
		}
	}

	@Override
	@Transactional
	public LobbyTable add_lobby(String ownerid) {
		LobbyTable newLobby = new LobbyTable();
		newLobby.setOwnerID(ownerid);
		newLobby.setToken(newLobby.getNewToken());
		newLobby.setInLobbyStatus(true);
		newLobby.persist();
		return newLobby;
	}
	
	@Override
	public int getlobbyDB_size() {
		return listAll().size();
	}

	@Override
	public List<LobbyTable> getFullDB() {
		return listAll();
	}

	@Override
	public boolean isHeTheOwner(String token, String userid) {
		String ownerID = ((LobbyTable) find("token", token).firstResult()).getOwnerID();
		return userid.equals(ownerID);
	}
	
	@Override
	public boolean hasItStart(String token) {
		return !find("token", token).firstResult().getInLobbyStatus();
	}
	
	@Override
	@Transactional
	public void addLobbyPref(String token, LobbyConfig config) {
		String json2string = "{\n\"genreList\" : [";
		for(int i = 0; i<config.getGenreList().length; i++) {
			json2string = json2string+"\""+config.getGenreList()[i]+"\"";
			if(i < config.getGenreList().length-1) {
				json2string = json2string+", ";
			}
		}
		json2string = json2string+"],\n";
		json2string = json2string+"\"rangeYear\" : [";
		for(int i = 0; i<config.getRangeYear().length; i++) {
			json2string = json2string+config.getRangeYear()[i];
			if(i < config.getRangeYear().length-1) {
				json2string = json2string+", ";
			}
		}
		json2string = json2string+"]\n";
		json2string = json2string+"}";
		
		find("token", token).firstResult().setLobbyPref(json2string);
	}
	
	@Override
	@Transactional
	public boolean removeLobby(String token) {
		LobbyTable lobbyInstance = (LobbyTable) find("token", token).firstResult();
		
		if(lobbyInstance.isPersistent()) {
			lobbyInstance.delete();
			return true;
		}else {
			return false;
		}
	}
	
	@Override 
	@Transactional
	public void setInLobbyStatus(String token, boolean newStatus) {
		find("token", token).firstResult().setInLobbyStatus(newStatus);
	}
	
	@Override
	@Transactional
	public String getLobbyPreferences(String token) {
		return find("token", token).firstResult().getLobbyPref();
	}
	
	@Override
	public boolean getInLobbyStatus(String token) {
		return find("token", token).firstResult().getInLobbyStatus();
	}
	
	@Override 
	public String getOwnerID(String token) {
		return find("token", token).firstResult().getOwnerID();
	}
}

