package com.orbitalforces;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.orbitalforces.model.UserProfile;

public class OrbitalPersistanceTest {

	OrbitalPersistence orbitalPersistence;
	@Mock
	Connection connectionMock;
	@Mock
	Statement statementMock;
	@Mock
	ResultSet resultSetMock;
	@Mock
	UserProfile userProfileMock;

	@Before
	public void initialize() throws Exception {
		orbitalPersistence = new OrbitalPersistence();
		connectionMock = Mockito.mock(Connection.class);
		statementMock = Mockito.mock(Statement.class);
		resultSetMock = Mockito.mock(ResultSet.class);
		userProfileMock = Mockito.mock(UserProfile.class);
		orbitalPersistence.conn = connectionMock;
		Mockito.when(connectionMock.createStatement())
				.thenReturn(statementMock);
		Mockito.when(statementMock.executeQuery(Mockito.anyString()))
				.thenReturn(resultSetMock);
		Mockito.when(resultSetMock.next()).thenReturn(true);
		Mockito.when(resultSetMock.getString("left_ctrl")).thenReturn("LEFT");
	}

	@Test
	public void loadUserProfileTest() {
		UserProfile userProfile = orbitalPersistence
				.loadUserProfile(userProfileMock);
		assertNotNull(userProfile);
		assertNotNull(userProfile.getUserSettings());

	}
}
