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
import kr.co.kiosk.userView.BurgerMenuView;
import kr.co.kiosk.userView.UserMainView;
import kr.co.kiosk.vo.MenuVO;

public class BurgerMenuEvt implements ActionListener {

	private BurgerMenuView bmv;
	private UserMainView umv;
	private List<MenuVO> burgerList; //버거 메뉴를 담는 VO 리스트

	public BurgerMenuEvt(BurgerMenuView bmv, UserMainView umv) {
		this.bmv = bmv;
		this.umv = umv;
		this.burgerList = getBurgerMenuFromDB();
	}// BurgerMenuEvt
	
	//데이터베이스에서 버거 메뉴를 가져오는 method
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

	// 데이터를 가져와서 메뉴판을 채우는 method
	public void loadMenu() {
		bmv.getMenuPanel().removeAll(); // 기존 메뉴 삭제

		int start = bmv.getCurrentPage() * BurgerMenuView.getMaxPerPage(); 
		int end = Math.min(start + BurgerMenuView.getMaxPerPage(), burgerList.size());

		for(int i = start; i < end; i++) {
			addMenuItem(burgerList.get(i));
		}
		
		//부족한 공간 빈패널로 채우기
		while(bmv.getMenuPanel().getComponentCount() < BurgerMenuView.getMaxPerPage()) {
			bmv.getMenuPanel().add(new JPanel());
		}

		// 버튼 활성화/비활성화 조정
		bmv.getBtnPrev().setEnabled(bmv.getCurrentPage() > 0);
		bmv.getBtnNext().setEnabled((bmv.getCurrentPage() + 1) * BurgerMenuView.getMaxPerPage() < burgerList.size());

		// 메뉴 채우기
		bmv.getMenuPanel().revalidate();
		bmv.getMenuPanel().repaint();
	}// loadMenu
	
	//메뉴판용 버튼 개별 생성 method
	public void addMenuItem(MenuVO burgerList) {
		JButton btn = new JButton("이미지");
		btn.addActionListener(e -> menuBtnClicked(burgerList));
		JLabel lbl = new JLabel(("<html>" + burgerList.getMenuName() + "<br>" + burgerList.getPrice() + "<html>"), SwingConstants.CENTER);
        JPanel itemPanel = new JPanel(new GridLayout(1, 1));
        
        itemPanel.add(btn);
        itemPanel.add(lbl);
        
        bmv.getMenuPanel().add(itemPanel);
	}//addMenuItem
	
	//메뉴 버튼 클릭 이벤트
	public void menuBtnClicked(MenuVO burgerList) {
	    DefaultTableModel model = umv.getDtm();
	    boolean found = false;
	    int totalQuantity = 0, totalPrice = 0;

	    //이미 장바구니에 추가된 메뉴면 수량 및 금액 증가
	    for (int i = 0; i < model.getRowCount(); i++) {
            String itemName = (String) model.getValueAt(i, 0);
            int quantity = (int) model.getValueAt(i, 1);
            int price = (int) model.getValueAt(i, 2);

            if (itemName.equals(burgerList.getMenuName())) {
                model.setValueAt(quantity + 1, i, 1);
                model.setValueAt(price + burgerList.getPrice(), i, 2);
                found = true;
            }
            totalQuantity += (int) model.getValueAt(i, 1);
            totalPrice += (int) model.getValueAt(i, 2);
        }


	    //아니면 새로 장바구니에 추가
	    if (!found) {
            model.addRow(new Object[]{burgerList.getMenuName(), 1, burgerList.getPrice(), burgerList.getMenuId()});
            totalQuantity++;
            totalPrice += burgerList.getPrice();
        }

	    //총수량 및 총금액 업데이트
	    umv.getJtfTotalQuantity().setText(String.valueOf(totalQuantity));
	    umv.getJtfTotalPrice().setText(String.valueOf(totalPrice));
	}//menuBtnClicked

	
	@Override
	public void actionPerformed(ActionEvent e) {

		//다음 버튼 클릭
		if (e.getSource() == bmv.getBtnNext() && (bmv.getCurrentPage() + 1) * BurgerMenuView.getMaxPerPage() < burgerList.size()) {
				bmv.setCurrentPage(bmv.getCurrentPage() + 1);
				loadMenu();
		}
		//이전 버튼 클릭
		if (e.getSource() == bmv.getBtnPrev() && bmv.getCurrentPage() > 0) {
				bmv.setCurrentPage(bmv.getCurrentPage() - 1);
				loadMenu();
		}

	}// actionPerformed

}
