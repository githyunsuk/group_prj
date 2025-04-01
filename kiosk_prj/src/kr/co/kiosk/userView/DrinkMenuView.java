package kr.co.kiosk.userView;

import java.awt.Panel;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class DrinkMenuView extends Panel{
	private UserMainView umv;
	
	public DrinkMenuView(UserMainView umv) {
		this.umv = umv;
		add(new JButton("음료"));
	}

}
