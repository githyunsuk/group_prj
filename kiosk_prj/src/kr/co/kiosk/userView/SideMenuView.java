package kr.co.kiosk.userView;

import java.awt.Panel;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class SideMenuView extends Panel {
	private UserMainView umv;
	
	public SideMenuView(UserMainView umv) {
		this.umv = umv;
		add(new JButton("사이드"));
	}

}
