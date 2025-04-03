package kr.co.kiosk.adminView;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import kr.co.kiosk.adminEvt.AdminMainEvt;

public class AdminMainView extends JFrame {

	private AdminMainButtons adminMainButtons;
	
	private AdminCenterPanel adminCenterPanel;
	
	private AdminMainEvt ame;
	
	public AdminMainView() {
		this.adminMainButtons = new AdminMainButtons();
		this.adminCenterPanel = new AdminCenterPanel();
		this.ame = new AdminMainEvt(this);
		
		add(adminMainButtons, "North");
		add(adminCenterPanel, "Center");
		
		
		
		setBounds(400,10,800,800);
		setVisible(true);
		setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프로그램 완전 종료
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null); //화면 중앙에 정렬
		//setExtendedState(JFrame.MAXIMIZED_BOTH); //전체화면 모드 
		
		
		//이벤트연결
		AdminMainEvt ame = new AdminMainEvt(this);
		
		adminMainButtons.getBtnLogout().addActionListener(ame);
		adminMainButtons.getBtnShutdown().addActionListener(ame);
//		adminMainButtons.getBtnOrder().addActionListener(ame);
//		adminMainButtons.getBtnMenu().addActionListener(ame);
//		adminMainButtons.getBtnFinancial().addActionListener(ame);
//		adminMainButtons.getBtnStock().addActionListener(ame);
//		adminMainButtons.getBtnMember().addActionListener(ame);
		
		adminMainButtons.getBtnOrder().addActionListener(e -> adminCenterPanel.showPanel("ORDER"));
		adminMainButtons.getBtnMenu().addActionListener(e -> adminCenterPanel.showPanel("MENU"));
		adminMainButtons.getBtnFinancial().addActionListener(e -> adminCenterPanel.showPanel("FINANCIAL"));
		adminMainButtons.getBtnStock().addActionListener(e -> adminCenterPanel.showPanel("STOCK"));
		adminMainButtons.getBtnMember().addActionListener(e -> adminCenterPanel.showPanel("MEMBER"));

		
	}

	public AdminMainButtons getAdminMainButtons() {
		return adminMainButtons;
	}
	
	
	
}
