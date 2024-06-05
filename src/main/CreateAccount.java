package main;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;
import javax.swing.text.NumberFormatter;
import java.text.DecimalFormat;
import javax.swing.JFormattedTextField;

public class CreateAccount {

	public CreateAccount() {
		//ImageIcon bankLogo = new ImageIcon(CreateAccount.class.getResource("/images/bank.png"));

		final JFrame frame = new JFrame("Create Account");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 600);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		//frame.setIconImage(bankLogo.getImage());

		frame.setLayout(null);

		final JTextField firstName = new JTextField();
		TitledBorder t1 = new TitledBorder("First Name");
		firstName.setBorder(t1);
		firstName.setOpaque(false);

		final JTextField lastName = new JTextField();
		TitledBorder t2 = new TitledBorder("Last Name");
		lastName.setBorder(t2);
		lastName.setOpaque(false);

		final JTextField phone = new JTextField();
		TitledBorder t3 = new TitledBorder("Phone No.");
		phone.setBorder(t3);
		phone.setOpaque(false);

		final JTextArea address = new JTextArea();
		TitledBorder t4 = new TitledBorder("Address");
		address.setBorder(t4);
		address.setOpaque(false);

		final JTextField usernameField = new JTextField();
		TitledBorder t5 = new TitledBorder("Username");
		usernameField.setBorder(t5);
		usernameField.setOpaque(false);
		
		final JTextField emailField = new JTextField();
		TitledBorder t7 = new TitledBorder("Email");
		emailField.setBorder(t7);
		emailField.setOpaque(false);

		final JPasswordField passwordField = new JPasswordField();
		TitledBorder t6 = new TitledBorder("Password");
		passwordField.setBorder(t6);
		passwordField.setOpaque(false);


		

		JButton createAccountButton = new JButton("Create Account");
		createAccountButton.setForeground(Color.white);
		createAccountButton.setBackground(new Color(126, 0, 0));
		createAccountButton.setFocusable(false);
		createAccountButton.setFont(new Font("Comic Sans", Font.BOLD, 18));
		createAccountButton.setBorder(BorderFactory.createLoweredBevelBorder());



		// Set the maximum selectable date as the current date
		

		final JToggleButton showPasswordToggle = new JToggleButton(); // Unicode eye character "👁"

		//ImageIcon showIcon = new ImageIcon(getClass().getResource("/images/show.png"));
		//ImageIcon hideIcon = new ImageIcon(getClass().getResource("/images/hide.png"));
		//showPasswordToggle.setIcon(showIcon);
		//showPasswordToggle.setSelectedIcon(hideIcon);
		showPasswordToggle.setFocusable(false);
		showPasswordToggle.setContentAreaFilled(false);
		showPasswordToggle.setBorder(null);
		showPasswordToggle.setBounds(545, 225, 30, 30);

		
		// int customerId = 1;
		

		JLabel createAccount = new JLabel("Create Your new Account Here");
		createAccount.setBounds(160, 70, 300, 30);
		createAccount.setFont(new Font("Dialog", Font.BOLD, 20));

		
		firstName.setBounds(20, 150, 250, 50);
		lastName.setBounds(20, 210, 250, 50);
		phone.setBounds(20, 270, 250, 50);
		address.setBounds(20, 330, 250, 80);
		usernameField.setBounds(290, 150, 250, 50);
		passwordField.setBounds(290, 210, 250, 50);
		emailField.setBounds(290, 270, 250, 50);
		
		
		createAccountButton.setBounds(210, 430, 150, 50);

		frame.add(createAccount);
		frame.add(firstName);
		frame.add(lastName);
		frame.add(phone);
		frame.add(address);
		frame.add(usernameField);
		frame.add(passwordField);
		frame.add(emailField);
		
		frame.add(createAccountButton);
		frame.add(showPasswordToggle);

		frame.setVisible(true);

		

		createAccountButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String userFirstName = firstName.getText();
				String userLastName = lastName.getText();
				String userPhone = phone.getText();
				String userAddress = address.getText();
				String username = usernameField.getText();
				String email = emailField.getText();
				@SuppressWarnings("deprecation")
				String password = passwordField.getText();

				if (userFirstName.isEmpty() || userLastName.isEmpty() || userPhone.isEmpty() || userAddress.isEmpty()
						|| username.isEmpty() || password.isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Please fill in all the fields.", "Empty Fields",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				

			
				String ifsc = "BPUN0123456";
				String bankAccountType = "Savings";
				//int id = DBHandler.getCustomerIdByLogin(username, password);
				
				

				try {
				    // Fetch the latest ID from the database
				    String fetchIdQuery = "SELECT MAX(id) FROM users";
				    ResultSet resultSet = DBHandler.dbExecuteQuery(fetchIdQuery);
				    int latestId = 0;
				    if (resultSet.next()) {
				        latestId = resultSet.getInt(1);
				    }
				    int newId = latestId + 1;

				    // Construct the SQL query for inserting the new user
				    String createTableQuery = "INSERT INTO users(id, firstName, lastName, username, password, phoneNumber, address, email) VALUES ('"
				            + newId + "','" + userFirstName + "','" + userLastName + "','" + username + "','" + password + "','"
				            + userPhone  + "','" + userAddress  + "', '" + email + "')";

				    // Execute the insert query
				    DBHandler.dbExecuteUpdate(createTableQuery);

				    JOptionPane.showMessageDialog(frame, "Account Created Successfully.", "Congratulations!!",
				            JOptionPane.INFORMATION_MESSAGE);

				    frame.dispose();
				    new Login();

				} catch (Exception ex) {
				    System.out.println("Exception: " + ex);
				}
			}
		});

		showPasswordToggle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				char echoChar = showPasswordToggle.isSelected() ? 0 : '\u2022'; // Show/hide password
				passwordField.setEchoChar(echoChar);

			}
		});
	}

	public static void main(String[] args) {

		new CreateAccount();

	}

	


	
}
