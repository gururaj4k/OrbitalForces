package com.orbitalforces.windows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import com.orbitalforces.OrbitalForces;

public class HelpWindow extends JPanel {
	
	
	
	JLabel l = new JLabel("<html><h2><b><u>Help</u></b></h2>" +
			"<br><br>Home Menu options:<br>" +
			"Profile: Create a new profile and start the game<br>" +
			"Save: Save the current state of the game<br>" +
			"Load: Load a saved state of the game<br>" +
			"Settings: Change configurations<br>" +
			"Help: Open up the help screen<br>" +
			"<br>Starting the game - Rules:<br>" +
			"At the start of the game after creating a profile, the player will be led to the space window.<br>" +
			"The player will choose a planet and have 20 seconds to reach that planet.<br>" +
			"The player may move their spacecraft using the arrow keys.<br>" +
			"<br>Upon landing, the player will be led to the planet window.<br>" +
			"The player will have to explore and collect resources scattered around the planet.<html> ");
	
	JButton btnReturnToMenu = new JButton("Return to menu");

	public HelpWindow() {
		setLayout(null);
		add(btnReturnToMenu);
		btnReturnToMenu.addActionListener(new HelpWindowListener());
		add(l);
		initialize();
	}
	public void initialize(){
		l.setLocation(420,0);
		l.setSize(500,500);
		btnReturnToMenu.setLocation(530,500);
		btnReturnToMenu.setSize(200,30);
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//g.drawRect(400, 50, 500, 500);
		g.setColor(new Color(229,204,255));
		g.fillRect(400, 50, 500, 500);
	}
	public class HelpWindowListener implements ActionListener{

	@Override
		public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//will return to home menubt
		if(e.getSource() == btnReturnToMenu){
			CardLayout cardLayout = (CardLayout) OrbitalForces.panelContainer
					.getLayout();			
			cardLayout.show(OrbitalForces.panelContainer, "home");
		}
		
	
}
}
}