package kr.co.kiosk.adminView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import kr.co.kiosk.vo.StockSummaryVO;
import kr.co.kiosk.vo.StockUpVO;
import kr.co.kiosk.vo.StockVO;

public class InOutDetailView extends JPanel {

	private DefaultTableModel dtm;
	private JTable jtblInoutStatus;
	private int countDataLogs;
	private String[][] dataLogs;
	private JButton showDetailOut; //상세보기 

	public InOutDetailView() {
		
		setLayout(new BorderLayout());

		this.countDataLogs = 20; //임시로 

		String[] columnNames = {"재료명", "날짜", "입고건수", "출고건수", "입고합계", "출고합계"};
		
		//데이터 직접 수정 불가
		this.dtm = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		jtblInoutStatus = new JTable(dtm);
		jtblInoutStatus.setRowHeight(30);
		JScrollPane jspinOut = new JScrollPane(jtblInoutStatus);
		
		add(jspinOut, "Center");
		
		JPanel jplShowDetailOut = new JPanel();
		jplShowDetailOut.setLayout(new GridLayout(1,4));
		jplShowDetailOut.add(new JLabel(""));
		jplShowDetailOut.add(new JLabel(""));
		jplShowDetailOut.add(new JLabel(""));
		showDetailOut = new JButton("자세히 보기");
		jplShowDetailOut.add(showDetailOut);
		add(jplShowDetailOut, BorderLayout.SOUTH);

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
	
	public void updateTable(List<StockSummaryVO> voList) {
		System.out.println("iodv.updateTable() 실행");
		dtm.setRowCount(0); //초기화 
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		for(int i = 0; i < voList.size(); i++) {
			StockSummaryVO vo = voList.get(i);
			//String formattedDate = sdf.format(vo.getInputDate());
			
			String[] row = {
					vo.getMenuName(),
					vo.getInputDate(),
					String.valueOf(vo.getInCount()),
					String.valueOf(vo.getOutCount()),
					String.valueOf(vo.getInTotal()),
					String.valueOf(vo.getOutTotal()),
			};
			
			dtm.addRow(row);
		}
		
	}
	
	
}
