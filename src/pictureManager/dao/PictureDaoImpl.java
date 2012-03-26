package pictureManager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pictureManager.domain.Picture;

public class PictureDaoImpl extends AbstractDatabaseDao implements PictureDao {

	@Override
	public Picture loadById(long picId) throws DataAccessException {
		try {
			Connection connection = super.getConnection();
			try {
				PreparedStatement stmt = connection
						.prepareStatement("SELECT id, pictureName, pictureCreated, pictureDimensionWidth, pictureDimensionHeight, pictureStorageLocation FROM PictureTable WHERE id=?");
				try {
					stmt.setLong(1, picId);
					ResultSet rs = stmt.executeQuery();
					try {
						return rs.next() ? this.loadFromRow(rs) : null;
					} finally {
						rs.close();
					}
				} finally {
					stmt.close();
				}
			} finally {
				connection.close();
			}
		} catch (SQLException e) {
			throw new DataAccessException("Failed to get picture by id: " + picId, e);
		}
	}

	@Override
	public Picture loadByName(String picName) throws DataAccessException {
		try {
			Connection connection = super.getConnection();
			try {
				PreparedStatement stmt = connection
						.prepareStatement("SELECT id, pictureName, pictureCreated, pictureDimensionWidth, pictureDimensionHeight, pictureStorageLocation FROM PictureTable WHERE pictureName LIKE ?");
				try {
					stmt.setString(1, picName);
					ResultSet rs = stmt.executeQuery();
					try {
						return rs.next() ? this.loadFromRow(rs) : null;
					} finally {
						rs.close();
					}
				} finally {
					stmt.close();
				}
			} finally {
				connection.close();
			}
		} catch (SQLException e) {
			throw new DataAccessException("Failed to get picture by name: " + picName, e);
		}
	}

	
	@Override
	public List<Picture> loadAll(long eventId) throws DataAccessException {
		try {
			Connection connection = super.getConnection();
			try {
				PreparedStatement stmt = connection
						.prepareStatement("SELECT id, pictureName, pictureCreated, pictureDimensionWidth, pictureDimensionHeight, pictureStorageLocation FROM PictureTable WHERE pictureEventiId=?");
					try {
						stmt.setLong(1, eventId);
						ResultSet rs = stmt.executeQuery();
						try{
							List<Picture> picList = new ArrayList<Picture>();
							while (rs.next()) {
								picList.add(this.loadFromRow(rs));
							}
							return picList;
					} finally {
						rs.close();
					}
				} finally {
					stmt.close();
				}
			} finally {
				connection.close();
			}
		} catch (SQLException e) {
			throw new DataAccessException("Failed to get all pictures", e);
		}
	}

	@Override
	public List<Picture> loadAll() throws DataAccessException {
		try {
			Connection connection = super.getConnection();
			try {
				Statement stmt = connection.createStatement();
				try {
					ResultSet rs = stmt
							.executeQuery("SELECT id, pictureName, pictureDimensionWidth, pictureDimensionHeight, pictureStorageLocation, pictureCreated FROM PictureTable ORDER BY pictureName");
					try {
						List<Picture> picList = new ArrayList<Picture>();
						while (rs.next()) {
							picList.add(this.loadFromRow(rs));
						}
						return picList;
					} finally {
						rs.close();
					}
				} finally {
					stmt.close();
				}
			} finally {
				connection.close();
			}
		} catch (SQLException e) {
			throw new DataAccessException("Failed to get all pictures", e);
		}
	}

	@Override
	public boolean save(Picture picObject) throws DataAccessException {

		if (picObject.isIdSet()) {
			return (this.update(picObject));
		} else {
			return (this.create(picObject));
		}
	}
	
	public boolean create(Picture picObject) throws DataAccessException {
		try {
			Connection connection = super.getConnection();
			try {
				PreparedStatement stmt = connection
						.prepareStatement("INSERT INTO PictureTable (pictureName, pictureCreated, pictureDimensionWidth, pictureDimensionHeight, pictureStorageLocation) VALUES (?, ?, ?, ?, ?)");
				try {
					stmt.setString(1, picObject.getPictureName());
					stmt.setTimestamp(2, picObject.getPictureCreated());
					stmt.setInt(3, picObject.getPictureDimensionWidth());
					stmt.setInt(4, picObject.getPictureDimensionHeight());
					stmt.setString(5, picObject.getPictureLocation());
					
					stmt.executeUpdate();
					picObject.setId(super.getLastInsertId(connection));
					return true;
					
				} finally {
					stmt.close();
				}
			
			} finally {
				connection.close();
			}
		} catch (SQLException e) {
			throw new DataAccessException("Failed to create picture: " + picObject.getPictureName(), e);
		}
	}

	public boolean update(Picture picObject) throws DataAccessException {
		try {
			Connection connection = super.getConnection();
			try {
				PreparedStatement stmt = connection
						.prepareStatement("UPDATE PictureTable SET pictureName=?, pictureDimensionWidth=?, pictureDimensionHeight=?, pictureStorageLocation=? where id = ?");
				try {
					
					stmt.setString(1, picObject.getPictureName());
					stmt.setInt(3, picObject.getPictureDimensionWidth());
					stmt.setInt(4, picObject.getPictureDimensionHeight());
					stmt.setString(5, picObject.getPictureLocation());
					stmt.setLong(6, picObject.getId());
					stmt.executeUpdate();
					
					picObject.setId(super.getLastInsertId(connection));
					return true;
					
				} finally {
					stmt.close();
				}
			
			} finally {
				connection.close();
			}
		} catch (SQLException e) {
			throw new DataAccessException("Failed to update picture by name: " + picObject.getPictureName(), e);
		}
	}

	@Override
	public boolean saveList(List<Picture> picList) throws DataAccessException {
		for (Picture picObject : picList){
			this.save(picObject);
		}
		return false;
	}

	@Override
	public boolean delete(Picture picObject) throws DataAccessException {
		this.deleteById(picObject.getId());
		return false;

	}

	@Override
	public boolean deleteById(Long picId) throws DataAccessException {
		try {
			Connection connection = super.getConnection();
			try {
				PreparedStatement stmt = connection
						.prepareStatement("DELETE FROM PictureTable WHERE id=?");
				try {
					stmt.setLong(1, picId);
					stmt.executeUpdate();
					return true;
				} finally {
					stmt.close();
				}
			} finally {
				connection.close();
			}
		} catch (SQLException e) {
			throw new DataAccessException("Failed to delete picture by id: " + picId, e);
		}
	}

	private Picture loadFromRow(ResultSet row) throws SQLException {
		Picture picObject = new Picture();
		
		picObject.setId(row.getLong("id"));
		picObject.setPictureName(row.getString("pictureName"));
		picObject.setPictureDimensionWidth(row.getInt("pictureDimensionWidth"));
		picObject.setPictureDimensionHeight(row.getInt("pictureDimensionHeight"));
		picObject.setPictureLocation(row.getString("pictureStorageLocation"));
		picObject.setPictureCreated(row.getTimestamp("pictureCreated"));

		return picObject;
	}

}
