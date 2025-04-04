package kr.co.kiosk.adminView;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class StockManageView extends JPanel {
	
	private StockCenterPanel scp;
	
	private JButton stockDetail, inOutDetail;
	
	public StockManageView() {
		this.scp = new StockCenterPanel();
		
		setLayout(null);
		
		this.stockDetail = new JButton("재고 현황");
		this.inOutDetail = new JButton("입출고 내역");
		stockDetail.setFont(new Font("맑은 고딕", Font.BOLD, 40));
		inOutDetail.setFont(new Font("맑은 고딕", Font.BOLD, 40));
		
		JPanel jlblButton = new JPanel();
		jlblButton.setLayout(new GridLayout(1,2));
		jlblButton.add(stockDetail);
		jlblButton.add(inOutDetail);
		
		jlblButton.setBounds(100, 10, 600, 80);
		add(jlblButton);
		
		StockCenterPanel scp = new StockCenterPanel();
		scp.setBounds(100, 100, 600, 550);
		add(scp);
		
		//JPanel크기 설정 : 없으면 표시 안됨 
		setPreferredSize(new java.awt.Dimension(800, 800)); 
		
		//이벤트
		stockDetail.addActionListener(e -> scp.showPanel("STOCKDETAIIL"));
		inOutDetail.addActionListener(e -> scp.showPanel("INOUTDETAIL"));
		
	}

	public StockCenterPanel getScp() {
		return scp;
	}

	public JButton getStockDetail() {
		return stockDetail;
	}

	public JButton getInOutDetail() {
		return inOutDetail;
	}
	
	
	
}
