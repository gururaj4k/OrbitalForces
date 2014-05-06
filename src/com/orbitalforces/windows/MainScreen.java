package com.orbitalforces.windows;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.orbitalforces.OrbitalForces;

public class MainScreen extends JPanel {
	JLabel lblOrbitalForces = new JLabel("WELCOME TO ORBITAL FORCES");
	JButton btnCreateProfile = new JButton("Create Profile");
	JButton btnLoadProfile = new JButton("Load Profile");

	public MainScreen() {
		setLayout(null);
		add(lblOrbitalForces);
		add(btnCreateProfile);
		add(btnLoadProfile);
		intialize();
		setVisible(true);
		btnCreateProfile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CardLayout cardLayout = (CardLayout) OrbitalForces.panelContainer
						.getLayout();
				cardLayout.show(OrbitalForces.panelContainer, "profile");
			}
		});
		
		btnLoadProfile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cardLayout = (CardLayout) OrbitalForces.panelContainer
						.getLayout();
				cardLayout.show(OrbitalForces.panelContainer, "existingProfiles");
				
			}
		});
	}

	private void intialize() {
		lblOrbitalForces.setLocation(500, 70);
		lblOrbitalForces.setSize(500, 50);
		btnCreateProfile.setLocation(550, 130);
		btnCreateProfile.setSize(200, 60);
		btnLoadProfile.setLocation(550, 200);
		btnLoadProfile.setSize(200, 60);		
		Font font = new Font("serif", Font.BOLD, 20);
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
	//	attributes.put(TextAttribute.WEIGHT_BOLD, 4f);		
		lblOrbitalForces.setFont(font.deriveFont(attributes));
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//g.drawRect(400, 50, 500, 500);
		//g.setColor(Color.BLUE);
		g.setColor(new Color(229,204,255));
		g.fillRect(400, 50, 500, 500);
	}
}
