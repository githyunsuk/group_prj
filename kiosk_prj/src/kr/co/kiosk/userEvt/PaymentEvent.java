package kr.co.kiosk.userEvt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import kr.co.kiosk.userView.InputPhonenumberView;
import kr.co.kiosk.userView.PaymentView;

public class PaymentEvent implements ActionListener {
    private JDialog dialog;
    private boolean paymentDone = false;
    private PaymentView pv;

    public PaymentEvent(PaymentView pv) {
        this.pv = pv;

        dialog = new JDialog(pv.getParentFrame(), "결제창", true);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(pv);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (!paymentDone) {
                    int result = JOptionPane.showConfirmDialog(dialog,
                            "결제가 완료되지 않았습니다. 종료하시겠습니까?",
                            "확인", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        dialog.dispose();
                    }
                }
            }
        });

        // 버튼에 이벤트 연결
        pv.getCreditcardBtn().addActionListener(this);
        pv.getGiftcardBtn().addActionListener(this);
        pv.getKakaopayBtn().addActionListener(this);
        pv.getPayCoinBtn().addActionListener(this);
        pv.getZeropayBtn().addActionListener(this);
        pv.getOtherBtn().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	if(e.getSource() == pv.getCreditcardBtn()) {
    		new InputPhonenumberView();	
    	}
    	if(e.getSource() == pv.getGiftcardBtn()) {
    		new InputPhonenumberView();
    	}
    	if(e.getSource() == pv.getKakaopayBtn()) {
    		new InputPhonenumberView();
    	}
    	if(e.getSource() == pv.getPayCoinBtn()) {
    		new InputPhonenumberView() ;
    	}
    	if(e.getSource() == pv.getZeropayBtn()) {
    		new InputPhonenumberView();
    	}
    	if(e.getSource() == pv.getOtherBtn()) {
    		new InputPhonenumberView();
    	}
        JButton clicked = (JButton) e.getSource();
        String method = clicked.getText();
        
//        if(InputPhonenumberView.isinputDone()) {
//        JOptionPane.showMessageDialog(dialog, method + " 결제가 완료되었습니다.");
//        paymentDone = true;
//        dialog.dispose();
//        }
    }
}
