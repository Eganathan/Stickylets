import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Notice, do not import com.mysql.cj.jdbc.*
// or you will have problems!

public class DB {
	
	
	private String url = "jdbc:mysql://localhost:3306/test_db";
	private String ddUserName = "root";
	private String dbPass = "1234";
	private Connection conn = null;
	private String tableName = "notes_test";
	private String dbName = "test_db";
	private String dataBaseNdTblName = "test_db.notes_test";
	private boolean isConnected = false;
	
	DB(){
		try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            conn =
               DriverManager.getConnection(url,
                                           "root","1234");

            // Do something with the Connection
            
            isConnected = true;
            System.out.println("DONE" );
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	void startConn() {
		try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            conn =
               DriverManager.getConnection(url,
                                           "root","1234");

            // Do something with the Connection
            
            isConnected = true;
            System.out.println("DONE" );
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//insert action 
	boolean insertNewNoteDB(String data,String title){
		
		String sql = "INSERT INTO test_db.notes_test (note_title, note_text) VALUES ('"+title+"' ,'"+data+"')";
		System.out.println(title+ "inserting");
		dealQuery(sql); 
	return true; 
	}
	
	//Gets the current  counter value
		int getNoRows(){
			
			dealConn();
			int rows = 0;
			String res = "";
			//SELECT CustomerName, City FROM Customers;
			String sql = "SELECT COUNT(*) FROM "+ dataBaseNdTblName;
			
			try {
				Statement statement = conn.prepareStatement(sql);
				ResultSet outputSet = statement.executeQuery(sql); //execute(sql);
				while(outputSet.next()) {
				res = outputSet.getString(1);
				}
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(Integer.valueOf(res) == 0) {
				rows = 1;
			}else {
				rows = Integer.valueOf(res);
			}
			
			
			return rows;
		}
		
		//Deals the SQL query 
		void dealQuery(String sql) {
			dealConn();
			try {
				Statement statement = conn.prepareStatement(sql);
				statement.execute(sql);
				System.out.println("Succesfully insert Data - connected!");
				statement.close();
				conn.close();
				isConnected = false;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
			}	
		}
		
		//Checks if the connection is still available if not then opens a new conection
		void dealConn() {
			startConn();
			
		}
		
	void loadDataFromDB() {
		dealConn();
		String sql = "SELECT * FROM test_db.notes_test ";
		String res;
		Notes.textHMap.clear();
		Notes.textHMap.clear();
		try {
			Statement statement = conn.prepareStatement(sql);
			ResultSet outputSet = statement.executeQuery(sql); 
			while(outputSet.next()) {
				
			int id = outputSet.getInt("note_id");
			String title = 	outputSet.getString("note_title"); //
			String text = 	outputSet.getString("note_text");
			
			Notes.titleHMap.put(id,title);
			Notes.textHMap.put(id,text);
			}
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Notes.updateListModal();
		
	}
	
	boolean updateTextAndTitle(int id, String title, String text){
		boolean insert = false;
		dealConn();
		String sql = "UPDATE test_db.notes_test SET  note_text = '"+ text +"' WHERE note_id = "+id;
		String sql1 = "UPDATE test_db.notes_test SET  note_title = '" +title+ "' WHERE note_id = "+id;

		try {
			
			Statement statement = conn.prepareStatement(sql);
			int s1 = statement.executeUpdate(sql);//execute(sql);
			statement.close();
			
			Statement statement1 = conn.prepareStatement(sql1);
			int s2 = statement1.executeUpdate(sql1);
			statement1.close();
			
			System.out.println(s1+" "+ s2 );
			Notes.titleHMap.replace(id,title);
			Notes.textHMap.replace(id, sql1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		Notes.updateListModal();
		return insert;
	}
	
	boolean deleteRowWithID(int id){
		boolean insert = false;
		dealConn();
		String sql = "DELETE FROM test_db.notes_test WHERE note_id = "+id;
		//DELETE FROM `table_name` [WHERE condition];
		try {
			
			Statement statement = conn.prepareStatement(sql);
			int s1 = statement.executeUpdate(sql);
			statement.close();
			Notes.titleHMap.remove(id);
			Notes.textHMap.remove(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		Notes.updateListModal();
		return insert;
	}

    
    
    
}