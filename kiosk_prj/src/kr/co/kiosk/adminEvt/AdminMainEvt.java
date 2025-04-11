package kr.co.kiosk.adminEvt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import kr.co.kiosk.adminService.OrderManageService;
import kr.co.kiosk.adminService.StockManageService;
import kr.co.kiosk.adminView.AdminCenterPanel;
import kr.co.kiosk.adminView.AdminMainView;
import kr.co.kiosk.adminView.OrderManageView;
import kr.co.kiosk.userView.MainPageView;
import kr.co.kiosk.vo.StockVO;
import kr.co.kiosk.vo.TotalOrderVO;

public class AdminMainEvt extends WindowAdapter implements ActionListener {
	
	private AdminCenterPanel acp;
	private AdminMainView amv;
	private OrderManageView omv;
	private OrderManageService oms;
	private StockManageService sms;
	
	public AdminMainEvt(AdminMainView amv) {
		this.amv = amv;
		this.acp = amv.getAdminCenterPanel();
		this.omv = amv.getOrderManageView();
		this.oms = new OrderManageService();
		this.sms = new StockManageService();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == amv.getAdminMainButtons().getBtnLogout()) {
			System.out.println("로그아웃 버튼 클릭");
			amv.dispose(); //해당창 종료 	
			new MainPageView().setVisible(true); //초기화면 이동 
		}
		
		if(e.getSource() == amv.getAdminMainButtons().getBtnShutdown()) {
		}
		
		if(e.getSource() == amv.getAdminMainButtons().getBtnOrder()) {
			System.out.println("주문관리 버튼 클릭");
			acp.showPanel("ORDER");
			List<TotalOrderVO> voList = oms.totalOrderVOList(0);
			omv.updateTable(voList);
			
			
		}
		if(e.getSource() == amv.getAdminMainButtons().getBtnMenu()) {
			System.out.println("메뉴관리 버튼 클릭");
			acp.showPanel("MENU");
			
		}
		if(e.getSource() == amv.getAdminMainButtons().getBtnFinancial()) {
			System.out.println("정산관리 버튼 클릭");
			acp.showPanel("FINANCIAL");
			
		}
		if(e.getSource() == amv.getAdminMainButtons().getBtnStock()) {
			System.out.println("재고관리 버튼 클릭");
			acp.showPanel("STOCK");
			amv.getAdminCenterPanel().getStockPage().getScp().showPanel("STOCKDETAIL");
			List<StockVO> list = sms.stockVOList();
			amv.getAdminCenterPanel().getStockPage().getScp().getSdtView().updateTable(list);
			
		}
		if(e.getSource() == amv.getAdminMainButtons().getBtnMember()) {
			System.out.println("회원관리 버튼 클릭");
			acp.showPanel("MEMBER");
			
		}
		
		

	}

}
