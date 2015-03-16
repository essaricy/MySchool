import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseUtil {

	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
		Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost/demo", "postgres", "sql");
		return connection;
	}

	public static void close(Connection connection) throws SQLException {
		if (connection != null) {
			connection.close();
		}
	}

	public static void close(ResultSet resultSet, Statement statement, Connection connection) throws SQLException {
		if (resultSet != null) {
			resultSet.close();
		}
		if (statement != null) {
			statement.close();
		}
		if (connection != null) {
			connection.close();
		}
	}

	public static void close(ResultSet resultSet, Statement statement) throws SQLException {
		close(resultSet, statement, null);
	}

	public static void close(PreparedStatement preparedStatement,
			Connection connection) throws SQLException {
		close(null, preparedStatement, connection);
	}

	public static void close(Statement statement) throws SQLException {
		close(null, statement, null);
	}

}
