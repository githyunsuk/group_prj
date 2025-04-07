package kr.co.kiosk.userView;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import kr.co.kiosk.userEvt.UsePointEvt;

public class UsePointView extends JFrame {
    
	private JTextField jtfTotalPoints;
	private JTextField jtfUsingPoints;
    private JButton[] arrNumpad;
    private JButton jbtnCancel;
    private JButton jbtnUse;
    private JButton jbtnClear;
    
    public UsePointView() {
        // 프레임 설정
        setLayout(null);
        setBounds(600, 100, 400, 600);
        
        JLabel jlblTitle = new JLabel("적립금 사용", JLabel.CENTER);
        jlblTitle.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        jlblTitle.setBounds(150, 30, 100, 20);
        
        jtfTotalPoints = new JTextField("보유 적립금", JTextField.LEFT);
        jtfTotalPoints.setEditable(false);
        jtfTotalPoints.setBounds(50, 60, 300, 40);
        
        jtfUsingPoints = new JTextField("사용할 금액", JTextField.LEFT);
        jtfUsingPoints.setEditable(false);
        jtfUsingPoints.setBounds(50, 105, 300, 40);
        
        // 숫자 패드 패널
        JPanel jpBtn = new JPanel(new GridLayout(4, 3));
        jpBtn.setBounds(50, 150, 300, 300);
        
        arrNumpad = new JButton[10];
        arrNumpad[0] = new JButton(String.valueOf(0));
//        ------------------------------이벤트를 여기서 해야 중복이 덜 발생해서 여기다 넣음----------------------
        UsePointEvt ipe = new UsePointEvt(this);
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
        jbtnUse = new JButton("사용");
        
        jbtnCancel.setBounds(50,470,110,60);
        jbtnUse.setBounds(240,470,110,60);
        
        
//        ---------------------------추가------------------------------------
        
        add(jlblTitle);
        add(jtfTotalPoints);
        add(jpBtn);
        add(jbtnCancel);
        add(jbtnUse);
        add(jtfUsingPoints);
        
        //--------------------이벤트 추가----------------------
        
        jbtnClear.addActionListener(ipe);
        jbtnCancel.addActionListener(ipe);
        jbtn.addActionListener(ipe);
        setVisible(true);
    } 
    
    

	public JTextField getJtfTotalPoints() {
		return jtfTotalPoints;
	}



	public JTextField getJtfUsingPoints() {
		return jtfUsingPoints;
	}



	public JButton[] getArrNumpad() {
		return arrNumpad;
	}



	public JButton getJbtnCancel() {
		return jbtnCancel;
	}



	public JButton getjbtnUse() {
		return jbtnUse;
	}



	public JButton getJbtnClear() {
		return jbtnClear;
	}

	
}