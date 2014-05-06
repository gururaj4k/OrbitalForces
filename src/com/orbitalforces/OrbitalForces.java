package com.orbitalforces;

import java.awt.CardLayout;
import java.awt.DisplayMode;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;



import com.orbitalforces.windows.ExistingProfiles;
import com.orbitalforces.windows.FailedWindow;
import com.orbitalforces.windows.FullScreen;
import com.orbitalforces.windows.HelpWindow;
import com.orbitalforces.windows.HomeWindow;
import com.orbitalforces.windows.MainScreen;
import com.orbitalforces.windows.PlanetWindow;
import com.orbitalforces.windows.ProfileWindow;
import com.orbitalforces.windows.SettingsWindow;
import com.orbitalforces.windows.SpaceWindow;

public class OrbitalForces {
	public static JPanel panelContainer = null;
	public static FrameKeyListener frameKeyListener = null;
	public JFrame OfFrame;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new OrbitalForces();
			}
		});
	}

	public OrbitalForces() {
		try {
			OfFrame = new JFrame("Orbital Forces");
			frameKeyListener = new FrameKeyListener();
			panelContainer = new JPanel();
			CardLayout ofLayout = new CardLayout();
			// SpaceWindow1 s= new SpaceWindow1();
			// s.setFocusable(true);
			OfFrame.add(panelContainer);
			panelContainer.setLayout(ofLayout);
			panelContainer.add(new MainScreen(), "menu");
			panelContainer.add(new ProfileWindow(), "profile");
			panelContainer.add(new SpaceWindow(), "space");
			//panelContainer.add(new PlanetWindow(), "planet");
			panelContainer.add(new FailedWindow(), "failed");
			panelContainer.add(new HomeWindow(), "home");
			panelContainer.add(new HelpWindow(), "help");
			panelContainer.add(new ExistingProfiles(), "existingProfiles");
			panelContainer.add(new SettingsWindow(), "settings");
			//OfFrame.setFocusable(true);
//			OfFrame.addKeyListener(getFrameKeyListener());
//			 ofFrame.setVisible(true);
			// ofFrame.setSize(600, 600);
			ofLayout.show(panelContainer, "menu");
			OfFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			FullScreen fs = new FullScreen();
			DisplayMode d = new DisplayMode(800, 800, 16,
					DisplayMode.REFRESH_RATE_UNKNOWN);
			fs.setFullScreen(d, OfFrame);
			// Thread.currentThread().sleep(5000);
			System.out.println("Iam here");
			
					} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("hiihihih" + ex.getMessage());
		}
	}
	
	public class FrameKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(KeyEvent arg0) {
			System.out.println("I Pressed som ekey..........");

		}
	}

	public static KeyListener getFrameKeyListener() {
		return frameKeyListener;
	}

}
