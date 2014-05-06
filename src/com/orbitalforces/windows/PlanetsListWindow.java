package com.orbitalforces.windows;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import com.orbitalforces.OrbitalForcesControl;
import com.orbitalforces.model.Planet;

public class PlanetsListWindow extends JPanel implements MouseListener {

	JList<Planet> planetJList = null;
	SpaceWindow spaceWindowPanel;
	public void openPlanetListWindow(SpaceWindow spaceWindowPanel) {
		this.spaceWindowPanel = spaceWindowPanel;
		planetJList = new JList<Planet>();
		DefaultListModel<Planet> listModel = new DefaultListModel<Planet>();
		OrbitalForcesControl ofControl = new OrbitalForcesControl();
		List<Planet> planetList = ofControl.USERPROFILE.getGame().getPlanetList();
		for (Planet planet : planetList) {
			listModel.addElement(planet);			
		}
		planetJList.setModel(listModel);
		planetJList.addMouseListener(this);
		add(planetJList);
	}

	@Override
	public void mouseClicked(MouseEvent evt) {
		if (evt.getClickCount() == 2) {
			System.out.println("Selected Planet :"+planetJList.getSelectedValue());
			spaceWindowPanel.setPlanetToTravelDetails(planetJList.getSelectedValue());
			this.setVisible(false);
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
