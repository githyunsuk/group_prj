package kr.co.kiosk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import kr.co.kiosk.vo.MemberVO;
import kr.co.kiosk.vo.MenuVO;
import kr.co.kiosk.vo.StockUpVO;
import kr.co.kiosk.vo.StockVO;

public class AdminStockDAO {

	private static AdminStockDAO aStockDAO;
	
	private AdminStockDAO() {
		
	}
	
	public static AdminStockDAO getInstatnce() {
		if(aStockDAO == null) {
			aStockDAO = new AdminStockDAO();
		}
		return aStockDAO;
	}
	
	//재고 리스트 불러오기 
	public List<StockVO> getStockListAll() throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		DbConnection dbCon = DbConnection.getInstance();
		
		List<StockVO> stVOList = new ArrayList<StockVO>();
		
		try {
			con = dbCon.getConn();
			String getStockListQuery = "SELECT MENU_ID, CATEGORY_ID, MENU_NAME, UNIT_NAME, INPUT_DATE, QUANTITY FROM STOCK ORDER BY CATEGORY_ID ASC ";
			
			pstmt = con.prepareStatement(getStockListQuery);
			rs = pstmt.executeQuery();
			
		    while (rs.next()) {
				StockVO sVO = new StockVO();
				sVO.setMenuId(rs.getInt("MENU_ID"));
				sVO.setCategoryId(rs.getInt("CATEGORY_ID"));
				sVO.setMenuName(rs.getString("MENU_NAME"));
				sVO.setUnitName(rs.getString("UNIT_NAME"));
				sVO.setInputDate(rs.getDate("INPUT_DATE"));
				sVO.setQuantity(rs.getInt("QUANTITY"));
				
				stVOList.add(sVO);
			}
			
		} finally {
			dbCon.closeDB(rs, pstmt, con);
		}
		
		return stVOList;
	}
	
	//재고에 새 상품 추가하기(MenuService.addMenu()에서 호출)
	public boolean insertNewStock(MenuVO menuVO) throws SQLException {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		DbConnection dbCon = DbConnection.getInstance();
		
		try {
			con = dbCon.getConn();
			
			String query = "INSERT INTO STOCK (MENU_ID, CATEGORY_ID , QUANTITY , INPUT_DATE , MENU_NAME , UNIT_NAME )\r\n"
					+ "VALUES (\r\n"
					+ "?, ?, ?, ?, ?, ?\r\n"
					+ ")";
			
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, menuVO.getMenuId());
			pstmt.setInt(2, menuVO.getCategoryId());
			pstmt.setInt(3, 0);
			pstmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
			pstmt.setString(5, menuVO.getMenuName());
			pstmt.setString(6, menuVO.getUnitName());
			
			return pstmt.executeUpdate() > 0;
			
		} finally {
			dbCon.closeDB(rs, pstmt, con);
		}
	}
	
	//해당 상품 입고(재고량 변경)
	public boolean updateStock(int menuId, int quantity) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		DbConnection dbCon = DbConnection.getInstance();
		
		try {
			con = dbCon.getConn();
			
			String query = " UPDATE STOCK SET QUANTITY = QUANTITY + ? WHERE MENU_ID = ? ";
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, quantity);
			pstmt.setInt(2, menuId);
			
			return pstmt.executeUpdate() > 0;
			
		} finally {
			dbCon.closeDB(rs, pstmt, con);
		}
		
	}
	//입고 테이블 작성에 필요한, menuId로 category_id값 가져오기
	public int selectCategory(int menuId) throws SQLException {
		int category = 0;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		DbConnection dbCon = DbConnection.getInstance();
		
		try {
			con = dbCon.getConn();
			String query = " SELECT category_id FROM MENU WHERE MENU_ID = ? ";
			
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, menuId);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
	            category = rs.getInt("category_id");
	        }
			
			return category;
			
		} finally {
			dbCon.closeDB(rs, pstmt, con);
		}
	}
	
	//입고 테이블(Stock_up) 작성 : 상품 입고시 insert
	public boolean insertStockUp(String ioType, int menuId, int categoryId, int quantity, LocalDateTime inputDate ) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		DbConnection dbCon = DbConnection.getInstance();
		
		try {
			con = dbCon.getConn();
		
			String query = "INSERT INTO STOCK_UP (IO_TYPE, MENU_ID, CATEGORY_ID, QUANTITY_RECEIVED, INPUT_DATE)\r\n"
					+ "VALUES (?, ?, ?, ? ,?) ";
			
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, ioType);
			pstmt.setInt(2, menuId);
			pstmt.setInt(3, categoryId);
			pstmt.setInt(4, quantity);
			pstmt.setTimestamp(5, Timestamp.valueOf(inputDate));
			
			return pstmt.executeUpdate() > 0;

		} finally {
			dbCon.closeDB(rs, pstmt, con);
		}
		
	}
	//
	
	//입출고 내역 전체 조회
	public List<StockUpVO> SelectStockUpList() throws SQLException{
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		DbConnection dbCon = DbConnection.getInstance();
		List<StockUpVO> stockUpList = new ArrayList<StockUpVO>();
		
		try {
			con = dbCon.getConn();
			String query = "SELECT IO_TYPE, MENU_ID, CATEGORY_ID, QUANTITY_RECEIVED, INPUT_DATE FROM STOCK_UP ORDER BY INPUT_DATE DESC   "; //최신날짜부터 
			pstmt = con.prepareStatement(query);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				StockUpVO vo = new StockUpVO();
				vo.setIoType(rs.getString("IO_TYPE"));
				vo.setMenuId(rs.getInt("MENU_ID"));
				vo.setCategoryId(rs.getInt("CATEGORY_ID"));
				vo.setQuantity(rs.getInt("QUANTITY_RECEIVED"));
				vo.setInputDate(rs.getTimestamp("INPUT_DATE").toLocalDateTime());
				
				stockUpList.add(vo);
			}
			
		} finally {
			dbCon.closeDB(rs, pstmt, con);
		}
		return stockUpList;
		
	}
	
	
	
}
