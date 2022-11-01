import java.sql.*;
import javax.swing.*;
public class sqliteConnector {
	
	Connection conn = null;
	public static Connection dbConnector()
	{
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\dtran\\441 Prj\\Inventory.sqlite");
			//JOptionPane.showMessageDialog(null, "Database Connected");
			return conn;
			
		}catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
}

