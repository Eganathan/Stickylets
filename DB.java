import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Notice, do not import com.mysql.cj.jdbc.*
// or you will have problems!

public class DB {

	private String url = "jdbc:mysql://localhost:3306/test_db";
	private Connection conn = null;
	DB() {
		
	
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, "root", "1234");

			// Do something with the Connection

			System.out.println("DONE");
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}

	void startConn() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url, "root", "1234");

			// Do something with the Connection

		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// insert action
	boolean insertNewNoteDB(String data, String title) {

		String sql = "INSERT INTO test_db.notes_test (note_title, note_text) VALUES ('" + title + "' ,'" + data + "')";
		System.out.println(title + "inserting");
		dealQuery(sql);
		return true;
	}

	// Gets the current counter value
	int getNoRows() {

		dealConn();
		int rows = 0;
		// SELECT CustomerName, City FROM Customers;
		String sql = "SELECT note_id FROM test_db.notes_test order by note_id desc limit 1;";

		try {
			Statement statement = conn.prepareStatement(sql);
			ResultSet outputSet = statement.executeQuery(sql);
			while (outputSet.next()) {
				rows = Integer.valueOf(outputSet.getString(1));
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (rows == 0) {
			rows = 1;
		}
		rows++;
		System.out.println(rows + " Counter");
		return rows;
	}

	// Deals the SQL query
	void dealQuery(String sql) {
		dealConn();
		try {
			Statement statement = conn.prepareStatement(sql);
			statement.execute(sql);
			statement.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	// Checks if the connection is still available if not then opens a new conection
	void dealConn() {
		startConn();

	}

	void loadDataFromDB() {
		dealConn();
		String sql = "SELECT * FROM test_db.notes_test WHERE intrash = 0;";

		Notes.titleHMap.clear();
		Notes.textHMap.clear();
		try {
			Statement statement = conn.prepareStatement(sql);
			ResultSet outputSet = statement.executeQuery(sql);
			while (outputSet.next()) {

				int id = outputSet.getInt("note_id");
				String title = outputSet.getString("note_title"); //
				String text = outputSet.getString("note_text");

				Notes.titleHMap.put(id, title);
				Notes.textHMap.put(id, text);
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		loadDELDataFromDB();
		Notes.updateListModal();

	}

	void loadDELDataFromDB() {
		dealConn();
		String sql = "SELECT * FROM test_db.notes_test WHERE intrash >= 1;";

		Notes.titleSHMapDEL.clear();
		Notes.textSHMapDEL.clear();
		try {
			Statement statement = conn.prepareStatement(sql);
			ResultSet outputSet = statement.executeQuery(sql);
			while (outputSet.next()) {

				int id = outputSet.getInt("note_id");
				String title = outputSet.getString("note_title"); //
				String text = outputSet.getString("note_text");

				Notes.titleSHMapDEL.put(id, title);
				Notes.textSHMapDEL.put(id, text);
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	boolean updateTextAndTitle(int id, String title, String text) {
		boolean insert = false;
		dealConn();
		String sql = "UPDATE test_db.notes_test SET  note_text = '" + text + " ' WHERE note_id = " + id;
		String sql1 = "UPDATE test_db.notes_test SET  note_title = '" + title + " ' WHERE note_id = " + id;

		try {

			Statement statement = conn.prepareStatement(sql);
			int s1 = statement.executeUpdate(sql);// execute(sql);
			statement.close();

			Statement statement1 = conn.prepareStatement(sql1);
			int s2 = statement1.executeUpdate(sql1);
			statement1.close();

			System.out.println(s1 + " " + s2);
			Notes.titleHMap.replace(id, title);
			Notes.textHMap.replace(id, text);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Notes.updateListModal();
		return insert;
	}

	boolean deleteRowWithID(int id) {
		boolean insert = false;
		dealConn();
		String sql = "UPDATE test_db.notes_test SET intrash = 1 where note_id =" + id;
		// DELETE FROM `table_name` [WHERE condition];
		try {

			Statement statement = conn.prepareStatement(sql);
			statement.executeUpdate(sql);
			statement.close();
			Notes.titleHMap.remove(id);
			Notes.textHMap.remove(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Notes.updateListModal();
		return insert;
	}

	void updateRestore(int id, int action, String title, String text) {
		dealConn();
		if (action == 1) {

			String sql = "UPDATE test_db.notes_test SET  intrash = 0  WHERE note_id = " + id;

			try {

				Statement statement = conn.prepareStatement(sql);
				statement.executeUpdate(sql);
				Notes.titleHMap.put(id, title);
				Notes.textHMap.put(id, text);
				statement.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else if (action >= 2) {

			String sql = "DELETE FROM test_db.notes_test WHERE note_id = " + id;
			Notes.titleSHMapDEL.remove(id);
			Notes.textSHMapDEL.remove(id);
			try {

				Statement statement = conn.prepareStatement(sql);
				statement.executeUpdate(sql);
				statement.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		Notes.updateListModal();
	}

}