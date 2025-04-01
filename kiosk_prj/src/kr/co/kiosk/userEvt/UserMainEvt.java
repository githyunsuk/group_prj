package kr.co.kiosk.userEvt;

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
	
	@Override
	public void windowClosing(WindowEvent e) {
		umv.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

}
