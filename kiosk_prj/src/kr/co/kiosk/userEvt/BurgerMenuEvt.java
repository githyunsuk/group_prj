package kr.co.kiosk.userEvt;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import kr.co.kiosk.userView.BurgerMenuView;
import kr.co.kiosk.userView.UserMainView;

public class BurgerMenuEvt implements ActionListener {

	private BurgerMenuView bmv;
	private UserMainView umv;
	private List<String> menuItems;

	public BurgerMenuEvt(BurgerMenuView bmv, UserMainView umv) {
		this.bmv = bmv;
		this.umv = umv;
		menuItems = getMenuFromDB();
	}// BurgerMenuEvt

	// 데이터를 가져와서 메뉴판을 채우는 method
	public void loadMenu() {
		bmv.getMenuPanel().removeAll(); // 기존 메뉴 삭제

		int start = bmv.getCurrentPage() * BurgerMenuView.getMaxPerPage();
		int end = Math.min(start + BurgerMenuView.getMaxPerPage(), menuItems.size());

		for (int i = start; i < end; i++) {
			String item = menuItems.get(i);
			JButton btn = new JButton("이미지");
			JLabel lbl = new JLabel(item, SwingConstants.CENTER);
			JPanel itemPanel = new JPanel(new BorderLayout());

			itemPanel.add(btn, BorderLayout.CENTER);
			itemPanel.add(lbl, BorderLayout.SOUTH);

			btn.addActionListener(e -> umv.getDtm().addRow(new Object[]{"자바버거 세트", 1, "8,000"})); // 각 버튼 클릭하면

			bmv.getMenuPanel().add(itemPanel);
		}

		// 버튼 활성화/비활성화 조정
		bmv.getBtnPrev().setEnabled(bmv.getCurrentPage() > 0);
		bmv.getBtnNext().setEnabled((bmv.getCurrentPage() + 1) * BurgerMenuView.getMaxPerPage() < menuItems.size());

		//메뉴 채우기
		bmv.getMenuPanel().revalidate(); 
		bmv.getMenuPanel().repaint();
	}// loadMenu

	// 임시데이터
	private List<String> getMenuFromDB() {
		List<String> menu = new ArrayList<>();
		menu.add("불고기버거 " + " 5000원");
		menu.add("불고기버거 " + " 5000원");
		menu.add("불고기버거 " + " 5000원");
		menu.add("불고기버거 " + " 5000원");
		menu.add("불고기버거 " + " 5000원");
		menu.add("불고기버거 " + " 5000원");
		menu.add("불고기버거 " + " 5000원");
		menu.add("불고기버거 " + " 5000원");
		menu.add("불고기버거 " + " 5000원");
		menu.add("새우버거 " + " 6000원");
		menu.add("새우버거 " + " 6000원");
		menu.add("새우버거 " + " 6000원");
		menu.add("새우버거 " + " 6000원");
		menu.add("새우버거 " + " 6000원");
		menu.add("새우버거 " + " 6000원");
		menu.add("새우버거 " + " 6000원");
		menu.add("새우버거 " + " 6000원");
		menu.add("새우버거 " + " 6000원");
		menu.add("치킨버거 " + " 7000원");
		menu.add("치킨버거 " + " 7000원");
		menu.add("치킨버거 " + " 7000원");
		menu.add("치킨버거 " + " 7000원");
		menu.add("치킨버거 " + " 7000원");
//		menu.add("치킨버거 " + " 7000원");
//		menu.add("치킨버거 " + " 7000원");
//		menu.add("치킨버거 " + " 7000원");
//		menu.add("치킨버거 " + " 7000원");

		return menu;
	}// getMenuFromDB

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == bmv.getBtnNext()) {
			if ((bmv.getCurrentPage() + 1) * BurgerMenuView.getMaxPerPage() < menuItems.size()) {
				bmv.setCurrentPage(bmv.getCurrentPage() + 1);
				loadMenu();
			}
		}

		if (e.getSource() == bmv.getBtnPrev()) {
			if (bmv.getCurrentPage() > 0) {
				bmv.setCurrentPage(bmv.getCurrentPage() - 1);
				loadMenu();
			}
		}

	}// actionPerformed

}
