package kr.co.kiosk.userEvt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import kr.co.kiosk.userView.InputPhonenumberView;
import kr.co.kiosk.userView.UseStampView;

public class UseStampEvt extends WindowAdapter implements ActionListener{
	private UseStampView usv;
	private InputPhonenumberView ipv;
	public UseStampEvt(UseStampView usv) {
		this.usv = usv;
	}
	
//	public UseStampEvt(InputPhonenumberView ipv) { // InputPhonenumberView 을 받기 위해 이유는 밑에 주석에 있음
//		this.ipv = ipv;
//	}//UsePointEvt
	
	@Override
	public void windowClosing(WindowEvent e) {
		usv.dispose();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == usv.getJbtnCancel()) {
			usv.dispose();
			//ipv.setEnabled(true); //이거로 자식창이 open 됐을 때 부모창을 선택을 못하게 하려고 했는데 에러남
		}
		
	}//actionPerformed
	
}
