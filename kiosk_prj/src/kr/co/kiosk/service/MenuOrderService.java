package kr.co.kiosk.service;

import java.sql.SQLException;

import kr.co.kiosk.dao.MenuOrderDAO;
import kr.co.kiosk.vo.MenuOrderVO;

public class MenuOrderService {
	
	public MenuOrderService() {
		
	}//MenuOrderService
	
	public boolean addMenuOrder(MenuOrderVO moVO) {
		boolean flag = false;
		MenuOrderDAO moDAO = MenuOrderDAO.getInstance();
		
		try {
			moDAO.insertMenuOrder(moVO);
			flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return flag;
	}//addMenuOrder

}
