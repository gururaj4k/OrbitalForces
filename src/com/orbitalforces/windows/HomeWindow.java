package com.orbitalforces.windows;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.google.gson.Gson;
import com.orbitalforces.OrbitalForces;
import com.orbitalforces.OrbitalForcesControl;
import com.orbitalforces.model.Game;
import com.orbitalforces.model.Planet;

public class HomeWindow extends JPanel {

	JLabel title = new JLabel("Home Menu");
	JButton btnNewGame = new JButton("New Game");
	// JButton btnContinue = new JButton("Continue");
	JButton btnLoadNSave = new JButton("Load");
	JButton btnHelp = new JButton("Help");
	JButton btnSettingsNContinue = new JButton("Settings");
	JButton btnExit = new JButton("Exit");
	JLabel lblMessage = new JLabel();
	OrbitalForcesControl ofControl = null;

	public HomeWindow() {
		System.out.println("Im home consrctor");
		setLayout(null);
		boolean isNewGame = isNewGame();
		add(title);
		add(btnNewGame);
		add(btnHelp);
		add(btnExit);
		add(btnSettingsNContinue);
		add(btnLoadNSave);
		add(lblMessage);
		if (isNewGame) {
			btnSettingsNContinue.setText("Settings");
			btnLoadNSave.setText("Load");
		} else {
			btnSettingsNContinue.setText("Continue");
			btnLoadNSave.setText("Save");
		}
		// add(btnContinue);
		intialize();
		HomeMenuListener listener = new HomeMenuListener();
		btnNewGame.addActionListener(listener);
		btnHelp.addActionListener(listener);
		btnExit.addActionListener(listener);
		btnLoadNSave.addActionListener(listener);
		btnSettingsNContinue.addActionListener(listener);
	}

	private void intialize() {
		title.setLocation(500, 70);
		title.setSize(500, 50);
		btnNewGame.setLocation(550, 130);
		btnNewGame.setSize(200, 60);
		btnLoadNSave.setLocation(550, 200);
		btnLoadNSave.setSize(200, 60);
		btnHelp.setLocation(550, 270);
		btnHelp.setSize(200, 60);
		btnSettingsNContinue.setLocation(550, 340);
		btnSettingsNContinue.setSize(200, 60);
		btnExit.setLocation(550, 410);
		btnExit.setSize(200, 60);
		lblMessage.setLocation(300, 600);
		lblMessage.setSize(350, 100);
		Font font = new Font("serif", Font.BOLD, 20);
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		// attributes.put(TextAttribute.WEIGHT_BOLD, 4f);
		title.setFont(font.deriveFont(attributes));
		lblMessage.setFont(font.deriveFont(attributes));
	}

	public void paintComponent(Graphics g) {
		System.out.println("Im home paint");
		super.paintComponent(g);
		// g.drawRect(400, 50, 500, 500);
		g.setColor(new Color(229, 204, 255));
		g.fillRect(400, 50, 500, 500);
	}

	public boolean isNewGame() {
		if (OrbitalForcesControl.USERPROFILE != null) {
			Game game = OrbitalForcesControl.USERPROFILE.getGame();
			if (game == null) {
				return true;
			} else {
				System.out.println(game.getPointsScored());
				System.out.println(game.getTimeLeft());
				for (Planet pl : game.getPlanetList()) {
					if (pl.isTravelling())
						System.out.println(pl.getPlanetName());
				}
				return false;
			}
		} else
			return true;
	}

	public class HomeMenuListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			// Continues to profile window
			if (e.getSource() == btnNewGame) {
				createNewGame();
				CardLayout cardLayout = (CardLayout) OrbitalForces.panelContainer
						.getLayout();
				OrbitalForces.panelContainer.add(new SpaceWindow(), "space");
				cardLayout.show(OrbitalForces.panelContainer, "space");
			}

			// Continues to help window
			if (e.getSource() == btnHelp) {
				CardLayout cardLayout = (CardLayout) OrbitalForces.panelContainer
						.getLayout();
				cardLayout.show(OrbitalForces.panelContainer, "help");
			}
			if (e.getSource() == btnExit) {
				System.exit(0);
			}
			if (e.getSource() == btnSettingsNContinue) {
				if (btnSettingsNContinue.getText().equals("Continue")) {
					String resumeWindow = OrbitalForcesControl.USERPROFILE
							.getGame().getResumeWindow();
					if (resumeWindow != null && !resumeWindow.equals("")) {
						CardLayout cardLayout = (CardLayout) OrbitalForces.panelContainer
								.getLayout();
						if (resumeWindow.equals("space")) {
							OrbitalForces.panelContainer.add(new SpaceWindow(),
									"space");
						}else{
							OrbitalForces.panelContainer.add(new PlanetWindow(),
									"planet");
						}
						cardLayout.show(OrbitalForces.panelContainer,
								resumeWindow);
					}
				} else {
					CardLayout cardLayout = (CardLayout) OrbitalForces.panelContainer
							.getLayout();
					cardLayout.show(OrbitalForces.panelContainer, "settings");
				}
			}
			if (e.getSource() == btnLoadNSave) {
				ofControl = new OrbitalForcesControl();
				if (btnLoadNSave.getText().equals("Save")) {
					Game game = OrbitalForcesControl.USERPROFILE.getGame();
					game.setResuming(true);
					String gameStr = serializeGame(game);
					System.out.println(gameStr);

					ofControl
							.saveGame(OrbitalForcesControl.USERPROFILE
									.getUserProfileId(), gameStr);
				} else {
					int userId = OrbitalForcesControl.USERPROFILE
							.getUserProfileId();
					String gameStr = ofControl.getGameContext(userId);
					if (gameStr != null && !gameStr.equals("")) {
						Game gameObj = deSerializeGame(gameStr);
						OrbitalForcesControl.USERPROFILE.setGame(gameObj);

						System.out.println(gameObj.getPointsScored());
						System.out.println(gameObj.getTimeLeft());
						if (gameObj.getResumeWindow().equals("space")) {
							CardLayout cardLayout = (CardLayout) OrbitalForces.panelContainer
									.getLayout();
							OrbitalForces.panelContainer.add(new SpaceWindow(),
									"space");
							cardLayout.show(OrbitalForces.panelContainer,
									"space");
						} else {
							CardLayout cardLayout = (CardLayout) OrbitalForces.panelContainer
									.getLayout();
							OrbitalForces.panelContainer.add(
									new PlanetWindow(), "planet");
							cardLayout.show(OrbitalForces.panelContainer,
									"planet");
						}
					} else {
						lblMessage.setText("No saved Games under this profile");
					}
				}
			}
		}

		private String serializeGame(Game game) {
			String gameStr = null;
			try {
				Gson gson = new Gson();
				gameStr = gson.toJson(game);
				// System.out.println(s);
			} catch (Exception ex) {
				System.out.println(ex.getMessage());

			}
			return gameStr;
		}

		private Game deSerializeGame(String gameStr) {
			Game game = null;
			try {
				Gson gson = new Gson();
				game = gson.fromJson(gameStr, Game.class);
				System.out.println(game.getPointsScored());
				System.out.println(game.getTimeLeft());
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
			return game;
		}

		private void createNewGame() {
			Game game = new Game();
			List<Planet> planetsList = getPlanetsList();
			game.setPlanetList(planetsList);
			ofControl.USERPROFILE.setGame(game);
		}

		private List<Planet> getPlanetsList() {
			ofControl = new OrbitalForcesControl();
			return ofControl.getPlanetsList();
		}
	}
}
