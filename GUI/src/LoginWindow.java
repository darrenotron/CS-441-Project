import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JPasswordField;
import java.awt.Color;
import java.awt.Toolkit;

public class LoginWindow {

	
	private JFrame frmInventoryManagementSystem;
	private JTextField EmpID;
	private JPasswordField passwordField;
	static Connection connection = null;
	static boolean Supervisor = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginWindow window = new LoginWindow();
					window.frmInventoryManagementSystem.setVisible(true);
					connection = sqliteConnector.dbConnector();
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

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmInventoryManagementSystem = new JFrame();
		frmInventoryManagementSystem.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\dtran\\Desktop\\USU logo.jpg"));
		frmInventoryManagementSystem.setTitle("Inventory Management System");
		frmInventoryManagementSystem.getContentPane().setBackground(new Color(255, 255, 255));
		frmInventoryManagementSystem.setBounds(100, 100, 663, 350);
		frmInventoryManagementSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmInventoryManagementSystem.getContentPane().setLayout(null);
		
		
		
		EmpID = new JTextField();
		EmpID.setBounds(130, 118, 89, 20);
		frmInventoryManagementSystem.getContentPane().add(EmpID);
		EmpID.setColumns(10);
		
		JLabel EmpIDText = new JLabel("Employee ID");
		EmpIDText.setFont(new Font("Tahoma", Font.PLAIN, 16));
		EmpIDText.setBounds(10, 112, 110, 29);
		frmInventoryManagementSystem.getContentPane().add(EmpIDText);
		
		JLabel PasswdText = new JLabel("Password");
		PasswdText.setFont(new Font("Tahoma", Font.PLAIN, 16));
		PasswdText.setBounds(10, 152, 110, 29);
		frmInventoryManagementSystem.getContentPane().add(PasswdText);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(130, 149, 89, 20);
		frmInventoryManagementSystem.getContentPane().add(passwordField);
		
		JButton LoginButton = new JButton("Login");
		LoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "select * from Logins where ID = ? and Password = ?";
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, EmpID.getText());
					pst.setString(2, passwordField.getText());
					
					ResultSet rs = pst.executeQuery();
					int count = 0;
					while(rs.next()) {
						count = count + 1;
						}

					if(count == 1) {
						query = "select Supervisor from Logins where ID = ?";
						pst.setString(1, EmpID.getText());
						
						rs = pst.executeQuery();
						int S = rs.getInt("Supervisor");
						
						if(S == 1) {
							Supervisor = true;
						}
						
						frmInventoryManagementSystem.dispose();
						MainMenu mm = new MainMenu();
						mm.setVisible(true);
					}
					
					else {
						JOptionPane.showMessageDialog(null, "ID or Password is Incorrect");
					}
					
					rs.close();
					pst.close();
					
				
				}catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e);
				}
				
			}
		});
		LoginButton.setBounds(130, 181, 89, 23);
		frmInventoryManagementSystem.getContentPane().add(LoginButton);
		
		JLabel Logo = new JLabel("");
		Image img= new ImageIcon(this.getClass().getResource("/USU logo.jpg")).getImage();
		Logo.setIcon(new ImageIcon(img));
		Logo.setBounds(320, 27, 248, 258);
		frmInventoryManagementSystem.getContentPane().add(Logo);
	}
}
