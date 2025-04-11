package kr.co.kiosk.userEvt;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
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

	private UserMainView umv;

	private JButton btnPrev, btnNext;
	private JPanel menuPanel;
	private DefaultTableModel dtm;
	private List<MenuVO> sideList; // 음료 메뉴를 담는 VO 리스트
	private final Map<Integer, Integer> stockMap = new HashMap<>();

	private int maxPage = 9; // 한 페이지당 최대 메뉴 개수
	private int currentPage = 0; // 현재 페이지 번호

	public SideMenuEvt(SideMenuView smv, UserMainView umv) {
		this.umv = umv;
		this.btnPrev = smv.getBtnPrev();
		this.btnNext = smv.getBtnNext();
		this.menuPanel = smv.getMenuPanel();
		this.dtm = umv.getDtm();
		this.sideList = getSideMenu();
		getStockInfo();
	}

	// 데이터베이스에서 사이드 메뉴를 가져오는 method
	private List<MenuVO> getSideMenu() {
		List<MenuVO> sideList = new ArrayList<MenuVO>();

		for (MenuVO mVO :  umv.getAllMenuList()) {
			if (mVO.getCategoryId() == 3) {
				sideList.add(mVO);
			}
		}
		return sideList;
	}// getSideMenuFromDB
	
	private void getStockInfo() {
		MenuService ms = new MenuService();
		for (MenuVO menu : sideList) {
			int availableCnt = ms.getAvailableCount(menu.getMenuId());
			stockMap.put(menu.getMenuId(), availableCnt);
		}
	}

	// 데이터를 가져와서 메뉴판을 채우는 method
	public void loadMenu() {
		menuPanel.removeAll(); // 기존 메뉴 삭제

		int start = currentPage * maxPage;
		int end = Math.min(start + maxPage, sideList.size());

		for (int i = start; i < end; i++) {
			addMenuItem(sideList.get(i));
		}

		// 부족한 공간 빈패널로 채우기
		while (menuPanel.getComponentCount() < maxPage) {
			menuPanel.add(new JPanel());
		}

		// 버튼 활성화/비활성화 조정
		btnPrev.setEnabled(currentPage > 0);
		btnNext.setEnabled((currentPage + 1) * maxPage < sideList.size());

		// 메뉴 채우기
		menuPanel.revalidate();
		menuPanel.repaint();
	}// loadMenu

	// 메뉴판용 버튼 개별 생성 method
	public void addMenuItem(MenuVO sideList) {
		ImageIcon icon = new ImageIcon(getClass().getResource("/kr/co/kiosk/assets/noChange.jpg"));
		if (sideList.getImgName() != null) {
				icon = sideList.getImage();
		}
		Image scaledImg = icon.getImage().getScaledInstance(125, 110, Image.SCALE_SMOOTH);
		ImageIcon img = new ImageIcon(scaledImg);
		JButton btn = new JButton(img);
		btn.addActionListener(e -> menuBtnClicked(sideList));
		
		/**
		 * 재고소진에 따른 주문 가능 횟수 표기			 
		 * */
		int availableCnt = stockMap.get(sideList.getMenuId());
		String alertText = "";
		if (availableCnt <= 0) {
		    alertText = "<font color='red'><b>Sold Out!</b></font>";
		    btn.setEnabled(false);
		} 
		
		JLabel lbl = new JLabel(("<html>" + sideList.getMenuName() + "<br>" + sideList.getPrice() + "<br>" + alertText + "<html>"), SwingConstants.CENTER);
		JPanel itemPanel = new JPanel(new GridLayout(1, 1));

		itemPanel.add(btn);
		itemPanel.add(lbl);

		menuPanel.add(itemPanel);
	}// addMenuItem

	// 메뉴 버튼 클릭 이벤트
	public void menuBtnClicked(MenuVO siderList) {
		boolean found = false;
		int totalQuantity = 0, totalPrice = 0;

		// 이미 장바구니에 추가된 메뉴면 수량 및 금액 증가
		for (int i = 0; i < dtm.getRowCount(); i++) {
			String itemName = (String) dtm.getValueAt(i, 0);
			int quantity = (int) dtm.getValueAt(i, 1);
			int price = (int) dtm.getValueAt(i, 2);

			if (itemName.equals(siderList.getMenuName())) {
				dtm.setValueAt(quantity + 1, i, 1);
				dtm.setValueAt(price + siderList.getPrice(), i, 2);
				found = true;
			}
			totalQuantity += (int) dtm.getValueAt(i, 1);
			totalPrice += (int) dtm.getValueAt(i, 2);
		}

		// 아니면 새로 장바구니에 추가
		if (!found) {
			dtm.addRow(new Object[] { siderList.getMenuName(), 1, siderList.getPrice(), siderList.getMenuId() });
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
		if (e.getSource() == btnNext
				&& (currentPage + 1) * maxPage < sideList.size()) {
			currentPage += 1;
			loadMenu();
		}
		// 이전 버튼 클릭
		if (e.getSource() == btnPrev && currentPage > 0) {
			currentPage -= 1;
			loadMenu();
		}

	}//actionPerformed

}
