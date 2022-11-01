import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JLayeredPane;
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

public class MainMenu extends JFrame {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;

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
		layeredPane.add(ViewInvPanel, "name_1659376283470600");
		ViewInvPanel.setLayout(null);
		
		JButton RefreshButton = new JButton("Refresh");
		RefreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "select * from Inv";
					PreparedStatement pst = connection.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rs));
					
				}catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		RefreshButton.setBounds(0, 0, 89, 23);
		ViewInvPanel.add(RefreshButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(553, 188, -449, -183);
		ViewInvPanel.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel AddItemPanel = new JPanel();
		layeredPane.add(AddItemPanel, "name_1659379628347400");
		
		JLabel lblNewLabel = new JLabel("Add Item Placeholder");
		AddItemPanel.add(lblNewLabel);
		
		JPanel RemoveItemPanel = new JPanel();
		layeredPane.add(RemoveItemPanel, "name_1659386861033000");
		
		JLabel lblNewLabel_1 = new JLabel("Remove Item Placeholder");
		RemoveItemPanel.add(lblNewLabel_1);
		
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
