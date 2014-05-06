package com.orbitalforces.model;

import java.io.Serializable;

public class ObjectItem implements Serializable{
private int  objectId;
private int x;
private int y;
private int points;
private String type;
private String imageUrl;
private boolean isAttacked;
public boolean isAttacked() {
	return isAttacked;
}
public void setAttacked(boolean isAttacked) {
	this.isAttacked = isAttacked;
}
public String getImageUrl() {
	return imageUrl;
}
public void setImageUrl(String imageUrl) {
	this.imageUrl = imageUrl;
}
public int getObjectId() {
	return objectId;
}
public void setObjectId(int objectId) {
	this.objectId = objectId;
}
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
public int getPoints() {
	return points;
}
public void setPoints(int points) {
	this.points = points;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
}
