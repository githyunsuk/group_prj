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

			String menuName = menuItems.get(i).split(" / ")[0];
			int menuPrice = Integer.parseInt(menuItems.get(i).split(" / ")[1]);

			JButton btn = new JButton("이미지");
			JLabel lbl = new JLabel(item, SwingConstants.CENTER);
			JPanel itemPanel = new JPanel(new BorderLayout());

			itemPanel.add(btn, BorderLayout.CENTER);
			itemPanel.add(lbl, BorderLayout.SOUTH);

			// 각 메뉴 버튼을 클릭하면
			btn.addActionListener(e -> menuBtnClicked(menuName, menuPrice));

			bmv.getMenuPanel().add(itemPanel);
		}

		// 버튼 활성화/비활성화 조정
		bmv.getBtnPrev().setEnabled(bmv.getCurrentPage() > 0);
		bmv.getBtnNext().setEnabled((bmv.getCurrentPage() + 1) * BurgerMenuView.getMaxPerPage() < menuItems.size());

		// 메뉴 채우기
		bmv.getMenuPanel().revalidate();
		bmv.getMenuPanel().repaint();
	}// loadMenu
	
	//각 메뉴버튼을 클릭하면 발생하는 이벤트 method
	public void menuBtnClicked(String menuName, int menuPrice) {
	    DefaultTableModel model = umv.getDtm();
	    boolean found = false;
	    int totalQuantity = 0, totalPrice = 0;
	    int quantity, price;

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

	    if (!found) {
	        model.addRow(new Object[]{menuName, 1, menuPrice});
	        totalQuantity += 1;
	        totalPrice += menuPrice;
	    }

	    umv.getJtfTotalQuantity().setText(String.valueOf(totalQuantity));
	    umv.getJtfTotalPrice().setText(String.valueOf(totalPrice));
	}//menuBtnClicked

	// db 에서 메뉴판 이미지, 메뉴명, 가격 빼오기
	private List<String> getMenuFromDB() {
		List<String> menuItems = new ArrayList<String>();
		List<MenuVO> allMenuList = new ArrayList<>();
		MenuService ms = new MenuService();
		allMenuList = ms.searchAllMenu();
		for (MenuVO mv : allMenuList) {
			if (mv.getCategoryId() == 1 || mv.getCategoryId() == 2) {
				menuItems.add(mv.getMenuName() + " / " + mv.getPrice());
			}
		}

		return menuItems;
	}// getMenuFromDB

	@Override
	public void actionPerformed(ActionEvent e) {

		//다음 버튼 클릭
		if (e.getSource() == bmv.getBtnNext()) {
			if ((bmv.getCurrentPage() + 1) * BurgerMenuView.getMaxPerPage() < menuItems.size()) {
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
