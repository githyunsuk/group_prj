package kr.co.kiosk.userEvt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import kr.co.kiosk.userView.InputPhonenumberView;
import kr.co.kiosk.userView.UsePointView;

public class InputPhonenumberEvt extends WindowAdapter implements ActionListener{
	private InputPhonenumberView ipv;
	
	public InputPhonenumberEvt(InputPhonenumberView ipv) {
		this.ipv = ipv;
	}//InputPhonenumberEvt

	
	
	
	
	
	
	@Override
	public void windowClosing(WindowEvent e) {
		ipv.dispose();
	}//windowClosing

	public void openStampView() {
		new UsePointView();
	}//openStampView
	
	public void openUsePiontView() {
		
	}//openUsePointView

	public void inputNumpad() {
		
	}
	/**
	 * 11자이상 적으면 안되서 제한하기 위한 메서드
	 * @param result
	 * @return
	 */
	public boolean chkLength(String result) {
	      if (result.length() > 10) {
	         JOptionPane.showMessageDialog(null, "전화번호는 11자입니다");
	         return false;
	      }
	      return true;
	   }

	   /**
	    * 처음 3글자가 010으로 시작해야되서 제한하기 위한 메서드
	 * @param result
	 * @return
	 */
	public boolean chk010(String result) {
	      if (result.length() == 3 && !result.equals("010")) {
	         JOptionPane.showMessageDialog(null, "전화번호는 010으로 시작해야 합니다");
	         ipv.getJtfPhoneNumber().setText("");
	         return false;
	      }
	      return true;
	   }


	 /**
	  * 번호 입력 메서드
	 * @param number
	 */
	public void inputNumber(int number) {
	      String result = ipv.getJtfPhoneNumber().getText();
	      if (result.equals("휴대폰 번호를 입력해주세요")) {
	         result = "";
	      } // end if

	      if (!(chkLength(result))) return;
	      if(!(chk010(result))) return;

	      ipv.getJtfPhoneNumber().setText(result + number);
	   }

	   @Override
	   public void actionPerformed(ActionEvent e) {
		   
		   if(e.getSource() == ipv.getJbtnClear()) {
				ipv.getJtfPhoneNumber().setText("");
				
			}//end if
			
			if(e.getSource() == ipv.getJbtnCancel()) {
				ipv.dispose();
			}//end if
			

	      for (int i = 0; i < 10; i++) {//버튼을 배열로 처리했기 때문에 0~9를 jtf에 보내기 위한 메서드
	         if (e.getSource() == ipv.getArrNumpad()[i]) {
	            inputNumber(i);
	            break;
	         }//end if
	      }//end for
	      
	      if(e.getSource() == ipv.getJbtnConfirm()) {
	    	  openStampView();
	    	  
	      }
	   }


	
	
	
}
