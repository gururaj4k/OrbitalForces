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
import javax.swing.SwingWorker;

import com.orbitalforces.OrbitalForcesControl;
import com.orbitalforces.model.Game;
import com.orbitalforces.model.Planet;

public class ResumeDialogWindow extends JPanel {
	JLabel lblText = new JLabel("");
	JButton btnOk = new JButton("ok");
	JPanel windowPanel;
	JLabel lblCount=new JLabel();
	int DEFAULTTIME=3;
	ResumeDialogWindow rdWindow=null;
	public ResumeDialogWindow() {
		setLayout(null);
		lblText.setLocation(40, 10);
		lblText.setSize(500, 20);
//		btnOk.setLocation(120, 40);
//		btnOk.setSize(60, 20);
		lblCount.setLocation(120, 40);
		lblCount.setSize(60, 20);
		Font font = new Font("serif", Font.BOLD, 12);
		Map attributes = font.getAttributes();
		//attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		// attributes.put(TextAttribute.WEIGHT_BOLD, 4f);
		lblText.setFont(font.deriveFont(attributes));
		add(lblText);
		
		//	add(btnOk);
		add(lblCount);
		setVisible(false);
		//btnOk.addActionListener(this);
		rdWindow=this;
		this.setFocusable(false);
	}

	public void openResumeMsgWindow(JPanel windowPanel,String msg){
		this.windowPanel=windowPanel;
		lblText.setText(msg);
		this.setVisible(true);
		timerStart();
	}
//	@Override
//	public void actionPerformed(ActionEvent e) {		
//		spaceWindowPanel.setPlanetToTravelDetails(getPlanetTravelling());
//		spaceWindowPanel.enableNavigation();
//		this.setVisible(false);
//	}
	
	private Planet getPlanetTravelling() {
		Game game=OrbitalForcesControl.USERPROFILE.getGame();
		if (game != null) {
			for (Planet pl : game.getPlanetList()) {
				if (pl.isTravelling())
					return pl;
			}
			return null;
		}
		return null;
	}
	private void timerStart() {
		SwingWorker timerProcess = new SwingWorker<Void, Void>() {
			@Override
			protected void process(List<Void> chunks) {
				lblCount.setText(DEFAULTTIME+"");
			}

			@Override
			protected Void doInBackground() throws Exception {
				while (true) {
					DEFAULTTIME--;					
					publish();
					if (DEFAULTTIME < 0) {
						break;
					}
					Thread.sleep(1000);
				}
				return null;
			}

			@Override
			protected void done() {
				if(windowPanel instanceof SpaceWindow){
					SpaceWindow spaceWindowPanel=(SpaceWindow)windowPanel;
					spaceWindowPanel.setPlanetToTravelDetails(getPlanetTravelling());
					spaceWindowPanel.enableNavigation();
					spaceWindowPanel.requestFocus();
					rdWindow.setVisible(false);
					spaceWindowPanel.remove(rdWindow);
				}else{
					PlanetWindow planetWindowPanel=(PlanetWindow)windowPanel;
					rdWindow.setVisible(false);
					planetWindowPanel.remove(rdWindow);
					planetWindowPanel.requestFocus();
					
				}
				
				
			}
		};

		timerProcess.execute();
	}
}
