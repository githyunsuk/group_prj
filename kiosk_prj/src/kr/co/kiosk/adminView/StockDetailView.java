package kr.co.kiosk.adminView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class StockDetailView extends JPanel {

	private DefaultTableModel dtm;
	
	private JTable jtblStockStatus;

	private int countDataLogs; //DefaultTableModel 객체를 생성하는데 필요한 매개변수의 값을 Evt에서 가져오기 위해 클래스변수화(재고에 있는 재료 중에 카테고리가 사이드,음료,추가재료, 부속재료 인 것들의 품목수 -> DAO만들기)
	
	private String[][] dataLogs;
	
	private JButton saveStock;
	
	public StockDetailView() {
		
		setLayout(new BorderLayout());
		
		this.countDataLogs = 20; //임시로 
		String[] columnNames = {"카테고리", "상품", "변동날짜", "재고량"};
		
		dataLogs = new String[countDataLogs][columnNames.length]; //Obejct자료형 배열인 dataLogs를 재료품목수 만큼 배열 요소 갯수만큼 생성 
		
		/**
		 * 이것도 사실 evt에서 뽑아서 해야한다.
		 */
		for(int i = 0; i < countDataLogs; i++) {
			
			//패널로 버튼 감싸
			LocalDateTime now = LocalDateTime.now();

	        // 원하는 날짜 형식 지정
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");
	        String formattedDate = now.format(formatter); 
			
			/**
			 * 변동날짜는 입고를 했을 때의 sysdate 로 update해야한다. 
			 */
			dataLogs[i] = new String[] {"카테고리", "재료" , formattedDate , String.valueOf(i+2) +"개"};
		}
		
		//데이터 직접 수정 불가
		this.dtm = new DefaultTableModel(dataLogs, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		jtblStockStatus = new JTable(dtm);
		jtblStockStatus.setRowHeight(30);
		JScrollPane jspStock = new JScrollPane(jtblStockStatus);
		
		add(jspStock, "Center");
		
		saveStock = new JButton("재료 입고");
		add(saveStock, BorderLayout.SOUTH);
		
//컬럼 헤더 및 데이터 중앙정렬화 		
		//각 컬럼의 데이터들의 정렬 방식을 중앙 정렬로 설정
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		centerRenderer.setBorder(BorderFactory.createLineBorder(Color.BLACK)); //보더 선 긋기 

		//TableColumnModel을 사용하여 각 컬럼의 셀 렌더러를 중앙 정렬로 설정
		TableColumnModel columnModel = jtblStockStatus.getColumnModel();
		for (int i = 0; i < jtblStockStatus.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(centerRenderer);
        }
		
		
	}
}
