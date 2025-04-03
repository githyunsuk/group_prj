package kr.co.kiosk.userEvt;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import kr.co.kiosk.service.MenuService;
import kr.co.kiosk.userView.SideMenuView;
import kr.co.kiosk.userView.UserMainView;
import kr.co.kiosk.vo.MenuVO;

public class SideMenuEvt implements ActionListener {

	private SideMenuView smv;
	private UserMainView umv;
	private List<MenuVO> sideList; // 사이드 메뉴를 담는 VO 리스트

	public SideMenuEvt(SideMenuView smv, UserMainView umv) {
		this.smv = smv;
		this.umv = umv;
		this.sideList = getSideMenuFromDB();
	}

	// 데이터베이스에서 사이드 메뉴를 가져오는 method
	private List<MenuVO> getSideMenuFromDB() {
		List<MenuVO> sideList = new ArrayList<MenuVO>();
		List<MenuVO> allMenuList = new ArrayList<MenuVO>();

		MenuService ms = new MenuService();
		allMenuList = ms.searchAllMenu();
		for (MenuVO mVO : allMenuList) {
			if (mVO.getCategoryId() == 3) {
				sideList.add(mVO);
			}
		}
		return sideList;
	}// getSideMenuFromDB

	// 데이터를 가져와서 메뉴판을 채우는 method
	public void loadMenu() {
		smv.getMenuPanel().removeAll(); // 기존 메뉴 삭제

		int start = smv.getCurrentPage() * SideMenuView.getMaxPerPage();
		int end = Math.min(start + SideMenuView.getMaxPerPage(), sideList.size());

		for (int i = start; i < end; i++) {
			addMenuItem(sideList.get(i));
		}

		// 부족한 공간 빈패널로 채우기
		while (smv.getMenuPanel().getComponentCount() < SideMenuView.getMaxPerPage()) {
			smv.getMenuPanel().add(new JPanel());
		}

		// 버튼 활성화/비활성화 조정
		smv.getBtnPrev().setEnabled(smv.getCurrentPage() > 0);
		smv.getBtnNext().setEnabled((smv.getCurrentPage() + 1) * SideMenuView.getMaxPerPage() < sideList.size());

		// 메뉴 채우기
		smv.getMenuPanel().revalidate();
		smv.getMenuPanel().repaint();
	}// loadMenu

	// 메뉴판용 버튼 개별 생성 method
	public void addMenuItem(MenuVO sideList) {
		JButton btn = new JButton("이미지");
		btn.addActionListener(e -> menuBtnClicked(sideList));
		JLabel lbl = new JLabel(("<html>" + sideList.getMenuName() + "<br>" + sideList.getPrice() + "<html>"), SwingConstants.CENTER);
		JPanel itemPanel = new JPanel(new GridLayout(1, 1));

		itemPanel.add(btn);
		itemPanel.add(lbl);

		smv.getMenuPanel().add(itemPanel);
	}// addMenuItem

	// 메뉴 버튼 클릭 이벤트
	public void menuBtnClicked(MenuVO siderList) {
		DefaultTableModel model = umv.getDtm();
		boolean found = false;
		int totalQuantity = 0, totalPrice = 0;

		// 이미 장바구니에 추가된 메뉴면 수량 및 금액 증가
		for (int i = 0; i < model.getRowCount(); i++) {
			String itemName = (String) model.getValueAt(i, 0);
			int quantity = (int) model.getValueAt(i, 1);
			int price = (int) model.getValueAt(i, 2);

			if (itemName.equals(siderList.getMenuName())) {
				model.setValueAt(quantity + 1, i, 1);
				model.setValueAt(price + siderList.getPrice(), i, 2);
				found = true;
			}
			totalQuantity += (int) model.getValueAt(i, 1);
			totalPrice += (int) model.getValueAt(i, 2);
		}

		// 아니면 새로 장바구니에 추가
		if (!found) {
			model.addRow(new Object[] { siderList.getMenuName(), 1, siderList.getPrice(), siderList.getMenuId() });
			totalQuantity++;
			totalPrice += siderList.getPrice();
		}

		// 총수량 및 총금액 업데이트
		umv.getJtfTotalQuantity().setText(String.valueOf(totalQuantity));
		umv.getJtfTotalPrice().setText(String.valueOf(totalPrice));
	}// menuBtnClicked

	@Override
	public void actionPerformed(ActionEvent e) {
		// 다음 버튼 클릭
		if (e.getSource() == smv.getBtnNext()
				&& (smv.getCurrentPage() + 1) * SideMenuView.getMaxPerPage() < sideList.size()) {
			smv.setCurrentPage(smv.getCurrentPage() + 1);
			loadMenu();
		}
		// 이전 버튼 클릭
		if (e.getSource() == smv.getBtnPrev() && smv.getCurrentPage() > 0) {
			smv.setCurrentPage(smv.getCurrentPage() - 1);
			loadMenu();
		}

	}

}
