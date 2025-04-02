package kr.co.kiosk.userView;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import kr.co.kiosk.userEvt.UserMainEvt;

@SuppressWarnings("serial")
public class UserMainView extends JFrame {

	private JFrame frame;
	private JTextField jtfTotalQuantity, jtfTotalPrice;
	
	private DefaultTableModel dtm;
	private JTable table;
	private JScrollPane jsp;
	
	private CardLayout cl;
	private JPanel jpnlMain, jpnlBtn;
	
	private JButton btnOrder, btnStamp, btnCancelAll, btnRecommendView, btnBurgerView, btnSideView, btnDrinkView;
	private JButton btnPlusOne, btnPlusTwo, btnPlusThree, btnMinusOne, btnMinusTwo, btnMinusThree,
					btnCancelOne, btnCancelTwo, btnCancelThree;
	
	private RecommendMenuView rmv;
	private BurgerMenuView bmv;
	private SideMenuView smv;
	private DrinkMenuView dmv;


	public UserMainView() {
		frame = new JFrame();
		frame.setBounds(400, 10, 800, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel jlblSsangyongBurger = new JLabel("쌍용버거");
		jlblSsangyongBurger.setFont(new Font("굴림", Font.PLAIN, 18));
		jlblSsangyongBurger.setBounds(12, 10, 115, 45);
		frame.getContentPane().add(jlblSsangyongBurger);
		
		btnOrder = new JButton("주 문");
		btnOrder.setFont(new Font("굴림", Font.PLAIN, 18));
		btnOrder.setBounds(657, 754, 115, 97);
		frame.getContentPane().add(btnOrder);
		
		btnStamp = new JButton("스탬프");
		btnStamp.setFont(new Font("굴림", Font.PLAIN, 18));
		btnStamp.setBounds(530, 754, 115, 97);
		frame.getContentPane().add(btnStamp);
		
		btnCancelAll = new JButton("전체 취소");
		btnCancelAll.setFont(new Font("굴림", Font.PLAIN, 18));
		btnCancelAll.setBounds(403, 754, 115, 97);
		frame.getContentPane().add(btnCancelAll);
		
		jtfTotalQuantity = new JTextField();
		jtfTotalQuantity.setBounds(177, 754, 215, 35);
		frame.getContentPane().add(jtfTotalQuantity);
		jtfTotalQuantity.setColumns(10);
		
		jtfTotalPrice = new JTextField();
		jtfTotalPrice.setColumns(10);
		jtfTotalPrice.setBounds(177, 816, 215, 35);
		frame.getContentPane().add(jtfTotalPrice);
		
		JLabel jlblTotalQuantity = new JLabel("수 량");
		jlblTotalQuantity.setFont(new Font("굴림", Font.PLAIN, 20));
		jlblTotalQuantity.setBounds(65, 752, 85, 35);
		frame.getContentPane().add(jlblTotalQuantity);
		
		JLabel jlblTotalPrice = new JLabel("금 액");
		jlblTotalPrice.setFont(new Font("굴림", Font.PLAIN, 20));
		jlblTotalPrice.setBounds(65, 816, 85, 35);
		frame.getContentPane().add(jlblTotalPrice);
		
		//dtm 설정
		String[] columnName = {"메뉴","수량","가격"};
		dtm = new DefaultTableModel(columnName, 0);
		
		table = new JTable(dtm);
		
		table.setDefaultEditor(Object.class, null);
		table.getColumnModel().getColumn(0).setMinWidth(200); // 최소 너비
		table.getColumnModel().getColumn(0).setMaxWidth(200); // 최대 너비
		table.getColumnModel().getColumn(0).setPreferredWidth(200); // 기본 너비
		
		//행의크기 설정.
		table.setRowHeight(38);
		
		jsp = new JScrollPane(table);
		jsp.setBounds(89, 517, 655, 225);
		frame.getContentPane().add(jsp);
		
		btnPlusOne = new JButton("+");
		btnPlusOne.setFont(new Font("굴림", Font.PLAIN, 10));
		btnPlusOne.setBounds(42, 543, 45, 35);
//		btnNewButton_5.setVisible(false);
		frame.getContentPane().add(btnPlusOne);
		
		//수량 변경 및 메뉴 취소 버튼들
		btnPlusTwo = new JButton("+");
		btnPlusTwo.setFont(new Font("굴림", Font.PLAIN, 10));
		btnPlusTwo.setBounds(42, 578, 45, 35);
		frame.getContentPane().add(btnPlusTwo);
		
		btnPlusThree = new JButton("+");
		btnPlusThree.setFont(new Font("굴림", Font.PLAIN, 10));
		btnPlusThree.setBounds(42, 611, 45, 35);
		frame.getContentPane().add(btnPlusThree);
		
		btnMinusOne = new JButton("-");
		btnMinusOne.setFont(new Font("굴림", Font.PLAIN, 10));
		btnMinusOne.setBounds(0, 543, 45, 35);
		frame.getContentPane().add(btnMinusOne);
		 
		btnMinusTwo = new JButton("-");
		btnMinusTwo.setFont(new Font("굴림", Font.PLAIN, 10));
		btnMinusTwo.setBounds(0, 578, 45, 35);
		frame.getContentPane().add(btnMinusTwo);
		
		btnMinusThree = new JButton("-");
		btnMinusThree.setFont(new Font("굴림", Font.PLAIN, 10));
		btnMinusThree.setBounds(0, 611, 45, 35);
		frame.getContentPane().add(btnMinusThree);
		
		btnCancelOne = new JButton("x");
		btnCancelOne.setFont(new Font("굴림", Font.PLAIN, 10));
		btnCancelOne.setBounds(745, 543, 39, 35);
		frame.getContentPane().add(btnCancelOne);
		
		btnCancelTwo = new JButton("x");
		btnCancelTwo.setFont(new Font("굴림", Font.PLAIN, 10));
		btnCancelTwo.setBounds(745, 578, 39, 35);
		frame.getContentPane().add(btnCancelTwo);
		
		btnCancelThree = new JButton("x");
		btnCancelThree.setFont(new Font("굴림", Font.PLAIN, 10));
		btnCancelThree.setBounds(745, 611, 39, 35);
		frame.getContentPane().add(btnCancelThree);
		
		//상단 메뉴바
		jpnlBtn = new JPanel();
		jpnlBtn.setBounds(0, 45, 784, 114);
		frame.getContentPane().add(jpnlBtn);
		jpnlBtn.setLayout(null);
		
		btnRecommendView = new JButton("추천 메뉴");
		btnRecommendView.setBounds(0, 22, 195, 66);
		btnRecommendView.setBackground(Color.decode("#2196F3"));
		jpnlBtn.add(btnRecommendView);
		
		btnBurgerView = new JButton("버거 / 세트 ");
		btnBurgerView.setBounds(196, 22, 195, 66);
		jpnlBtn.add(btnBurgerView);
		
		btnSideView = new JButton("사이드 메뉴");
		btnSideView.setBounds(392, 22, 195, 66);
		jpnlBtn.add(btnSideView);
		
		btnDrinkView = new JButton("음료");
		btnDrinkView.setBounds(589, 22, 195, 66);
		jpnlBtn.add(btnDrinkView);
		
		cl=new CardLayout();
		jpnlMain = new JPanel(cl);
		jpnlMain.setBounds(0, 161, 772, 346);
		frame.getContentPane().add(jpnlMain);
		
		//카드레이아웃 패널 생성
		rmv = new RecommendMenuView(this);
		bmv = new BurgerMenuView(this);
		smv = new SideMenuView(this);
		dmv = new DrinkMenuView(this);
		
		jpnlMain.add(rmv,"rmv");
		jpnlMain.add(bmv,"bmv");
		jpnlMain.add(smv,"smv");
		jpnlMain.add(dmv,"dmv");

		UserMainEvt ume = new UserMainEvt(this);
		addWindowListener(ume);
		btnRecommendView.addActionListener(ume);
		btnBurgerView.addActionListener(ume);
		btnSideView.addActionListener(ume);
		btnDrinkView.addActionListener(ume);
		btnCancelAll.addActionListener(ume);
		
		frame.setVisible(true);
		
	}

	public JFrame getFrame() {
		return frame;
	}


	public JTextField getJtfTotalQuantity() {
		return jtfTotalQuantity;
	}


	public JTextField getJtfTotalPrice() {
		return jtfTotalPrice;
	}


	public DefaultTableModel getDtm() {
		return dtm;
	}


	public JTable getTable() {
		return table;
	}


	public JScrollPane getJsp() {
		return jsp;
	}


	public CardLayout getCl() {
		return cl;
	}


	public JPanel getJpnlMain() {
		return jpnlMain;
	}


	public JPanel getJpnlBtn() {
		return jpnlBtn;
	}


	public JButton getBtnOrder() {
		return btnOrder;
	}


	public JButton getBtnStamp() {
		return btnStamp;
	}


	public JButton getBtnCancelAll() {
		return btnCancelAll;
	}


	public JButton getBtnRecommendView() {
		return btnRecommendView;
	}


	public JButton getBtnBurgerView() {
		return btnBurgerView;
	}


	public JButton getBtnSideView() {
		return btnSideView;
	}


	public JButton getBtnDrinkView() {
		return btnDrinkView;
	}


	public JButton getBtnPlusOne() {
		return btnPlusOne;
	}


	public JButton getBtnPlusTwo() {
		return btnPlusTwo;
	}


	public JButton getBtnPlusThree() {
		return btnPlusThree;
	}


	public JButton getBtnMinusOne() {
		return btnMinusOne;
	}


	public JButton getBtnMinusTwo() {
		return btnMinusTwo;
	}


	public JButton getBtnMinusThree() {
		return btnMinusThree;
	}


	public JButton getBtnCancelOne() {
		return btnCancelOne;
	}


	public JButton getBtnCancelTwo() {
		return btnCancelTwo;
	}


	public JButton getBtnCancelThree() {
		return btnCancelThree;
	}


	public RecommendMenuView getRmv() {
		return rmv;
	}


	public BurgerMenuView getBmv() {
		return bmv;
	}


	public SideMenuView getSmv() {
		return smv;
	}


	public DrinkMenuView getDmv() {
		return dmv;
	}
	
	
}
