import javax.swing.JOptionPane;
import java.sql.*;

public class JDBCProgram {

	static String userid = "root", password = "111";
	static String url = "jdbc:mysql://127.0.0.1:3306/mydatabase";
	static Statement stmt;
	static PreparedStatement pstmt;
	static Connection con;

	public static void main(String args[]) {

		// JOptionPane.showMessageDialog(null,"JDBC Programming showing Retrieval of Table Data");
		int choice = -1;

		choice = getChoice();
		if (choice != 0) {
			getSelected(choice);
		}

		System.exit(0);
	}

	public static int getChoice() {
		String choice;
		int ch;
		choice = JOptionPane
				.showInputDialog(
						null,
						"1. Create Users Table\n"
								+ "2. Add data into Users Table\n"
								+ "3. Retrieve data from Users Table\n"
								+ "4. Retrieve data from Users Table by Name\n"
								+ "0. Exit\n\n" + "Enter your choice");
		if (choice == null)
			System.exit(0);
		ch = Integer.parseInt(choice);

		return ch;

	}

	public static void getSelected(int choice) {
		if (choice == 1) {
			createUsers();
		}
		if (choice == 2) {
			addUser();
		}
		if (choice == 3) {
			retrieveUsers();
		}
		if (choice == 4) {
			retrieveUsersWithStatement();
		}
	}

	public static Connection getConnection() {

		try {
			Class.forName("com.mysql.jdbc.Driver");

		} catch (java.lang.ClassNotFoundException e) {
			System.err.print("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		}

		try {
			con = DriverManager.getConnection(url, userid, password);

		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}

		return con;
	}

	public static void createUsers() {
		Connection con = getConnection();

		String createString;
		createString = "create table Users ("
				+ "ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
				+ "Name VARCHAR(30) NOT NULL, "
				+ "LastName VARCHAR(30), "
				+ "Date_Birth Date)";
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(createString);
			stmt.close();
			con.close();
			JOptionPane.showMessageDialog(null, "Users Table Created");
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}

	}

	public static void addUser() {
		Connection con = getConnection();

		String n1 = JOptionPane.showInputDialog(null, "Insert Name");
		String n2 = JOptionPane.showInputDialog(null, "Insert Last Name");
		String n3 = JOptionPane.showInputDialog(null, "Insert Date of birth (YYYY-MM-DD)");
		

		String insertString = "insert into users (Name,LastName,Date_Birth) "
				+ "values ('"+n1+"','"+n2+"','"+n3+"')";

		try {
			stmt = con.createStatement();
			stmt.executeUpdate(insertString);

			stmt.close();
			con.close();
			JOptionPane.showMessageDialog(null,
					"Data Inserted into Users Table");
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}

	}

	public static void retrieveUsers() {
		Connection con = getConnection();
		String result = null;
		String selectString;
		selectString = "select * from Users";
		result = "ID || Name || LastName || Date_Birth \n\n";
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(selectString);
			while (rs.next()) {
				int id = rs.getInt("ID");
				String name = rs.getString("Name");
				String lastName = rs.getString("LastName");
				String date = rs.getString("Date_Birth");
				result += id + " || " + name + " || " + lastName + " || " + date + "\n";
			}
			stmt.close();
			con.close();
			JOptionPane.showMessageDialog(null, result);
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}

	}

	public static void retrieveUsersWithStatement(){
		Connection con = getConnection();
		String result = null;
		String selectString;
		String n1 = JOptionPane.showInputDialog(null, "Insert Name");
		selectString = "select Name, LastName from Users where Name = '"+n1+"' ";
	    result ="Name || LastName \n\n";
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(selectString);
			while (rs.next()) {
			    String name = rs.getString("Name");
			    String lastName = rs.getString("LastName");
			    result+=name+" || "+lastName+ "\n";
			}
			stmt.close();
			con.close();

		} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}
	JOptionPane.showMessageDialog(null, result);
	}
}// End of class