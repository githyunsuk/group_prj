package kr.co.kiosk.adminService;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import kr.co.kiosk.dao.AdminStockDAO;
import kr.co.kiosk.vo.StockVO;

public class StockManageService {

	private AdminStockDAO aDAO;
	
	public StockManageService() {
		this.aDAO = AdminStockDAO.getInstatnce();
	}
	
	
	//재고 가져오기( 3:사이드메뉴, 4:음료, 5:재료만 가져옴)
	public List<StockVO> stockVOList(){
		List<StockVO> list = new ArrayList<StockVO>();
		
		try {
			list = aDAO.getStockListAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list; 
	}
	
	//상품 입고하기(동시에 입고내역 테이블에도 행 생성하기)
	public boolean saveStock(int menuId, int quantity) {
		
		boolean result = false;
		try {
			result = aDAO.updateStock(menuId, quantity);
			
			int categoryId = aDAO.selectCategory(menuId); //insertStockUp()에 필요하다.
			//입고내역 테이블에도 기록하기
			LocalDateTime now = LocalDateTime.now();
			aDAO.insertStockUp("입고", menuId, categoryId, quantity, now);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
}
