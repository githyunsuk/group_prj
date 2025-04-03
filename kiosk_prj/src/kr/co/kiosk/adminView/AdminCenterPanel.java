package kr.co.kiosk.adminView;

import java.awt.CardLayout;

import javax.swing.JPanel;

/**
 *	카드레이아웃이 적용된 JPanel클래스
 *	AdminMainView에서 메인을 차지한다. 
 */
public class AdminCenterPanel extends JPanel {

	
	private CardLayout cardLayout;
	private JPanel orderPanel, menuPanel, financialPanel, stockPanel, memberPanel;
	
	public AdminCenterPanel() {
		
		//레이아웃을 카드레이아웃으로 변경
		cardLayout = new CardLayout(); 
		setLayout(cardLayout);
		
		//주문관리
		orderPanel = new JPanel();
		OrderManageView orderPage = new OrderManageView();
		orderPanel.add(orderPage);
		
		
		//메뉴관리
		menuPanel = new JPanel();
		MenuManageView menuPage = new MenuManageView();
		menuPanel.add(menuPage);
		
		
		//정산관리
		financialPanel = new JPanel();
		FinancialManageView financialPage = new FinancialManageView();
		financialPanel.add(financialPage);
		
		
		//재고관리
		stockPanel = new JPanel();
		StockManageView stockPage = new StockManageView();
		stockPanel.add(stockPage);
		
		
		//회원관리
		memberPanel = new JPanel();
		MemberManageView memberPage = new MemberManageView();
		memberPanel.add(memberPage);
		
		//cardLayout에 추가
		add(orderPanel, "ORDER");
		add(menuPanel, "MENU");
		add(financialPanel, "FINANCIAL");
		add(stockPanel, "STOCK");
		add(memberPanel, "MEMBER");

		/**
		 * this는 CardLayout이 적용된 컨테이너여야 한다. 
		 */
		cardLayout.show(this, "ORDER");
		
	}
	
	public void showPanel(String panelName) {
		cardLayout.show(this, panelName);
	}
	
	
	
}
