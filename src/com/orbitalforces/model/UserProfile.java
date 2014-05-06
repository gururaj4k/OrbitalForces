package com.orbitalforces.model;

import java.io.Serializable;

public class UserProfile implements Serializable{
	private int userProfileId;
	private int age;
	private String name;
	private String username;
	private Game game;
	private UserSettings userSettings;
	public UserSettings getUserSettings() {
		return userSettings;
	}
	public void setUserSettings(UserSettings userSettings) {
		this.userSettings = userSettings;
	}
	public Game getGame() {
		return game;
	}
	public void setGame(Game game) {
		this.game = game;
	}
	public int getUserProfileId() {
		return userProfileId;
	}
	public void setUserProfileId(int userProfileId) {
		this.userProfileId = userProfileId;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
