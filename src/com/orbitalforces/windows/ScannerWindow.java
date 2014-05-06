package com.orbitalforces.windows;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.orbitalforces.OrbitalForcesControl;
import com.orbitalforces.model.Game;
import com.orbitalforces.model.ObjectItem;
import com.orbitalforces.model.Planet;

public class ScannerWindow extends JPanel {

	PlanetWindow planetWindow = null;
	JLabel lblResources1 = new JLabel("Resources :");
	JLabel lblResources2 = new JLabel("Enemies :");
	JLabel lblHeading = new JLabel("Scanner Report");
	JLabel lblResources1Cnt = new JLabel();
	JLabel lblResources2Cnt = new JLabel();
	JLabel lblResources3Cnt = new JLabel();
	JLabel lblPlanet = new JLabel("Planet :");
	JLabel lblPlanetTxt = new JLabel("");
	JButton btnOk = new JButton("Ok");

	public ScannerWindow() {
		setLayout(null);
		add(lblResources1);
		add(lblResources2);
		add(lblHeading);
		add(lblResources1Cnt);
		add(lblResources2Cnt);
		add(lblPlanet);
		add(lblPlanetTxt);
		// add(lblResources3Cnt);
		add(btnOk);
		btnOk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				((JButton) arg0.getSource()).getParent().setVisible(false);
				planetWindow.attachKeyListener();
			}
		});
		initialize();
	}

	private void initialize() {
		lblHeading.setLocation(20, 0);
		lblHeading.setSize(150, 20);
		Font font = lblHeading.getFont();
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		lblHeading.setFont(font.deriveFont(attributes));
		lblPlanet.setLocation(5, 30);
		lblPlanet.setSize(60, 20);
		lblResources1.setLocation(5, 50);
		lblResources1.setSize(80, 20);
		lblResources2.setLocation(5, 70);
		lblResources2.setSize(80, 20);
		lblPlanetTxt.setLocation(65, 30);
		lblPlanetTxt.setSize(60, 20);
		lblResources1Cnt.setLocation(85, 50);
		lblResources1Cnt.setSize(60, 20);
		lblResources2Cnt.setLocation(70, 70);
		lblResources2Cnt.setSize(60, 20);

		// lblResources3Cnt.setLocation(70, 60);
		// lblResources3Cnt.setSize(60, 20);
		btnOk.setLocation(40, 90);
		btnOk.setSize(60, 20);
		setVisible(false);
	}

	private void fillResources() {
		Planet planet=getPlanetTravelling();
		List<ObjectItem> objItemsList = planet.getObjects();
		int resourceCnt=0;
		int enemyCnt=0;
		for (ObjectItem objectItem : objItemsList) {
			if(!objectItem.isAttacked()){
			if(objectItem.getType().equals("R"))
				resourceCnt++;
			else
				enemyCnt++;			
			}
		}
		lblResources1Cnt.setText(resourceCnt+"");
		lblResources2Cnt.setText(enemyCnt+"");

	}

	public void openPlanetListWindow(PlanetWindow planetWindowPanel) {
		fillResources();
		lblPlanetTxt.setText(SpaceWindow.planetName);
		setVisible(true);
		this.planetWindow = planetWindowPanel;
	}

	private Planet getPlanetTravelling() {
		Game game = OrbitalForcesControl.USERPROFILE.getGame();
		if (game != null) {
			for (Planet pl : game.getPlanetList()) {
				if (pl.isTravelling())
					return pl;
			}
			return null;
		}
		return null;
	}
}
