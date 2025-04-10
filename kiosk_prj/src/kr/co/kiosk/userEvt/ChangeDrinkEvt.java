package kr.co.kiosk.userEvt;

import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import kr.co.kiosk.userView.ChangeDrinkView;
import kr.co.kiosk.userView.UserMainView;
import kr.co.kiosk.vo.MenuVO;

public class ChangeDrinkEvt {
	private JPanel menuPanel;
	private ChangeDrinkView cdv;
	private UserMainView umv;
	private StringBuilder menuName;
	private AtomicInteger menuPrice;
	private List<MenuVO> drinkList;
	private int basicPrice; // 세트 기본 메뉴의 가격

	public ChangeDrinkEvt(ChangeDrinkView cdv, StringBuilder menuName, AtomicInteger menuPrice, UserMainView umv) {
		this.cdv = cdv;
		this.umv = umv;
		this.menuName = menuName;
		this.menuPrice = menuPrice;
		this.menuPanel = cdv.getMenuPanel();
		this.drinkList = getDrinkMenu();
	}

	private List<MenuVO> getDrinkMenu() {
		List<MenuVO> drinkList = new ArrayList<MenuVO>();

		for (MenuVO mVO : umv.getAllMenuList()) {
			if (mVO.getCategoryId() == 4) {
				drinkList.add(mVO);
			}
		}
		return drinkList;
	}// getDrinkMenu

	public void addMenuItem() {

		ImageIcon icon = new ImageIcon(getClass().getResource("/kr/co/kiosk/assets/noChange.jpg"));
		Image scaledImg = icon.getImage().getScaledInstance(125, 110, Image.SCALE_SMOOTH);
		ImageIcon img = new ImageIcon(scaledImg);


		// 우선 세트 기본 메뉴를 찾아서 기본 가격을 설정
		for (MenuVO mv : drinkList) {
			if (mv.getMenuName().equals("콜라M")) {
				basicPrice = mv.getPrice();
				break; // 먼저 찾고 종료
			}
		}

		// 재료 버튼들
		for (MenuVO mv : drinkList) {
			ImageIcon menuIcon = img;

			if (mv.getImgName() != null) {
					ImageIcon tempIcon = mv.getImage();
					Image tempImg = tempIcon.getImage().getScaledInstance(160, 130, Image.SCALE_SMOOTH);
					menuIcon = new ImageIcon(tempImg);
			}

			JButton btn = new JButton(menuIcon);
			btn.addActionListener(e -> menuBtnClicked(mv));

			JLabel lbl = new JLabel("<html>" + mv.getMenuName() + "<br>+" + (mv.getPrice() - basicPrice) + "</html>",
					SwingConstants.CENTER);

			JPanel itemPanel = new JPanel(new GridLayout(1, 1));
			itemPanel.add(btn);
			itemPanel.add(lbl);

			menuPanel.add(itemPanel);
		}

		// 빈공간 채우기
		while (menuPanel.getComponentCount() < 9) {
			menuPanel.add(new JPanel());
		}
	}// addMenuItem

	public void menuBtnClicked(MenuVO drinkList) {
		menuName.append(" / (음료)").append(drinkList.getMenuName());
		menuPrice.addAndGet(drinkList.getPrice()-basicPrice);
		cdv.dispose();
	}

}
