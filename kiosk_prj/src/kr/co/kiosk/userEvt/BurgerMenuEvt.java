package kr.co.kiosk.userEvt;

import java.awt.BorderLayout;
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
import kr.co.kiosk.userView.BurgerMenuView;
import kr.co.kiosk.userView.UserMainView;
import kr.co.kiosk.vo.MenuVO;

public class BurgerMenuEvt implements ActionListener {

	private BurgerMenuView bmv;
	private UserMainView umv;
	private List<MenuVO> burgerList;

	public BurgerMenuEvt(BurgerMenuView bmv, UserMainView umv) {
		this.bmv = bmv;
		this.umv = umv;
		burgerList = getBurgerMenuFromDB();
	}// BurgerMenuEvt

	// 데이터를 가져와서 메뉴판을 채우는 method
	public void loadMenu() {
		bmv.getMenuPanel().removeAll(); // 기존 메뉴 삭제

		int start = bmv.getCurrentPage() * BurgerMenuView.getMaxPerPage();
		int end = Math.min(start + BurgerMenuView.getMaxPerPage(), burgerList.size());

		for (int i = start; i < end; i++) {
			MenuVO burger = burgerList.get(i);

			String menuName = burger.getMenuName();
			int menuPrice = burger.getPrice();
			int menuId = burger.getMenuId();

			JButton btn = new JButton("이미지");
			JLabel lbl = new JLabel(menuName + " / " + menuPrice, SwingConstants.CENTER);
			JPanel itemPanel = new JPanel(new BorderLayout());

			itemPanel.add(btn, BorderLayout.CENTER);
			itemPanel.add(lbl, BorderLayout.SOUTH);

			// 각 메뉴 버튼을 클릭하면
			btn.addActionListener(e -> menuBtnClicked(menuName, menuPrice, menuId));

			bmv.getMenuPanel().add(itemPanel);
		}

		// 버튼 활성화/비활성화 조정
		bmv.getBtnPrev().setEnabled(bmv.getCurrentPage() > 0);
		bmv.getBtnNext().setEnabled((bmv.getCurrentPage() + 1) * BurgerMenuView.getMaxPerPage() < burgerList.size());

		// 메뉴 채우기
		bmv.getMenuPanel().revalidate();
		bmv.getMenuPanel().repaint();
	}// loadMenu
	
	//각 메뉴버튼을 클릭하면 발생하는 이벤트 method
	public void menuBtnClicked(String menuName, int menuPrice, int menuId) {
	    DefaultTableModel model = umv.getDtm();
	    boolean found = false;
	    int totalQuantity = 0, totalPrice = 0;
	    int quantity, price;

	    //이미 장바구니에 추가된 메뉴면 수량 및 금액 증가
	    for (int j = 0; j < model.getRowCount(); j++) {
	        if (model.getValueAt(j, 0).equals(menuName)) {
	            quantity = (int) model.getValueAt(j, 1) + 1;
	            price = (int) model.getValueAt(j, 2) + menuPrice;

	            model.setValueAt(quantity, j, 1);
	            model.setValueAt(price, j, 2);
	            found = true;
	        }
	        totalQuantity += (int) model.getValueAt(j, 1);
	        totalPrice += (int) model.getValueAt(j, 2);
	    }

	    //아니면 새로 장바구니에 추가
	    if (!found) {
	        model.addRow(new Object[]{menuName, 1, menuPrice, menuId});
	        totalQuantity += 1;
	        totalPrice += menuPrice;
	    }

	    //총수량 및 총금액 업데이트
	    umv.getJtfTotalQuantity().setText(String.valueOf(totalQuantity));
	    umv.getJtfTotalPrice().setText(String.valueOf(totalPrice));
	}//menuBtnClicked

	private List<MenuVO> getBurgerMenuFromDB(){
		List<MenuVO> burgerList = new ArrayList<MenuVO>();
		List<MenuVO> allMenuList = new ArrayList<MenuVO>();
		
		MenuService ms = new MenuService();
		allMenuList = ms.searchAllMenu();
		for(MenuVO mVO : allMenuList) {
			if (mVO.getCategoryId() == 1 || mVO.getCategoryId() == 2) {
				burgerList.add(mVO);
			}
		}
		return burgerList;
	}//getBurgerMenuFromDB
	
	@Override
	public void actionPerformed(ActionEvent e) {

		//다음 버튼 클릭
		if (e.getSource() == bmv.getBtnNext()) {
			if ((bmv.getCurrentPage() + 1) * BurgerMenuView.getMaxPerPage() < burgerList.size()) {
				bmv.setCurrentPage(bmv.getCurrentPage() + 1);
				loadMenu();
			}
		}

		//이전 버튼 클릭
		if (e.getSource() == bmv.getBtnPrev()) {
			if (bmv.getCurrentPage() > 0) {
				bmv.setCurrentPage(bmv.getCurrentPage() - 1);
				loadMenu();
			}
		}

	}// actionPerformed

}
