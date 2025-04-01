package kr.co.kiosk.userView;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import kr.co.kiosk.userEvt.MainPageEvt;

/**
 * 하얀배경, 빨간버튼, 하얀버튼글자 나중에 시도. 
 */
public class MainPageView extends JFrame {

	private JButton btnAdminView;
	private JButton btnTakeout;
	private JButton btnHall;
	String restaurantTitle = "쌍용버거";
	private JPanel titlePanel;
	
	public MainPageView() {
	 
//컴포넌트 모음  
		titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout());
		
		btnAdminView = new JButton("관리자모드");
		btnTakeout = new JButton("포장주문");
		btnHall = new JButton("매장식사");
		ImageIcon logo = new ImageIcon(getClass().getResource("/kr/co/kiosk/assets/BrandLogo.png"));
		
//컴포넌트 꾸미기. 이쁘게 부탁드려요.
		//타이틀 네온사인 효과 
		neonSignTitleEffect();
		
		//관리자 버튼 설정 
		btnAdminView.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		
		btnTakeout.setFont(new Font("맑은 고딕", Font.BOLD, 40));
		btnHall.setFont(new Font("맑은 고딕", Font.BOLD, 40));
		
		//로고이미지 사이즈 변경 
		Image originImg = logo.getImage();
		Image resizedImg = originImg.getScaledInstance(400, 350, Image.SCALE_SMOOTH);
		ImageIcon resizedLogo = new ImageIcon(resizedImg);
		
		//MainPage 레이아웃 변경 : 수동 배치  
		setLayout(null);
		
		//컴포넌트 배치 
		btnAdminView.setBounds(10,10,100,30);
		add(btnAdminView);
		
		titlePanel.setBounds(100, 70, 600, 100);
		add(titlePanel);
		
		JLabel jlblImg = new JLabel(resizedLogo);
		jlblImg.setBounds(150, 170, 500, 400);
		add(jlblImg);
		
		JPanel jplButtons = new JPanel();
		jplButtons.setLayout(new GridLayout(1, 2));
		jplButtons.add(btnTakeout);
		jplButtons.add(btnHall);
		jplButtons.setBounds(100,570, 600, 150);
		add(jplButtons);
		
		//이벤트 생성
		MainPageEvt mve = new MainPageEvt(this);
		addWindowListener(mve);
		
		btnAdminView.addActionListener(mve);
		btnHall.addActionListener(mve);
		btnTakeout.addActionListener(mve);
		
		setBounds(400,10,800,800);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null); //화면 중앙에 정렬
		//setExtendedState(JFrame.MAXIMIZED_BOTH); //전체화면 모드 
		
		
	}
	
	private void neonSignTitleEffect() {
		Timer timer = new Timer(700,  new ActionListener() {
			private boolean toggle = false;// 번갈아가며 실행할 변수 
			@Override
			public void actionPerformed(ActionEvent e) {
				if (toggle) {
	                changeTitleColor01();
	            } else {
	                changeTitleColor02();
	            }
	            toggle = !toggle; // true, false를 번갈아가며 변경
			}
		}); 
		timer.start();
	}
	
	private void changeTitleColor01() {
		// 레이아웃 갱신
		titlePanel.removeAll();
		
		char[] charArr = restaurantTitle.toCharArray();
		JLabel[] jlbArr = new JLabel[charArr.length];
		String[] colorArr = {"#00FF00", "#FF007F", "#9B00FF", "#FF6600"};
		
		for(int i = 0; i < charArr.length; i ++) {
			jlbArr[i] = new JLabel(String.valueOf(charArr[i]));
			jlbArr[i].setFont(new Font("맑은 고딕", Font.BOLD, 80));
			
			jlbArr[i].setForeground(Color.decode(colorArr[new Random().nextInt(4)]));
			titlePanel.add(jlbArr[i]);
		}
		// 레이아웃 갱신
		titlePanel.revalidate();
		titlePanel.repaint();
		
	}
	private void changeTitleColor02() {
		// 레이아웃 갱신
		titlePanel.removeAll();
		
		char[] charArr = restaurantTitle.toCharArray();
		JLabel[] jlbArr = new JLabel[charArr.length];
		String[] colorArr = {"#00FF00", "#FF007F", "#9B00FF", "#FF6600"};
		
		for(int i = 0; i < charArr.length; i ++) {
			jlbArr[i] = new JLabel(String.valueOf(charArr[i]));
			jlbArr[i].setFont(new Font("맑은 고딕", Font.BOLD, 80));
			
			jlbArr[i].setForeground(Color.decode(colorArr[new Random().nextInt(4)]));
			titlePanel.add(jlbArr[i]);
		}
		// 레이아웃 갱신
		titlePanel.revalidate();
		titlePanel.repaint();
		
	}

	public JButton getBtnAdminView() {
		return btnAdminView;
	}

	public JButton getBtnTakeout() {
		return btnTakeout;
	}

	public JButton getBtnHall() {
		return btnHall;
	}

	public String getRestaurantTitle() {
		return restaurantTitle;
	}

	public JPanel getTitlePanel() {
		return titlePanel;
	}
	
	
	
}
