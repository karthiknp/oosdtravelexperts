
package txweb;

// Import the JDBC classes
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * @author will_ad
 */
public class TXConnection 
{
	// TODO: load from properties file
	private static String dbDriverClassname = "oracle.jdbc.driver.OracleDriver";
	private static String dbConnectionString = "jdbc:oracle:thin:@localhost:1521:xe";
	private static String linuxConnectionString = "jdbc:oracle:thin:@localhost:1521:orant11g";
	private static String dbUsername = "ictoosd";
	private static String dbPassword = "ictoosd";
	
	private static Connection dbConnection = null;
	
	static {
	    try {
			Class.forName(dbDriverClassname);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	    
	    try {
			dbConnection = DriverManager.getConnection(dbConnectionString, dbUsername, dbPassword);
		} catch (SQLException e) {
			try {
				dbConnection = DriverManager.getConnection(linuxConnectionString, dbUsername, dbPassword);
			}
			catch(Exception e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}		
	}
	
	public static Connection getConnection() {
		return dbConnection;
	}	
}
