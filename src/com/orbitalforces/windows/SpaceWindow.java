package com.orbitalforces.windows;

import java.awt.AWTKeyStroke;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
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
import com.orbitalforces.model.Bullet;
import com.orbitalforces.model.Game;
import com.orbitalforces.model.Planet;
import com.orbitalforces.model.UserSettings;

public class SpaceWindow extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int DEFAULTTIME = 0;
	int x, y;
	String imagePath="C:\\Guru\\of\\";
	Image playerIcon = null;
	List<Planet> planetList = null;
	public Planet planetToTravel = null;
	JLabel lblStatus = new JLabel("Status: ");
	JLabel lblAction = new JLabel("Action: ");
	JLabel lblScore = new JLabel("Points: ");
	JLabel lblStatusMsg = new JLabel();
	JLabel lblActionMsg = new JLabel();
	JLabel lblScoreMsg = new JLabel();
	SpaceWindow spaceWindow = null;
	boolean isPlanetSelected = false;
	boolean isNavigationEnabled = false;
	boolean isPlanetReached = false;
	boolean isTimeout = false;
	OrbitalForcesControl ofControl = null;
	// Timer t = new Timer(5000, this);

	KeyBoardCtrlListener keyBoardListener = null;
	boolean isPlanetArrived = false;
	JLabel i1 = new JLabel(new ImageIcon(imagePath+"bullet.gif"));
	JLabel i2 = new JLabel(new ImageIcon(imagePath+"bullet.gif"));
	JLabel i3 = new JLabel(new ImageIcon(imagePath+"bullet.gif"));
	public static int pointsScored = 0;
	public static String planetName = "";
	Random r = new Random();
	SwingWorker<Void, Bullet> worker1;
	SwingWorker<Void, Bullet> worker2;
	SwingWorker<Void, Bullet> worker3;
	SwingWorker<Void, Void> timerProcess;
	JButton planetButton = new JButton("Get Planets");
	PlanetsListWindow pw = new PlanetsListWindow();
	ResumeDialogWindow rdw = new ResumeDialogWindow();
	DialogWindow dialogWindow = null;
	Game game = null;
	// Ctrls To Use will get updated by User Profile // Values are default
	int leftCtrl = KeyEvent.VK_LEFT;
	int rightCtrl = KeyEvent.VK_RIGHT;
	int topCtrl = KeyEvent.VK_UP;
	int bottomCtrl = KeyEvent.VK_DOWN;

	public SpaceWindow() {
		try {
			game = OrbitalForcesControl.USERPROFILE.getGame();
			// //GEt ALl the planets List
			planetList = game.getPlanetList();
			DEFAULTTIME = game.getTimeLeft();
			pointsScored = game.getPointsScored();
			x=game.getX();
			y=game.getY();
			// setBackground(Color.red);
			setBackground(new Color(229, 204, 255));
			spaceWindow = this;
			setLayout(null);
			updateKeyControls();
			keyBoardListener = new KeyBoardCtrlListener();
			ofControl = new OrbitalForcesControl();
			playerIcon = ImageIO.read(new File(imagePath+"player.jpg"));

			dialogWindow = new DialogWindow();
			add(lblStatus);
			add(lblAction);
			add(lblScore);
			add(lblStatusMsg);
			add(lblActionMsg);
			add(lblScoreMsg);
			add(i1);
			add(i2);
			add(i3);
			add(planetButton);
			add(pw);
			add(rdw);
			add(dialogWindow);

			planetButton.setFocusable(false);
			pw.openPlanetListWindow(this);
			initialize();
			addKeyListener(keyBoardListener);
			this.setFocusable(true);
			this.setFocusTraversalKeysEnabled(false);
			EscAction escAction = new EscAction();
			getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
					KeyStroke.getKeyStroke("ESCAPE"), "doEscAction");
			getActionMap().put("doEscAction", escAction);
			planetButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					pw.setVisible(true);
				}
			});
			// Logic for resuming the game
			if (game.isResuming()) {
				game.setResuming(false);
				
				rdw.openResumeMsgWindow(spaceWindow, "Resuming the Game");

			}

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	private void updateKeyControls() {
		UserSettings userSettings = OrbitalForcesControl.USERPROFILE
				.getUserSettings();
		if (userSettings != null) {
			System.out.println("Left..." + userSettings.getLeft());
			leftCtrl = AWTKeyStroke.getAWTKeyStroke(userSettings.getLeft())
					.getKeyCode();
			rightCtrl = AWTKeyStroke.getAWTKeyStroke(userSettings.getRight())
					.getKeyCode();
			topCtrl = AWTKeyStroke.getAWTKeyStroke(userSettings.getTop())
					.getKeyCode();
			bottomCtrl = AWTKeyStroke.getAWTKeyStroke(userSettings.getBottom())
					.getKeyCode();

			System.out.println(leftCtrl + "," + rightCtrl + "," + topCtrl + ","
					+ bottomCtrl);
		}

	}

	private void initialize() {
		i1.setVisible(false);
		i2.setVisible(false);
		i3.setVisible(false);
		// lblStatus.setBounds(0, getSize().height - 70, 60, 20);
		// lblStatusMsg.setBounds(60, getSize().height - 70, 140, 20);
		// lblAction.setBounds(200, getSize().height - 70, 60, 20);
		// lblActionMsg.setBounds(260, getSize().height - 70, 140, 20);
		// lblScore.setBounds(400, getSize().height - 70, 60, 20);
		// lblScoreMsg.setBounds(460, getSize().height - 70, 50, 20);
		// planetButton.setBounds(510, getSize().height - 70, 80, 30);

		lblStatus.setLocation(0, 750);
		lblStatus.setSize(60, 20);
		lblStatusMsg.setLocation(60, 750);
		lblStatusMsg.setSize(140, 20);
		lblAction.setLocation(200, 750);
		lblAction.setSize(60, 20);
		lblActionMsg.setLocation(260, 750);
		lblActionMsg.setSize(190, 20);
		lblScore.setLocation(450, 750);
		lblScore.setSize(60, 20);
		lblScoreMsg.setLocation(510, 750);
		lblScoreMsg.setSize(30, 20);
		planetButton.setLocation(540, 750);
		planetButton.setSize(120, 20);
		pw.setLocation(1050, 0);
		pw.setSize(100, 200);
		rdw.setLocation(300, 200);
		rdw.setSize(250, 100);
		dialogWindow.setLocation(400, 400);
		dialogWindow.setSize(350, 100);
		pw.setVisible(false);
		updateMessage(lblActionMsg, "Click Get Planet to select Planet");
		updateMessage(lblStatusMsg, "You are in Space");
		updateMessage(lblScoreMsg, pointsScored + "");
		loadPlanets();
	}

	private void loadPlanets() {
		for (Planet planet : planetList) {
			JLabel lbl = new JLabel(new ImageIcon(imagePath+planet.getImageUrl()));
			add(lbl);
			lbl.setSize(40, 40);
			lbl.setLocation(planet.getX(), planet.getY());
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		applyCorderCases();
		g.drawImage(playerIcon, x, y, null);
		g.setColor(Color.BLACK);
		g.fillRect(0, 700, this.getWidth(), 5);
		g.fillRect(1000, 0, 5, this.getHeight());
		// for (Planet planet : planetList) {
		// g.setColor(planet.getPlanetColor());
		// g.fillOval(planet.getLocation().x, planet.getLocation().y,
		// planet.getDimension().width, planet.getDimension().height);
		// }

		if (planetToTravel != null) {
			int x1 = planetToTravel.getX();
			int x2 = planetToTravel.getX() + (40); // width
			int y1 = planetToTravel.getY();
			int y2 = planetToTravel.getY() + 40; // width
			if (!(spaceWindow.isPlanetArrived)) {
				if (x > x1 && x < x2 && y > y1 && y < y2) {
					spaceWindow.isPlanetArrived = true;
					worker1.cancel(true);
					worker2.cancel(true);
					worker3.cancel(true);
					timerProcess.cancel(true);
					isPlanetReached = true;
					removeKeyListener(keyBoardListener);
					dialogWindow.openWindow("Reached the Destination!!");
					System.out.println("Needs to be planet window");
				}
			}
		}
	}

	private void applyCorderCases() {
		if (y > 650)
			y = 650;
		if (y < 0)
			y = 0;
		if (x > 960)
			x = 960;
		if (x < 0)
			x = 0;
	}

	public void setPlanetToTravelDetails(Planet planet) {
		updateMessage(lblStatusMsg,
				"Planet Selected - " + planet.getPlanetName());
		updateMessage(lblActionMsg, "Press F10 to enable navigation");
		planetToTravel = planet;
		planetToTravel.setTravelling(true);
		this.setFocusable(true);
		this.setFocusTraversalKeysEnabled(false);
		addKeyListener(keyBoardListener);
		spaceWindow.requestFocus();

	}

	private void throwBullets() {
		worker1 = new SwingWorker<Void, Bullet>() {
			@Override
			protected void process(List<Bullet> arg0) {
				Bullet b = arg0.get(arg0.size() - 1);
				i1.setVisible(true);
				i1.setBounds(b.getXpos(), b.getYpos(), 20, 20);

				if (x >= b.getXpos() && x < (b.getXpos() + 30)
						&& y >= b.getYpos() && y <= b.getYpos() + 30) {
					pointsScored--;
					System.out.println("Player Marks :" + pointsScored);
					game.setPointsScored(pointsScored);
					updateMessage(lblScoreMsg, pointsScored + "");
					if (pointsScored == 0) {
						removeKeyListener(keyBoardListener);
						showMissionFailedMsg("Mission Failed.\n Lost all resources");
					}
				}
			}

			@Override
			protected void done() {
				i1.setVisible(false);
			}

			@Override
			protected Void doInBackground() throws Exception {
				// Simulate doing something useful.

				Bullet b = new Bullet();
				while (true) {
					int i;
					b.setYpos(getRandomHeight());
					for (i = 970; i >= 0; i -= 10) {
						Thread.sleep(20);
						b.setXpos(i);
						publish(b);
					}

				}

			}
		};
		worker2 = new SwingWorker<Void, Bullet>() {
			@Override
			protected void process(List<Bullet> arg0) {
				Bullet b = arg0.get(arg0.size() - 1);
				i2.setVisible(true);

				i2.setBounds(b.getXpos(), b.getYpos(), 20, 20);
				if (x >= b.getXpos() && x < (b.getXpos() + 30)
						&& y >= b.getYpos() && y <= b.getYpos() + 30) {
					pointsScored--;
					game.setPointsScored(pointsScored);
					System.out.println("Player Marks :" + pointsScored);
					updateMessage(lblScoreMsg, pointsScored + "");
					if (pointsScored == 0) {
						removeKeyListener(keyBoardListener);
						showMissionFailedMsg("Mission Failed.\n Lost all resources");
					}
				}
			}

			@Override
			protected void done() {
				i2.setVisible(false);
			}

			@Override
			protected Void doInBackground() throws Exception {
				// Simulate doing something useful.
				Bullet b = new Bullet();
				while (true) {

					b.setYpos(getRandomHeight());
					int i;
					for (i = 970; i >= 0; i -= 10) {
						Thread.sleep(50);
						b.setXpos(i);
						publish(b);
					}
				}

			}
		};
		worker3 = new SwingWorker<Void, Bullet>() {
			@Override
			protected void process(List<Bullet> arg0) {
				Bullet b = arg0.get(arg0.size() - 1);
				i3.setVisible(true);
				i3.setBounds(b.getXpos(), b.getYpos(), 20, 20);
				if (x >= b.getXpos() && x < (b.getXpos() + 30)
						&& y >= b.getYpos() && y <= b.getYpos() + 30) {
					pointsScored--;
					game.setPointsScored(pointsScored);
					System.out.println("Player Marks :" + pointsScored);
					updateMessage(lblScoreMsg, pointsScored + "");
					if (pointsScored == 0) {
						removeKeyListener(keyBoardListener);
						showMissionFailedMsg("Mission Failed.\n Lost all resources");
					}
				}
			}

			@Override
			protected void done() {
				i3.setVisible(false);
			}

			@Override
			protected Void doInBackground() throws Exception {
				// Simulate doing something useful.
				Bullet b = new Bullet();
				while (true) {
					b.setYpos(getRandomHeight());
					int i;
					for (i = 970; i >= 0; i -= 10) {
						Thread.sleep(30);
						b.setXpos(i);
						publish(b);
					}
				}
			}
		};
		worker1.execute();
		worker2.execute();
		worker3.execute();
		System.out.println("I am worker");
	}

	public void enableNavigation() {
		try {
			updateMessage(lblActionMsg, "Use Ctrls to navigate");
			spaceWindow.setFocusable(true);
			spaceWindow.setFocusTraversalKeysEnabled(false);
			playerIcon = ImageIO.read(new File(imagePath+"rocket.jpg"));
			repaint();
			timerStart();
			spaceWindow.requestFocus();
			isNavigationEnabled = true;
			throwBullets();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	private int getRandomHeight() {
		int randomInt = r.nextInt(650);
		return randomInt + 10;
	}

	public void timerStart() {
		timerProcess = new SwingWorker<Void, Void>() {
			@Override
			protected void process(List<Void> chunks) {
				updateMessage(lblStatusMsg, "Time Left:" + DEFAULTTIME);
			}

			@Override
			protected Void doInBackground() throws Exception {
				while (true) {
					DEFAULTTIME--;
					game.setTimeLeft(DEFAULTTIME);
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
				removeKeyListener(keyBoardListener);
				if (DEFAULTTIME <= 0) {
					showMissionFailedMsg("Mission Failed.\n Not reached the planet in time.");
				}
			}
		};

		timerProcess.execute();
	}

	public void attachKeyListener() {
		KeyBoardCtrlListener kCtrl = new KeyBoardCtrlListener();
		this.addKeyListener(kCtrl);
	}

	public class KeyBoardCtrlListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent arg0) {
			if (!isPlanetSelected && arg0.getKeyCode() == KeyEvent.VK_F2) {
				PlanetsListWindow plist = new PlanetsListWindow();
				plist.openPlanetListWindow(spaceWindow);
			}
			if (!isNavigationEnabled && arg0.getKeyCode() == KeyEvent.VK_F10) {
				enableNavigation();
			}
			if (isNavigationEnabled) {
				System.out.println("navigation code ..." + arg0.getKeyCode());
				if (arg0.getKeyCode() == leftCtrl) {
					x -= 5;
				} else if (arg0.getKeyCode() == rightCtrl) {
					x += 5;
				} else if (arg0.getKeyCode() == topCtrl) {
					y -= 5;
				} else if (arg0.getKeyCode() == bottomCtrl) {
					y += 5;
				}
			}
			// System.out.println("Im still pressed");
			repaint();

		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			// System.out.println("Im still released");
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			// System.out.println("Im still typed");
		}
	}

	private void updateMessage(JLabel lbl, String message) {
		lbl.setText(message);
	}

	public void showMissionFailedMsg(String message) {
		worker1.cancel(true);
		worker2.cancel(true);
		worker3.cancel(true);
		isTimeout = true;
		dialogWindow.openWindow(message);
	}

	public class DialogWindow extends JPanel implements ActionListener {
		JLabel lblText = new JLabel("You reached Successfully");
		JButton btnOk = new JButton("ok");

		public DialogWindow() {
			setLayout(null);
			lblText.setLocation(40, 10);
			lblText.setSize(300, 20);
			btnOk.setLocation(120, 40);
			btnOk.setSize(60, 20);
			add(lblText);
			add(btnOk);
			setSize(250, 100);
			setVisible(false);
			btnOk.addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			CardLayout cardLayout = (CardLayout) OrbitalForces.panelContainer
					.getLayout();
			if (isPlanetArrived) {
				System.out.println(planetToTravel.getPlanetName());
				planetName = planetToTravel.getPlanetName();
				planetToTravel.setTravelling(true);
				System.out.println("Planet name :" + planetName);
				OrbitalForces.panelContainer.add(new PlanetWindow(), "planet");
				cardLayout.show(OrbitalForces.panelContainer, "planet");

			}
			if (isTimeout) {
				cardLayout.show(OrbitalForces.panelContainer, "home");
			}

		}

		public void openWindow(String message) {
			this.setVisible(true);
			lblText.setText(message);
		}

		public void hideWindow() {
			this.setVisible(false);
		}
	}

	public class EscAction extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.out.println("I am in Esc in SPace");
			game.setResuming(true);
			game.setResumeWindow("space");
			game.setX(x);
			game.setY(y);
			CardLayout cardLayout = (CardLayout) OrbitalForces.panelContainer
					.getLayout();
			OrbitalForces.panelContainer.add(new HomeWindow(), "home");
			cardLayout.show(OrbitalForces.panelContainer, "home");
		}
	}
}
