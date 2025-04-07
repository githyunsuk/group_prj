package kr.co.kiosk.userView;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import kr.co.kiosk.userEvt.InputPhonenumberEvt;

public class InputPhonenumberView extends JFrame {
    
	private JTextField jtfPhoneNumber; 
    private JButton[] arrNumpad;
    private JButton jbtnCancel;
    private JButton jbtnConfirm;
    private JButton jbtnClear;
    
    public InputPhonenumberView() {
        // 프레임 설정
        setLayout(null);
        setBounds(600, 100, 400, 600);
        
        JLabel jlblTitle = new JLabel("번호 입력", JLabel.CENTER);
        jlblTitle.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        jlblTitle.setBounds(150, 30, 100, 20);
        
        jtfPhoneNumber = new JTextField("휴대폰 번호를 입력해주세요", JTextField.CENTER);
        jtfPhoneNumber.setEditable(false);
        jtfPhoneNumber.setBounds(50, 70, 300, 50);
        
        // 숫자 패드 패널
        JPanel jpBtn = new JPanel(new GridLayout(4, 3));
        jpBtn.setBounds(50, 150, 300, 300);
        
        arrNumpad = new JButton[10];
        arrNumpad[0] = new JButton(String.valueOf(0));
//        ------------------------------이벤트를 여기서 해야 중복이 덜 발생----------------------
        InputPhonenumberEvt ipe = new InputPhonenumberEvt(this);
        addWindowListener(ipe);
        
        
        for(int i = 0; i < 10; i++) {
        	arrNumpad[i] = new JButton(String.valueOf(i));
        	if(i != 0) {
                jpBtn.add(arrNumpad[i]);	
        	}//end if
        	arrNumpad[i].addActionListener(ipe);
        	
        }//end for
        JButton jbtn = new JButton("");
        jpBtn.add(jbtn);
        jpBtn.add(arrNumpad[0]);
        // 추가 버튼 (지우기, 확인 등)
        jbtnClear = new JButton("clear");
        jpBtn.add(jbtnClear);
        
        jbtnCancel = new JButton("취소");
        jbtnConfirm = new JButton("조회");
        
        jbtnCancel.setBounds(50,470,110,60);
        jbtnConfirm.setBounds(240,470,110,60);
        
        
//        ---------------------------추가------------------------------------
        
        add(jlblTitle);
        add(jtfPhoneNumber);
        add(jpBtn);
        add(jbtnCancel);
        add(jbtnConfirm);
        
        
        //--------------------이벤트 추가----------------------
        
        jbtnClear.addActionListener(ipe);
        jbtnCancel.addActionListener(ipe);
        jbtnConfirm.addActionListener(ipe);
        
        setVisible(true);
    } 
    
  
    




	public JTextField getJtfPhoneNumber() {
		return jtfPhoneNumber;
	}







	public JButton[] getArrNumpad() {
		return arrNumpad;
	}







	public JButton getJbtnCancel() {
		return jbtnCancel;
	}







	public JButton getJbtnConfirm() {
		return jbtnConfirm;
	}







	public JButton getJbtnClear() {
		return jbtnClear;
	}



	
}