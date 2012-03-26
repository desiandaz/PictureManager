package pictureManager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pictureManager.domain.Event;
import pictureManager.domain.EventLocation;

public class EventDaoImpl extends AbstractDatabaseDao implements EventDao {

	@Override
	public Event loadById(long eveId) throws DataAccessException {
		try {
			Connection connection = super.getConnection();
			try {
				PreparedStatement stmt = connection
						.prepareStatement("SELECT * FROM EventTable WHERE id=?");
				try {
					stmt.setLong(1, eveId);
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
			throw new DataAccessException("Failed to get event: " + eveId, e);
		}
	}

	@Override
	public Event loadByName(String eveName) throws DataAccessException {
		try {
			Connection connection = super.getConnection();
			try {
				PreparedStatement stmt = connection
						.prepareStatement("SELECT * FROM EventTable WHERE eventName LIKE ?");
				try {
					stmt.setString(1, eveName);
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
			throw new DataAccessException("Failed to get event by name: " + eveName, e);
		}
	}

	@Override
	public List<Event> loadAll() throws DataAccessException {
		try {
			Connection connection = super.getConnection();
			try {
				Statement stmt = connection.createStatement();
				try {
					ResultSet rs = stmt
							.executeQuery("SELECT * FROM EventTable ORDER BY eventName");
					try {
						List<Event> eveList = new ArrayList<Event>();
						while (rs.next()) {
							eveList.add(this.loadFromRow(rs));
						}
						return eveList;
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
	public boolean save(Event eveObject) throws DataAccessException {

		if (eveObject.isIdSet()) {
			return (this.update(eveObject));
		} else {
			return (this.create(eveObject));
		}
	}
	
	public boolean create(Event eveObject) throws DataAccessException {
		try {
			Connection connection = super.getConnection();
			try {
				PreparedStatement stmt = connection
						.prepareStatement("INSERT INTO EventTable (eventName, eventDate, eventLocationId) VALUES (?, ?, ?)");
				try {
					stmt.setString(1, eveObject.getEventName());
					stmt.setTimestamp(2, eveObject.getEventDate());
					stmt.setLong(3, eveObject.getEventLocationId());
					stmt.executeUpdate();
					eveObject.setId(super.getLastInsertId(connection));
					return true;
					
				} finally {
					stmt.close();
				}
			
			} finally {
				connection.close();
			}
		} catch (SQLException e) {
			throw new DataAccessException("Failed to create picture: " + eveObject.toString(), e);
		}
	}

	public boolean update(Event eveObject) throws DataAccessException {
		try {
			Connection connection = super.getConnection();
			try {
				PreparedStatement stmt = connection
						.prepareStatement("UPDATE EventTable SET eventName=?, eventDate=?, eventLocation=? where id = ?");
				try {
					
					stmt.setString(1, eveObject.getEventName());
					stmt.setTimestamp(2, eveObject.getEventDate());
					EventLocation evlObject = eveObject.getEventLocation();
					stmt.setLong(3, evlObject.getId());
					stmt.setLong(4, eveObject.getId());
					stmt.executeUpdate();
					
					eveObject.setId(super.getLastInsertId(connection));
					return true;
					
				} finally {
					stmt.close();
				}
			
			} finally {
				connection.close();
			}
		} catch (SQLException e) {
			throw new DataAccessException("Failed to update event: " + eveObject.toString(), e);
		}
	}

	@Override
	public boolean saveList(List<Event> eveList) throws DataAccessException {
		for (Event eveObject : eveList){
			this.save(eveObject);
		}
		return false;
	}

	@Override
	public boolean delete(Event eveObject) throws DataAccessException {
		this.deleteById(eveObject.getId());
		return false;

	}

	@Override
	public boolean deleteById(Long eveId) throws DataAccessException {
		try {
			Connection connection = super.getConnection();
			try {
				PreparedStatement stmt = connection
						.prepareStatement("DELETE FROM EventTable WHERE id=?");
				try {
					stmt.setLong(1, eveId);
					stmt.executeUpdate();
					return true;
				} finally {
					stmt.close();
				}
			} finally {
				connection.close();
			}
		} catch (SQLException e) {
			throw new DataAccessException("Failed to delete event: " + eveId, e);
		}
	}

	private Event loadFromRow(ResultSet row) throws SQLException {
		Event eveObject = new Event();
		EventLocationDao evlDaoObject = new EventLocationDaoImpl();
		
		eveObject.setId(row.getLong("id"));
		eveObject.setEventName(row.getString("eventName"));
		eveObject.setEventDate(row.getTimestamp("eventDate"));
		eveObject.setEventLocationId(row.getLong("eventLocationId"));
		eveObject.setEventLocation(evlDaoObject.loadById(row.getLong("eventLocationId")));

		return eveObject;
	}

}
