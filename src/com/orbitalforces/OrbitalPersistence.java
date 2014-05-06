package com.orbitalforces;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.orbitalforces.model.Game;
import com.orbitalforces.model.ObjectItem;
import com.orbitalforces.model.Planet;
import com.orbitalforces.model.UserProfile;
import com.orbitalforces.model.UserSettings;

public class OrbitalPersistence {
	static String database = "jdbc:mysql://localhost:3306/orbitalforces";
	static String user = "root";
	static String pwd = "mypassword";
	Connection conn = null;

	public boolean saveUser(UserProfile userProfile) {
		StringBuilder sb = new StringBuilder("");
		sb.append("{call SaveUser('" + userProfile.getName() + "','"
				+ userProfile.getUsername() + "'," + userProfile.getAge()
				+ ")}");
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(database, user, pwd);
			CallableStatement cs = conn.prepareCall(sb.toString());
			ResultSet rs = cs.executeQuery();
			int userID = 0;
			while (rs.next()) {
				userID = rs.getInt("userID");
				break;
			}
			userProfile.setUserProfileId(userID);
			return true;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			// ex.printStackTrace();
			System.out.println("s=:" + sb.toString());
			System.out.println("exception than :"
					+ Thread.currentThread().getName());
			System.exit(0);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return false;

	}

	public boolean saveSettings(UserSettings userSettings) {
		StringBuilder sb = new StringBuilder("");
		sb.append("{call SaveUserSettings(" + userSettings.getUserId() + ",'"
				+ userSettings.getLeft() + "','" + userSettings.getRight()
				+ "','" + userSettings.getTop() + "','"
				+ userSettings.getBottom() + "','" + userSettings.getCombat()
				+ "','" + userSettings.getLaunch() + "','"
				+ userSettings.getCivilization() + "',"
				+ userSettings.getVolume() + ")}");
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(database, user, pwd);
			CallableStatement cs = conn.prepareCall(sb.toString());
			cs.execute();
			return true;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			// ex.printStackTrace();
			System.out.println("s=:" + sb.toString());
			System.out.println("exception than :"
					+ Thread.currentThread().getName());
			System.exit(0);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return false;

	}

	public List<UserProfile> getUserProfiles() {
		StringBuilder sb = new StringBuilder();
		sb.append("select userId,name,username,age from userProfile order by userId desc");
		List<UserProfile> userProfileList = new ArrayList<UserProfile>();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(database, user, pwd);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sb.toString());
			while (rs.next()) {
				UserProfile us = new UserProfile();
				us.setAge(rs.getInt("age"));
				us.setUserProfileId(rs.getInt("userId"));
				us.setName(rs.getString("name"));
				us.setUsername(rs.getString("username"));
				userProfileList.add(us);
			}
			return userProfileList;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			// ex.printStackTrace();
			System.out.println("s=:" + sb.toString());
			System.out.println("exception than :"
					+ Thread.currentThread().getName());
			System.exit(0);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return userProfileList;
	}

	public boolean isUserAlreadyExists(UserProfile userProfile) {
		StringBuilder sb = new StringBuilder();
		sb.append("select username from userProfile where username='"
				+ userProfile.getUsername() + "';");
		List<UserProfile> userProfileList = new ArrayList<UserProfile>();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(database, user, pwd);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sb.toString());
			while (rs.next()) {
				return true;
			}
			return false;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			// ex.printStackTrace();
			System.out.println("s=:" + sb.toString());
			System.out.println("exception than :"
					+ Thread.currentThread().getName());
			System.exit(0);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return true;
	}

	public UserProfile loadUserProfile(UserProfile userProfile) {

		StringBuilder sb = new StringBuilder();
		sb.append("select * from userSettings us left outer join game g on us.userid=g.userprofileid"
				+ "where us.userid=" + userProfile.getUserProfileId());
		List<UserProfile> userProfileList = new ArrayList<UserProfile>();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(database, user, pwd);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sb.toString());
			UserSettings uSettings = new UserSettings();
			Game g = new Game();
			while (rs.next()) {
				uSettings.setLeft(rs.getString("left_ctrl"));
				uSettings.setBottom(rs.getString("bottom_ctrl"));
				uSettings.setTop(rs.getString("top_ctrl"));
				uSettings.setRight(rs.getString("right_ctrl"));
				uSettings.setCombat(rs.getString("combat_ctrl"));
				uSettings.setLaunch(rs.getString("launch_ctrl"));
				uSettings.setCivilization(rs.getString("civilization_ctrl"));

			}
			userProfile.setUserSettings(uSettings);
			return userProfile;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			// ex.printStackTrace();
			System.out.println("s=:" + sb.toString());
			System.out.println("exception than :"
					+ Thread.currentThread().getName());
			System.exit(0);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return userProfile;
	}

	public List<Planet> getPlanetsList() {

		StringBuilder sb = new StringBuilder();
		sb.append("select * from planet");
		List<Planet> planetsList = new ArrayList<Planet>();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(database, user, pwd);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sb.toString());
			while (rs.next()) {
				Planet planet = new Planet();
				planet.setPlanetId(rs.getInt("PlanetId"));
				planet.setPlanetName(rs.getString("PlanetName"));
				planet.setImageUrl(rs.getString("ImageUrl"));
				planet.setX(rs.getInt("X"));
				planet.setY(rs.getInt("Y"));
				planetsList.add(planet);
			}

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			// ex.printStackTrace();
			System.out.println("s=:" + sb.toString());
			System.out.println("exception than :"
					+ Thread.currentThread().getName());
			System.exit(0);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		for (Planet planet : planetsList) {
			planet.setObjects(getPlanetObjects(planet.getPlanetId()));
		}
		return planetsList;
	}

	public List<ObjectItem> getPlanetObjects(int planetId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select o.* from PlanetObject po, Object o where po.objectid=o.objectid and po.planetid="
				+ planetId);
		List<ObjectItem> objectsList = new ArrayList<ObjectItem>();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(database, user, pwd);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sb.toString());
			while (rs.next()) {
				ObjectItem objectItem = new ObjectItem();
				objectItem.setObjectId(rs.getInt("ObjectId"));
				objectItem.setX(rs.getInt("X"));
				objectItem.setY(rs.getInt("Y"));
				objectItem.setPoints(rs.getInt("points"));
				objectItem.setType(rs.getString("Type"));
				objectItem.setImageUrl(rs.getString("imageUrl"));
				objectsList.add(objectItem);
			}
			return objectsList;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			// ex.printStackTrace();
			System.out.println("s=:" + sb.toString());
			System.out.println("exception than :"
					+ Thread.currentThread().getName());
			System.exit(0);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return objectsList;
	}

	public boolean saveGame(int userid, String gameContext) {
		StringBuilder sb = new StringBuilder("");
		sb.append("{call SaveGame(" + userid + ",'" + gameContext + "')}");
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(database, user, pwd);
			CallableStatement cs = conn.prepareCall(sb.toString());
			cs.execute();
			return true;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			// ex.printStackTrace();
			System.out.println("s=:" + sb.toString());
			System.out.println("exception than :"
					+ Thread.currentThread().getName());
			System.exit(0);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return false;
	}

	public String getGameContext(int userID) {
		StringBuilder sb = new StringBuilder();
		sb.append("select gameContext from game where userProfileId=" + userID);
		String gameContext = "";
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(database, user, pwd);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sb.toString());

			while (rs.next()) {
				gameContext = rs.getString("gameContext");
			}
			return gameContext;

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			// ex.printStackTrace();
			System.out.println("s=:" + sb.toString());
			System.out.println("exception than :"
					+ Thread.currentThread().getName());
			System.exit(0);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return gameContext;
	}

	public int getUserID(String username) {
		StringBuilder sb = new StringBuilder();
		sb.append("select userId from userProfile where username='" + username
				+ "';");
		int userId = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(database, user, pwd);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sb.toString());

			while (rs.next()) {
				userId = rs.getInt("userId");
			}
			return userId;

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			// ex.printStackTrace();
			System.out.println("s=:" + sb.toString());
			System.out.println("exception than :"
					+ Thread.currentThread().getName());
			System.exit(0);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return userId;
	}

	public UserSettings getUserSettings(int userId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from UserSettings where userId=" + userId);
		UserSettings userSettings = new UserSettings();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(database, user, pwd);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sb.toString());
			while (rs.next()) {
				userSettings.setLeft(rs.getString("left_ctrl"));
				userSettings.setRight(rs.getString("right_ctrl"));
				userSettings.setBottom(rs.getString("bottom_ctrl"));
				userSettings.setTop(rs.getString("top_ctrl"));
				userSettings.setCivilization(rs.getString("civilization_ctrl"));
				userSettings.setLaunch(rs.getString("launch_ctrl"));
				userSettings.setCombat(rs.getString("combat_ctrl"));
			}

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			// ex.printStackTrace();
			System.out.println("s=:" + sb.toString());
			System.out.println("exception than :"
					+ Thread.currentThread().getName());
			System.exit(0);
		} finally {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
		return userSettings;
	}
}
