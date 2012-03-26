package pictureManager.dao;

import java.util.List;

import pictureManager.domain.Event;

public interface EventDao {
	
	public Event loadById(long eveId) throws DataAccessException;
	public Event loadByName(String eveName) throws DataAccessException;
	public List<Event> loadAll() throws DataAccessException;
	public boolean save(Event eveObject) throws DataAccessException;
	public boolean saveList(List<Event> eveList) throws DataAccessException;
    public boolean delete(Event eveObject) throws DataAccessException;
    public boolean deleteById(Long eveId) throws DataAccessException;

}
