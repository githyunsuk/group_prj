package kr.co.kiosk.userEvt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import kr.co.kiosk.userView.UsePointView;

public class UsePointEvt extends WindowAdapter implements ActionListener{
	private UsePointView upv;
	
	public UsePointEvt(UsePointView usePointView) {
		this.upv = usePointView;
	}//InputPhonenumberEvt

	
	
	
	
	
	
	
	
	@Override
	public void windowClosing(WindowEvent e) {
		upv.dispose();
	}//windowClosing

	public void openStampView() {
		
	}//openStampView
	
	public void openUsePiontView() {
		
	}//openUsePointView

		
	


	 /**
	  * 
	  * 버튼을 눌러 jta에 추가하기 위한 메서드
	 * @param number
	 */
	public void inputNumber(int number) {
	      String result = upv.getJtfUsingPoints().getText();
	      upv.getJtfUsingPoints().setText(result + number);
	   }//inputNumber

	   @Override
	   public void actionPerformed(ActionEvent e) {
		   
		   if(e.getSource() == upv.getJbtnClear()) {//clear 버튼
				upv.getJtfUsingPoints().setText("");
				
			}//end if
			
			if(e.getSource() == upv.getJbtnCancel()) {//취소버튼
				upv.dispose();
			}//end if
			

	      for (int i = 0; i < 10; i++) {//버튼 배열에 0부터 9까지 넣기 위해 반복문으로 처리
	         if (e.getSource() == upv.getArrNumpad()[i]) {
	            inputNumber(i);
	            break;
	         }
	      }
	   }


	
	
	
}
