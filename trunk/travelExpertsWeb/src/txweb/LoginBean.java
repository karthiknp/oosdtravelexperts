package txweb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * Login bean used in the web session
 * - Will Dixon
 */

public class LoginBean {
	
	private String username = "guest";
	private String password = "guest";
	private String role;
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}
	
	public boolean authenticate(String type) {
		try {
			PreparedStatement pst = TXConnection.getConnection().prepareStatement
				("SELECT * FROM Customers WHERE CustEmail = ? AND Password = ?");
			pst.setString(1, getUsername());
			pst.setString(2, getPassword());
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	

}
