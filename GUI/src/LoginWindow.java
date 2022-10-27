import java.awt.EventQueue;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPasswordField;

public class LoginWindow {

	Connection connection = null;
	private JFrame frame;
	private JTextField EmpID;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow window = new LoginWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	

	
	/**
	 * Create the application.
	 */
	public LoginWindow() {
		initialize();
		connection = sqliteConnector.dbConnector();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 663, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton LoginButton = new JButton("Login");
		LoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				MainMenu mm = new MainMenu();
				mm.setVisible(true);
			}
		});
		LoginButton.setBounds(130, 180, 89, 23);
		frame.getContentPane().add(LoginButton);
		
		EmpID = new JTextField();
		EmpID.setBounds(130, 118, 86, 20);
		frame.getContentPane().add(EmpID);
		EmpID.setColumns(10);
		
		JLabel EmpIDText = new JLabel("Employee ID");
		EmpIDText.setFont(new Font("Tahoma", Font.PLAIN, 16));
		EmpIDText.setBounds(10, 112, 110, 29);
		frame.getContentPane().add(EmpIDText);
		
		JLabel PasswdText = new JLabel("Password");
		PasswdText.setFont(new Font("Tahoma", Font.PLAIN, 16));
		PasswdText.setBounds(10, 152, 110, 29);
		frame.getContentPane().add(PasswdText);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(130, 149, 86, 20);
		frame.getContentPane().add(passwordField);
	}
}
