package com.orbitalforces.windows;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;

import javax.swing.JFrame;

public class FullScreen {

	/**
	 * @param args
	 */
	private GraphicsDevice gd;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public FullScreen() {
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		gd = ge.getDefaultScreenDevice();
	}

	public void setFullScreen(DisplayMode dm, JFrame w) {
		w.setUndecorated(true);
		w.setResizable(false);
		gd.setFullScreenWindow(w);
		try {
			if (dm != null && gd.isDisplayChangeSupported()) {
				gd.setDisplayMode(dm);
			}
		} catch (Exception ex) {

		}
	}

	public Window getFullScreenWindow() {
		return gd.getFullScreenWindow();
	}

	public void restoreFullScreen() {
		Window w = gd.getFullScreenWindow();
		if (w != null) {
			w.dispose();
		}
		gd.setFullScreenWindow(null);
	}
}
