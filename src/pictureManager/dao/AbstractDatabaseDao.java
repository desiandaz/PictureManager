package pictureManager.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractDatabaseDao {


    protected Connection getConnection() throws DataAccessException {
    	Connection connection = null;    
    	try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/PictureManager","-","-");
        } catch (SQLException e) {
            throw new DataAccessException("Failed to get connection from DataSource: ", e);
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	return connection;
    }

    protected String getLastInsertIdSql() {
        return "SELECT @@IDENTITY";
    }

    protected Long getLastInsertId(Connection connection) throws DataAccessException {
        try {
            Statement stmt = connection.createStatement();
            try {
                ResultSet rs = stmt.executeQuery(this.getLastInsertIdSql());
                try {
                    if (rs.next()) {
                        return rs.getLong(1);
                    } else {
                        throw new DataAccessException(
                                "Last insert key id is missing");
                    }
                } finally {
                    rs.close();
                }
            } finally {
                stmt.close();
            }
        } catch (SQLException e) {
            throw new DataAccessException("Failed to get last insert id", e);
        }
    }
}
