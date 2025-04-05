//package kr.co.kiosk.userEvt;
//
//import java.awt.Color;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
//import java.util.List;
//
//import javax.swing.table.DefaultTableModel;
//
//import kr.co.kiosk.service.MenuOrderService;
//import kr.co.kiosk.service.MenuService;
//import kr.co.kiosk.service.TotalOrderService;
//import kr.co.kiosk.userView.AddIngredientsView;
//import kr.co.kiosk.userView.MainPageView;
//import kr.co.kiosk.userView.UserMainView;
//import kr.co.kiosk.vo.MenuOrderVO;
//import kr.co.kiosk.vo.MenuVO;
//import kr.co.kiosk.vo.TotalOrderVO;
//
//public class UserMainEvt extends WindowAdapter implements ActionListener {
//
//	private UserMainView umv;
//	private String orderType;
//	private DefaultTableModel dtm;
//	private boolean isMember; // 회원한지 판단하는 변수
//	private int memberId; // 회원 아이디
//
//	public UserMainEvt(UserMainView umv) {
//		this.umv = umv;
//		this.dtm = umv.getDtm();
//		isMember = false;
//	}
//
//	// 패널 바꿀 때 마다 버튼 색 초기화 하는 method
//	public void resetButtonColors() {
//		umv.getBtnRecommendView().setBackground(null);
//		umv.getBtnBurgerView().setBackground(null);
//		umv.getBtnSideView().setBackground(null);
//		umv.getBtnDrinkView().setBackground(null);
//
//	}
//
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		if (e.getSource() == umv.getBtnHome()) {
//			umv.getFrame().dispose();
//			new MainPageView();
//		}
//		
//		if(e.getSource() == umv.getBtnStamp()) {
//		}
//
//		// 이벤트가 발생하면 Card에 보여줄 패널을 설정하여 보여준다
//		if (e.getSource() == umv.getBtnRecommendView()) {
//			resetButtonColors();
//			umv.getCl().show(umv.getJpnlMain(), "rmv");
//			umv.getBtnRecommendView().setBackground(Color.decode("#2196F3"));
//		}
//		if (e.getSource() == umv.getBtnBurgerView()) {
//			resetButtonColors();
//			umv.getCl().show(umv.getJpnlMain(), "bmv");
//			umv.getBtnBurgerView().setBackground(Color.decode("#2196F3"));
//
//		}
//		if (e.getSource() == umv.getBtnSideView()) {
//			resetButtonColors();
//			umv.getCl().show(umv.getJpnlMain(), "smv");
//			umv.getBtnSideView().setBackground(Color.decode("#2196F3"));
//		}
//		if (e.getSource() == umv.getBtnDrinkView()) {
//			resetButtonColors();
//			umv.getCl().show(umv.getJpnlMain(), "dmv");
//			umv.getBtnDrinkView().setBackground(Color.decode("#2196F3"));
//		}
//		if (e.getSource() == umv.getBtnCancelAll()) {
//			umv.getJtfTotalPrice().setText("");
//			umv.getJtfTotalQuantity().setText("");
//			dtm.setNumRows(0);
//		}
//		// 수량 빼기 버튼
//		if (e.getSource() == umv.getBtnMinus()) {
//			int selectedRow = -1;
//			selectedRow = umv.getTable().getSelectedRow();
//
//			if (selectedRow == -1) {
//				return;
//			}
//			int quantity = (int) dtm.getValueAt(selectedRow, 1);
//			int price = (int) dtm.getValueAt(selectedRow, 2);
//
//			if (quantity == 1) {
//				return;
//			}
//			dtm.setValueAt(quantity - 1, selectedRow, 1);
//			dtm.setValueAt(price - (price / quantity), selectedRow, 2);
//
//			// 수량 및 총금액 라벨 업데이트
//			umv.getJtfTotalQuantity()
//					.setText(String.valueOf(Integer.parseInt(umv.getJtfTotalQuantity().getText()) - 1));
//			umv.getJtfTotalPrice()
//					.setText(String.valueOf(Integer.parseInt(umv.getJtfTotalPrice().getText()) - price / quantity));
//
//		}
//		// 수량 추가 버튼
//		if (e.getSource() == umv.getBtnPlus()) {
//			int selectedRow = -1;
//			selectedRow = umv.getTable().getSelectedRow();
//
//			if (selectedRow == -1) {
//				return;
//			}
//			int quantity = (int) dtm.getValueAt(selectedRow, 1);
//			int price = (int) dtm.getValueAt(selectedRow, 2);
//
//			dtm.setValueAt(quantity + 1, selectedRow, 1);
//			dtm.setValueAt(price + (price / quantity), selectedRow, 2);
//
//			// 수량 및 총금액 라벨 업데이트
//			umv.getJtfTotalQuantity()
//					.setText(String.valueOf(Integer.parseInt(umv.getJtfTotalQuantity().getText()) + 1));
//			umv.getJtfTotalPrice()
//					.setText(String.valueOf(Integer.parseInt(umv.getJtfTotalPrice().getText()) + price / quantity));
//
//		}
//		// 상품 취소 버튼
//		if (e.getSource() == umv.getBtnCancel()) {
//			int selectedRow = -1;
//			selectedRow = umv.getTable().getSelectedRow();
//
//			if (selectedRow == -1) {
//				return;
//			}
//			int quantity = (int) dtm.getValueAt(selectedRow, 1);
//			int price = (int) dtm.getValueAt(selectedRow, 2);
//
//			dtm.removeRow(selectedRow);
//			umv.getJtfTotalQuantity()
//					.setText(String.valueOf(Integer.parseInt(umv.getJtfTotalQuantity().getText()) - quantity));
//			umv.getJtfTotalPrice().setText(String.valueOf(Integer.parseInt(umv.getJtfTotalPrice().getText()) - price));
//
//			// 만약 수량이 0이면 라벨에 0이 표시되지 않게 아예 초기화
//			if (umv.getJtfTotalQuantity().getText().equals("0")) {
//				umv.getJtfTotalPrice().setText("");
//				umv.getJtfTotalQuantity().setText("");
//			}
//		}
//		// 주문 버튼 누르면
//		if (e.getSource() == umv.getBtnOrder()) {
//
//			// 주문내역에 아무것도 없으면 return
//			if (dtm.getRowCount() == 0) {
//				return;
//			}
//
//			// 매장식사면 주문 타입 변경
//			orderType = "포장";
//			if (umv.isHall()) {
//				orderType = "홀";
//			}
//
//			// 빈 주문관리 생성
//			TotalOrderVO toVO;
//			TotalOrderService tos = new TotalOrderService();
//
//			// 회원이면
//			if (isMember) {
//				toVO = new TotalOrderVO(tos.acquireNextOrderId(), memberId, orderType, "주문중");
//				tos.addTotalOrderMember(toVO);
//			} else {
//				toVO = new TotalOrderVO(tos.acquireNextOrderId(), orderType, "주문중");
//				tos.addTotalOrderGuest(toVO);
//			}
//
//			// 메뉴별 주문 생성
//			MenuService ms = new MenuService();
//			MenuOrderService mos = new MenuOrderService();
//			for (int i = 0; i < dtm.getRowCount(); i++) {
//				MenuVO mVO = ms.searchMenu((int) dtm.getValueAt(i, 3));
//				MenuOrderVO moVO = new MenuOrderVO(toVO.getOrderId(), mVO.getMenuId(), mVO.getCategoryId(),
//						(int) dtm.getValueAt(i, 1), (int) dtm.getValueAt(i, 2));
//				mos.addMenuOrder(moVO);
//			}
//		}
//	}// actionPerformed
//}
package kr.co.kiosk.userEvt;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

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

	public UserMainEvt(UserMainView umv) {
		this.umv = umv;
		this.dtm = umv.getDtm();
		isMember = false;
	}

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
		else if (src == umv.getBtnMinus())
			itemMinus();
		else if (src == umv.getBtnPlus())
			itemPlus();
		else if (src == umv.getBtnCancel())
			itemCancel();
		else if (src == umv.getBtnOrder())
			itemOrder();
	}//actionPerformed

	public void resetButtonColors() {
		umv.getBtnRecommendView().setBackground(null);
		umv.getBtnBurgerView().setBackground(null);
		umv.getBtnSideView().setBackground(null);
		umv.getBtnDrinkView().setBackground(null);
	}//resetButtonColors

	private void switchPanel(String panelName, javax.swing.JButton btn) {
		resetButtonColors();
		umv.getCl().show(umv.getJpnlMain(), panelName);
		btn.setBackground(Color.decode("#2196F3"));
	}//switchPanel

	public void cancelAll() {
		dtm.setNumRows(0);
		umv.getJtfTotalPrice().setText("");
		umv.getJtfTotalQuantity().setText("");
	}//cancelAll

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
	}//itemMinus

	public void itemPlus() {
		int row = umv.getTable().getSelectedRow();
		if (row == -1)
			return;

		int quantity = (int) dtm.getValueAt(row, 1);
		int price = (int) dtm.getValueAt(row, 2);

		dtm.setValueAt(quantity + 1, row, 1);
		dtm.setValueAt(price + (price / quantity), row, 2);
		updateSummary(1, price / quantity);
	}//itemPlus

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
	}//itemCancel

	public void updateSummary(int quantityDiff, int priceDiff) {
		int totalQty = Integer.parseInt(umv.getJtfTotalQuantity().getText()) + quantityDiff;
		int totalPrice = Integer.parseInt(umv.getJtfTotalPrice().getText()) + priceDiff;

		umv.getJtfTotalQuantity().setText(String.valueOf(totalQty));
		umv.getJtfTotalPrice().setText(String.valueOf(totalPrice));
	}//updateSummary

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
