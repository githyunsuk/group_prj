package kr.co.kiosk.userEvt;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import kr.co.kiosk.userView.UserMainView;

public class UserMainEvt extends WindowAdapter implements ActionListener {

	private UserMainView umv;

	public UserMainEvt(UserMainView umv) {
		this.umv = umv;
	}

	//패널 바꿀 때 마다 버튼 색 초기화 하는 method
	private void resetButtonColors() {
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

	}

}
