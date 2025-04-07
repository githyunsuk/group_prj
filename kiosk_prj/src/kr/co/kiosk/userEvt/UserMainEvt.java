
package kr.co.kiosk.userEvt;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import kr.co.kiosk.service.MenuOrderService;
import kr.co.kiosk.service.MenuService;
import kr.co.kiosk.service.TotalOrderService;
import kr.co.kiosk.userView.MainPageView;
import kr.co.kiosk.userView.UserMainView;
import kr.co.kiosk.vo.MenuOrderVO;
import kr.co.kiosk.vo.MenuVO;
import kr.co.kiosk.vo.TotalOrderVO;

public class UserMainEvt extends WindowAdapter implements ActionListener {

	private UserMainView umv;
	private String orderType;
	private DefaultTableModel dtm;
	private boolean isMember;
	private int memberId;

	private List<String> easterEgg;

	public UserMainEvt(UserMainView umv) {
		this.umv = umv;
		this.dtm = umv.getDtm();
		isMember = false;
		easterEgg = new ArrayList<String>();
	} //UserMainEvt

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();

		if (src == umv.getBtnHome()) {
			umv.getFrame().dispose();
			new MainPageView();
		} else if (src == umv.getBtnStamp()) {

		} else if (src == umv.getBtnRecommendView()) {
			switchPanel("rmv", umv.getBtnRecommendView());
		} else if (src == umv.getBtnBurgerView()) {
			switchPanel("bmv", umv.getBtnBurgerView());
		} else if (src == umv.getBtnSideView()) {
			switchPanel("smv", umv.getBtnSideView());
		} else if (src == umv.getBtnDrinkView()) {
			switchPanel("dmv", umv.getBtnDrinkView());
		} else if (src == umv.getBtnCancelAll())
			cancelAll();
		else if (src == umv.getBtnMinus()) {
			addClick("MINUS");
			itemMinus();
			checkEasterEgg();
		} else if (src == umv.getBtnPlus()) {
			addClick("PLUS");
			itemPlus();
			checkEasterEgg();
		} else if (src == umv.getBtnCancel()) {
			addClick("CANCEL");
			itemCancel();
			checkEasterEgg();
		} else if (src == umv.getBtnOrder())
			itemOrder();
	}// actionPerformed

	public void addClick(String str) {
		easterEgg.add(str);
		if (easterEgg.size() > 5) {
			easterEgg.remove(0);
		}
	}// addClick

	public void checkEasterEgg() {
		List<String> easterEggCode = List.of("PLUS", "PLUS", "MINUS", "MINUS", "CANCEL");
		if (easterEgg.equals(easterEggCode)) {
			JOptionPane.showMessageDialog(null, "축하합니다!!!", "이스터에그", JOptionPane.INFORMATION_MESSAGE);
			easterEgg.clear();
		}
	}// checkEasterEgg

	public void resetButtonColors() {
		umv.getBtnRecommendView().setBackground(null);
		umv.getBtnBurgerView().setBackground(null);
		umv.getBtnSideView().setBackground(null);
		umv.getBtnDrinkView().setBackground(null);
	}// resetButtonColors

	private void switchPanel(String panelName, javax.swing.JButton btn) {
		resetButtonColors();
		umv.getCl().show(umv.getJpnlMain(), panelName);
		btn.setBackground(Color.decode("#2196F3"));
	}// switchPanel

	public void cancelAll() {
		dtm.setNumRows(0);
		umv.getJtfTotalPrice().setText("");
		umv.getJtfTotalQuantity().setText("");
	}// cancelAll

	public void itemMinus() {
		int row = umv.getTable().getSelectedRow();
		if (row == -1)
			return;

		int quantity = (int) dtm.getValueAt(row, 1);
		int price = (int) dtm.getValueAt(row, 2);
		if (quantity == 1)
			return;

		dtm.setValueAt(quantity - 1, row, 1);
		dtm.setValueAt(price - (price / quantity), row, 2);
		updateSummary(-1, -price / quantity);
	}// itemMinus

	public void itemPlus() {
		int row = umv.getTable().getSelectedRow();
		if (row == -1)
			return;

		int quantity = (int) dtm.getValueAt(row, 1);
		int price = (int) dtm.getValueAt(row, 2);

		dtm.setValueAt(quantity + 1, row, 1);
		dtm.setValueAt(price + (price / quantity), row, 2);
		updateSummary(1, price / quantity);
	}// itemPlus

	public void itemCancel() {
		int row = umv.getTable().getSelectedRow();
		if (row == -1)
			return;

		int quantity = (int) dtm.getValueAt(row, 1);
		int price = (int) dtm.getValueAt(row, 2);

		dtm.removeRow(row);
		updateSummary(-quantity, -price);

		if (umv.getJtfTotalQuantity().getText().equals("0")) {
			umv.getJtfTotalPrice().setText("");
			umv.getJtfTotalQuantity().setText("");
		}
	}// itemCancel

	public void updateSummary(int quantityDiff, int priceDiff) {
		int totalQty = Integer.parseInt(umv.getJtfTotalQuantity().getText()) + quantityDiff;
		int totalPrice = Integer.parseInt(umv.getJtfTotalPrice().getText()) + priceDiff;

		umv.getJtfTotalQuantity().setText(String.valueOf(totalQty));
		umv.getJtfTotalPrice().setText(String.valueOf(totalPrice));
	}// updateSummary

	public void itemOrder() {
		if (dtm.getRowCount() == 0)
			return;

		orderType = umv.isHall() ? "홀" : "포장";
		TotalOrderService tos = new TotalOrderService();

		TotalOrderVO toVO = null;
		if (isMember) {
			toVO = new TotalOrderVO(tos.acquireNextOrderId(), memberId, orderType, "주문중");
			tos.addTotalOrderMember(toVO);
		} else {
			toVO = new TotalOrderVO(tos.acquireNextOrderId(), orderType, "주문중");
			tos.addTotalOrderGuest(toVO);
		}

		MenuService ms = new MenuService();
		MenuOrderService mos = new MenuOrderService();
		MenuVO mVO = null;
		MenuOrderVO moVO = null;

		for (int i = 0; i < dtm.getRowCount(); i++) {
			mVO = ms.searchMenu((int) dtm.getValueAt(i, 3));
			moVO = mos.searchOneMenuOrder(toVO.getOrderId(), mVO.getMenuId());
			if (moVO != null) { // 이미 해당 제품이 MenuOrder에 올라가있으면
				moVO.setQuantity(moVO.getQuantity() + (int) dtm.getValueAt(i, 1));
				moVO.setTotalPrice(moVO.getTotalPrice() + (int) dtm.getValueAt(i, 2));
				mos.modifyMenuOrder(moVO);
			} else { // 아니면 새로 생성
				moVO = new MenuOrderVO(toVO.getOrderId(), mVO.getMenuId(), mVO.getCategoryId(),
						(int) dtm.getValueAt(i, 1), (int) dtm.getValueAt(i, 2));
				mos.addMenuOrder(moVO);
			}

			// 세트메뉴면(사이드 및 음료 변경 위해 따로 관리)
			if (mVO.getCategoryId() == 1 || mVO.getCategoryId() == 2) {
				String[] strArr = String.valueOf(dtm.getValueAt(i, 0)).split("/");
				for (int j = 1; j < strArr.length; j++) {
					strArr[j] = strArr[j].substring(strArr[j].indexOf(')') + 1).trim();
					mVO = ms.searchMenuWithName(strArr[j]);
					moVO = mos.searchOneMenuOrder(toVO.getOrderId(), mVO.getMenuId());
					if (moVO != null) { // 이미 해당 제품이 MenuOrder에 올라가있으면
						moVO.setQuantity(moVO.getQuantity() + 1);
						mos.modifyMenuOrder(moVO);
					} else { // 아니면 새로 생성
						moVO = new MenuOrderVO(toVO.getOrderId(), mVO.getMenuId(), mVO.getCategoryId(), 1, 0);
						mos.addMenuOrder(moVO);
					}
				}
			}
		}
	}// itemOrder
}
