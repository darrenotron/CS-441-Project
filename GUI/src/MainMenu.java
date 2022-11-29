import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.SwingConstants;

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
	
Connection connection = null;
private JTable InvTable;
private JTextField IDNum;
private JTextField ItemName;
private JTextField Quant;
private JTextField Price;
private JTextField Date;
private JTextField IDtoRemove;

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
		
		JButton RefreshButton = new JButton("Refresh");
		RefreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		RefreshButton.setBounds(0, 0, 89, 23);
		ViewInvPanel.add(RefreshButton);
		
		InvTable = new JTable();
		InvTable.setBounds(99, 22, 518, 198);
		ViewInvPanel.add(InvTable);
		
		
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
		
		JButton btnNewButton = new JButton("Add Item");
		btnNewButton.addActionListener(new ActionListener() {
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
		btnNewButton.setBounds(483, 39, 89, 20);
		AddItemPanel.add(btnNewButton);
		
		JPanel RemoveItemPanel = new JPanel();
		layeredPane.add(RemoveItemPanel, "name_1659386861033000");
		RemoveItemPanel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("ID Number");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(0, 0, 79, 28);
		RemoveItemPanel.add(lblNewLabel_1);
		
		IDtoRemove = new JTextField();
		IDtoRemove.setBounds(0, 39, 86, 20);
		RemoveItemPanel.add(IDtoRemove);
		IDtoRemove.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("Remove");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "delete from Inv where IDNumber= '"+IDtoRemove.getText()+"' ";
					PreparedStatement pst = connection.prepareStatement(query);
					
					
					pst.execute();
					JOptionPane.showMessageDialog(null, "Entry Deleted");
				}catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(96, 38, 89, 23);
		RemoveItemPanel.add(btnNewButton_1);
		
		JButton ViewButton = new JButton("View Inventory");
		ViewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				layeredPane.removeAll();
				layeredPane.add(ViewInvPanel);
				layeredPane.repaint();
				layeredPane.revalidate();
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
				layeredPane.removeAll();
				layeredPane.add(RemoveItemPanel);
				layeredPane.repaint();
				layeredPane.revalidate();
			}
		});
		RemoveButton.setBounds(308, 11, 116, 23);
		contentPane.add(RemoveButton);
	}
}
