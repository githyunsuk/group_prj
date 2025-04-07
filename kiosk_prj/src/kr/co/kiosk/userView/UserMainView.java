package kr.co.kiosk.userView;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import kr.co.kiosk.service.MenuService;
import kr.co.kiosk.userEvt.UserMainEvt;
import kr.co.kiosk.vo.MenuVO;

@SuppressWarnings("serial")
public class UserMainView extends JFrame {

	private JFrame frame;
	private JTextField jtfTotalQuantity, jtfTotalPrice;

	private DefaultTableModel dtm;
	private JTable table;
	private JScrollPane jsp;

	private CardLayout cl;
	private JPanel jpnlMain, jpnlBtn;

	private JButton btnHome, btnOrder, btnStamp, btnCancelAll, btnRecommendView, btnBurgerView, btnSideView,
			btnDrinkView;
	private JButton btnPlus, btnMinus, btnCancel;

	private RecommendMenuView rmv;
	private BurgerMenuView bmv;
	private SideMenuView smv;
	private DrinkMenuView dmv;

	private boolean isHall;
	private List<MenuVO> allMenuList;

	public UserMainView(boolean isHall) {
		this.isHall = isHall;
		MenuService ms = new MenuService();
		allMenuList = ms.searchAllMenu();

		frame = new JFrame();
		frame.setBounds(400, 10, 800, 950);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel jlblSsangyongBurger = new JLabel("쌍용버거");
		jlblSsangyongBurger.setFont(new Font("굴림", Font.PLAIN, 18));
		jlblSsangyongBurger.setBounds(12, 10, 115, 45);
		frame.getContentPane().add(jlblSsangyongBurger);

		btnHome = new JButton("HOME");
		btnHome.setBounds(662, 10, 115, 45);
		frame.getContentPane().add(btnHome);

		btnOrder = new JButton("주 문");
		btnOrder.setFont(new Font("굴림", Font.PLAIN, 18));
		btnOrder.setBounds(657, 804, 115, 97);
		frame.getContentPane().add(btnOrder);
	

		btnStamp = new JButton("스탬프");
		btnStamp.setFont(new Font("굴림", Font.PLAIN, 18));
		btnStamp.setBounds(530, 804, 115, 97);
		frame.getContentPane().add(btnStamp);

		btnCancelAll = new JButton("전체 취소");
		btnCancelAll.setFont(new Font("굴림", Font.PLAIN, 18));
		btnCancelAll.setBounds(403, 804, 115, 97);
		frame.getContentPane().add(btnCancelAll);

		jtfTotalQuantity = new JTextField("0");
		jtfTotalQuantity.setColumns(10);
		jtfTotalQuantity.setEditable(false);
		jtfTotalQuantity.setBounds(177, 804, 215, 35);
		frame.getContentPane().add(jtfTotalQuantity);

		jtfTotalPrice = new JTextField("0");
		jtfTotalPrice.setColumns(10);
		jtfTotalPrice.setEditable(false);
		jtfTotalPrice.setBounds(177, 864, 215, 35);
		frame.getContentPane().add(jtfTotalPrice);

		JLabel jlblTotalQuantity = new JLabel("수 량");
		jlblTotalQuantity.setFont(new Font("굴림", Font.PLAIN, 20));
		jlblTotalQuantity.setBounds(65, 804, 85, 35);
		frame.getContentPane().add(jlblTotalQuantity);

		JLabel jlblTotalPrice = new JLabel("금 액");
		jlblTotalPrice.setFont(new Font("굴림", Font.PLAIN, 20));
		jlblTotalPrice.setBounds(65, 864, 85, 35);
		frame.getContentPane().add(jlblTotalPrice);

		// dtm 설정
		String[] columnName = { "메뉴", "수량", "가격", "메뉴id" };
		dtm = new DefaultTableModel(columnName, 0);

		table = new JTable(dtm);

		table.setDefaultEditor(Object.class, null);
		table.getColumnModel().getColumn(0).setMinWidth(400); // 최소 너비
		table.getColumnModel().getColumn(0).setMaxWidth(400); // 최대 너비
		table.getColumnModel().getColumn(0).setPreferredWidth(400); // 기본 너비

		// 메뉴Id컬럼 숨기기(메뉴ID를 받아와야해서 어쩔 수 없이 dtm에 저장은 하되, 사용자에게는 보이지 않게
		table.getColumnModel().getColumn(3).setMinWidth(0);
		table.getColumnModel().getColumn(3).setMaxWidth(0);
		table.getColumnModel().getColumn(3).setWidth(0);
		table.getColumnModel().getColumn(3).setPreferredWidth(0);

		// 행의크기 설정.
		table.setRowHeight(38);

		jsp = new JScrollPane(table);
		jsp.setBounds(15, 517, 755, 225);
		frame.getContentPane().add(jsp);

		btnMinus = new JButton("-");
		btnMinus.setBounds(307, 754, 50, 30);
		frame.getContentPane().add(btnMinus);
		btnPlus = new JButton("+");
		btnPlus.setBounds(367, 754, 50, 30);
		frame.getContentPane().add(btnPlus);
		btnCancel = new JButton("x");
		btnCancel.setBounds(427, 754, 50, 30);
		frame.getContentPane().add(btnCancel);

		// 상단 메뉴바
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

		cl = new CardLayout();
		jpnlMain = new JPanel(cl);
		jpnlMain.setBounds(6, 161, 772, 346);
		frame.getContentPane().add(jpnlMain);

		// 카드레이아웃 패널 생성
		rmv = new RecommendMenuView(this);
		bmv = new BurgerMenuView(this);
		smv = new SideMenuView(this);
		dmv = new DrinkMenuView(this);

		jpnlMain.add(rmv, "rmv");
		jpnlMain.add(bmv, "bmv");
		jpnlMain.add(smv, "smv");
		jpnlMain.add(dmv, "dmv");

		UserMainEvt ume = new UserMainEvt(this);
		frame.addWindowListener(ume);

		btnRecommendView.addActionListener(ume);
		btnBurgerView.addActionListener(ume);
		btnSideView.addActionListener(ume);
		btnDrinkView.addActionListener(ume);
		btnCancelAll.addActionListener(ume);
		btnOrder.addActionListener(ume);
		btnPlus.addActionListener(ume);
		btnMinus.addActionListener(ume);
		btnCancel.addActionListener(ume);
		btnHome.addActionListener(ume);
		btnStamp.addActionListener(ume);

		frame.setVisible(true);

	}

	public List<MenuVO> getAllMenuList() {
		return allMenuList;
	}

	public void setAllMenuList(List<MenuVO> allMenuList) {
		this.allMenuList = allMenuList;
	}

	public JButton getBtnHome() {
		return btnHome;
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

	public JButton getBtnPlus() {
		return btnPlus;
	}

	public JButton getBtnMinus() {
		return btnMinus;
	}

	public JButton getBtnCancel() {
		return btnCancel;
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

	public boolean isHall() {
		return isHall;
	}

}
