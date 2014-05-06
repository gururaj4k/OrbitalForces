package com.orbitalforces.windows;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import com.google.gson.Gson;
import com.orbitalforces.OrbitalForces;
import com.orbitalforces.OrbitalForcesControl;
import com.orbitalforces.model.Game;
import com.orbitalforces.model.UserProfile;

public class ExistingProfiles extends JPanel implements ActionListener {
	OrbitalForcesControl ofControl = null;
	String columnNames[] = { "S.No", "USERNAME", "NAME", "AGE" };
	JTable tblProfiles = null;
	JScrollPane scrollPane = null;
	JButton btnSubmit = new JButton("Submit");

	public ExistingProfiles() {
		setLayout(null);
		loadProfiles();
		tblProfiles.getSelectionModel().addListSelectionListener(
				new RowListener());
		scrollPane = new JScrollPane(tblProfiles);
		add(scrollPane, BorderLayout.CENTER);
		initialize();
		add(btnSubmit);
		btnSubmit.addActionListener(this);

	}

	private void initialize() {
		scrollPane.setSize(400, 200);
		scrollPane.setLocation(400, 50);
		btnSubmit.setLocation(500, 400);
		btnSubmit.setSize(80, 40);
		tblProfiles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < tblProfiles.getColumnCount(); i++) {
			tblProfiles.getColumnModel().getColumn(i)
					.setCellRenderer(centerRenderer);
		}
	}

	private void loadProfiles() {
		ofControl = new OrbitalForcesControl();
		List<UserProfile> profilesList = ofControl.getUserProfiles();
		String[][] profileDataArr = new String[profilesList.size()][4];

		int userProfileIndex = 1;

		for (UserProfile userProfile : profilesList) {
			String[] strArr = new String[4];
			strArr[0] = userProfileIndex + "";
			strArr[1] = userProfile.getUsername();
			strArr[2] = userProfile.getName();
			strArr[3] = userProfile.getAge() + "";
			profileDataArr[userProfileIndex - 1] = strArr;
			userProfileIndex++;
		}
		tblProfiles = new JTable(profileDataArr, columnNames) {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
	}

	public class RowListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent event) {
			if (event.getValueIsAdjusting()) {
				return;
			}
			System.out.println("ROW SELECTION EVENT. ");

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int i = tblProfiles.getSelectionModel().getLeadSelectionIndex();
		System.out.println(tblProfiles.getSelectionModel()
				.getLeadSelectionIndex());
		if (i >= 0) {
			UserProfile userProfile = new UserProfile();
			userProfile.setUsername(tblProfiles.getModel().getValueAt(i, 1)
					.toString());
			userProfile.setName(tblProfiles.getModel().getValueAt(i, 2)
					.toString());
			userProfile.setAge(Integer.parseInt(tblProfiles.getModel()
					.getValueAt(i, 3).toString()));
			// User Profile has been updated.
			userProfile.setUserProfileId(ofControl.getUserID(userProfile
					.getUsername()));
			userProfile.setUserSettings(ofControl.getUserSettings(userProfile
					.getUserProfileId()));
			ofControl.USERPROFILE = userProfile;
			CardLayout cardLayout = (CardLayout) OrbitalForces.panelContainer
					.getLayout();
			OrbitalForces.panelContainer.add(new HomeWindow(), "home");
			cardLayout.show(OrbitalForces.panelContainer, "home");
		}

	}

}
