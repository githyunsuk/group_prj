package kr.co.kiosk.userEvt;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import kr.co.kiosk.service.MenuService;
import kr.co.kiosk.userView.RecommendMenuView;
import kr.co.kiosk.userView.UserMainView;
import kr.co.kiosk.vo.MenuVO;

public class RecommendMenuEvt {

	private RecommendMenuView rmv;
	private UserMainView umv;
	private List<MenuVO> recommendList;

	public RecommendMenuEvt(RecommendMenuView rmv, UserMainView umv) {
		this.rmv = rmv;
		this.umv = umv;
		recommendList = getRecommendMenuFromDB();
	}// RecommendMenuEvt

	private List<MenuVO> getRecommendMenuFromDB() {
		List<MenuVO> recommendList = new ArrayList<MenuVO>();
		List<MenuVO> allMenuList = new ArrayList<MenuVO>();

		MenuService ms = new MenuService();
		allMenuList = ms.searchAllMenu();

		for (MenuVO mVO : allMenuList) {
			if (mVO.getCategoryId() != 5) {
				recommendList.add(mVO);
			}
		}

		return recommendList;
	}

	public void addMenuItem() {
		Random random = new Random();

		// 리스트를 섞고 처음 3개 선택
		Collections.shuffle(recommendList, random);
		List<MenuVO> randomMenu = recommendList.subList(0, 3);

		for (int i = 0; i < 3; i++) {
			MenuVO menu = randomMenu.get(i);
			JButton btnMenu = new JButton("이미지");
			JLabel jlblMenu = new JLabel(menu.getMenuName() + " / " + menu.getPrice());
			JButton btnOrder = new JButton("주문하기");
			btnOrder.addActionListener(e -> orderBtnClicked(menu));
			btnOrder.setContentAreaFilled(false); // 버튼의 배경을 투명하게 만듦
			btnOrder.setBorderPainted(false);
			JPanel itemPanel = new JPanel(new GridLayout(1, 3));

			itemPanel.add(btnMenu);
			itemPanel.add(jlblMenu);
			itemPanel.add(btnOrder);

			rmv.getMenuPanel().add(itemPanel);
		}

	}//addMenuItem

	public void orderBtnClicked(MenuVO randomMenu) {
		DefaultTableModel model = umv.getDtm();
		boolean found = false;
		int totalQuantity = 0, totalPrice = 0;

		// 이미 장바구니에 추가된 메뉴면 수량 및 금액 증가
		for (int i = 0; i < model.getRowCount(); i++) {
			String itemName = (String) model.getValueAt(i, 0);
			int quantity = (int) model.getValueAt(i, 1);
			int price = (int) model.getValueAt(i, 2);

			if (itemName.equals(randomMenu.getMenuName())) {
				model.setValueAt(quantity + 1, i, 1);
				model.setValueAt(price + randomMenu.getPrice(), i, 2);
				found = true;
			}
			totalQuantity += (int) model.getValueAt(i, 1);
			totalPrice += (int) model.getValueAt(i, 2);
		}

		// 아니면 새로 장바구니에 추가
		if (!found) {
			model.addRow(new Object[] { randomMenu.getMenuName(), 1, randomMenu.getPrice(), randomMenu.getMenuId() });
			totalQuantity++;
			totalPrice += randomMenu.getPrice();
		}

		// 총수량 및 총금액 업데이트
		umv.getJtfTotalQuantity().setText(String.valueOf(totalQuantity));
		umv.getJtfTotalPrice().setText(String.valueOf(totalPrice));
	}//orderBtnClicked
}
