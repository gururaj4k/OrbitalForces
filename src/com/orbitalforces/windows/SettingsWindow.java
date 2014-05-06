package com.orbitalforces.windows;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.color.CMMException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.font.TextAttribute;
import java.awt.peer.ComponentPeer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.orbitalforces.OrbitalForces;
import com.orbitalforces.OrbitalForcesControl;
import com.orbitalforces.model.UserSettings;

public class SettingsWindow extends JPanel implements ActionListener {

	String[] navigationCtrls = { "Select", "LEFT", "RIGHT", "UP", "DOWN", "A",
			"S", "W", "Z" };
	String[] launchingCtrls = { "Select", "B", "U", "L", "S", "M", "N" };
	JLabel lblTitle = new JLabel("Change Settings");
	JLabel lblLeft = new JLabel("Control to turn Left :");
	JLabel lblRight = new JLabel("Control to turn Right :");
	JLabel lblTop = new JLabel("Control to turn Top :");
	JLabel lblBottom = new JLabel("Control to turn Bottom :");
	JLabel lblCombat = new JLabel("Control for deploying combats :     CTRL+");
	JLabel lblLaunch = new JLabel(
			"Control for go back :                          CTRL+");
	JLabel lblCivilation = new JLabel(
			"Control for create Civilization :       CTRL+");
	JComboBox<String> cbLeft = new JComboBox<String>(navigationCtrls);
	JComboBox<String> cbRight = new JComboBox<String>(navigationCtrls);
	JComboBox<String> cbTop = new JComboBox<String>(navigationCtrls);
	JComboBox<String> cbBottom = new JComboBox<String>(navigationCtrls);
	JComboBox<String> cbCombat = new JComboBox<String>(launchingCtrls);
	JComboBox<String> cbLaunch = new JComboBox<String>(launchingCtrls);
	JComboBox<String> cbCivilization = new JComboBox<String>(launchingCtrls);
	JLabel lblMessage = new JLabel("Please use different controls.");
	JButton btnSubmit = new JButton("Submit");
	DialogWindow dialogWindow = null;

	OrbitalForcesControl ofControl = null;

	public SettingsWindow() {
		setLayout(null);
		ofControl = new OrbitalForcesControl();
		dialogWindow = new DialogWindow();
		add(lblTitle);
		add(lblLeft);
		add(lblRight);
		add(lblTop);
		add(lblBottom);
		add(lblCombat);
		add(lblLaunch);
		add(lblCivilation);
		add(cbLeft);
		add(cbRight);
		add(cbTop);
		add(cbBottom);
		add(cbLaunch);
		add(cbCombat);
		add(cbCivilization);
		add(btnSubmit);
		add(lblMessage);
		add(dialogWindow, 2);
		btnSubmit.addActionListener(this);
		lblMessage.setVisible(false);
		initialize();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(229, 204, 255));
		g.fillRect(400, 50, 500, 500);
	}

	private void initialize() {
		lblTitle.setLocation(300, 30);
		lblTitle.setSize(500, 50);
		Font font = new Font("serif", Font.BOLD, 20);
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		// attributes.put(TextAttribute.WEIGHT_BOLD, 4f);
		lblTitle.setFont(font.deriveFont(attributes));

		lblLeft.setLocation(300, 100);
		lblLeft.setSize(300, 30);
		cbLeft.setLocation(530, 100);
		cbLeft.setSize(100, 30);

		lblRight.setLocation(300, 150);
		lblRight.setSize(300, 30);
		cbRight.setLocation(530, 150);
		cbRight.setSize(100, 30);

		lblTop.setLocation(300, 200);
		lblTop.setSize(300, 30);
		cbTop.setLocation(530, 200);
		cbTop.setSize(100, 30);

		lblBottom.setLocation(300, 250);
		lblBottom.setSize(300, 30);
		cbBottom.setLocation(530, 250);
		cbBottom.setSize(100, 30);

		lblLaunch.setLocation(300, 300);
		lblLaunch.setSize(300, 30);
		cbLaunch.setLocation(530, 300);
		cbLaunch.setSize(100, 30);

		lblCombat.setLocation(300, 350);
		lblCombat.setSize(300, 30);
		cbCombat.setLocation(530, 350);
		cbCombat.setSize(100, 30);

		lblCivilation.setLocation(300, 400);
		lblCivilation.setSize(300, 30);
		cbCivilization.setLocation(530, 400);
		cbCivilization.setSize(100, 30);

		lblMessage.setLocation(350, 450);
		lblMessage.setSize(250, 40);

		btnSubmit.setLocation(400, 500);
		btnSubmit.setSize(100, 40);

		dialogWindow.setLocation(400, 400);
		dialogWindow.setSize(350, 100);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Check for duplicates.
		HashMap<String, Integer> ctrlSet = new HashMap<String, Integer>();
		Component[] componentsArr = this.getComponents();
		for (Component component : componentsArr) {
			if (component instanceof JComboBox) {
				String ctrl = ((JComboBox<String>) component).getSelectedItem()
						.toString();
				int cnt = ctrlSet.get(ctrl) == null ? 0 : (int) ctrlSet
						.get(ctrl);
				ctrlSet.put(ctrl, ++cnt);
			}
		}
		for (Component component : componentsArr) {
			if (component instanceof JComboBox) {
				String ctrl = ((JComboBox<String>) component).getSelectedItem()
						.toString();
				if (ctrlSet.get(ctrl) > 1) {
					lblMessage.setVisible(true);
					return;
				}
			}
		}
		UserSettings userSettings = new UserSettings();
		userSettings.setBottom(cbBottom.getSelectedItem().toString());
		userSettings.setTop(cbTop.getSelectedItem().toString());
		userSettings.setRight(cbRight.getSelectedItem().toString());
		userSettings.setLeft(cbLeft.getSelectedItem().toString());
		userSettings.setCombat(cbCombat.getSelectedItem().toString());
		userSettings.setCivilization(cbCivilization.getSelectedItem()
				.toString());
		userSettings.setLaunch(cbLaunch.getSelectedItem().toString());
		userSettings.setUserId(OrbitalForcesControl.USERPROFILE
				.getUserProfileId());
		System.out.println("I am saving....");
		boolean isSaved = ofControl.saveSettings(userSettings);
		System.out.println("Savedd...");
		showMessage("You Settings has been Saved.", isSaved);
	}

	public class DialogWindow extends JPanel implements ActionListener {
		JLabel lblText = new JLabel("");
		JButton btnOk = new JButton("ok");
		boolean isSaved = false;

		public DialogWindow() {
			setLayout(null);
			lblText.setLocation(40, 10);
			lblText.setSize(300, 20);
			btnOk.setLocation(120, 40);
			btnOk.setSize(60, 20);
			Font font = new Font("serif", Font.BOLD, 20);
			Map attributes = font.getAttributes();
			attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
			// attributes.put(TextAttribute.WEIGHT_BOLD, 4f);
			lblText.setFont(font.deriveFont(attributes));
			add(lblText);
			add(btnOk);
			setSize(350, 100);
			setVisible(false);
			btnOk.addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			CardLayout cardLayout = (CardLayout) OrbitalForces.panelContainer
					.getLayout();
			if (isSaved) {
				OrbitalForces.panelContainer.add(new HomeWindow(), "home");
				cardLayout.show(OrbitalForces.panelContainer, "home");
			} else {
				this.setVisible(false);
			}
		}

		public void openWindow(String message, boolean isSaved) {
			System.out.println("Opened");
			this.setVisible(true);
			this.isSaved = isSaved;
			lblText.setText(message);
		}

		public void hideWindow() {
			this.setVisible(false);
		}
	}

	public void showMessage(String message, boolean isSaved) {
		dialogWindow.openWindow(message, isSaved);
	}

}
