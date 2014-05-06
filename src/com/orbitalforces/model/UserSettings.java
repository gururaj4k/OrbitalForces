package com.orbitalforces.model;

import java.io.Serializable;

public class UserSettings implements Serializable{

	private int userId;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	private String left;
	private String right;
	private String top;
	private String bottom;
	private String civilization;
	private String combat;
	private int volume;
	private String launch;
	public String getLaunch() {
		return launch;
	}
	public void setLaunch(String launch) {
		this.launch = launch;
	}
	public String getLeft() {
		return left;
	}
	public void setLeft(String left) {
		this.left = left;
	}
	public String getRight() {
		return right;
	}
	public void setRight(String right) {
		this.right = right;
	}
	public String getTop() {
		return top;
	}
	public void setTop(String top) {
		this.top = top;
	}
	public String getBottom() {
		return bottom;
	}
	public void setBottom(String bottom) {
		this.bottom = bottom;
	}
	public String getCivilization() {
		return civilization;
	}
	public void setCivilization(String civilization) {
		this.civilization = civilization;
	}
	public String getCombat() {
		return combat;
	}
	public void setCombat(String combat) {
		this.combat = combat;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
}
