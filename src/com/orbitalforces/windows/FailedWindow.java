package com.orbitalforces.windows;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class FailedWindow extends JPanel {
	JLabel l = new JLabel("You Failed buddy!!!!!");

	public FailedWindow() {
		add(l);
		l.setSize(40, 50);
	}
}
