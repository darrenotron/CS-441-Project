import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.filechooser.*;
import java.io.*;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.io.File;
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.IOException;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.*;  
import org.apache.poi.ss.usermodel.Sheet;  
import org.apache.poi.ss.usermodel.Workbook;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;  

@SuppressWarnings("unused")
public class MainMenu extends JFrame {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu frame = new MainMenu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
		
static Connection connection = null; //connection
static boolean Super = false; //use to keep supervisor permission after first password input
private JTable InvTable; //tables and text fields
private JTextField IDNum;
private JTextField ItemName;
private JTextField Quant;
private JTextField Price;
private JTextField Date;
private JTextField IDtoRemoveModify;
private JTextField NewValueField;

JFileChooser chooser; //file explorer
String sourceFolder = ""; //location of file
String theFile  =""; //file name
static String SpreadSheetPath = ""; //path to spreadsheet to be used, global

//user uses file explorer to select spreadsheet they want to import
public void GetSpreadSheet()
{
	chooser = new JFileChooser(); //file chooser
	chooser.setCurrentDirectory(new java.io.File("C:\\Users\\dtran\\Desktop")); //default to desktop for ease of use
	FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "Spreadsheets", "xlsx"); //filter by xl files 
	     chooser.setFileFilter(filter);
	     chooser.setFileSelectionMode(JFileChooser.FILES_ONLY); //show file explorer
	   
	     if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { //when approved file selected
	         
	         String dirr = "" + chooser.getCurrentDirectory(); //get directory of file
	         File file = chooser.getSelectedFile(); //get file name
	     
	       
	      if(dirr.substring(dirr.length()-1,dirr.length()).equals(".")){ //in case of current directory fix period
	           dirr = dirr.substring(0,dirr.length()-1);
	           sourceFolder=""+dirr + "" + file.getName();
	        }else{
	            
	            sourceFolder=""+dirr + "/" + file.getName(); //get source folder
	        }

	          SpreadSheetPath = dirr + "\\" + file.getName(); //assign spreadsheet location to global variable
	     }
	          
}


//reads the specific cell from the given spreadsheet. returns value of cell in chosen locations
@SuppressWarnings("deprecation")
public static String SpreadSheetReadCell(int sRow, int sColumn) {
	String value=null;         
	Workbook wb=null; 
	try {  
		FileInputStream fis=new FileInputStream(new File(SpreadSheetPath)); //use assigned path to find file
		wb=new XSSFWorkbook(fis);  //declare workbook to work with file
		
	}catch(Exception e) {
		e.printStackTrace();  
	}
	
	Sheet sheet=wb.getSheetAt(0);   //get sheet
	Row row = sheet.getRow(sRow); //get specific row
	Cell cell=row.getCell(sColumn);  //get specific column
	cell.setCellType(Cell.CELL_TYPE_STRING); //set cell type to string to fix input
	value=cell.getStringCellValue();      //get value
	return value;               //return value
}

public static void ImportSpreadSheet() { //import spreadsheet data row by row
	String value=null;         
	Workbook wb=null; 
	int entries = 0;
	try {  
		FileInputStream fis=new FileInputStream(new File(SpreadSheetPath)); //get spreadsheet path
		wb=new XSSFWorkbook(fis);  
		
		
	}catch(Exception e) {
		e.printStackTrace();  
	}
	
	Sheet sheet=wb.getSheetAt(0); //get sheet
	entries = sheet.getLastRowNum(); //get row count, accounts for title row on 0
	
	for(int i = 1; i <= entries; i++) //go through each line
	{
		try {
			String query = "Insert into Inv (IDNumber,ItemName, Quantity, Price, DateAdded) values (?, ?, ?, ?, ?)"; //insert into sql table
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, SpreadSheetReadCell(i, 0)); //id num
			pst.setString(2, SpreadSheetReadCell(i, 1)); //name
			pst.setString(3, SpreadSheetReadCell(i, 2)); //quant
			pst.setString(4, SpreadSheetReadCell(i, 3)); //price
			pst.setString(5, SpreadSheetReadCell(i, 4)); //date
			pst.execute();	
			
		}catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	JOptionPane.showMessageDialog(null, entries + " items added"); //tell user how many items added
	
}

	/**
	 * Create the frame.
	 */
	public MainMenu() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\dtran\\Desktop\\USU logo.jpg"));
		setTitle("Inventory Management System");
		connection = sqliteConnector.dbConnector(); //connect to database
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 663, 350);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 192));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(10, 69, 627, 231);
		contentPane.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		JPanel ViewInvPanel = new JPanel();
		ViewInvPanel.setBackground(new Color(255, 255, 255));
		layeredPane.setLayer(ViewInvPanel, -1);
		layeredPane.add(ViewInvPanel, "name_1659376283470600");
		ViewInvPanel.setLayout(null);
		
		try { //display table for first time
			String query = "select * from Inv";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(0, 0, 627, 231);
			ViewInvPanel.add(scrollPane);
			
			InvTable = new JTable();
			scrollPane.setViewportView(InvTable);
			InvTable.setModel(DbUtils.resultSetToTableModel(rs));

			
		}catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		JPanel AddItemPanel = new JPanel();
		AddItemPanel.setBackground(new Color(255, 255, 255));
		layeredPane.add(AddItemPanel, "name_1659379628347400");
		AddItemPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ID Number");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 79, 28);
		AddItemPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("Item Name");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(109, 11, 79, 28);
		AddItemPanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Quantity");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(205, 11, 79, 28);
		AddItemPanel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Price");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(301, 11, 79, 28);
		AddItemPanel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Date Added");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setBounds(397, 11, 79, 28);
		AddItemPanel.add(lblNewLabel_5);
		
		IDNum = new JTextField();
		IDNum.setHorizontalAlignment(SwingConstants.LEFT);
		IDNum.setBounds(10, 50, 86, 20);
		AddItemPanel.add(IDNum);
		IDNum.setColumns(10);
		
		ItemName = new JTextField();
		ItemName.setBounds(109, 50, 86, 20);
		AddItemPanel.add(ItemName);
		ItemName.setColumns(10);
		
		Quant = new JTextField();
		Quant.setBounds(205, 50, 86, 20);
		AddItemPanel.add(Quant);
		Quant.setColumns(10);
		
		Price = new JTextField();
		Price.setBounds(301, 50, 86, 20);
		AddItemPanel.add(Price);
		Price.setColumns(10);
		
		Date = new JTextField();
		Date.setBounds(397, 50, 86, 20);
		AddItemPanel.add(Date);
		Date.setColumns(10);
		
		JButton AddItemButton = new JButton("Add Item");
		AddItemButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		AddItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "Insert into Inv (IDNumber,ItemName, Quantity, Price, DateAdded) values (?, ?, ?, ?, ?)"; //add item to table with given values
					PreparedStatement pst = connection.prepareStatement(query);
					pst.setString(1, IDNum.getText());
					pst.setString(2, ItemName.getText());
					pst.setString(3, Quant.getText());
					pst.setString(4, Price.getText());
					pst.setString(5, Date.getText());
					pst.execute();	
					JOptionPane.showMessageDialog(null, "Entry Added");
				}catch (Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		AddItemButton.setBounds(493, 50, 89, 20);
		AddItemPanel.add(AddItemButton);
		
		JButton ChooseFileButton = new JButton("");
		ChooseFileButton.setIcon(new ImageIcon("C:\\Users\\dtran\\Downloads\\ImportImage.png"));
		ChooseFileButton.addActionListener(new ActionListener() { //choose xl file then import contents
			public void actionPerformed(ActionEvent e) {
				GetSpreadSheet();
				ImportSpreadSheet();
			}
		});
		ChooseFileButton.setBounds(253, 141, 101, 79);
		AddItemPanel.add(ChooseFileButton);
		
		JLabel lblNewLabel_1 = new JLabel("Import Spreadsheet");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(250, 102, 115, 28);
		AddItemPanel.add(lblNewLabel_1);
		
		JPanel RemoveItemPanel = new JPanel();
		RemoveItemPanel.setBackground(new Color(255, 255, 255));
		layeredPane.add(RemoveItemPanel, "name_1659386861033000");
		RemoveItemPanel.setLayout(null);
		
		JLabel IDNumLabel = new JLabel("ID Number");
		IDNumLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		IDNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
		IDNumLabel.setBounds(10, 0, 113, 28);
		RemoveItemPanel.add(IDNumLabel);
		
		IDtoRemoveModify = new JTextField();
		IDtoRemoveModify.setBounds(10, 39, 113, 20);
		RemoveItemPanel.add(IDtoRemoveModify);
		IDtoRemoveModify.setColumns(10);
		
		JButton RemoveItemButton = new JButton("Remove");
		RemoveItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "delete from Inv where IDNumber= '"+IDtoRemoveModify.getText()+"' "; //remove item with typed id
					PreparedStatement pst = connection.prepareStatement(query);
					
					pst.execute();
					JOptionPane.showMessageDialog(null, "Entry Deleted");
				}catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		RemoveItemButton.setBounds(502, 38, 113, 23);
		RemoveItemPanel.add(RemoveItemButton);
		
		JComboBox<Object> DataDropdown = new JComboBox<Object>();
		DataDropdown.setModel(new DefaultComboBoxModel<Object>(new String[] {"Item Name", "Quantity", "Price", "Date Updated"})); //box selector for property to modify
		DataDropdown.setBounds(133, 38, 113, 22);
		RemoveItemPanel.add(DataDropdown);
		
		
		JLabel PropertyLabel = new JLabel("Property to Modify");
		PropertyLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		PropertyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		PropertyLabel.setBounds(133, 0, 113, 28);
		RemoveItemPanel.add(PropertyLabel);
		
		NewValueField = new JTextField();
		NewValueField.setBounds(256, 39, 113, 20);
		RemoveItemPanel.add(NewValueField);
		NewValueField.setColumns(10);
		
		JLabel NewValueLabel = new JLabel("New Value");
		NewValueLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		NewValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		NewValueLabel.setBounds(256, 0, 113, 28);
		RemoveItemPanel.add(NewValueLabel);
		
		JButton ModifyButton = new JButton("Modify"); //when pressing modify button, gets value from dropdown box to choose type then makes modification from entered value
		ModifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ToMod = (String) DataDropdown.getSelectedItem();
				String property = null;
				
				switch(ToMod) {
				case "Item Name":
					property = "ItemName";
					break;
					
				case "Quantity":
					property = ToMod;
					break;
					
				case "Price":
					property = ToMod;
					break;
					
				case "Date Updated":
					property = "DateAdded";
					break;
				}
				
				try {
					String query = "update Inv set " + property + " = '" +  NewValueField.getText() + "' where IDNumber= '"+ IDtoRemoveModify.getText() +"' ";
					PreparedStatement pst = connection.prepareStatement(query);
					
					pst.execute();
					JOptionPane.showMessageDialog(null, "Entry Updated");
				}catch (Exception e1) {
					e1.printStackTrace();
				}
				
				
			}
		});
		ModifyButton.setBounds(379, 38, 113, 23);
		RemoveItemPanel.add(ModifyButton);
		
		JButton ViewButton = new JButton("View Inventory");
		ViewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		ViewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(ViewInvPanel);
				layeredPane.repaint();
				layeredPane.revalidate();
				
				try {
					String query = "select * from Inv"; //print table
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					InvTable.setModel(DbUtils.resultSetToTableModel(rs));

					
				}catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		ViewButton.setBounds(10, 11, 202, 23);
		contentPane.add(ViewButton);
		
		JButton AddButton = new JButton("Add Items");
		AddButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		AddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(AddItemPanel);
				layeredPane.repaint();
				layeredPane.revalidate();
			}
		});
		AddButton.setBounds(222, 11, 203, 23);
		contentPane.add(AddButton);
		
		JButton RemoveButton = new JButton("Remove / Modify");
		RemoveButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		RemoveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(LoginWindow.Supervisor == true || Super == true) {//if user is supervisor or already got password, no need to ask
					layeredPane.removeAll();
					layeredPane.add(RemoveItemPanel);
					layeredPane.repaint();
					layeredPane.revalidate();
				}
				
				else {
					JPasswordField pf = new JPasswordField(); //get supervisor password 
					JOptionPane.showConfirmDialog(null, pf, "Enter Supervisor Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
					try {
					String query = "select Supervisor from Logins where Password = ?";
					PreparedStatement pst = connection.prepareStatement(query);
					
					String pass = String.valueOf(pf.getPassword());
					pst.setString(1, new String(pass));
					
					ResultSet rs = pst.executeQuery();
					int S = rs.getInt("Supervisor");
					
					if(S == 1) { //only works if password is supervisor
						Super = true;
						layeredPane.removeAll();
						layeredPane.add(RemoveItemPanel);
						layeredPane.repaint();
						layeredPane.revalidate();
					}
					
					else {
						JOptionPane.showMessageDialog(null, "Password Incorrect");
					}
					
					}catch(Exception e2) {
						}
					
					}
				}
			
		});
		RemoveButton.setBounds(435, 11, 202, 23);
		contentPane.add(RemoveButton);
		
	}
}
