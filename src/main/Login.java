package main;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;

public class Login {
	public static int accountNum = 0;
	public static String userName = "";
	public static String passWord = "";
	public static int customerId = 0;

	Login() {

		final JFrame frame = new JFrame("Login Page");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 600);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);

		frame.setLayout(null);

		JLabel welcome = new JLabel("Welcome To Pune Metro Sathi!!");
		welcome.setBounds(140, 70, 500, 50);
		welcome.setForeground(new Color(126, 0, 0));
		welcome.setFont(new Font("Comic Sans", Font.BOLD, 24));

		final JTextField username = new JTextField();
		final JPasswordField password = new JPasswordField();
		JLabel lb1 = new JLabel("Don't Have an Account?");
		JButton b1 = new JButton("Login");
		b1.setForeground(Color.white);
		b1.setBackground(new Color(126, 0, 0));
		b1.setFocusable(false);
		b1.setFont(new Font("Comic Sans", Font.BOLD, 18));
		b1.setBorder(BorderFactory.createLoweredBevelBorder());
		JButton textButton = new JButton("Create account here !!");

		JLabel login = new JLabel("Login Here");
		login.setBounds(245, 150, 200, 30);
		login.setFont(new Font("Dialog", Font.BOLD, 20));

		username.setBounds(100, 200, 400, 50);
		TitledBorder t1 = new TitledBorder("Username");

		username.setBorder(t1);
		username.setOpaque(false);

		password.setBounds(100, 270, 400, 50);
		TitledBorder t2 = new TitledBorder("Password");
		password.setBorder(t2);
		password.setOpaque(false);

		final JToggleButton showPasswordToggle = new JToggleButton(); 

		
		showPasswordToggle.setFocusable(false);
		showPasswordToggle.setContentAreaFilled(false);
		showPasswordToggle.setBorder(null);
		showPasswordToggle.setBounds(505, 282, 30, 30);

		b1.setBounds(250, 340, 100, 40);
		lb1.setBounds(235, 390, 200, 30);
		textButton.setBounds(205, 420, 200, 30);
		textButton.setContentAreaFilled(false);
		textButton.setBorderPainted(false);
		textButton.setForeground(Color.BLUE);

		frame.add(welcome);
		frame.add(login);
		frame.add(username);
		frame.add(password);
		frame.add(textButton);
		frame.add(b1);
		frame.add(lb1);
		frame.add(showPasswordToggle);

		frame.setVisible(true);
		

		b1.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {

				userName = username.getText();
				passWord = password.getText();

				char[] passwordChars = password.getPassword();
				String passwordString = new String(passwordChars);

				if (userName.isEmpty() || passwordString.isEmpty()) {
					JOptionPane.showMessageDialog(frame, "Please fill in all the fields.", "Empty Fields",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				// Validate the username and password
				boolean isValidLogin = DBHandler.validateLogin(userName, passWord);

				

				if (isValidLogin) {
					System.out.println("Success");
					frame.dispose();
					new Graph_M();

				} else {
					System.out.println("Fail");
					JOptionPane.showMessageDialog(frame, "Wrong Crendentials.", "Wrong Credentials",
							JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		textButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new CreateAccount();

			}
		});

		

		showPasswordToggle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				char echoChar = showPasswordToggle.isSelected() ? 0 : '\u2022'; 
				password.setEchoChar(echoChar);

			}
		});

	}

	public static void main(String[] args) {

		new Login();
	}
}