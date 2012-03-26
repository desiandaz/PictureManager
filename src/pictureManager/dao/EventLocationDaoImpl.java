package pictureManager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import pictureManager.domain.EventLocation;

public class EventLocationDaoImpl extends AbstractDatabaseDao implements EventLocationDao {

	@Override
	public EventLocation loadById(long evlId) throws DataAccessException {
		try {
			Connection connection = super.getConnection();
			try {
				PreparedStatement stmt = connection
						.prepareStatement("SELECT * FROM EventLocationTable WHERE id=?");
				try {
					stmt.setLong(1, evlId);
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
			throw new DataAccessException("Failed to get event location by id: " + evlId, e);
		}
	}

	@Override
	public boolean save(EventLocation evlObject) throws DataAccessException {

		if (evlObject.isIdSet()) {
			return (this.update(evlObject));
		} else {
			return (this.create(evlObject));
		}
	}
	
	public boolean create(EventLocation evlObject) throws DataAccessException {
		try {
			Connection connection = super.getConnection();
			try {
				PreparedStatement stmt = connection
						.prepareStatement("INSERT INTO EventLocationTable (streetAddress, city, state, country, lattitude, longitude) VALUES (?, ?, ?, ?, ?, ?)");
				try {
					stmt.setString(1, evlObject.getStreetAddress());
					stmt.setString(2, evlObject.getCity());
					stmt.setString(3, evlObject.getState());
					stmt.setString(4, evlObject.getCountry());
					stmt.setDouble(5, evlObject.getLattitude());
					stmt.setDouble(6, evlObject.getLongitude());
					
					stmt.executeUpdate();
					evlObject.setId(super.getLastInsertId(connection));
					return true;
					
				} finally {
					stmt.close();
				}
			
			} finally {
				connection.close();
			}
		} catch (SQLException e) {
			throw new DataAccessException("Failed to create event location: " + evlObject.toString(), e);
		}
	}

	public boolean update(EventLocation evlObject) throws DataAccessException {
		try {
			Connection connection = super.getConnection();
			try {
				PreparedStatement stmt = connection
						.prepareStatement("UPDATE EventLocationTable SET streetAddress=?, city=?, state=?, country=?, lattitude=?, longitude=? where id = ?");
				try {
					
					stmt.setString(1, evlObject.getStreetAddress());
					stmt.setString(2, evlObject.getCity());
					stmt.setString(3, evlObject.getState());
					stmt.setString(4, evlObject.getCountry());
					stmt.setDouble(5, evlObject.getLattitude());
					stmt.setDouble(6, evlObject.getLongitude());
					stmt.executeUpdate();
					
					evlObject.setId(super.getLastInsertId(connection));
					return true;
					
				} finally {
					stmt.close();
				}
			
			} finally {
				connection.close();
			}
		} catch (SQLException e) {
			throw new DataAccessException("Failed to update event location: " + evlObject.toString(), e);
		}
	}

	@Override
	public boolean deleteById(long evlId) throws DataAccessException {
		try {
			Connection connection = super.getConnection();
			try {
				PreparedStatement stmt = connection
						.prepareStatement("DELETE FROM EventLocationTable WHERE id=?");
				try {
					stmt.setLong(1, evlId);
					stmt.executeUpdate();
					return true;
				} finally {
					stmt.close();
				}
			} finally {
				connection.close();
			}
		} catch (SQLException e) {
			throw new DataAccessException("Failed to delete event location by id: " + evlId, e);
		}
	}

	@Override
	public void setLatLong(long evlId) throws DataAccessException {
		/*try {
			Connection connection = super.getConnection();
			try {
				PreparedStatement stmt = connection.prepareStatement("DELETE FROM PictureTable WHERE id=?");
				try {
					stmt.setLong(1, evlId);
					stmt.executeUpdate();
					return true;
				} finally {
					stmt.close();
				}
			} finally {
				connection.close();
			}
		} catch (SQLException e) {
			throw new DataAccessException("Failed to delete picture by id: " + evlId, e);
		}*/
	}
	
	private EventLocation loadFromRow(ResultSet row) throws SQLException {
		EventLocation evlObject = new EventLocation();
		
		evlObject.setId(row.getLong("id"));
		evlObject.setStreetAddress(row.getString("streetAddress"));
		evlObject.setCity(row.getString("city"));
		evlObject.setState(row.getString("state"));
		evlObject.setCountry(row.getString("country"));
		evlObject.setLattitude(row.getDouble("lattitude"));
		evlObject.setLongitude(row.getDouble("longitude"));

		return evlObject;
	}
	
}
