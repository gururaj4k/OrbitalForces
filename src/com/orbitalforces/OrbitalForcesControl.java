package com.orbitalforces;

import java.util.List;

import com.orbitalforces.model.Planet;
import com.orbitalforces.model.UserProfile;
import com.orbitalforces.model.UserSettings;

public class OrbitalForcesControl {

	OrbitalPersistence oPersistence = new OrbitalPersistence();
	public static UserProfile USERPROFILE;

	public int saveUserProfile(UserProfile userProfile) {
		if (userProfile != null) {
			if (!oPersistence.isUserAlreadyExists(userProfile)) {
				if (oPersistence.saveUser(userProfile)) {
					userProfile.setUserSettings(oPersistence
							.getUserSettings(userProfile.getUserProfileId()));
					return 0;
				}
			} else {
				return -1;
			}
		}
		return -2;
	}

	public List<UserProfile> getUserProfiles() {
		return oPersistence.getUserProfiles();
	}
	
	public UserSettings getUserSettings(int userID){
		return oPersistence.getUserSettings(userID);
	}

//	public UserProfile loadUserProfile(UserProfile userProfile) {
//		userProfile = oPersistence.loadUserProfile(userProfile);
//		USERPROFILE = userProfile;
//		return userProfile;
//	}

	public boolean saveSettings(UserSettings userSettings) {
		return oPersistence.saveSettings(userSettings);
	}

	public List<Planet> getPlanetsList() {
		return oPersistence.getPlanetsList();
	}

	public boolean saveGame(int userId, String gameContext) {
		return oPersistence.saveGame(userId, gameContext);
	}

	public String getGameContext(int userID) {
		return oPersistence.getGameContext(userID);
	}

	public int getUserID(String username) {
		return oPersistence.getUserID(username);
	}
}
