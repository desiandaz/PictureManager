package pictureManager.dao;

import pictureManager.domain.EventLocation;

public interface EventLocationDao {
	
	public EventLocation loadById(long evlID) throws DataAccessException;
	public boolean save(EventLocation evlObject) throws DataAccessException;
    public boolean deleteById(long evlID) throws DataAccessException;
    public void setLatLong(long evlID) throws DataAccessException;
    
}
