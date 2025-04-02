package kr.co.kiosk.userEvt;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import kr.co.kiosk.service.MenuOrderService;
import kr.co.kiosk.service.MenuService;
import kr.co.kiosk.service.TotalOrderService;
import kr.co.kiosk.userView.UserMainView;
import kr.co.kiosk.vo.MenuOrderVO;
import kr.co.kiosk.vo.MenuVO;
import kr.co.kiosk.vo.TotalOrderVO;

public class UserMainEvt extends WindowAdapter implements ActionListener {

	private UserMainView umv;
	private String orderStatus;

	public UserMainEvt(UserMainView umv) {
		this.umv = umv;
	}
	
	//패널 바꿀 때 마다 버튼 색 초기화 하는 method
	public void resetButtonColors() {
		umv.getBtnRecommendView().setBackground(null);
		umv.getBtnBurgerView().setBackground(null);
		umv.getBtnSideView().setBackground(null);
		umv.getBtnDrinkView().setBackground(null);

	}

	@Override
	public void windowClosing(WindowEvent e) {
		umv.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

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
		if(e.getSource() == umv.getBtnCancelAll()) {
			umv.getJtfTotalPrice().setText("");
			umv.getJtfTotalQuantity().setText("");
			umv.getDtm().setNumRows(0);
		}
		//주문 버튼 누르면
		if(e.getSource() == umv.getBtnOrder()) {
			
			orderStatus = "포장";
			if( umv.isHall() ) {
				orderStatus = "홀";
			}
			
			//빈 주문 관리 생성
			TotalOrderService tos = new TotalOrderService();
			TotalOrderVO toVO = new TotalOrderVO(tos.acquireNextOrderId(), orderStatus, "주문중");
			tos.addTotalOrder(toVO);
			
			//메뉴별 주문 생성
			MenuService ms = new MenuService();
			MenuOrderService mos = new MenuOrderService();
			for(int i = 0; i < umv.getDtm().getRowCount(); i++) {
				MenuVO mVO = ms.searchMenu((int)umv.getDtm().getValueAt(i, 3));
				MenuOrderVO moVO = new MenuOrderVO(toVO.getOrderId(), mVO.getMenuId(), mVO.getCategoryId(), (int)umv.getDtm().getValueAt(i, 1));
				mos.addMenuOrder(moVO);
			}
		}

	}

}
