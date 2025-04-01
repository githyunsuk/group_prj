package kr.co.kiosk.userView;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import kr.co.kiosk.userEvt.BurgerMenuEvt;

@SuppressWarnings("serial")
public class BurgerMenuView extends JPanel {

	private static final int MAX_PER_PAGE = 9; // 한 페이지당 최대 메뉴 개수
	private int currentPage = 0; // 현재 페이지 번호

	private JPanel menuPanel; // 메뉴를 표시할 패널
	private JButton btnPrev, btnNext; // 페이지 이동 버튼
	

	private UserMainView umv;


	public BurgerMenuView(UserMainView umv) {
		this.umv = umv;
		setLayout(new BorderLayout());

		// 메뉴를 담을 패널 (GridLayout 적용)
		menuPanel = new JPanel(new GridLayout(3, 3, 10,10));
		add(menuPanel);

		// 페이지 이동 버튼 추가
		JPanel jpnlBtn = new JPanel();
		btnPrev = new JButton("이전");
		btnNext = new JButton("다음");

		BurgerMenuEvt bme = new BurgerMenuEvt(this, umv);
		bme.loadMenu();
		btnPrev.addActionListener(bme);
		btnNext.addActionListener(bme);
		

		jpnlBtn.add(btnPrev);
		jpnlBtn.add(btnNext);
		add(jpnlBtn, BorderLayout.SOUTH);

		
	
	}//BurgerMenuView


	public int getCurrentPage() {
		return currentPage;
	}


	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public JPanel getMenuPanel() {
		return menuPanel;
	}


	public void setMenuPanel(JPanel menuPanel) {
		this.menuPanel = menuPanel;
	}


	public JButton getBtnPrev() {
		return btnPrev;
	}


	public void setBtnPrev(JButton btnPrev) {
		this.btnPrev = btnPrev;
	}


	public JButton getBtnNext() {
		return btnNext;
	}


	public void setBtnNext(JButton btnNext) {
		this.btnNext = btnNext;
	}


	public UserMainView getUmv() {
		return umv;
	}


	public void setUmv(UserMainView umv) {
		this.umv = umv;
	}


	public static int getMaxPerPage() {
		return MAX_PER_PAGE;
	}

	
}