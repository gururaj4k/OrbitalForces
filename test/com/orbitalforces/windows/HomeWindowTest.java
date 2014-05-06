package com.orbitalforces.windows;

import static org.junit.Assert.*;

import java.awt.event.ActionEvent;

import javax.swing.JButton;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.orbitalforces.OrbitalForcesControl;
import com.orbitalforces.model.UserProfile;
import com.orbitalforces.windows.HomeWindow.HomeMenuListener;

public class HomeWindowTest {
	HomeWindow homeWindow;
	HomeMenuListener homeMenuListener;
	@Mock
	ActionEvent actionEventMock;
	@Mock
	JButton btnMock;
	@Mock
	UserProfile userProfileMock;
	@Mock
	OrbitalForcesControl orbitalForcesContrl;
	
	@Before
	public void initialize(){
		homeWindow=new HomeWindow();
		homeMenuListener=homeWindow.new HomeMenuListener();
		actionEventMock=Mockito.mock(ActionEvent.class);
		btnMock=Mockito.mock(JButton.class);
		userProfileMock=Mockito.mock(UserProfile.class);
		orbitalForcesContrl=Mockito.mock(OrbitalForcesControl.class);
		Mockito.when(actionEventMock.getSource()).thenReturn(btnMock);
		Mockito.when(btnMock.getText()).thenReturn("LOAD");
		Mockito.when(userProfileMock.getUserProfileId()).thenReturn(1);
		Mockito.when(orbitalForcesContrl.getGameContext(1)).thenReturn(null);
		homeWindow.btnLoadNSave=btnMock;
		OrbitalForcesControl.USERPROFILE=userProfileMock;
	}
	
	@Test
	public void loadSavedGamesTest(){
		homeMenuListener.actionPerformed(actionEventMock);
		assertEquals(homeWindow.lblMessage.getText(),"No saved Games under this profile");
	}

}
