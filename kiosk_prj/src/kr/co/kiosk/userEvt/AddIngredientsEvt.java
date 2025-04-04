package kr.co.kiosk.userEvt;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import kr.co.kiosk.service.MenuService;
import kr.co.kiosk.userView.AddIngredientsView;
import kr.co.kiosk.userView.UserMainView;
import kr.co.kiosk.vo.MenuVO;

public class AddIngredientsEvt {

	private AddIngredientsView aiv;
	private UserMainView umv;
	private List<MenuVO> ingredList;

	public AddIngredientsEvt(AddIngredientsView aiv, UserMainView umv) {
		this.aiv = aiv;
		this.umv = umv;
		this.ingredList = getIngredFromDB();
	}

	private List<MenuVO> getIngredFromDB() {
		List<MenuVO> ingredList = new ArrayList<MenuVO>();
		List<MenuVO> allMenuList = new ArrayList<MenuVO>();

		MenuService ms = new MenuService();
		allMenuList = ms.searchAllMenu();
		for (MenuVO mVO : allMenuList) {
			if (mVO.getCategoryId() == 5) {
				ingredList.add(mVO);
			}
		}
		return ingredList;
	}// getIngredFromDB

	public void addMenuItem() {

		JButton btn2 = new JButton("변경 안함");
		JLabel lbl2 = new JLabel(("<html>" + "변경안함" + "<br>" + "+0" + "<html>"), SwingConstants.CENTER);
		btn2.addActionListener(e -> aiv.dispose());
		JPanel itemPanel2 = new JPanel(new GridLayout(1, 1));

		itemPanel2.add(btn2);
		itemPanel2.add(lbl2);

		aiv.getMenuPanel().add(itemPanel2);

		for (MenuVO mv : ingredList) {
			JButton btn = new JButton("이미지");
			btn.addActionListener(e -> menuBtnClicked(mv));
			JLabel lbl = new JLabel(("<html>" + mv.getMenuName() + "<br>" + "+" + mv.getPrice() + "<html>"),
					SwingConstants.CENTER);
			JPanel itemPanel = new JPanel(new GridLayout(1, 1));

			itemPanel.add(btn);
			itemPanel.add(lbl);

			aiv.getMenuPanel().add(itemPanel);

		} // addMenuItem

	}// addMenuItem

	public void menuBtnClicked(MenuVO ingredList) {
		DefaultTableModel model = umv.getDtm();
	    boolean found = false;
	    int totalQuantity = 0, totalPrice = 0;
	    
	  //이미 장바구니에 추가된 메뉴면 수량 및 금액 증가
	    for (int i = 0; i < model.getRowCount(); i++) {
            String itemName = (String) model.getValueAt(i, 0);
            int quantity = (int) model.getValueAt(i, 1);
            int price = (int) model.getValueAt(i, 2);

            if (itemName.equals(ingredList.getMenuName())) {
                model.setValueAt(quantity + 1, i, 1);
                model.setValueAt(price + ingredList.getPrice(), i, 2);
                found = true;
            }
            totalQuantity += (int) model.getValueAt(i, 1);
            totalPrice += (int) model.getValueAt(i, 2);
	    }
	    
	  //아니면 새로 장바구니에 추가
	    if (!found) {
            model.addRow(new Object[]{"+"+ingredList.getMenuName(), 1, ingredList.getPrice(), ingredList.getMenuId()});
            totalQuantity++;
            totalPrice += ingredList.getPrice();
        }

	    //총수량 및 총금액 업데이트
	    umv.getJtfTotalQuantity().setText(String.valueOf(totalQuantity));
	    umv.getJtfTotalPrice().setText(String.valueOf(totalPrice));
	    
	    aiv.dispose();
	}

}
