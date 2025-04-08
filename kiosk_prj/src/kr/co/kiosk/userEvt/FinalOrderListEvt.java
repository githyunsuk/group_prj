package kr.co.kiosk.userEvt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.table.DefaultTableModel;

import kr.co.kiosk.service.MemberService;
import kr.co.kiosk.service.MenuOrderService;
import kr.co.kiosk.service.MenuService;
import kr.co.kiosk.service.TotalOrderService;
import kr.co.kiosk.userView.FinalOrderListView;
import kr.co.kiosk.userView.InputPhonenumberView;
import kr.co.kiosk.userView.UsePointView;
import kr.co.kiosk.userView.UserMainView;
import kr.co.kiosk.vo.MemberVO;
import kr.co.kiosk.vo.MenuOrderVO;
import kr.co.kiosk.vo.MenuVO;
import kr.co.kiosk.vo.TotalOrderVO;

public class FinalOrderListEvt extends WindowAdapter implements ActionListener {

	private FinalOrderListView folv;
	private UserMainView umv;
	private DefaultTableModel dtm;
	private DefaultTableModel dtmUmv;
	private int totalResult;
	private int totalPriceAfterDiscount;

	public FinalOrderListEvt(FinalOrderListView folv, UserMainView umv) {
		this.folv = folv;
		this.umv = umv;
		dtm = folv.getDtm();
		dtmUmv = umv.getDtm();

		showOderSummary();
		updatePrice();

	}// FinalOrderListEvt

	public void showOderSummary() {
		for (int i = 0; i < dtmUmv.getRowCount(); i++) {
			Object[] rowData = new Object[dtmUmv.getColumnCount()];
			for (int j = 0; j < dtmUmv.getColumnCount(); j++) {
				rowData[j] = dtmUmv.getValueAt(i, j);
			}
			dtm.addRow(rowData);
		}
	}// showOderSummary

	public void updatePrice() {
		totalResult = 0;
		totalPriceAfterDiscount = 0;
		for (int i = 0; i < dtm.getRowCount(); i++) {// 합계
			try {
				// dtm 에서 1열, 2열을 가져와 int형 변수에 담음
				int price = Integer.parseInt(dtm.getValueAt(i, 2).toString());
				totalResult += price;
			} catch (NumberFormatException nfe) {// 숫자가 아니라 다르거 오는거 예외처리
				nfe.printStackTrace();
			} catch (NullPointerException npe) {// 값이 안들어있을거를 대비해 예외처리
				npe.printStackTrace();
			}
		}
		folv.getJlblTotalResult().setText(String.valueOf(totalResult) + "원");
		totalPriceAfterDiscount = totalResult - umv.getUsingPoints();
		folv.getJlblTotalPriceResult().setText(String.valueOf(totalPriceAfterDiscount) + "원");
	}// updatePrice

	public void pressPayBtn() {

		String orderType = umv.isHall() ? "홀" : "포장";
		TotalOrderService tos = new TotalOrderService();

		TotalOrderVO toVO = null;
		if (umv.getMemberId() != -1) {
			toVO = new TotalOrderVO(umv.getMemberId(), orderType, "주문중");
			tos.addTotalOrderMember(toVO);
		} else {
			toVO = new TotalOrderVO(orderType, "주문중");
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
			}//end if
		}//end for
		
		toVO.setOrderStatus("조리중");
		toVO.setPrice(totalResult - umv.getUsingPoints());
		
		MemberService mems = new MemberService();
		MemberVO memVO = mems.searchMember(umv.getMemberId());
		memVO.setTotalAmount(memVO.getTotalAmount() + totalPriceAfterDiscount );
		memVO.setStamps(memVO.getStamps() - umv.getUsingStamps() + (totalResult / 10000)); //사용한 스탬프 빼고, 만원 당 스탬프 1개씩
		memVO.setPoints(memVO.getPoints() - umv.getUsingPoints() + ((int)(totalResult * 0.05))); //사용한 포인트 빼고, 일단 결제 금액의 5%로 포인트 적립
		
		tos.modifyTotalOrder(toVO);
		mems.modifyMember(memVO);
		
		//member 업데이트(누적금액, 스탬프, 포인트, 등급 등)
	}// pressPayBtn

	public void openPointView() {
		if(umv.getMemberId() != -1) { //이미 앞서 스탬프를 통해 번호 조회를 완료했다면
			MemberService ms = new MemberService();
			new UsePointView(umv, ms.searchMember(umv.getMemberId()));
		}else {
			new InputPhonenumberView(umv, "point");
		}
		
		// 할인 금액이 상품총액보다 크면
		if (umv.getUsingPoints() > totalResult) {
			umv.setUsingPoints(totalResult);
		}

		folv.getJlblDiscountResult().setText(String.valueOf(umv.getUsingPoints()) + "원");
		updatePrice();
	}//openPointView
	
	@Override
	public void windowClosing(WindowEvent e) {
		folv.dispose();
		umv.getFrame().setVisible(true);
	}//windowClosing

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == folv.getJbtnCancel()) {
			folv.dispose();
			umv.getFrame().setVisible(true);
		}
		if (e.getSource() == folv.getJbtnDiscount()) {
			openPointView();
		}
		if (e.getSource() == folv.getJbtnPay()) {
			pressPayBtn();
		}

	}//actionPerformed

}//class
