package kr.co.kiosk.service;

import java.sql.SQLException;

import kr.co.kiosk.dao.TotalOrderDAO;
import kr.co.kiosk.vo.TotalOrderVO;

public class TotalOrderService {

	public TotalOrderService() {

	}// TotalOrderService

	public boolean addTotalOrder(TotalOrderVO toVO) {
		boolean flag = false;
		TotalOrderDAO toDAO = TotalOrderDAO.getInstance();

		try {
			toDAO.insertTotalOrder(toVO);
			flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}// addTotalOrder

	public int acquireNextOrderId() {
		int orderId = -1;
		TotalOrderDAO toDAO = TotalOrderDAO.getInstance();
		try {
			orderId = toDAO.getNextOrderId();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderId;
	}
}
