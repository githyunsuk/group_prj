package kr.co.kiosk.adminView;

import java.awt.Color;
import java.awt.Panel;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import kr.co.kiosk.adminEvt.MemberManageEvt;
import kr.co.kiosk.adminEvt.MenuManageEvt;

public class MemberManageView extends Panel  {

	// 회원 관리 화면에서 사용되는 컴포넌트들 선언
    private JTextField jtfSearch; // 회원 검색을 위한 텍스트 필드
    private JTable jtblMember;  // 회원 목록을 표시할 테이블
    private DefaultTableModel tableModel;// 테이블에 데이터를 넣기 위한 모델
    private JButton jbtnSearch, jbtnselect, jbtnDelete, jbtnReset,
    		jbtnPointAdd, jbtnPointSubtract, jbtnStempAdd, jbtnStempSubtract ; // 각종 버튼들
    private JTextField jtfPoint; // 포인트 입력 필드
    private JTextField jtfStemp;  // 스탬프 입력 필드
    private JComboBox cb; // 회원 등급을 변경할 JComboBox
    

	
	
	public MemberManageView() {
		
		  // 회원관리 화면의 레이아웃 및 구성 요소 설정
		add(new JLabel("회원관리"));// 화면 제목 "회원관리" 레이블 추가

		
		  // 레이아웃을 null로 설정하여 위치를 좌표로 지정
			setLayout(null);

		    // 검색 텍스트 필드 설정
			jtfSearch = new JTextField();
	        jtfSearch.setBounds(80, 20, 200, 25);
	        add(jtfSearch);
	        
	        // 검색 버튼 설정
	        jbtnSearch=new JButton("검색");
	        jbtnSearch.setBounds(290, 20, 80, 25);
	        add(jbtnSearch);

	     // 테이블의 열(column)을 정의하고, 테이블 모델을 생성
	        String[] columns= {"회원ID", "전화번호", "누적금액","등급", "포인트", "스탬프"};
	        tableModel=new DefaultTableModel(columns, 0);
	        jtblMember=new JTable(tableModel);
	        
	        // 테이블을 스크롤 페인에 넣어 화면에 표시
	        JScrollPane jsp=new JScrollPane(jtblMember);
	        jsp.setBounds(20, 60, 370, 400);
	        add(jsp);
	        
	        
	        // 일괄 선택 버튼 추가
	        jbtnselect=new JButton("일괄 선택");
	        jbtnselect.setBounds(400, 110, 100, 30);
	        add(jbtnselect);
	        
	        // 회원 삭제 버튼 추가
	        jbtnDelete=new JButton("회원 삭제");
	        jbtnDelete.setBounds(530, 110, 100, 30);
	        add(jbtnDelete);
	        
	        // 초기화 버튼 추가
	        jbtnReset=new JButton("초기화");
	        jbtnReset.setBounds(660, 110, 100, 30);
	        add(jbtnReset);
	        
	        // 포인트 지급/차감 관련 레이블 및 텍스트 필드 추가
	        JLabel jlblpoint = new JLabel("포인트 지급/차감");
	        jlblpoint.setBounds(400,290,100, 30);
	        add(jlblpoint);
	        
	        jtfPoint=new JTextField();
	        jtfPoint.setBounds(500,290,100, 25);
	        add(jtfPoint);
	        
	        // 포인트 지급 및 차감 버튼 추가
	        jbtnPointAdd=new JButton("지급");
	        jbtnPointSubtract=new JButton("차감");
	        jbtnPointAdd.setBounds(610, 290, 80, 25);
	        jbtnPointSubtract.setBounds(695, 290, 80, 25);
	        add(jbtnPointAdd);
	        add(jbtnPointSubtract);
	        
	     // 스탬프 지급/차감 관련 레이블 및 텍스트 필드 추가
	        JLabel jlblStemp = new JLabel("스탬프 지급/차감");
	        jlblStemp.setBounds(400,210,100, 30);
	        add(jlblStemp);
	        
	        jtfStemp=new JTextField();
	        jtfStemp.setBounds(500,210,100, 25);
	        add(jtfStemp);
	        
	        // 스탬프 지급 및 차감 버튼 추가
	        jbtnStempAdd=new JButton("지급");
	        jbtnStempSubtract=new JButton("차감");
	        jbtnStempAdd.setBounds(610, 210, 80, 25);
	        jbtnStempSubtract.setBounds(695, 210, 80, 25);
	        add(jbtnStempAdd);
	        add(jbtnStempSubtract);
	        
	     // 등급 변경 관련 레이블 추가
	        JLabel jlblclass = new JLabel("등급변경");
	        jlblclass.setBounds(440,370,100, 30);
	        add(jlblclass);
	        
	     // 회원 등급을 변경할 수 있는 JComboBox 추가
	        String[] item= {"뱀", "이무기 ", "용", "쌍용"};
	        cb=new JComboBox<String>(item);
	        cb.setBounds(500, 370, 120, 30);
	        add(cb);
	        
	        // 이벤트 핸들러(MemberManageEvt) 등록
	        new MemberManageEvt(this);
	        
	     // 화면을 보이도록 설정하고 크기 및 위치 설정
	        setVisible(true);
	        setBounds(100, 100, 800, 800);
	        
	    
	}




	public JTextField getJtfSearch() {
		return jtfSearch;
	}




	public JTable getJtblMember() {
		return jtblMember;
	}




	public DefaultTableModel getTableModel() {
		return tableModel;
	}




	public JButton getJbtnSearch() {
		return jbtnSearch;
	}




	public JButton getJbtnselect() {
		return jbtnselect;
	}




	public JButton getJbtnDelete() {
		return jbtnDelete;
	}




	public JButton getJbtnReset() {
		return jbtnReset;
	}




	public JButton getJbtnPointAdd() {
		return jbtnPointAdd;
	}




	public JButton getJbtnPointSubtract() {
		return jbtnPointSubtract;
	}




	public JButton getJbtnStempAdd() {
		return jbtnStempAdd;
	}




	public JButton getJbtnStempSubtract() {
		return jbtnStempSubtract;
	}




	public JTextField getJtfPoint() {
		return jtfPoint;
	}




	public JTextField getJtfStemp() {
		return jtfStemp;
	}




	public JComboBox getCb() {
		return cb;
	}
	
	
}
