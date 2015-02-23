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
						"1. Create Employees Table\n"
								+ "2. Create Products Table\n"
								+ "3. Insert data into Employees Table\n"
								+ "4. Insert data into Orders Table\n"
								+ "5. Retrieve data for Employees Table\n"
								+ "6. Retrieve data for Orders Table\n"
								+ "7. Update Employees Table\n"
								+ "8. Update Employees Table Using a Prepared Statement\n"
								+ "9. Update many records of Orders Table Using a Prepared Statement\n"
								+ "10. List the name of employees who bought CD'sn"
								+ "0. Exit\n\n" + "Enter your choice");
		if (choice == null)
			System.exit(0);
		ch = Integer.parseInt(choice);

		return ch;

	}

	public static void getSelected(int choice) {
		if (choice == 1) {
			createEmployees();
		}
		if (choice == 2) {
			createOrders();
		}
		if (choice == 3) {
			insertEmployees();
		}
		if (choice == 4) {
			insertOrders();
		}
		if (choice == 5) {
			retrieveEmployees();
		}
		if (choice == 6) {
			retrieveOrders();
		}
		if (choice == 7) {
			updateEmployees();
		}
		if (choice == 8) {
			updateEmployeesPrepared();
		}
		if (choice == 9) {
			updateOrdersPrepared();
		}
		if (choice == 10) {
			dynamicQuery();
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

	public static void createEmployees() {
		Connection con = getConnection();

		String createString;
		createString = "create table Employees ("
				+ "ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
				+ "Name VARCHAR(30) NOT NULL)";
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(createString);
			stmt.close();
			con.close();
			JOptionPane.showMessageDialog(null, "Employees Table Created");
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}

	}

	/*
	 * CREATE TABLE Orders ( Prod_ID INTEGER, ProductName VARCHAR(20),
	 * Employee_ID INTEGER );
	 */

	public static void createOrders() {
		Connection con = getConnection();

		String createString;
		createString = "create table Orders ("
				+ "ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
				+ "ProductName VARCHAR(20) NOT NULL, "
				+ "Employee_ID INTEGER )";

		try {
			stmt = con.createStatement();
			stmt.executeUpdate(createString);

			stmt.close();
			con.close();
			JOptionPane.showMessageDialog(null, "Orders Table Created");
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}

	}

	/*
	 * Employee_ID Name 6323 Hemanth 5768 Bob 1234 Shawn 5678 Michaels
	 */
	public static void insertEmployees() {
		Connection con = getConnection();

		String insertString1, insertString2, insertString3, insertString4;
		insertString1 = "insert into Employees values(6323, 'Hemanth')";
		insertString2 = "insert into Employees values(5768, 'Bob')";
		insertString3 = "insert into Employees values(1234, 'Shawn')";
		insertString4 = "insert into Employees values(5678, 'Michaels')";

		try {
			stmt = con.createStatement();
			stmt.executeUpdate(insertString1);
			stmt.executeUpdate(insertString2);
			stmt.executeUpdate(insertString3);
			stmt.executeUpdate(insertString4);

			stmt.close();
			con.close();
			JOptionPane.showMessageDialog(null,
					"Data Inserted into Employees Table");
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}

	}

	/*
	 * Prod_ID ProductName Employee_ID 543 Belt 6323 432 Bottle 1234 876 Ring
	 * 5678
	 */
	public static void insertOrders() {
		Connection con = getConnection();

		String insertString1, insertString2, insertString3;
		insertString1 = "insert into Orders values(543, 'Belt', 6323)";
		insertString2 = "insert into Orders values(432, 'Bottle', 1234)";
		insertString3 = "insert into Orders values(876, 'Ring', 5678)";

		try {
			stmt = con.createStatement();
			stmt.executeUpdate(insertString1);
			stmt.executeUpdate(insertString2);
			stmt.executeUpdate(insertString3);

			stmt.close();
			con.close();
			JOptionPane.showMessageDialog(null,
					"Data Inserted into Orders Table");
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}

	}

	public static void retrieveEmployees() {
		Connection con = getConnection();
		String result = null;
		String selectString;
		selectString = "select * from Employees";
		result = "ID || Name\n";
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(selectString);
			while (rs.next()) {
				int id = rs.getInt("ID");
				String name = rs.getString("Name");
				result += id + " || " + name + "\n";
			}
			stmt.close();
			con.close();
			JOptionPane.showMessageDialog(null, result);
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}

	}

	public static void retrieveOrders() {
		Connection con = getConnection();
		String result = null;
		String selectString;
		selectString = "select * from Orders";
		result = "ID || ProductName || Employee_ID\n";
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(selectString);
			while (rs.next()) {
				int pr_id = rs.getInt("ID");
				String prodName = rs.getString("ProductName");
				int id = rs.getInt("Employee_ID");
				result += pr_id + " || " + prodName + " || " + id + "\n";
			}
			stmt.close();
			con.close();
			JOptionPane.showMessageDialog(null, result);
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}

	}

	public static void updateEmployees(){
		Connection con = getConnection();

		String updateString1;
		updateString1 = "update Employees set name = 'hemanthbalaji' where id = 6323";

		try {
			stmt = con.createStatement();
	   		stmt.executeUpdate(updateString1);

			stmt.close();
			con.close();

		} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}
	JOptionPane.showMessageDialog(null,"Data Updated into Employees Table");
	}

	public static void updateEmployeesPrepared() {
		Connection con = getConnection();
		// create prepared statement
		try {
			pstmt = con
					.prepareStatement("update Employees set name = ? where Id  = ?");
			pstmt.setString(1, "hemanthbob"); // Note index starts with 1
			pstmt.setInt(2, 6323);

			pstmt.executeUpdate();

			pstmt.close();
			con.close();

		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}
		JOptionPane
				.showMessageDialog(null, "Data Updated into Employees Table");
	}

	public static void updateOrdersPrepared() {

		int[] productIds = { 543, 432, 876 };
		String[] productNames = { "cds", "dvds", "Espresso" };
		int len = productNames.length;

		Connection con = getConnection();

		try {
			pstmt = con
					.prepareStatement("update Orders set productname = ? where Id  = ?");
			for (int i = 0; i < len; i++) {
				pstmt.setInt(2, productIds[i]);
				pstmt.setString(1, productNames[i]);
				pstmt.executeUpdate();
			}

			pstmt.close();
			con.close();

		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}
		JOptionPane.showMessageDialog(null, "Data Updated into Orders Table");
	}

	public static void dynamicQuery(){
		Connection con = getConnection();
		String result = null;
		String selectString;
		selectString = "select Employees.name from Employees, Orders where productname = 'cds' " +
			"and Employees.id = Orders.employee_id ";
	    result ="Name ";
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(selectString);
			while (rs.next()) {
			    String name = rs.getString("Name");
			    result+=name+", ";
			}
			stmt.close();
			con.close();

		} catch(SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}
	JOptionPane.showMessageDialog(null, result);
	}
}// End of class