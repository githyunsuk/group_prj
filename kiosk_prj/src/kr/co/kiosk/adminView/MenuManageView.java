package kr.co.kiosk.adminView;

import java.awt.Color;
import java.awt.Panel;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import kr.co.kiosk.adminEvt.MenuManageEvt;


public class MenuManageView extends Panel {
	
    private JTextField jtfSearch;  // 메뉴명을 검색할 텍스트 필드
    private JTable jtblMenu;  // 메뉴 항목을 표시할 테이블
    private DefaultTableModel tableModel;// 메뉴 데이터의 테이블 모델
    private JLabel jlblImage; // 선택된 메뉴 항목의 이미지를 표시할 레이블
    private JButton jbtnSearch, jbtnAdd, jbtnEdit, jbtnDelete, jbtnReset, jbtnFind;// 버튼들 (검색, 추가, 수정, 삭제, 초기화 등)
    
 // 메뉴 항목 세부 정보를 입력할 텍스트 필드들
    private JTextField JtfCategory;
	private JTextField JtfNumber;
	private JTextField JtfName;
	private JTextField JtfImage;
	private JTextField JtfWeight;
	private JTextField JtfCalorie;
	private JTextField JtfPrice;
	private JTextField JtfExplain;

  

	public MenuManageView() {
		
		// 패널에 제목 추가
		add(new JLabel("메뉴명 관리"));
		 
	    // 레이아웃 설정 (null 레이아웃 사용)
		setLayout(null);
		
	    // 검색 텍스트 필드 생성 및 추가
		jtfSearch = new JTextField();
        jtfSearch.setBounds(35, 20, 200, 25);
        add(jtfSearch);
        
        // 검색 버튼 생성 및 추가       
        jbtnSearch=new JButton("메뉴검색");
        jbtnSearch.setBounds(240, 20, 120, 25);
        add(jbtnSearch);

        // 테이블의 열(column) 이름을 정의하고, 테이블 모델 생성
        String[] columns= {"카테고리", "메뉴ID", "메뉴명", "사진 경로", "중량","칼로리","가격"};
        tableModel=new DefaultTableModel(columns, 0);
       
        // 테이블 생성 후 스크롤 페인에 넣어서 패널에 추가
        jtblMenu=new JTable(tableModel);
        JScrollPane jsp=new JScrollPane(jtblMenu);
        jsp.setBounds(20, 60, 400,400);
        add(jsp);
        
        
        // 이미지 표시용 레이블 생성 및 추가
        jlblImage=new JLabel("이미지",SwingConstants.CENTER);
        jlblImage.setBounds(520, 50, 200, 150);
        jlblImage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(jlblImage);
        
        // 메뉴 추가 버튼 생성 및 추가
        jbtnAdd=new JButton("메뉴 추가");
        jbtnAdd.setBounds(470, 540, 100, 30);
        add(jbtnAdd);
        
        // 메뉴 수정 버튼 생성 및 추가
        jbtnEdit=new JButton("메뉴 수정");
        jbtnEdit.setBounds(470, 580, 100, 30);
        add(jbtnEdit);
        
     // 메뉴 삭제 버튼 생성 및 추가
        jbtnDelete=new JButton("메뉴 삭제");
        jbtnDelete.setBounds(580, 540, 100, 30);
        add(jbtnDelete);
        
        // 초기화 버튼 생성 및 추가
        jbtnReset=new JButton("초기화");
        jbtnReset.setBounds(580, 580, 100, 30);
        add(jbtnReset);
        
        // 각 메뉴 항목에 대한 입력 필드와 레이블 생성 및 추가
        JLabel JlblCategory= new JLabel("카테고리");
		JlblCategory.setBounds(460,220,70, 30);
		add(JlblCategory);
      
		JtfCategory=new JTextField();
		JtfCategory.setBounds(530,220,200,30);
		add(JtfCategory);
		
		
		JLabel JlblNumber= new JLabel("고유번호");
		JlblNumber.setBounds(460,260,100, 30);
		add(JlblNumber);
		
		JtfNumber=new JTextField();
		JtfNumber.setBounds(530,260,200,30);
		add(JtfNumber);
		
		JLabel JlblName= new JLabel("이름");
		JlblName.setBounds(460,300,100, 30);
		add(JlblName);
		
		JtfName=new JTextField();
		JtfName.setBounds(530,300,200,30);
		add(JtfName);
		
		
		
		JLabel JlblImage= new JLabel("사진");
		JlblImage.setBounds(460,340,100, 30);
		add(JlblImage);
		
		JtfImage=new JTextField();
		JtfImage.setBounds(530,340,200,30);
		add(JtfImage);
		
		// 이미지 찾기 버튼 추가
		jbtnFind=new JButton("찾기");
		jbtnFind.setBounds(730, 340, 60, 30);
		add(jbtnFind);
		
		
		
		JLabel JlblWeight= new JLabel("중량");
		JlblWeight.setBounds(460,380,70, 30);
		add(JlblWeight);
		
		JtfWeight=new JTextField();
		JtfWeight.setBounds(530,380,200,30);
		add(JtfWeight);
		
		JLabel JlblCalorie= new JLabel("칼로리");
		JlblCalorie.setBounds(460,420,100, 30);
		add(JlblCalorie);
		
		JtfCalorie=new JTextField();
		JtfCalorie.setBounds(530,420,200,30);
		add(JtfCalorie);
		
		JLabel JlblPrice= new JLabel("가격");
		JlblPrice.setBounds(460,460,100, 30);
		add(JlblPrice);
		
		JtfPrice=new JTextField();
		JtfPrice.setBounds(530,460,200,30);
		add(JtfPrice);
		
		JLabel JlblExplain= new JLabel("설명");
		JlblExplain.setBounds(460,500,100, 30);
		add(JlblExplain);
		
		JtfExplain=new JTextField();
		JtfExplain.setBounds(530,500,200,30);
		add(JtfExplain);
        
      
		 // 이벤트 핸들러(MenuManageEvt)를 초기화하여 컴포넌트와 연결
        new MenuManageEvt(this);
        
        // 패널을 보이도록 설정하고 크기 설정
        setVisible(true);
        setBounds(200, 200, 810, 800);
    }

	public JTextField getJtfSearch() {
		return jtfSearch;
	}


	public JTable getJtblMenu() {
		return jtblMenu;
	}


	public DefaultTableModel getTableModel() {
		return tableModel;
	}


	public JLabel getJlblImage() {
		return jlblImage;
	}


	public JButton getJbtnSearch() {
		return jbtnSearch;
	}


	public JButton getJbtnAdd() {
		return jbtnAdd;
	}


	public JButton getJbtnEdit() {
		return jbtnEdit;
	}


	public JButton getJbtnDelete() {
		return jbtnDelete;
	}


	public JButton getJbtnReset() {
		return jbtnReset;
	}


	public JTextField getJtfCategory() {
		return JtfCategory;
	}


	public JTextField getJtfNumber() {
		return JtfNumber;
	}


	public JTextField getJtfName() {
		return JtfName;
	}


	public JTextField getJtfImage() {
		return JtfImage;
	}


	public JTextField getJtfWeight() {
		return JtfWeight;
	}


	public JTextField getJtfCalorie() {
		return JtfCalorie;
	}


	public JTextField getJtfPrice() {
		return JtfPrice;
	}


	public JTextField getJtfExplain() {
		return JtfExplain;
	}

	public JButton getJbtnFind() {
		return jbtnFind;
	}

	
	
}


		