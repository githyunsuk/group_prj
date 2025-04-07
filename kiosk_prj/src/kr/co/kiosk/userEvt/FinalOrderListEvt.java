package kr.co.kiosk.userEvt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.table.DefaultTableModel;

import kr.co.kiosk.userView.FinalOrderListView;
import kr.co.kiosk.userView.InputPhonenumberView;

public class FinalOrderListEvt extends WindowAdapter implements ActionListener{
	
	private FinalOrderListView folv;
	
	
	public FinalOrderListEvt(FinalOrderListView folv) {
	   this.folv = folv;
	   DefaultTableModel dtm = folv.getDtm();
	   int totalResult = 0; // 총합을 저장할 변수
		    
	    if(dtm != null) { // 합계를 처리하기 위해 만들었는데 db에서 끌고올거라면 필요 없는 것 같음
	        for(int i = 0; i < dtm.getRowCount(); i++) {// 합계
	            try {
	                //dtm 에서 1열, 2열을 가져와 int형 변수에 담음 
	            	int price = Integer.parseInt(dtm.getValueAt(i, 2).toString());
	                int quantity = Integer.parseInt(dtm.getValueAt(i, 1).toString());
	                totalResult += price * quantity;
	            } catch (NumberFormatException nfe) {//숫자가 아니라 다르거 오는거 예외처리
	            	nfe.printStackTrace();
	            } catch(NullPointerException npe) {// 값이 안들어있을거를 대비해 예외처리
	            	npe.printStackTrace();
	            }
	        }
	        folv.getJlblTotalResult().setText(String.valueOf(totalResult) + "원");
	    }
	}
	
	public void openPaymentView() {
		
	}
	
//	public  openInputPhonenumberView(boolean) -> 클다에는 이렇게 하라는데 이유를 모르겠어서 일단 주석처리하고 밑에 void로 정의
	public void openInputPhonenumberView() {
		new InputPhonenumberView();
	}
	
	public void modifyQuantity() {
		
	}
	
	public void updateDtm() {
		
	}
	
	
	@Override
	public void windowClosing(WindowEvent e) {
		folv.dispose();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == folv.getJbtnCancel()) {
			folv.dispose();
		}
		if(e.getSource() == folv.getJbtnDiscount()) {
			new InputPhonenumberView();
		}
		
	}
	
}
