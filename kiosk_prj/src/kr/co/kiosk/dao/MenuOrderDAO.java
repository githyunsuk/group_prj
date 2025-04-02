package kr.co.kiosk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import kr.co.kiosk.vo.MenuOrderVO;

public class MenuOrderDAO {

	private static MenuOrderDAO moDAO;

	private MenuOrderDAO() {

	}// MenuOrderDAO

	public static MenuOrderDAO getInstance() {
		if (moDAO == null) {
			moDAO = new MenuOrderDAO();
		}

		return moDAO;
	}// getInstance

	public void insertMenuOrder(MenuOrderVO moVO) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;

		DbConnection dbCon = DbConnection.getInstance();
		try {
			con = dbCon.getConn();
			StringBuilder insertMenuOrder = new StringBuilder();
			insertMenuOrder
			.append("	insert into menu_order(order_id, menu_id, category_id, quantity)")
			.append("	values(?,?,?,?)");
			pstmt = con.prepareStatement(insertMenuOrder.toString());
			
			pstmt.setInt(1, moVO.getOrderId());
			pstmt.setInt(2, moVO.getMenuId());
			pstmt.setInt(3, moVO.getCategoryId());
			pstmt.setInt(4, moVO.getQuantity());
			
			pstmt.executeUpdate();
		} finally {
			dbCon.closeDB(null, pstmt, con);
		}
	}//insertMenuOrder
}
