package com.orbitalforces;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.orbitalforces.model.Game;
import com.orbitalforces.model.UserProfile;
import com.orbitalforces.model.UserSettings;

public class OrbitalForcesControlTest {

	OrbitalForcesControl orbitalForcesCtrl;
	@Mock
	OrbitalPersistence orbitalPersistanceMock;
	@Mock
	UserProfile userProfileMock;
	@Mock
	UserSettings userSettingsMock;

	@Mock
	Game gameMock;

	@Before
	public void initialize() {
		orbitalForcesCtrl = new OrbitalForcesControl();
		orbitalPersistanceMock = Mockito.mock(OrbitalPersistence.class);
		userProfileMock = Mockito.mock(UserProfile.class);
		userSettingsMock = Mockito.mock(UserSettings.class);
		orbitalForcesCtrl.oPersistence = orbitalPersistanceMock;
		Mockito.when(userProfileMock.getUserProfileId()).thenReturn(1);
		Mockito.when(
				orbitalPersistanceMock.isUserAlreadyExists(Mockito
						.any(UserProfile.class))).thenReturn(false);
		Mockito.when(
				orbitalPersistanceMock.saveUser(Mockito.any(UserProfile.class)))
				.thenReturn(true);
		Mockito.when(
				orbitalPersistanceMock.getUserSettings(userProfileMock
						.getUserProfileId())).thenReturn(userSettingsMock);
	}

	@Test
	public void saveUserProfileCheckPositive() {
		int resp = orbitalForcesCtrl.saveUserProfile(userProfileMock);
		assertEquals(resp, 0);
		assertNotNull(userProfileMock.getUserSettings());
	}

	@Test
	public void saveUserProfileCheckNegative() {
		Mockito.when(
				orbitalPersistanceMock.isUserAlreadyExists(Mockito
						.any(UserProfile.class))).thenReturn(true);
		int resp = orbitalForcesCtrl.saveUserProfile(userProfileMock);
		assertEquals(resp, -1);
		assertNull(userProfileMock.getUserSettings());
	}

	@Test
	public void serializeGame() {
		gameMock = Mockito.mock(Game.class);
		Mockito.when(gameMock.getX()).thenReturn(100);
		String gameString = orbitalForcesCtrl.serializeGame(gameMock);
		assertNotNull(gameString);
		assertEquals(gameString.contains("100"), true);
	}

}
