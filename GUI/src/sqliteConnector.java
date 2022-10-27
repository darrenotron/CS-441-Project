import java.sql.*;
import javax.swing.*;
public class sqliteConnector {
	
	Connection conn = null;
	public static Connection dbConnector()
	{
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:‪Inventory.db");
			//JOptionPane.showMessageDialog(null, "Database Connected");
			return conn;
			
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
}

