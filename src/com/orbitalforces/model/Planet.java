package com.orbitalforces.model;

import java.io.Serializable;
import java.util.List;

public class Planet implements Serializable{

	private int planetId;
	private String planetName;
	private String imageUrl;
	private int x;
	private int y;
	private List<ObjectItem> objects;
	private boolean isConquered;
	private boolean isTravelling;
	private List<Building> buildingList;
	public List<Building> getBuildingList() {
		return buildingList;
	}
	public void setBuildingList(List<Building> buildingList) {
		this.buildingList = buildingList;
	}
	public boolean isTravelling() {
		return isTravelling;
	}
	public void setTravelling(boolean isTravelling) {
		this.isTravelling = isTravelling;
	}
	public boolean isConquered() {
		return isConquered;
	}
	public void setConquered(boolean isConquered) {
		this.isConquered = isConquered;
	}
	public int getPlanetId() {
		return planetId;
	}
	public List<ObjectItem> getObjects() {
		return objects;
	}
	public void setObjects(List<ObjectItem> objects) {
		this.objects = objects;
	}
	public void setPlanetId(int planetId) {
		this.planetId = planetId;
	}
	public String getPlanetName() {
		return planetName;
	}
	public void setPlanetName(String planetName) {
		this.planetName = planetName;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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

 public String toString(){
	 return this.planetName;
 }
	
}
