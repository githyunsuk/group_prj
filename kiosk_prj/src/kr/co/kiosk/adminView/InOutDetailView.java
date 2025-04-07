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

public class InOutDetailView extends JPanel {

	private DefaultTableModel dtm;
	private JTable jtblInoutStatus;
	private int countDataLogs;
	private String[][] dataLogs;
	private JButton showDetailOut; //상세보기 

	public InOutDetailView() {
		
		setLayout(new BorderLayout());

		this.countDataLogs = 20; //임시로 

		String[] columnNames = {"전체", "카테고리", "상품", "발생날짜", "총량"};
		
		dataLogs = new String[countDataLogs][columnNames.length]; 
		
		for(int i = 0; i < countDataLogs; i++) {
			
			//패널로 버튼 감싸
			LocalDateTime now = LocalDateTime.now();

	        // 원하는 날짜 형식 지정
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm");
	        String formattedDate = now.format(formatter); 
			
			/**
			 * 변동날짜는 입고를 했을 때의 sysdate 로 update해야한다. 
			 */
			dataLogs[i] = new String[] {"입고/출고/전체", "패티", "불고기패티", formattedDate , String.valueOf(i+2)+"Kg"};
		}
		
		
		//데이터 직접 수정 불가
		this.dtm = new DefaultTableModel(dataLogs, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		jtblInoutStatus = new JTable(dtm);
		jtblInoutStatus.setRowHeight(30);
		JScrollPane jspinOut = new JScrollPane(jtblInoutStatus);
		
		add(jspinOut, "Center");
		
		showDetailOut = new JButton("자세히 보기");
		add(showDetailOut, BorderLayout.SOUTH);

//컬럼 헤더 및 데이터 중앙정렬화 		
		//각 컬럼의 데이터들의 정렬 방식을 중앙 정렬로 설정
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		centerRenderer.setBorder(BorderFactory.createLineBorder(Color.BLACK)); //보더 선 긋기 
	
		//TableColumnModel을 사용하여 각 컬럼의 셀 렌더러를 중앙 정렬로 설정
		TableColumnModel columnModel = jtblInoutStatus.getColumnModel();
		for (int i = 0; i < jtblInoutStatus.getColumnCount(); i++) {
	        columnModel.getColumn(i).setCellRenderer(centerRenderer);
	    }
				
		
		// 패널의 크기 조정
		setPreferredSize(new java.awt.Dimension(700, 580)); // 가로 1000, 세로 600 크기로 설정

		
	}
}
