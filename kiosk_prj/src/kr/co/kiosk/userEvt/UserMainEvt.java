package kr.co.kiosk.userEvt;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import kr.co.kiosk.service.MenuOrderService;
import kr.co.kiosk.service.MenuService;
import kr.co.kiosk.service.TotalOrderService;
import kr.co.kiosk.userView.AddIngredientsView;
import kr.co.kiosk.userView.MainPageView;
import kr.co.kiosk.userView.UserMainView;
import kr.co.kiosk.vo.MenuOrderVO;
import kr.co.kiosk.vo.MenuVO;
import kr.co.kiosk.vo.TotalOrderVO;

public class UserMainEvt extends WindowAdapter implements ActionListener {

	private UserMainView umv;
	private String orderType;
	private boolean isMember; // 회원한지 판단하는 변수
	private int memberId; // 회원 아이디

	public UserMainEvt(UserMainView umv) {
		this.umv = umv;
		isMember = false;
	}

	// 패널 바꿀 때 마다 버튼 색 초기화 하는 method
	public void resetButtonColors() {
		umv.getBtnRecommendView().setBackground(null);
		umv.getBtnBurgerView().setBackground(null);
		umv.getBtnSideView().setBackground(null);
		umv.getBtnDrinkView().setBackground(null);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == umv.getBtnHome()) {
			umv.getFrame().dispose();
			new MainPageView();
		}
		
		//우선 스탬프 버튼으로 jdialog 활성화
		if(e.getSource() == umv.getBtnStamp()) {
			new AddIngredientsView(umv);
		}

		// 이벤트가 발생하면 Card에 보여줄 패널을 설정하여 보여준다
		if (e.getSource() == umv.getBtnRecommendView()) {
			resetButtonColors();
			umv.getCl().show(umv.getJpnlMain(), "rmv");
			umv.getBtnRecommendView().setBackground(Color.decode("#2196F3"));
		}
		if (e.getSource() == umv.getBtnBurgerView()) {
			resetButtonColors();
			umv.getCl().show(umv.getJpnlMain(), "bmv");
			umv.getBtnBurgerView().setBackground(Color.decode("#2196F3"));

		}
		if (e.getSource() == umv.getBtnSideView()) {
			resetButtonColors();
			umv.getCl().show(umv.getJpnlMain(), "smv");
			umv.getBtnSideView().setBackground(Color.decode("#2196F3"));
		}
		if (e.getSource() == umv.getBtnDrinkView()) {
			resetButtonColors();
			umv.getCl().show(umv.getJpnlMain(), "dmv");
			umv.getBtnDrinkView().setBackground(Color.decode("#2196F3"));
		}
		if (e.getSource() == umv.getBtnCancelAll()) {
			umv.getJtfTotalPrice().setText("");
			umv.getJtfTotalQuantity().setText("");
			umv.getDtm().setNumRows(0);
		}
		// 수량 빼기 버튼
		if (e.getSource() == umv.getBtnMinus()) {
			int selectedRow = -1;
			selectedRow = umv.getTable().getSelectedRow();

			if (selectedRow == -1) {
				return;
			}
			int quantity = (int) umv.getDtm().getValueAt(selectedRow, 1);
			int price = (int) umv.getDtm().getValueAt(selectedRow, 2);

			if (quantity == 1) {
				return;
			}
			umv.getDtm().setValueAt(quantity - 1, selectedRow, 1);
			umv.getDtm().setValueAt(price - (price / quantity), selectedRow, 2);

			// 수량 및 총금액 라벨 업데이트
			umv.getJtfTotalQuantity()
					.setText(String.valueOf(Integer.parseInt(umv.getJtfTotalQuantity().getText()) - 1));
			umv.getJtfTotalPrice()
					.setText(String.valueOf(Integer.parseInt(umv.getJtfTotalPrice().getText()) - price / quantity));

		}
		// 수량 추가 버튼
		if (e.getSource() == umv.getBtnPlus()) {
			int selectedRow = -1;
			selectedRow = umv.getTable().getSelectedRow();

			if (selectedRow == -1) {
				return;
			}
			int quantity = (int) umv.getDtm().getValueAt(selectedRow, 1);
			int price = (int) umv.getDtm().getValueAt(selectedRow, 2);

			umv.getDtm().setValueAt(quantity + 1, selectedRow, 1);
			umv.getDtm().setValueAt(price + (price / quantity), selectedRow, 2);

			// 수량 및 총금액 라벨 업데이트
			umv.getJtfTotalQuantity()
					.setText(String.valueOf(Integer.parseInt(umv.getJtfTotalQuantity().getText()) + 1));
			umv.getJtfTotalPrice()
					.setText(String.valueOf(Integer.parseInt(umv.getJtfTotalPrice().getText()) + price / quantity));

		}
		// 상품 취소 버튼
		if (e.getSource() == umv.getBtnCancel()) {
			int selectedRow = -1;
			selectedRow = umv.getTable().getSelectedRow();

			if (selectedRow == -1) {
				return;
			}
			int quantity = (int) umv.getDtm().getValueAt(selectedRow, 1);
			int price = (int) umv.getDtm().getValueAt(selectedRow, 2);

			umv.getDtm().removeRow(selectedRow);
			umv.getJtfTotalQuantity()
					.setText(String.valueOf(Integer.parseInt(umv.getJtfTotalQuantity().getText()) - quantity));
			umv.getJtfTotalPrice().setText(String.valueOf(Integer.parseInt(umv.getJtfTotalPrice().getText()) - price));

			// 만약 수량이 0이면 라벨에 0이 표시되지 않게 아예 초기화
			if (umv.getJtfTotalQuantity().getText().equals("0")) {
				umv.getJtfTotalPrice().setText("");
				umv.getJtfTotalQuantity().setText("");
			}
		}
		// 주문 버튼 누르면
		if (e.getSource() == umv.getBtnOrder()) {

			// 주문내역에 아무것도 없으면 return
			if (umv.getDtm().getRowCount() == 0) {
				return;
			}

			// 매장식사면 주문 타입 변경
			orderType = "포장";
			if (umv.isHall()) {
				orderType = "홀";
			}

			// 빈 주문관리 생성
			TotalOrderVO toVO;
			TotalOrderService tos = new TotalOrderService();

			// 회원이면
			if (isMember) {
				toVO = new TotalOrderVO(tos.acquireNextOrderId(), memberId, orderType, "주문중");
				tos.addTotalOrderMember(toVO);
			} else {
				toVO = new TotalOrderVO(tos.acquireNextOrderId(), orderType, "주문중");
				tos.addTotalOrderGuest(toVO);
			}

			// 메뉴별 주문 생성
			MenuService ms = new MenuService();
			MenuOrderService mos = new MenuOrderService();
			for (int i = 0; i < umv.getDtm().getRowCount(); i++) {
				MenuVO mVO = ms.searchMenu((int) umv.getDtm().getValueAt(i, 3));
				MenuOrderVO moVO = new MenuOrderVO(toVO.getOrderId(), mVO.getMenuId(), mVO.getCategoryId(),
						(int) umv.getDtm().getValueAt(i, 1), (int) umv.getDtm().getValueAt(i, 2));
				mos.addMenuOrder(moVO);
			}
		}
	}// actionPerformed
}
