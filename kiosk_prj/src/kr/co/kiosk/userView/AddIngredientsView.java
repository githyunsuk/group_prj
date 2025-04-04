package kr.co.kiosk.userView;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import kr.co.kiosk.userEvt.AddIngredientsEvt;

@SuppressWarnings("serial")
public class AddIngredientsView extends JDialog {
	
	private JButton btnCancel, btnConfirm;
	private JPanel menuPanel;
	private UserMainView umv;
	
	public AddIngredientsView(UserMainView umv) {
		super(umv,"재료추가",true);
		this.umv = umv;
		
		setLayout(new BorderLayout());
		
		menuPanel = new JPanel(new GridLayout(0,3,10,10));
		add(menuPanel);
		
//		JPanel jpnlBtn = new JPanel();
//		btnCancel = new JButton("취소");
//		btnConfirm = new JButton("확인");
//		
//
//		jpnlBtn.add(btnCancel);
//		jpnlBtn.add(btnConfirm);
//		add(jpnlBtn, BorderLayout.SOUTH);
		
		AddIngredientsEvt aie = new AddIngredientsEvt(this, umv);
		aie.addMenuItem();
		
		setBounds(300,300,600,400);
		setVisible(true);
	}

	public JPanel getMenuPanel() {
		return menuPanel;
	}

	public void setMenuPanel(JPanel menuPanel) {
		this.menuPanel = menuPanel;
	}

	public UserMainView getUmv() {
		return umv;
	}

	public void setUmv(UserMainView umv) {
		this.umv = umv;
	}
	
	

}
