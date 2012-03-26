package pictureManager.dao;

import java.util.List;

import pictureManager.domain.Picture;

public interface PictureDao {
	
	public Picture loadById(long picID) throws DataAccessException;
	public Picture loadByName(String picName) throws DataAccessException;
	public List<Picture> loadAll() throws DataAccessException;
	public List<Picture> loadAll(long eventID) throws DataAccessException;
	public boolean save(Picture picObject) throws DataAccessException;
	public boolean saveList(List<Picture> picList) throws DataAccessException;
    public boolean delete(Picture picObject) throws DataAccessException;
    public boolean deleteById(Long picId) throws DataAccessException;

}
