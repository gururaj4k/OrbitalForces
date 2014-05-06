package com.orbitalforces.windows;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.SpringLayout.Constraints;

import com.orbitalforces.OrbitalForces;
import com.orbitalforces.OrbitalForcesControl;
import com.orbitalforces.model.UserProfile;
import com.orbitalforces.windows.PlanetWindow.DialogWindow;

public class ProfileWindow extends JPanel {

	JLabel lblHeading = new JLabel("Create a New Profile");
	String[] labels = { "Name: ", "Username: ", "Age: " };
	int numPairs = labels.length;
	JLabel lblName = new JLabel("Name :");
	JLabel lblUsername = new JLabel("Username :");
	JLabel lblAge = new JLabel("Age :");
	JTextField txtName = new JTextField(20);
	JTextField txtUsername = new JTextField(20);
	JTextField txtAge = new JTextField(20);
	JButton btnSubmit = new JButton("Submit");
	OrbitalForcesControl ofControl = null;
	DialogWindow dialogWindow = null;

	public ProfileWindow() {
		// Create and populate the panel.

		setLayout(null);
		add(lblHeading);
		add(lblName);
		add(txtName);
		add(lblUsername);
		add(txtUsername);
		add(lblAge);
		add(txtAge);

		add(btnSubmit);
		dialogWindow = new DialogWindow();
		add(dialogWindow);
		initialize();
		btnSubmit.addActionListener(new ProfileWindowListener());

	}

	private void initialize() {
		lblHeading.setLocation(580, 70);
		lblHeading.setSize(500, 50);
		lblName.setLocation(500, 120);
		lblName.setSize(100, 30);
		txtName.setLocation(600, 120);
		txtName.setSize(150, 30);

		lblUsername.setLocation(500, 170);
		lblUsername.setSize(100, 30);
		txtUsername.setLocation(600, 170);
		txtUsername.setSize(150, 30);

		lblAge.setLocation(500, 220);
		lblAge.setSize(100, 30);
		txtAge.setLocation(600, 220);
		txtAge.setSize(150, 30);

		btnSubmit.setLocation(600, 300);
		btnSubmit.setSize(100, 30);
		dialogWindow.setLocation(400, 400);
		dialogWindow.setSize(350, 100);
		Font font = new Font("serif", Font.BOLD, 20);
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		// attributes.put(TextAttribute.WEIGHT_BOLD, 4f);
		lblHeading.setFont(font.deriveFont(attributes));
	}

	public class ProfileWindowListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("Im called");
			UserProfile userProfile = new UserProfile();
			userProfile.setName(txtName.getText());
			userProfile.setAge(Integer.parseInt(txtAge.getText()));
			userProfile.setUsername(txtUsername.getText());
			saveUser(userProfile);

		}

	}

	private void saveUser(UserProfile userProfile) {
		ofControl = new OrbitalForcesControl();
		int resp = ofControl.saveUserProfile(userProfile);
		if (resp == 0) {
			loadProfileInContext(userProfile);
			showMsg("Profile created Successfully", resp);
		} else if (resp == -1) {
			showMsg("Username already exists.Please try another one.", resp);
		} else {
			showMsg("Profile Registration Failed. Please try again later.",
					resp);
		}

	}

	private void loadProfileInContext(UserProfile userProfile) {
		// User Profile context created.
		ofControl.USERPROFILE = userProfile;
		System.out.println("my user id"+userProfile.getUserProfileId());

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// g.drawRect(400, 50, 500, 500);
		g.setColor(new Color(229, 204, 255));
		g.fillRect(400, 50, 500, 500);
	}

	public class DialogWindow extends JPanel implements ActionListener {
		JLabel lblText = new JLabel("You reached Successfully");
		JButton btnOk = new JButton("ok");
		int resp = 0;

		public DialogWindow() {
			setLayout(null);
			lblText.setLocation(40, 10);
			lblText.setSize(300, 20);
			btnOk.setLocation(120, 40);
			btnOk.setSize(60, 20);
			add(lblText);
			add(btnOk);
			btnOk.addActionListener(this);
			setSize(250, 100);
			setVisible(false);
		}

		public void openWindow(String message, int resp) {
			System.out.println("Opened");
			this.setVisible(true);
			lblText.setText(message);
			this.resp = resp;
		}

		public void hideWindow() {
			this.setVisible(false);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (resp == 0) {
				CardLayout cardLayout = (CardLayout) OrbitalForces.panelContainer
						.getLayout();
				cardLayout.show(OrbitalForces.panelContainer, "home");
			} else {
				this.setVisible(false);
			}
		}
	}

	public void showMsg(String message, int resp) {
		dialogWindow.openWindow(message, resp);
	}
}
