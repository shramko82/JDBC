import java.sql.*;

public class TestSQL {
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://127.0.0.1:3306/mydatabase"; // 127.0.0.1:3306/mydb
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "111";

	private static final String createTableSQL = "CREATE TABLE DBUSER("
			+ "USER_ID INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY, "
			+ "USERNAME VARCHAR(20) NOT NULL, " + "CREATED_DATE DATE NOT NULL)";

	private static Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,
					DB_PASSWORD);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
	}

	private static void createDbUserTable() throws SQLException {
		Connection dbConnection = null;
		Statement statement = null;
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			statement.execute(createTableSQL);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null)
				statement.close();
			if (dbConnection != null)
				dbConnection.close();
		}
	}

	public static void main(String[] args) {
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			createDbUserTable();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}