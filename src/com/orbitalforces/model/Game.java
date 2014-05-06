package com.orbitalforces.model;

import java.io.Serializable;
import java.util.List;

public class Game implements Serializable {
private List<Planet> planetList;
private int pointsScored=5;
private int timeLeft=30;
private boolean isResuming;
private String resumeWindow;
private int combatCount=5;
private int x;
public int getX() {
	return x;
}
public void setX(int x) {
	this.x = x;
}
public int getY() {
	return y;
}
public void setY(int y) {
	this.y = y;
}
private int y;
public int getCombatCount() {
	return combatCount;
}
public void setCombatCount(int combatCount) {
	this.combatCount = combatCount;
}
public String getResumeWindow() {
	return resumeWindow;
}
public void setResumeWindow(String resumeWindow) {
	this.resumeWindow = resumeWindow;
}
public boolean isResuming() {
	return isResuming;
}
public void setResuming(boolean isResuming) {
	this.isResuming = isResuming;
}
public int getTimeLeft() {
	return timeLeft;
}
public void setTimeLeft(int timeLeft) {
	this.timeLeft = timeLeft;
}
public List<Planet> getPlanetList() {
	return planetList;
}
public void setPlanetList(List<Planet> planetList) {
	this.planetList = planetList;
}
public int getPointsScored() {
	return pointsScored;
}
public void setPointsScored(int pointsScored) {
	this.pointsScored = pointsScored;
}
	
}
