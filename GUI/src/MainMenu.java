import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import java.awt.CardLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Color;
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

//reads the specific cell from the given spreadsheet.
@SuppressWarnings("deprecation")
public static String SpreadSheetReadCell(int sRow, int sColumn) {
	String value=null;         
	Workbook wb=null; 
	try {  
		FileInputStream fis=new FileInputStream(new File("C:\\Users\\dtran\\Desktop\\Demo.xlsx"));
		wb=new XSSFWorkbook(fis);  
		
	}catch(Exception e) {
		e.printStackTrace();  
	}
	
	Sheet sheet=wb.getSheetAt(0);   
	Row row = sheet.getRow(sRow); 
	Cell cell=row.getCell(sColumn);  
	cell.setCellType(Cell.CELL_TYPE_STRING);
	value=cell.getStringCellValue();     
	return value;               
}

public static void ImportSpreadSheet() {
	String value=null;         
	Workbook wb=null; 
	int entries = 0;
	try {  
		FileInputStream fis=new FileInputStream(new File("C:\\Users\\dtran\\Desktop\\Demo.xlsx"));
		wb=new XSSFWorkbook(fis);  
		
		
	}catch(Exception e) {
		e.printStackTrace();  
	}
	
	Sheet sheet=wb.getSheetAt(0); 
	entries = sheet.getLastRowNum();
	
	for(int i = 1; i <= entries; i++)
	{
		try {
			String query = "Insert into Inv (IDNumber,ItemName, Quantity, Price, DateAdded) values (?, ?, ?, ?, ?)";
			PreparedStatement pst = connection.prepareStatement(query);
			pst.setString(1, SpreadSheetReadCell(i, 0));
			pst.setString(2, SpreadSheetReadCell(i, 1));
			pst.setString(3, SpreadSheetReadCell(i, 2));
			pst.setString(4, SpreadSheetReadCell(i, 3));
			pst.setString(5, SpreadSheetReadCell(i, 4));
			pst.execute();	
			
		}catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	JOptionPane.showMessageDialog(null, entries + " items added");
	
}

	/**
	 * Create the frame.
	 */
	public MainMenu() {
		connection = sqliteConnector.dbConnector();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 663, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(10, 69, 627, 231);
		contentPane.add(layeredPane);
		layeredPane.setLayout(new CardLayout(0, 0));
		
		JPanel ViewInvPanel = new JPanel();
		layeredPane.setLayer(ViewInvPanel, -1);
		layeredPane.add(ViewInvPanel, "name_1659376283470600");
		ViewInvPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(614, 219, -510, -196);
		ViewInvPanel.add(scrollPane);
		
		InvTable = new JTable();
		InvTable.setBounds(99, 22, 518, 198);
		ViewInvPanel.add(InvTable);
		
		try {
			String query = "select * from Inv";
			PreparedStatement pst = connection.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			InvTable.setModel(DbUtils.resultSetToTableModel(rs));

			
		}catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		JPanel AddItemPanel = new JPanel();
		layeredPane.add(AddItemPanel, "name_1659379628347400");
		AddItemPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ID Number");
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 0, 79, 28);
		AddItemPanel.add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("Item Name");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(99, 0, 79, 28);
		AddItemPanel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Quantity");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(195, 0, 79, 28);
		AddItemPanel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Price");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(291, 0, 79, 28);
		AddItemPanel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Date Added");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setBounds(387, 0, 79, 28);
		AddItemPanel.add(lblNewLabel_5);
		
		IDNum = new JTextField();
		IDNum.setHorizontalAlignment(SwingConstants.LEFT);
		IDNum.setBounds(0, 39, 86, 20);
		AddItemPanel.add(IDNum);
		IDNum.setColumns(10);
		
		ItemName = new JTextField();
		ItemName.setBounds(99, 39, 86, 20);
		AddItemPanel.add(ItemName);
		ItemName.setColumns(10);
		
		Quant = new JTextField();
		Quant.setBounds(195, 39, 86, 20);
		AddItemPanel.add(Quant);
		Quant.setColumns(10);
		
		Price = new JTextField();
		Price.setBounds(291, 39, 86, 20);
		AddItemPanel.add(Price);
		Price.setColumns(10);
		
		Date = new JTextField();
		Date.setBounds(387, 39, 86, 20);
		AddItemPanel.add(Date);
		Date.setColumns(10);
		
		JButton AddItemButton = new JButton("Add Item");
		AddItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "Insert into Inv (IDNumber,ItemName, Quantity, Price, DateAdded) values (?, ?, ?, ?, ?)";
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
		AddItemButton.setBounds(483, 39, 89, 20);
		AddItemPanel.add(AddItemButton);
		
		JButton btnNewButton_2 = new JButton("New button");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ImportSpreadSheet();
			}
		});
		btnNewButton_2.setBounds(195, 123, 89, 23);
		AddItemPanel.add(btnNewButton_2);
		
		JPanel RemoveItemPanel = new JPanel();
		layeredPane.add(RemoveItemPanel, "name_1659386861033000");
		RemoveItemPanel.setLayout(null);
		
		JLabel IDNumLabel = new JLabel("ID Number");
		IDNumLabel.setHorizontalAlignment(SwingConstants.CENTER);
		IDNumLabel.setBounds(0, 0, 79, 28);
		RemoveItemPanel.add(IDNumLabel);
		
		IDtoRemoveModify = new JTextField();
		IDtoRemoveModify.setBounds(0, 39, 86, 20);
		RemoveItemPanel.add(IDtoRemoveModify);
		IDtoRemoveModify.setColumns(10);
		
		JButton RemoveItemButton = new JButton("Remove");
		RemoveItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "delete from Inv where IDNumber= '"+IDtoRemoveModify.getText()+"' ";
					PreparedStatement pst = connection.prepareStatement(query);
					
					pst.execute();
					JOptionPane.showMessageDialog(null, "Entry Deleted");
				}catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		RemoveItemButton.setBounds(528, 38, 89, 23);
		RemoveItemPanel.add(RemoveItemButton);
		
		JComboBox<Object> DataDropdown = new JComboBox<Object>();
		DataDropdown.setModel(new DefaultComboBoxModel<Object>(new String[] {"Item Name", "Quantity", "Price", "Date Updated"}));
		DataDropdown.setBounds(96, 38, 86, 22);
		RemoveItemPanel.add(DataDropdown);
		
		
		JLabel PropertyLabel = new JLabel("Property to Modify");
		PropertyLabel.setHorizontalAlignment(SwingConstants.CENTER);
		PropertyLabel.setBounds(96, 0, 93, 28);
		RemoveItemPanel.add(PropertyLabel);
		
		NewValueField = new JTextField();
		NewValueField.setBounds(192, 39, 86, 20);
		RemoveItemPanel.add(NewValueField);
		NewValueField.setColumns(10);
		
		JLabel NewValueLabel = new JLabel("New Value");
		NewValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		NewValueLabel.setBounds(192, 0, 79, 28);
		RemoveItemPanel.add(NewValueLabel);
		
		JButton ModifyButton = new JButton("Modify");
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
		ModifyButton.setBounds(288, 38, 89, 23);
		RemoveItemPanel.add(ModifyButton);
		
		JButton ViewButton = new JButton("View Inventory");
		ViewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(ViewInvPanel);
				layeredPane.repaint();
				layeredPane.revalidate();
				
				try {
					String query = "select * from Inv";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					InvTable.setModel(DbUtils.resultSetToTableModel(rs));

					
				}catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		ViewButton.setBounds(10, 11, 116, 23);
		contentPane.add(ViewButton);
		
		JButton AddButton = new JButton("Add Items");
		AddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(AddItemPanel);
				layeredPane.repaint();
				layeredPane.revalidate();
			}
		});
		AddButton.setBounds(160, 11, 116, 23);
		contentPane.add(AddButton);
		
		JButton RemoveButton = new JButton("Remove Items");
		RemoveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(LoginWindow.Supervisor == true || Super == true) {
					layeredPane.removeAll();
					layeredPane.add(RemoveItemPanel);
					layeredPane.repaint();
					layeredPane.revalidate();
				}
				
				else {
					JPasswordField pf = new JPasswordField();
					JOptionPane.showConfirmDialog(null, pf, "Enter Supervisor Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
					try {
					String query = "select Supervisor from Logins where Password = ?";
					PreparedStatement pst = connection.prepareStatement(query);
					
					String pass = String.valueOf(pf.getPassword());
					pst.setString(1, new String(pass));
					
					ResultSet rs = pst.executeQuery();
					int S = rs.getInt("Supervisor");
					
					if(S == 1) {
						Super = true;
						layeredPane.removeAll();
						layeredPane.add(RemoveItemPanel);
						layeredPane.repaint();
						layeredPane.revalidate();
					}
					
					}catch(Exception e2) {
						}
					
					}
				}
			
		});
		RemoveButton.setBounds(308, 11, 116, 23);
		contentPane.add(RemoveButton);
		
	}
}
