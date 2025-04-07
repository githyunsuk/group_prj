package kr.co.kiosk.userView;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import kr.co.kiosk.userEvt.UseStampEvt;

public class UseStampView extends JFrame {
    private JTextField jtfStamp;
    private JButton jbtnCancel;
    private JButton jbtnConfirm;
    private JButton jbtnOther1;
    private JButton jbtnOther2;
    private JButton jbtnOther3;
    private JButton jbtnOther4;

    public UseStampView() {
        // 프레임 설정
        setLayout(null); 
        setBounds(600, 100, 400, 700); 

        // 제목
        JLabel jlblTitle = new JLabel("스탬프 사용", JLabel.CENTER);
        jlblTitle.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        jlblTitle.setBounds(150, 30, 100, 20);

        // 보유 스탬프 jtf
        jtfStamp = new JTextField("보유 스탬프", JTextField.LEFT);
        jtfStamp.setEditable(false); // 편집 불가
        jtfStamp.setBounds(50, 70, 300, 50); 

        // 버튼 패널
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 0, 10)); // 4개의 버튼, 세로로 10px 간격

        // 버튼 네개
        jbtnOther1 = new JButton("감자튀김                             필요 스탬프 : 3");// 띄우는 법을 몰라서 띄어쓰기함
        jbtnOther2 = new JButton("버튼 2");
        jbtnOther3 = new JButton("버튼 3");
        jbtnOther4 = new JButton("버튼 4");

        // 버튼들을 버튼 패널에 추가
        buttonPanel.add(jbtnOther1);
        buttonPanel.add(jbtnOther2);
        buttonPanel.add(jbtnOther3);
        buttonPanel.add(jbtnOther4);

        buttonPanel.setBounds(50, 140, 300, 350); 

        // 기존 버튼 설정 (취소, 사용 버튼)
        jbtnCancel = new JButton("취소");
        jbtnConfirm = new JButton("사용");

        // 취소, 사용 버튼 위치 설정
        jbtnCancel.setBounds(50, 510, 110, 60); 
        jbtnConfirm.setBounds(240, 510, 110, 60);

        // 이벤트 리스너 설정
        UseStampEvt ipe = new UseStampEvt(this); 
        addWindowListener(ipe);

        //추가
        add(jlblTitle); 
        add(jtfStamp);  
        add(buttonPanel);
        add(jbtnCancel); 
        add(jbtnConfirm);

        // 각 버튼에 이벤트 리스너 추가
        jbtnCancel.addActionListener(ipe);
        jbtnConfirm.addActionListener(ipe);
        
        setVisible(true); // 프레임을 보이게 설정
    }

    // 텍스트 필드와 버튼에 대한 getter 메소드
    public JTextField getJtfStamp() {
        return jtfStamp;
    }

    public JButton getJbtnCancel() {
        return jbtnCancel;
    }

    public JButton getJbtnConfirm() {
        return jbtnConfirm;
    }

    
}
