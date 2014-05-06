package com.orbitalforces.windows;

import java.awt.AWTKeyStroke;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingWorker;

import com.orbitalforces.OrbitalForces;
import com.orbitalforces.OrbitalForcesControl;
import com.orbitalforces.model.Building;
import com.orbitalforces.model.Game;
import com.orbitalforces.model.ObjectItem;
import com.orbitalforces.model.Planet;
import com.orbitalforces.model.UserSettings;

public class PlanetWindow extends JPanel {
	JLabel playerIcon = null;
	String imagePath="C:\\Guru\\of\\";
	PlanetWindow planetWindow = null;
	OrbitalForcesControl ofControl = null;
	JButton btnScanner = new JButton("Scanner");
	JLabel lblAction = new JLabel("Action: ");
	JLabel lblScore = new JLabel("Points: ");
	JLabel lblActionMsg = new JLabel();
	JLabel lblScoreMsg = new JLabel();
	JLabel lblCombats = new JLabel("Combats: ");
	JLabel lblCombatsMsg = new JLabel();
	ScannerWindow scannerPanel;
	HashMap<JLabel, ObjectItem> resourceElements = new HashMap<JLabel, ObjectItem>();
	HashMap<JLabel, ObjectItem> enemyElements = new HashMap<JLabel, ObjectItem>();
	int x, y;
	JLabel building = new JLabel(new ImageIcon(imagePath+"building.gif"));
	DialogWindow dialogWindow = null;

	Planet currentPlanet = null;
	Game currentGame = null;
	ResumeDialogWindow rdw = new ResumeDialogWindow();
	// Ctrls To Use will get updated by User Profile // Values are default
	int leftCtrl = KeyEvent.VK_LEFT;
	int rightCtrl = KeyEvent.VK_RIGHT;
	int topCtrl = KeyEvent.VK_UP;
	int bottomCtrl = KeyEvent.VK_DOWN;
	int civilizationCtrl = KeyEvent.VK_B;
	int launchCtrl = KeyEvent.VK_L;
	int combatCtrl = KeyEvent.VK_U;

	public PlanetWindow() {
		try {
			ofControl = new OrbitalForcesControl();
			currentPlanet = getCurrentPlanet();
			currentGame = OrbitalForcesControl.USERPROFILE.getGame();
			x=currentGame.getX();
			y=currentGame.getY();
			setBackground(new Color(229, 204, 255));
			planetWindow = this;
			setLayout(null);
			dialogWindow = new DialogWindow();
			add(dialogWindow);
			playerIcon = new JLabel(new ImageIcon(imagePath+"player.jpg"));
			updateKeyControls();
			if (isPlanetConquered(currentPlanet)) {
				System.out.println("Conquered");
				showMessage("This planet is Conqured..!!");
			} else {
				System.out.println("ELSEEEEEEE");
				add(lblAction);
				add(lblActionMsg);
				add(lblScoreMsg);
				add(lblScore);
				add(lblCombats);
				add(lblCombatsMsg);
				add(btnScanner);
				add(rdw);
				planetWindow.add(playerIcon);
				playerIcon.setSize(42, 47);
				setFocusable(true);
				setFocusTraversalKeysEnabled(false);
				btnScanner.setFocusable(false);
				currentGame.setPointsScored(currentGame.getPointsScored() + 3);
				EscAction escAction = new EscAction();
				getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
						KeyStroke.getKeyStroke("ESCAPE"), "doEscAction");
				getActionMap().put("doEscAction", escAction);

				btnScanner.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						// System.out.println("Click on Planet windw");
						scannerPanel.openPlanetListWindow(planetWindow);
					}
				});
				scannerPanel = new ScannerWindow();
				add(scannerPanel);
				scannerPanel.setSize(150, 150);
				scannerPanel.setFocusable(false);
				scannerPanel.setLocation(1050, 0);
				lblScore.setLocation(0, 750);
				lblScore.setSize(60, 20);
				lblScoreMsg.setLocation(60, 750);
				lblScoreMsg.setSize(140, 20);
				lblCombats.setLocation(200, 750);
				lblCombats.setSize(60, 20);
				lblCombatsMsg.setLocation(260, 750);
				lblCombatsMsg.setSize(140, 20);
				lblAction.setLocation(400, 750);
				lblAction.setSize(60, 20);
				lblActionMsg.setLocation(460, 750);
				lblActionMsg.setSize(300, 20);
				btnScanner.setLocation(700, 750);
				btnScanner.setSize(100, 20);
				rdw.setLocation(300, 200);
				rdw.setSize(250, 100);
				initialize();
				if (currentGame.isResuming()) {
					// rdw.openResumeMsgWindow(planetWindow,
					// "Resuming the Game");

					scannerPanel.openPlanetListWindow(planetWindow);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("its exception");
		}

	}

	private void updateKeyControls() {
		UserSettings userSettings = OrbitalForcesControl.USERPROFILE
				.getUserSettings();
		if (userSettings != null) {
			leftCtrl = AWTKeyStroke.getAWTKeyStroke(userSettings.getLeft())
					.getKeyCode();
			rightCtrl = AWTKeyStroke.getAWTKeyStroke(userSettings.getRight())
					.getKeyCode();
			topCtrl = AWTKeyStroke.getAWTKeyStroke(userSettings.getTop())
					.getKeyCode();
			bottomCtrl = AWTKeyStroke.getAWTKeyStroke(userSettings.getBottom())
					.getKeyCode();
			combatCtrl = AWTKeyStroke.getAWTKeyStroke(userSettings.getCombat())
					.getKeyCode();
			civilizationCtrl = AWTKeyStroke.getAWTKeyStroke(
					userSettings.getCivilization()).getKeyCode();
			launchCtrl = AWTKeyStroke.getAWTKeyStroke(userSettings.getLaunch())
					.getKeyCode();

		}

	}

	private Planet getCurrentPlanet() {
		List<Planet> planetsList = ofControl.USERPROFILE.getGame()
				.getPlanetList();
		for (Planet planet : planetsList) {
			if (planet.isTravelling()) {
				System.out.println("its true buddy");
				return planet;
			}
		}
		System.out.println("its false buddy");
		return null;
	}

	private boolean isPlanetConquered(Planet planet) {
		if (planet != null)
			return planet.isConquered();
		return false;
	}

	private void initialize() {
		lblActionMsg.setText("Click on Scanner to Get Resource Details");
		lblCombatsMsg.setText(currentGame.getCombatCount() + "");
		lblScoreMsg.setText(currentGame.getPointsScored() + "");
		dialogWindow.setLocation(400, 400);
		dialogWindow.setSize(350, 100);
	}

	private void initializeResources() {
		System.out.println("I am initialiazing Resources");
		List<ObjectItem> objectItemsList = currentPlanet.getObjects();
		if (objectItemsList != null) {
			for (ObjectItem resource : objectItemsList) {
				if (!resource.isAttacked()) {
					JLabel lblResource = new JLabel(new ImageIcon(
							imagePath+resource.getImageUrl()));
					lblResource.setLocation(resource.getX(), resource.getY());
					lblResource.setSize(40, 40);
					planetWindow.add(lblResource);

					if (resource.getType().equals("R"))
						resourceElements.put(lblResource, resource);
					else
						enemyElements.put(lblResource, resource);
				}
			}
		}
		
		if (currentPlanet.getBuildingList() != null) {
			for (Building building : currentPlanet.getBuildingList()) {
				JLabel lblBuilding = new JLabel(new ImageIcon(
						imagePath+"building.gif"));
				lblBuilding.setLocation(building.getX(), building.getY());
				lblBuilding.setSize(40, 40);
				planetWindow.add(lblBuilding);
			}
		}
	}

	private void applyCorderCases() {
		if (y > 653)
			y = 653;
		if (y < 0)
			y = 0;
		if (x > 958)
			x = 958;
		if (x < 0)
			x = 0;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		applyCorderCases();
		if (playerIcon != null)
			playerIcon.setLocation(x, y);
		g.setColor(Color.BLACK);
		g.fillRect(0, 700, this.getWidth(), 5);
		g.fillRect(1000, 0, 5, this.getHeight());
		// g.drawImage(playerIcon, x, y, null);

		checkforCollision();

	}

	public void attachKeyListener() {
		// System.out.println("I am attaching the Listener to planet window");

		initializeResources();
		PlanetWindowKeyListener kCtrl = new PlanetWindowKeyListener();

		planetWindow.setFocusTraversalKeysEnabled(false);
		this.addKeyListener(kCtrl);
		planetWindow.requestFocus();
		System.out.println("This is frame listener removing");
		// OrbitalForces.OfFrame.removeKeyListener(
		// OrbitalForces.getFrameKeyListener());
		// System.out.println(OrbitalForces.OfFrame.getTitle());
		// OrbitalForces.OfFrame.setFocusable(false);
		planetWindow.setFocusable(true);
		lblActionMsg.setText("Use controls to Navigate");
		btnScanner.setVisible(false);
		if (currentGame.isResuming()) {
			currentGame.setResuming(false);
			rdw.openResumeMsgWindow(planetWindow, "Resuming the game..");
		}
	}

	public void checkforCollision() {
		Iterator<Entry<JLabel, ObjectItem>> it = resourceElements.entrySet()
				.iterator();
		while (it.hasNext()) {
			Map.Entry<JLabel, ObjectItem> resource = (Map.Entry<JLabel, ObjectItem>) it
					.next();
			int x1 = resource.getKey().getLocation().x;
			int x2 = resource.getKey().getLocation().x
					+ resource.getKey().getWidth();
			int y1 = resource.getKey().getLocation().y;
			int y2 = resource.getKey().getLocation().y
					+ resource.getKey().getHeight();
			if (x > x1 && x < x2 && y > y1 && y < y2) {
				System.out.println("Resource Value :"
						+ resource.getValue().getPoints());
				planetWindow.remove(resource.getKey());
				currentGame.setPointsScored(resource.getValue().getPoints()
						+ currentGame.getPointsScored());
				lblScoreMsg.setText(currentGame.getPointsScored() + "");
				resourceElements.remove(resource.getKey());
				resource.getValue().setAttacked(true);
				break;
			}
		}

	}

	public class PlanetWindowKeyListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println("Key Pressed");
			if (e.getKeyCode() == leftCtrl) {
				x -= 5;
			} else if (e.getKeyCode() == rightCtrl) {
				x += 5;
			} else if (e.getKeyCode() == topCtrl) {
				y -= 5;
			} else if (e.getKeyCode() == bottomCtrl) {
				y += 5;
			}
			if (e.isControlDown()) {
				if (e.getKeyCode() == civilizationCtrl) {
					if (currentGame.getPointsScored() >= 4) {
						JLabel building = new JLabel(new ImageIcon(
								imagePath+"building.gif"));
						planetWindow.add(building);
						building.setSize(75, 75);
						building.setLocation(x, y + 50);
						currentGame.setPointsScored(currentGame
								.getPointsScored() - 4);
						lblScoreMsg.setText(currentGame.getPointsScored() + "");
						addBuilding(x, (y + 50));
						if (currentPlanet.getBuildingList().size() >= 3) {
							System.out.println("building cnt ==3");
							currentPlanet.setConquered(true);
							showMessage("You conquered the planet successfully");
						}
					}
				}
				if (e.getKeyCode() == combatCtrl) {
					deployUnits();
				}
				if (e.getKeyCode() == launchCtrl) {
					launchSpaceCraft();
				}
			}
			repaint();
		}

		private void addBuilding(int x, int y) {
			Building building = new Building();
			building.setX(x);
			building.setY(y);
			if (currentPlanet.getBuildingList() == null) {
				currentPlanet.setBuildingList(new ArrayList<Building>());
			}
			currentPlanet.getBuildingList().add(building);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		}

	}

	private void deployUnits() {
		System.out.println("Deploy units called");
		Point topLocation = new Point(x + 20, y);
		Point rightLocation = new Point(x + 40, y + 20);
		Point leftLocation = new Point(x, y + 20);
		Point bottonLocation = new Point(x + 20, y + 40);
		currentGame.setCombatCount(currentGame.getCombatCount() - 1);
		lblCombatsMsg.setText(currentGame.getCombatCount() + "");
		executeCombatThread(topLocation, CombatDirection.TOP);
		executeCombatThread(rightLocation, CombatDirection.RIGHT);
		executeCombatThread(leftLocation, CombatDirection.LEFT);
		executeCombatThread(bottonLocation, CombatDirection.BOTTOM);
	}

	private void executeCombatThread(Point point, CombatDirection cDirection) {
		Combats combat = new Combats(cDirection, point.x, point.y);
		combat.execute();
	}

	private void launchSpaceCraft() {
		SwingWorker<Void, Void> craftWorker = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				while (true) {
					if (x >= 0 && y >= 0) {
						x -= 5;
						y -= 5;
					} else if (x >= 0) {
						x -= 5;
					} else if (y >= 0) {
						y -= 5;
					} else {
						System.out.println("ima brak");
						done();
						break;
					}
					Thread.sleep(50);
					publish();
				}
				return null;
			}

			@Override
			protected void process(List<Void> chunks) {
				playerIcon.setLocation(x, y);
				if (x < 5 && y < 5) {
					CardLayout cardLayout = (CardLayout) OrbitalForces.panelContainer
							.getLayout();
					OrbitalForces.panelContainer.add(new SpaceWindow(), "space");
					cardLayout.show(OrbitalForces.panelContainer, "space");
					System.out.println("DONE 222222");
				}
			}

		};
		craftWorker.execute();
	}

	public enum CombatDirection {
		TOP, LEFT, RIGHT, BOTTOM
	}

	public class Combats extends SwingWorker<Void, Void> {

		CombatDirection combatDirection;
		JLabel lblSoldier = null;
		int x;
		int y;
		boolean isCombatReachedCorner = false;

		public Combats(CombatDirection direction, int x, int y) {
			this.combatDirection = direction;
			this.x = x;
			this.y = y;
		}

		@Override
		protected Void doInBackground() throws Exception {
			lblSoldier = new JLabel(new ImageIcon(imagePath+"soldier.gif"));
			planetWindow.add(lblSoldier);
			while (!isCombatReachedCorner) {
				lblSoldier.setSize(30, 30);
				Thread.sleep(50);
				publish();
			}
			return null;
		}

		@Override
		protected void done() {
			System.out.println("I am combat worker and im clsed");
			planetWindow.remove(lblSoldier);
		}

		@Override
		protected void process(List<Void> chunks) {
			switch (combatDirection) {
			case BOTTOM:
				y += 5;
				break;
			case LEFT:
				x -= 5;
				break;
			case RIGHT:
				x += 5;
				break;
			case TOP:
				y -= 5;
				break;
			}
			if (x > 975 || x < 0)
				isCombatReachedCorner = true;
			if (y > 675 || y < 0)
				isCombatReachedCorner = true;
			lblSoldier.setLocation(x, y);
			Iterator<Entry<JLabel, ObjectItem>> it = enemyElements.entrySet()
					.iterator();
			while (it.hasNext()) {
				Map.Entry<JLabel, ObjectItem> enemy = (Map.Entry<JLabel, ObjectItem>) it
						.next();
				int x1 = enemy.getKey().getLocation().x;
				int x2 = enemy.getKey().getLocation().x
						+ enemy.getKey().getWidth();
				int y1 = enemy.getKey().getLocation().y;
				int y2 = enemy.getKey().getLocation().y
						+ enemy.getKey().getHeight();
				if (x > x1 && x < x2 && y > y1 && y < y2) {
					System.out.println("Enemy Value :" + enemy.getValue());
					currentGame.setPointsScored(currentGame.getPointsScored()
							+ enemy.getValue().getPoints());
					lblScoreMsg.setText(currentGame.getPointsScored() + "");
					planetWindow.remove(enemy.getKey());
					enemyElements.remove(enemy.getKey());
					enemy.getValue().setAttacked(true);
					break;
				}
			}
		}
	}

	public class EscAction extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			CardLayout cardLayout = (CardLayout) OrbitalForces.panelContainer
					.getLayout();
			currentGame.setResumeWindow("planet");
			currentGame.setResuming(true);
			currentGame.setX(x);
			currentGame.setY(y);
			OrbitalForces.panelContainer.add(new HomeWindow(), "home");
			cardLayout.show(OrbitalForces.panelContainer, "home");
		}
	}

	public class DialogWindow extends JPanel implements ActionListener {
		JLabel lblText = new JLabel("");
		JButton btnOk = new JButton("ok");

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
			currentGame.setX(0);
			currentGame.setY(0);
			OrbitalForces.panelContainer.add(new SpaceWindow(), "space");
			cardLayout.show(OrbitalForces.panelContainer, "space");
		}

		public void openWindow(String message) {
			System.out.println("Opened");
			this.setVisible(true);
			lblText.setText(message);
		}

		public void hideWindow() {
			this.setVisible(false);
		}
	}

	public void showMessage(String message) {
		dialogWindow.openWindow(message);
	}

}
