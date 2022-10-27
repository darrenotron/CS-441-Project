import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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

	/**
	 * Create the frame.
	 */
	public MainMenu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton AddItemButton = new JButton("Add Items");
		AddItemButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		AddItemButton.setBounds(10, 75, 172, 53);
		contentPane.add(AddItemButton);
		
		JButton RemoveItemButton = new JButton("Remove Items");
		RemoveItemButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		RemoveItemButton.setBounds(10, 139, 172, 53);
		contentPane.add(RemoveItemButton);
		
		JButton ViewButton = new JButton("View Inventory");
		ViewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				ViewInv vi = new ViewInv();
				vi.setVisible(true);
			}
		});
		ViewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		ViewButton.setBounds(10, 11, 172, 53);
		contentPane.add(ViewButton);
	}

}
