import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

public class Login {

	protected Shell shell;
	private Text text;
	private Text text_1;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Login window = new Login();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		
		CLabel lblNewLabel = new CLabel(shell, SWT.NONE);
		lblNewLabel.setBounds(10, 10, 110, 47);
		lblNewLabel.setText("Welcome!");
		
		CLabel lblUsername = new CLabel(shell, SWT.NONE);
		lblUsername.setBounds(10, 63, 68, 21);
		lblUsername.setText("Username:");
		
		CLabel lblPassword = new CLabel(shell, SWT.NONE);
		lblPassword.setBounds(10, 90, 68, 21);
		lblPassword.setText("Password:");
		
		Button btnLogin = new Button(shell, SWT.NONE);
		btnLogin.setBounds(51, 117, 75, 25);
		btnLogin.setText("Login");
		
		text = new Text(shell, SWT.BORDER);
		text.setBounds(84, 63, 76, 21);
		
		text_1 = new Text(shell, SWT.BORDER);
		text_1.setBounds(84, 90, 76, 21);

	}
}
