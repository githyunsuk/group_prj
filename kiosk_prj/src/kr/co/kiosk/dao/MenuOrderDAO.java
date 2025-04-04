package kr.co.kiosk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.kiosk.vo.MenuOrderVO;
import kr.co.kiosk.vo.MenuVO;

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
			insertMenuOrder.append("	insert into menu_order(order_id, menu_id, category_id, quantity, total_price)")
					.append("	values(?,?,?,?,?)");
			pstmt = con.prepareStatement(insertMenuOrder.toString());

			pstmt.setInt(1, moVO.getOrderId());
			pstmt.setInt(2, moVO.getMenuId());
			pstmt.setInt(3, moVO.getCategoryId());
			pstmt.setInt(4, moVO.getQuantity());
			pstmt.setInt(5, moVO.getTotalPrice());

			pstmt.executeUpdate();
		} finally {
			dbCon.closeDB(null, pstmt, con);
		}
	}// insertMenuOrder

	public int deleteMenuOrder(int orderId) throws SQLException {
		int rowCnt = 0;

		Connection con = null;
		PreparedStatement pstmt = null;

		DbConnection dbCon = DbConnection.getInstance();
		try {
			con = dbCon.getConn();

			String sql = "delete from menu_order where order_id=?";
			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, orderId);

			rowCnt = pstmt.executeUpdate();

		} finally {
			dbCon.closeDB(null, pstmt, con);
		}
		return rowCnt;
	}//deleteMenuOrder

	// 해당 orderId와 같은 menu_order 목록만 찾기
	public List<MenuOrderVO> selectMenuOrder(int orderId) throws SQLException {
		List<MenuOrderVO> list = new ArrayList<MenuOrderVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		DbConnection dbCon = DbConnection.getInstance();

		try {
			con = dbCon.getConn();
			String sql = "select ORDER_ID, MENU_ID, CATEGORY_ID, QUANTITY, TOTAL_PRICE from MENU_ORDER where ORDER_ID=?";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, orderId);

			rs = pstmt.executeQuery();

			MenuOrderVO moVO = null;
			while (rs.next()) {
				moVO = new MenuOrderVO();
				moVO.setOrderId(rs.getInt("order_id"));
				moVO.setMenuId(rs.getInt("menu_id"));
				moVO.setCategoryId(rs.getInt("category_id"));
				moVO.setQuantity(rs.getInt("quantity"));
				moVO.setTotalPrice(rs.getInt("total_price"));

				list.add(moVO);
			}

		} finally {
			dbCon.closeDB(rs, pstmt, con);
		}

		return list;
	}// selectMenuOrder
}
