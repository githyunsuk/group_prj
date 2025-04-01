package kr.co.kiosk.userView;

import java.awt.Panel;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class RecommendMenuView extends Panel {
	private UserMainView umv;
	
	public RecommendMenuView(UserMainView umv) {
		this.umv = umv;
		add(new JButton("추천 메뉴"));
	}

}
